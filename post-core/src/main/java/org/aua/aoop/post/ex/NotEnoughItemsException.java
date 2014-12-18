package org.aua.aoop.post.ex;


import java.io.Serializable;

public class NotEnoughItemsException extends ProductException  implements Serializable {
    public NotEnoughItemsException() {
        super("Not enough items available");
    }
}
