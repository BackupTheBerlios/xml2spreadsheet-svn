/*
 * XML2SpreadSheet.java
 *
 * Created on December 2, 2006, 5:08 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package org.wiztools.xml2spreadsheet;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import org.wiztools.xml2spreadsheet.exception.XML2XLSFatalException;

/**
 *
 * @author subhash
 */
public final class XML2SpreadSheet {
    
    /** Creates a new instance of XML2SpreadSheet */
    private XML2SpreadSheet() {
    }
    
    public static void convert(InputStream in, OutputStream out) 
            throws FileNotFoundException,
            IOException,
            XML2XLSFatalException{
        
        WorkBookCreator wbc = WorkBookCreatorFactory.getWorkBookCreator();
        XML2XLSGenerator gen = new XML2XLSGenerator();
        // Following line is capable of throwing Exception:
        gen.parse((WorkBookGenerationHandler)wbc, in);
        WorkBook workBook = wbc.getWorkBook();
        workBook.write(out);
        
    }
    
}
