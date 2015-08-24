/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cableaccountmanager.dba;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author Saurabh
 */
public class CentralRepository 
{
    public static final ObservableList<String> AREA_OPTIONS = 
            FXCollections.observableArrayList("Vasant Vihar Colony","MAHADA Colony",
                    "MangalDham Colony","Tekadi Wadi","Ratan Vihar","Shahu Lay-Out",
                    "Zade Complex","STB","Kohade Lay-Out","DD Group","All");
    
    public static final ObservableList<String> PAYMENT_OPTIONS =
            FXCollections.observableArrayList("Paid","Balance","All");
    
    public static final ObservableList<String> USER_OPTIONS = 
            FXCollections.observableArrayList("User","Admin");
        
}
