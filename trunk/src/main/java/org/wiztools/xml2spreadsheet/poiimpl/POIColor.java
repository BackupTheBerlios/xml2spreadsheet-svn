/*
 * POIColor.java
 *
 * Created on April 11, 2005, 4:32 PM
 */

package org.wiztools.xml2spreadsheet.poiimpl;

import org.wiztools.xml2spreadsheet.exception.OperationException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import org.apache.poi.hssf.util.HSSFColor;

/**
 *
 * @author subhash
 */
public final class POIColor {
    
    private static POIColor poiColor;
    private Map<String, Integer> colorMap;
    private Collection<String> colorNames;
    
    /** Creates a new instance of POIColor */
    private POIColor() {
    }
    
    public static synchronized POIColor getInstance(){
        if(poiColor == null){
            poiColor = new POIColor();
            poiColor.populateColor();
        }
        return poiColor;
    }
    
    private void populateColor(){
        colorMap = new HashMap<String, Integer>();
        colorNames = new ArrayList<String>();
        Map m = HSSFColor.getIndexHash();
        Set s = m.keySet();
        for(Object o: s){
            String className = m.get(o).getClass().getName();
            // Example className: org.apache.poi.hssf.util.HSSFColor$DARK_RED
            // For the above example, the color name would be: dark_red
            className = className.split("\\$")[1].toLowerCase();
            colorNames.add(className);
            colorMap.put(className ,(Integer)o);
        }
    }
    
    public Collection<String> getSupportedColors(){
        return colorNames;
    }
    
    public short getColor(final String str) throws OperationException{
        Integer i = colorMap.get(str);
        if(i == null){
            throw new OperationException("Color value not found: "+str);
        }
        return (short)i.intValue();
    }
}

