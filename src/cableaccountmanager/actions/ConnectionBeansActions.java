/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cableaccountmanager.actions;

import cableaccountmanager.beans.ConnectionBeans;
import cableaccountmanager.beans.PaymentBeans;
import cableaccountmanager.dba.DBConnection;
import cableaccountmanager.user.ClientMainWindowController;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author Saurabh
 */
public class ConnectionBeansActions 
{
    //class content
      private static final String exceptionString = "Exception@ConnectionBeansActions :-"  ;
      private static final String errorString = "Error@ConnectionBeansActions :-"  ;
        
   
    //db table content
        private static final String TABLE_NAME = "tblconnectiondetails";
        private static final String COLUMN_ID = "id";
        private static final String COLUMN_NAME = "username";
        private static final String COLUMN_ADDRESS = "address";
        private static final String COLUMN_CONTACT = "contactnumber";
        private static final String COLUMN_CARD = "cardnumber";
        private static final String COLUMN_AREA = "area";
        
   
    //dbfddcablenetwork component
        private int srNo=0;
        private DBConnection connection;  
        private Statement statement;
        private ResultSet resultSet;
        private PreparedStatement prepareStatement;
    
    
    //this method add connections
    public boolean addConnections(ConnectionBeans connectionBeans){
        boolean isConnectionAdd = false;
        String query = "insert into "+TABLE_NAME
                            +" ("+COLUMN_NAME
                            +","+COLUMN_ADDRESS
                            +","+COLUMN_AREA
                            +","+COLUMN_CARD
                            +","+COLUMN_CONTACT+")values(?,?,?,?,?)";
        try {
                connection = new DBConnection();
                if(connection.getConnection() != null){
                    prepareStatement = connection.getConnection().prepareStatement(query);
                    if(prepareStatement != null){
                        prepareStatement.setString(1, connectionBeans.getName());
                        prepareStatement.setString(2, connectionBeans.getAddress());
                        prepareStatement.setString(3, connectionBeans.getArea());
                        prepareStatement.setString(4, connectionBeans.getCard());
                        prepareStatement.setString(5, connectionBeans.getContact());
                        isConnectionAdd = (prepareStatement.executeUpdate() > 0);
                    }
                    else
                        System.err.println(errorString+"prepare statement is null");
                }else
                    System.err.println(errorString+"Connection not establish");
                
        } catch (Exception e) {
            System.err.println(exceptionString+e.getMessage());
        }finally{
                try {
                    if(prepareStatement != null)
                        prepareStatement.close();
                } catch (Exception e) {
                    System.err.println(exceptionString+e.getMessage());
                }
                try{
                    if(connection !=null)
                        connection.close();
                }catch(Exception e){
                    System.err.println(exceptionString+e.getMessage());
                }    
        }
        return isConnectionAdd;
    }
        
        
        
