/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cableaccountmanager.dba;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author Saurabh
 */
public class DBConnection 
{
    private final String DRIVER_CLASS = "com.mysql.jdbc.Driver";
    private final String URL = "jdbc:mysql://localhost:3306/";
    private final String DB_NAME = "dbfddcablenetwork";
    private final String USERNAME = "root";
    private final String PASSWORD = "root";
    
    private Connection connection;
    
    public DBConnection()throws ClassNotFoundException,SQLException{
        
            Class.forName(DRIVER_CLASS);
            connection = DriverManager.getConnection(URL+DB_NAME,USERNAME,PASSWORD);
            if(connection == null)
                System.err.println("DB Connection Unsuccessful");
            /*else
                System.out.println("DB Connection successful");*/
        
    }
    
    public void close() throws SQLException{
        this.connection.close();
    }
    public Connection getConnection(){
        return this.connection;
    }
    
    /*public static void main(String[] args) {
        new DBConnection();
    }*/
}
