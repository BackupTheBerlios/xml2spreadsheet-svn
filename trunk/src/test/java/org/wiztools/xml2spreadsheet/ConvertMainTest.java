/*
 * ConvertMainTest.java
 * JUnit based test
 *
 * Created on November 14, 2006, 2:04 PM
 */

package org.wiztools.xml2spreadsheet;

import java.io.File;
import java.util.logging.Logger;
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
    
    private static final Logger LOG = 
            Logger.getLogger(ConvertMainTest.class.getName());
    
    public ConvertMainTest(String testName) {
        super(testName);
    }

    protected void setUp() throws Exception {
    }

    protected void tearDown() throws Exception {
    }
    
    private String[] getFolder(File file, String impl){
        String path = file.getAbsolutePath();
        int i = path.lastIndexOf(File.separatorChar);
        String fileName = path.substring(i+1);
        String[] arr = new String[]{path, System.getProperty("java.io.tmpdir")
                + File.separator + impl + "-" + fileName + ".xls"};
        return arr;
    }

    /**
     * Test of main method, of class org.wiztools.xml2spreadsheet.ConvertMain.
     */
    public void testMain() {
        LOG.info("main");
        
        try{
            File dir = new File("src/test/resources");
            File[] arr = dir.listFiles();
            
            System.setProperty("xml2xls.impl", "poi");
            for(File file: arr){
                String[] str_arr = getFolder(file, "poi");
                if(str_arr[1].matches(".*\\.xml.xls")){
                    System.out.println("== Running For POI: " + str_arr[1] + " ==");
                    ConvertMain.main(new String[]{"-i", str_arr[0], "-o", str_arr[1]});
                }
            }
            System.setProperty("xml2xls.impl", "jxl");
            for(File file: arr){
                String[] str_arr = getFolder(file, "jxl");
                if(str_arr[1].matches(".*\\.xml.xls")){
                    System.out.println("== Running For JXL: " + str_arr[1] + " ==");
                    ConvertMain.main(new String[]{"-i", str_arr[0], "-o", str_arr[1]});
                }
            }
        }
        catch(Exception e){
            e.printStackTrace();
            fail("An exception occurred: " + e.getMessage());
        }
        
    }
    
}
