/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cableaccountmanager.admin;

import cableaccountmanager.actions.UserBeansActions;
import cableaccountmanager.beans.UserBeans;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Saurabh
 */
public class UserdialogController  {

    /**
     * Initializes the controller class.
     */
    @FXML
        Label lblName,lblPassword,lblMessage;
    
    @FXML
        Button btnOk,btnClose;
    
    private Stage dialogStage;
    private UserBeans userBeans;
    private boolean okClicked = false;
    private String message;
    
    
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        
    } 
    @FXML
    public void okButtonAction(ActionEvent actionEvent){
        if(this.message.equalsIgnoreCase("success") || this.message.equalsIgnoreCase("fail"))
        {
            dialogStage.close();
            okClicked = true;
        }
        else if(this.message.equalsIgnoreCase("remove"))
        {
            UserBeansActions actions = new UserBeansActions(actionEvent);
                if(this.userBeans != null){
                    String result = actions.removeUser(userBeans);
                    if(result.equals("success")){
                       dialogStage.close();
                       okClicked = true;
                    }   
                    else if(result.equals("fail"))
                    {
                        okClicked = false;
                    }
                }
        }
    }
    @FXML
    public void closeButtonAction(ActionEvent actionEvent){
      dialogStage.close();
      
    }
    
    public void setMessage(String message){
        this.message = message;
        
    }
    public void setDialogStage(Stage dialogStage){
        this.dialogStage = dialogStage;
    }
    
    public void setUserBeans(UserBeans userBeans){
        this.userBeans = userBeans;
        
        lblMessage.setText(message);
        lblName.setText(userBeans.getName());
        lblPassword.setText("*******");
    }
    public boolean isOkClicked(){
        return okClicked;
    }
}
