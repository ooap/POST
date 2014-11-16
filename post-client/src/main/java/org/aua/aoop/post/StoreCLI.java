package org.aua.aoop.post;

import org.aua.aoop.post.payment.AbstractPayment;
import org.aua.aoop.post.ex.ItemNotFoundException;
import org.aua.aoop.post.ex.NotEnoughItemsException;
import org.aua.aoop.post.ex.ProductException;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RMISecurityManager;
import java.rmi.RemoteException;
import java.util.Scanner;


public class StoreCLI {

    private static String askUPC(Scanner scanner) {
        System.out.print("Enter UPC: ");
        return scanner.nextLine();
    }

    private static int askQty(Scanner scanner) throws NumberFormatException {
        System.out.print("Enter quantity: ");
        return Integer.parseInt(scanner.nextLine());
    }

    private static String askCustomerName(Scanner scanner) {
        System.out.print("Customer Name: ");
        return scanner.nextLine();
    }

    public static void main(String[] args) {
        System.setSecurityManager(new RMISecurityManager());
        String remoteName = "rmi://10.15.1.172/terminal";
        try {
            ITerminalFacade service = (ITerminalFacade) Naming.lookup(remoteName);

            while (true) {

                Scanner scanner = new Scanner(System.in);

                try {
                    service.startNewSale(askCustomerName(scanner));
                    String upc = askUPC(scanner);

                    while (upc.toLowerCase().compareTo("eos") != 0) {

                        if (!service.productExists(upc)) {
                            try {
                                throw new ItemNotFoundException();
                            } catch (ItemNotFoundException e) {
                                System.out.println(e.getMessage());
                                upc = askUPC(scanner);
                                continue;
                            }
                        }

                        int qty = 0;
                        try {
                            qty = askQty(scanner);
                        } catch (NumberFormatException e) {
                            System.out.println("Please enter valid quantity");
                            continue;
                        }

                        try {
                            service.addItem(upc, qty);
                        } catch (ItemNotFoundException e) {
                            System.out.println(e.getMessage());
                        } catch (NotEnoughItemsException e) {
                            System.out.println(e.getMessage());
                        } catch (ProductException e) {
                            e.printStackTrace();
                        }

                        upc = askUPC(scanner);
                    }

                    while (true) {
                        boolean result = false;
                        System.out.print("Payment Type (CASH = 1|CREDIT = 2|CHEQUE = 3): ");
                        int paymentType = Integer.parseInt(scanner.nextLine());
                        switch (paymentType) {

                            case 1:     // Cash payment
                                System.out.println("Total amount to be payed = " + service.getCurrentSale().getTotal());
                                System.out.print("Tendered money:");
                                String tm = scanner.nextLine();
                                double total = 0;

                                try {
                                    total = Double.parseDouble(tm);
                                    result = service.processPayment(AbstractPayment.PaymentType.CASH, total, "");
                                } catch (NumberFormatException e) {
                                    System.out.println("Please enter valid amount of money");
                                } finally {
                                    break;
                                }

                            case 2:     // Credit card payment
                                System.out.print("Enter card number: ");
                                String creditCardNumber = scanner.nextLine();
                                result = service.processPayment(AbstractPayment.PaymentType.CREDIT_CARD, 0, creditCardNumber);
                                break;

                            case 3:     // Check payment
                                System.out.print("Enter check number: ");
                                String checkNumber = scanner.nextLine();
                                result = service.processPayment(AbstractPayment.PaymentType.CHEQUE, 0, checkNumber);
                                break;
                        }
                        if (result) break;
                    }


                    System.out.println(service.getReceipt());
//                    store.saveState();         TODO: move this server-side


                } catch (RemoteException e) {
                    e.printStackTrace();

                }
            }
        } catch (NotBoundException e) {
            e.printStackTrace();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }
}
