import java.io.*;
import java.net.*;
import java.time.*;
import com.thoughtworks.xstream.*;

public class EventoDiNavigazioneGUI
{
    String nomeApplicazione; 
    String indirizzoIP; 
    java.util.Date data;
    String nomeEvento;
    
    public EventoDiNavigazioneGUI(String nomeApplicazione, String indirizzoIP, java.util.Date data, String nomeEvento)
    {
        this.nomeApplicazione = nomeApplicazione;
        this.indirizzoIP = indirizzoIP;
        this.data = data;
        this.nomeEvento = nomeEvento;
    }
    
    public EventoDiNavigazioneGUI(String evt) throws UnknownHostException
    {
        this(
            "ToDos",
            InetAddress
                .getLocalHost()
                .toString(),
            
            java.sql.Date.from(
                Instant.from(
                    LocalDate
                        .now()
                        .atStartOfDay(
                            ZoneId.systemDefault()
                        )
                )
            ), 
            evt
        );
    }
    
    public void invia()
    {
        try 
        (
            Socket s = new java.net.Socket("localhost", 8080);
            ObjectOutputStream oout = new ObjectOutputStream(s.getOutputStream());
        )
        {        
            String xml 
                = todosutils.GestoreFileXML
                    .toPrettyString(
                        new XStream().toXML(this), 
                        4
                    );
            
            oout.writeUTF(xml);
        }
        catch (IOException ex)
        {
            System.err.print(ex.getMessage());
        }
    }
}
