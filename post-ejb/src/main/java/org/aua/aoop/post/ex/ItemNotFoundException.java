package org.aua.aoop.post.ex;


import java.io.Serializable;

public class ItemNotFoundException extends ProductException  implements Serializable {

    public ItemNotFoundException() {
        super("Item not found");
    }
}
