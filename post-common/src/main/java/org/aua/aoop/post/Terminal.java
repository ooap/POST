package org.aua.aoop.post;

import org.aua.aoop.post.payment.AbstractPayment;
import org.aua.aoop.post.payment.CashPayment;
import org.aua.aoop.post.payment.CheckPayment;
import org.aua.aoop.post.payment.CreditPayment;
import org.aua.aoop.post.ex.ItemNotFoundException;
import org.aua.aoop.post.ex.NotEnoughItemsException;
import org.aua.aoop.post.ex.ProductException;
import org.aua.aoop.post.product.ProductSpecification;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Date;
import java.util.List;
import java.util.Scanner;
import java.util.UUID;


public class Terminal {
    /**/
    private UUID terminalID;
    private Sale currentSale;
    private Cashier currentCashier;
    private transient Store store;
    private SaleItem currentSaleItem;

    /* *
     * Constructor
     * @param currentCashier
     * @param  store
     * @throws RemoteException
     * */
    public Terminal(Cashier currentCashier, Store store) throws RemoteException {
        this.currentCashier = currentCashier;
        this.store = store;
        terminalID = UUID.randomUUID();
    }

    /**
     * Starts new sale
     *
     * @param customerName name of the customer
     * @throws java.rmi.RemoteException
     */
    public void startNewSale(String customerName) throws RemoteException {
        currentSale = new Sale(this, customerName);
        System.out.println(new Date() + "\t" + "New sale started");
    }

    public void addItem(String UPC, int qty) throws ProductException, RemoteException {
        ProductSpecification specification = store.getProductCatalog().getProductSpecByID(UPC);
        if (specification != null) {
            if (specification.getQty() >= qty) {
                currentSaleItem = new SaleItem(specification, qty);
                currentSale.addSaleItem(currentSaleItem);
                System.out.println(new Date() + "\t" + "New item added\t" + UPC + "\t" + qty + "\t" + currentSale.getCustomerName());
            } else {
                throw new NotEnoughItemsException();
            }
        } else {
            throw new ItemNotFoundException();
        }
    }

    public SaleItem getCurrSaleItem() throws RemoteException {
        return currentSaleItem;
    }

    public boolean processPayment(AbstractPayment.PaymentType paymentType, double amount, String info) throws RemoteException {
        boolean result = false;
        switch (paymentType) {
            case CASH:
                currentSale.setPayment(new CashPayment(currentSale.getTotal()));
                result = currentSale.getPayment().process(amount);
                break;
            case CHEQUE:
                currentSale.setPayment(new CheckPayment(info));
                result = currentSale.getPayment().process(amount);
                break;
            case CREDIT_CARD:
                currentSale.setPayment(new CreditPayment(info));
                result = currentSale.getPayment().process(amount);
                break;
        }

        if (result) {
            endSale();
        }
        return result;
    }


    private void endSale() throws RemoteException {
        List<SaleItem> saleItems = currentSale.getSaleItems();

        for (SaleItem item : saleItems) {
            item.getProductSpecification().decreaseQty(item.getQty());
        }

        store.getSalesLog().archiveSale(currentSale);
        System.out.println(new Date() + "\t" + "Sale ended");
    }

    public String getReceipt() throws RemoteException {
        return currentSale.toString();
    }

    public double getCashBalance() throws RemoteException {
        return ((CashPayment) currentSale.getPayment()).getBalance();
    }

    public void setPayment(double total) throws RemoteException {
        currentSale.setPayment(new CashPayment(total));
    }

    public Sale getCurrentSale() throws RemoteException {
        return currentSale;
    }

    public boolean productExists(String UPC) throws RemoteException {
        return store.getProductCatalog().productExists(UPC);
    }

    @Override
    public String toString() {
        return "Terminal{" +
                "terminalID=" + terminalID +
                '}';
    }
}
