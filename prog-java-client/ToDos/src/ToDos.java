import java.io.*;
import java.net.*;
import javafx.application.*;
import javafx.fxml.*;
import javafx.scene.*;
import javafx.stage.*;


public class ToDos extends Application
{

    @Override
    public void start (Stage stage) throws Exception
    {
        try
        {
            new EventoDiNavigazioneGUI("Avvio").invia();
            
            FXMLLoader loader = new FXMLLoader();
            
            Parent root = loader.load(getClass().getResource("ToDosGUI.fxml").openStream());

            Scene scene = new Scene(root);
            
            stage.setOnCloseRequest(evt -> {
                
                ((ToDosGUIController) loader.getController())
                    .scriviCacheInput();
                
                try
                {
                    new EventoDiNavigazioneGUI("Termine").invia();
                }
                catch (UnknownHostException ex)
                {
                    System.err.print(ex.getMessage());
                }
            });
            
            stage.setResizable(false);
            stage.setScene(scene);
            stage.show();
            
        }
        catch (IOException ex)
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
