package org.aua.aoop.post;

import org.aua.aoop.post.payment.AbstractPayment;

import javax.annotation.PostConstruct;
import javax.ejb.Stateful;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;


@Stateful
public class ShoppingCart implements Serializable {
    private UUID saleID;
    private List<SaleItem> saleItems;
    private AbstractPayment payment;
    private String customerName;
    private Date dateTime;
    private double total;

    public ShoppingCart() {
    }

    @PostConstruct
    public void ShoppingCartConstruct() {
        dateTime = new Date();
        saleID = UUID.randomUUID();
        saleItems = new ArrayList<>();
        total = 0;
    }


    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public void addSaleItem(SaleItem item) {
        saleItems.add(item);
        total += item.getProductSpecification().getPrice() * item.getQty();
    }

    public List<SaleItem> getSaleItems() {
        return saleItems;
    }

    public UUID getSaleID() {
        return saleID;
    }

    public AbstractPayment getPayment() {
        return payment;
    }

    public String getCustomerName() {
        return customerName;
    }

    public Date getDateTime() {
        return dateTime;
    }

    public double getTotal() {
        return total;
    }

    public String getLog() {

        String saleProducts = "";

        for (SaleItem saleItem : saleItems) {
            saleProducts += saleItem.toString();
        }

        String printLog = "Sale{" +
                "saleID = " + saleID +
                "\n\t dateTime = " + dateTime +
                "\n\t customerName = '" + customerName + '\'' +
                "\n\t saleItems = " + saleProducts +
                "\n\t total = " + total +
                "\n\t payment = " + payment +
                '}';

        return printLog;
    }

    public void setPayment(AbstractPayment payment) {
        this.payment = payment;
    }
}
