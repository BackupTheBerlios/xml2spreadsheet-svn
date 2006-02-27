/*
 * XML2XLSFatalException.java
 *
 * Created on April 5, 2005, 7:24 PM
 */

package org.wiztools.xml2spreadsheet.exception;

/**
 *
 * @author subhash
 */
public class XML2XLSFatalException extends Exception{
    
    /** Creates a new instance of XML2XLSFatalException */
    public XML2XLSFatalException(String str) {
        super(str);
    }
    
    public XML2XLSFatalException(String str, Throwable t) {
        super(str, t);
    }
    
}
