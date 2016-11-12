package edu.eezo.data;

import edu.eezo.MainGUI;

import javax.swing.*;
import java.awt.event.*;
import java.util.Date;

public class OrderGUI extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JLabel labelTop;
    private JTextField textFieldReceiptTime;
    private JComboBox comboBoxClient;
    private JComboBox comboBoxOrigin;
    private JComboBox comboBoxDestination;
    private JTextField textFieldFreight;
    private JTextField textFieldMaxCost;
    private JTextField textFieldDeliveryDuration;
    private JTextField textFieldFine;
    private JCheckBox checkBoxIsBases;

    private Order order;

    public OrderGUI(Order order) {
        setContentPane(contentPane);
        setTitle("Order GUI -- MAS");
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);
        if (order == null) {
            labelTop.setText("New Order");
        } else {
            labelTop.setText("Edit Order");
            this.order = order;
        }

        for (Client client : MainGUI.clientList) {
            comboBoxClient.addItem(client);
        }

        for (Place place : MainGUI.placeList) {
            comboBoxOrigin.addItem(place);
            comboBoxDestination.addItem(place);
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
        if (order == null) {
            return;
        }
        textFieldReceiptTime.setText(order.getOrderReceiptTime().toString());
        comboBoxClient.setSelectedItem(order.getClient());
        checkBoxIsBases.setSelected(order.isBases());
        comboBoxOrigin.setSelectedItem(order.getOrigin());
        comboBoxDestination.setSelectedItem(order.getDestination());
        textFieldFreight.setText(order.getFreightVolume() + "");
        textFieldMaxCost.setText(order.getMaxCost() + "");
        textFieldDeliveryDuration.setText(order.getTimeOfDelivery() + "");
        textFieldFine.setText(order.getFine() + "");
    }

    private void onOK() {
        if (!checkRequiredFields()) {
            return;
        }
        if (!checkOptionalFields()) {
            return;
        }
        if (order == null) {
            order = new Order();
        }
        if (textFieldReceiptTime.getText().isEmpty()) {
            order.setOrderReceiptTime(new Date(System.currentTimeMillis()));
        } else {
            Date date = MainGUI.parseStringDate(textFieldReceiptTime.getText());
            if (date == null) {
                return;
            } else {
                order.setOrderReceiptTime(date);
            }
        }
        order.setClient((Client) comboBoxClient.getSelectedItem());
        order.setBases(checkBoxIsBases.isSelected());
        order.setOrigin((Place) comboBoxOrigin.getSelectedItem());
        order.setDestination((Place) comboBoxDestination.getSelectedItem());
        order.setFreightVolume(Double.parseDouble(textFieldFreight.getText()));
        order.setMaxCost(Integer.parseInt(textFieldMaxCost.getText()));
        if (textFieldDeliveryDuration.getText().isEmpty()) {
            order.setTimeOfDelivery(48.0);
        } else {
            order.setTimeOfDelivery(Double.parseDouble(textFieldDeliveryDuration.getText()));
        }
        if (textFieldFine.getText().isEmpty()) {
            order.setFine(Order.defaultFine);
        } else {
            order.setFine(Integer.parseInt(textFieldFine.getText()));
        }

        dispose();
    }

    private void onCancel() {
        // add your code here if necessary
        dispose();
    }

    public static void main(Order order) {
        OrderGUI dialog = new OrderGUI(order);
        dialog.pack();
        dialog.setVisible(true);
    }

    private boolean checkRequiredFields() {
        if (textFieldFreight.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Specify the freight.");
            return false;
        }
        try {
            if (Double.parseDouble(textFieldFreight.getText()) <= 0) {
                JOptionPane.showMessageDialog(null, "Freight value must be grater than 0.");
                return false;
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Freight value is not a number!");
            return false;
        }

        if (textFieldMaxCost.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Specify the cost.");
            return false;
        }
        try {
            if (Integer.parseInt(textFieldMaxCost.getText()) <= 0) {
                JOptionPane.showMessageDialog(null, "Max cost value must be grater than 0.");
                return false;
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Max cost value is not a number!");
            return false;
        }

        return true;
    }

    private boolean checkOptionalFields() {
        if (textFieldDeliveryDuration.getText().isEmpty()) {
            System.out.println("INFO: You didn't specified delivery duration. It will be set to default (48 hrs).");
        }
        if (textFieldFine.getText().isEmpty()) {
            System.out.println("INFO: You didn't specified fine. It will be set to default (100 UAH/hour).");
        }

        try {
            if (Double.parseDouble(textFieldDeliveryDuration.getText()) <= 0) {
                JOptionPane.showMessageDialog(null, "Delivery duration value must be grater than 0.");
                return false;
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Delivery duration value is not a number!");
            return false;
        }

        try {
            if (Integer.parseInt(textFieldFine.getText()) < 0) {
                JOptionPane.showMessageDialog(null, "Fine value must be positive.");
                return false;
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Fine value is not a number!");
            return false;
        }

        return true;
    }
}
