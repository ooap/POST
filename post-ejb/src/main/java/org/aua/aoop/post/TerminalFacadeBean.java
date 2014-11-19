package org.aua.aoop.post;

import org.aua.aoop.post.ex.ProductException;
import org.aua.aoop.post.payment.AbstractPayment;
import org.jboss.logging.Logger;

import javax.annotation.PostConstruct;
import javax.ejb.Remote;
import javax.ejb.Startup;
import javax.ejb.Stateless;

/**
 * Created with IntelliJ IDEA.
 * User: vahemomjyan
 * Date: 11/16/14
 * Time: 10:54 PM
 */
@Stateless
@Startup
@Remote(TerminalFacade.class)
public class TerminalFacadeBean implements TerminalFacade {

    private static final Logger logger =
            Logger.getLogger(TerminalFacadeBean.class);

    private Terminal terminal;
    @Override
    public void startNewSale(String customerName){
          terminal.startNewSale(customerName);
    }

    @Override
    public void addItem(String UPC, int qty) throws ProductException{
          terminal.addItem(UPC, qty);
    }

    @Override
    public SaleItem getCurrSaleItem() {
        return terminal.getCurrSaleItem();
    }

    @Override
    public boolean processPayment(AbstractPayment.PaymentType paymentType, double amount, String info){
        return terminal.processPayment(paymentType, amount, info);
    }

    @Override
    public String getReceipt(){
        return terminal.getReceipt();
    }

    @Override
    public double getCashBalance() {
        return terminal.getCashBalance();
    }

    @Override
    public void setPayment(double total){
        terminal.setPayment(total);
    }

    @Override
    public ShoopingCart getCurrentSale(){
        return terminal.getCurrentShoopingCart();
    }

    @Override
    public boolean productExists(String UPC){
        return terminal.productExists(UPC);
    }

    @PostConstruct
    private void SetupStore(){
        Store store = new Store();
        terminal = store.getTerminal();
    }
}
