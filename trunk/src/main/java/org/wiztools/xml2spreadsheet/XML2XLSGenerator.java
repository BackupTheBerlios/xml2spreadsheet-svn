/*
 * XML2XLSGenerator.java
 *
 * Created on April 7, 2005, 5:53 PM
 */

package org.wiztools.xml2spreadsheet;

import org.wiztools.xml2spreadsheet.entity.CellEntity;
import org.wiztools.xml2spreadsheet.entity.RowEntity;
import org.wiztools.xml2spreadsheet.entity.SheetEntity;
import org.wiztools.xml2spreadsheet.entity.WorkBookEntity;
import org.wiztools.xml2spreadsheet.exception.XML2XLSFatalException;
import org.wiztools.xml2spreadsheet.exception.XMLInvalidNestedElementException;
import org.wiztools.xml2spreadsheet.exception.XMLSyntaxException;
import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;

/**
 *
 * @author subhash
 */
public class XML2XLSGenerator {
    
    private WorkBookGenerationHandler wbgh;
    
    /** Creates a new instance of XML2XLSGenerator */
    public XML2XLSGenerator() {
    }
    
    private int getIntFromStr(final String str)
    throws IllegalArgumentException{
        try{
            int i = Integer.parseInt(str);
            if(i<0){
                throw new IllegalArgumentException(
                        "Illegal attribute value: "+i);
            }
            return i;
        } catch(NumberFormatException nfe){
            throw new IllegalArgumentException(
                    "Illegal attribute value: "+str);
        }
    }
    
    private Date getDate(final String date, final String format)
    throws XML2XLSFatalException{
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        try{
            return sdf.parse(date);
        } catch(ParseException pe){
            throw new XML2XLSFatalException(
                    "Date format ("+format+") conversion failed: "+date, pe);
        }
    }
    
    private Calendar getCalendar(final String date, final String format)
    throws XML2XLSFatalException{
        Date dt = getDate(date, format);
        Calendar cal = Calendar.getInstance();
        cal.setTime(dt);
        return cal;
    }
    
    private Document getDocument(final InputStream is)
    throws IOException, XML2XLSFatalException{
        SAXBuilder builder = new SAXBuilder();
        Document doc;
        try{
            doc = builder.build(is);
        } catch(JDOMException je){
            throw new XML2XLSFatalException("", je);
        }
        return doc;
    }
    
    private void processCell(final Element ecell)
    throws XML2XLSFatalException{
        List<Element> l = ecell.getChildren();
        if(l.size()>1){
            throw new XMLSyntaxException(
                    "<cell> can have only one child element: "
                    + "text|number|formula");
        }
        for(Element e: l){
            String name = e.getName();
            CellEntity cell = new CellEntity();
            if("text".equals(name)){
                wbgh.setCellValue(e.getText());
            } else if("number".equals(name)){
                String doubleStr = e.getTextTrim();
                double d = -1;
                try{
                    d = Double.parseDouble(doubleStr);
                } catch(NumberFormatException nfe){
                    throw new XML2XLSFatalException(
                            "<number> cell value is not valid double: "
                            + doubleStr, nfe);
                }
                wbgh.setCellValue(d);
            } else if("formula".equals(name)){
                String formula = e.getTextTrim();
                if(formula == null || "".equals(formula)){
                    throw new XML2XLSFatalException(
                            "element <formula> cannot be empty");
                }
                wbgh.setCellFormula(formula);
            } else if("date".equals(name)){
                String format = e.getAttributeValue("format");
                if(format == null){
                    throw new XML2XLSFatalException(
                            "<date> element should have `format'"
                            + " attribute specified");
                }
                wbgh.setCellValue(getDate(e.getTextTrim(), format));
            } else if("calendar".equals(name)){
                String format = e.getAttributeValue("format");
                if(format == null){
                    throw new XML2XLSFatalException(
                            "<date> element should have `format'"
                            + " attribute specified");
                }
                wbgh.setCellValue(getCalendar(e.getTextTrim(), format));
            } else if("boolean".equals(name)){
                String value = e.getTextTrim();
                boolean bolVal;
                if("true".equals(value)){
                    bolVal = true;
                } else if("false".equals(value)){
                    bolVal = false;
                } else{
                    throw new XML2XLSFatalException(
                            "<boolean> cell can have only true/false as value. "
                            + "The present value: " + value);
                }
                wbgh.setCellValue(bolVal);
            } else{
                throw new XMLInvalidNestedElementException("cell", name);
            }
        }
    }
    
