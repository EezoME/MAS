package edu.eezo.thread;

import javax.swing.*;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Time displaying util.
 * Created by Eezo on 03.12.2016.
 */
public class TimerThread extends Thread {

    private boolean isRunning;

    private JLabel dateLabel;
    private JLabel timeLabel;

    private SimpleDateFormat dateFormat = new SimpleDateFormat(" EEE, d MMM yyyy");
    private SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");

    public TimerThread(JLabel dateLabel, JLabel timeLabel) {
        this.dateLabel = dateLabel;
        this.timeLabel = timeLabel;
        this.isRunning = true;
    }

    @Override
    public void run() {
        while (isRunning) {
            SwingUtilities.invokeLater(new Runnable() {
                @Override
                public void run() {
                    Calendar currentCalendar = Calendar.getInstance();
                    Date currentTime = currentCalendar.getTime();
                    dateLabel.setText(dateFormat.format(currentTime));
                    timeLabel.setText(timeFormat.format(currentTime));
                }
            });

            try {
                Thread.sleep(1000L);
            } catch (InterruptedException e) {
                JOptionPane.showMessageDialog(null, "Timer Timing Error.");
            }
        }
    }

    public void setRunning(boolean isRunning) {
        this.isRunning = isRunning;
    }

}