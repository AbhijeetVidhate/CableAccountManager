/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cableaccountmanager.actions;

import cableaccountmanager.beans.UserBeans;
import cableaccountmanager.dba.DBConnection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.input.MouseEvent;

/**
 *
 * @author Saurabh
 */
public class UserBeansActions 
{
    //constructor component
        private ActionEvent actionEvent;
        private MouseEvent mouseEvent;
    //dbfddcablenetwork component
        private int sr_no=0;
        private DBConnection connection;  
        private Statement statement;
        private ResultSet resultSet;
        private PreparedStatement prepareStatement;
    
    //tbluserdetails component
        private final String TABLE_NAME = "tbluserdetails";
        private final String COLUMN_ID = "id";
        private final String COLUMN_NAME = "username";
        private final String COLUMN_PASSWORD = "password";
        private final String COLUMN_ROLL = "roll";
        private final String COLUMN_CONTACT = "contactnumber";
        
    public UserBeansActions(ActionEvent actionEvent){
        this.actionEvent = actionEvent;
    }
    
    public UserBeansActions(MouseEvent mouseEvent){
        this.mouseEvent = mouseEvent;
    }
    
    public UserBeansActions(){
        super();
    }
    
    public String addUserAction(UserBeans userBeans){
        String result = "fail";
        String query = "insert into "+TABLE_NAME+" ("+
                COLUMN_NAME+","+COLUMN_PASSWORD+","+COLUMN_CONTACT+","+COLUMN_ROLL+")values(?,?,?,?)";
        try{
            connection = new DBConnection();
            prepareStatement = connection.getConnection().prepareStatement(query);
            prepareStatement.setString(1, userBeans.getName());
            prepareStatement.setString(2, userBeans.getPassword());
            prepareStatement.setString(3, userBeans.getContact());
            prepareStatement.setString(4, userBeans.getRoll());
            
            if(prepareStatement.executeUpdate()>0)
               result = "success";
        }catch(Exception ex){
            System.err.println("Exception@UserBeansAction:- "+ex.getMessage());
        }finally{
            try{
                    if(prepareStatement != null)
                        prepareStatement.close();
            }catch(Exception e){
                System.err.println("Exception@UserBeansAction:- "+e.getMessage());
            }
            try{
                    if(connection != null)
                        connection.close();
            }catch(Exception e){
                    System.err.println("Exception@UserBeansAction:- "+e.getMessage());
            }
        }
        return result;
    }
    
    public ObservableList<UserBeans> getAllUsersDetails(){
        ObservableList<UserBeans> list = FXCollections.observableArrayList();
        
        String query = "select "+COLUMN_ID
                       +","+COLUMN_NAME
                       +","+COLUMN_PASSWORD
                       +","+COLUMN_ROLL
                       +","+COLUMN_CONTACT
                       +" from "+TABLE_NAME;
        try{
            connection = new DBConnection();
            statement = connection.getConnection().createStatement();
            resultSet = statement.executeQuery(query);
            if(resultSet != null){
                while(resultSet.next()){
                    UserBeans userBeans = new UserBeans();
                    userBeans.setId(resultSet.getInt(COLUMN_ID));
                    userBeans.setSr_no(""+ ++sr_no);
                    userBeans.setName(resultSet.getString(COLUMN_NAME));
                    userBeans.setPassword(resultSet.getString(COLUMN_PASSWORD));
                    userBeans.setContact(resultSet.getString(COLUMN_CONTACT));
                    userBeans.setRoll(resultSet.getString(COLUMN_ROLL));
                    list.add(userBeans);
                }
               
            }
            
        }catch(Exception ex){
            System.err.println("Exception@UserBeansAction :- "+ex.getMessage());
        }finally{
            try{
                    if(resultSet != null)
                        resultSet.close();
            }catch(Exception e){
                System.err.println("Exception@UserBeansAction:- "+e.getMessage());
            }
            try{
                    if(statement != null)
                        statement.close();
            }catch(Exception e){
                System.err.println("Exception@UserBeansAction:- "+e.getMessage());
            }
            try{
                    if(connection != null)
                        connection.close();
            }catch(Exception e){
                    System.err.println("Exception@UserBeansAction:- "+e.getMessage());
            }
                
        }
        
        return list;
    }
    
    public String removeUser(UserBeans userBeans){
        String result = "fail";
        String query = "delete from "+TABLE_NAME
                            +" where "+COLUMN_ID+"="+userBeans.getId();
        try {
                connection = new DBConnection();
                prepareStatement = connection.getConnection().prepareStatement(query);
                result = (prepareStatement.executeUpdate()>0)?"success":"fail";
        } catch (Exception e) {
            System.out.println("Exception@UserBeansAction:- "+e.getLocalizedMessage());
        }finally{
            try{
                    if(prepareStatement != null)
                        prepareStatement.close();
            }catch(Exception e){
                System.err.println("Exception@UserBeansAction:- "+e.getMessage());
            }
            try{
                    if(connection != null)
                        connection.close();
            }catch(Exception e){
                    System.err.println("Exception@UserBeansAction:- "+e.getMessage());
            }
        }
        return result;
    }
    
    public UserBeans getUserDetails(UserBeans userBeans){
        UserBeans ub = null;
        String query = "select "+COLUMN_ID+","
                            + COLUMN_NAME+","
                            + COLUMN_PASSWORD+","
                            + COLUMN_ROLL+","
                            + COLUMN_CONTACT
                            + " from "+TABLE_NAME+" where"
                            + " "+COLUMN_NAME+" = ? AND "+COLUMN_PASSWORD+" = ?";
        PreparedStatement preparedStatement = null;
        try {
            DBConnection dBConnection = new DBConnection();
                if(dBConnection.getConnection() != null){
                    preparedStatement = dBConnection.getConnection().prepareStatement(query);
                    preparedStatement.setString(1, userBeans.getName());
                    preparedStatement.setString(2, userBeans.getPassword());
                    //System.out.println(""+preparedStatement.toString());
                    ResultSet rs = preparedStatement.executeQuery();
                    if(rs != null){
                        if(rs.next()){
                            ub = new UserBeans();
                            ub.setId(rs.getInt(COLUMN_ID));
                            ub.setName(rs.getString(COLUMN_NAME));
                            ub.setPassword(rs.getString(COLUMN_PASSWORD));
                            ub.setContact(rs.getString(COLUMN_CONTACT));
                            ub.setRoll(rs.getString(COLUMN_ROLL));
                        }
                        else{
                            ub = null;
                        }
                    }
                    
                }
                else{
                    System.err.println("Connection not established....!");
                }
        } catch (Exception e) {
            System.err.println("Exception@UserBeansAction:- "+e.getMessage());
        }
        finally{
            try{
                    if(resultSet != null)
                        resultSet.close();
            }catch(Exception e){
                System.err.println("Exception@UserBeansAction:- "+e.getMessage());
            }
            try{
                    if(preparedStatement != null)
                        preparedStatement.close();
            }catch(Exception e){
                System.err.println("Exception@UserBeansAction:- "+e.getMessage());
            }
            try{
                    if(connection != null)
                        connection.close();
            }catch(Exception e){
                    System.err.println("Exception@UserBeansAction:- "+e.getMessage());
            }
        }
        return ub;
    }
}
