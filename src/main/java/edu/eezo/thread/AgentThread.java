package edu.eezo.thread;

import edu.eezo.MainGUI;
import edu.eezo.data.Order;
import edu.eezo.data.Place;
import edu.eezo.data.Vehicle;
import edu.eezo.saving.LocalRoute;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.List;

/**
 * Run and update tools for agent.
 * Created by Eezo on 08.12.2016.
 */
public class AgentThread extends Thread {

    private boolean isRunning;

    private List<Vehicle> vehicleList;

    private JTable tableAgentsStatus;

    private AgentActual agentActual;


    public AgentThread(List<Vehicle> vehicleList, List<LocalRoute> localRoutes, JTable table, AgentActual agentActual) {
        this.tableAgentsStatus = table;
        this.agentActual = agentActual;
        this.vehicleList = vehicleList;
        for (int i = 0; i < localRoutes.size(); i++) {
            if (i < vehicleList.size()) {
                vehicleList.get(i).setState(Vehicle.State.ON_THE_ROUTE);
                vehicleList.get(i).setCurrentRoute(localRoutes.get(i).getRouteOrders().get(0).getOrigin() + " - " +
                        localRoutes.get(i).getRouteOrders().get(localRoutes.get(i).getRouteOrders().size() - 1).getDestination());
                vehicleList.get(i).setRouteCompletedLength(0.0d);
                vehicleList.get(i).setTimeOnRoute(0.0d);
            }
        }
        this.isRunning = true;
    }


    @Override
    public void run() {
        int counter = 0;
        boolean added = false;
        while (isRunning) {
            if (checkOrderList()) {
                agentActual.labelNewOrders.setText("New order has been received.");
                for (int i = 0; i < vehicleList.size(); i++) {
                    agentActual.tableNewOrders.setValueAt(vehicleList.get(i).getIdentification(), i, 0);
                    agentActual.tableNewOrders.setValueAt("waiting", i, 1);
                }
                added = true;
            }

            SwingUtilities.invokeLater(new Runnable() {
                @Override
                public void run() {
                    for (int i = 0; i < vehicleList.size(); i++) {
                        if (vehicleList.get(i).getState().equals(Vehicle.State.ON_THE_ROUTE)) {
                            vehicleList.get(i).setRouteCompletedLength(vehicleList.get(i).getRouteCompletedLength() + 10); // 60 km/min
                            vehicleList.get(i).setTimeOnRoute(vehicleList.get(i).getTimeOnRoute() + 60);
                        }
                    }

                    updateAgentTable(tableAgentsStatus, agentActual.getTableRowData());
                }
            });

            try {
                Thread.sleep(1000L);
            } catch (InterruptedException e) {
                JOptionPane.showMessageDialog(null, "Agent Timing Error.");
            }

            if (added) {
                for (int i = 0; i < vehicleList.size(); i++) {
                    agentActual.tableNewOrders.setValueAt(vehicleList.get(i).addMoreFreight(MainGUI.orderList.get(MainGUI.orderList.size() - 1).getFreightVolume()), i, 1);
                    added = false;
                }
            }

            if (counter == 6) {
                MainGUI.orderList.add(new Order(null, false, Place.getRandomPlaceFromList(MainGUI.placeList),
                        Place.getRandomPlaceFromList(MainGUI.placeList), 0, 0, 0));
            }

            counter++;
        }
    }

    static int size = MainGUI.orderList.size();

    /**
     * Inspects if orders list size changed.
     *
     * @return <b>true</b> if it is, <b>false</b> - otherwise
     */
    private static boolean checkOrderList() {
        if (size != MainGUI.orderList.size()) {
            size = MainGUI.orderList.size();
            return true;
        }

        return false;
    }

    /**
     * Updates specified table with specified column data.
     *
     * @param table specified table
     * @param rows  specified data
     */
    private void updateAgentTable(JTable table, Object[][] rows) {
        DefaultTableModel model = (DefaultTableModel) table.getModel();

        while (model.getRowCount() > 0) {
            model.removeRow(0);
        }

        for (Object[] row : rows) {
            model.addRow(row);
        }
    }

    public void setRunning(boolean isRunning) {
        this.isRunning = isRunning;
    }

}
