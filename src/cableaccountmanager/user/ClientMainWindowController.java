/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cableaccountmanager.user;

import cableaccountmanager.actions.ConnectionBeansActions;
import cableaccountmanager.actions.ConnectionPaymentAction;
import cableaccountmanager.beans.ConnectionBeans;
import cableaccountmanager.beans.ConnectionPaymentBeans;
import cableaccountmanager.dba.CentralRepository;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 *
 * @author Saurabh
 */
public class ClientMainWindowController implements Initializable {
    
    @FXML
        TableView<ConnectionBeans> tblConnectionsDetailsTab2;
    
    @FXML
        TableView<ConnectionPaymentBeans> tblConnectionsDetailsTab3;
    
    @FXML
        TableColumn<ConnectionBeans,String> clmSrNoTab2,clmCardTab2,clmNameTab2,clmAreaTab2,clmContactTab2;
    
    @FXML
        TableColumn<ConnectionPaymentBeans,String> clmSrNoTab3,clmCardTab3,clmNameTab3,clmAreaTab3,clmBalanceAmountTab3,clmDateTab3;
    
    @FXML
        TextArea txtAddressTab1;
    
    @FXML
        TextField txtNameTab1,txtContactTab1,txtCardTab1,txtCardTab2,txtCardTab3;
    
    @FXML
        ComboBox<String> cbAreaTab1,cbAreaTab2,cbAreaTab3;
    
    @FXML
        Button btnAddTab1,btnResetTab1,btnPayBillTab2,btnResetTab2,btnPayBalBillTab3,btnSearchTab2,btnSearchTab3,btnResetTab3;
    
    @FXML
        Label lblErrNameTab1,lblErrAreaTab1,lblErrAddressTab1,lblErrContactTab1,lblMsgTab2,lblMsgTab3;
    
    //class component
        private ConnectionBeans connectionBeans;
        private ConnectionPaymentBeans connectionPaymentBeans;
    
