/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cableaccountmanager.admin;

import cableaccountmanager.actions.UserBeansActions;
import cableaccountmanager.beans.UserBeans;
import cableaccountmanager.dba.CentralRepository;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Saurabh
 */
public class AddUserController implements  Initializable{

    /**
     * Initializes the controller class.
     */
    @FXML
        TextField txtUserName,txtContactNumber;
    
    @FXML
        PasswordField txtPassword,txtConfirmPassword;
    
    @FXML
        ComboBox<String> cbUserType;
    
    @FXML
        Button btnAddUser,btnClose;
    
    @FXML
        Text txtErrUserName,txtErrPassword,txtErrConfirmPassword,txtErrContactNumber,txtErrUserType;
    
    private boolean isWindowClose = false;
    @FXML
        private void ButtonActionHandler(ActionEvent actionEvent){
            if(actionEvent.getSource().equals(btnAddUser)){
                UserBeans userBeans = new UserBeans();
                    if(txtUserName.getText().isEmpty()){
                        txtErrUserName.setText("*Please enter the name");
                    }
                    if(txtPassword.getText().isEmpty()){
                        txtErrPassword.setText("*Please enter the password");
                    }
                    if(true != txtPassword.getText().equals(txtConfirmPassword.getText())){
                        txtErrConfirmPassword.setText("*Password and conform password doesn't match...!");
                    }
                    if(txtContactNumber.getText().isEmpty()){
                        txtErrContactNumber.setText("*Please enter the contact number");
                    }
                    if(null == cbUserType.getSelectionModel().getSelectedItem()){
                        txtErrUserType.setText("*Please select user type");
                    }
                    if(txtUserName.getText().isEmpty()!= true 
                            && txtPassword.getText().isEmpty() != true 
                            && txtPassword.getText().equals(txtConfirmPassword.getText())
                            && txtContactNumber.getText().isEmpty() != true
                            && null != cbUserType.getSelectionModel().getSelectedItem())
                    {
                    //setting values in userbeans object
                        userBeans.setName(txtUserName.getText());
                        userBeans.setPassword(txtPassword.getText());
                        userBeans.setContact(txtContactNumber.getText());
                        userBeans.setRoll(cbUserType.getSelectionModel().getSelectedItem());
                    //send userbeans to controller    
                        UserBeansActions controller = new UserBeansActions(actionEvent);
                        String result = controller.addUserAction(userBeans);
                        
                    //result shows.....in dialog box
                        if(showUserDialogBox(userBeans, result)){
                            isWindowClose = true;
                            Stage stage = (Stage)btnAddUser.getScene().getWindow();
                            stage.close();
                            
                        }
                        else{
                            //do nothing
                            isWindowClose = true;
                        }
                    }
            
            }else if(actionEvent.getSource().equals(btnClose)){
               isWindowClose = false;
               Stage stage = (Stage) btnClose.getScene().getWindow();
               stage.close();
            }         
    }
            
    
    
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        
        cbUserType.setItems(CentralRepository.USER_OPTIONS);
    }    
    
    public boolean isWindowColsed(){
        return this.isWindowClose;
    }
    public boolean showUserDialogBox(UserBeans userBeans,String result){
        try {
                // Load the fxml file and create a new stage for the popup
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("userdialog.fxml"));
                    AnchorPane page = (AnchorPane) loader.load();
                    Stage dialogStage = new Stage();
                    dialogStage.setTitle("Message");
                    dialogStage.initModality(Modality.WINDOW_MODAL);
                    //dialogStage.initOwner(primaryStage);
                    Scene scene = new Scene(page);
                    dialogStage.setScene(scene);

                // Set the person into the controller
                    UserdialogController controller = loader.getController();
                    controller.setDialogStage(dialogStage);
                    controller.setMessage(result);
                    controller.setUserBeans(userBeans);
            
                // Show the dialog and wait until the user closes it
            
                    dialogStage.showAndWait();
        
                    return controller.isOkClicked();

      } catch (IOException e) {
                // Exception gets thrown if the fxml file could not be loaded
                 System.err.println("Exception:- "+e.getMessage());
                return false;
      }
    
    }
}    
