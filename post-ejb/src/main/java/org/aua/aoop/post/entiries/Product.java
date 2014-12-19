//package org.aua.aoop.post.entiries;
//
//
//import org.hibernate.annotations.GenericGenerator;
//
//import javax.persistence.*;
//
//@Entity
//public class Product {
//
//    @Id
//    @GeneratedValue(strategy= GenerationType.AUTO)
//    @GenericGenerator(name="increment", strategy = "increment")
//    private Long id;
//
//    private String description;
//    private int quantity;
//    private float price;
//
//    public Product() {
//    }
//
//    public Product(String description, float price, int quantity) {
//        this.description = description;
//        this.price = price;
//        this.quantity = quantity;
//    }
//
//    public int getQuantity() {
//        return quantity;
//    }
//
//    @Column(name = "quantity")
//    public void setQuantity(int quantity) {
//        this.quantity = quantity;
//    }
//
//    @Column(name = "price")
//    public float getPrice() {
//        return price;
//    }
//
//    @Column(name = "Name")
//    public String getDescription() {
//        return description;
//    }
//
//    public void setPrice(float price) {
//        this.price = price;
//    }
//    public void setDescription(String name) {
//        this.description = name;
//    }
//
//    public Long getId() {
//        return id;
//    }
//
//    public void setId(Long id) {
//        this.id = id;
//    }
//}
