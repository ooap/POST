package org.aua.aoop.post;

import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class SalesLog implements Serializable {
    private static String SAVE_FILE_URL = "salesLog.save";
    Map<UUID, Sale> processedSaleList;

    public SalesLog() {
        this.processedSaleList = new HashMap<>();
    }

    public void archiveSale(Sale sale) {
        processedSaleList.put(sale.getSaleID(), sale);
    }

    public void printLog() {
        for (Sale sale : processedSaleList.values()) {
            System.out.println("\r\n" + sale.toString());
        }
    }

//    public void printLogForCustomer(String customerName) {
//    }

    public void saveSalesLogToFile() {
        try {
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(new FileOutputStream(SAVE_FILE_URL));
            objectOutputStream.writeObject(this);
            objectOutputStream.flush();
            objectOutputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static SalesLog loadSalesLogFromFile() {
        SalesLog salesLog = null;
        ObjectInputStream objectInputStream = null;
        try {
            objectInputStream = new ObjectInputStream(new FileInputStream(SAVE_FILE_URL));
            Object loadedObj = objectInputStream.readObject();
            salesLog = (SalesLog) loadedObj;

        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            if (objectInputStream != null) {
                try {
                    objectInputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return salesLog == null ? new SalesLog() : salesLog;
    }
}
