/*
 * POIStyleRepository.java
 *
 * Created on November 14, 2006, 3:03 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package org.wiztools.xml2spreadsheet.poiimpl;

import java.util.HashMap;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;

/**
 *
 * @author subhash
 */
public class POIStyleRepository {
    
    private static POIStyleRepository psr = new POIStyleRepository();
    private HashMap<String, HSSFCellStyle> hm = new HashMap<String, HSSFCellStyle>();
    
    public void put(final String hash, final HSSFCellStyle style){
        hm.put(hash, style);
    }
    
    public HSSFCellStyle get(String hash){
        return hm.get(hash);
    }
    
    /** Creates a new instance of POIStyleRepository */
    private POIStyleRepository() {
    }
    
    public static POIStyleRepository getInstance(){
        return psr;
    }
    
}
