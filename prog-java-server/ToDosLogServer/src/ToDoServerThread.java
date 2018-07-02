import java.io.*;
import java.net.*;
import todosutils.*;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Paolo
 */
public class ToDoServerThread extends Thread
{

    Socket _soc;

    public ToDoServerThread (String name, Socket soc)
    {
        super(name);

        _soc = soc;
    }

    @Override
    public void run ()
    {
        try 
        (
            ObjectInputStream oin 
                = new ObjectInputStream(
                    _soc.getInputStream()
                );
        )
        {
            File xsdFile
                = new File("src\\evento.xsd");
            
            File txtFile
                = new File("log.txt");
            
            String xml = oin.readUTF();
            
            XMLManager.appendValidetedXMLToTXT(xml, xsdFile, txtFile);
        }
        catch (Exception ex)
        {
            System.err.print(ex.getMessage());
        }
    }
}
