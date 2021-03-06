package edu.eezo.thread;

import edu.eezo.MainGUI;
import edu.eezo.data.ITableViewable;
import edu.eezo.saving.LocalRoute;

import javax.swing.*;
import java.util.List;

/**
 * Class for agent actual data.
 * Created by Eezo on 08.12.2016.
 */
public class AgentActual implements ITableViewable {

    private AgentThread agentThread;

    JTable tableNewOrders;

    JLabel labelNewOrders;


    public AgentActual(List<LocalRoute> localRoutes, JTable table, JTable tableNewOrders, JLabel labelNewOrders) {
        this.tableNewOrders = tableNewOrders;
        this.labelNewOrders = labelNewOrders;

        agentThread = new AgentThread(MainGUI.vehicleList, localRoutes, table, this);
        agentThread.start();
    }

    /**
     * Sends signal to stop multi-agent thread.
     */
    public void stopMAS() {
        agentThread.setRunning(false);
    }

    /**
     * Help method for <code>Object[] getTableRowData()</code>.
     *
     * @return an array of columns identifiers
     */
    public static String[] getTableColumnsIdentifiers() {
        return new String[]{"Agent", "Current State", "Current Route", "Passed (km)", "Time On Route", "Current Freight"};
    }


    @Override
    public Object[][] getTableRowData() {
        Object[][] rowData = new Object[MainGUI.vehicleList.size()][6];

        for (int i = 0; i < rowData.length; i++) {
            rowData[i] = MainGUI.vehicleList.get(i).getTableRowActualData();
        }

        return rowData;
    }

    /**
     * @return column identifiers for new orders table
     */
    public static String[] getTableColumnsIdentifiersForNewOrder() {
        return new String[]{"Agent", "Can accept"};
    }
}
