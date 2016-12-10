package edu.eezo.saving;

import edu.eezo.MainGUI;
import edu.eezo.data.Order;
import edu.eezo.data.Place;

import java.util.List;

/**
 * Utils for saving table.
 * <p>
 * Created by Eezo on 12.11.2016.
 */
public class SavingTable {

    /**
     * Saving table column identifiers.
     * @return an array of columns identifiers
     */
    public static String[] getTableColumnsIdentifiers() {
        return new String[]{"Order #", "Origin", "Destination", "Saving Distance"};
    }

    /**
     * Sorting saving table rows with bubbles sorting algorithm.
     * @return saving distance for sorted list
     */
    public static long[] sort(List<Order> orderList) {
        if (orderList == null || orderList.isEmpty()) {
            return new long[0];
        }

        long[] distances = getSavingDistances(orderList);

        for (int i = 0; i < distances.length - 1; i++) {
            boolean swapped = false;

            for (int j = 0; j < distances.length - i - 1; j++) {
                if (distances[j] < distances[j + 1]) { // descending
                    long tmp = distances[j];
                    distances[j] = distances[j + 1];
                    distances[j + 1] = tmp;

                    Order tmp2 = orderList.get(j);
                    orderList.set(j, orderList.get(j + 1));
                    orderList.set(j + 1, tmp2);

                    swapped = true;
                }
            }

            if (!swapped)
                break;
        }

        return distances;
    }

    private static long[] getSavingDistances(List<Order> orderList) {
        long[] distances = new long[orderList.size()];

        for (int i = 0; i < distances.length; i++) {
            distances[i] = calculateSavingDistance(orderList.get(i));
        }

        return distances;
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
    private static long calculateSavingDistance(Order order) {
        long Sod = Place.getDistanceBetweenPlaces(order.getOrigin(), order.getDestination());
        long Soh = Place.getDistanceBetweenPlaces(order.getOrigin(), MainGUI.myHeadquarter);
        long Sdh = Place.getDistanceBetweenPlaces(order.getDestination(), MainGUI.myHeadquarter);

        return (Soh + Sdh - Sod);
    }
}