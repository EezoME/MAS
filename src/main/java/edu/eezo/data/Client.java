package edu.eezo.data;

import edu.eezo.MainGUI;

import java.util.ArrayList;
import java.util.List;

/**
 * Class represents a customer (Client).
 * Created by Eezo on 02.11.2016.
 */
public class Client {
    /**
     * Client name.
     */
    private String name;

    /**
     * Client's list of base orders.
     */
    private List<Order> baseOrderList;

    /**
     * Client's list of it's own orders.
     */
    private List<Order> clientOrderList;

    public Client(String name, List<Order> baseOrderList, List<Order> clientOrderList) {
        this.name = name;
        this.baseOrderList = baseOrderList;
        this.clientOrderList = clientOrderList;
        // if NullPointerException -> add new ArrayList()
    }

    public static Client getClientByName(List<Client> clientList, String name) {
        for (Client client : clientList) {
            if (client.name.equals(name)) {
                return client;
            }
        }

        return null;
    }

    /* Generators */

    /**
     * Generates default customers with no orders.
     *
     * @param number a number of customers to generate
     * @return a list of customers
     */
    public static List<Client> generateDefaultCustomers(int number) {
        List<Client> customers = new ArrayList<>();

        for (int i = 0; i < number; i++) {
            customers.add(new Client("Default Client " + (i + 1), null, null));
        }

        return customers;
    }

    public static Client getRandomCustomerFromList(List<Client> clientList) {
        return clientList.get(MainGUI.getRandomNumberInRange(0, MainGUI.clientList.size()));
    }

    @Override
    public String toString() {
        return name;
    }
}
