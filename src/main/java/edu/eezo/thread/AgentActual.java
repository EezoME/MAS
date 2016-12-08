package edu.eezo.thread;

import edu.eezo.MainGUI;
import edu.eezo.data.ITableViewable;
import edu.eezo.saving.Route;

import javax.swing.*;
import java.util.List;

/**
 * Created by Eezo on 08.12.2016.
 */
public class AgentActual implements ITableViewable {

    private AgentThread agentThread;

    public AgentActual(List<Route.LocalRoute> localRoutes, JTable table){
        agentThread = new AgentThread(MainGUI.vehicleList, localRoutes, table, this);
        agentThread.start();
    }

    public void stopMAS(){
        agentThread.setRunning(false);
    }

    /**
     * Help method for <code>Object[] getTableRowData()</code>.
     *
     * @return an array of columns identifiers
     */
    public static String[] getTableColumnsIdentifiers() {
        return new String[]{"Vehicle ID", "Current State", "Current Route", "Passed (km)", "Time On Route", "Current Freight"};
    }


    @Override
    public Object[][] getTableRowData() {
        Object[][] rowData = new Object[MainGUI.vehicleList.size()][6];

        for (int i = 0; i < rowData.length; i++) {
            rowData[i] = MainGUI.vehicleList.get(i).getTableRowActualData();
        }

        return rowData;
    }
}
