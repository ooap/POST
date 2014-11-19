package org.aua.aoop.post.payment;


import java.io.Serializable;

public class CreditPayment extends AbstractPayment  implements Serializable {
    private String cardNumber;
    private int cardType;

    public CreditPayment(String cardNumber) {
        this.cardNumber = cardNumber;
        determineCreditCardType(cardNumber);
    }

    private void determineCreditCardType(String cardNumber) {

        switch (cardNumber.charAt(0)) {
            case '5':
                cardType = 0;

            case '4':
                cardType = 1;

            case '3':
                cardType = 2;
        }
    }

    @Override
    public boolean process(double total) {
        return true;
    }

    @Override
    public String toString() {
        return "Payed by card: xx-" + cardNumber.substring(12);
    }


}
