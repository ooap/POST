package org.aua.aoop.post.product;

import org.aua.aoop.post.Manager;
import org.aua.aoop.post.factory.ProductCrud;

import java.io.*;
import java.util.*;


public class ProductCatalog implements Serializable {
    private ProductCrud productCrud;
    private Map<Long, Product> productList;

    public ProductCatalog(Manager manager) {
        productCrud = new ProductCrud();
        productList = getProductListMap(productCrud.getProductList());
    }

    private Map<Long, Product> getProductListMap(List<Product> list) {
        Map<Long, Product> map = new HashMap<>();
        for (Product i : list) {
            map.put(i.getId(), i);
        }
        return map;
    }

    public Product getProductSpecByID(Long id) {
        return productList.get(id);
    }

    public void printCatalog() {
        for (Product spec : productList.values()) {
            System.out.println(spec.toString());
        }
    }

    public boolean productExists(Long id) {
        return productList.containsKey(id);
    }

    public void save() {
        List<Product> list = new ArrayList<Product>(productList.values());

        for (Product product : list) {
            productCrud.updateProduct(product);
        }
    }
}
