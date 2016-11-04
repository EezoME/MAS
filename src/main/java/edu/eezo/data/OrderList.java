package edu.eezo.data;

import java.util.ArrayList;
import java.util.List;

/**
 * Class represents a named list of orders.
 * Created by Eezo on 02.11.2016.
 */
public class OrderList {
    private String title;
    private List<Order> orders;

    public OrderList(String title, List<Order> orders) {
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

    public static OrderList generateDefaultOrderList(String title) {
        List<Order> orders = new ArrayList<>();
        for (int i = 0; i < 50; i++) {
            orders.add(Order.generateRandomOrder());
        }
        return new OrderList(title, orders);
    }

    @Override
    public String toString() {
        return title;
    }
}
