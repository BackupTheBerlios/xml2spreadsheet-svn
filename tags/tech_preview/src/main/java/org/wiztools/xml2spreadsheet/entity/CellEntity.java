/*
 * CellEntity.java
 *
 * Created on April 7, 2005, 5:07 PM
 */

package org.wiztools.xml2spreadsheet.entity;

import java.util.Map;

/**
 *
 * @author subhash
 */
public class CellEntity {
    
    private Map<String, String> attributes;
    
    /** Creates a new instance of CellEntity */
    public CellEntity() {
    }
    
    public void setAttributes(Map<String, String> attributes){
        this.attributes = attributes;
    }
    
    public Map<String, String> getAttributes(){
        return attributes;
    }
}
