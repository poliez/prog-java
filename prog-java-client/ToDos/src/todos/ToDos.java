/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package todos;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 *
 * @author Paolo
 */
public class ToDos extends Application
{

    @Override
    public void start (Stage stage) throws Exception
    {
        try
        {
            Parent root = FXMLLoader.load(getClass().getResource("ToDosUserInterface.fxml"));

            Scene scene = new Scene(root);

            stage.setScene(scene);
            stage.show();
        }
        catch (Exception ex)
        {
            System.err.println(ex.getMessage());
        }
    }

    /**
     * @param args the command line arguments
     */
    public static void main (String[] args)
    {
        launch(args);
    }

}
