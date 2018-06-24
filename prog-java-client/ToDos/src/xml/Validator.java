/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package xml;

import java.io.*;
import javax.xml.*;
import javax.xml.parsers.*;
import javax.xml.transform.dom.*;
import javax.xml.transform.stream.*;
import javax.xml.validation.*;
import org.w3c.dom.*;
import org.xml.sax.*;

/**
 *
 * @author Paolo
 */
public class Validator
{

    public static boolean validate (File xml, File xsd)
    {
        try
        {
            DocumentBuilder db = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            SchemaFactory sf = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
            Document d = db.parse(xml);
            Schema s = sf.newSchema(new StreamSource(xsd));
            
            s.newValidator().validate(new DOMSource(d));
            
            return true;
        }
        catch (IOException | ParserConfigurationException e)
        {
            System.err.println(e.getMessage());
        }
        catch (SAXException ex)
        {
            System.err.println("Errore di validazione: " + ex.getMessage());
        }
        
        return false;
    }
}
