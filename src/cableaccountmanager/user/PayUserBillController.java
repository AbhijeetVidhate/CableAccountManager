/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cableaccountmanager.user;

import cableaccountmanager.actions.PaymentBeansActions;
import cableaccountmanager.beans.ConnectionBeans;
import cableaccountmanager.beans.PaymentBeans;
import java.net.URL;
import java.util.Calendar;


import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import java.sql.Date;
/**
 * FXML Controller class
 *
 * @author Saurabh
 */
public class PayUserBillController implements Initializable {

    /**
     * Initializes the controller class.
     */
    
    
    @FXML
    Label lblName,lblCard,lblDate,lblBalanceAmount,lblErrPaidAmount,lblErrBillAmount;
    
    @FXML
    TextField txtBillAmount,txtPaidAmount;
    
    @FXML
    Button btnPayBill,btnCancleBill;
    
    //class content
        private PaymentBeans paymentBeans;
        private ConnectionBeans connectionBeans;
        private Stage dialogStage;
        private boolean isClose = false;
        private Date date;
        
    //setting connection beans object
    public void setConnectionBeans(ConnectionBeans connectionBeans){
        this.connectionBeans = connectionBeans;
    }
    
    //setting stage
    public void setStage(Stage dialogStage){
        this.dialogStage = dialogStage;
    }
    
    //setting all labels in window
    public void init(){
        lblName.setText(connectionBeans.getName());
        lblCard.setText(connectionBeans.getCard());
    }
    
    //returning while closing window
    public boolean isClose(){
        return this.isClose;
    }
    //button actions
    public void handleButtonAction(ActionEvent actionEvent){
        if(actionEvent.getSource().equals(btnPayBill)){
            if(connectionBeans == null){
                dialogStage.close();
            }else if(connectionBeans != null && Integer.parseInt(lblBalanceAmount.getText()) >= 0){
                paymentBeans = new PaymentBeans();
                paymentBeans.setCardNumber(connectionBeans.getCard());
                paymentBeans.setBillAmount(Integer.parseInt(txtBillAmount.getText()));
                paymentBeans.setPaidAmount(Integer.parseInt(txtPaidAmount.getText()));
                paymentBeans.setBalanceAmount(Integer.parseInt(lblBalanceAmount.getText()));
                paymentBeans.setBillDate(lblDate.getText());
                paymentBeans.setBalanceDate("");
                boolean isAdd = new PaymentBeansActions().addBill(paymentBeans);
                if(isAdd){
                    isClose = true;
                    dialogStage.close();
                }else{
                    System.err.println("Payment not successfull.....!");
                }
                
            }
        
        }else if(actionEvent.getSource().equals(btnCancleBill)){
            isClose = false;
            dialogStage.close();
        }
    }
    //calaculating balance
    @FXML    
    public void handleKeyPress(KeyEvent keyEvent){
        if(keyEvent.getSource().equals(txtPaidAmount)){
            try{
                    if(txtBillAmount.getText().equals("") != true && txtPaidAmount.getText().equals("") != true)
                    {
                        int billAmount = Integer.parseInt(txtBillAmount.getText());
                        int paidAmount = Integer.parseInt(txtPaidAmount.getText());
                        if(billAmount >= paidAmount){
                            lblBalanceAmount.setText(""+(billAmount-paidAmount));
                            lblErrBillAmount.setText("");
                            btnPayBill.setDisable(false);
                        }    
                        else{
                            lblErrPaidAmount.setText("*Must be less than or equal to bill amount");
                            btnPayBill.setDisable(true);
                        }
                    }else{
                        lblBalanceAmount.setText(""+0);
                    }
            }catch(Exception ex){
                System.err.println(""+ex.getMessage());
            }
        }
    }
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO        
        
        Calendar calendar = Calendar.getInstance();
        date=new Date(calendar.getTime().getTime());
        lblDate.setText(date.toString());
        btnPayBill.setDisable(true);
    }    
    
}
