package org.aua.aoop.post.entiries;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Observable;
import java.util.Observer;

@Entity
public class Product extends Observable implements Serializable {
    private Long id;
    private String description;
    private double price;
    private String imagePath;
    private int quantity;

    public Product() {
    }

    public void decreaseQty(int qty) {
        this.quantity -= qty;
        setChanged();
        notifyObservers();
    }

    public Product(Long id, String description, double price, String imagePath, int qty, Observer manager) {
        this.id = id;
        this.description = description;
        this.price = price;
        this.imagePath = imagePath;
        this.quantity = qty;
        addObserver(manager);
    }

    public Product(String description, double price, String imagePath, int quantity) {
        this.description = description;
        this.price = price;
        this.imagePath = imagePath;
        this.quantity = quantity;
    }

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    @GenericGenerator(name="increment", strategy = "increment")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column(name = "description")
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Column(name = "price")
    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    @Column(name = "imagePath")
    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    @Column(name = "quantity")
    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String toStringCustomer() {
        return
                " description='" + description + '\'' +
                        ", price=" + price
                ;
    }
}
