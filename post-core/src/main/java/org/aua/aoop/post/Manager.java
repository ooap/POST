package org.aua.aoop.post;

import org.aua.aoop.post.product.ProductSpecification;

import java.util.Observable;
import java.util.Observer;
import java.util.UUID;

public class Manager implements Observer {
    private UUID managerID;
    private String name;

    @Override
    public void update(Observable o, Object arg) {
        ProductSpecification pSpec = (ProductSpecification) o;
        if (pSpec.getQty() < 5) {
            System.out.println("* * * NOTICE FOR MANAGER! * * *");
            System.out.println("Inventory is low for " + pSpec.getUPC() + "\t" + pSpec.getDescription());
            System.out.println("Items left is " + pSpec.getQty());
            System.out.println("* * * * * * * END * * * * * * *");
        }
    }
}
