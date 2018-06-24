/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package xml;

import java.io.*;
import com.thoughtworks.xstream.*;
import java.util.logging.*;
import utility.*;

/**
 *
 * @author Paolo
 */
public class Loader
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
            Logger.getLogger(Loader.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return null;        
    }

    public static Object loadObjectFromValidatedXML (File xmlFile, File xsdFile, Class cls)
    {
        if (!Validator.validate(xmlFile, xsdFile))
            return null;

        return loadObjectFromXML(xmlFile, cls);
    }

}
