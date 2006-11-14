/*
 * POIStyleCreator.java
 *
 * Created on April 11, 2005, 4:17 PM
 */

package org.wiztools.xml2spreadsheet.poiimpl;

import org.wiztools.xml2spreadsheet.exception.OperationException;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;


/**
 *
 * @author subhash
 */
public final class POIStyleCreator {
    
    private static POIStyleCreator styleCreator;
    
    /** Creates a new instance of POIStyleCreator */
    private POIStyleCreator() {
    }
    
    public static POIStyleCreator getInstance(){
        if(styleCreator == null){
            styleCreator = new POIStyleCreator();
        }
        return styleCreator;
    }
    
    private void setBorder(HSSFCellStyle style, short border){
        style.setBorderTop(border);
        style.setBorderBottom(border);
        style.setBorderLeft(border);
        style.setBorderRight(border);
    }
    
    private short getColor(String color) throws OperationException{
        return POIColor.getInstance().getColor(color);
    }
    
    public HSSFCellStyle getStyle(HSSFWorkbook workBook, String cellStyleVal)
                        throws OperationException{
        
        String hash = POIStyleHashCreator.getHash(cellStyleVal);
        // System.out.println("style hash: "+hash);
        HSSFCellStyle style = POIStyleRepository.getInstance().get(hash);
        
        if(style != null){
            // System.out.println("Got style from repository for hash: "+hash);
            return style;
        }
        
        style = workBook.createCellStyle();
        String[] arr = cellStyleVal.split("\\s*;\\s*");
        for(int i=0; i<arr.length; i++){
            String[] tarr = arr[i].split("\\s*:\\s*");
            if(tarr.length != 2){
                continue;
            }
            String key = tarr[0];
            String val = tarr[1];
            if("background".equals(key)){
                // style.setFillBackgroundColor(HSSFColor.BLACK.index);
                style.setFillForegroundColor(getColor(val));
            }
            else if("color".equals(key)){
                HSSFFont font = workBook.createFont();
                // font.setColor(HSSFFont.COLOR_RED);
                font.setColor(getColor(val));
                style.setFont(font);
            }
            else if("align".equals(key)){
                if("center".equals(val)){
                    style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
                }
                else if("justify".equals(val)){
                    style.setAlignment(HSSFCellStyle.ALIGN_JUSTIFY);
                }
                else if("left".equals(val)){
                    style.setAlignment(HSSFCellStyle.ALIGN_LEFT);
                }
                else if("right".equals(val)){
                    style.setAlignment(HSSFCellStyle.ALIGN_RIGHT);
                }
                else if("fill".equals(val)){
                    style.setAlignment(HSSFCellStyle.ALIGN_FILL);
                }
                else if("general".equals(val)){
                    style.setAlignment(HSSFCellStyle.ALIGN_GENERAL);
                }
                else if("center-selection".equals(val)){
                    style.setAlignment(HSSFCellStyle.ALIGN_CENTER_SELECTION);
                }
                else{
                    throw new OperationException("Invalid value for style align: "+val);
                }
            }
            else if("valign".equals(key)){
                if("center".equals(val)){
                    style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
                }
                else if("bottom".equals(val)){
                    style.setVerticalAlignment(HSSFCellStyle.VERTICAL_BOTTOM);
                }
                else if("top".equals(val)){
                    style.setVerticalAlignment(HSSFCellStyle.VERTICAL_TOP);
                }
                else if("justify".equals(val)){
                    style.setVerticalAlignment(HSSFCellStyle.VERTICAL_JUSTIFY);
                }
                else{
                    throw new OperationException("Invalid value for style valign: "+val);
                }
            }
            else if("text-decoration".equals(key)){
                if("bold".equals(val)){
                    HSSFFont font = workBook.createFont();
                    font.setBoldweight(font.BOLDWEIGHT_BOLD);
                    style.setFont(font);
                }
                else if("normal".equals(val)){
                    HSSFFont font = workBook.createFont();
                    font.setBoldweight(font.BOLDWEIGHT_NORMAL);
                    style.setFont(font);
                }
                else{
                    throw new OperationException(
                            "Invalid value for style text-decoration: "+val);
                }
            }
            else if("border".equals(key)){
                if("dashed".equals(val)){
                    short border = HSSFCellStyle.BORDER_DASHED;
                    setBorder(style, border);
                }
                else if("dotted".equals(val)){
                    short border = HSSFCellStyle.BORDER_DOTTED;
                    setBorder(style, border);
                }
                else if("double".equals(val)){
                    short border = HSSFCellStyle.BORDER_DOUBLE;
                    setBorder(style, border);
                }
                else if("hair".equals(val)){
                    short border = HSSFCellStyle.BORDER_HAIR;
                    setBorder(style, border);
                }
                else if("medium".equals(val)){
                    short border = HSSFCellStyle.BORDER_MEDIUM;
                    setBorder(style, border);
                }
                else if("thick".equals(val)){
                    short border = HSSFCellStyle.BORDER_THICK;
                    setBorder(style, border);
                }
                else{
                    throw new OperationException(
                            "Invalid value for style border: "+val);
                }
            }
        }
        
        POIStyleRepository.getInstance().put(hash, style);
        
        return style;
    }
}
