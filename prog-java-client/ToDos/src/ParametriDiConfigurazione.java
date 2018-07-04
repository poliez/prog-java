/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package todos.config;

import java.io.*;

/**
 *
 * Modello dei dati salvati/letti su/da file binario di configurazione.
 * 
 * @author Paolo
 */
public class Configurazione implements Serializable
{

    String[] _incaricati;

    String[] _compiti;
    
    int _giorniPrecedenti;

    public Configurazione(String[] incaricati, String[] compiti, int giorniPrecedenti)
    {
        _incaricati = incaricati;
        _compiti = compiti;
        _giorniPrecedenti = giorniPrecedenti;
    }
    
    public String[] getIncaricati ()
    {
        return _incaricati;
    }
    
    public String[] getCompiti ()
    {
        return _compiti;
    }
    
    public int getGiorniPrecedenti ()
    {
        return _giorniPrecedenti;
    }
}
