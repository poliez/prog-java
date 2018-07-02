/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package todos;

import java.util.Date;
/**
 * Classe da serializzare XML per inviare eventi da loggare al server.
 * @author Paolo
 */
public class Evento
{
    String nomeApplicazione; 
    String indirizzoIP; 
    Date data; 
    String nomeEvento;
    
    public Evento(String nomeApplicazione, String indirizzoIP, Date data, String nomeEvento)
    {
        this.nomeApplicazione = nomeApplicazione;
        this.indirizzoIP = indirizzoIP;
        this.data = data;
        this.nomeEvento = nomeEvento;
    }
}
