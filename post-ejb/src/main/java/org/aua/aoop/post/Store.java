package org.aua.aoop.post;

import org.aua.aoop.post.factory.DBSetup;
import org.aua.aoop.post.product.ProductCatalog;
import org.aua.aoop.post.sales.SalesLog;

import javax.annotation.ManagedBean;
import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.enterprise.context.RequestScoped;
import java.io.Serializable;

@Singleton
@ManagedBean
public class Store implements Serializable {
    private Manager manager;
    private ProductCatalog productCatalog;
    private SalesLog salesLog;

    private String merchantID;

    public Store() {}

    @PostConstruct
    public void startUp(){
        manager = new Manager();
        productCatalog = new ProductCatalog(manager);
        salesLog = new SalesLog();
        merchantID = "MERCHANT_BOND_007";
        DBSetup.getInstance();
    }

    public void close() {
        saveState();
    }

    public void saveState() {
        salesLog.save();
        productCatalog.save();
    }

    public ProductCatalog getProductCatalog() {
        return productCatalog;
    }

    public SalesLog getSalesLog() {
        return salesLog;
    }
}
