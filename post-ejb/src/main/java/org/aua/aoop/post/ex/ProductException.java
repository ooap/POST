package org.aua.aoop.post.ex;


import java.io.Serializable;

public class ProductException extends Exception implements Serializable{
    public ProductException(String message) {
        super(message);
    }
}
