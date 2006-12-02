/*
 * ConvertMain.java
 *
 * Created on April 5, 2005, 7:03 PM
 */

package org.wiztools.xml2spreadsheet;

import java.util.ResourceBundle;
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
    
    private static ResourceBundle rb = ResourceBundle.getBundle("org.wiztools.xml2spreadsheet.message");
    
    private static void print(PrintStream ps, String msg){
        ps.println(msg);
    }
    
    private static String getHelpText(){
        String help = rb.getString("msg.Give_following_arguments");
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
            } else if("--colors".equals(arg[0])){
                print(System.out, rb.getString("msg.The_colors_supported"));
                Collection<String> colors = POIColor.getInstance().getSupportedColors();
                for(String color: colors){
                    print(System.out, color);
                }
                System.exit(0);
            } else{
                print(System.err, getHelpText());
                System.exit(-1);
            }
        } else if(arg.length != 2){
            print(System.err, getHelpText());
            System.exit(-1);
        }
        
        try{
            XML2SpreadSheet.convert(new FileInputStream(arg[0]),
                    new FileOutputStream(arg[1]));
        } catch(FileNotFoundException fnfe){
            print(System.err, rb.getString("err.FileNotFoundException"));
            fnfe.printStackTrace(System.err);
        } catch(IOException ioe){
            print(System.err, rb.getString("err.IOException"));
            ioe.printStackTrace(System.err);
        } catch(XML2XLSFatalException e){
            print(System.err, rb.getString("err.XML2XLSFatalException")+e.getMessage());
            e.printStackTrace(System.err);
        }
    }
}

