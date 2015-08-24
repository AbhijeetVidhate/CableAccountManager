/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cableaccountmanager.user;

import cableaccountmanager.actions.PaymentBeansActions;
import cableaccountmanager.beans.ConnectionPaymentBeans;
import cableaccountmanager.beans.PaymentBeans;
import java.net.URL;
import java.sql.Date;
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

/**
 * FXML Controller class
 *
 * @author Saurabh
 */
public class PayBalanceController implements Initializable {

    /**
     * Initializes the controller class.
     */
    @FXML
        Label lblName,lblCard,lblLastBillDate,lblErrBalanceAmount,lblErrPaidAmount,lblBalanceAmount;
    
    @FXML
        Button btnPayBill,btnCancleBill;
    
    @FXML
        TextField txtBalanceAmount,txtPaidAmount;
    
    
    //class components
        ConnectionPaymentBeans connectionPaymentBeans;
        Stage dialogStage;
        boolean isDialogClose = false;
        
    //setting connectionpaymentbeans object
        public void setConnectionPaymentBeans(ConnectionPaymentBeans connectionPaymentBeans){
            this.connectionPaymentBeans = connectionPaymentBeans;
        }
        
    //setting dialog stage object
        public void setDialogStage(Stage dialogStage){
            this.dialogStage = dialogStage;
        }
        
    //setting all the component of diaolg
        public void init(){
            lblName.setText(connectionPaymentBeans.getName());
            lblCard.setText(connectionPaymentBeans.getCardNumber());
            lblLastBillDate.setText(connectionPaymentBeans.getBillDate());
            txtBalanceAmount.setText(""+connectionPaymentBeans.getBalanceAmount());
        }
        
    //getting dialog stage is close or not
        public boolean isDialogClose(){
            return this.isDialogClose;
        }
    
    //buttons controller method
    @FXML
        public void handleButtonAction(ActionEvent actionEvent){
            if(actionEvent.getSource().equals(btnPayBill)){
                if(connectionPaymentBeans == null)
                    this.dialogStage.close();
                else {
                    PaymentBeans paymentBeans = new PaymentBeans();
                        if(txtPaidAmount.getText().equals("") != true){
                            int paidAmount = Integer.parseInt(txtPaidAmount.getText());
                            int oldPaidAmount = connectionPaymentBeans.getPaidAmount();
                            paymentBeans.setPaidAmount(paidAmount+oldPaidAmount);
                            paymentBeans.setBalanceAmount(Integer.parseInt(lblBalanceAmount.getText()));
                            paymentBeans.setBalanceDate(getCurrentDate());
                            paymentBeans.setId(connectionPaymentBeans.getId());
                            if(new PaymentBeansActions().updateRecord(paymentBeans));{
                                isDialogClose = true;
                                dialogStage.close();
                            }
                        }       
                }
                              
                
            }else if(actionEvent.getSource().equals(btnCancleBill))
                    this.dialogStage.close();
            
        }
    //text field action
    @FXML
        public void keyPressActions(KeyEvent keyEvent){
            if(keyEvent.getSource().equals(txtPaidAmount)){
                try{
                    if(txtBalanceAmount.getText().equals("") != true && txtPaidAmount.getText().equals("") != true){
                        int balanceAmount = Integer.parseInt(txtBalanceAmount.getText());
                        int paidAmount = Integer.parseInt(txtPaidAmount.getText());

                        if(paidAmount<=balanceAmount){
                            lblBalanceAmount.setText(""+(balanceAmount-paidAmount));
                            lblErrPaidAmount.setText("");
                            btnPayBill.setDisable(false);
                        }else
                        {
                            lblErrPaidAmount.setText("*Must be less than or equal to bill amount");
                            btnPayBill.setDisable(true);
                        }
                    }else{
                        lblBalanceAmount.setText(""+0);
                    }
                }catch(Exception ex){
                    System.err.println("Exception:- "+ex.getMessage());
                }
            }
        }
    //getting current date
        public String getCurrentDate(){
            Calendar calendar = Calendar.getInstance();
            Date date=new Date(calendar.getTime().getTime());
            
            return date.toString();
        }
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        txtBalanceAmount.setDisable(true);
        btnPayBill.setDisable(true);
    }    
    
}
