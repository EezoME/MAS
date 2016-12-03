package edu.eezo.data;

import edu.eezo.MainGUI;

import javax.swing.*;
import java.awt.event.*;

public class VehicleGUI extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JLabel labelTop;
    private JTextField textFieldIdentification;
    private JTextField textFieldMaxFreight;
    private JTextField textFieldAverageSpeed;
    private JTextField textFieldTimeForLoad;
    private JTextField textFieldTimeForUnload;
    private JTextField textFieldTCost;
    private JComboBox comboBoxLocation;
    private JComboBox comboBoxState;

    private Vehicle vehicle;

    public VehicleGUI(Vehicle vehicle) {
        setContentPane(contentPane);
        setTitle("Vehicle GUI -- MAS");
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);

        if (vehicle == null) {
            labelTop.setText("New Transportation Unit");
        } else {
            labelTop.setText("Edit Transportation Unit");
            this.vehicle = vehicle;
        }

        for (Place place : MainGUI.placeList) {
            comboBoxLocation.addItem(place);
        }
        comboBoxLocation.setSelectedItem(MainGUI.myHeadquarter);

        for (Vehicle.State state : Vehicle.State.values()) {
            comboBoxState.addItem(state);
        }

        showData();

        buttonOK.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onOK();
            }
        });

        buttonCancel.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        });

        // call onCancel() when cross is clicked
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onCancel();
            }
        });

        // call onCancel() on ESCAPE
        contentPane.registerKeyboardAction(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        }, KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
    }

    private void showData() {
        if (vehicle == null) {
            return;
        }

        textFieldIdentification.setText(vehicle.getIdentification());
        textFieldMaxFreight.setText(vehicle.getFreight() + "");
        textFieldAverageSpeed.setText(vehicle.getAverageSpeed() + "");
        textFieldTimeForLoad.setText(vehicle.getTimeForLoad() + "");
        textFieldTimeForUnload.setText(vehicle.getTimeForUnload() + "");
        textFieldTCost.setText(vehicle.getTransportationCost() + "");
        comboBoxLocation.setSelectedItem(vehicle.getCurrentLocation());
        comboBoxState.setSelectedItem(vehicle.getState());
    }

    private void onOK() {
        if (!checkRequiredFields()) {
            return;
        }

        if (vehicle == null) {
            vehicle = new Vehicle();
        }

        vehicle.setIdentification(textFieldIdentification.getText());
        vehicle.setFreight(Double.parseDouble(textFieldMaxFreight.getText()));
        vehicle.setAverageSpeed(Integer.parseInt(textFieldAverageSpeed.getText()));
        vehicle.setTimeForLoad(Double.parseDouble(textFieldTimeForLoad.getText()));
        vehicle.setTimeForUnload(Double.parseDouble(textFieldTimeForUnload.getText()));
        vehicle.setTransportationCost(Double.parseDouble(textFieldTCost.getText()));
        vehicle.setCurrentLocation(((Place) comboBoxLocation.getSelectedItem()).getGmapsAddress());
        vehicle.setState((Vehicle.State) comboBoxState.getSelectedItem());
        vehicle.setCurrentRoute(null);
        vehicle.setRouteCompletedLength(0.0d);
        vehicle.setTimeOnRoute(0.0d);

        dispose();
    }

    private void onCancel() {
        // add your code here if necessary
        dispose();
    }

    public static void main(Vehicle vehicle) {
        VehicleGUI dialog = new VehicleGUI(vehicle);
        dialog.pack();
        dialog.setVisible(true);
    }

    private boolean checkRequiredFields() {
        if (textFieldIdentification.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Specify the vehicle identification.");
            return false;
        }

        try {
            if (Double.parseDouble(textFieldMaxFreight.getText()) <= 0) {
                JOptionPane.showMessageDialog(null, "Max Freight value must be grater than 0.");
                return false;
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Freight value is not a number!");
            return false;
        }

        try {
            if (Integer.parseInt(textFieldAverageSpeed.getText()) <= 0) {
                JOptionPane.showMessageDialog(null, "Average speed value must be grater than 0.");
                return false;
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Average speed value is not a number!");
            return false;
        }

        try {
            if (Double.parseDouble(textFieldTimeForLoad.getText()) <= 0) {
                JOptionPane.showMessageDialog(null, "Time for load value must be grater than 0.");
                return false;
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Time for load value is not a number!");
            return false;
        }

        try {
            if (Double.parseDouble(textFieldTimeForUnload.getText()) <= 0) {
                JOptionPane.showMessageDialog(null, "Time for unload value must be grater than 0.");
                return false;
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Time for unload value is not a number!");
            return false;
        }

        try {
            if (Double.parseDouble(textFieldTCost.getText()) <= 0) {
                JOptionPane.showMessageDialog(null, "Transportation cost value must be grater than 0.");
                return false;
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Transportation cost value is not a number!");
            return false;
        }

        return true;
    }
}
