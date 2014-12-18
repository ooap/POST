/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.aua.aoop.post;

import javax.ejb.EJB;
import javax.ejb.Singleton;

@Singleton
public class SalesInfo {

    public SalesInfo () {
    }

    private double amountToBePayed;

    @EJB
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
