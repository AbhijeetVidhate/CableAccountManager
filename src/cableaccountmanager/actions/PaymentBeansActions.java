/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cableaccountmanager.actions;

import cableaccountmanager.beans.ConnectionBeans;
import cableaccountmanager.beans.PaymentBeans;
import cableaccountmanager.dba.DBConnection;
import java.sql.Date;
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
public class PaymentBeansActions 
{
    //db table content
        private  final String TABLE_NAME = "tbluserbill";
        private  final String COLUMN_ID = "id";
        private  final String COLUMN_PAIDAMOUNT = "paidamount";
        private  final String COLUMN_BILLAMOUNT = "billamount";
        private  final String COLUMN_BALANCEAMOUNT = "balanceamount";
        private  final String COLUMN_BALANCEDATE = "balancedate";
        private  final String COLUMN_BILLDATE = "billdate";
        private  final String COLUMN_CARD = "cardnumber";
   
    //dbfddcablenetwork component
        private int srNo=0;
        private DBConnection connection;  
        private Statement statement;
        private ResultSet resultSet;
        private PreparedStatement prepareStatement;
        
    //PaymentBeansActions class components
        private PaymentBeans paymentBeans;
        private  final String exceptionString = "Exception@PaymentBeansActions :-"  ;
        private  final String errorString = "Error@PaymentBeansActions :-"  ;
        
        
    //PaymentBeansActions constructor
    public PaymentBeansActions(){
        
    }
    
   
    
    //PaymentBeansActions methods
    //this methode return boolean value for inserting record
    public boolean addBill(PaymentBeans paymentBeans){
            boolean isRecordAdd = false;
            String query = "insert into "+TABLE_NAME
                            +" ("+COLUMN_CARD
                            +","+COLUMN_BILLAMOUNT
                            +","+COLUMN_PAIDAMOUNT
                            +","+COLUMN_BALANCEAMOUNT
                            +","+COLUMN_BILLDATE+")values(?,?,?,?,?)";
                                    
            try{
                connection = new DBConnection();
                if(connection.getConnection() != null){
                    prepareStatement = connection.getConnection().prepareStatement(query);
                    if(prepareStatement != null){
                        prepareStatement.setString(1, paymentBeans.getCardNumber());
                        prepareStatement.setString(2, ""+paymentBeans.getBillAmount());
                        prepareStatement.setString(3, ""+paymentBeans.getPaidAmount());
                        prepareStatement.setString(4, ""+paymentBeans.getBalanceAmount());
                        prepareStatement.setDate(5, Date.valueOf(paymentBeans.getBillDate()));
                        isRecordAdd = (prepareStatement.executeUpdate()>0);
                    }else
                    {
                        System.err.println(errorString+"PrepareStatment is null");
                    }
                }else{
                    System.err.println(errorString+"Connection not establish");
                }   
            }catch(ClassNotFoundException | SQLException ex){
                System.err.println(exceptionString);
                //ex.printStackTrace();
            }finally{
                try{
                    if(prepareStatement != null)
                        prepareStatement.close();
                }catch(SQLException ex){
                    System.err.println(exceptionString+ex.getMessage());
                }
                try{
                    if(connection.getConnection() != null)
                        connection.close();
                }catch(SQLException ex){
                    System.err.println(exceptionString+ex.getMessage());
                }
            }
        return isRecordAdd;
    }
    
    //Updating balance amount and date  
    public boolean updateRecord(PaymentBeans paymentBeans){
        boolean isRecordUpdate = false;
        String query = "update  "+TABLE_NAME
                        +" set "+COLUMN_PAIDAMOUNT+"=?"
                        +" ,"+COLUMN_BALANCEAMOUNT+"=?"
                        +" ,"+COLUMN_BALANCEDATE+"=? where "+COLUMN_ID+"=?";

        try {
            connection = new DBConnection();
            if(connection.getConnection() != null){
                prepareStatement = connection.getConnection().prepareStatement(query);
                //System.out.println(""+prepareStatement.toString());
                if(prepareStatement != null){
                    prepareStatement.setInt(1, paymentBeans.getPaidAmount());
                    prepareStatement.setInt(2, paymentBeans.getBalanceAmount());
                    prepareStatement.setDate(3,Date.valueOf(paymentBeans.getBalanceDate()));
                    prepareStatement.setInt(4, paymentBeans.getId());
                    isRecordUpdate = (prepareStatement.executeUpdate()>0);
                }else{
                    System.err.println(errorString+"PrepareStatment is null");
                }
            }else{
                System.err.println(errorString+"Connection is not establish");
            }
        } catch (ClassNotFoundException | SQLException e) {
            System.err.println(exceptionString+e.getMessage());
        }finally{
            try{
                if(prepareStatement != null)
                    prepareStatement.close();
            }catch(Exception e){
                System.err.println(exceptionString+e.getMessage());
            }
            try {
                if(connection.getConnection() != null)
                    connection.close();
            } catch (Exception e) {
                System.err.println(exceptionString+e.getMessage());
            }
        }

        return isRecordUpdate;
    }  
    
