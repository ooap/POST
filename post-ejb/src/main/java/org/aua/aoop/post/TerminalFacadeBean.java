package org.aua.aoop.post;

import org.aua.aoop.post.ex.ProductException;
import org.aua.aoop.post.payment.AbstractPayment;
import org.aua.aoop.post.sales.SaleItem;
import org.jboss.logging.Logger;

import javax.annotation.PostConstruct;
import javax.ejb.Stateless;
import javax.inject.Inject;

@Stateless
public class TerminalFacadeBean implements TerminalFacade {

    private static final Logger logger =
            Logger.getLogger(TerminalFacadeBean.class);

    @Inject
    private Terminal terminal;

    private String customerName;

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    @Override
    public void startNewSale(String customerName) {
        terminal.startNewSale(customerName);
    }

    public String startSale() {
        startNewSale(this.customerName);
        return "sale";
    }

    @Override
    public void addItem(Long id, int qty) throws ProductException {
        terminal.addItem(id, qty);
    }

    @Override
    public SaleItem getCurrSaleItem() {
        return terminal.getCurrSaleItem();
    }

    @Override
    public boolean processPayment(AbstractPayment.PaymentType paymentType, double amount, String info) {
        return terminal.processPayment(paymentType, amount, info);
    }

    @Override
    public String getReceipt() {
        return terminal.getReceipt();
    }

    @Override
    public double getCashBalance() {
        return terminal.getCashBalance();
    }

    @Override
    public void setPayment(double total) {
        terminal.setPayment(total);
    }

    @Override
    public double getTotal() {
        return terminal.getCurrentShoppingCart().getTotal();
    }

    @Override
    public boolean productExists(Long id) {
        return terminal.productExists(id);
    }

    @PostConstruct
    private void SetupStore() {
    }
}
