package org.aua.aoop.post;

import org.aua.aoop.post.product.ProductSpecification;

import java.io.Serializable;


public class SaleItem implements Serializable {
    private ProductSpecification productSpecification;
    private int qty;

    public SaleItem(ProductSpecification productSpecification, int qty) {
        this.productSpecification = productSpecification;
        this.qty = qty;
    }

    public ProductSpecification getProductSpecification() {
        return productSpecification;
    }

    public int getQty() {
        return qty;
    }

    @Override
    public String toString() {


        return "\n\t\t" + productSpecification.toStringCustomer() +
                ", qty=" + qty;
    }
}