    //this method returnning bills of single connection
    public ObservableList<PaymentBeans> getConnectionBillRecord(ConnectionBeans connectionBeans,String date1,String date2){
        String query = "select "+COLUMN_ID
                        +","+COLUMN_CARD
                        +","+COLUMN_BILLAMOUNT
                        +","+COLUMN_PAIDAMOUNT
                        +","+COLUMN_BALANCEAMOUNT
                        +","+COLUMN_BILLDATE
                        +","+COLUMN_BALANCEDATE+" from "+TABLE_NAME+" where "+COLUMN_CARD+"=?";
        
        ObservableList<PaymentBeans> list = FXCollections.observableArrayList();
        
        if(!date1.equals("null") && date2.equals("null"))
            query = query+" AND "+COLUMN_BILLDATE+"='"+Date.valueOf(date1)+"'";
        else if(!date1.equals("null") && !date2.equals("null"))
            query = query+" AND "+COLUMN_BILLDATE+" between '"+Date.valueOf(date1)+"' and '"+Date.valueOf(date2)+"'";   
        
        //System.out.println(query);
        
        
        try {
            connection = new DBConnection();
            if(connection.getConnection() != null){
                prepareStatement = connection.getConnection().prepareStatement(query);
                if(prepareStatement != null){
                    prepareStatement.setString(1, connectionBeans.getCard());
                    resultSet = prepareStatement.executeQuery();
                    if(resultSet != null){
                        while(resultSet.next()){
                            paymentBeans = new PaymentBeans();
                                paymentBeans.setSr_no(""+(++srNo));
                                paymentBeans.setId(resultSet.getInt(COLUMN_ID));
                                paymentBeans.setCardNumber(resultSet.getString(COLUMN_CARD));
                                paymentBeans.setBillAmount(resultSet.getInt(COLUMN_BILLAMOUNT));
                                paymentBeans.setPaidAmount(resultSet.getInt(COLUMN_PAIDAMOUNT));
                                paymentBeans.setBalanceAmount(resultSet.getInt(COLUMN_BALANCEAMOUNT));
                                paymentBeans.setBillDate(""+resultSet.getDate(COLUMN_BILLDATE));
                                paymentBeans.setBalanceDate(""+resultSet.getDate(COLUMN_BALANCEDATE));
                            list.add(paymentBeans);
                        }
                    }
                }else{
                    System.err.println(errorString+"PrepareStatement is null");
                }
            }else{
                System.err.println(errorString+"Connection not establish");
            }
        } catch (ClassNotFoundException | SQLException e) {
            System.err.println(exceptionString+e.getMessage());
            //e.printStackTrace();
        }finally{
            try{
                if(resultSet != null)
                    resultSet.close();
            }catch(Exception e){
                System.err.println(exceptionString+e.getMessage());
            }
            try{
                if(prepareStatement != null)
                    prepareStatement.close();
            }catch(Exception e){
                System.err.println(exceptionString+e.getMessage());
            }
            try {
                if(connection.getConnection() != null)
                    connection.close();
            } catch (Exception e) {
                System.err.println(exceptionString+e.getMessage());
            }
        }
        return list;
    }
    
    
    //this method update the card number
    public boolean updateCardNumber(String oldCardNumber,String newCardNumber){
        boolean isUpdate = false;
        String query = "update tbluserbill set "+COLUMN_CARD+"=? where "+COLUMN_CARD+"=?";
        
        try {
            connection = new DBConnection();
            if(connection.getConnection() != null){
                prepareStatement = connection.getConnection().prepareStatement(query);
                if(prepareStatement != null){
                    prepareStatement.setString(1, newCardNumber);
                    prepareStatement.setString(2, oldCardNumber);
                    //System.err.println(prepareStatement.toString());
                    
                        isUpdate= (prepareStatement.executeUpdate()>0);
                }else{
                    System.err.println(errorString+"PrepareStatement is null");
                }
            }else{
                System.err.println(errorString+"Connection is null");
            }
        } catch (ClassNotFoundException | SQLException e) {
            System.err.println(exceptionString+e.getMessage());
        }finally{
            try{
                if(prepareStatement != null)
                    prepareStatement.close();
            }catch(Exception e){
                System.err.println(exceptionString+e.getMessage());
            }
            try {
                if(connection.getConnection() != null)
                    connection.close();
            } catch (Exception e) {
                System.err.println(exceptionString+e.getMessage());
            }
        }
        return isUpdate;
    }
    
    //this method removes all bill record by cardnumber only calling when connection is removed
    public boolean removeBillRecord(PaymentBeans paymentBeans){
        boolean isRemove = false;
        String query = "delete from "+TABLE_NAME+" where "+COLUMN_CARD+"=?";
        
        try {
            connection = new DBConnection();
            if(connection.getConnection() != null){
                prepareStatement = connection.getConnection().prepareStatement(query);
                if(prepareStatement != null){
                    prepareStatement.setString(1, paymentBeans.getCardNumber());
                    isRemove = (prepareStatement.executeUpdate()>0);
                }else{
                    System.err.println(errorString+"PrepareStatement is null");
                }
            }else{
                System.err.println(errorString+"Connection is null");
            }
        } catch (ClassNotFoundException | SQLException e) {
            System.err.println(exceptionString+""+e.getMessage());
        }finally{
            try{
                prepareStatement.close();
            }catch(SQLException se){
                System.err.println(exceptionString+se.getMessage());
            }try{
                connection.close();
            }catch(SQLException se){
                System.out.println(exceptionString+se.getMessage());
            }
        }
        return  isRemove;
    }
    
}
