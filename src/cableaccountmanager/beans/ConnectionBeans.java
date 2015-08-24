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
public class ConnectionBeans 
{
    private int id;
    private StringProperty name,address,contact,area,card,sr_no;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public String getCard() {
        return card.getValue();
    }

    public String getSr_no() {
        return sr_no.getValue();
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

    public StringProperty getCardProperty() {
        return card;
    }

    public void setCard(String card) {
        this.card = new SimpleStringProperty(card);
    }

    public StringProperty getSr_noProperty() {
        return sr_no;
    }

    public void setSr_no(String sr_no) {
        this.sr_no = new SimpleStringProperty(sr_no);
    }
        
}
