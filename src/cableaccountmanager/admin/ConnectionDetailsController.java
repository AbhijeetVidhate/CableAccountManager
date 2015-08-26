/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cableaccountmanager.admin;

import cableaccountmanager.actions.ConnectionBeansActions;
import cableaccountmanager.actions.PaymentBeansActions;
import cableaccountmanager.beans.ConnectionBeans;
import cableaccountmanager.beans.PaymentBeans;
import cableaccountmanager.dba.CentralRepository;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Saurabh
 */
public class ConnectionDetailsController implements Initializable{

    /**
     * Initializes the controller class.
     */
    //Content of window
        @FXML
            TextField txtUserName,txtCardNumber,txtContactNumber;

        @FXML
            TextArea txtAddress;

        @FXML 
            ComboBox<String> cbArea;

        @FXML
            TableView<PaymentBeans> tblUserBills;

        @FXML
            TableColumn<PaymentBeans,String> clmUserBillAmount,clmUserBillDate,
                clmUserBillPaidAmount,clmUserBillBalanceAmount;

        @FXML
            DatePicker dpStartingDate,dpEndingDate;

        @FXML
            Button btnShowBills,btnRemove;

        @FXML
           Text lblEditUserName,lblEditAddress,lblEditArea,lblEditContactNumber; 
   
    //handle mouse actions
        @FXML
        public void handleEditTextClicked(MouseEvent mouseEvent){
            Text clickedText = (Text) mouseEvent.getSource();
                
                if(clickedText.getText().equalsIgnoreCase("edit")){
                    clickedText.setText("Save");
                    if(clickedText.equals(lblEditUserName))
                        txtUserName.setDisable(false);
                    
                    else if(clickedText.equals(lblEditAddress))
                        txtAddress.setDisable(false);
                    
                    else if(clickedText.equals(lblEditContactNumber))
                        txtContactNumber.setDisable(false);
                    
                    else if(clickedText.equals(lblEditArea))
                        cbArea.setDisable(false);
                }
                else if(clickedText.getText().equalsIgnoreCase("save")){
                    clickedText.setText("Edit");
                    if(clickedText.equals(lblEditUserName)){                        
                        if(new ConnectionBeansActions().updateConnection
                                (connectionBeans.getId(),"username", txtUserName.getText()))
                                        txtUserName.setDisable(true);
                    }                    
                    else if(clickedText.equals(lblEditAddress)){                        
                        if(new ConnectionBeansActions().updateConnection
                                    (connectionBeans.getId(),"address", txtAddress.getText()))
                                        txtAddress.setDisable(true);
                    }
                    else if(clickedText.equals(lblEditContactNumber)){                        
                        if(new ConnectionBeansActions().updateConnection
                                    (connectionBeans.getId(),"contactnumber", txtContactNumber.getText()))
                                        txtContactNumber.setDisable(true);
                    }
                    else if(clickedText.equals(lblEditArea)){
                        if(!cbArea.getSelectionModel().getSelectedItem().equals(connectionBeans.getArea())){
                            txtCardNumber.setText(new ConnectionBeansActions().updateArea(connectionBeans.getId(), cbArea.getSelectionModel().getSelectedItem()));
                            new PaymentBeansActions().updateCardNumber(connectionBeans.getCard(), txtCardNumber.getText());
                            cbArea.setDisable(true);
                        }                        
                    }
                }
        }
        
    //handling button actions
        @FXML
        public void handleButtonActions(ActionEvent actionEvent){
            if(actionEvent.getSource().equals(btnShowBills)){
                String startingDate = ""+dpStartingDate.getValue();
                String endingDate = ""+dpEndingDate.getValue();
                tblUserBills.setItems(new PaymentBeansActions().getConnectionBillRecord(connectionBeans, startingDate, endingDate));
                
            }else if(actionEvent.getSource().equals(btnRemove)){
                if(connectionBeans != null){
                    isClose = new ConnectionBeansActions().removeConnectionBills(connectionBeans);
                    dialogStage.close();
                }
            }
        }
    //class content
        private ConnectionBeans connectionBeans;
        private Stage dialogStage;
        private boolean isClose = false;
        
    //setting connectionsBeans object
        public void setConnectionBeans(ConnectionBeans connectionBeans){
            this.connectionBeans = connectionBeans;
        }

    //setting dialog stage
        public void setDialogStage(Stage dialogStage){
            this.dialogStage = dialogStage;
        }
        
    //returning is window close
        public boolean isClose(){
            return this.isClose;
        }
        
    //setting all values to fields    
        public void init(){
            txtUserName.setText(connectionBeans.getName());
            txtAddress.setText(connectionBeans.getAddress());
            txtCardNumber.setText(connectionBeans.getCard());
            txtContactNumber.setText(connectionBeans.getContact());
            ObservableList<String> area = CentralRepository.AREA_OPTIONS;
            area.remove("All");
            cbArea.setItems(area);
            cbArea.getSelectionModel().select(connectionBeans.getArea());
            tblUserBills.setItems(new PaymentBeansActions().getConnectionBillRecord(connectionBeans,"null","null"));
            txtUserName.setDisable(true);
            txtAddress.setDisable(true);
            txtCardNumber.setDisable(true);
            txtContactNumber.setDisable(true);
            cbArea.setDisable(true);
        }
        
    

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        clmUserBillAmount.setCellValueFactory(celldata -> celldata.getValue().getBillAmountProperty());
        clmUserBillPaidAmount.setCellValueFactory(celldata -> celldata.getValue().getPaidAmountProperty());
        clmUserBillBalanceAmount.setCellValueFactory(celldata -> celldata.getValue().getBalanceAmountProperty());
        clmUserBillDate.setCellValueFactory(celldata -> celldata.getValue().getBillDateProperty());
    }
   
}
