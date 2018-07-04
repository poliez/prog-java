package todosutils;

import java.io.*;
import com.thoughtworks.xstream.*;
import java.nio.file.*;
import javax.xml.parsers.*;
import javax.xml.transform.*;
import javax.xml.transform.dom.*;
import javax.xml.transform.stream.*;
import javax.xml.xpath.*;
import org.w3c.dom.*;
import org.xml.sax.*;

public class GestoreFileXML
{

    public static Object loadObjectFromFile (File file)
    {
        try
        {
            ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file));
            return ois.readObject();
        }
        catch (FileNotFoundException ex)
        {
            System.err.println(ex.getMessage());
        }
        catch (IOException | ClassNotFoundException ex)
        {
            System.err.println(ex.getMessage());
        }

        return null;
    }

    public static Object loadObjectFromXML (File xmlFile, Class cls)
    {
        XStream str = new XStream();
        
        try
        {
            String className = cls.getName().replaceAll("^([A-z]*[.])*", "");
            
            str.alias(
                className, 
                Class.forName(
                    cls.getName()
                )
            );
            
            return str.fromXML(xmlFile);
        }
        catch (ClassNotFoundException ex)
        {
            System.err.println(ex.getMessage());
        }
        
        return null;        
    }

    public static Object loadObjectFromValidatedXML (File xmlFile, File xsdFile, Class cls)
    {
        if (!ValidazioneXML.validate(xmlFile, xsdFile))
            return null;

        return loadObjectFromXML(xmlFile, cls);
    }

    public static void appendValidetedXMLToTXT (String xml, File xsdFile, File txtFile)
    {
        if(!ValidazioneXML.validate(xml, xsdFile))
            return;
        
        appendXMLtoTXT(xml, xsdFile, txtFile);
    }
    
    public static void appendXMLtoTXT(String xml, File xsdFile, File txtFile)
    {
        // Permette di riconoscere a colpo d'occhio dove si interrompe un log
        // e ne inizia un altro.
        xml = "\n\n" + xml;
        
        try
        {
            Files.write(txtFile.toPath(), xml.getBytes(), StandardOpenOption.APPEND);
        }
        catch(NoSuchFileException ex)
        {
            // Se il file non esiste l'APPEND solleva un eccezione. 
            // E' quindi necessario aprire il file in CREATE_NEW.
            try
            {
                Files.write(txtFile.toPath(), xml.getBytes(), StandardOpenOption.CREATE_NEW);
            }
            catch (IOException ex1)
            {
                System.err.print(ex1.getMessage());
            }
        }
        catch (IOException ex)
        {
            System.err.print(ex.getMessage());
        }
    }
    
    /**
     * 
     * Formatta del codice XML in modo da renderlo leggibile
     * 
     * @param xml Codice XML da formattare
     * @param indent Numero di spazi da utilizzare per l'indentazione
     * @return Codice XML formattato
     */
    public static String toPrettyString (String xml, int indent)
    {
        try
        {
            // Genero un documento dall'XML
            Document document = DocumentBuilderFactory.newInstance()
                    .newDocumentBuilder()
                    .parse(new InputSource(new ByteArrayInputStream(xml.getBytes("utf-8"))));

            // Rimuovo gli spazi al di fuori dei TAG
            document.normalize();
            XPath xPath = XPathFactory.newInstance().newXPath();
            NodeList nodeList = (NodeList) xPath.evaluate("//text()[normalize-space()='']",
                                                          document,
                                                          XPathConstants.NODESET);

            for (int i = 0; i < nodeList.getLength(); ++i)
            {
                Node node = nodeList.item(i);
                node.getParentNode().removeChild(node);
            }

            // Imposto il transformer per ottenere il formato desiderato
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            transformerFactory.setAttribute("indent-number", indent);
            Transformer transformer = transformerFactory.newTransformer();
            transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
            transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");

            // Restituisco la stringa formattata
            StringWriter stringWriter = new StringWriter();
            transformer.transform(new DOMSource(document), new StreamResult(stringWriter));
            return stringWriter.toString();
        }
        catch (Exception ex)
        {
            System.out.print(ex.getMessage());
            return "";
        }
    }

}
