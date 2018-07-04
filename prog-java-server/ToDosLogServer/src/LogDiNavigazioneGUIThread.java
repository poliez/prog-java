import java.io.*;
import java.net.*;
import todosutils.*;

/**
 * 
 * @author Paolo
 */
public class ToDoServerThread extends Thread
{

    Socket _soc;
    
    File _xsdFile;
    File _txtLogFile;

    public ToDoServerThread (String name, Socket soc, File xsdFile, File txtLogFile)
    {
        super(name);

        _soc = soc;
        
        _xsdFile = xsdFile;
        _txtLogFile = txtLogFile;
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
            String xml = oin.readUTF();
            
            GestoreFileXML.appendValidetedXMLToTXT(xml, _xsdFile, _txtLogFile);
        }
        catch (Exception ex)
        {
            System.err.print(ex.getMessage());
        }
    }
}
