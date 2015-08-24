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
public class UserBeans {
    private int id;
    private StringProperty sr_no,name,password,contact,roll;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSr_no() {
        return sr_no.getValue();
    }

    public void setSr_no(String sr_no) {
        this.sr_no = new SimpleStringProperty(sr_no);
    }

    public String getName() {
        return name.getValue();
    }

    public void setName(String name) {
        this.name = new SimpleStringProperty(name);
    }

    public String getPassword() {
        return password.getValue();
    }

    public void setPassword(String password) {
        this.password = new SimpleStringProperty(password);
    }

    public String getContact() {
        return contact.getValue();
    }

    public void setContact(String contact) {
        this.contact = new SimpleStringProperty(contact);
    }

    public String getRoll() {
        return roll.getValue();
    }

    public void setRoll(String roll) {
        this.roll = new SimpleStringProperty(roll);
    }

    public StringProperty getSr_noProperty() {
        return sr_no;
    }

    public StringProperty getNameProperty() {
        return name;
    }

    public StringProperty getPasswordProperty() {
        return password;
    }

    public StringProperty getContactProperty() {
        return contact;
    }

    public StringProperty getRollProperty() {
        return roll;
    }
    
    
    
    
    
}