    private void processRow(final Element erow)
    throws XML2XLSFatalException{
        List<Element> l = erow.getChildren();
        for(Element e: l){
            String name = e.getName();
            if("cell".equals(name)){
                String placementStr = e.getAttributeValue("placement");
                int placement = getIntFromStr(placementStr);
                CellEntity cell = new CellEntity();
                String styleVal = e.getAttributeValue("style");
                if(styleVal != null){
                    Map<String, String> attributes = new HashMap<String, String>();
                    attributes.put("style", styleVal);
                    cell.setAttributes(attributes);
                }
                wbgh.createCell(cell, (short)placement);
                processCell(e);
            } else{
                throw new XMLInvalidNestedElementException("row", name);
            }
        }
    }
    
    private void processMergeRegion(final Element emerge)
    throws XML2XLSFatalException{
        String reg = emerge.getAttributeValue("region");
        if(reg == null){
            throw new XML2XLSFatalException(
                    "<merge> element should have `region' attribute");
        }
        String[] arr = reg.split("\\s*,\\s*");
        if(arr.length != 4){
            throw new XML2XLSFatalException(
                    "region attribute for <merge> should have the "
                    + "format: N,N,N,N: "+reg);
        }
        int[] regions = new int[4];
        for(int i=0;i<arr.length;i++){
            int tmp = -1;
            try{
                regions[i] = Integer.parseInt(arr[i]);
                if(regions[i]<0){
                    throw new XML2XLSFatalException(
                            "region attribute cannot have -ve value: "+reg);
                }
            } catch(NumberFormatException nfe){
                throw new XML2XLSFatalException(
                        "region attribute has non-number value: "+reg);
            }
        }
        wbgh.mergeCells(regions[0],
                (short)regions[1],
                regions[2],
                (short)regions[3]);
    }
    
    private void processColumnWidth(final Element e)
    throws XML2XLSFatalException{
        String columnStr = e.getAttributeValue("column");
        String widthStr = e.getAttributeValue("width");
        if(columnStr == null || widthStr == null){
            throw new XML2XLSFatalException(
                    "<column-width> element should have both the attributes: column and width");
        }
        short column = -1;
        short width = -1;
        try{
            column = Short.parseShort(columnStr);
            width = Short.parseShort(widthStr);
            if(column < 0 || width < 0){
                throw new XML2XLSFatalException("column or width attribute cannot be negative");
            }
        } catch(NumberFormatException nfe){
            throw new XML2XLSFatalException("column and width attribute should be numbers");
        }
        wbgh.setColumnWidth(column, width);
    }
    
    private void processSheet(final Element esheet)
    throws XML2XLSFatalException{
        List<Element> l = esheet.getChildren();
        for(Element e: l){
            String name = e.getName();
            if("row".equals(name)){
                String placementStr = e.getAttributeValue("placement");
                int placement = getIntFromStr(placementStr);
                RowEntity row = new RowEntity();
                wbgh.createRow(row, placement);
                processRow(e);
            } else if("merge".equals(name)){
                processMergeRegion(e);
            } else if("column-width".equals(name)){
                processColumnWidth(e);
            } else{
                throw new XMLInvalidNestedElementException("sheet", name);
            }
        }
    }
    
    public void parse(final WorkBookGenerationHandler wbgh, final InputStream is)
    throws IOException, XML2XLSFatalException{
        this.wbgh = wbgh;
        Document doc = getDocument(is);
        Element root = doc.getRootElement();
        if(!"workbook".equals(root.getName())){
            throw new XMLSyntaxException("Root element should be <workbook>");
        }
        WorkBookEntity workBook = new WorkBookEntity();
        wbgh.createWorkBook(workBook);
        List<Element> l = root.getChildren();
        for(Element e: l){
            String name = e.getName();
            if("sheet".equals(name)){
                SheetEntity sheet = new SheetEntity();
                String namea = e.getAttributeValue("name");
                if(namea!=null && !"".equals(namea)){
                    Map<String, String> m = new HashMap<String, String>();
                    m.put("name", namea);
                    sheet.setAttributes(m);
                    wbgh.createSheet(sheet);
                } else{
                    wbgh.createSheet(sheet);
                }
                processSheet(e);
            } else{
                throw new XMLInvalidNestedElementException("workbook", name);
            }
        }
    }
}

