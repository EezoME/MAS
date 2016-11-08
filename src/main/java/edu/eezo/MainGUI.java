package edu.eezo;

import edu.eezo.data.*;
import edu.eezo.saving.SavingAlgorithm;

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
    private JComboBox comboBoxOrdersLists;
    private JButton buttonAddNewOrderList;
    private JTable tableOrdersList;
    private JButton buttonAddOrder;
    private JButton buttonDeleteOrder;
    private JButton buttonEditOrder;
    private JTable tableTrucksList;
    private JButton buttonAddTruck;
    private JButton buttonRemoveTruck;
    private JButton buttonData;
    private JButton buttonRemoveOrderList;
    private JTextField textFieldVehicleIdentificationInput;
    private JTextField textFieldVehicleWeightInput;
    private JButton buttonNextStep;
    private JComboBox comboBoxOrdersLists2;
    private JButton buttonRunAlgorithm;
    private JTable tableSaving;
    private JLabel labelGlobalRoute;
    private JButton buttonBuildLocalRoutes;
    private JLabel labelTakeAWhile;

    public static Locale locale = Locale.ENGLISH;
    /**
     * A list of locations (by default, Ukrainian cities).
     */
    public static java.util.List<Place> placeList = Place.generateDefaultPlaces();
    /**
     * A list of customers.
     */
    public static java.util.List<Customer> customerList = Customer.generateDefaultCustomers();
    /**
     * Order lists.
     */
    public static java.util.List<OrderList> orderLists = null;
    /**
     * Current headquarter location (for saving algorithm).
     * By default sets to "Николаев".
     */
    public static Place myHeadquarter = Place.getPlaceByTitle(placeList, "Николаев");
    /**
     * A list of company vehicles (trucks).
     */
    public static java.util.List<Vehicle> vehicleList = Vehicle.generateDefaultVehiclesList();

    public MainGUI() {
        super("MAS");
        setSize(900, 700);
        setContentPane(rootPanel);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setVisible(true);

        makeDataInitialization();

        makeFormInitialization();


        /* LISTENERS OF TAB 1 */



        /* COMBOBOXES LISTENERS */
        comboBoxOrdersLists.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (!checkOrdersListComboBox()) {
                    return;
                }
                fillOrderTable(((OrderList) comboBoxOrdersLists.getSelectedItem()).getOrders());
            }
        });

        /* ORDER LISTENERS */
        buttonAddOrder.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                orderLists.get(comboBoxOrdersLists.getSelectedIndex()).getOrders().add(Order.generateRandomOrder());
                runOrderGUI(orderLists.get(comboBoxOrdersLists.getSelectedIndex()).getOrders().get(orderLists.get(comboBoxOrdersLists.getSelectedIndex()).getOrders().size() - 1));
            }
        });
        buttonEditOrder.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!checkOrdersListComboBox()) {
                    return;
                }
                if (!checkTableSelection(tableOrdersList)) {
                    JOptionPane.showMessageDialog(null, "Select an order in the table");
                    return;
                }
                runOrderGUI(orderLists.get(comboBoxOrdersLists.getSelectedIndex()).getOrders().get(tableOrdersList.getSelectedRow()));
            }
        });
        buttonDeleteOrder.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!checkOrdersListComboBox()) {
                    return;
                }
                if (!checkTableSelection(tableOrdersList)) {
                    JOptionPane.showMessageDialog(null, "Select an order in the table");
                    return;
                }
                if (JOptionPane.showConfirmDialog(null, "Are you sure you want to delete an order?") == JOptionPane.OK_OPTION) {
                    orderLists.get(comboBoxOrdersLists.getSelectedIndex()).getOrders().remove(tableOrdersList.getSelectedRow());
                }
                refreshOrdersTable();
            }
        });

        /* ORDER LIST LISTENERS */
        buttonAddNewOrderList.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String title = JOptionPane.showInputDialog(null, "Please, enter a new order list name");
                if (title == null || title.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "You didn't type new order list name. Creation failed.");
                    return;
                }
                orderLists.add(new OrderList(title, null));
                refreshOrderListsCombobox(comboBoxOrdersLists);
            }
        });
        buttonRemoveOrderList.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!checkOrdersListComboBox()) {
                    return;
                }
                if (JOptionPane.showConfirmDialog(null, "Are you sure you want remove an order list?") == JOptionPane.OK_OPTION) {
                    orderLists.remove(comboBoxOrdersLists.getSelectedIndex());
                    refreshOrderListsCombobox(comboBoxOrdersLists);
                }
            }
        });

        /* VEHICLE LISTENERS */
        buttonAddTruck.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (textFieldVehicleIdentificationInput.getText().isEmpty()) {
                    JOptionPane.showMessageDialog(null, "You didn't input truck identification data. Creation failed.");
                    return;
                }
                if (textFieldVehicleWeightInput.getText().isEmpty()) {
                    JOptionPane.showMessageDialog(null, "You didn't input truck weight data. Creation failed.");
                    return;
                }
                vehicleList.add(new Vehicle(textFieldVehicleIdentificationInput.getText(), myHeadquarter,
                        Integer.parseInt(textFieldVehicleWeightInput.getText())));

                fillVehicleTable(vehicleList);
            }
        });
        buttonRemoveTruck.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!checkTableSelection(tableTrucksList)) {
                    JOptionPane.showMessageDialog(null, "Select a vehicle in the table.");
                    return;
                }
                if (JOptionPane.showConfirmDialog(null, "Are you sure you want to delete a vehicle?") == JOptionPane.OK_OPTION) {
                    vehicleList.remove(tableTrucksList.getSelectedRow());
                }
                fillVehicleTable(vehicleList);
            }
        });

        /* OTHER LISTENERS */
        buttonData.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                SettingsGUI.main(null);
            }
        });
        buttonNextStep.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!preSavingChecks()){
                    return;
                }
                refreshOrderListsCombobox(comboBoxOrdersLists2);
                tabbedPane1.setSelectedIndex(1);
            }
        });



        /* LISTENERS OF TAB 2 */


        buttonRunAlgorithm.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!preSavingChecks()){
                    return;
                }
                SavingAlgorithm savingAlgorithm = new SavingAlgorithm(orderLists.get(comboBoxOrdersLists2.getSelectedIndex()));
                savingAlgorithm.runAlgorithm();
                fillSavingTable(savingAlgorithm.getTableRowsData());
            }
        });
    }

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                new MainGUI();
            }
        });
    }


    /* GENERAL METHODS */

    private void makeDataInitialization() {
        orderLists = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            orderLists.add(OrderList.generateDefaultOrderList("Order list " + i));
            //orderLists.add(OrderList.generateOrderListWithUniquePairs("Order list " + i, new int[]{2500,8600}, 2000));
        }
    }

    private void makeFormInitialization() {
        // ORDER LISTS INITIALIZATION
        refreshOrderListsCombobox(comboBoxOrdersLists);

        // ORDERS TABLE INITIALIZATION
        DefaultTableModel model1 = (DefaultTableModel) tableOrdersList.getModel();
        model1.setColumnIdentifiers(Order.getTableColumnsIdentifiers());
        model1.setRowCount(0);

        // TRUCKS TABLE INITIALIZATION
        DefaultTableModel model2 = (DefaultTableModel) tableTrucksList.getModel();
        model2.setColumnIdentifiers(Vehicle.getTableColumnsIdentifiers());
        model2.setRowCount(0);

        fillVehicleTable(vehicleList);

        // SAVING TABLE INITIALIZATION
        DefaultTableModel model3 = (DefaultTableModel) tableSaving.getModel();
        model3.setColumnIdentifiers(SavingAlgorithm.getTableColumnsIdentifiers());
        model3.setRowCount(0);
    }


    /* TABLES HELP METHODS */

    private void fillOrderTable(List<Order> orders) {
        removeRows(tableOrdersList);
        DefaultTableModel model = (DefaultTableModel) tableOrdersList.getModel();
        int i = 0;
        for (Order order : orders) {
            Object[] objects = order.getTableRowData();
            objects[0] = i++;
            model.addRow(objects);
        }
    }

    private void fillVehicleTable(List<Vehicle> vehicles) {
        removeRows(tableTrucksList);
        DefaultTableModel model = (DefaultTableModel) tableTrucksList.getModel();
        int i = 0;
        for (Vehicle vehicle : vehicles) {
            Object[] objects = vehicle.getTableRowData();
            objects[0] = i++;
            model.addRow(objects);
        }
    }

    private void fillSavingTable(Object[][] rows) {
        removeRows(tableSaving);
        DefaultTableModel model = (DefaultTableModel) tableSaving.getModel();
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

    private void refreshOrdersTable() {
        fillOrderTable(orderLists.get(comboBoxOrdersLists.getSelectedIndex()).getOrders());
    }

    private boolean checkTableSelection(JTable table) {
        return !(table == null || table.getSelectedRow() == -1);
    }

    /* HELP METHOD FOR COMBOBOXES */

    private void refreshOrderListsCombobox(JComboBox comboBox) {
        int count = comboBox.getItemCount();
        for (OrderList orderList : orderLists) {
            comboBox.addItem(orderList);
        }
        for (int i = 0; i < count; i++) {
            comboBox.removeItemAt(0);
        }
    }

    private boolean checkOrdersListComboBox() {
        if (comboBoxOrdersLists == null || comboBoxOrdersLists.getSelectedIndex() == -1) {
            JOptionPane.showMessageDialog(null, "No orders list was found.");
            return false;
        }
        return true;
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
        refreshOrdersTable();
    }

    private boolean preSavingChecks(){
        if (orderLists.isEmpty()){
            JOptionPane.showMessageDialog(null, "Order Lists is empty. Saving algorithm cannot be run.");
            return false;
        }
        if (vehicleList.isEmpty()){
            JOptionPane.showMessageDialog(null, "Vehicles List is empty. Saving algorithm cannot be run.");
            return false;
        }
        return true;
    }
}
