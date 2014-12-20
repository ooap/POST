package org.aua.aoop.post;

import org.aua.aoop.post.entiries.Product;

import java.util.Observable;
import java.util.Observer;
import java.util.UUID;

public class Manager implements Observer {
    private UUID managerID;
    private String name;

    @Override
    public void update(Observable o, Object arg) {
        Product pSpec = (Product) o;
        if (pSpec.getQuantity() < 5) {
            System.out.println("* * * NOTICE FOR MANAGER! * * *");
            System.out.println("Inventory is low for " + pSpec.getId() + "\t" + pSpec.getDescription());
            System.out.println("Items left is " + pSpec.getQuantity());
            System.out.println("* * * * * * * END * * * * * * *");
        }
    }
}