    @FXML
        public void handleButtonAction(ActionEvent actionEvent){
            //button add user tab1 action
            if(actionEvent.getSource().equals(btnAddTab1)){
                
            //check username field and shows errors
               if(txtNameTab1.getText().isEmpty())
                   lblErrNameTab1.setText("Please enter the name");
               else
                   lblErrNameTab1.setText("");
               
            //check area field and shows errors
               if(cbAreaTab1.getSelectionModel().getSelectedItem() == null)
                   lblErrAreaTab1.setText("Please select the area");
               else
                   lblErrAreaTab1.setText("");
               
            //check contact number field and shows errors
               if(txtContactTab1.getText().isEmpty())
                   lblErrContactTab1.setText("Please enter the contact number");
               else
                   lblErrContactTab1.setText("");
               
            //check address field and shows errors   
               if(txtAddressTab1.getText().isEmpty())
                   lblErrAddressTab1.setText("Please enter the address");
               else
                   lblErrAddressTab1.setText("");
               
            //checking all fields are not empty and add connection into db
               if(txtNameTab1.getText().isEmpty() != true 
                       && cbAreaTab1.getSelectionModel().getSelectedItem() != null
                       && txtContactTab1.getText().isEmpty() != true
                       && txtAddressTab1.getText().isEmpty() != true){
                    //setting connection details into beans
                        connectionBeans = new ConnectionBeans();
                        connectionBeans.setName(txtNameTab1.getText());
                        connectionBeans.setAddress(txtAddressTab1.getText());
                        connectionBeans.setArea(cbAreaTab1.getSelectionModel().getSelectedItem());
                        connectionBeans.setCard(txtCardTab1.getText());
                        connectionBeans.setContact(txtContactTab1.getText());
                    //adding connection in db and returning result in true = successfully added, false = failed
                        if(new ConnectionBeansActions().addConnections(connectionBeans)){
                            if(showConnectionDialog("Connection added successfully...!"))
                                resetAllFields((Button)actionEvent.getSource());
                        }else{
                            if(showConnectionDialog("Connection failed to added...!"))
                                resetAllFields((Button)actionEvent.getSource());
                        }
               
               }
            }//end if
            //select area Tab1 action
            else if(actionEvent.getSource().equals(cbAreaTab1)){
                String selectedArea = cbAreaTab1.getSelectionModel().getSelectedItem();
                if(selectedArea != null){
                    String cardNumber = getCardNumber(selectedArea);
                    if(cardNumber.isEmpty() != true)
                        txtCardTab1.setText(cardNumber);
                }          
            }//end else if
            //button reset tab1 action
            else if(actionEvent.getSource().equals(btnResetTab1)){
                resetAllFields((Button)actionEvent.getSource());
            }//end if
            //button serach tab2 action
            else if(actionEvent.getSource().equals(btnSearchTab2)){
                connectionBeans = new ConnectionBeans();
                if(txtCardTab2.getText().isEmpty() != true 
                        && cbAreaTab2.getSelectionModel().getSelectedItem() == null){
                    connectionBeans.setCard(txtCardTab2.getText());
                    connectionBeans.setArea("");
                    tblConnectionsDetailsTab2.setItems(new ConnectionBeansActions().getConnectionDetails(connectionBeans,1));
                }
                else if(cbAreaTab2.getSelectionModel().getSelectedItem() != null 
                        && txtCardTab2.getText().isEmpty()){
                    connectionBeans.setCard("");
                    connectionBeans.setArea(cbAreaTab2.getSelectionModel().getSelectedItem());
                    tblConnectionsDetailsTab2.setItems(new ConnectionBeansActions().getConnectionDetails(connectionBeans,1));
                }else if(cbAreaTab2.getSelectionModel().getSelectedItem() != null 
                        && txtCardTab2.getText().isEmpty() != true){
                    connectionBeans.setCard(txtCardTab2.getText());
                    connectionBeans.setArea(cbAreaTab2.getSelectionModel().getSelectedItem());
                    tblConnectionsDetailsTab2.setItems(new ConnectionBeansActions().getConnectionDetails(connectionBeans,2));
                }else{
                    lblMsgTab2.setText("Please enter the card number or select the area...!");
                }
                
            }//end else if
            //button reset tab2 action
            else if(actionEvent.getSource().equals(btnResetTab2)){
                resetAllFields(btnResetTab2);
            }//end if
            //button pay bill tab2 action
            else if(actionEvent.getSource().equals(btnPayBillTab2)){
                connectionBeans = tblConnectionsDetailsTab2.getSelectionModel().getSelectedItem();
                if(connectionBeans != null){
                    boolean isClose = showPayBillDialog(connectionBeans);
                    if(isClose){
                        lblMsgTab2.setText("Bill paid....!");
                    } else if(!isClose){
                        lblMsgTab2.setText("");
                    }
                }              
                
                else if(connectionBeans == null){
                    lblMsgTab2.setText("Please select the connection.....!");
                }
            }//end else if
            //button search tab3 action
            else if(actionEvent.getSource().equals(btnSearchTab3)){
                connectionPaymentBeans = new ConnectionPaymentBeans();
                ObservableList<ConnectionPaymentBeans> list = FXCollections.observableArrayList();
                if(txtCardTab3.getText().isEmpty() != true 
                        && cbAreaTab3.getSelectionModel().getSelectedItem() == null)
                {
                        connectionPaymentBeans.setCardNumber(txtCardTab3.getText());
                        list = new ConnectionPaymentAction().getBalanceBills(connectionPaymentBeans, 1);
                        if(list != null)
                            tblConnectionsDetailsTab3.setItems(list);
                        
                }
                else if(cbAreaTab3.getSelectionModel().getSelectedItem() != null 
                        && txtCardTab3.getText().isEmpty())
                {
                            connectionPaymentBeans.setCardNumber("");
                            connectionPaymentBeans.setArea(cbAreaTab3.getSelectionModel().getSelectedItem());
                            list = new ConnectionPaymentAction().getBalanceBills(connectionPaymentBeans, 1);
                            if(list != null)
                                tblConnectionsDetailsTab3.setItems(list);
                }else if(cbAreaTab3.getSelectionModel().getSelectedItem() != null 
                        && txtCardTab3.getText().isEmpty() != true)
                {
                        connectionPaymentBeans.setCardNumber(txtCardTab3.getText());
                        connectionPaymentBeans.setArea(cbAreaTab3.getSelectionModel().getSelectedItem());
                        list = new ConnectionPaymentAction().getBalanceBills(connectionPaymentBeans, 2);
                        if(list != null)
                            tblConnectionsDetailsTab3.setItems(list);
                }else{
                    lblMsgTab3.setText("Please enter the card number or select the area...!");
                }
            }//end if
            //button reset tab3 action
            else if(actionEvent.getSource().equals(btnResetTab3)){
                resetAllFields(btnResetTab3);
            }
            //button pay balance bill tab3 action
            else if(actionEvent.getSource().equals(btnPayBalBillTab3)){
                connectionPaymentBeans = tblConnectionsDetailsTab3.getSelectionModel().getSelectedItem();
                if(connectionPaymentBeans != null)
                {
                    lblMsgTab3.setText("");
                    if(showBalanceBillDialog(connectionPaymentBeans))
                        lblMsgTab3.setText("Balanced bill is paid.....!");
                }else{
                    lblMsgTab3.setText("Please select the connection....!");
                }
            }
    }
            
