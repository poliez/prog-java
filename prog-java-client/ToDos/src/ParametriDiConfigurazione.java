import java.io.*;

public class ParametriDiConfigurazione implements Serializable
{

    String[] _incaricati;

    String[] _compiti;
    
    int _giorniPrecedenti;

    public ParametriDiConfigurazione(String[] incaricati, String[] compiti, int giorniPrecedenti)
    {
        _incaricati = incaricati;
        _compiti = compiti;
        _giorniPrecedenti = giorniPrecedenti;
    }
    
    public ParametriDiConfigurazione()
    {
        File xmlConf
             = new File("configurazione.xml");

        File xsdConf
             = new File("configurazione.xsd");

        ParametriDiConfigurazione params
            = (ParametriDiConfigurazione) todosutils.GestoreFileXML
                                    .loadObjectFromValidatedXML(
                                        xmlConf,
                                        xsdConf,
                                        ParametriDiConfigurazione.class
                                    );
        
        _incaricati = params._incaricati;
        _compiti = params._compiti;
        _giorniPrecedenti = params._giorniPrecedenti;
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
