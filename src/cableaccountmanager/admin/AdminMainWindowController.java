/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cableaccountmanager.admin;

import cableaccountmanager.actions.ConnectionBeansActions;
import cableaccountmanager.actions.ConnectionPaymentAction;
import cableaccountmanager.actions.PaymentBeansActions;
import cableaccountmanager.actions.UserBeansActions;
import cableaccountmanager.beans.AdminBeans;
import cableaccountmanager.beans.ConnectionBeans;
import cableaccountmanager.beans.ConnectionPaymentBeans;
import cableaccountmanager.beans.PaymentBeans;
import cableaccountmanager.beans.UserBeans;
import cableaccountmanager.dba.CentralRepository;
import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ResourceBundle;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Saurabh
 */
public class AdminMainWindowController implements Initializable {

    /**
     * Initializes the controller class.
     */
    //common in all tabs
        @FXML
            Label lblMsgFieldTab1,lblMsgFieldTab2,lblMsgFieldTab3;
        
       
    
    //tab 1:-
        @FXML
            TableView<UserBeans> tblUserDetailsTab1;
        @FXML
            TableColumn<UserBeans,String> clmSrNoTab1,clmUserNameTab1,clmContactNoTab1,clmRollTab1;
        
        @FXML
            Button btnAddUserTab1,btnRemoveUserTab1;
        
        
        
        
    //tab2
        @FXML
            TextField txtCardTab2;
        
        @FXML
            ComboBox<String> cbAreaTab2,cbBillStatusTab2;
        
        @FXML
            DatePicker dpDate1Tab2,dpDate2Tab2;
        
        @FXML
            Button btnSearchTab2,btnResetTab2,btnRemoveTab2;
        
        @FXML
            TableView<ConnectionPaymentBeans> tblPaymentStatusTab2;
        @FXML
            TableColumn<ConnectionPaymentBeans,String> clmSrNoTab2,clmCardTab2,clmUserTab2,clmAreaTab2,
                            clmBillAmountTab2,clmPaidAmountTab2,clmDateTab2;
        
    //tab3
        @FXML
            TextField txtCardTab3;
        
        @FXML
            ComboBox<String> cbAreaTab3;
        
        @FXML
            Button btnSearchTab3,btnShowDetailsTab3,btnRemoveConnectionTab3,btnResetTab3;
        
        @FXML
            TableView<ConnectionBeans> tblConnectionsDetailsTab3;
        @FXML
            TableColumn<ConnectionBeans,String> clmSrNoTab3,clmCardNumberTab3,clmNameTab3,clmAddressTab3,clmAreaTab3;
        
