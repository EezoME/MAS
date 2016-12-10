package edu.eezo;

import edu.eezo.data.*;
import edu.eezo.saving.LocalRoute;
import edu.eezo.saving.Route;
import edu.eezo.saving.SavingTable;
import edu.eezo.thread.*;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.List;

/**
 * Main GUI for multi-agent system for commercial transportation (education version).
 * Created by Eezo on 03.11.2016.
 */
public class MainGUI extends JFrame {
    private JPanel rootPanel;
    private JTabbedPane tabbedPane1;
    private JTable tableClientOrdersList;
    private JButton buttonAddClientOrder;
    private JButton buttonDeleteClientOrder;
    private JButton buttonEditClientOrder;
    private JTable tableBasesOrderList;
    private JButton buttonAddBaseOrder;
    private JButton buttonEditBaseOrder;
    private JButton buttonDeleteBaseOrder;
    private JTable tableVehiclesList;
    private JButton buttonAddVehicle;
    private JButton buttonEditVehicle;
    private JButton buttonRemoveVehicle;
    private JButton buttonRunAlgorithm;
    private JTable tableSaving;
    private JLabel labelGlobalRoute;
    private JButton buttonBuildLocalRoutes;
    private JButton buttonData;
    private JButton buttonNextStep;
    private JTextArea textArea1;
    private JLabel labelTime;
    private JLabel labelDate;
    private JButton buttonStartMAS;
    private JTable tableAgentsStatus;
    private JButton buttonStopAgents;
    private JLabel labelNewOrderAlert;
    private JTable tableNewOders;

    /**
     * Locale for date parsing.
     */
    public static Locale locale = Locale.ENGLISH;

    /**
     * A list of locations (by default, Ukrainian cities).
     */
    public static java.util.List<Place> placeList;

    /**
     * Distances between places (to avoid multiple requests to GoogleMaps service on each start).
     */
    public static long[][] distancesMatrix;

    /**
     * A list of clients.
     */
    public static java.util.List<Client> clientList;

    /**
     * Order lists (include clients and bases orders).
     */
    public static java.util.List<Order> orderList;

    /**
     * A list of company vehicles (trucks).
     */
    public static java.util.List<Vehicle> vehicleList;

    /**
     * Current headquarter location (for saving algorithm).
     * By default sets to "Николаев".
     */
    public static Place myHeadquarter;

    private Route globalRoute;

    private List<LocalRoute> localRoutes;

    private TimerThread timerThread;

    private AgentActual agentActual;


