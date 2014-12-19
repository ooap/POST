package org.aua.aoop.post.sales;

import org.aua.aoop.post.ShoppingCart;
import org.aua.aoop.post.conf.AppConfig;

import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class SalesLog implements Serializable {
    private static String SAVE_FILE_URL = AppConfig.getInstance().getSalesLogSaveFileName();
    Map<UUID, ShoppingCart> processedSaleList;

    public SalesLog() {
        this.processedSaleList = new HashMap<>();
    }

    public void archiveSale(ShoppingCart shoppingCart) {
        processedSaleList.put(shoppingCart.getSaleID(), shoppingCart);
    }

    public void printLog() {
        for (ShoppingCart shoppingCart : processedSaleList.values()) {
            System.out.println("\r\n" + shoppingCart.getLog());
        }
    }

    public void save() {

    }
}
