/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package todos;

import java.sql.*;
import java.text.*;
import java.util.logging.*;
import javafx.collections.*;

/**
 *
 * @author Paolo
 */
public class ToDoService
{

    private static Connection dbConnection;

    private static Statement dbStatement;

    public static boolean Init ()
    {
        try
        {
            dbConnection
            = DriverManager
                            .getConnection(
                                    "jdbc:mysql://localhost:3306/todos",
                                    "root",
                                    ""
                            );

            dbStatement = dbConnection.createStatement();

            return true;
        }
        catch (SQLException ex)
        {
            return false;
        }
    }

    public static ObservableList<ToDo> caricaToDos ()
    {
        ObservableList<ToDo> retList = FXCollections.observableArrayList();

        try
        {
            ResultSet rs = dbStatement
                    .executeQuery(
                            "SELECT * FROM todo T WHERE T.eliminato = 0"
                    );

            while (rs.next())
            {
                int id = rs.getInt("id");
                String incaricato = rs.getString("incaricato");
                String compito = rs.getString("compito");
                Date data = rs.getDate("data");
                String descrizione = rs.getString("descrizione");

                SimpleDateFormat sdf
                                 = new java.text.SimpleDateFormat("HH:mm");

                String ora = sdf.format(data);

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
        }
        catch (SQLException e)
        {
            System.err.println(e.getMessage());
        }

        return retList;
    }

    public static void eliminaToDo (int id)
    {
        try
        {
            
            dbStatement.executeUpdate(
                  "UPDATE todos"
                + "SET eliminato = 1"
                + "WHERE id = " + id
            );

        }
        catch (SQLException e)
        {
            System.err.println(e.getMessage());
        }

    }
    
    public static boolean modificaToDo (ToDo todo)
    {
        try
        {
            dbStatement.executeUpdate(
                "UPDATE todos "
              + "SET incaricato = '" + todo.getIncaricato() + "'"
              + ", compito = '" + todo.getCompito() + "'"
              + ", data = '" + todo.getDataFormattataPerDatabase() + "'"
              + ", descrizione = '" + todo.getDescrizione() + "' "
              + "WHERE id = '" + todo.getId() + "'"
            );
            
            return true;
        }
        catch (SQLException ex)
        {
            System.err.println(ex.getMessage());
            return false;
        }
    }
}
