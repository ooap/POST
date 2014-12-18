package org.aua.aoop.post;

import org.aua.aoop.post.payment.AbstractPayment;
import org.aua.aoop.post.ex.ProductException;

import javax.ejb.Remote;
import java.io.Serializable;


@Remote
public interface TerminalFacade extends Serializable {
    void startNewSale(String customerName);

    void addItem(String UPC, int qty) throws ProductException;

    SaleItem getCurrSaleItem();

    boolean processPayment(AbstractPayment.PaymentType paymentType, double amount, String info);

    String getReceipt();

    double getCashBalance();

    void setPayment(double total);

    ShoppingCart getCurrentSale();

    boolean productExists(String UPC);
}
