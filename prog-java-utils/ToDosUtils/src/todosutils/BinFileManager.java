package todosutils;

import java.io.*;

public class BinFileManager
{

    public static void salvaBin (Serializable obj, String binFilePath)
    {
        try
        {
            ObjectOutputStream out
                = new ObjectOutputStream(
                    new FileOutputStream(
                            binFilePath
                    )
                );

            out.writeObject(obj);
        }
        catch (IOException ex)
        {
            System.err.print(ex.getMessage());
        }

    }
    
    public static Object leggiBin (String binFilePath)
    {
        try 
        {
            ObjectInputStream instr
                = new ObjectInputStream(
                    new FileInputStream(
                        binFilePath
                    ));
            
            return instr.readObject();
        }
        catch (IOException | ClassNotFoundException ex)
        {
            System.err.print(ex.getMessage());
        }
        
        return null;
    }
}
