/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package todos;

import java.util.Date;
import javafx.beans.property.*;

/**
 *
 * @author Paolo
 */
public class ToDo
{

    int _id;
    SimpleStringProperty _incaricato;
    SimpleStringProperty _compito;
    Date _data;
    SimpleStringProperty _ora;
    SimpleStringProperty _descrizione;

    // <editor-fold desc="Getters e Setters">
    public int getId ()
    {
        return _id;
    }

    public SimpleStringProperty getIncaricato ()
    {
        return _incaricato;
    }

    public void setIncaricato (SimpleStringProperty incaricato)
    {
        this._incaricato = incaricato;
    }

    public SimpleStringProperty getCompito ()
    {
        return _compito;
    }

    public void setCompito (SimpleStringProperty compito)
    {
        this._compito = compito;
    }

    public Date getData ()
    {
        return _data;
    }

    public void setData (Date data)
    {
        this._data = data;
    }

    public SimpleStringProperty getOra ()
    {
        return _ora;
    }

    public void setOra (SimpleStringProperty ora)
    {
        this._ora = ora;
    }

    public SimpleStringProperty getDescrizione ()
    {
        return _descrizione;
    }

    public void setDescrizione (SimpleStringProperty descrizione)
    {
        this._descrizione = descrizione;
    }

    // </editor-fold>
    public String getDataFormattataPerDatabase ()
    {
        String[] time = _ora.getValue().split(":");
        _data.setHours(Integer.parseInt(time[0]));
        _data.setMinutes(Integer.parseInt(time[1]));

        java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        return sdf.format(_data);
    }

    public ToDo (int id,
                 String incaricato,
                 String compito,
                 Date data,
                 String ora,
                 String descrizione)
    {
        _id = id;
        _incaricato = new SimpleStringProperty(incaricato);
        _compito = new SimpleStringProperty(compito);
        _data = data;
        _ora = new SimpleStringProperty(ora);
        _descrizione = new SimpleStringProperty(descrizione);
    }

}
