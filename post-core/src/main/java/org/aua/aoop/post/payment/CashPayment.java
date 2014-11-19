package org.aua.aoop.post.payment;


import java.io.Serializable;

public class CashPayment extends AbstractPayment  implements Serializable {
    private double total;
    private double tendered;

    public CashPayment(double total) {
        this.total = total;
    }

    @Override
    public boolean process(double tendered) {
        this.tendered = tendered;
        return tendered >= total;
    }

    @Override
    public String toString() {
        return tendered + "\n\t Balance = " + getBalance();
    }

    public double getBalance() {
        return tendered - total;
    }


}
