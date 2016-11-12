package edu.eezo.data;

import edu.eezo.MainGUI;

import java.util.ArrayList;
import java.util.List;

/**
 * Class represents a named list of orders.
 * Created by Eezo on 02.11.2016.
 */
public class OrderList {
    /*private String title;
    private List<Order> orders;

    public OrderList(String title, List<Order> orders) {
        if (title == null || title.isEmpty()){
            title = "Order List";
        }
        this.title = title;
        if (orders == null) {
            this.orders = new ArrayList<>();
        } else {
            this.orders = orders;
        }
    }

    public List<Order> getOrders() {
        return orders;
    }

    /* *
     * Generates an order list with 50 orders with random data (places pairs could repeat).
     * @param title order list title
     * @return an order list
     * /
    public static OrderList generateDefaultOrderList(String title) {
        List<Order> orders = new ArrayList<>();
        for (int i = 0; i < 50; i++) {
            orders.add(Order.generateDefaultOrder());
        }
        return new OrderList(title, orders);
    }

    /* *
     * Generates an order list with no-repeat places pairs (list size depends on <code>placeList.size()</code>).
     * @param title order list title
     * @param weightRange 2 numbers, possible weight value: [ weightRange[0], weightRange[1] ]
     * @param priceForTon a price for ton
     * @return an order list
     *  /
    public static OrderList generateOrderListWithUniquePairs(String title, int[] weightRange, int priceForTon){
        List<Order> orders = new ArrayList<>();
        for (Place origin : MainGUI.placeList){
            for (Place destination : MainGUI.placeList){
                if (origin.equals(destination)) continue;
                int weight = MainGUI.getRandomNumberInRange(weightRange[0], weightRange[1]);
                orders.add(new Order(Client.getRandomCustomerFromList(MainGUI.clientList), origin, destination,
                        weight, priceForTon, (7*24*60)));
            }
        }
        return new OrderList(title, orders);
    }

    @Override
    public String toString() {
        return title;
    }*/
}
