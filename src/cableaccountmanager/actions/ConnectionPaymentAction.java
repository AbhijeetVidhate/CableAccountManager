/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cableaccountmanager.actions;

import cableaccountmanager.beans.AdminBeans;
import cableaccountmanager.beans.ConnectionPaymentBeans;
import cableaccountmanager.dba.DBConnection;
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
public class ConnectionPaymentAction {
    //db table content
        private  final String TBLUSERBILL_TABLE_NAME = "tbluserbill";
        private  final String TBLUSERBILL_COLUMN_ID = "id";
        private  final String TBLUSERBILL_COLUMN_PAIDAMOUNT = "paidamount";
        private  final String TBLUSERBILL_COLUMN_BILLAMOUNT = "billamount";
        private  final String TBLUSERBILL_COLUMN_BALANCEAMOUNT = "balanceamount";
        private  final String TBLUSERBILL_COLUMN_BALANCEDATE = "balancedate";
        private  final String TBLUSERBILL_COLUMN_BILLDATE = "billdate";
        private  final String TBLUSERBILL_COLUMN_CARD = "cardnumber";
        
    //dbfddcablenetwork component
        private int srNo=0;
        private DBConnection connection;  
        private Statement statement;
        private ResultSet resultSet;
        private PreparedStatement prepareStatement;
    //class content
      private static final String exceptionString = "Exception@ConnectionPaymentAction :-"  ;
      private static final String errorString = "Error@ConnectionPaymentAction :-"  ;
        
   
    //db table content
        private static final String TBLCONDETAILS_TABLE_NAME = "tblconnectiondetails";
        private static final String TBLCONDETAILS_COLUMN_ID = "id";
        private static final String TBLCONDETAILS_COLUMN_NAME = "username";
        private static final String TBLCONDETAILS_COLUMN_ADDRESS = "address";
        private static final String TBLCONDETAILS_COLUMN_CONTACT = "contactnumber";
        private static final String TBLCONDETAILS_COLUMN_CARD = "cardnumber";
        private static final String TBLCONDETAILS_COLUMN_AREA = "area";
    
    //getting only balanced bills
        public ObservableList<ConnectionPaymentBeans> getBalanceBills(ConnectionPaymentBeans connectionPaymentBeans,int noOfFillObjects){
            ObservableList<ConnectionPaymentBeans> list = FXCollections.observableArrayList();
            
            String query = "select "+TBLCONDETAILS_TABLE_NAME+"."+TBLCONDETAILS_COLUMN_NAME+","
                    +TBLCONDETAILS_TABLE_NAME+"."+TBLCONDETAILS_COLUMN_AREA+","
                    +TBLUSERBILL_TABLE_NAME+".* from "+TBLCONDETAILS_TABLE_NAME+" JOIN "
                    +TBLUSERBILL_TABLE_NAME+" on ("+TBLCONDETAILS_TABLE_NAME+"."
                    +TBLCONDETAILS_COLUMN_CARD+"="+TBLUSERBILL_TABLE_NAME+"."+TBLCONDETAILS_COLUMN_CARD+") where "
                    +TBLUSERBILL_TABLE_NAME+"."+TBLUSERBILL_COLUMN_BALANCEAMOUNT+">0 AND ";
            
            int noOfConditionAdded = 1; 
            if(connectionPaymentBeans.getCardNumber().isEmpty() != true){
                query = query+TBLCONDETAILS_TABLE_NAME+"."+TBLCONDETAILS_COLUMN_CARD+"='"+connectionPaymentBeans.getCardNumber()+"'";
                if(noOfConditionAdded < noOfFillObjects){
                    query = query +" AND ";
                    noOfConditionAdded++;
                }
            }
            if(connectionPaymentBeans.getArea().isEmpty() != true){
                query = query+TBLCONDETAILS_TABLE_NAME+"."+TBLCONDETAILS_COLUMN_AREA+"='"+connectionPaymentBeans.getArea()+"'";
                if(noOfConditionAdded < noOfFillObjects){
                    query = query +" AND ";
                    noOfConditionAdded++;
                }
            }
            //System.out.println(""+query);
            try {
                    connection = new DBConnection();
                    if(connection.getConnection() != null){
                    statement = connection.getConnection().createStatement();
                    if(statement != null){
                       resultSet = statement.executeQuery(query);
                       if(resultSet != null){
                           while (resultSet.next()) {
                               ConnectionPaymentBeans listItem = new ConnectionPaymentBeans();
                               listItem.setSr_no(""+(++srNo));
                               listItem.setId(resultSet.getInt(TBLUSERBILL_COLUMN_ID));
                               listItem.setName(resultSet.getString(TBLCONDETAILS_COLUMN_NAME));
                               listItem.setBillDate(resultSet.getDate(TBLUSERBILL_COLUMN_BILLDATE).toString());
                               listItem.setArea(resultSet.getString(TBLCONDETAILS_COLUMN_AREA));
                               listItem.setBalanceAmount(Integer.parseInt(resultSet.getString(TBLUSERBILL_COLUMN_BALANCEAMOUNT)));
                               listItem.setPaidAmount(Integer.parseInt(resultSet.getString(TBLUSERBILL_COLUMN_PAIDAMOUNT)));
                               listItem.setCardNumber(resultSet.getString(TBLUSERBILL_COLUMN_CARD));
                               
                               list.add(listItem);
                            }
                           
                       }else{
                           System.err.println("ResultSet is null");
                           
                       }
                   }else{
                       System.err.println("Statement is null");
                   }
               }else{
                   System.err.println("Connection is null");
               }
               
            } catch (SQLException | NumberFormatException e) {
            System.err.println(exceptionString+e.getMessage());
            //e.printStackTrace();
            }
            return list;
}
        
        
//this method return the all bill records using conditions
        public ObservableList<ConnectionPaymentBeans> getUserBillRecords(AdminBeans beans){
            
            
            
        return null;
        }
    
        
}
