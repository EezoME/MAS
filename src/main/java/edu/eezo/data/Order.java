package edu.eezo.data;

import edu.eezo.MainGUI;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Class represents an order.
 * Created by Eezo on 02.11.2016.
 */
public class Order implements ITableViewable {
    /**
     * Default value for <code>fine</code>.
     */
    public static int defaultFine = 100;
    private static int orderCounter = 0;

    private int id;
    /**
     * The timing of the order.<br/>
     * <i>ТЗ:</i> Час надходження замовлення.
     */
    private Date orderReceiptTime;
    /**
     * The one who ordered.
     */
    private Client client;
    /**
     * Indicates if this order from the base (false for clients orders).
     */
    private boolean isBases;
    /**
     * From what location it is necessary to make the movement.<br/>
     * <i>ТЗ:</i> З якого пункту необхідно зробити переміщення.
     */
    private Place origin;
    /**
     * Up to what location it is necessary to make the movement.<br/>
     * <i>ТЗ:</i> До я кого пункту необхідно зробити переміщення.
     */
    private Place destination;
    /**
     * A freight of order in tons (tn) that need to be moved.<br/>
     * <i>ТЗ:</i> Вага замовлення.
     */
    private double freightVolume;
    /**
     * Time of the order (from the date of the order); in hours.<br/>
     * <i>ТЗ:</i>Час на виконання замовлення (від моменту надходження замовлення).
     */
    private double timeOfDelivery;
    /**
     * The maximum value that client can pay for transportation (in UAH).<br/>
     * <i>ТЗ:</i> Максимальна вартість, яку може заплатити клієнт.
     */
    private int maxCost;
    /**
     * Penalties for late execution of the order (by default: 100 UAH / 1 hour).<br/>
     * <i>ТЗ:</i> Штрафні санкції за невчасне виконання замовлення (за замовчуванням: 100 грн / 1 год).
     */
    private int fine;


    Order() {
        this.id = orderCounter++;
        this.orderReceiptTime = new Date(System.currentTimeMillis());
    }

    /**
     * Creates an order (short way).
     *
     * @param client         orders client
     * @param isBases        is order form base
     * @param origin         orders origin
     * @param destination    orders destination
     * @param freightVolume  orders freight volume (in tons)
     * @param timeOfDelivery maximal duration of transportation (in hrs)
     */
    public Order(Client client, boolean isBases, Place origin, Place destination, int freightVolume, int priceForTon, double timeOfDelivery) {
        this(new Date(System.currentTimeMillis()), client, isBases, origin, destination, freightVolume, (int) (priceForTon * freightVolume / 1000.0), timeOfDelivery, defaultFine);
    }

    /**
     * Creates an order.
     *
     * @param orderReceiptTime the timing of the order
     * @param client           orders client
     * @param isBases          is order form base
     * @param origin           orders origin
     * @param destination      orders destination
     * @param freightVolume    orders freight volume (in tons)
     * @param maxCost          maximal cost that client can pay for transportation (in UAH)
     * @param timeOfDelivery   maximal duration of transportation (in hrs)
     * @param fine             penalties for late execution of the order (in UAH)
     */
    public Order(Date orderReceiptTime, Client client, boolean isBases, Place origin, Place destination, double freightVolume, int maxCost,
                 double timeOfDelivery, int fine) {
        this.id = orderCounter++;
        this.orderReceiptTime = orderReceiptTime;
        this.client = client;
        this.isBases = isBases;
        this.origin = origin;
        this.destination = destination;
        this.freightVolume = freightVolume;
        this.maxCost = maxCost;
        this.timeOfDelivery = timeOfDelivery;
        this.fine = fine;
    }

    /**
     * Returns a sublist only with clients orders.
     *
     * @param orderList general orders list
     * @return orders sublist
     */
    public static List<Order> getClientsOrderSublist(List<Order> orderList) {
        List<Order> list = new ArrayList<>();
        for (Order order : orderList) {
            if (!order.isBases) {
                list.add(order);
            }
        }
        return list;
    }

    /**
     * Returns a sublist only with base orders.
     *
     * @param orderList general orders list
     * @return orders sublist
     */
    public static List<Order> getBaseOrderSublist(List<Order> orderList) {
        List<Order> list = new ArrayList<>();
        for (Order order : orderList) {
            if (order.isBases) {
                list.add(order);
            }
        }
        return list;
    }

    public static Order getOrderById(List<Order> orderList, int id) {
        for (Order order : orderList) {
            if (order.id == id) {
                return order;
            }
        }
        return null;
    }

