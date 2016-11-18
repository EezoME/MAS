package edu.eezo.saving;


import edu.eezo.data.Order;

import java.util.List;

/**
 * Created by Eezo on 06.11.2016.
 */
public class SavingAlgorithm {
    private SavingTable savingTable;
    private Route globalRoute;

    public SavingAlgorithm(List<Order> orderList) {
        this.savingTable = new SavingTable(orderList);
    }

    public void runAlgorithm() {
        savingTable.sort();

        // build global route
        globalRoute = new Route();
        globalRoute.buildGlobalRoute(savingTable);

        // build local routes
    }

    public SavingTable getSavingTable() {
        return savingTable;
    }

    public Route getGlobalRoute() {
        return globalRoute;
    }
}
