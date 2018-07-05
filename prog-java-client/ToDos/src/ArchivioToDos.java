import java.sql.*;
import java.text.*;
import java.util.*;
import javafx.collections.*;


public class ArchivioToDos
{

    private static Connection dbConnection() throws SQLException
    {
        return DriverManager
                .getConnection(
                        "jdbc:mysql://localhost:3306/todos",
                        "root",
                        "root"
                );
    };

    private static Statement dbStatement() throws SQLException
    {
        return dbConnection().createStatement();
    }

    // <editor-fold desc="CRUD">
    
    public static ObservableList<ToDo> caricaToDos ()
    {
        try
        {
            ResultSet rs = dbStatement()
                    .executeQuery(
                            "SELECT * FROM todo WHERE eliminato = 0 AND data >= DATE(NOW())"
                    );

            return prelevaToDo(rs);
        }
        catch (SQLException e)
        {
            System.err.println(e.getMessage());
        }
        catch (Exception ex)
        {
            System.err.println(ex.getMessage());
        }
        
        return null;
    }
    
    public static ObservableList<ToDo> caricaToDos (String incaricato, String compito, java.util.Date data)
    {
        try
        {
            String baseQuery = "SELECT * FROM todo WHERE eliminato = 0";
            
            // E' necessario usare questo format, in quanto
            // _data.getYear(), con data = '2018-01-01', ritorna 118
            SimpleDateFormat year = new SimpleDateFormat("yyyy");
            
            SimpleDateFormat month = new SimpleDateFormat("MM");
            
            SimpleDateFormat day = new SimpleDateFormat("d");
            
            String condizioneData
                = (data != null) 
                    ? "AND YEAR(data) = '" + year.format(data)
                      +"' AND MONTH(data) = '" + month.format(data).replace("0", "")
                      +"' AND DAY(data) = '" + day.format(data) 
                      +"' " 
                    : "";
            
            String condizioneIncaricato 
                = (incaricato != "") ? "AND incaricato = '" + incaricato +"' " : "";
            
            String condizioneCompito
                = (compito != "") ? "AND compito = '" + compito +"' " : "";
            
            ResultSet rs = dbStatement()
                    .executeQuery(
                        String.join(
                            " "
                            , baseQuery
                            , condizioneData
                            , condizioneIncaricato
                            , condizioneCompito
                        )
                    );
            
            return prelevaToDo(rs);
        }
        catch (SQLException e)
        {
            System.err.println(e.getMessage());
        }
        catch (Exception ex)
        {
            System.err.println(ex.getMessage());
        }

        return null;
    }
    
    public static boolean eliminaToDo (int id)
    {
        try
        {
            dbStatement().executeUpdate(
                  "UPDATE todo "
                + "SET eliminato = 1 "
                + "WHERE id = " + id
            );
            
            return true;
        }
        catch (SQLException e)
        {
            System.err.println(e.getMessage());
        }
        
        return false;

    }
    
    public static boolean modificaToDo (ToDo todo)
    {
        try
        {   
            PreparedStatement update = dbConnection()
                .prepareStatement(
                      "UPDATE todos "
                    + "SET incaricato = ?"
                    + ", compito = ?"
                    + ", data = ?"
                    + ", descrizione = ? "
                    + "WHERE id = ?"
                );
            
            update.setString(1, todo.getIncaricato());
            update.setString(2, todo.getCompito());
            update.setString(3, todo.getDataFormattataPerDatabase());
            update.setString(4, todo.getDescrizione());
            update.setInt(5, todo.getId());
            
            update.executeUpdate();
            
            return true;
        }
        catch (SQLException ex)
        {
            System.err.println(ex.getMessage());
            return false;
        }
    }
    
    public static boolean inserisciToDo (ToDo todo)
    {
        try
        {

            PreparedStatement insert = dbConnection()
                .prepareStatement(
                    "INSERT INTO todo (incaricato, compito, data, descrizione) "
                    + "VALUES (?,?,?,?)"
                );
            
            insert.setString(1, todo.getIncaricato());
            insert.setString(2, todo.getCompito());
            insert.setString(3, todo.getDataFormattataPerDatabase());
            insert.setString(4, todo.getDescrizione());

            
            insert.executeUpdate();
            
            return true;
        }
        catch (SQLException ex)
        {
            System.err.println(ex.getMessage());
            return false;
        }
    }
            
    // </editor-fold>
    
    /**
     * @param giorniPrecedenti Numero di giorni da considerare precedenti al presente 
     * @return Numero di todo nei giorniPrecedenti per ogni incaricato
     */
    public static Map<String, Integer> prelevaStatistiche (int giorniPrecedenti)
    {
        try
        {
            Map<String, Integer> retMap = new HashMap<>();
            
            ResultSet rs = dbStatement()
                    .executeQuery(
                            "SELECT incaricato, count(*) as conto "
                            + "FROM todo "
                            + "WHERE eliminato = 0 "
                            + "AND data BETWEEN (NOW() - INTERVAL " 
                            + giorniPrecedenti 
                            + " DAY) AND NOW() "
                            + "GROUP BY incaricato"
                    );
            
            while(rs.next())
            {
                String incaricato = rs.getString("incaricato");
                int conto = rs.getInt("conto");
                
                retMap.put(incaricato, conto);
            }
            
            return retMap;
        }
        catch (SQLException e)
        {
            System.err.println(e.getMessage());
        }
        catch (Exception ex)
        {
            System.err.println(ex.getMessage());
        }

        return null;
    }
    
    public static ToDo prelevaUltimoInserito()
    {
        
        try
        {
            ResultSet rs = dbStatement()
                    .executeQuery(
                            "SELECT * FROM todo ORDER BY id DESC LIMIT 1"
                    );

            if(!rs.next())
                return null;
            
            int id = rs.getInt("id");
            String incaricato = rs.getString("incaricato");
            String compito = rs.getString("compito");
            java.sql.Date data = rs.getDate("data");
            String descrizione = rs.getString("descrizione");
            String ora = rs.getTime("data").toLocalTime().toString();

            ToDo retVal = new ToDo(
                    id,
                    incaricato,
                    compito,
                    data,
                    ora,
                    descrizione
            );

            return retVal;
        }
        catch (SQLException e)
        {
            System.err.println(e.getMessage());
        }
        catch (Exception ex)
        {
            System.err.println(ex.getMessage());
        }

        return null;
    }
    
    private static ObservableList<ToDo> prelevaToDo (ResultSet rs) throws SQLException
    {
        ObservableList<ToDo> retList = FXCollections.observableArrayList();

        while (rs.next())
        {
            int id = rs.getInt("id");
            String incaricato = rs.getString("incaricato");
            String compito = rs.getString("compito");
            java.sql.Date data = rs.getDate("data");
            String descrizione = rs.getString("descrizione");

            String ora = rs.getTime("data").toLocalTime().toString();

            ToDo toAdd = new ToDo(
                    id,
                    incaricato,
                    compito,
                    data,
                    ora,
                    descrizione
            );

            retList.add(toAdd);
        }

        return retList;
    }
}
