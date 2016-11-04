package edu.eezo.data;

import edu.eezo.MainGUI;

import java.util.ArrayList;
import java.util.List;

/**
 * Class represents a customer.
 * Created by Eezo on 02.11.2016.
 */
public class Customer {
    private String name;
    private Place headquartersLocation;

    public Customer(String name, Place headquartersLocation) {
        this.name = name;
        this.headquartersLocation = headquartersLocation;
    }

    public static List<Customer> generateDefaultCustomers() {
        List<Customer> customers = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            customers.add(new Customer("Default Customer " + (i + 1), Place.getPlaceByTitle(MainGUI.placeList, "Николаев")));
        }
        return customers;
    }

    public static Customer getCustomerByName(List<Customer> customerList, String name) {
        for (Customer customer : customerList) {
            if (customer.name.equals(name)) {
                return customer;
            }
        }
        return null;
    }

    public static Customer getRandomCustomerFromList(List<Customer> customerList) {
        return customerList.get(MainGUI.getRandomNumberInRange(0, MainGUI.customerList.size()));
    }

    @Override
    public String toString() {
        return name;
    }
}
