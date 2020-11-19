/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projets5t4;

import javax.swing.*;
import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.*;
import java.util.GregorianCalendar;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;
import java.awt.Container;
import java.awt.Insets;
import java.awt.Dimension;

/**
 *
 * @author Gaspard Lefort-Louet
 */
public class PanelProfil extends JFrame {

    private final int WINDOW_WIDTH = 1080;   // Window width
    private final int WINDOW_HEIGHT = 720;  // Window height
    private JTable tableau;
    private JPanel panel;
    private JButton nextWeek, previousWeek, actualWeek;
    private DefaultTableModel model;
    private JList hours;

    private final LocalTime startDay = LocalTime.of(8, 0);
    private final LocalTime endDay = LocalTime.of(19, 0);
    private final LocalTime[] time = new LocalTime[19];

    private String[] days = new String[5];
    String[][] event = new String[time.length / 2 + 2][];

    private Calendar calendar = Calendar.getInstance();

    public PanelProfil() {
        // Set the window title.
        setTitle("Profil");

        // Set the size of the window.
        setSize(WINDOW_WIDTH, WINDOW_HEIGHT);

        // Specify what happens when the close button is clicked.
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Build the panel and add it to the frame.
        buildPanel();

        add(panel);
        // Display the window.
        setVisible(true);
    }

    private void buildPanel() {

        panel = new JPanel();

        hours = new JList(time);

        hours.setFixedCellHeight(30);

        SimpleDateFormat format = new SimpleDateFormat("EEEE d MMMM yyyy");

        int delta = -calendar.get(GregorianCalendar.DAY_OF_WEEK) + 2;

        calendar.add(Calendar.DAY_OF_MONTH, delta);

        for (int i = 0; i <= 11; ++i) {
            time[i] = startDay.plusHours(i);
        }

        for (int i = 0; i < 5; i++) {
            days[i] = format.format(calendar.getTime());
            calendar.add(Calendar.DAY_OF_MONTH, 1);
        }

        nextWeek = new JButton("Next week");
        previousWeek = new JButton("Previous week");
        actualWeek = new JButton("Actual week");

        nextWeek.addActionListener(new ButtonListener());
        previousWeek.addActionListener(new ButtonListener());
        actualWeek.addActionListener(new ButtonListener());

        model = new DefaultTableModel(event, days);
        tableau = new JTable(model);

        tableau.setRowHeight(30);
        TableColumnModel column = tableau.getColumnModel();

        for (int i = 0; i < days.length; ++i) {
            column.getColumn(i).setPreferredWidth(150);
        }

        JScrollPane tab = new JScrollPane(tableau);

        panel.setLayout(null);

        panel.add(previousWeek);
        panel.add(tab);
        panel.add(nextWeek);
        panel.add(actualWeek);
        panel.add(hours);

        tab.setSize(5 * 170 - 1, 12 * 30 - 7);

        Insets insets = panel.getInsets();
        Dimension size = previousWeek.getPreferredSize();
        previousWeek.setBounds(620 + insets.left, 40 + insets.top, size.width, size.height);
        nextWeek.setBounds(760 + insets.left, 40 + insets.top, size.width, size.height);
        actualWeek.setBounds(685 + insets.left, 450 + insets.top, size.width, size.height);

        size = tab.getSize();
        tab.setBounds(320 + insets.left, 90 + insets.top, size.width, size.height);
        hours.setBounds(285 + insets.left, 95 + insets.top, size.width, size.height);

        panel.setBackground(Color.white);

    }

    public void setNextWeek() {

        SimpleDateFormat format = new SimpleDateFormat("EEEE d MMMM yyyy");

        int delta = -calendar.get(GregorianCalendar.DAY_OF_WEEK) + 2;

        calendar.add(Calendar.DAY_OF_MONTH, delta + 7);

        for (int i = 0; i < 5; i++) {
            days[i] = format.format(calendar.getTime());
            calendar.add(Calendar.DAY_OF_MONTH, 1);
        }

        model = new DefaultTableModel(event, days);

        model.fireTableDataChanged();

        tableau.setModel(model);
    }

    public void setPreviousWeek() {

        SimpleDateFormat format = new SimpleDateFormat("EEEE d MMMM yyyy");

        int delta = -calendar.get(GregorianCalendar.DAY_OF_WEEK) + 2;

        calendar.add(Calendar.DAY_OF_MONTH, delta - 7);

        for (int i = 0; i < 5; i++) {
            days[i] = format.format(calendar.getTime());
            calendar.add(Calendar.DAY_OF_MONTH, 1);
        }

        model = new DefaultTableModel(event, days);

        model.fireTableDataChanged();

        tableau.setModel(model);
    }

    public void setActualWeek() {
        SimpleDateFormat format = new SimpleDateFormat("EEEE d MMMM yyyy");
        
        calendar = Calendar.getInstance();

        int delta = -calendar.get(GregorianCalendar.DAY_OF_WEEK) + 2;

        calendar.add(Calendar.DAY_OF_MONTH, delta);

        for (int i = 0; i < 5; i++) {
            days[i] = format.format(calendar.getTime());
            calendar.add(Calendar.DAY_OF_MONTH, 1);
        }
        
        model = new DefaultTableModel(event, days);

        model.fireTableDataChanged();

        tableau.setModel(model);
    }

    private class ButtonListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {

            if (e.getSource() == nextWeek) {
                setNextWeek();
            }
            if (e.getSource() == previousWeek) {
                setPreviousWeek();
            }
            if (e.getSource() == actualWeek) {
                setActualWeek();
            }
        }

    }
}
