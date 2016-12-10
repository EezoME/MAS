package edu.eezo.saving;

import edu.eezo.data.Order;
import edu.eezo.data.Place;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents local route (for one vehicle).
 * Created by Eezo on 10.12.2016.
 */
public class LocalRoute {

    /**
     * A list of orders gor this route.
     */
    private List<Order> routeOrders;

    /**
     * A list of places that vehicle must visited on route.
     */
    private List<Place> routePlaces;

    public LocalRoute() {
        routeOrders = new ArrayList<>();
        routePlaces = new ArrayList<>();
    }

    /**
     * Adds a new order to order list and new places to place list.
     *
     * @param order a new order
     */
    public void addOrderToRoute(Order order) {
        routeOrders.add(order);
        routePlaces.add(order.getOrigin());
        routePlaces.add(order.getDestination());
    }

    /**
     * Makes route to right form (deletes duplicated places,
     * adds headquarter location on the begin and the end of the route).
     *
     * @param headquarter current headquarter location
     */
    public void finalizeRoute(Place headquarter) {
        for (int i = 0; i < routePlaces.size() - 1; i++) {
            if (routePlaces.get(i).equals(routePlaces.get(i + 1))) routePlaces.remove(i + 1);
        }

        if (!routePlaces.get(0).equals(headquarter)) routePlaces.add(0, headquarter);
        if (!Route.getLastPlaceFromList(routePlaces).equals(headquarter)) routePlaces.add(headquarter);
    }

    public boolean isEmpty() {
        return (routeOrders.isEmpty() || routePlaces.isEmpty());
    }

    /**
     * Returns a string representation of places that route includes.
     *
     * @return a string of places ID
     */
    public String getRoutePathInId() {
        String path = "";

        for (int i = 0; i < routePlaces.size(); i++) {
            path += routePlaces.get(i).getId() + " - ";
        }

        return path.substring(0, path.length() - 3);
    }

    public List<Order> getRouteOrders() {
        return routeOrders;
    }
}
