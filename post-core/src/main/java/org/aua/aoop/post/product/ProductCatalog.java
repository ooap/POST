package org.aua.aoop.post.product;

import org.aua.aoop.post.Manager;
import org.aua.aoop.post.conf.AppConfig;

import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.StringTokenizer;


public class ProductCatalog  implements Serializable {
    private Map<String, ProductSpecification> productList;

    public ProductCatalog(Manager manager) {
        productList = new HashMap<>();
        initFromFile(AppConfig.getInstance().getProductCatalogFileName(), manager);
    }

    // Unused method
//    public void addProduct(String UPC, String description, double price, String image, int qty) {
//        productList.put(UPC, new ProductSpecification(UPC, description, price, image, qty));
//    }

    public void saveToFile(String fileName) {
        try {
            PrintWriter writer = new PrintWriter(fileName);

            for (ProductSpecification prodSpec : productList.values()) {
                writer.print(prodSpec.getUPC() + "\t");
                writer.print(prodSpec.getDescription() + "\t");
                writer.print(prodSpec.getPrice() + "\t");
                writer.print("\t");     // skip image
                writer.print(prodSpec.getQty());
                writer.println();
            }

            writer.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void initFromFile(String fileName, Manager manager) {
        try {
            Scanner scanner = new Scanner(new FileInputStream(fileName));
            StringTokenizer tokenizer;
            while (scanner.hasNext()) {
                String line = scanner.nextLine();
                tokenizer = new StringTokenizer(line);
                String UPC = tokenizer.nextToken("\t");
                String description = tokenizer.nextToken("\t");
                double price = Double.parseDouble(tokenizer.nextToken("\t"));
                int qty = Integer.parseInt(tokenizer.nextToken("\t"));
                productList.put(UPC, new ProductSpecification(UPC, description, price, "", qty, manager));
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public ProductSpecification getProductSpecByID(String UPC) {
        return productList.get(UPC);
    }

    public void printCatalog() {
        for (ProductSpecification spec : productList.values()) {
            System.out.println(spec.toString());
        }
    }

//    public static void main(String[] args) {
//        ProductCatalog productCatalog = new ProductCatalog(manager);
//        productCatalog.initFromFile("products.txt", manager);
//        productCatalog.printCatalog();
//
//        productCatalog.addProduct("8965", "New product", 45.30, "", 100);
//        productCatalog.saveToFile("products.txt");
//    }

    public boolean productExists(String upc) {
        return productList.containsKey(upc);
    }
}
