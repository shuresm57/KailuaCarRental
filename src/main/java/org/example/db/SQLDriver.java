package org.example.db;

import java.sql.*;
import java.sql.DriverManager;
import java.sql.SQLException;


public class SQLDriver {

    /*En SQL connection skal være en singletonklasse
    *da vi skal være sikre på at der kun bliver
    *dannet forbindelse kun én gang.*/

    private static final String URL = "jdbc:mysql://localhost:3306/kailua_car_rental";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "root";

    private static Connection connection;

    private SQLDriver(){}

    //Singleton (Lazy Initialization)
    public static Connection connection(){
        if(connection == null){
            try{
                connection = DriverManager.getConnection(URL,USERNAME,PASSWORD);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        return connection;
    }

    public static void closeConnection(){
        try{
            connection.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
