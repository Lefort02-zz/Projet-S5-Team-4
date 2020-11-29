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
import java.awt.Insets;
import java.awt.Dimension;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.Date;
import java.sql.Time;
import javax.swing.table.DefaultTableCellRenderer;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Gaspard Lefort-Louet
 */
public class PanelProfil extends JFrame {

    private JTable tableau;
    private JPanel panel;
    private JButton nextWeek, previousWeek, actualWeek;
    private DefaultTableModel model;
    private JList hours;

    private final LocalTime startDay = LocalTime.of(8, 0);
    private final LocalTime endDay = LocalTime.of(19, 0);
    private final LocalTime[] time = new LocalTime[12];
    private int numSécuPatient = 0;
    private int numSécuDocteur = 111;

    private String[] days = new String[5];
    private String[][] event = new String[time.length - 1][];

    private String eventDate, eventDisplay;

    private Calendar calendar = Calendar.getInstance();

    private SimpleDateFormat format = new SimpleDateFormat("EEEE d MMMM yyyy");

    private DAO<RDV> rdvDao = new RDVDAO(SQL.getInstance());
    private DAO<Doctor> docteurDAO = new DocteurDAO(SQL.getInstance());
    private DAO<Patient> patientDAO = new PatientDAO(SQL.getInstance());

    private List<Doctor> DoctorList = new ArrayList<>();
    private List<Patient> PatientList = new ArrayList<>();
    private List<RDV> RdvList = new ArrayList<>();

    private int posx, posy, tempHour;

    private String nom, prenom, age, antecedant, informations, numRDV;

    public PanelProfil() {
        // Set the window title.
        setTitle("Profil");

        setExtendedState(JFrame.MAXIMIZED_BOTH);

        // Specify what happens when the close button is clicked.
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Build the panel and add it to the frame.
        buildPanel();

        add(panel);

        // Display the window.
        setVisible(true);
    }
    
    public PanelProfil(int i){
        
    }

    private void buildPanel() {

        panel = new JPanel();

        hours = new JList(time);

        hours.setFixedCellHeight(30);
        hours.setSelectionModel(new NoSelectionModel());

        int delta = -calendar.get(GregorianCalendar.DAY_OF_WEEK) + 2;

        calendar.add(Calendar.DAY_OF_MONTH, delta);

        for (int i = 0; i < 12; ++i) {
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

        tableau.setDefaultEditor(Object.class, null);
        tableau.setRowSelectionAllowed(false);
        tableau.getTableHeader().setReorderingAllowed(false);

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

        actualWeek.setBackground(new java.awt.Color(102, 102, 102));
        actualWeek.setFont(new java.awt.Font("Arial", 0, 12)); 
        actualWeek.setForeground(new java.awt.Color(255, 255, 255));
        actualWeek.setBorderPainted(false);
        actualWeek.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        actualWeek.setFocusable(false);
        actualWeek.setBounds(130, 440, 110, 30);

        previousWeek.setBackground(new java.awt.Color(102, 102, 102));
        previousWeek.setFont(new java.awt.Font("Arial", 0, 12)); 
        previousWeek.setForeground(new java.awt.Color(255, 255, 255));
        previousWeek.setBorderPainted(false);
        previousWeek.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        previousWeek.setFocusable(false);
        previousWeek.setBounds(130, 440, 110, 30);

        nextWeek.setBackground(new java.awt.Color(102, 102, 102));
        nextWeek.setFont(new java.awt.Font("Arial", 0, 12));
        nextWeek.setForeground(new java.awt.Color(255, 255, 255));
        nextWeek.setBorderPainted(false);
        nextWeek.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        nextWeek.setFocusable(false);
        nextWeek.setBounds(130, 440, 110, 30);

        actualWeek.setFocusable(false);
        nextWeek.setFocusable(false);
        previousWeek.setFocusable(false);

        tab.setSize(5 * 170 - 1, 12 * 30 - 7);

        Insets insets = panel.getInsets();
        Dimension size = previousWeek.getPreferredSize();
        previousWeek.setBounds(620 + insets.left, 40 + insets.top, size.width, size.height);
        nextWeek.setBounds(760 + insets.left, 40 + insets.top, size.width, size.height);
        actualWeek.setBounds(685 + insets.left, 450 + insets.top, size.width, size.height);

        size = tab.getSize();
        tab.setBounds(320 + insets.left, 90 + insets.top, size.width, size.height);
        hours.setBounds(285 + insets.left, 95 + insets.top, size.width, size.height);

        tableau.getTableHeader().setFont(new Font("Arial", Font.BOLD, 12));

        panel.setBackground(Color.white);
        colorActualDay();
        displayEvent();

        tableau.addMouseListener(new Mouse());

    }

    public void infoPanel(int row, int col) {

        int ageText;

        UIManager UI = new UIManager();
        UI.put("OptionPane.background", Color.white);
        UI.put("Panel.background", Color.white);

        String text;
        Time heuretemp;
        Date datetemp = null;

        for (int i = 0; i < RdvList.size(); ++i) {

            if (numSécuPatient == 0 && RdvList.get(i).getDoctor().insuranceNumber == numSécuDocteur) {

                if (RdvList.get(i).getNumberRDV() == tableau.getValueAt(row, col).toString()) {

                    nom = RdvList.get(i).getPatient().getLastName();
                    prenom = RdvList.get(i).getPatient().getName();

                    ageText = RdvList.get(i).getPatient().getBorn();

                    age = String.valueOf(ageText);

                    informations = RdvList.get(i).getInformations();

                    antecedant = RdvList.get(i).getPatient().getAntecedent();

                    numRDV = RdvList.get(i).getNumberRDV();

                    heuretemp = RdvList.get(i).getTime();

                    datetemp = RdvList.get(i).getDate();
                }

            }
        }

        text = "Numéro de RDV: " + numRDV + "\nNom: " + nom + "\nPrénom: " + prenom
                + "\nAge: " + age + "\nInformations: " + informations
                + "\nAntécédants du patient: " + antecedant + "\nHeure: " + tempHour + "h00\n" + format.format(datetemp);

        JOptionPane.showOptionDialog(null, text, "Informations sur le RDV", JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, new Object[]{}, null);

    }

    public void setNextWeek() {

        int delta = -calendar.get(GregorianCalendar.DAY_OF_WEEK) + 2;

        calendar.add(Calendar.DAY_OF_MONTH, delta + 7);

        for (int i = 0; i < 5; i++) {
            days[i] = format.format(calendar.getTime());
            calendar.add(Calendar.DAY_OF_MONTH, 1);
        }

        model = new DefaultTableModel(event, days);

        model.fireTableDataChanged();

        tableau.setModel(model);

        displayEvent();

    }

    public void setPreviousWeek() {

        int delta = -calendar.get(GregorianCalendar.DAY_OF_WEEK) + 2;

        calendar.add(Calendar.DAY_OF_MONTH, delta - 7);

        for (int i = 0; i < 5; i++) {
            days[i] = format.format(calendar.getTime());
            calendar.add(Calendar.DAY_OF_MONTH, 1);
        }

        model = new DefaultTableModel(event, days);

        model.fireTableDataChanged();

        tableau.setModel(model);
        displayEvent();

    }

    public void setActualWeek() {

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
        displayEvent();

    }

    public void colorActualDay() {

        String date;

        calendar = Calendar.getInstance();

        date = format.format(calendar.getTime());

        calendar.add(Calendar.DATE, 0);

        TableColumnModel column = tableau.getColumnModel();

        for (int i = 0; i < days.length; ++i) {
            if (date.equals(days[i])) {
                column.getColumn(i).setCellRenderer(new ColumnColorRenderer(Color.lightGray));
            }
        }
    }

    public void displayEvent() {

        DoctorList = docteurDAO.find();

        PatientList = patientDAO.find();

        RdvList = rdvDao.find();

        for (int i = 0; i < RdvList.size(); ++i) {
            eventDate = format.format(RdvList.get(i).getDate());
            tempHour = RdvList.get(i).getTime().getHours();

            for (int k = 0; k < time.length; ++k) {

                for (int j = 0; j < days.length; ++j) {

                    if (time[k].getHour() == tempHour && days[j].equals(eventDate)) {

                        if (numSécuDocteur == 0 && RdvList.get(i).getPatient().insuranceNumber == numSécuPatient) {
                            posx = k;
                            posy = j;

                            eventDisplay = RdvList.get(i).getNumberRDV();

                            model.setValueAt(eventDisplay, posx, posy);
                        }

                        if (numSécuPatient == 0 && RdvList.get(i).getDoctor().insuranceNumber == numSécuDocteur) {
                            posx = k;
                            posy = j;

                            eventDisplay = RdvList.get(i).getNumberRDV();

                            model.setValueAt(eventDisplay, posx, posy);
                        }

                    }

                }
            }
        }

        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);

        for (int i = 0; i < days.length; ++i) {
            tableau.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }

    }

