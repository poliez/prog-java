/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utility;

import java.io.*;

/**
 *
 * @author Paolo
 */
public class Configurazione implements Serializable
{

    String[] _incaricati;

    String[] _compiti;

    public Configurazione(String[] incaricati, String[] compiti)
    {
        _incaricati = incaricati;
        _compiti = compiti;
    }
    
    public String[] getIncaricati ()
    {
        return _incaricati;
    }
    
    public String[] getCompiti ()
    {
        return _compiti;
    }
}
