/*
 * ConvertMainTest.java
 * JUnit based test
 *
 * Created on November 14, 2006, 2:04 PM
 */

package org.wiztools.xml2spreadsheet;

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
        
        // TODO: Uses *ix specific hardcoded value
        // xml2xls.xml
        // String[] arg = new String[]{"src/test/resources/xml-xls.xml", "/tmp/out.xls"};
        String[] arg = new String[]{"src/test/resources/xml2xls.xml", "/tmp/out.xls"};
        
        try{
            ConvertMain.main(arg);
        }
        catch(Exception e){
            fail("An exception occurred: " + e.getMessage());
            e.printStackTrace();
        }
        
    }
    
}