    //this method getting new card number
    public int getCardNumber(String area){
        String query = "select count(*) from "+TABLE_NAME
                +" where "+COLUMN_AREA+"='"+area+"'";
        int count = 0;
        try {
                connection = new DBConnection();
                if(connection != null){
                    statement = connection.getConnection().createStatement();
                    if(statement != null){
                        resultSet = statement.executeQuery(query);
                        if(resultSet != null){
                            if (resultSet.next()) {
                                count = resultSet.getInt(1);
                            }
                        }else
                            System.err.println(errorString+"ResultSet is null");
                    }else{
                        System.err.println(errorString+"Statement is null");
                    }    
                }else{
                    System.err.println(errorString+"Connection not establish");
                }
        } catch (Exception e) {
            System.err.println(exceptionString+e.getMessage());
        }finally{
            try{
                if(resultSet != null)
                    resultSet.close();
            }catch(Exception e){
                System.err.println(exceptionString+e.getMessage());
            }
            try {
                if(statement != null)
                    statement.close();
            } catch (Exception e) {
                System.err.println(exceptionString+e.getMessage());
            }
            try {
                if(connection != null)
                    connection.close();
            } catch (Exception e) {
                System.err.println(exceptionString+e.getMessage());
            }
        }
        return ++count;
    }
    
    
    //this method return connection details by cardnumber and area
    public ObservableList<ConnectionBeans> 
        getConnectionDetails(ConnectionBeans cb,int noOfFillObjects){
        ObservableList<ConnectionBeans> connectionDetailsList = FXCollections.observableArrayList();
        String query = "select "+COLUMN_ID
                        +","+COLUMN_NAME
                        +","+COLUMN_ADDRESS
                        +","+COLUMN_AREA
                        +","+COLUMN_CONTACT
                        +","+COLUMN_CARD+" from "+TABLE_NAME+" ";
        boolean isWhereAdd = false;
        int noOfConditionAdded = 1; 
        if(cb.getCard().isEmpty() != true)
        {
            if(!isWhereAdd){
                query = query+" where ";
                isWhereAdd = true;
                
            }
            else if(noOfConditionAdded <= noOfFillObjects){
                query = query +" AND ";
                noOfConditionAdded++;
            }
            query = query+COLUMN_CARD+"='"+cb.getCard()+"'";
            
        }
        if(cb.getArea().isEmpty() != true){
            if(!isWhereAdd){
                query = query+" where ";
                //isWhereAdd = true;
            }
            else if(noOfConditionAdded <= noOfFillObjects){
                query = query +" AND ";
                noOfConditionAdded++;
            }
            query = query+COLUMN_AREA+"='"+cb.getArea()+"'";
            
        }
        try {
            connection = new DBConnection();
            
            if(connection.getConnection() != null){
                statement = connection.getConnection().createStatement();
                if(statement != null){
                    //System.out.println(""+query);
                    resultSet = statement.executeQuery(query);
                    if(resultSet != null){
                        while(resultSet.next()){
                            ConnectionBeans listBean = new ConnectionBeans();
                            listBean.setSr_no(""+(++srNo));
                            listBean.setName(resultSet.getString(COLUMN_NAME));
                            listBean.setId(resultSet.getInt(COLUMN_ID));
                            listBean.setCard(resultSet.getString(COLUMN_CARD));
                            listBean.setArea(resultSet.getString(COLUMN_AREA));
                            listBean.setAddress(resultSet.getString(COLUMN_ADDRESS));
                            listBean.setContact(resultSet.getString(COLUMN_CONTACT));
                            connectionDetailsList.add(listBean);
                        }
                        //System.out.println(""+connectionDetailsList.toString());
                    }else{
                        System.err.println(errorString+"ResultSet is null");
                    }
                }
                else{
                    System.err.println(errorString+"Statement is null");
                }
            }else{
                System.err.println(errorString+"Connection is not establish");
            }
        } catch (Exception e) {
            System.err.println(exceptionString +e.getMessage());
        }finally{
            try {
                    if(resultSet != null)
                        resultSet.close();
            } catch (SQLException e) {
                System.err.println(exceptionString +e.getMessage());
            }try {
                    if(statement != null)
                        statement.close();
            } catch (SQLException e) {
                System.err.println(exceptionString +e.getMessage());
            }try {
                    if(connection.getConnection() != null)
                        connection.close();
            } catch (SQLException e) {
                System.err.println(exceptionString +e.getMessage());
            }
        }
        return connectionDetailsList;
    }
        
        
    //this method update the connections by key and value
    public boolean updateConnection(int id,String key,String value){
        boolean isUpdate = false;
        String query = "update "+TABLE_NAME+" set"
                        +" "+key+"=? where "+COLUMN_ID+"=?";
        try {
            connection = new DBConnection();
            if(connection.getConnection() != null){
                prepareStatement = connection.getConnection().prepareStatement(query);
                if(prepareStatement != null){
                    prepareStatement.setString(1, value);
                    prepareStatement.setInt(2, id);
                    
                    isUpdate = (prepareStatement.executeUpdate()>0);
                }else{
                    System.err.println(errorString+"PrepareStatement is null");
                }
            }else{
                System.err.println(errorString+"Connection is not establish");
            }
        } catch (Exception e) {
            System.err.println(exceptionString +e.getMessage());
        }finally{
            try {
                    if(prepareStatement != null)
                        prepareStatement.close();
            } catch (SQLException e) {
                System.err.println(exceptionString +e.getMessage());
            }try {
                    if(connection.getConnection() != null)
                        connection.close();
            } catch (SQLException e) {
                System.err.println(exceptionString +e.getMessage());
            }
        }
        return isUpdate;
    }    
    
    //this method update area
    public String updateArea(int id,String newArea){
        String newCard = ClientMainWindowController.getCardNumber(newArea);
        String query = "update "+TABLE_NAME+" set"
                        +" "+COLUMN_AREA+"=? , "+COLUMN_CARD+"=? where "+COLUMN_ID+"=?";
        try {
            connection = new DBConnection();
            if(connection.getConnection() != null){
                prepareStatement = connection.getConnection().prepareStatement(query);
                if(prepareStatement != null){
                    prepareStatement.setString(1, newArea);
                    prepareStatement.setString(2, newCard);
                    prepareStatement.setInt(3, id);
                    boolean isUpdate = (prepareStatement.executeUpdate()>0);
                }else{
                    System.err.println(errorString+"PrepareStatement is null");
                }
            }else{
                System.err.println(errorString+"Connection is not establish");
            }
        } catch (Exception e) {
            System.err.println(exceptionString +e.getMessage());
        }finally{
            try {
                    if(prepareStatement != null)
                        prepareStatement.close();
            } catch (SQLException e) {
                System.err.println(exceptionString +e.getMessage());
            }try {
                    if(connection.getConnection() != null)
                        connection.close();
            } catch (SQLException e) {
                System.err.println(exceptionString +e.getMessage());
            }
        }
        return newCard;
    }
    
    //this method delete whole bill record of connection
    public boolean removeConnectionBills(ConnectionBeans connectionBeans){
        boolean isRemoved = false;
        PaymentBeans paymentBeans = new PaymentBeans();
        paymentBeans.setCardNumber(connectionBeans.getCard());
        
        if(new PaymentBeansActions().removeBillRecord(paymentBeans)){
            String query = "delete from "+TABLE_NAME+" where "+COLUMN_ID+"=?";
            try{
                connection = new DBConnection();
                if(connection.getConnection() != null){
                    prepareStatement = connection.getConnection().prepareStatement(query);
                    if(prepareStatement != null){
                        prepareStatement.setInt(1,connectionBeans.getId());
                        
                        isRemoved = (prepareStatement.executeUpdate()>0);
                    }else{
                        System.err.println(errorString+"PrepareStatement is null");
                    }
                }else{
                    System.err.println(errorString+"Connection is null");
                }
            }catch(SQLException se){
                System.err.println(exceptionString+se.getMessage());
            }finally{
            
            }
        }
           
        return isRemoved;
    }
    
    
}
