package todosutils;

import java.io.*;
import javax.xml.*;
import javax.xml.parsers.*;
import javax.xml.transform.dom.*;
import javax.xml.transform.stream.*;
import javax.xml.validation.*;
import org.w3c.dom.*;
import org.xml.sax.*;

public class ValidazioneXML
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
    
    public static boolean validate (String xml, File xsd)
    {
        try
        {
            SchemaFactory sf = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
            Schema s = sf.newSchema(new StreamSource(xsd));
            
            s.newValidator().validate(new StreamSource(new StringReader(xml)));
            
            return true;
        }
        catch (IOException e)
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
