package edu.eezo.gmap;

import com.teamdev.jxbrowser.chromium.Browser;
import com.teamdev.jxbrowser.chromium.swing.BrowserView;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by Eezo on 25.10.2016.
 */
public class GoogleMapsSample {

    public static final int MIN_ZOOM = 0;
    public static final int MAX_ZOOM = 21;

    /**
     * In map.html file default zoom value is set to 4.
     */
    private static int zoomValue = 4;

    public static void main(String[] args) {
        /*final Browser browser = new Browser();
        BrowserView browserView = new BrowserView(browser);

        JButton zoomInButton = new JButton("Zoom In");
        zoomInButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (zoomValue < MAX_ZOOM) {
                    browser.executeJavaScript("map.setZoom(" + ++zoomValue + ")");
                }
            }
        });

        JButton zoomOutButton = new JButton("Zoom Out");
        zoomOutButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (zoomValue > MIN_ZOOM) {
                    browser.executeJavaScript("map.setZoom(" + --zoomValue + ")");
                }
            }
        });

        JButton setMarkerButton = new JButton("Set Marker");
        setMarkerButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                browser.executeJavaScript("var myLatlng = new google.maps.LatLng(48.4431727,23.0488126);\n" +
                        "var marker = new google.maps.Marker({\n" +
                        "    position: myLatlng,\n" +
                        "    map: map,\n" +
                        "    title: 'Hello World!'\n" +
                        "});");
            }
        });

        JPanel map = new JPanel();
        map.add(browserView.getComponent(0));
        JPanel toolBar = new JPanel();
        toolBar.add(zoomInButton);
        toolBar.add(zoomOutButton);
        toolBar.add(setMarkerButton);
        map.add(toolBar);

        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.addTab("Google Map", map);

        JFrame frame = new JFrame("map.html");
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.add(tabbedPane, BorderLayout.CENTER);
        //frame.add(tabbedPane, BorderLayout.SOUTH);
        //frame.add(browserView, BorderLayout.CENTER);
        frame.setSize(900, 500);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        browser.loadURL("D:\\MAS\\src\\main\\resources\\html\\map.html");*/

        Browser browser = new Browser();
        JPanel panel = new JPanel();

        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.addTab("Tools", panel);
        tabbedPane.addTab("Google Map", new BrowserView(browser));

        JFrame frame = new JFrame();
        frame.getContentPane().add(tabbedPane, BorderLayout.CENTER);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setSize(800, 600);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        browser.loadURL("D:\\MAS\\src\\main\\resources\\html\\map.html");
        //browser.loadURL("https://www.google.com.ua");
    }
}
