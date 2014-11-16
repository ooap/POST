package org.aua.aoop.post;

import org.aua.aoop.post.payment.AbstractPayment;
import org.aua.aoop.post.ex.ProductException;

import java.io.Serializable;
import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ITerminalFacade extends Serializable {
    void startNewSale(String customerName) throws RemoteException;

    void addItem(String UPC, int qty) throws ProductException, RemoteException;

    SaleItem getCurrSaleItem() throws RemoteException;

    boolean processPayment(AbstractPayment.PaymentType paymentType, double amount, String info) throws RemoteException;

    String getReceipt() throws RemoteException;

    double getCashBalance() throws RemoteException;

    void setPayment(double total) throws RemoteException;

    Sale getCurrentSale() throws RemoteException;

    boolean productExists(String UPC) throws RemoteException;
}
