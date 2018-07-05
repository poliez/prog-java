

import java.io.*;
import java.net.*;

/**
 * 
 * @author Paolo
 */
public class LogDiNavigazioneGUIThread extends Thread
{

    Socket _soc;
    
    File _xsdFile;
    File _txtLogFile;

    public LogDiNavigazioneGUIThread (String name, Socket soc, File xsdFile, File txtLogFile)
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
            
            todosutils.GestoreFileXML.appendValidetedXMLToTXT(xml, _xsdFile, _txtLogFile);
        }
        catch (Exception ex)
        {
            System.err.print(ex.getMessage());
        }
    }
}
