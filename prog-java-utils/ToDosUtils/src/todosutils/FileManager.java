/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package todosutils;

import java.io.*;

/**
 *
 * @author Paolo
 */
public class FileManager
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

    public static void salvaTxt (Object obj, String txtFilePath)
    {
        try
        (
            ObjectOutputStream out
                = new ObjectOutputStream(
                    new FileOutputStream(
                            txtFilePath
                    )
                );
        )
        {
            out.writeObject(obj);
        }
        catch (IOException ex)
        {
            System.err.print(ex.getMessage());
        }
    }
}
