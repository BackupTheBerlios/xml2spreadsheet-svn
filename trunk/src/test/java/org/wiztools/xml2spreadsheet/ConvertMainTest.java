/*
 * ConvertMainTest.java
 * JUnit based test
 *
 * Created on November 14, 2006, 2:04 PM
 */

package org.wiztools.xml2spreadsheet;

import java.io.File;
import junit.framework.*;
import org.wiztools.xml2spreadsheet.exception.XML2XLSFatalException;
import org.wiztools.xml2spreadsheet.poiimpl.POIColor;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.Collection;

/**
 *
 * @author subhash
 */
public class ConvertMainTest extends TestCase {
    
    public ConvertMainTest(String testName) {
        super(testName);
    }

    protected void setUp() throws Exception {
    }

    protected void tearDown() throws Exception {
    }

    /**
     * Test of main method, of class org.wiztools.xml2spreadsheet.ConvertMain.
     */
    public void testMain() {
        System.out.println("main");
        
        String tmpDir = System.getProperty("java.io.tmpdir");
        
        String[] arg1 = new String[]{"src/test/resources/poi.xml", 
                tmpDir + File.separator + "poi-out.xls"};
        String[] arg2 = new String[]{"src/test/resources/poi_large.xml",
                tmpDir + File.separator + "poi-large-out.xls"};
        String[] arg3 = new String[]{"src/test/resources/jxl.xml",
                tmpDir + File.separator + "jxl-out.xls"};
        
        try{
            System.setProperty("xml2xls.impl", "poi");
            ConvertMain.main(arg1);
            ConvertMain.main(arg2);
            System.setProperty("xml2xls.impl", "jxl");
            ConvertMain.main(arg3);
        }
        catch(Exception e){
            fail("An exception occurred: " + e.getMessage());
            e.printStackTrace();
        }
        
    }
    
}
