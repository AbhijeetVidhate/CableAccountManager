/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cableaccountmanager.user;

import cableaccountmanager.beans.ConnectionBeans;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Saurabh
 */
public class ConnectiondialogController implements Initializable {

    /**
     * Initializes the controller class.
     */
    //FXML component
        @FXML
            Label lblMessage,lblName,lblArea,lblCard;

        @FXML
            Button btnOk;
    
    //class components
        private ConnectionBeans connectionBeans;
        private String message;
        private boolean isOkClick = false;
        private Stage dialogStage;
        
    //FXML button actions
    public void handleButtonAction(ActionEvent actionEvent){
        if(actionEvent.getSource().equals(btnOk)){
            dialogStage.close();
            isOkClick = true;
        }
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        
        
    }
    
    public void setConnectionBeans(ConnectionBeans connectionBeans){
        this.connectionBeans = connectionBeans;
        lblName.setText(connectionBeans.getName());
        lblArea.setText(connectionBeans.getArea());
        lblCard.setText(connectionBeans.getCard());
    }
    public void setDialogStage(Stage dialogStage){
        this.dialogStage = dialogStage;
    }
    
    public void setMessage(String message){
        this.message = message;
        lblMessage.setText(this.message);
    }
   public boolean isOkClicked(){
       return this.isOkClick;
   }
}
