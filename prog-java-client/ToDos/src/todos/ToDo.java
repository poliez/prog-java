/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package todos;

import java.util.Date;

/**
 *
 * @author Paolo
 */
public class ToDo implements java.io.Serializable
{

    int _id = 0;
    String _incaricato;
    String _compito;
    Date _data;
    String _ora;
    String _descrizione;

    // <editor-fold desc="Getters e Setters">
    
    public int getId ()
    {
        return _id;
    }

    public String getIncaricato ()
    {
        return _incaricato;
    }

    public void setIncaricato (String incaricato)
    {
        this._incaricato = incaricato;
    }

    public String getCompito ()
    {
        return _compito;
    }

    public void setCompito (String compito)
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

    public String getOra ()
    {
        return _ora;
    }

    public void setOra (String ora)
    {
        this._ora = ora;
    }

    public String getDescrizione ()
    {
        return _descrizione;
    }

    public void setDescrizione (String descrizione)
    {
        this._descrizione = descrizione;
    }

    // </editor-fold>
    
    public String getDataFormattataPerDatabase ()
    {
        try
        {
            String[] time = _ora.split(":");
            _data.setHours(Integer.parseInt(time[0]));
            _data.setMinutes(Integer.parseInt(time[1]));
        }
        catch (NumberFormatException ex)
        {
            _data.setHours(0);
            _data.setMinutes(0);
        }
        
        java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        return sdf.format(_data);
    }

    ToDo ()
    {
        this(0, "", "", null, "", "");
    }
    
    public ToDo (int id,
                 String incaricato,
                 String compito,
                 Date data,
                 String ora,
                 String descrizione)
    {
        _id = id;
        _incaricato = incaricato;
        _compito = compito;
        _data = data;
        _ora = ora;
        _descrizione = descrizione;
    }

}
