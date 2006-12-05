/*
 * XMLValidator.java
 *
 * Created on December 5, 2006, 3:31 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package org.wiztools.xml2spreadsheet;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import javax.xml.XMLConstants;
import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;
import org.xml.sax.SAXException;

/**
 *
 * @author subhash
 */
class XMLValidator {
    
    private static Validator validator;
    
    /** Creates a new instance of XMLValidator */
    private XMLValidator() {
    }
    
    private static void init(){
        SchemaFactory factory = SchemaFactory.newInstance(XMLConstants.RELAXNG_NS_URI);
        ClassLoader cl = XMLValidator.class.getClassLoader();
        URL schema_url = cl.getResource("org/wiztools/xml2spreadsheet/xml2spreadsheet.rng");
        Schema schema = null;
        try{
            schema = factory.newSchema(schema_url);
            validator = schema.newValidator();
        }
        catch(SAXException se){
            assert true:"The DTD bundled is not valid!";
        }
    }
    
    public static boolean isValid(InputStream is) throws IOException{
        if(validator == null){
            init();
        }
        Source source = new StreamSource(is);
        try{
            validator.validate(source);
        }
        catch(SAXException se){
            return false;
        }
        return true;
    }
    
}
