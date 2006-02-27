/*
 * RowEntity.java
 *
 * Created on April 7, 2005, 5:07 PM
 */

package org.wiztools.xml2spreadsheet.entity;

import java.util.Map;

/**
 *
 * @author subhash
 */
public class RowEntity {
    
    private Map<String, String> attributes;
    
    /** Creates a new instance of RowEntity */
    public RowEntity() {
    }
    
    public void setAttributes(Map<String, String> attributes){
        this.attributes = attributes;
    }
    
    public Map<String, String> getAttributes(){
        return attributes;
    }
}
