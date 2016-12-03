package edu.eezo.saving;

import edu.eezo.MainGUI;
import edu.eezo.data.ITableViewable;
import edu.eezo.data.Order;
import edu.eezo.data.Place;

import java.util.List;

/**
 * Represents current saving table data in ne place.
 * <p>
 * Created by Eezo on 12.11.2016.
 */
public class SavingTable implements ITableViewable {

    // client orders data
    private Order[] clientOrderList;
    private long[] clientSavingDistances;

    // base orders data
    private Order[] baseOrderList;
    private long[] baseSavingDistances;

    // all client and base orders data
    private Order[] orderList;
    private long[] savingDistances;

    SavingTable(List<Order> orderList) {
        if (orderList == null || orderList.isEmpty()) {
            return;
        }

        this.orderList = new Order[orderList.size()];
        for (int i = 0; i < orderList.size(); i++) {
            this.orderList[i] = orderList.get(i);
        }

        List<Order> clientOrders = Order.getClientsOrderSublist(orderList);
        this.clientOrderList = new Order[clientOrders.size()];
        for (int i = 0; i < clientOrders.size(); i++) {
            this.clientOrderList[i] = clientOrders.get(i);
        }

        List<Order> baseOrders = Order.getBaseOrderSublist(orderList);
        this.baseOrderList = new Order[baseOrders.size()];
        for (int i = 0; i < baseOrders.size(); i++) {
            this.baseOrderList[i] = baseOrders.get(i);
        }

        this.savingDistances = new long[orderList.size()];
        for (int i = 0; i < savingDistances.length; i++) {
            savingDistances[i] = calculateSavingDistance(this.orderList[i]);
        }

        this.clientSavingDistances = new long[clientOrders.size()];
        for (int i = 0; i < clientSavingDistances.length; i++) {
            clientSavingDistances[i] = calculateSavingDistance(this.clientOrderList[i]);
        }

        this.baseSavingDistances = new long[baseOrders.size()];
        for (int i = 0; i < baseSavingDistances.length; i++) {
            baseSavingDistances[i] = calculateSavingDistance(this.baseOrderList[i]);
        }
    }

    /**
     * Sorting saving table rows with bubbles sorting algorithm.
     */
    void sort() {
        for (int i = 0; i < clientSavingDistances.length - 1; i++) {
            boolean swapped = false;

            for (int j = 0; j < clientSavingDistances.length - i - 1; j++) {
                if (clientSavingDistances[j] < clientSavingDistances[j + 1]) { // descending
                    long tmp = clientSavingDistances[j];
                    clientSavingDistances[j] = clientSavingDistances[j + 1];
                    clientSavingDistances[j + 1] = tmp;

                    Order tmp2 = clientOrderList[j];
                    clientOrderList[j] = clientOrderList[j + 1];
                    clientOrderList[j + 1] = tmp2;

                    swapped = true;
                }
            }

            if (!swapped)
                break;
        }
    }

    /**
     * Calculates saving distance between two places in order.<br>
     * Saving distance calculates with formula: <code>Sd = Soh + Sdh - Sod</code>,<br>
     * where:<ul>
     * <li><code>Sd</code> - result saving distance</li>
     * <li><code>Soh</code> - distance between origin and headquarter</li>
     * <li><code>Sdh</code> - distance between destination and headquarter</li>
     * <li><code>Sod</code> - distance between origin and destination</li>
     * </ul>
     * Headquarter is {@link MainGUI#myHeadquarter}.
     *
     * @param order an order with origin and destination
     * @return saving distance for two places
     */
    private long calculateSavingDistance(Order order) {
        long Sod = Place.getDistanceBetweenPlaces(order.getOrigin(), order.getDestination());
        long Soh = Place.getDistanceBetweenPlaces(order.getOrigin(), MainGUI.myHeadquarter);
        long Sdh = Place.getDistanceBetweenPlaces(order.getDestination(), MainGUI.myHeadquarter);

        return (Soh + Sdh - Sod);
    }

    public Order[] getOrderList() {
        return orderList;
    }

    public long[] getSavingDistances() {
        return savingDistances;
    }

    public Order[] getClientOrderList() {
        return clientOrderList;
    }

    public long[] getClientSavingDistances() {
        return clientSavingDistances;
    }

    /**
     * Help method for <code>Object[] getTableRowData()</code>.
     *
     * @return an array of columns identifiers
     */
    public static String[] getTableColumnsIdentifiers() {
        return new String[]{"Order #", "Origin", "Destination", "Saving Distance"};
    }

    @Override
    public Object[][] getTableRowData() {
        Object[][] tableData = new Object[clientOrderList.length][4];

        for (int i = 0; i < tableData.length; i++) {
            tableData[i] = new Object[]{clientOrderList[i].getId(), clientOrderList[i].getOrigin(), clientOrderList[i].getDestination(), clientSavingDistances[i]};
        }

        return tableData;
    }
}