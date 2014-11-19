package org.aua.aoop.post.product;

import java.io.Serializable;
import java.util.Observable;
import java.util.Observer;

public class ProductSpecification extends Observable implements Serializable {
    private String UPC;
    private String description;
    private double price;
    private String imagePath;
    private int qty;

    public void decreaseQty(int qty) {
        this.qty -= qty;
        setChanged();
        notifyObservers();
    }

    public ProductSpecification(String UPC, String description, double price, String imagePath, int qty, Observer manager) {
        this.UPC = UPC;
        this.description = description;
        this.price = price;
        this.imagePath = imagePath;
        this.qty = qty;
        addObserver(manager);
    }

    @Override
    public String toString() {
        return "ProductSpecification{" +
                "UPC='" + UPC + '\'' +
                ", description='" + description + '\'' +
                ", price=" + price +
                ", imagePath='" + imagePath + '\'' +
                ", qty=" + qty +
                '}';
    }

    public String getUPC() {
        return UPC;
    }

    public String getDescription() {
        return description;
    }

    public int getQty() {
        return qty;
    }

    public double getPrice() {
        return price;
    }

    public String toStringCustomer() {
        return
                " description='" + description + '\'' +
                        ", price=" + price
                ;
    }
}
