package edu.eezo.data;

import edu.eezo.MainGUI;

import java.util.Date;

/**
 * Class represents an order.
 * Created by Eezo on 02.11.2016.
 */
public class Order {
    /**
     * The timing of the order.<br/>
     * <i>ТЗ:</i> Час надходження замовлення.
     */
    private Date orderReceiptTime;
    private Customer customer;
    /**
     * From what point it is necessary to make the movement.<br/>
     * <i>ТЗ:</i> З якого пункту необхідно зробити переміщення.
     */
    private Place origin;
    /**
     * Up to what point it is necessary to make the movement.<br/>
     * <i>ТЗ:</i> До я кого пункту необхідно зробити переміщення.
     */
    private Place destination;
    /**
     * A weight of order in kilograms (kg) that need to be moved.<br/>
     * <i>ТЗ:</i> Вага замовлення.
     */
    private int weight; // kilograms
    /**
     * The maximum value that the customer can pay for transportation (in UAH).<br/>
     * <i>ТЗ:</i> Максимальна вартість, яку може заплатити клієнт.
     */
    private int maxPrice;
    /**
     * Time of the order (from the date of the order); in minutes.<br/>
     * <i>ТЗ:</i>Час на виконання замовлення (від моменту надходження замовлення).
     */
    private long deliveryDuration;
    /**
     * Penalties for late execution of the order (by default: 100 UAH / 1 hour).<br/>
     * <i>ТЗ:</i> Штрафні санкції за невчасне виконання замовлення (за замовчуванням: 100 грн / 1 год).
     */
    private int fine;

    /**
     * Default value for <code>fine</code>.
     */
    public static int DEFAULT_FINE = 100;

    Order() {
        this.orderReceiptTime = new Date(System.currentTimeMillis());
    }

    /**
     * Creates an order (short way).
     * @param customer orders customer
     * @param origin order origin
     * @param destination order destination
     * @param weight cargo weight (in kilograms)
     * @param priceForTon price per ton (in UAH)
     * @param deliveryDuration maximal duration of transportation (in minutes)
     */
    public Order(Customer customer, Place origin, Place destination, int weight, int priceForTon, long deliveryDuration) {
        this(new Date(System.currentTimeMillis()), customer, origin, destination, weight, (int) (priceForTon * weight / 1000.0), deliveryDuration, DEFAULT_FINE);
    }

    /**
     * Creates an order.
     * @param orderReceiptTime the timing of the order
     * @param customer orders customer
     * @param origin order origin
     * @param destination order destination
     * @param weight cargo weight (in kilograms)
     * @param maxPrice maximal price that customer can pay for transportation (in UAH)
     * @param deliveryDuration maximal duration of transportation (in minutes)
     * @param fine penalties for late execution of the order (in UAH)
     */
    public Order(Date orderReceiptTime, Customer customer, Place origin, Place destination, int weight, int maxPrice,
                 long deliveryDuration, int fine) {
        this.orderReceiptTime = orderReceiptTime;
        this.customer = customer;
        this.origin = origin;
        this.destination = destination;
        this.weight = weight;
        this.maxPrice = maxPrice;
        this.deliveryDuration = deliveryDuration;
        this.fine = fine;
    }

    public Date getOrderReceiptTime() {
        return orderReceiptTime;
    }

    public Customer getCustomer() {
        return customer;
    }

    public Place getOrigin() {
        return origin;
    }

    public Place getDestination() {
        return destination;
    }

    public int getWeight() {
        return weight;
    }

    public int getMaxPrice() {
        return maxPrice;
    }

    public long getDeliveryDuration() {
        return deliveryDuration;
    }

    public int getFine() {
        return fine;
    }

    public void setOrderReceiptTime(Date orderReceiptTime) {
        this.orderReceiptTime = orderReceiptTime;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public void setOrigin(Place origin) {
        this.origin = origin;
    }

    public void setDestination(Place destination) {
        this.destination = destination;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public void setMaxPrice(int maxPrice) {
        this.maxPrice = maxPrice;
    }

    public void setDeliveryDuration(long deliveryDuration) {
        this.deliveryDuration = deliveryDuration;
    }

    public void setFine(int fine) {
        this.fine = fine;
    }

    public static Order generateRandomOrder() {
        Date current = new Date(System.currentTimeMillis());
        long deliveryDuration = 7 * 24 * 60; // 1 week by default
        Place origin = Place.getRandomPlaceFromList(MainGUI.placeList);
        int weight = MainGUI.getRandomNumberInRange(1800, 8500);
        return new Order(current, MainGUI.customerList.get(MainGUI.getRandomNumberInRange(0, MainGUI.customerList.size())),
                origin, Place.getRandomPlaceFromListExclude(MainGUI.placeList, origin),
                weight, weight * 2, deliveryDuration, DEFAULT_FINE);
    }

    public static String[] getTableColumnsIdentifiers() {
        return new String[]{"№", "Customer", "Origin", "Destination", "Weight", "Max Price"};
    }

    public Object[] getTableRowData() {
        return new Object[]{0, customer, origin, destination, getReadableWeight(), getReadablePrice()};
    }

    private String getReadableWeight() {
        return weight / 1000.0 + " t";
    }

    private String getReadablePrice() {
        return maxPrice + " UAH";
    }
}
