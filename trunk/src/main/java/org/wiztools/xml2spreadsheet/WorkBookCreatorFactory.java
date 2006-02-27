/*
 * WorkBookCreator.java
 *
 * Created on April 5, 2005, 7:15 PM
 */

package org.wiztools.xml2spreadsheet;
import org.wiztools.xml2spreadsheet.jxlimpl.JXLWorkBookCreator;
import org.wiztools.xml2spreadsheet.poiimpl.POIWorkBookCreator;


/**
 *
 * @author subhash
 */
public final class WorkBookCreatorFactory {
    
    /** Creates a new instance of WorkBookCreator */
    private WorkBookCreatorFactory() {
    }
    
    public static WorkBookCreator getWorkBookCreator(){
        String prop = System.getProperty("xml2xls.impl");
        if(prop == null || "poi".equals(prop)){
            return new POIWorkBookCreator();
        }
        else if("jxl".equals(prop)){
            return new JXLWorkBookCreator();
        }
        return new POIWorkBookCreator();
    }
}
