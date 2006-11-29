/*
 * JXLStyleCreator.java
 *
 * Created on November 17, 2006, 5:18 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package org.wiztools.xml2spreadsheet.jxlimpl;

import jxl.format.Alignment;
import jxl.format.Border;
import jxl.format.BorderLineStyle;
import jxl.format.Colour;
import jxl.format.Font;
import jxl.format.VerticalAlignment;
import jxl.write.WritableCell;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WriteException;
import org.wiztools.xml2spreadsheet.exception.OperationException;
import org.wiztools.xml2spreadsheet.util.StyleHashCreator;
import org.wiztools.xml2spreadsheet.util.StyleRepository;

/**
 *
 * @author subhash
 */
public class JXLStyleCreator {
    
    private static JXLStyleCreator jsc = new JXLStyleCreator();
    
    /** Creates a new instance of JXLStyleCreator */
    private JXLStyleCreator() {
    }
    
    public static JXLStyleCreator getInstance(){
        return jsc;
    }
    
    public void setBackground(WritableCellFormat wcf, Colour color){
        
    }
    
    public WritableCellFormat getStyle(final String cellStyleVal,
            final StyleRepository styleRepo)
                    throws OperationException{
        
        String hash = StyleHashCreator.getHash(cellStyleVal);
        // System.out.println("style hash: "+hash);
        WritableCellFormat style = (WritableCellFormat)styleRepo.get(hash);
        
        if(style != null){
            // System.out.println("Got style from repository for hash: "+hash);
            return style;
        }
        
        style = new WritableCellFormat();
        
        String[] arr = cellStyleVal.split("\\s*;\\s*");
        
        try{
        
            for(int i=0; i<arr.length; i++){
                String[] tarr = arr[i].split("\\s*:\\s*");
                if(tarr.length != 2){
                    continue;
                }
                String key = tarr[0];
                String val = tarr[1];
                if("background".equals(key)){
                    // style.setFillBackgroundColor(HSSFColor.BLACK.index);
                    style.setBackground(JXLColor.getInstance().getColor(val));
                }
                else if("color".equals(key)){
                    Font font = style.getFont();
                    // font.setColor(HSSFFont.COLOR_RED);
                    //font.setColor(getColor(val));
                    //style.setFont(font);
                }
                else if("align".equals(key)){
                    if("center".equals(val)){
                        style.setAlignment(Alignment.CENTRE);
                    }
                    else if("justify".equals(val)){
                        style.setAlignment(Alignment.JUSTIFY);
                    }
                    else if("left".equals(val)){
                        style.setAlignment(Alignment.LEFT);
                    }
                    else if("right".equals(val)){
                        style.setAlignment(Alignment.RIGHT);
                    }
                    else if("fill".equals(val)){
                        style.setAlignment(Alignment.FILL);
                    }
                    else if("general".equals(val)){
                        style.setAlignment(Alignment.GENERAL);
                    }
                    else if("center-selection".equals(val)){
                        style.setAlignment(Alignment.CENTRE);
                    }
                    else{
                        throw new OperationException("Invalid value for style align: "+val);
                    }
                }
                else if("valign".equals(key)){
                    if("center".equals(val)){
                        style.setVerticalAlignment(VerticalAlignment.CENTRE);
                    }
                    else if("bottom".equals(val)){
                        style.setVerticalAlignment(VerticalAlignment.BOTTOM);
                    }
                    else if("top".equals(val)){
                        style.setVerticalAlignment(VerticalAlignment.TOP);
                    }
                    else if("justify".equals(val)){
                        style.setVerticalAlignment(VerticalAlignment.JUSTIFY);
                    }
                    else{
                        throw new OperationException("Invalid value for style valign: "+val);
                    }
                }
                else if("text-decoration".equals(key)){
                    if("bold".equals(val)){
                        Font font = style.getFont();
                        //HSSFFont font = workBook.createFont();
                        //font.setBoldweight(font.BOLDWEIGHT_BOLD);
                        //style.setFont(font);
                    }
                    else if("normal".equals(val)){
                        //HSSFFont font = workBook.createFont();
                        //font.setBoldweight(font.BOLDWEIGHT_NORMAL);
                        //style.setFont(font);
                    }
                    else{
                        throw new OperationException(
                                "Invalid value for style text-decoration: "+val);
                    }
                }
                else if("border".equals(key)){
                    if("dashed".equals(val)){
                        style.setBorder(Border.ALL, BorderLineStyle.DASHED);
                    }
                    else if("dotted".equals(val)){
                        style.setBorder(Border.ALL, BorderLineStyle.DOTTED);
                    }
                    else if("double".equals(val)){
                        style.setBorder(Border.ALL, BorderLineStyle.DOUBLE);
                    }
                    else if("hair".equals(val)){
                        style.setBorder(Border.ALL, BorderLineStyle.HAIR);
                    }
                    else if("medium".equals(val)){
                        style.setBorder(Border.ALL, BorderLineStyle.MEDIUM);
                    }
                    else if("thick".equals(val)){
                        style.setBorder(Border.ALL, BorderLineStyle.THICK);
                    }
                    else{
                        throw new OperationException(
                                "Invalid value for style border: "+val);
                    }
                }
            }
        }
        catch(WriteException we){
            throw new OperationException("" + we.getMessage());
        }
        
        styleRepo.put(hash, style);
        
        return style;
    }
    
}
