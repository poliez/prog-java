import todosutils.*;
import java.net.*;
import java.time.*;
import java.util.*;
import javafx.collections.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.*;
import javafx.scene.control.*;
import javafx.scene.control.cell.*;


public class ToDosGUIController implements Initializable
{

    private final String _inputCachePath = "input.bin";
    
    // <editor-fold desc="UI Controls' links">
    @FXML
    private TableView todos_tv;

    @FXML
    private PieChart todos_pie;

    @FXML
    private Button elimina_btn;

    @FXML
    private Button ricerca_btn;

    @FXML
    private Button aggiungi_btn;

    @FXML
    private TextField ora_tf;

    @FXML
    private DatePicker data_dp;

    @FXML
    private ChoiceBox compito_cb;

    @FXML
    private ChoiceBox incaricato_cb;
    
    @FXML
    private TextArea descrizione_ta;
    
    @FXML
    private TableColumn incaricato_col;
    
    @FXML
    private TableColumn compito_col;
    
    @FXML
    private TableColumn data_col;
    
    @FXML
    private TableColumn ora_col;
    
    @FXML
    private TableColumn desc_col;

    // </editor-fold>
    
    // <editor-fold desc="'Properties'">
    
    private String leggiIncaricato()
    {
        try
        {
            String incaricato 
                = incaricato_cb
                    .getSelectionModel()
                        .getSelectedItem()
                            .toString();
        
            return incaricato;
        }
        catch (Exception ex)
        {
            return "";
        }
    }
    
    private String leggiCompito()
    {
        try
        {
            String compito 
                = compito_cb
                    .getSelectionModel()
                        .getSelectedItem()
                            .toString();
        
            return compito;
        }
        catch (Exception ex)
        {
            return "";
        }
    }
    
    private Date leggiData()
    {
        try
        {
            // Il valore contenuto in un DatePicker JavaFX è un LocalDate, è
            // necessaria una conversione.
            Date data 
                = Date.from(
                    Instant.from(
                        data_dp
                            .getValue()
                            .atStartOfDay(
                                ZoneId.systemDefault()
                            )
                    )
                );

            return data;
        }
        catch (Exception ex)
        {
            return null;
        }
    }
    
    private String leggiOra()
    {
        String ora = ora_tf.getText();
        
        return ora;
    }
    
    private String leggiDescrizione()
    {
        String descrizione  = descrizione_ta.getText();
        
        return descrizione;
    }
    
    // </editor-fold>
    
    private ParametriDiConfigurazione _conf;
    
    private ObservableList<ToDo> _todos;
    
    @Override
    public void initialize (URL url, ResourceBundle rb)
    {
        caricaConfigurazione();
        
        caricaCacheInput();
        
        inizializzaTabella();
    }
    
    private void caricaConfigurazione()
    {
        _conf = new ParametriDiConfigurazione();

        inizializzaChoiceBox();
        
        aggiornaGrafico();
    }

    private void inizializzaChoiceBox()
    {
        incaricato_cb.setItems(
            FXCollections.observableArrayList(
                    _conf.getIncaricati()
            )
        );

        compito_cb.setItems(
            FXCollections.observableArrayList(
                    _conf.getCompiti()
            )
        );
    }
    
    private void inizializzaTabella()
    {
        incaricato_col.setCellValueFactory(new PropertyValueFactory("incaricato"));
        
        compito_col.setCellValueFactory(new PropertyValueFactory("compito"));
        
        data_col.setCellValueFactory(new PropertyValueFactory("data"));
        
        ora_col.setCellValueFactory(new PropertyValueFactory("ora"));
        
        desc_col.setCellValueFactory(new PropertyValueFactory("descrizione"));
        
        _todos = ArchivioToDos.caricaToDos();
        
        todos_tv.setItems(_todos);
    }
    
    @FXML
    private void eliminaToDo (ActionEvent event)
    {
        int index = todos_tv.getSelectionModel().getFocusedIndex();
        
        ToDo toRemove = (ToDo) _todos.get(index);
        
        boolean check = ArchivioToDos.eliminaToDo(toRemove.getId());
        
        if(check)
            _todos.remove(toRemove);
        
        aggiornaGrafico();

        try
        {
            new EventoDiNavigazioneGUI("Elimina ToDo").invia();
        }
        catch (UnknownHostException ex)
        {
            System.err.print(ex.getMessage());
        }
    }

    @FXML
    private void ricercaToDo (ActionEvent event)
    {   
        _todos 
            = ArchivioToDos
                .caricaToDos(
                    leggiIncaricato(),
                    leggiCompito(),
                    leggiData()
                );
        
        todos_tv.setItems(_todos);
        
        try
        {
            new EventoDiNavigazioneGUI("Ricerca ToDo").invia();
        }
        catch (UnknownHostException ex)
        {
            System.err.print(ex.getMessage());
        }
    }

    @FXML
    private void aggiungiToDo (ActionEvent event)
    {

        ToDo toAdd = estraiToDoDaForm();
        
        ArchivioToDos.inserisciToDo(toAdd);
        
        // Prelevo il ToDo appena inserito dal DB per avere l'id
        todos_tv
            .getItems()
                .add(ArchivioToDos
                        .prelevaUltimoInserito()
                );
        
        aggiornaGrafico();
        
        try
        {
            new EventoDiNavigazioneGUI("Aggiungi ToDo").invia();
        }
        catch (UnknownHostException ex)
        {
            System.err.print(ex.getMessage());
        }
    }
    
    private ToDo estraiToDoDaForm()
    {
        ToDo retVal = new ToDo();
        
        retVal.setIncaricato(leggiIncaricato());
        retVal.setCompito(leggiCompito());
        retVal.setData(leggiData());
        retVal.setOra(leggiOra());
        retVal.setDescrizione(leggiDescrizione());
        
        return retVal;
    }
    
    private void aggiornaGrafico()
    {

        ObservableList<PieChart.Data> pieChartData 
            = FXCollections.observableArrayList();

        Map<String, Integer> stat 
            = ArchivioToDos.prelevaStatistiche(_conf.getGiorniPrecedenti());

        for(String incaricato : stat.keySet())
        {   
            pieChartData.add(
                new PieChart.Data(incaricato, stat.get(incaricato))
            );
        }

        todos_pie.setData(pieChartData);
    }
    
    private void caricaCacheInput()
    {
        try
        {
            ToDo prevInput = (ToDo) GestoreFileBIN.leggiBin(_inputCachePath);

            incaricato_cb.setValue(prevInput.getIncaricato());
            compito_cb.setValue(prevInput.getCompito());

            Date data = prevInput.getData();
            
            if(data != null)
                data_dp
                    .setValue(
                        data
                            .toInstant()
                            .atZone(
                                ZoneId.systemDefault()
                            ).toLocalDate());

            ora_tf.setText(prevInput.getOra());
            descrizione_ta.setText(prevInput.getDescrizione());
        }
        catch (Exception ex)
        {
            System.err.print(ex.getMessage());
        }
    }
    
    public void scriviCacheInput()
    {
        GestoreFileBIN
            .salvaBin(
                estraiToDoDaForm(), 
                _inputCachePath
            );
    }
    
}
