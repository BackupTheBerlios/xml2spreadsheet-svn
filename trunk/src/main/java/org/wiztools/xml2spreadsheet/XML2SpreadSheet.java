/*
 * XML2SpreadSheet.java
 *
 * Created on December 2, 2006, 5:08 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package org.wiztools.xml2spreadsheet;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;
import org.wiztools.xml2spreadsheet.exception.XML2XLSFatalException;
import org.xml.sax.SAXException;

/**
 *
 * @author subhash
 */
public final class XML2SpreadSheet {
    
    private static final Logger LOG = 
            Logger.getLogger(XML2SpreadSheet.class.getName());
    
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
        
        List<Byte> l = new ArrayList<Byte>();
        int i = -1;
        while((i=in.read())!=-1){
            byte b = (byte)i;
            l.add(b);
        }
        int size = l.size();
        byte[] arr = new byte[size];
        int k = 0;
        for(byte b: l){
            arr[k] = b;
            k++;
        }
        
        // System.out.println("^"+new String(arr)+"$");
        ByteArrayInputStream b_in = new ByteArrayInputStream(arr);
        
        // Validate first before conversion
        try{
            XMLValidator.checkValidity(b_in);
        }
        catch(SAXException se){
            throw new XML2XLSFatalException("Invalid XML: " + se.getMessage());
        }
        
        LOG.info("~~~~~~~~~~Successfully validated XML~~~~~~~");
        
        b_in.reset();
        
        // Now the conversion logic
        WorkBookCreator wbc = WorkBookCreatorFactory.getWorkBookCreator();
        XML2XLSGenerator gen = new XML2XLSGenerator();
        // Following line is capable of throwing Exception:
        gen.parse((WorkBookGenerationHandler)wbc, b_in);
        WorkBook workBook = wbc.getWorkBook();
        workBook.write(out);
        
        // Help GC :-)
        arr = null;
        b_in = null;
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
