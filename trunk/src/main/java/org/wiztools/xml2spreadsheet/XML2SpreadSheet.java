/*
 * XML2SpreadSheet.java
 *
 * Created on December 2, 2006, 5:08 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package org.wiztools.xml2spreadsheet;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
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
    
    /**
     * Accepts XML in the inputstream and outputs .xls in the output stream.
     *
     */
    public static void convert(InputStream in, OutputStream out) 
            throws IOException,
            XML2XLSFatalException{
        
        // Validate first before conversion
        if(!XMLValidator.isValid(in)){
            throw new XML2XLSFatalException("Invalid XML!");
        }
        
        WorkBookCreator wbc = WorkBookCreatorFactory.getWorkBookCreator();
        XML2XLSGenerator gen = new XML2XLSGenerator();
        // Following line is capable of throwing Exception:
        gen.parse((WorkBookGenerationHandler)wbc, in);
        WorkBook workBook = wbc.getWorkBook();
        workBook.write(out);
        
    }

    /**
     * Accepts input XML File as first parameter and outputs .xls as second File.
     * If file exists, will overwrite.
     */
    public static void convert(File in, File out) 
            throws FileNotFoundException,
            IOException,
            XML2XLSFatalException{
        FileInputStream is_in = new FileInputStream(in);
        FileOutputStream is_out = new FileOutputStream(out);
        convert(is_in, is_out);
    }
   
}
