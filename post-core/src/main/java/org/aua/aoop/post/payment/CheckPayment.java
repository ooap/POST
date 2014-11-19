package org.aua.aoop.post.payment;

import java.io.Serializable;

public class CheckPayment extends AbstractPayment  implements Serializable {
    private String checkNumber;

    public CheckPayment(String checkNumber) {
        this.checkNumber = checkNumber;
    }

    @Override
    public boolean process(double total) {
        return true;
    }

    @Override
    public String toString() {
        return "Paid by check: " + checkNumber;
    }


}
