package org.aua.aoop.post;

import org.aua.aoop.post.conf.AppConfig;
import org.aua.aoop.post.product.ProductCatalog;

import java.io.Serializable;

public class Store implements Serializable {
    private Manager manager;
    private ProductCatalog productCatalog;
    private SalesLog salesLog;
    private Terminal terminal;
    private Cashier cashier;
    private String merchantID;

    public Store(){
        startUp();
    }

    public void startUp(){
        manager = new Manager();
        productCatalog = new ProductCatalog(manager);
        salesLog = new SalesLog();
        cashier = new Cashier("Seroj");
        terminal = new Terminal(cashier, this);
        merchantID = "MERCHANT_BOND_007";
    }

    public void close() {
        saveState();
    }

    public void saveState() {
        salesLog.saveSalesLogToFile();
        productCatalog.saveToFile(AppConfig.getInstance().getProductCatalogFileName());
    }

    public Terminal getTerminal() {
        return terminal;
    }

    public ProductCatalog getProductCatalog() {
        return productCatalog;
    }

    public SalesLog getSalesLog() {
        return salesLog;
    }
}
