/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cableaccountmanager.beans;

/**
 *
 * @author Saurabh
 */
public class AdminBeans 
{
    private String cardNumber,area,paymentStatus,date1,date2;

    public String getCardNumber() {
        return cardNumber;
    }

    @Override
    public String toString() {
        return "AdminTab2Beans{" + "cardNumber=" + cardNumber + ", area=" + area + ", paymentStatus=" + paymentStatus + ", date1=" + date1 + ", date2=" + date2 + '}';
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(String paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    public String getDate1() {
        return date1;
    }

    public void setDate1(String date1) {
        this.date1 = date1;
    }

    public String getDate2() {
        return date2;
    }

    public void setDate2(String date2) {
        this.date2 = date2;
    }
    
    
}
