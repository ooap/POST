package org.aua.aoop.post.sales;

import org.aua.aoop.post.product.Product;

import java.io.Serializable;


public class SaleItem implements Serializable {
    private Product product;
    private int qty;

    public SaleItem(Product product, int qty) {
        this.product = product;
        this.qty = qty;
    }

    public Product getProduct() {
        return product;
    }

    public int getQty() {
        return qty;
    }

    @Override
    public String toString() {

        return "\n\t\t" + product.toStringCustomer() +
                ", qty=" + qty;
    }
}
