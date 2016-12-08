package edu.eezo.thread;

import edu.eezo.MainGUI;
import edu.eezo.data.Vehicle;
import edu.eezo.saving.Route;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.List;

/**
 * Created by Eezo on 08.12.2016.
 */
public class AgentThread extends Thread {

    private boolean isRunning;

    private List<Vehicle> vehicleList;

    private JTable tableAgentsStatus;

    private AgentActual agentActual;

    public AgentThread(List<Vehicle> vehicleList, List<Route.LocalRoute> localRoutes, JTable table, AgentActual agentActual) {
        this.tableAgentsStatus = table;
        this.agentActual = agentActual;
        this.vehicleList = vehicleList;
        for (int i = 0; i < localRoutes.size(); i++) {
            if (i < vehicleList.size()){
                vehicleList.get(i).setState(Vehicle.State.ON_THE_ROUTE);
                vehicleList.get(i).setCurrentRoute(MainGUI.orderList.get(i).getOrigin() + " - " +
                        MainGUI.orderList.get(i).getDestination());
                vehicleList.get(i).setRouteCompletedLength(0.0d);
                vehicleList.get(i).setTimeOnRoute(0.0d);
            }
        }
        this.isRunning = true;
    }

    @Override
    public void run() {
        while (isRunning) {
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
        }
    }

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
