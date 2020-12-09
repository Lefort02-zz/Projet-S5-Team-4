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
import java.awt.font.TextAttribute;
import java.io.File;
import java.sql.Date;
import java.sql.Time;
import javax.swing.table.DefaultTableCellRenderer;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.imageio.ImageIO;
import javax.swing.table.TableCellRenderer;

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
    private int numSécuDocteur = 0;

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

    private int posx, posy, tempHour, row, col;

    private JPanel panelInfo;
    private JLabel LnumRdv, Lnom, Lprenom, Lage, Lantecedent, Linfo, Ldate, Lheure, Lsexe;
    private JLabel numRdv, nom, prenom, age, antecedent, info, date, heure, sexe;
    private JButton deleteRdv;
    private JFrame frame;
    private JLabel quickInfo, infosQ;

    private JButton profile;
    private JPopupMenu profileMenu;
    private JMenuItem logoutMenuItem, profileMenuItem;
    private JButton deleteAccount;

    public PanelProfil(Person p) {

        if (p.getClass() == Doctor.class) {
            this.numSécuDocteur = p.getInsuranceNumber();
            this.numSécuPatient = 0;
        } else {
            this.numSécuPatient = p.getInsuranceNumber();
            this.numSécuDocteur = 0;
        }

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

    private void buildPanel() {

        panel = new JPanel();

        hours = new JList(time);

        quickInfo = new JLabel();
        panel.add(quickInfo);
        Insets insetsI = panel.getInsets();
        Dimension size = quickInfo.getPreferredSize();
        quickInfo.setBounds(1600 + insetsI.left, 300 + insetsI.top, size.width, size.height);

        infosQ = new JLabel();
        panel.add(infosQ);
        size = infosQ.getPreferredSize();
        infosQ.setBounds(1600 + insetsI.left, 350 + insetsI.top, size.width, size.height);

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
        size = previousWeek.getPreferredSize();
        previousWeek.setBounds(830 + insets.left, 250 + insets.top, size.width, size.height);
        nextWeek.setBounds(960 + insets.left, 250 + insets.top, size.width, size.height);
        actualWeek.setBounds(900 + insets.left, 675 + insets.top, size.width, size.height);

        size = tab.getSize();
        tab.setBounds(540 + insets.left, 300 + insets.top, size.width, size.height);
        hours.setBounds(500 + insets.left, 305 + insets.top, size.width, size.height);

        tableau.getTableHeader().setFont(new Font("Arial", Font.BOLD, 12));
        for (int i = 0; i < days.length; ++i) {
            tableau.getColumnModel().getColumn(i).setHeaderRenderer(new HorizontalAlignmentHeaderRenderer(SwingConstants.CENTER));
        }
        tableau.getTableHeader().setResizingAllowed(false);

        panel.setBackground(Color.white);
        colorActualDay();
        displayEvent();
        displayWelcome();

        tableau.addMouseListener(new Mouse());

        profile = new JButton("Profile");
        size = profile.getPreferredSize();
        profile.setBounds(15 + insets.left, 10 + insets.top, size.width, size.height);
        panel.add(profile);
        profile.setFocusable(false);

        profileMenu = new JPopupMenu();
        profileMenu.add(profileMenuItem = new JMenuItem("Profile"));
        profileMenu.add(logoutMenuItem = new JMenuItem("Logout"));

        profile.addActionListener(new ButtonListener());
        profileMenuItem.addActionListener(new ButtonListener());
        logoutMenuItem.addActionListener(new ButtonListener());

        if (numSécuPatient == 0) {

            ArrayList<Patient> historiqueList = new ArrayList<>();
            Patient patientTemp;
            String col[] = {"Nom", "Prénom", "Age", "Sexe", "Antécédants"};

            String nom, prenom, sexe, ante;
            int age;

            DefaultTableModel modelHistorique = new DefaultTableModel(col, 0);

            JTable historiquePatient = new JTable(modelHistorique);

            for (int i = 0; i < RdvList.size(); ++i) {

                if (RdvList.get(i).getDoctor().insuranceNumber == numSécuDocteur) {

                    patientTemp = RdvList.get(i).getPatient();

                    if (historiqueList.contains(patientTemp) == false) {
                        historiqueList.add(patientTemp);
                    }

                }

            }

            for (int k = 0; k < historiqueList.size(); ++k) {
                nom = historiqueList.get(k).getLastName();
                prenom = historiqueList.get(k).getName();
                age = historiqueList.get(k).getBorn();
                sexe = historiqueList.get(k).getSexe();
                ante = historiqueList.get(k).getAntecedent();

                Object[] data = {nom, prenom, age, sexe, ante};

                modelHistorique.addRow(data);
            }

            JScrollPane hisPatient = new JScrollPane(historiquePatient);

            panel.add(hisPatient);
            hisPatient.setSize(5 * 170 - 1, 12 * 30 - 7);

            size = hisPatient.getSize();

            System.out.println(size.width);
            hisPatient.setBounds(540 + insets.left, 730 + insets.top, size.width, size.height);

            historiquePatient.setRowHeight(30);

            historiquePatient.getTableHeader().setFont(new Font("Arial", Font.BOLD, 12));

            for (int i = 0; i < col.length; ++i) {
                historiquePatient.getColumnModel().getColumn(i).setHeaderRenderer(new HorizontalAlignmentHeaderRenderer(SwingConstants.CENTER));
            }
            historiquePatient.getTableHeader().setResizingAllowed(false);
            historiquePatient.setDefaultEditor(Object.class, null);
            historiquePatient.getTableHeader().setReorderingAllowed(false);

        }
    }

    public void infoPanel(int row, int col) {

        frame = new JFrame("Information sur votre rendez-vous");
        frame.setSize(600, 500);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        JPanel panelInfo = new JPanel();
        panelInfo.setLayout(null);

        int ageText;
        Time heuretemp;
        Date datetemp = null;

        //ImageIcon icon = new ImageIcon(this.getClass().getResource("/img/trash.png"));
        deleteRdv = new JButton("Supprimerndez-vous");

        panelInfo.add(deleteRdv);

        Insets insetsB = panelInfo.getInsets();
        Dimension sizeB = deleteRdv.getPreferredSize();
        deleteRdv.setBounds(400 + insetsB.left, 10 + insetsB.top, sizeB.width, sizeB.height);
        //deleteRdv.setSize(15, 20);
        deleteRdv.setFocusable(false);

        deleteRdv.addActionListener(new ButtonListener());

        setDefaultCloseOperation(frame.DISPOSE_ON_CLOSE);

        LnumRdv = new JLabel("Numéro de rendez-vous: ");
        Lnom = new JLabel("Nom: ");
        Lprenom = new JLabel("Prénom: ");
        Lage = new JLabel("Age: ");
        Lantecedent = new JLabel("Antécédents: ");
        Linfo = new JLabel("Informations: ");
        Ldate = new JLabel("Date: ");
        Lheure = new JLabel("Heure: ");
        Lsexe = new JLabel("Sexe: ");

        panelInfo.add(LnumRdv);
        panelInfo.add(Lnom);
        panelInfo.add(Lprenom);
        panelInfo.add(Lage);
        panelInfo.add(Lsexe);
        panelInfo.add(Lantecedent);
        panelInfo.add(Linfo);
        panelInfo.add(Ldate);
        panelInfo.add(Lheure);

        panelInfo.setBackground(Color.WHITE);

        Insets insetsI = panelInfo.getInsets();
        Dimension size = LnumRdv.getPreferredSize();
        LnumRdv.setBounds(65 + insetsI.left, 100 + insetsI.top, size.width, size.height);
        Lnom.setBounds(65 + insetsI.left, 120 + insetsI.top, size.width, size.height);
        Lprenom.setBounds(65 + insetsI.left, 140 + insetsI.top, size.width, size.height);
        Lage.setBounds(65 + insetsI.left, 160 + insetsI.top, size.width, size.height);
        Lsexe.setBounds(65 + insetsI.left, 180 + insetsI.top, size.width, size.height);
        Lantecedent.setBounds(65 + insetsI.left, 200 + insetsI.top, size.width, size.height);
        Linfo.setBounds(65 + insetsI.left, 220 + insetsI.top, size.width, size.height);
        Ldate.setBounds(65 + insetsI.left, 240 + insetsI.top, size.width, size.height);
        Lheure.setBounds(65 + insetsI.left, 260 + insetsI.top, size.width, size.height);

        numRdv = new JLabel();
        nom = new JLabel();
        prenom = new JLabel();
        age = new JLabel();
        antecedent = new JLabel();
        info = new JLabel();
        date = new JLabel();
        heure = new JLabel();
        sexe = new JLabel();

        panelInfo.add(numRdv);
        panelInfo.add(nom);
        panelInfo.add(prenom);
        panelInfo.add(age);
        panelInfo.add(sexe);
        panelInfo.add(antecedent);
        panelInfo.add(info);
        panelInfo.add(date);
        panelInfo.add(heure);

        for (int i = 0; i < RdvList.size(); ++i) {

            if (numSécuPatient == 0 && RdvList.get(i).getDoctor().insuranceNumber == numSécuDocteur) { //Affiche les informations si la personne connecté est un docteur

                if (RdvList.get(i).getNumberRDV() == tableau.getValueAt(row, col).toString()) {

                    numRdv.setText(RdvList.get(i).getNumberRDV());

                    nom.setText(RdvList.get(i).getPatient().getLastName());

                    prenom.setText(RdvList.get(i).getPatient().getName());

                    ageText = RdvList.get(i).getPatient().getBorn();

                    age.setText(String.valueOf(ageText));

                    sexe.setText(RdvList.get(i).getPatient().getSexe());

                    info.setText(RdvList.get(i).getInformations());

                    antecedent.setText(RdvList.get(i).getPatient().getAntecedent());

                    datetemp = RdvList.get(i).getDate();
                    date.setText(format.format(datetemp));

                    heuretemp = RdvList.get(i).getTime();
                    heure.setText(heuretemp.toString());

                    size = antecedent.getPreferredSize();
                    numRdv.setFont(new Font("Arial", Font.PLAIN, 12));
                    nom.setFont(new Font("Arial", Font.PLAIN, 12));
                    prenom.setFont(new Font("Arial", Font.PLAIN, 12));
                    age.setFont(new Font("Arial", Font.PLAIN, 12));
                    antecedent.setFont(new Font("Arial", Font.PLAIN, 12));
                    info.setFont(new Font("Arial", Font.PLAIN, 12));
                    date.setFont(new Font("Arial", Font.PLAIN, 12));
                    heure.setFont(new Font("Arial", Font.PLAIN, 12));
                    sexe.setFont(new Font("Arial", Font.PLAIN, 12));

                    numRdv.setBounds(350 + insetsI.left, 100 + insetsI.top, size.width, size.height);
                    nom.setBounds(350 + insetsI.left, 120 + insetsI.top, size.width, size.height);
                    prenom.setBounds(350 + insetsI.left, 140 + insetsI.top, size.width, size.height);
                    age.setBounds(350 + insetsI.left, 160 + insetsI.top, size.width, size.height);
                    sexe.setBounds(350 + insetsI.left, 180 + insetsI.top, size.width, size.height);
                    antecedent.setBounds(350 + insetsI.left, 200 + insetsI.top, size.width, size.height);
                    info.setBounds(350 + insetsI.left, 220 + insetsI.top, size.width, size.height);
                    heure.setBounds(350 + insetsI.left, 260 + insetsI.top, size.width, size.height);
                    date.setBounds(350 + insetsI.left, 240 + insetsI.top, size.width, size.height);

                }

            }

            if (numSécuDocteur == 0 && RdvList.get(i).getPatient().insuranceNumber == numSécuPatient) { //Affiche les informations si la personne connecté est un patient

                if (RdvList.get(i).getNumberRDV() == tableau.getValueAt(row, col).toString()) {

                    numRdv.setText(RdvList.get(i).getNumberRDV());

                    nom.setText(RdvList.get(i).getDoctor().getLastName());

                    prenom.setText(RdvList.get(i).getDoctor().getName());

                    info.setText(RdvList.get(i).getInformations());

                    datetemp = RdvList.get(i).getDate();
                    date.setText(format.format(datetemp));

                    heuretemp = RdvList.get(i).getTime();
                    heure.setText(heuretemp.toString());

                    size = date.getPreferredSize();
                    numRdv.setFont(new Font("Arial", Font.PLAIN, 12));
                    nom.setFont(new Font("Arial", Font.PLAIN, 12));
                    prenom.setFont(new Font("Arial", Font.PLAIN, 12));
                    info.setFont(new Font("Arial", Font.PLAIN, 12));
                    date.setFont(new Font("Arial", Font.PLAIN, 12));
                    heure.setFont(new Font("Arial", Font.PLAIN, 12));

                    numRdv.setBounds(350 + insetsI.left, 100 + insetsI.top, size.width, size.height);
                    nom.setBounds(350 + insetsI.left, 120 + insetsI.top, size.width, size.height);
                    prenom.setBounds(350 + insetsI.left, 140 + insetsI.top, size.width, size.height);
                    info.setBounds(350 + insetsI.left, 160 + insetsI.top, size.width, size.height);
                    heure.setBounds(350 + insetsI.left, 180 + insetsI.top, size.width, size.height);
                    date.setBounds(350 + insetsI.left, 200 + insetsI.top, size.width, size.height);

                    Lage.setVisible(false);
                    Lsexe.setVisible(false);
                    Lantecedent.setVisible(false);

                    Linfo.setBounds(65 + insetsI.left, 160 + insetsI.top, size.width, size.height);
                    Ldate.setBounds(65 + insetsI.left, 180 + insetsI.top, size.width, size.height);
                    Lheure.setBounds(65 + insetsI.left, 200 + insetsI.top, size.width, size.height);

                }

            }
        }

        frame.add(panelInfo);

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

    public void displayWelcome() {

        JLabel welcome = new JLabel();

        if (numSécuPatient == 0) {
            for (int i = 0; i < DoctorList.size(); ++i) {

                if (DoctorList.get(i).insuranceNumber == numSécuDocteur) {  //Si la personne connecté est un docteur

                    welcome.setText("Bienvenue Docteur " + DoctorList.get(i).getName() + " " + DoctorList.get(i).getLastName());

                }
            }

        }

        if (numSécuDocteur == 0) {
            for (int i = 0; i < PatientList.size(); ++i) {

                if (PatientList.get(i).insuranceNumber == numSécuPatient) {  //Si la personne connecté est un patient

                    welcome.setText("Bienvenue " + PatientList.get(i).getLastName() + " " + PatientList.get(i).getName());

                }
            }

        }

        Insets insetsW = panel.getInsets();

        welcome.setFont(new Font("Arial", Font.BOLD, 20));
        Dimension size = welcome.getPreferredSize();
        welcome.setBounds(800 + insetsW.left, 10 + insetsW.top, size.width, size.height);

        panel.add(welcome);

    }

    public void quickInfo(int row, int col) {

        String informations;

        quickInfo.setText("Informations rapides sur votre rendez-vous: ");
        Font font = quickInfo.getFont();
        Map attributes = font.getAttributes();
        attributes.put(TextAttribute.UNDERLINE, TextAttribute.UNDERLINE_ON);
        quickInfo.setFont(font.deriveFont(attributes));

        Insets insetsI = panel.getInsets();
        Dimension size = quickInfo.getPreferredSize();
        quickInfo.setBounds(1500 + insetsI.left, 300 + insetsI.top, size.width, size.height);

        for (int i = 0; i < RdvList.size(); ++i) {

            if (numSécuPatient == 0 && RdvList.get(i).getDoctor().insuranceNumber == numSécuDocteur) { //Affiche les informations si la personne connecté est un docteur

                if (RdvList.get(i).getNumberRDV() == tableau.getValueAt(row, col).toString()) {

                    informations = "Vous avez rendez-vous avec " + RdvList.get(i).getPatient().getLastName() + " ";

                    informations += RdvList.get(i).getPatient().getName() + ", ";

                    informations += "agé de " + RdvList.get(i).getPatient().getBorn() + ", pour ";

                    informations += RdvList.get(i).getInformations() + ".";

                    infosQ.setText(informations);

                }

            }

            if (numSécuDocteur == 0 && RdvList.get(i).getPatient().insuranceNumber == numSécuPatient) { //Affiche les informations si la personne connecté est un patient

                if (RdvList.get(i).getNumberRDV() == tableau.getValueAt(row, col).toString()) {

                    informations = "Vous avez rendez-vous avec le docteur " + RdvList.get(i).getDoctor().getLastName() + " ";

                    informations += RdvList.get(i).getDoctor().getName() + ", ";

                    informations += "pour " + RdvList.get(i).getInformations() + ".";

                    infosQ.setText(informations);
                }

            }
        }

        insetsI = panel.getInsets();
        size = infosQ.getPreferredSize();
        infosQ.setBounds(1475 + insetsI.left, 350 + insetsI.top, size.width, size.height);
        infosQ.setFont(new Font("Arial", Font.PLAIN, 12));

    }

    public void profilePopup() {
        frame = new JFrame("Profile");
        frame.setSize(600, 500);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        setDefaultCloseOperation(frame.DISPOSE_ON_CLOSE);

        JPanel profilPanel = new JPanel();
        profilPanel.setLayout(null);
        profilPanel.setBackground(Color.WHITE);

        JLabel titre = new JLabel("VOS INFORMATIONS");
        profilPanel.add(titre);
        Insets insetsTitle = profilPanel.getInsets();
        Dimension size = titre.getPreferredSize();
        titre.setBounds(230 + insetsTitle.left, 10 + insetsTitle.top, size.width, size.height);

        deleteAccount = new JButton("SUPPRIMER VOTRE COMPTE");

        profilPanel.add(deleteAccount);
        Insets insetsD = profilPanel.getInsets();
        size = deleteAccount.getPreferredSize();
        deleteAccount.setBounds(200 + insetsD.left, 400 + insetsD.top, size.width, size.height);
        deleteAccount.setFocusable(false);
        deleteAccount.setBackground(Color.red);
        deleteAccount.addActionListener(new ButtonListener());

        Lnom = new JLabel("Nom: ");
        Lprenom = new JLabel("Prénom: ");
        Lage = new JLabel("Age: ");
        Lantecedent = new JLabel("Antécédents: ");
        Lsexe = new JLabel("Sexe: ");
        JLabel LnumSecu = new JLabel("Numéro de sécurité social: ");
        JLabel Lpsw = new JLabel("Mot de passe: ");

        profilPanel.add(Lnom);
        profilPanel.add(Lprenom);
        profilPanel.add(Lage);
        profilPanel.add(Lsexe);
        profilPanel.add(Lantecedent);
        profilPanel.add(LnumSecu);
        profilPanel.add(Lpsw);

        Insets insetsI = profilPanel.getInsets();
        size = LnumSecu.getPreferredSize();
        Lnom.setBounds(65 + insetsI.left, 120 + insetsI.top, size.width, size.height);
        Lprenom.setBounds(65 + insetsI.left, 140 + insetsI.top, size.width, size.height);
        Lage.setBounds(65 + insetsI.left, 160 + insetsI.top, size.width, size.height);
        Lsexe.setBounds(65 + insetsI.left, 180 + insetsI.top, size.width, size.height);
        Lantecedent.setBounds(65 + insetsI.left, 200 + insetsI.top, size.width, size.height);
        LnumSecu.setBounds(65 + insetsI.left, 220 + insetsI.top, size.width, size.height);
        Lpsw.setBounds(65 + insetsI.left, 240 + insetsI.top, size.width, size.height);

        nom = new JLabel();
        prenom = new JLabel();
        age = new JLabel();
        antecedent = new JLabel();
        sexe = new JLabel();
        JLabel numSecu = new JLabel();
        JLabel psw = new JLabel();

        nom.setFont(new Font("Arial", Font.PLAIN, 12));
        prenom.setFont(new Font("Arial", Font.PLAIN, 12));
        age.setFont(new Font("Arial", Font.PLAIN, 12));
        sexe.setFont(new Font("Arial", Font.PLAIN, 12));
        antecedent.setFont(new Font("Arial", Font.PLAIN, 12));
        psw.setFont(new Font("Arial", Font.PLAIN, 12));
        numSecu.setFont(new Font("Arial", Font.PLAIN, 12));

        profilPanel.add(nom);
        profilPanel.add(prenom);
        profilPanel.add(age);
        profilPanel.add(sexe);
        profilPanel.add(antecedent);
        profilPanel.add(numSecu);
        profilPanel.add(psw);

        int ageText, numsecuText;

        if (numSécuPatient == 0) { //Affiche les informations si la personne connecté est un docteur

            for (int i = 0; i < DoctorList.size(); ++i) {

                if (numSécuPatient == 0 && DoctorList.get(i).getInsuranceNumber() == numSécuDocteur) {

                    Lantecedent.setText("Spécialité");

                    ageText = DoctorList.get(i).getBorn();

                    nom.setText(DoctorList.get(i).getName());
                    prenom.setText(DoctorList.get(i).getLastName());
                    age.setText(String.valueOf(ageText));
                    sexe.setText(DoctorList.get(i).getSexe());
                    antecedent.setText(DoctorList.get(i).getSpeciality());

                    numsecuText = DoctorList.get(i).getInsuranceNumber();

                    numSecu.setText(String.valueOf(numsecuText));
                    psw.setText(DoctorList.get(i).getPassWord());

                    size = antecedent.getPreferredSize();
                    nom.setBounds(350 + insetsI.left, 120 + insetsI.top, 50, size.height);
                    prenom.setBounds(350 + insetsI.left, 140 + insetsI.top, 50, size.height);
                    age.setBounds(350 + insetsI.left, 160 + insetsI.top, 50, size.height);
                    sexe.setBounds(350 + insetsI.left, 180 + insetsI.top, 50, size.height);
                    antecedent.setBounds(350 + insetsI.left, 200 + insetsI.top, size.width, size.height);
                    numSecu.setBounds(350 + insetsI.left, 220 + insetsI.top, 50, size.height);
                    psw.setBounds(350 + insetsI.left, 240 + insetsI.top, 50, size.height);

                }

            }

        }

        if (numSécuDocteur == 0) { //Affiche les informations si la personne connecté est un patient

            for (int i = 0; i < PatientList.size(); ++i) {

                if (PatientList.get(i).getInsuranceNumber() == numSécuPatient) {

                    ageText = PatientList.get(i).getBorn();

                    nom.setText(PatientList.get(i).getName());
                    prenom.setText(PatientList.get(i).getLastName());
                    age.setText(String.valueOf(ageText));
                    sexe.setText(PatientList.get(i).getSexe());
                    antecedent.setText(PatientList.get(i).getAntecedent());

                    numsecuText = PatientList.get(i).getInsuranceNumber();

                    numSecu.setText(String.valueOf(numsecuText));
                    psw.setText(PatientList.get(i).getPassWord());

                    size = antecedent.getPreferredSize();

                    nom.setBounds(350 + insetsI.left, 120 + insetsI.top, size.width, size.height);
                    prenom.setBounds(350 + insetsI.left, 140 + insetsI.top, size.width, size.height);
                    age.setBounds(350 + insetsI.left, 160 + insetsI.top, size.width, size.height);
                    sexe.setBounds(350 + insetsI.left, 180 + insetsI.top, size.width, size.height);
                    antecedent.setBounds(350 + insetsI.left, 200 + insetsI.top, size.width, size.height);
                    numSecu.setBounds(350 + insetsI.left, 220 + insetsI.top, size.width, size.height);
                    psw.setBounds(350 + insetsI.left, 240 + insetsI.top, size.width, size.height);
                }

            }

        }

        frame.add(profilPanel);

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

    class HorizontalAlignmentHeaderRenderer implements TableCellRenderer {

        private int horizontalAlignment = SwingConstants.LEFT;

        public HorizontalAlignmentHeaderRenderer(int horizontalAlignment) {
            this.horizontalAlignment = horizontalAlignment;
        }

        @Override
        public Component getTableCellRendererComponent(
                JTable table, Object value, boolean isSelected,
                boolean hasFocus, int row, int column) {
            TableCellRenderer r = table.getTableHeader().getDefaultRenderer();
            JLabel l = (JLabel) r.getTableCellRendererComponent(
                    table, value, isSelected, hasFocus, row, column);
            l.setHorizontalAlignment(horizontalAlignment);
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
            if (e.getSource() == deleteRdv) {
                for (int i = 0; i < RdvList.size(); ++i) {
                    if (RdvList.get(i).getNumberRDV() == tableau.getValueAt(row, col).toString()) {
                        rdvDao.delete(tableau.getValueAt(row, col).toString());
                        JOptionPane.showMessageDialog(null, tableau.getValueAt(row, col).toString() + " supprimée");
                    }

                }

                frame.dispose();

                model = new DefaultTableModel(event, days);

                model.fireTableDataChanged();

                tableau.setModel(model);
                displayEvent();

            }

            if (e.getSource() == profile) {
                profileMenu.show(profile, profile.getWidth(), profile.getHeight() / 2);
            }

            if (e.getSource() == profileMenuItem) {
                System.out.println("Profile popup");
                profilePopup();
            }

            if (e.getSource() == deleteAccount) {

                int dialogButton = JOptionPane.YES_NO_OPTION;
                int dialogResult = JOptionPane.showConfirmDialog(null, "Voulez-vous vraiment supprimer votre conmpte ?", "Warning", dialogButton);

                if (dialogResult == JOptionPane.YES_OPTION) {

                    if (numSécuDocteur == 0) { //Si la personne connecté est un patient

                        patientDAO.delete(numSécuPatient);

                    }

                    if (numSécuPatient == 0) { //Si la personne connecté est un docteur

                        docteurDAO.delete(numSécuDocteur);

                    }

                    frame.dispose();
                    dispose();

                }
            }

            if (e.getSource() == logoutMenuItem) {
                dispose();
                new Login().setVisible(true);

            }

        }
    }

    private class Mouse implements MouseListener {

        @Override
        public void mouseClicked(MouseEvent e) {

            row = tableau.rowAtPoint(e.getPoint());

            col = tableau.columnAtPoint(e.getPoint());

            if (e.getClickCount() == 1) {
                quickInfo(row, col);
            }

            if (e.getClickCount() == 2) {
                infoPanel(row, col);
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
