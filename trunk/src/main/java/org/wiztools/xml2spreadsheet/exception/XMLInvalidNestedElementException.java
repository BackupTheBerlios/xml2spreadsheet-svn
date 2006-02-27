/*
 * XMLInvalidNestedElementException.java
 *
 * Created on April 6, 2005, 12:09 PM
 */

package org.wiztools.xml2spreadsheet.exception;

/**
 *
 * @author subhash
 */
public class XMLInvalidNestedElementException extends XMLSyntaxException {
    
    /** Creates a new instance of XMLInvalidNestedElementException */
    public XMLInvalidNestedElementException(String parentElement, String childElement) {
        super("Invalid element inside <"+parentElement+">: <"+childElement+">");
    }
    
}
