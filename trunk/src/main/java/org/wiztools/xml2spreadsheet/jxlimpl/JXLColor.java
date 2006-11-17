/*
 * JXLColor.java
 *
 * Created on November 17, 2006, 5:36 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package org.wiztools.xml2spreadsheet.jxlimpl;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;
import jxl.format.Colour;
import org.wiztools.xml2spreadsheet.exception.OperationException;
import org.wiztools.xml2spreadsheet.exception.XML2XLSFatalException;

/**
 *
 * @author subhash
 */
public class JXLColor {
    
    private static JXLColor jxlc;
    
    private Map<String, Colour> colorMap;
    
    /** Creates a new instance of JXLColor */
    private JXLColor() {
        colorMap = new HashMap<String, Colour>();
        Field[] arr = Colour.class.getFields();
        try{
            for(Field f: arr){
                //System.out.println("=>>>>"+f.getType().getName()+" :: "+Colour.class.getName());
                if(f.getType().getName().equals(Colour.class.getName())){
                    Colour color = (Colour)f.get(null);
                    //System.out.println("Putting in Map colors: "+f.getName().toLowerCase());
                    colorMap.put(f.getName().toLowerCase(), color);
                }
            }
        }
        catch(IllegalAccessException iae){
            //throw new XML2XLSFatalException("Access permission for reflection need to be provided for this application to work.");
            iae.printStackTrace();
        }
    }
    
    public static JXLColor getInstance(){
        if(jxlc == null){
            jxlc = new JXLColor();
        }
        return jxlc;
    }
    
    public Colour getColor(String colorStr) throws OperationException{
        colorStr = colorStr.toLowerCase();
        Colour c = colorMap.get(colorStr);
        if(c == null){
            throw new OperationException("Color not found in the map: "+colorStr);
        }
        return c;
    }
}
