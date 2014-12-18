/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.aua.aoop.post;


public class SalesInfo {

    private static SalesInfo instance;

    public static SalesInfo getInstance() {
        if (instance == null) instance = new SalesInfo();

        return instance;
    }

    private double amountToBePayed;

    private Terminal service;

    public Terminal getService() {
        return service;
    }

    public void setService(Terminal service) {
        this.service = service;
    }

    public double getAmountToBePayed() {
        return amountToBePayed;
    }

    public void setAmountToBePayed(double amountToBePayed) {
        this.amountToBePayed = amountToBePayed;
    }

    public void reset() {
        this.amountToBePayed = 0;

    }
}
