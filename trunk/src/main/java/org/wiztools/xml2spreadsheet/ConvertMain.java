/*
 * ConvertMain.java
 *
 * Created on April 5, 2005, 7:03 PM
 */

package org.wiztools.xml2spreadsheet;

import java.io.InputStream;
import java.util.ResourceBundle;
import java.util.logging.Logger;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.GnuParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.wiztools.xml2spreadsheet.exception.XML2XLSFatalException;
import org.wiztools.xml2spreadsheet.poiimpl.POIColor;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.Collection;
import org.xml.sax.SAXException;


/**
 *
 * @author subhash
 */
public class ConvertMain {

    private static final Logger LOG = Logger.getLogger(ConvertMain.class.getName());
    private static ResourceBundle rb = ResourceBundle.getBundle("org.wiztools.xml2spreadsheet.message");
    
    private static void print(PrintStream ps, String msg){
        ps.println(msg);
    }
    
    private static String getHelpText(){
        String help = rb.getString("msg.Give_following_arguments");
        return help;
    }
    
    private static Options getCLIOptions(){
        Options options = new Options();
        
        // TODO i18ned
        Option o_help = new Option("h", "help", false, "Prints this message");
        Option o_validate = new Option("validate", true, "Validate given XML");
        Option o_input = new Option("i", "input", true, "Give input XML file path (should be used alongwith -o option)");
        Option o_output = new Option("o", "output", true, "Give the .xls file to be generated (should be used alongwith -i option)");
        Option o_template = new Option("t", "template", false, "Generate template XML");
        Option o_colors = new Option("colors", false, "List supported colors");
        
        options.addOption(o_help);
        options.addOption(o_validate);
        options.addOption(o_input);
        options.addOption(o_output);
        options.addOption(o_template);
        options.addOption(o_colors);
        
        return options;
    }
    
    private static void printHelp(){
        HelpFormatter formatter = new HelpFormatter();
        formatter.printHelp( "xml2spreadsheet", getCLIOptions());
    }
    
    /**
     * The main method
     */
    public static void main(String[] arg) {
        
        CommandLineParser parser = new GnuParser();
        
        try{
            CommandLine line = parser.parse(getCLIOptions(), arg);
            if(line.hasOption( "h" )) {
                printHelp();
            }
            else if(line.hasOption("validate")){
                String xmlFilePath = line.getOptionValue("validate");
                try{
                    XMLValidator.checkValidity(new FileInputStream(xmlFilePath));
                }
                catch(IOException ioe){
                    System.err.println("Error reading the input file "+
                            xmlFilePath + ": " + ioe.getMessage());
                }
                catch(SAXException se){
                    System.err.println("Error in parsing XML: " +
                            se.getMessage());
                }
            }
            else if(line.hasOption("t")){
                ClassLoader cl = ConvertMain.class.getClassLoader();
                try{
                    InputStream is = cl.getResourceAsStream("org/wiztools/xml2spreadsheet/template.xml");
                    int c;
                    while((c = is.read())!=-1){
                        System.out.print((char)c);
                    }
                    System.out.println();
                    is.close();
                }
                catch(IOException ioe){
                    LOG.throwing("ConvertMain", "main", ioe);
                    System.err.println("Template XML not bundled in Jar!");
                }
            }
            else if(line.hasOption("colors")){
                print(System.out, rb.getString("msg.The_colors_supported"));
                Collection<String> colors = POIColor.getInstance().getSupportedColors();
                for(String color: colors){
                    print(System.out, color);
                }
            }
            else if(line.hasOption("i") && line.hasOption("o")){
                String in_file = line.getOptionValue("i");
                String out_file = line.getOptionValue("o");
                try{
                    XML2SpreadSheet.convert(new FileInputStream(in_file),
                            new FileOutputStream(out_file));
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
            else{
                System.err.println("The allowed options are:");
                printHelp();
            }
        }
        catch(ParseException exp){
            System.err.println( "Parsing failed.  Reason: " + exp.getMessage() );
        }
        
    }
}

