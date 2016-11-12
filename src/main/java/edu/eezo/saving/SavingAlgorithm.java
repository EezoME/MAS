package edu.eezo.saving;

import edu.eezo.MainGUI;
import edu.eezo.data.OrderList;
import edu.eezo.data.Place;
import edu.eezo.gmap.GMDAPIUtility;

import javax.swing.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by Eezo on 06.11.2016.
 */
public class SavingAlgorithm {
    /*private List<SavingRowAtom> list;

    public SavingAlgorithm(OrderList orderList) {
        if (orderList == null || orderList.getOrders().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Algorithm needs data for computing.");
            return;
        }
        list = new ArrayList<>();
        for (int i = 0; i < orderList.getOrders().size(); i++) {
            Place origin = orderList.getOrders().get(i).getOrigin();
            Place destination = orderList.getOrders().get(i).getDestination();
            list.add(new SavingRowAtom(i, origin, destination, getSavingDistance(origin, destination, MainGUI.myHeadquarter)));
        }
    }

    public void runAlgorithm() {
        Collections.sort(list);
        Collections.reverse(list);
        makeGlobalRoute();
        // make local routes
    }

    public static String[] getTableColumnsIdentifiers() {
        return new String[]{"Order #", "Origin", "Destination", "Saving Distance"};
    }

    public Object[][] getTableRowsData() {
        Object[][] rowsData = new Object[list.size()][list.get(0).getTableRowData().length];
        for (int i = 0; i < rowsData.length; i++) {
            rowsData[i] = list.get(i).getTableRowData();
        }
        return rowsData;
    }

    public static long getSavingDistance(Place origin, Place destination, Place headquarter) {
        long s_od = GMDAPIUtility.getDistance(origin, destination);
        long s_oh = GMDAPIUtility.getDistance(origin, headquarter);
        long s_dh = GMDAPIUtility.getDistance(destination, headquarter);
        return (s_oh + s_dh - s_od);
    }

    private void makeGlobalRoute() {
        List<Place> globalRoute = new ArrayList<>();
        globalRoute.add(MainGUI.myHeadquarter);
        while (checkListForIsOnRoute()) {
            for (SavingRowAtom element : list) {
                if (element.isOnRoute) continue;
                // check list for previous, add other if match found
                // else add tops
                Place otherPlace = checkForPlace(globalRoute.get(globalRoute.size() - 1));
                if (otherPlace != null) {
                    globalRoute.add(otherPlace);
                } else {
                    globalRoute.add(getTopsPlaceInList());
                }
                markListAtomsInAllOccurrences(globalRoute.get(globalRoute.size() - 1));
            }
        }
    }

    private Place checkForPlace(Place place) {
        for (SavingRowAtom element : list) {
            if (element.isOnRoute) continue;
            if (element.origin.equals(place)) {
                element.isOnRoute = true;
                return element.destination;
            }
            if (element.destination.equals(place)) {
                element.isOnRoute = true;
                return element.origin;
            }
        }
        return null;
    }

    private void markListAtomsInAllOccurrences(Place place) {
        for (SavingRowAtom element : list) {
            if (place.equals(element.origin) || place.equals(element.destination)) {
                element.isOnRoute = true;
            }
        }
    }

    private boolean checkListForIsOnRoute() {
        if (list == null || list.isEmpty()) {
            return false;
        }
        for (SavingRowAtom element : list) {
            if (!element.isOnRoute) {
                return true;
            }
        }
        return false;
    }

    private Place getTopsPlaceInList() {
        for (SavingRowAtom element : list) {
            if (!element.isOnRoute) {
                return element.origin;
            }
        }
        return null;
    }

    class SavingRowAtom implements Comparable<SavingRowAtom> {
        private int orderNumber;
        private Place origin;
        private Place destination;
        /**
         * Saving distance between origin and destination (in meters).<br/>
         * Calculates by formula: <code>Soh + Sdh - Sod</code>,<br/>
         * where <ul>
         * <li><code>Soh</code> - is a distance between origin and headquarter</li>
         * <li><code>Sdh</code> - is a distance between destination and headquarter</li>
         * <li><code>Sod</code> - is a distance between origin and destination</li>
         * </ul>
         * /
        private long distance;
        private boolean isOnRoute;

        public SavingRowAtom(int orderNumber, Place origin, Place destination, long distance) {
            this.orderNumber = orderNumber;
            this.origin = origin;
            this.destination = destination;
            this.distance = distance;
            this.isOnRoute = false;
        }

        public Object[] getTableRowData() {
            return new Object[]{orderNumber, origin, destination, getReadableDistance()};
        }

        private String getReadableDistance() {
            return distance / 1000.0 + " km";
        }

        @Override
        public int compareTo(SavingRowAtom o) {
            return (int) (distance - o.distance);
        }
    }*/
}
