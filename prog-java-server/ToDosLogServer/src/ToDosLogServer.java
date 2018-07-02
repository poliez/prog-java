
import java.io.*;
import java.net.*;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Paolo
 */
public class ToDosLogServer
{

    public static void main (String[] args)
    {
        int n = 0;
        
        try (ServerSocket servs = new ServerSocket(8080, 7))
        {
            while (true)
            {
                new ToDoServerThread(
                    "ToDoServerThread"+ n++, 
                    servs.accept()
                ).start();
            }
        }
        catch (IOException ex)
        {
            System.err.print(ex.getMessage());
        }
    }
}