    private void resetAllFields(Button btnAction){
        if(btnAction == btnAddTab1 || btnResetTab1 == btnAction){
                txtAddressTab1.setText("");
                txtCardTab1.setText("");
                txtNameTab1.setText("");
                cbAreaTab1.getSelectionModel().clearSelection();
                txtContactTab1.setText("");
                lblErrAddressTab1.setText("");
                lblErrAreaTab1.setText("");
                lblErrContactTab1.setText("");
                lblErrNameTab1.setText("");
                
        }else if(btnSearchTab2 == btnAction || btnResetTab2 == btnAction){
                txtCardTab2.setText("");
                cbAreaTab2.getSelectionModel().clearSelection();
                lblMsgTab2.setText("");
                tblConnectionsDetailsTab2.getItems().clear();
        }else if(btnSearchTab3 == btnAction || btnResetTab3 == btnAction){
                lblMsgTab3.setText("");
                cbAreaTab3.getSelectionModel().clearSelection();
                txtCardTab3.setText("");
                tblConnectionsDetailsTab3.getItems().clear();
        }
    }
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        
        //setting tblconnectiondetailstab2 columns
            clmSrNoTab2.setCellValueFactory(celldata -> celldata.getValue().getSr_noProperty());
            clmCardTab2.setCellValueFactory(celldata -> celldata.getValue().getCardProperty());
            clmNameTab2.setCellValueFactory(celldata -> celldata.getValue().getNameProperty());
            clmAreaTab2.setCellValueFactory(celldata -> celldata.getValue().getAreaProperty());
            clmContactTab2.setCellValueFactory(celldata -> celldata.getValue().getContactProperty());
            
            
        //setting tblconnectiondetailstab3 columns
            clmSrNoTab3.setCellValueFactory(celldata -> celldata.getValue().getSr_noProperty());
            clmAreaTab3.setCellValueFactory(celldata -> celldata.getValue().getAreaProperty());
            clmCardTab3.setCellValueFactory(celldata -> celldata.getValue().getCardNumberProperty());
            clmNameTab3.setCellValueFactory(celldata -> celldata.getValue().getNameProperty());
            clmBalanceAmountTab3.setCellValueFactory(celldata -> celldata.getValue().getBalanceAmountProperty());
            clmDateTab3.setCellValueFactory(celldata -> celldata.getValue().getBillDateProperty());
            
        //setting tab1 content
            ObservableList options = CentralRepository.AREA_OPTIONS;
            options.remove("All");
            cbAreaTab1.setItems(options);
            txtCardTab1.setDisable(true);
            
        //setting tab2 content
            cbAreaTab2.setItems(options);
        
