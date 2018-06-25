/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package todos;

import java.io.*;
import java.net.URL;
import java.time.*;
import java.util.*;
import javafx.collections.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.*;
import javafx.scene.control.*;
import javafx.scene.control.cell.*;
import utility.*;
import xml.*;

/**
 *
 * @author Paolo
 */
public class ToDosUserInterfaceController implements Initializable
{

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
    
    @Override
    public void initialize (URL url, ResourceBundle rb)
    {
        initChoiceBoxes();
        
        initTableView();
        
    }
    
    private void initChoiceBoxes()
    {
        File xmlConf
             = new File("src\\todos\\config\\configurazione.xml");

        File xsdConf
             = new File("src\\todos\\config\\configurazione.xsd");

        Configurazione conf
                       = (Configurazione) Loader.loadObjectFromValidatedXML(
                        xmlConf,
                        xsdConf,
                        Configurazione.class
                );

        incaricato_cb.setItems(
                FXCollections.observableArrayList(
                        conf.getIncaricati()
                )
        );

        compito_cb.setItems(
                FXCollections.observableArrayList(
                        conf.getCompiti()
                )
        );
    }

    private void initTableView()
    {
        incaricato_col.setCellValueFactory(new PropertyValueFactory("incaricato"));
        
        compito_col.setCellValueFactory(new PropertyValueFactory("compito"));
        
        data_col.setCellValueFactory(new PropertyValueFactory("data"));
        
        ora_col.setCellValueFactory(new PropertyValueFactory("ora"));
        
        desc_col.setCellValueFactory(new PropertyValueFactory("descrizione"));
        
        ObservableList<ToDo> todos = ToDoService.caricaToDos();
        
        todos_tv.setItems(todos);
    }
    
    @FXML
    private void eliminaToDo (ActionEvent event)
    {

    }

    @FXML
    private void ricercaToDo (ActionEvent event)
    {

    }

    @FXML
    private void aggiungiToDo (ActionEvent event)
    {

        ToDo toAdd = new ToDo();
        
        String incaricato = incaricato_cb.getSelectionModel().getSelectedItem().toString();
        String compito = compito_cb.getSelectionModel().getSelectedItem().toString();
        
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
        
        String ora = ora_tf.getText();
        String descrizione  = descrizione_ta.getText();
        
        toAdd.setIncaricato(incaricato);
        toAdd.setCompito(compito);
        toAdd.setData(data);
        toAdd.setOra(ora);
        toAdd.setDescrizione(descrizione);
        
        ToDoService.inserisciToDo(toAdd);
        
        todos_tv.getItems().add(toAdd);
    }

}
