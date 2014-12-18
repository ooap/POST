package org.aua.aoop.post.payment;

import java.io.Serializable;
import java.util.UUID;


public abstract class AbstractPayment implements Serializable {

    public static enum PaymentType {
        CASH, CREDIT_CARD, CHEQUE
    }

    private UUID paymentID;

    public abstract boolean process(double total);

    public abstract String toString();

}
