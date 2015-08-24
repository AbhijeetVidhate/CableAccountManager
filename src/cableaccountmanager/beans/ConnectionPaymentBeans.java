/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cableaccountmanager.beans;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 *
 * @author Saurabh
 */
public class ConnectionPaymentBeans {
   
    private StringProperty name,address,contact,area,sr_no;
    private int id,paidAmount,billAmount,balanceAmount;
    private StringProperty cardNumber,billDate,balanceDate;

    public int getId() {
        return id;
    }

    public int getPaidAmount() {
        return paidAmount;
    }

    public int getBillAmount() {
        return billAmount;
    }

    public int getBalanceAmount() {
        return balanceAmount;
    }

    public String getSr_no() {
        return sr_no.getValue();
    }

    public String getCardNumber() {
        return cardNumber.getValue();
    }

    public String getBillDate() {
        return billDate.getValue();
    }

    public String getBalanceDate() {
        return balanceDate.getValue();
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setPaidAmount(int paidAmount) {
        this.paidAmount = paidAmount;
    }

    public void setBillAmount(int billAmount) {
        this.billAmount = billAmount;
    }

    public void setBalanceAmount(int balanceAmount) {
        this.balanceAmount = balanceAmount;
    }

    public void setSr_no(StringProperty sr_no) {
        this.sr_no = sr_no;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = new SimpleStringProperty(cardNumber);
    }

    public void setBillDate(String billDate) {
        this.billDate = new SimpleStringProperty(billDate);
    }

    public void setBalanceDate(String balanceDate) {
        this.balanceDate = new SimpleStringProperty(balanceDate);
    }

    public StringProperty getSr_noProperty() {
        return sr_no;
    }

    public StringProperty getCardNumberProperty() {
        return cardNumber;
    }

    public StringProperty getBillDateProperty() {
        return billDate;
    }

    public StringProperty getBalanceDateProperty() {
        return balanceDate;
    }
    
    public StringProperty getPaidAmountProperty() {
        return new SimpleStringProperty(""+this.paidAmount);
    }
    
    public StringProperty getBalanceAmountProperty() {
        return new SimpleStringProperty(""+this.balanceAmount);
    }
    
    public StringProperty getBillAmountProperty() {
        return new SimpleStringProperty(""+this.billAmount);
    }

    public String getName() {
        return name.getValue();
    }

    public String getAddress() {
        return address.getValue();
    }

    public String getContact() {
        return contact.getValue();
    }

    public String getArea() {
        return area.getValue();
    }

    

    public StringProperty getNameProperty() {
        return name;
    }

    public void setName(String name) {
        this.name = new SimpleStringProperty(name);
    }

    public StringProperty getAddressProperty() {
        return address;
    }

    public void setAddress(String address) {
        this.address = new SimpleStringProperty(address);
    }

    public StringProperty getContactProperty() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = new SimpleStringProperty(contact);
    }

    public StringProperty getAreaProperty() {
        return area;
    }

    public void setArea(String area) {
        this.area = new SimpleStringProperty(area);
    }

   

    public void setSr_no(String sr_no) {
        this.sr_no = new SimpleStringProperty(sr_no);
    }
    
    
}
