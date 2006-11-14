/*
 * POIStyleRepository.java
 *
 * Created on November 14, 2006, 3:03 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package org.wiztools.xml2spreadsheet.util;

import java.util.HashMap;

/**
 *
 * @author subhash
 */
public class StyleRepository {
    
    private HashMap<String, Object> hm = new HashMap<String, Object>();
    
    public void put(final String hash, final Object style){
        hm.put(hash, style);
    }
    
    public Object get(String hash){
        return hm.get(hash);
    }
    
    /** Creates a new instance of POIStyleRepository */
    public StyleRepository() {
    }
    
}
