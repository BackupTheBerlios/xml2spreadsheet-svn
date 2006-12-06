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
import java.util.logging.Logger;
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
    
    private static final Logger LOG = 
            Logger.getLogger(XMLValidator.class.getName());
    
    private static Validator validator;
    
    /** Creates a new instance of XMLValidator */
    private XMLValidator() {
    }
    
    private static void init(){
        SchemaFactory factory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
        LOG.info("Factory: "+factory);
        ClassLoader cl = XMLValidator.class.getClassLoader();
        URL schema_url = cl.getResource("org/wiztools/xml2spreadsheet/xml2spreadsheet.xsd");
        LOG.info("URL: "+schema_url);
        Schema schema = null;
        try{
            schema = factory.newSchema(schema_url);
            LOG.info("Schema: "+schema);
            validator = schema.newValidator();
            LOG.info("Validator: "+validator);
        }
        catch(SAXException se){
            LOG.throwing(XMLValidator.class.getName(), "init", se);
            assert true:"The DTD bundled is not valid!";
        }
    }
    
    public static void checkValidity(InputStream is) throws 
            IOException, SAXException{
        if(validator == null){
            init();
        }
        Source source = new StreamSource(is);
        validator.validate(source);
    }
    
 }
