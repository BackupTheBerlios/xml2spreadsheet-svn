/*
 * ConvertMain.java
 *
 * Created on April 5, 2005, 7:03 PM
 */

package org.wiztools.xml2spreadsheet;

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
public class ConvertMain {
    
    private static void print(PrintStream ps, String msg){
        ps.println(msg);
    }
    
    private static String getHelpText(){
        String help = "Give following arguments:\n"
                + "\t1. XML input filename\n"
                + "\t2. XLS output filename\n";
        return help;
    }
    
    /**
     * The main method
     */
    public static void main(String[] arg) {
        if(arg.length == 1){
            if("-h".equals(arg[0]) || "--help".equals(arg[0])){
                print(System.out, getHelpText());
                System.exit(0);
            }
            else if("--colors".equals(arg[0])){
                print(System.out, "The colors supported: \n");
                Collection<String> colors = POIColor.getInstance().getSupportedColors();
                for(String color: colors){
                    print(System.out, color);
                }
                System.exit(0);
            }
            else{
                print(System.err, getHelpText());
                System.exit(-1);
            }
        }
        else if(arg.length != 2){
            print(System.err, getHelpText());
            System.exit(-1);
        }
        
        try{
            WorkBookCreator wbc = WorkBookCreatorFactory.getWorkBookCreator();
            XML2XLSGenerator gen = new XML2XLSGenerator();
            // Following line is capable of throwing Exception:
            gen.parse((WorkBookGenerationHandler)wbc, new FileInputStream(arg[0]));
            WorkBook workBook = wbc.getWorkBook();
            workBook.write(new FileOutputStream(arg[1]));
        }
        catch(FileNotFoundException fnfe){
            print(System.err, "FileNotFoundException occurred!");
            fnfe.printStackTrace(System.err);
        }
        catch(IOException ioe){
            print(System.err, "IOException occurred!");
            ioe.printStackTrace(System.err);
        }
        catch(XML2XLSFatalException e){
            print(System.err, "Error occurred: "+e.getMessage());
            e.printStackTrace(System.err);
        }
    }
}
