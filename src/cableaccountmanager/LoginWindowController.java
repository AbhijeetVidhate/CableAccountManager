/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cableaccountmanager;

import cableaccountmanager.actions.UserBeansActions;
import cableaccountmanager.beans.UserBeans;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 *
 * @author Saurabh
 */
public class LoginWindowController extends AnchorPane implements Initializable{
    @FXML
    private Text txtError;
    @FXML
    private TextField txtUserName;
    @FXML        
    private PasswordField txtPassword;
    @FXML
    private Button btnLogin;
    
    @FXML
    private void handleExitButton(ActionEvent ae)
    {
        //txtError.setText("Clicked exit button");
        System.exit(0);
    }
    
    @FXML
    private void handleLoginButton(ActionEvent ae) throws Exception
    {
        String nextWindow = "";      
        if(txtUserName.getText().isEmpty())
           txtError.setText("Please enter username");
        if(txtPassword.getText().isEmpty())
            txtError.setText("Please enter password");
        if(txtUserName.getText().isEmpty() && txtPassword.getText().isEmpty())
            txtError.setText("Please enter username and password");
        if(txtUserName.getText().isEmpty() != true && txtPassword.getText().isEmpty() != true){
            UserBeans userBeans = new UserBeans();
            userBeans.setName(txtUserName.getText());
            userBeans.setPassword(txtPassword.getText());
            try {
                UserBeans vaildatedUserBeans = new UserBeansActions().getUserDetails(userBeans);
                if(vaildatedUserBeans != null){
                    if(vaildatedUserBeans.getRoll().equalsIgnoreCase("admin"))
                        nextWindow = "admin/adminMainWindow.fxml";
                    else if(vaildatedUserBeans.getRoll().equalsIgnoreCase("user"))
                        nextWindow = "user/clientMainWindow.fxml";
                    
                    if(true != showNextWindow(nextWindow))
                                System.err.println("Error@LoginWindowController:- Enable to open next window");
                    else{
                        Stage stage = (Stage)btnLogin.getScene().getWindow();
                        stage.close();
                    }
                }else{
                    txtUserName.setText("");
                    txtPassword.setText("");
                    txtError.setText("Username and password incorrect....!");
                }
            } catch (Exception e) {
                System.err.println("Exception@LoginWiondowController:- "+e.getMessage());
            }
            
        }
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }
    
    private boolean showNextWindow(String name){
        boolean isOpen = false;
        FXMLLoader loader ;
        try {
                loader = new FXMLLoader(getClass().getResource(name));
                Parent parent = loader.load();
                Scene scene = new Scene(parent);
                Stage stage = new Stage();
                stage.setTitle("Shree Gajanan Prasana...!");
                stage.setScene(scene);
                stage.show();
                isOpen = true;
        
        } catch (Exception e) {
            System.err.println("Exception@LoginWiondowController:- "+e.getMessage());
        }
        return isOpen;
    }
}
