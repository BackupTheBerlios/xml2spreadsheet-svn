/*
 * POIStyleHashCreator.java
 *
 * Created on November 14, 2006, 3:09 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package org.wiztools.xml2spreadsheet.poiimpl;

import java.util.Arrays;

/**
 * This class creates a Hash of the style value provided for a cell.
 * The hash is nothing but the key/value pairs organized in a sorted format.
 * @author subhash
 */
public class POIStyleHashCreator {
    
    public static String getHash(final String cellStyleVal){
        String[] arr = cellStyleVal.split("\\s*;\\s*");
        int len = arr.length;
        String[] arrOut = new String[len];
        for(int i=0; i<arr.length; i++){
            String[] tarr = arr[i].split("\\s*:\\s*");
            if(tarr.length != 2){
                continue;
            }
            String key = tarr[0].trim();
            String val = tarr[1].trim();
            arrOut[i] = key + ":" + val + ";";
        }
        Arrays.sort(arrOut);
        StringBuilder sb = new StringBuilder();
        for(String str: arrOut){
            sb.append(str);
        }
        return sb.toString();
    }
    
    /** Creates a new instance of POIStyleHashCreator */
    private POIStyleHashCreator() {
    }
    
}
