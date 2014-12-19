package org.aua.aoop.post;

import org.aua.aoop.post.payment.AbstractPayment;
import org.aua.aoop.post.ex.ProductException;
import org.aua.aoop.post.sales.SaleItem;

import javax.ejb.Remote;
import java.io.Serializable;


@Remote
public interface TerminalFacade extends Serializable {
    void startNewSale(String customerName);

    void addItem(Long id, int qty) throws ProductException;

    SaleItem getCurrSaleItem();

    boolean processPayment(AbstractPayment.PaymentType paymentType, double amount, String info);

    String getReceipt();

    double getCashBalance();

    void setPayment(double total);

    double getTotal();

    boolean productExists(Long id);
}
