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
            ToDoService.inviaEvento("Avvio");
            
            FXMLLoader loader = new FXMLLoader();
            
            Parent root = loader.load(getClass().getResource("ToDosUserInterface.fxml").openStream());

            Scene scene = new Scene(root);
            
            stage.setOnCloseRequest(evt -> {
                ((ToDosUserInterfaceController) loader.getController())
                    .scriviCacheInput();
                
                ToDoService.inviaEvento("Chiusura");
            });
            
            stage.setResizable(false);
            stage.setScene(scene);
            stage.show();
            
        }
        catch (Exception ex)
        {
            System.err.print(ex.getMessage());
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
