package org.aua.aoop.post;

import org.aua.aoop.post.payment.AbstractPayment;
import org.aua.aoop.post.payment.CashPayment;
import org.aua.aoop.post.payment.CheckPayment;
import org.aua.aoop.post.payment.CreditPayment;
import org.aua.aoop.post.ex.ItemNotFoundException;
import org.aua.aoop.post.ex.NotEnoughItemsException;
import org.aua.aoop.post.ex.ProductException;
import org.aua.aoop.post.product.ProductSpecification;

import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.ejb.Stateful;
import javax.ejb.Stateless;
import javax.inject.Inject;

import java.util.Date;
import java.util.List;
import java.util.UUID;


@Stateless
@Startup
public class Terminal {
    /**/
    private UUID terminalID;

    @Inject
    private ShoppingCart currentShoppingCart;

    @Inject
    private transient Store store;

    private SaleItem currentSaleItem;

    public Terminal(){}

    @PostConstruct
     public void TerminalCtor(){
        terminalID = UUID.randomUUID();
    }

    /**
     * Starts new sale
     *
     * @param customerName name of the customer
     */
    public void startNewSale(String customerName){
        currentShoppingCart.setCustomerName(customerName);
        System.out.println(new Date() + "\t" + "New sale started");
    }

    public void addItem(String UPC, int qty) throws ProductException {
        ProductSpecification specification = store.getProductCatalog().getProductSpecByID(UPC);
        if (specification != null) {
            if (specification.getQty() >= qty) {
                currentSaleItem = new SaleItem(specification, qty);
                currentShoppingCart.addSaleItem(currentSaleItem);
                System.out.println(new Date() + "\t" + "New item added\t" + UPC + "\t" + qty + "\t" + currentShoppingCart.getCustomerName());
            } else {
                throw new NotEnoughItemsException();
            }
        } else {
            throw new ItemNotFoundException();
        }
    }

    public SaleItem getCurrSaleItem(){
        return currentSaleItem;
    }

    public boolean processPayment(AbstractPayment.PaymentType paymentType, double amount, String info) {
        boolean result = false;
        switch (paymentType) {
            case CASH:
                currentShoppingCart.setPayment(new CashPayment(currentShoppingCart.getTotal()));
                result = currentShoppingCart.getPayment().process(amount);
                break;
            case CHEQUE:
                currentShoppingCart.setPayment(new CheckPayment(info));
                result = currentShoppingCart.getPayment().process(amount);
                break;
            case CREDIT_CARD:
                currentShoppingCart.setPayment(new CreditPayment(info));
                result = currentShoppingCart.getPayment().process(amount);
                break;
        }

        if (result) {
            endSale();
        }
        return result;
    }


    private void endSale(){
        List<SaleItem> saleItems = currentShoppingCart.getSaleItems();

        for (SaleItem item : saleItems) {
            item.getProductSpecification().decreaseQty(item.getQty());
        }

        store.getSalesLog().archiveSale(currentShoppingCart);
        System.out.println(new Date() + "\t" + "Sale ended");
    }

    public String getReceipt(){
        return currentShoppingCart.toString();
    }

    public double getCashBalance(){
        return ((CashPayment) currentShoppingCart.getPayment()).getBalance();
    }

    public void setPayment(double total){
        currentShoppingCart.setPayment(new CashPayment(total));
    }

    public ShoppingCart getCurrentShoppingCart(){
        return currentShoppingCart;
    }

    public boolean productExists(String UPC) {
        return store.getProductCatalog().productExists(UPC);
    }

    @Override
    public String toString() {
        return "Terminal{" +
                "terminalID=" + terminalID +
                '}';
    }
}