    //button controller
        @FXML
        public void handleButtonActions(ActionEvent actionEvent){
            if(actionEvent.getSource().equals(btnAddUserTab1)){
                    try {
                                if(showAddUserWindow())                                    
                                    tblUserDetailsTab1.setItems(new UserBeansActions().getAllUsersDetails());
                                    
                    }catch(Exception ex){
                                System.err.println("Exception:- "+ex.getLocalizedMessage());
                    }
            }else if(actionEvent.getSource().equals(btnRemoveUserTab1)){
                UserBeans  userBeans= tblUserDetailsTab1.getSelectionModel().getSelectedItem();
                
                if(userBeans != null){
                    if(new AddUserController().showUserDialogBox(userBeans, "Remove")){
                        tblUserDetailsTab1.setItems(new UserBeansActions().getAllUsersDetails());
                    }
                }
                else{
                    lblMsgFieldTab1.setText("Please the user....!");
                }
            }else if(actionEvent.getSource().equals(btnSearchTab3)){
                ConnectionBeans connectionBeans = new ConnectionBeans();
                if(txtCardTab3.getText().equals("")!= true &&
                        cbAreaTab3.getSelectionModel().getSelectedItem() == null){
                                    connectionBeans.setCard(txtCardTab3.getText());
                                    connectionBeans.setArea("");
                                    tblConnectionsDetailsTab3.setItems(new ConnectionBeansActions().getConnectionDetails(connectionBeans, 1));
                }else if(txtCardTab3.getText().equals("") &&
                        cbAreaTab3.getSelectionModel().getSelectedItem()!= null){
                                    connectionBeans.setCard("");
                                    connectionBeans.setArea(cbAreaTab3.getSelectionModel().getSelectedItem());
                                    tblConnectionsDetailsTab3.setItems(new ConnectionBeansActions().getConnectionDetails(connectionBeans, 1));
                }else if(txtCardTab3.getText().equals("") != true &&
                        cbAreaTab3.getSelectionModel().getSelectedItem()!= null){
                                    connectionBeans.setCard(txtCardTab3.getText());
                                    connectionBeans.setArea(cbAreaTab3.getSelectionModel().getSelectedItem());
                                    tblConnectionsDetailsTab3.setItems(new ConnectionBeansActions().getConnectionDetails(connectionBeans, 2));
                }else if(txtCardTab3.getText().equals("") && 
                        cbAreaTab3.getSelectionModel().getSelectedItem() == null){
                    lblMsgFieldTab3.setText("Please enter card number or select the area...!");
                }
            }else if(actionEvent.getSource().equals(btnResetTab3)){
                resetFields(btnResetTab3);
            }else if(actionEvent.getSource().equals(btnShowDetailsTab3))
            {
                ConnectionBeans connectionBeans = tblConnectionsDetailsTab3.getSelectionModel().getSelectedItem();
                //int seletedIndex = tblConnectionsDetailsTab3.getSelectionModel().getSelectedIndex();
                if(connectionBeans != null){
                    if(showUserDetailWindow(connectionBeans)){
                        lblMsgFieldTab3.setText("Connection removed successfully....!");
                        ObservableList<ConnectionBeans> list = tblConnectionsDetailsTab3.getItems();
                        list.remove(connectionBeans);
                        tblConnectionsDetailsTab3.setItems(list);
                    }else{
                        lblMsgFieldTab3.setText("Connection remove failed....!");
                    }
                }else{
                    lblMsgFieldTab3.setText("Please select the connection....!");
                }
            }else if(actionEvent.getSource().equals(btnRemoveConnectionTab3)){
                ConnectionBeans connectionBeans = tblConnectionsDetailsTab3.getSelectionModel().getSelectedItem();
                
                if(connectionBeans != null){
                    if(new ConnectionBeansActions().removeConnectionBills(connectionBeans)){
                        lblMsgFieldTab3.setText("Connection removed successfully....!");
                        ObservableList<ConnectionBeans> list = tblConnectionsDetailsTab3.getItems();
                        list.remove(connectionBeans);
                        tblConnectionsDetailsTab3.setItems(list);
                    }else{
                        lblMsgFieldTab3.setText("Connection remove failed....!");
                    }
                }else{
                    lblMsgFieldTab3.setText("Please select the connection....!");
                }
            }else if(actionEvent.getSource().equals(btnSearchTab2)){
                
                
                AdminBeans adminBeans = new AdminBeans();
                
                    if(!txtCardTab2.getText().equals("null"))
                        adminBeans.setCardNumber(txtCardTab2.getText());
                    else
                        adminBeans.setCardNumber("");
                              
                    if(cbAreaTab2.getSelectionModel().getSelectedItem() != null){
                        adminBeans.setArea(cbAreaTab2.getSelectionModel().getSelectedItem());
                        
                    }
                    else
                        adminBeans.setArea("");
                    
                    if(cbBillStatusTab2.getSelectionModel().getSelectedItem() != null){
                        adminBeans.setBillStatus(cbBillStatusTab2.getSelectionModel().getSelectedItem());
                        
                    }
                    else
                        adminBeans.setBillStatus("");
                
                try{
                    adminBeans.setDate1(dpDate1Tab2.getValue().toString());
                    
                }catch(NullPointerException nullEx){
                    adminBeans.setDate1("");
                }
                try{
                    adminBeans.setDate2(dpDate2Tab2.getValue().toString());
                    
                }catch(NullPointerException nullEx){
                    adminBeans.setDate2("");
                }
                
                
                tblPaymentStatusTab2.setItems(new ConnectionPaymentAction().getUserBillRecords(adminBeans));
            }else if(actionEvent.getSource().equals(btnResetTab2)){
                resetFields(btnResetTab2);
            }else if(actionEvent.getSource().equals(btnRemoveTab2)){
                ConnectionPaymentBeans connectionPaymentBeans = tblPaymentStatusTab2.getSelectionModel().getSelectedItem();
                
                if(new PaymentBeansActions().removeBillRecord(connectionPaymentBeans)){
                    ObservableList<ConnectionPaymentBeans> list = tblPaymentStatusTab2.getItems();
                    list.remove(connectionPaymentBeans);
                    tblPaymentStatusTab2.setItems(list);
                    lblMsgFieldTab2.setText("Bill record deleted successfully...!");
                }else
                    lblMsgFieldTab2.setText("Bill record not deleted...!");
            }
        }
        
