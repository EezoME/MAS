package edu.eezo.data;

import java.util.List;

/**
 * Class represents a customer (Client).
 * Created by Eezo on 02.11.2016.
 */
public class Client {
    private String name;
    private List<Order> storageList;
    private List<Order> orderList;

    public Client(String name, List<Order> storageList, List<Order> orderList) {
        this.name = name;
        this.storageList = storageList;
        this.orderList = orderList;
        // if NullPointerException -> new ArrayList()
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
    /* *
     * Generates 5 default customers with headquarters in "Николаев".
     * @return a list of customers
     * /
    public static List<Client> generateDefaultCustomers() {
        List<Client> customers = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            customers.add(new Client("Default Client " + (i + 1), Place.getPlaceByTitle(MainGUI.placeList, "Николаев")));
        }
        return customers;
    }

    public static Client getRandomCustomerFromList(List<Client> clientList) {
        return clientList.get(MainGUI.getRandomNumberInRange(0, MainGUI.clientList.size()));
    }*/

    @Override
    public String toString() {
        return name;
    }
}
