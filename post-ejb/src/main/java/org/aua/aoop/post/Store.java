package org.aua.aoop.post;

import org.aua.aoop.post.conf.AppConfig;
import org.aua.aoop.post.product.ProductCatalog;

import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.inject.Inject;
import java.io.Serializable;

@Singleton
@Startup
public class Store implements Serializable {
    private Manager manager;
    private ProductCatalog productCatalog;
    private SalesLog salesLog;

    @Inject
    private Terminal terminal;

    private String merchantID;

    public Store(){
        startUp();
    }

    public void startUp(){
        manager = new Manager();
        productCatalog = new ProductCatalog(manager);
        salesLog = new SalesLog();
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