    //mouse event controller
    @FXML
        public void handleMouseEvent(MouseEvent mouseEvent){
            
        }
        
        
    //reset all fields in window
    private void resetFields(Button btnReset){
        if(btnReset.equals(btnResetTab2)){
            //reseting tab2 fields
            txtCardTab2.setText("");
            cbAreaTab2.getSelectionModel().clearSelection();
            cbBillStatusTab2.getSelectionModel().clearSelection();
            dpDate1Tab2.setValue(null);
            dpDate2Tab2.setValue(null);
            lblMsgFieldTab2.setText("");
        }else if(btnReset.equals(btnResetTab3)){
            //reseting tab3 fields
            txtCardTab3.setText("");
            cbAreaTab3.getSelectionModel().clearSelection();
            lblMsgFieldTab3.setText("");
            tblConnectionsDetailsTab3.getItems().clear();
        }
        
    }
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        //setting table tblUserDetailsTab1 columns
            clmSrNoTab1.setCellValueFactory(celldata -> celldata.getValue().getSr_noProperty());
            clmUserNameTab1.setCellValueFactory(celldata -> celldata.getValue().getNameProperty());
            clmContactNoTab1.setCellValueFactory(celldata -> celldata.getValue().getContactProperty());
            clmRollTab1.setCellValueFactory(celldata -> celldata.getValue().getRollProperty());
            
        //setting table tblConnectionsDetailsTab3 columns
            clmSrNoTab3.setCellValueFactory(celldata -> celldata.getValue().getSr_noProperty());
            clmCardNumberTab3.setCellValueFactory(celldata -> celldata.getValue().getCardProperty());
            clmNameTab3.setCellValueFactory(celldata -> celldata.getValue().getNameProperty());
            clmAddressTab3.setCellValueFactory(celldata -> celldata.getValue().getAddressProperty());
            clmAreaTab3.setCellValueFactory(celldata -> celldata.getValue().getAreaProperty());
        
        //setting table columns
            clmSrNoTab2.setCellValueFactory(celldata -> celldata.getValue().getSr_noProperty());
            clmCardTab2.setCellValueFactory(celldata -> celldata.getValue().getCardNumberProperty());
            clmUserTab2.setCellValueFactory(celldata -> celldata.getValue().getNameProperty());
            clmAreaTab2.setCellValueFactory(celldata -> celldata.getValue().getAreaProperty());
            clmBillAmountTab2.setCellValueFactory(celldata -> celldata.getValue().getBillAmountProperty());
            clmPaidAmountTab2.setCellValueFactory(celldata -> celldata.getValue().getPaidAmountProperty());
            clmDateTab2.setCellValueFactory(celldata -> celldata.getValue().getBillDateProperty());
                    
                    
                    
        tblUserDetailsTab1.setItems(new UserBeansActions().getAllUsersDetails());
        cbAreaTab3.setItems(CentralRepository.AREA_OPTIONS);
        cbAreaTab2.setItems(CentralRepository.AREA_OPTIONS);
        cbBillStatusTab2.setItems(CentralRepository.PAYMENT_OPTIONS);
    }    
    
    
    //show adduser.fxml
    
    private boolean showAddUserWindow(){
        try {
                // Load the fxml file and create a new stage for the popup
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("adduser.fxml"));
                    AnchorPane page = (AnchorPane) loader.load();
                    Stage dialogStage = new Stage();
                    dialogStage.setTitle("Add new user");
                    dialogStage.initModality(Modality.WINDOW_MODAL);
                //dialogStage.initOwner(primaryStage);
                    Scene scene = new Scene(page);
                    dialogStage.setScene(scene);

                // Set the AddUser into the controller
                    AddUserController addUserController = loader.getController();
            
                // Show the dialog and wait until the user closes it
                    dialogStage.showAndWait();
                //return okbutton status
                    return addUserController.isWindowColsed();

      } catch (IOException e) {
                // Exception gets thrown if the fxml file could not be loaded
                 System.err.println("Exception:- "+e.getMessage());
                return false;
      }
    
    
    }
    private boolean showUserDetailWindow(ConnectionBeans connectionBeans){
        try{
                // Load the fxml file and create a new stage for the popup
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("connectiondetails.fxml"));
                    AnchorPane page = (AnchorPane) loader.load();
                    Stage dialogStage = new Stage();
                    dialogStage.setTitle("Connection Details");
                    dialogStage.initModality(Modality.WINDOW_MODAL);
                //dialogStage.initOwner(primaryStage);
                    Scene scene = new Scene(page);
                    dialogStage.setScene(scene);

                // Set the connectiondetails into the controller
                    ConnectionDetailsController connectionDetailsController = loader.getController();
                    connectionDetailsController.setConnectionBeans(connectionBeans);
                    connectionDetailsController.setDialogStage(dialogStage);
                    connectionDetailsController.init();
                    
                // Show the dialog and wait until the user closes it
                    dialogStage.showAndWait();
                    
                //return okbutton status
                    return connectionDetailsController.isClose();
            
        }catch(Exception ex){
            System.err.println(ex.getMessage());
            return false;
        }
    }
}
