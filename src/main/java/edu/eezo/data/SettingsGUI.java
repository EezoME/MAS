package edu.eezo.data;

import edu.eezo.MainGUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Arrays;

public class SettingsGUI extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JComboBox comboBoxHeadquarter;
    private JComboBox comboBoxPlaces;
    private JButton buttonAddPlace;
    private JComboBox comboBoxCustomers;
    private JButton addNewCustomerButton;
    private JTextField textFieldCityInput;
    private JTextField textFieldRegionInput;
    private JTextField textFieldCustomerNameInput;
    private JComboBox comboBoxCustomerHeadquarterInput;

    public SettingsGUI() {
        setContentPane(contentPane);
        setModal(true);
        setResizable(false);
        getRootPane().setDefaultButton(buttonOK);
        setLocationRelativeTo(getParent());

        refreshPlacesCBs();
        refresgCustomersCB();
        comboBoxHeadquarter.setSelectedItem(MainGUI.myHeadquarter);

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

        buttonAddPlace.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (textFieldCityInput.getText().isEmpty()) {
                    JOptionPane.showMessageDialog(null, "You don't input city.");
                    return;
                }
                Place newPlace = new Place(textFieldCityInput.getText(), textFieldRegionInput.getText());
                MainGUI.placeList.add(newPlace);
                refreshPlacesCBs();
                JOptionPane.showMessageDialog(null, "A new place added.");
            }
        });
        addNewCustomerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (textFieldCustomerNameInput.getText().isEmpty()) {
                    JOptionPane.showMessageDialog(null, "You don't input customer name or title.");
                    return;
                }
                Customer newCustomer = new Customer(textFieldCustomerNameInput.getText(), (Place) comboBoxCustomerHeadquarterInput.getSelectedItem());
                MainGUI.customerList.add(newCustomer);
                refresgCustomersCB();
                JOptionPane.showMessageDialog(null, "A new customer added.");
            }
        });
    }

    private void onOK() {
        MainGUI.myHeadquarter = (Place) comboBoxHeadquarter.getSelectedItem();
        dispose();
    }

    private void onCancel() {
        // add your code here if necessary
        dispose();
    }

    public static void main(String[] args) {
        SettingsGUI dialog = new SettingsGUI();
        dialog.pack();
        dialog.setVisible(true);
    }


    private void refreshPlacesCBs() {
        Place headquerter = (Place) comboBoxHeadquarter.getSelectedItem();
        comboBoxPlaces.removeAllItems();
        comboBoxHeadquarter.removeAllItems();
        comboBoxCustomerHeadquarterInput.removeAllItems();
        for (Place place : MainGUI.placeList) {
            comboBoxPlaces.addItem(place);
            comboBoxHeadquarter.addItem(place);
            comboBoxCustomerHeadquarterInput.addItem(place);
        }
        comboBoxHeadquarter.setSelectedItem(headquerter);
    }

    private void refresgCustomersCB() {
        comboBoxCustomers.removeAllItems();
        for (Customer customer : MainGUI.customerList) {
            comboBoxCustomers.addItem(customer);
        }
    }
}
