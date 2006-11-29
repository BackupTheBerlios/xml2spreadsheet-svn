/*
 * SyntaxException.java
 *
 * Created on April 18, 2005, 7:30 PM
 */

package org.wiztools.xml2spreadsheet.exception;

/**
 *
 * @author subhash
 */
public class OperationException extends XML2XLSFatalException{
    
    /** Creates a new instance of SyntaxException */
    public OperationException(String str) {
        super(str);
    }
    
}