    public List<Doctor> getDoctorList() {
        return DoctorList;
    }

    public List<Patient> getPatientList() {
        return PatientList;
    }

    public List<RDV> getRdvList() {
        return RdvList;
    }

    public int getNumSécuPatient() {
        return numSécuPatient;
    }

    public int getNumSécuDocteur() {
        return numSécuDocteur;
    }

    public JTable getTableau() {
        return tableau;
    }
    
    

    public class ColumnColorRenderer extends DefaultTableCellRenderer {

        Color backgroundColor, foregroundColor;

        public ColumnColorRenderer(Color backgroundColor) {
            super();
            this.backgroundColor = backgroundColor;
        }

        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            Component cell = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
            cell.setBackground(backgroundColor);
            cell.setForeground(foregroundColor);
            return cell;
        }

    }

    public class StatusColumnCellRenderer extends DefaultTableCellRenderer {

        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int col) {

            //Cells are by default rendered as a JLabel.
            JLabel l = (JLabel) super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, col);

            l.setBackground(Color.GREEN);

            //Return the JLabel which renders the cell.
            return l;

        }
    }

    private static class NoSelectionModel extends DefaultListSelectionModel {

        @Override
        public void setAnchorSelectionIndex(final int anchorIndex) {
        }

        @Override
        public void setLeadAnchorNotificationEnabled(final boolean flag) {
        }

        @Override
        public void setLeadSelectionIndex(final int leadIndex) {
        }

        @Override
        public void setSelectionInterval(final int index0, final int index1) {
        }
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

    private class Mouse implements MouseListener {

        @Override
        public void mouseClicked(MouseEvent e) {

            int row = tableau.rowAtPoint(e.getPoint());

            int col = tableau.columnAtPoint(e.getPoint());

            //infoPanel(row, col);
            
            
            
             if (e.getClickCount() == 2) {
                    PopupWindow popup = new PopupWindow(row, col);
             }

        }

        @Override
        public void mousePressed(MouseEvent e) {
        }

        @Override
        public void mouseReleased(MouseEvent e) {
        }

        @Override
        public void mouseEntered(MouseEvent e) {
        }

        @Override
        public void mouseExited(MouseEvent e) {
        }

    }
}
