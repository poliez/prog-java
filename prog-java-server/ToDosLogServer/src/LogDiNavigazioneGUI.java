import java.io.*;
import java.net.*;

public class LogDiNavigazioneGUI
{

    public static void main (String[] args)
    {
        int n = 0;
        
        File xsdFile 
            = new File("evento.xsd");
            
        File txtFile
            = new File("log.txt");
        
        try (ServerSocket servs = new ServerSocket(8080, 7))
        {
            while (true)
            {
                new LogDiNavigazioneGUIThread(
                    "ToDoServerThread"+ n++, 
                    servs.accept(),
                    xsdFile,
                    txtFile
                ).start();
            }
        }
        catch (IOException ex)
        {
            System.err.print(ex.getMessage());
        }
    }
}
