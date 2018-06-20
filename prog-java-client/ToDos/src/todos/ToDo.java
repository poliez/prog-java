/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package todos;

import java.util.*;
import javafx.beans.property.*;

/**
 *
 * @author Paolo
 */
public class ToDo
{
    SimpleStringProperty incaricato;
    SimpleStringProperty compito;
    Date data;
    SimpleStringProperty ora;
    SimpleStringProperty descrizione;

    // <editor-fold desc="Getters e Setters">
    
    public SimpleStringProperty getIncaricato ()
    {
        return incaricato;
    }

    public void setIncaricato (SimpleStringProperty incaricato)
    {
        this.incaricato = incaricato;
    }

    public SimpleStringProperty getCompito ()
    {
        return compito;
    }

    public void setCompito (SimpleStringProperty compito)
    {
        this.compito = compito;
    }

    public Date getData ()
    {
        return data;
    }

    public void setData (Date data)
    {
        this.data = data;
    }

    public SimpleStringProperty getOra ()
    {
        return ora;
    }

    public void setOra (SimpleStringProperty ora)
    {
        this.ora = ora;
    }

    public SimpleStringProperty getDescrizione ()
    {
        return descrizione;
    }

    public void setDescrizione (SimpleStringProperty descrizione)
    {
        this.descrizione = descrizione;
    }
    
    // </editor-fold>
    
    
    
}
