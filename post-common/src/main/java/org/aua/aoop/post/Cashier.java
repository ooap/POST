package org.aua.aoop.post;

import java.io.Serializable;
import java.util.UUID;


public class Cashier implements Serializable {
    private UUID cashierID;
    String cashierName;

    public Cashier(String cashierName) {
        this.cashierID = UUID.randomUUID();
        this.cashierName = cashierName;
    }
}