    public static Order generateDefaultOrder(boolean isBased) {
        return new Order(null, isBased, null, null, 0, 0, 0.0d);
    }

    /**
     * Generates an order list that includes client and base orders.
     *
     * @param clientOrdersCount number of client orders in list
     * @param baseOrdersCount   number of base orders in list
     * @return a list of orders
     */
    public static List<Order> generateRandomOrderList(int clientOrdersCount, int baseOrdersCount) {
        List<Order> orders = generateRandomBaseOrderList(baseOrdersCount);
        orders.addAll(generateRandomClientOrderList(clientOrdersCount));
        return orders;
    }

    /**
     * Generates an order list that includes only client orders.
     *
     * @param count number of orders in list
     * @return a list of orders from clients
     */
    public static List<Order> generateRandomClientOrderList(int count) {
        if (count > 0) {
            List<Order> clientOrders = new ArrayList<>();
            for (int i = 0; i < count; i++) {
                Place origin = Place.getRandomPlaceFromListExclude(MainGUI.placeList, MainGUI.myHeadquarter);
                Place destination;
                while ((destination = Place.getRandomPlaceFromListExclude(MainGUI.placeList, origin)).equals(MainGUI.myHeadquarter)); // to avoid myHeadquarter here
                int cost = (int) (Math.round(12.0 * Place.getDistanceBetweenPlaces(origin, destination) / 1000)); // 12 UAH / 1 km
                clientOrders.add(new Order(new Date(System.currentTimeMillis() - 3 * 24 * 60 * 60 * 1000), null, false, origin,
                        destination, 15.0, cost, 10 * 24, defaultFine));
            }
            return clientOrders;
        } else {
            return new ArrayList<>();
        }
    }

    /**
     * Generates an order list that includes only base orders.
     *
     * @param count number of orders in list
     * @return a list of orders from base
     */
    public static List<Order> generateRandomBaseOrderList(int count) {
        if (count > 0) {
            List<Order> baseOrders = new ArrayList<>();
            for (int i = 0; i < count; i++) {
                Place origin = MainGUI.myHeadquarter;
                Place destination = Place.getRandomPlaceFromListExclude(MainGUI.placeList, MainGUI.myHeadquarter);
                int cost = (int) (Math.round(12.0 * Place.getDistanceBetweenPlaces(origin, destination) / 1000)); // 12 UAH / 1 km
                baseOrders.add(new Order(new Date(System.currentTimeMillis() - 3 * 24 * 60 * 60 * 1000), null, true, origin,
                        destination, 15.0, cost, 10 * 24, defaultFine));
            }
            return baseOrders;
        } else {
            return new ArrayList<>();
        }
    }

    public static String[] getTableColumnsIdentifiers() {
        return new String[]{"ID", "Timing", "Client", "Origin", "Destination", "Freight Volume", "Time for delivery",
                "Max Cost", "Fine"};
    }


    public Object[] getTableRowData() {
        return new Object[]{id, orderReceiptTime, client, origin, destination, getReadableWeight(), getReadableDeliveryTime(),
                getReadableCost(maxCost), getReadableCost(fine)};
    }

    private String getReadableWeight() {
        return freightVolume + " tn";
    }

    private String getReadableDeliveryTime() {
        return timeOfDelivery + " hrs";
    }

    private String getReadableCost(int cost) {
        return cost + " UAH";
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getOrderReceiptTime() {
        return orderReceiptTime;
    }

    public Client getClient() {
        return client;
    }

    public boolean isBases() {
        return isBases;
    }

    public Place getOrigin() {
        return origin;
    }

    public Place getDestination() {
        return destination;
    }

    public double getFreightVolume() {
        return freightVolume;
    }

    public int getMaxCost() {
        return maxCost;
    }

    public double getTimeOfDelivery() {
        return timeOfDelivery;
    }

    public int getFine() {
        return fine;
    }

    public void setOrderReceiptTime(Date orderReceiptTime) {
        this.orderReceiptTime = orderReceiptTime;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public void setBases(boolean bases) {
        isBases = bases;
    }

    public void setOrigin(Place origin) {
        this.origin = origin;
    }

    public void setDestination(Place destination) {
        this.destination = destination;
    }

    public void setFreightVolume(double freightVolume) {
        this.freightVolume = freightVolume;
    }

    public void setMaxCost(int maxCost) {
        this.maxCost = maxCost;
    }

    public void setTimeOfDelivery(double timeOfDelivery) {
        this.timeOfDelivery = timeOfDelivery;
    }

    public void setFine(int fine) {
        this.fine = fine;
    }

    @Override
    public String toString() {
        return id + "";
    }
}
