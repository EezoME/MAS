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
    private JComboBox comboBoxCustomer;
    private JComboBox comboBoxOrigin;
    private JComboBox comboBoxDestination;
    private JTextField textFieldWeight;
    private JTextField textFieldMaxPrice;
    private JTextField textFieldDeliveryDuration;
    private JTextField textFieldFine;

    private Order order;

    public OrderGUI(Order order) {
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);
        if (order == null) {
            labelTop.setText("New Order");
        } else {
            labelTop.setText("Edit Order");
            this.order = order;
        }

        for (Customer customer : MainGUI.customerList) {
            comboBoxCustomer.addItem(customer);
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
        comboBoxCustomer.setSelectedItem(order.getCustomer());
        comboBoxOrigin.setSelectedItem(order.getOrigin());
        comboBoxDestination.setSelectedItem(order.getDestination());
        textFieldWeight.setText(order.getWeight() + "");
        textFieldMaxPrice.setText(order.getMaxPrice() + "");
        textFieldDeliveryDuration.setText(order.getDeliveryDuration() + "");
        textFieldFine.setText(order.getFine() + "");
    }

    private void onOK() {
        if (!checkRequiredFields()) {
            return;
        }
        if (checkOptionalFields()) {
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
        order.setCustomer((Customer) comboBoxCustomer.getSelectedItem());
        order.setOrigin((Place) comboBoxOrigin.getSelectedItem());
        order.setDestination((Place) comboBoxDestination.getSelectedItem());
        order.setWeight(Integer.parseInt(textFieldWeight.getText()));
        order.setMaxPrice(Integer.parseInt(textFieldMaxPrice.getText()));
        if (textFieldDeliveryDuration.getText().isEmpty()) {
            order.setDeliveryDuration(order.getOrderReceiptTime().getTime() + Long.parseLong(textFieldDeliveryDuration.getText()) * (60 * 1000));
        } else {
            order.setDeliveryDuration(Long.parseLong(textFieldDeliveryDuration.getText()));
        }
        if (textFieldFine.getText().isEmpty()) {
            order.setFine(Order.DEFAULT_FINE);
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
        if (textFieldWeight.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Specify the weight.");
            return false;
        }
        try {
            if (Integer.parseInt(textFieldWeight.getText()) <= 0) {
                JOptionPane.showMessageDialog(null, "Weight value must be grater than 0.");
                return false;
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Weight value is not a number!");
            return false;
        }

        if (textFieldMaxPrice.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Specify the price.");
            return false;
        }
        try {
            if (Integer.parseInt(textFieldMaxPrice.getText()) <= 0) {
                JOptionPane.showMessageDialog(null, "Max price value must be grater than 0.");
                return false;
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Max price value is not a number!");
            return false;
        }

        return true;
    }

    private boolean checkOptionalFields() {
        if (textFieldDeliveryDuration.getText().isEmpty()) {
            System.out.println("INFO: You didn't specified delivery duration. It will be set to default (1 week).");
        }
        if (textFieldFine.getText().isEmpty()) {
            System.out.println("INFO: You didn't specified fine. It will be set to default (100 UAH/hour).");
        }

        try {
            if (Long.parseLong(textFieldDeliveryDuration.getText()) <= 0) {
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