    public MainGUI() {
        super("MAS");
        setSize(900, 700);
        setContentPane(rootPanel);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setVisible(true);

        dataInitialization();
        formInitialization();



        /* -------- LISTENERS OF TAB 1 -------- */



        /* CLIENT ORDER LISTENERS */
        buttonAddClientOrder.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                orderList.add(Order.generateDefaultOrder(false));
                runOrderGUI(orderList.get(orderList.size() - 1));
                refreshOrdersTables();
            }
        });

        buttonEditClientOrder.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!checkTableSelection(tableClientOrdersList)) {
                    JOptionPane.showMessageDialog(null, "Select an order in the table");
                    return;
                }

                runOrderGUI(Order.getOrderById(orderList, (int)
                        tableClientOrdersList.getValueAt(tableClientOrdersList.getSelectedRow(), 0)));

                refreshOrdersTables();
            }
        });

        buttonDeleteClientOrder.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!checkTableSelection(tableClientOrdersList)) {
                    JOptionPane.showMessageDialog(null, "Select an order in the table");
                    return;
                }

                if (JOptionPane.showConfirmDialog(null, "Are you sure you want to delete this order?") == JOptionPane.OK_OPTION) {
                    orderList.remove(Order.getOrderById(orderList, (int) tableClientOrdersList.getValueAt(tableClientOrdersList.getSelectedRow(),
                            0)));
                }

                fillTableWithData(tableClientOrdersList, Order.getClientsOrderSublist(orderList));
            }
        });

        /* BASE ORDER LISTENERS */
        buttonAddBaseOrder.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                orderList.add(Order.generateDefaultOrder(true));
                runOrderGUI(orderList.get(orderList.size() - 1));
                refreshOrdersTables();
            }
        });

        buttonEditBaseOrder.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!checkTableSelection(tableBasesOrderList)) {
                    JOptionPane.showMessageDialog(null, "Select an order in the table");
                    return;
                }

                runOrderGUI(Order.getOrderById(orderList,
                        (int) tableBasesOrderList.getValueAt(tableBasesOrderList.getSelectedRow(), 0)));

                refreshOrdersTables();
            }
        });

        buttonDeleteBaseOrder.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!checkTableSelection(tableBasesOrderList)) {
                    JOptionPane.showMessageDialog(null, "Select an order in the table");
                    return;
                }

                if (JOptionPane.showConfirmDialog(null, "Are you sure you want to delete this order?") == JOptionPane.OK_OPTION) {
                    orderList.remove(Order.getOrderById(orderList,
                            (int) tableBasesOrderList.getValueAt(tableBasesOrderList.getSelectedRow(), 0)));
                }

                fillTableWithData(tableBasesOrderList, Order.getBaseOrderSublist(orderList));
            }
        });

        /* VEHICLE LISTENERS */
        buttonAddVehicle.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                vehicleList.add(Vehicle.generateDefaultVehicle());
                runVehicleGUI(vehicleList.get(vehicleList.size() - 1));
                fillTableWithData(tableVehiclesList, vehicleList);
            }
        });

        buttonEditVehicle.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!checkTableSelection(tableVehiclesList)) {
                    JOptionPane.showMessageDialog(null, "Select a vehicle in the table");
                    return;
                }

                runVehicleGUI(vehicleList.get(tableVehiclesList.getSelectedRow()));
                fillTableWithData(tableVehiclesList, vehicleList);
            }
        });

        buttonRemoveVehicle.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!checkTableSelection(tableVehiclesList)) {
                    JOptionPane.showMessageDialog(null, "Select a vehicle in the table");
                    return;
                }

                if (JOptionPane.showConfirmDialog(null, "Are you sure you want to delete this order?") == JOptionPane.OK_OPTION) {
                    vehicleList.remove(tableVehiclesList.getSelectedRow());
                }

                fillTableWithData(tableVehiclesList, vehicleList);
            }
        });

        /* OTHER LISTENERS */
        buttonData.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                SettingsGUI.main(null);
                fillTableWithData(tableClientOrdersList, Order.getClientsOrderSublist(orderList));
                fillTableWithData(tableBasesOrderList, Order.getBaseOrderSublist(orderList));
            }
        });

        buttonNextStep.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!preSavingAlgorithmChecks()) {
                    return;
                }

                tabbedPane1.setSelectedIndex(1);
            }
        });



        /* -------- LISTENERS OF TAB 2 -------- */


        buttonRunAlgorithm.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!preSavingAlgorithmChecks()) {
                    return;
                }

                globalRoute = new Route(myHeadquarter);
                localRoutes = null;

                globalRoute.makeGlobalRoute(Order.getClientsOrderSublist(orderList));
                globalRoute.finalizeRoute();

                fillTableWithDataArrays(tableSaving, globalRoute.getTableData());
                labelGlobalRoute.setText(globalRoute.getRoutePathInId());
            }
        });

        buttonBuildLocalRoutes.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (globalRoute == null) {
                    JOptionPane.showMessageDialog(null, "Run algorithm first!");
                    return;
                }

                localRoutes = globalRoute.makeLocalRoutes(vehicleList);

                textArea1.setText("");

                for (LocalRoute localRoute : localRoutes) {
                    localRoute.finalizeRoute(myHeadquarter);
                    textArea1.append("Route: " + localRoute.getRoutePathInId() + "\n");
                }
            }
        });

        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent event) {
                exitProcedure();
            }
        });

        timerThread = new TimerThread(labelDate, labelTime);
        timerThread.start();

        buttonStartMAS.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                agentActual = new AgentActual(localRoutes, tableAgentsStatus, tableNewOders, labelNewOrderAlert);
                fillTableWithDataArrays(tableAgentsStatus, agentActual.getTableRowData());

                tabbedPane1.setSelectedIndex(2);
            }
        });
        buttonStopAgents.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                agentActual.stopMAS();
            }
        });
    }

    /**
     * Sends signals for stopping threads.
     */
    public void exitProcedure() {
        timerThread.setRunning(false);
        if (agentActual != null) agentActual.stopMAS();
        System.exit(0);
    }

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                new MainGUI();
            }
        });
    }


    /* GENERAL METHODS */

    private void dataInitialization() {
        placeList = Place.generateDefaultPlaces();
        distancesMatrix = Place.getDistancesMatrix();
        myHeadquarter = Place.getPlaceByTitle(placeList, "Николаев"); // ALWAYS ABOVE vehicleList AND orderList INITIALIZATION, OTHERWISE - NullPointerException

        clientList = new ArrayList<>();
        orderList = Order.generateRandomOrderList(5, 5);
        vehicleList = Vehicle.generateDefaultVehiclesList();
    }

    private void formInitialization() {
        // CLIENT ORDERS TABLE INITIALIZATION
        DefaultTableModel model1 = (DefaultTableModel) tableClientOrdersList.getModel();
        model1.setColumnIdentifiers(Order.getTableColumnsIdentifiers());
        model1.setRowCount(0);

        // BASE ORDERS TABLE INITIALIZATION
        DefaultTableModel model2 = (DefaultTableModel) tableBasesOrderList.getModel();
        model2.setColumnIdentifiers(Order.getTableColumnsIdentifiers());
        model2.setRowCount(0);

        // VEHICLES TABLE INITIALIZATION
        DefaultTableModel model3 = (DefaultTableModel) tableVehiclesList.getModel();
        model3.setColumnIdentifiers(Vehicle.getTableColumnsIdentifiers());
        model3.setRowCount(0);

        // SAVING TABLE INITIALIZATION
        DefaultTableModel model4 = (DefaultTableModel) tableSaving.getModel();
        model4.setColumnIdentifiers(SavingTable.getTableColumnsIdentifiers());
        model4.setRowCount(0);

        // AGENT TABLE INITIALIZATION
        DefaultTableModel model5 = (DefaultTableModel) tableAgentsStatus.getModel();
        model5.setColumnIdentifiers(AgentActual.getTableColumnsIdentifiers());
        model5.setRowCount(0);

        // NEW ORDER TABLE INITIALIZATION
        DefaultTableModel model6 = (DefaultTableModel) tableNewOders.getModel();
        model6.setColumnIdentifiers(AgentActual.getTableColumnsIdentifiersForNewOrder());
        model6.setRowCount(vehicleList.size());

        fillTableWithData(tableClientOrdersList, Order.getClientsOrderSublist(orderList));
        fillTableWithData(tableBasesOrderList, Order.getBaseOrderSublist(orderList));
        fillTableWithData(tableVehiclesList, vehicleList);
    }


    /* TABLES HELP METHODS */

    private void refreshOrdersTables() {
        fillTableWithData(tableClientOrdersList, Order.getClientsOrderSublist(orderList));
        fillTableWithData(tableBasesOrderList, Order.getBaseOrderSublist(orderList));
    }

    private void fillTableWithData(JTable table, List<? extends ITableViewable> list) {
        removeRows(table);
        DefaultTableModel model = (DefaultTableModel) table.getModel();

        for (ITableViewable element : list) {
            model.addRow(element.getTableRowData());
        }
    }

    /**
     * Fills table with objects.
     * Cannot use <code>fillTableWithData</code> because of different parameter
     *
     * @param rows an array of rows with data (two-dimensional)
     */
    private void fillTableWithDataArrays(JTable table, Object[][] rows) {
        removeRows(table);
        DefaultTableModel model = (DefaultTableModel) table.getModel();

        for (Object[] row : rows) {
            model.addRow(row);
        }
    }

    private void removeRows(JTable table) {
        DefaultTableModel model = (DefaultTableModel) table.getModel();

        while (model.getRowCount() > 0) {
            model.removeRow(0);
        }
    }

    private boolean checkTableSelection(JTable table) {
        return !(table == null || table.getSelectedRow() == -1);
    }


    /* OTHER HELP METHODS */

    public static Date parseStringDate(String date) {
        String pattern = "EEE MMM d HH:mm:ss zzz yyyy";
        SimpleDateFormat parser = new SimpleDateFormat(pattern, locale);

        try {
            return parser.parse(date);
        } catch (ParseException e) {
            JOptionPane.showMessageDialog(null, "Your data format doesn't match current pattern ('" + pattern + "').");
            return null;
        }
    }

    public static int getRandomNumberInRange(int min, int max) {
        return (int) (min + Math.random() * max);
    }

    private void runOrderGUI(Order order) {
        OrderGUI.main(order);
    }

    private void runVehicleGUI(Vehicle vehicle) {
        VehicleGUI.main(vehicle);
    }

    private boolean preSavingAlgorithmChecks() {
        if (orderList.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Order List is empty. Saving algorithm cannot be run.");
            return false;
        }

        if (vehicleList.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Vehicles List is empty. Saving algorithm cannot be run.");
            return false;
        }

        return true;
    }

}
