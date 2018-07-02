import java.io.*;
import java.net.*;

/**
 *
 * @author Paolo
 */
public class ToDosLogServer
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
                new ToDoServerThread(
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
