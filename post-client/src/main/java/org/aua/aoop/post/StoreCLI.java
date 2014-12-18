package org.aua.aoop.post;

import org.aua.aoop.post.payment.AbstractPayment;
import org.aua.aoop.post.ex.ItemNotFoundException;
import org.aua.aoop.post.ex.NotEnoughItemsException;
import org.aua.aoop.post.ex.ProductException;
import org.aua.aoop.post.util.ClientUtility;

import javax.naming.Context;
import javax.naming.NamingException;
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


            while (true) {

                Scanner scanner = new Scanner(System.in);

                try {
                    TerminalFacade service = lookupRemoteTerminalFacade();

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
                        } catch (ItemNotFoundException | NotEnoughItemsException e) {
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
                                System.out.println("Total amount to be payed = " + Double.toString(service.getTotal()));
                                System.out.print("Tendered money:");
                                String tm = scanner.nextLine();
                                double total = 0;

                                try {
                                    total = Double.parseDouble(tm);
                                    result = service.processPayment(AbstractPayment.PaymentType.CASH, total, "");
                                } catch (NumberFormatException e) {
                                    System.out.println("Please enter valid amount of money");
                                }
                                break;

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


                } catch (NamingException e) {
                    e.printStackTrace();

                }
            }
    }



    /**
     * Looks up and returns the proxy to remote stateless calculator bean
     *
     * @return TerminalFacade remote EJB
     * @throws NamingException
     */
    private static TerminalFacade lookupRemoteTerminalFacade() throws NamingException {

        final Context context = ClientUtility.getInitialContext();
        // The app name is the application name of the deployed EJBs. This is typically the ear name
        // without the .ear suffix. However, the application name could be overridden in the application.xml of the
        // EJB deployment on the server.
        // Since we haven't deployed the application as a .ear, the app name for us will be an empty string
        final String appName = "";
        // This is the module name of the deployed EJBs on the server. This is typically the jar name of the
        // EJB deployment, without the .jar suffix, but can be overridden via the ejb-jar.xml
        // In this example, we have deployed the EJBs in a post-ejb.jar, so the module name is
        // post-ejb
        final String moduleName = "post-ejb";
        // AS7 allows each deployment to have an (optional) distinct name. We haven't specified a distinct name for
        // our EJB deployment, so this is an empty string
        final String distinctName = "";
        // The EJB name which by default is the simple class name of the bean implementation class
        final String beanName = "TerminalFacadeBean";
        // the remote view fully qualified class name
        final String viewClassName = TerminalFacade.class.getName();
        // let's do the lookup
        return (TerminalFacade) context.lookup("ejb:" + appName + "/" + moduleName + "/" + distinctName + "/" + beanName + "!" + viewClassName);
    }

}