        //setting tab3 content
            cbAreaTab3.setItems(options);
    }    
    
    public static String getCardNumber(String area){
        String cardNumber = "";
        
        
        //Getting prefix of card number
       
            if(area.equals(CentralRepository.AREA_OPTIONS.get(0)))
                cardNumber = cardNumber + "VV-";
            else if(area.equals(CentralRepository.AREA_OPTIONS.get(1)))
                cardNumber = cardNumber + "MH-";
            else if(area.equals(CentralRepository.AREA_OPTIONS.get(2)))
                cardNumber = cardNumber + "MD-";
            else if(area.equals(CentralRepository.AREA_OPTIONS.get(3)))
                cardNumber = cardNumber + "TW-";
            else if(area.equals(CentralRepository.AREA_OPTIONS.get(4)))
                cardNumber = cardNumber + "RV-";
            else if(area.equals(CentralRepository.AREA_OPTIONS.get(5)))
                cardNumber = cardNumber + "SL-";
            else if(area.equals(CentralRepository.AREA_OPTIONS.get(6)))
                cardNumber = cardNumber + "ZC-";
            else if(area.equals(CentralRepository.AREA_OPTIONS.get(7)))
                cardNumber = cardNumber + "STB-";
            else if(area.equals(CentralRepository.AREA_OPTIONS.get(8)))
                cardNumber = cardNumber + "KL-";
            else if(area.equals(CentralRepository.AREA_OPTIONS.get(9)))
                cardNumber = cardNumber + "DD-";
          
        //getting card nunber suffix
            if(cardNumber.isEmpty() != true)
                cardNumber = cardNumber+new ConnectionBeansActions().getCardNumber(area);
        
        //returning values
            return cardNumber;
    }
    private boolean showConnectionDialog(String message){
        try {
            // Load the fxml file and create a new stage for the popup
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("connectiondialog.fxml"));
                    AnchorPane page = (AnchorPane) loader.load();
                    Stage dialogStage = new Stage();
                    dialogStage.setTitle("Message");
                    dialogStage.initModality(Modality.WINDOW_MODAL);
                    //dialogStage.initOwner(primaryStage);
                    Scene scene = new Scene(page);
                    dialogStage.setScene(scene);

                // Set the person into the controller
                    ConnectiondialogController controller = loader.getController();
                    controller.setDialogStage(dialogStage);
                    controller.setMessage(message);
                    controller.setConnectionBeans(connectionBeans);
            
                // Show the dialog and wait until the user closes it
            
                    dialogStage.showAndWait();
        
                    return controller.isOkClicked();
            
        } catch (Exception e) {
            System.err.println("Exception@ClientMainWindowController:- ");
            e.printStackTrace();
            return false;
        } 
    }
    
    //show pay bill window
    public boolean showPayBillDialog(ConnectionBeans connectionBeans){
    try {
            // Load the fxml file and create a new stage for the popup
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("payuserbill.fxml"));
                    AnchorPane page = (AnchorPane) loader.load();
                    Stage dialogStage = new Stage();
                    dialogStage.setTitle("Pay bills");
                    dialogStage.initModality(Modality.WINDOW_MODAL);
                    //dialogStage.initOwner(primaryStage);
                    Scene scene = new Scene(page);
                    dialogStage.setScene(scene);

                // Set the person into the controller
                    PayUserBillController controller = loader.getController();
                    controller.setStage(dialogStage);
                    controller.setConnectionBeans(connectionBeans);
                    controller.init();
                    
                    
                // Show the dialog and wait until the user closes it
            
                    dialogStage.showAndWait();
        
                    return controller.isClose();
            
        } catch (Exception e) {
            System.err.println("Exception@ClientMainWindowController:- ");
            e.printStackTrace();
            return false;
        }
    
    }
    
    //show balance bill dialog
    public boolean showBalanceBillDialog(ConnectionPaymentBeans connectionPaymentBeans){
        try {
            // Load the fxml file and create a new stage for the popup
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("paybalance.fxml"));
                    AnchorPane page = (AnchorPane) loader.load();
                    Stage dialogStage = new Stage();
                    dialogStage.setTitle("Pay balance bills");
                    dialogStage.initModality(Modality.WINDOW_MODAL);
                    //dialogStage.initOwner(primaryStage);
                    Scene scene = new Scene(page);
                    dialogStage.setScene(scene);

            // Set the person into the controller
                    PayBalanceController controller = loader.getController();
                    controller.setDialogStage(dialogStage);
                    controller.setConnectionPaymentBeans(connectionPaymentBeans);
                    controller.init();
                    
            // Show the dialog and wait until the user closes it
                    dialogStage.showAndWait();
                    
            // return to parant method 
                return controller.isDialogClose();
            
        } catch (Exception e) {
            System.err.println("Exception@ClientMainWindowController:- ");
            e.printStackTrace();
            return false;
        } 
    }
    
}
