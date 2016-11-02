package edu.eezo.gmap;

import com.teamdev.jxbrowser.chromium.Browser;
import com.teamdev.jxbrowser.chromium.swing.BrowserView;

import javax.swing.*;
import java.awt.*;

/**
 * Created by Eezo on 25.10.2016.
 */
public class MainGUI extends JFrame {

    private JPanel rootPanel;
    private JLabel label;
    private JPanel gmTab;

    public MainGUI(){
        super("Renamer");
        final Browser browser = new Browser();
        BrowserView browserView = new BrowserView(browser);

        setSize(450, 415);
        setLocationRelativeTo(null);
        setContentPane(rootPanel);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        panel.add(browserView.getComponent(0));
        gmTab.add(panel);

        setVisible(true);
        browser.loadURL("D:\\MAS\\src\\main\\resources\\html\\map.html");
    }

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                new MainGUI();
            }
        });
    }

}
