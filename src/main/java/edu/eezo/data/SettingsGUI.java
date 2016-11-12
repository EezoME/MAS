package edu.eezo.data;

import edu.eezo.MainGUI;

import javax.swing.*;
import java.awt.event.*;

public class SettingsGUI extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JComboBox comboBoxHeadquarter;
    private JComboBox comboBoxPlaces;
    private JButton buttonAddPlace;
    private JComboBox comboBoxClients;
    private JButton buttonAddClient;
    private JTextField textFieldCityInput;
    private JTextField textFieldRegionInput;
    private JTextField textFieldClientNameInput;
    private JComboBox comboBoxClientOrdersInput;
    private JComboBox comboBoxBaseOrdersInput;

    public SettingsGUI() {
        setTitle("General Settings -- MAS");
        setContentPane(contentPane);
        setModal(true);
        setResizable(false);
        getRootPane().setDefaultButton(buttonOK);
        setLocationRelativeTo(getParent());

        refreshPlacesCBs();
        refreshClientsCB();
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
        buttonAddClient.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (textFieldClientNameInput.getText().isEmpty()) {
                    JOptionPane.showMessageDialog(null, "You don't input customer name or title.");
                    return;
                }
                Client newClient = new Client(textFieldClientNameInput.getText(), null, null);
                MainGUI.clientList.add(newClient);
                refreshClientsCB();
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
        Place headquarter = (Place) comboBoxHeadquarter.getSelectedItem();
        comboBoxPlaces.removeAllItems();
        comboBoxHeadquarter.removeAllItems();
        for (Place place : MainGUI.placeList) {
            comboBoxPlaces.addItem(place);
            comboBoxHeadquarter.addItem(place);
        }
        comboBoxHeadquarter.setSelectedItem(headquarter);
    }

    private void refreshClientsCB() {
        comboBoxClients.removeAllItems();
        for (Client client : MainGUI.clientList) {
            comboBoxClients.addItem(client);
        }
    }
}
