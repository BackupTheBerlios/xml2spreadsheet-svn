/*
 * SheetEntity.java
 *
 * Created on April 7, 2005, 5:06 PM
 */

package org.wiztools.xml2spreadsheet.entity;

import java.util.Map;

/**
 *
 * @author subhash
 */
public class SheetEntity {
    
    private Map<String, String> attributes;
    
    /** Creates a new instance of SheetEntity */
    public SheetEntity() {
    }
    
    public void setAttributes(Map<String, String> attributes){
        this.attributes = attributes;
    }
    
    public Map<String, String> getAttributes(){
        return attributes;
    }
}
