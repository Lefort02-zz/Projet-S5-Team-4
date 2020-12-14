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
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumnModel;

import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.font.TextAttribute;
import java.io.File;
import java.sql.Date;
import java.sql.Time;
import java.text.DateFormat;
import java.text.ParseException;
import java.time.format.DateTimeFormatter;
import java.time.temporal.WeekFields;
import javax.swing.table.DefaultTableCellRenderer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.event.MenuEvent;
import javax.swing.event.MenuListener;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import org.jfree.ui.RefineryUtilities;

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

    private int posx, posy, tempHour, row, col, row2, col2;

    private JPanel panelInfo;
    private JLabel LnumRdv, Lnom, Lprenom, Lage, Lantecedent, Linfo, Ldate, Lheure, Lsexe, numSecu;
    private JLabel numRdv, nom, prenom, age, antecedent, info, date, heure, sexe;
    private JButton deleteRdv;
    private JFrame frame;
    private JLabel quickInfo, infosQ;

    private JButton profile;
    private JPopupMenu profileMenu;
    private JMenuItem logoutMenuItem, profileMenuItem, statMenuItem;
    private JButton deleteAccount;

    private JTable historiquePatient;

    private JButton addRdv;
    private JList listDoc;
    private JPopupMenu addRdvMenu;
    private JMenu[] doctorItems;
    private JMenuItem[] speItems;
    private ArrayList<RDV> listNewRdv = new ArrayList<>();
    private int numSecuDocTemp, colorX, colorY;
    private boolean researchRdv = false;
    private JButton cancelResearch;

    private Patient p;
    private JFrame frameAnte = new JFrame();
    private JPanel panelAnte = new JPanel();
    private JTextField antnew;
    private JButton update = new JButton("Update");

    private RDV rdvNew;
    private JFrame popNewRdvFrame = new JFrame();
    private JPanel panelNewRdv = new JPanel();
    private JTextField raisonField;
    private JButton validerButton = new JButton("Valider le rendez-vous");
    private int row3, col3;

    public PanelProfil() throws HeadlessException {
    }

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
        panel.setBackground(new java.awt.Color(218, 227, 230));

        hours = new JList(time);
        hours.setBackground(new java.awt.Color(218, 227, 230));

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

        actualWeek.setBackground(new java.awt.Color(18, 92, 117));
        actualWeek.setFont(new java.awt.Font("Arial", 0, 12));
        actualWeek.setForeground(new java.awt.Color(255, 255, 255));
        actualWeek.setBorderPainted(false);
        actualWeek.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        actualWeek.setFocusable(false);
        actualWeek.setBounds(130, 440, 110, 30);

        previousWeek.setBackground(new java.awt.Color(18, 92, 117));
        previousWeek.setFont(new java.awt.Font("Arial", 0, 12));
        previousWeek.setForeground(new java.awt.Color(255, 255, 255));
        previousWeek.setBorderPainted(false);
        previousWeek.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        previousWeek.setFocusable(false);
        previousWeek.setBounds(130, 440, 110, 30);

        nextWeek.setBackground(new java.awt.Color(18, 92, 117));
        nextWeek.setFont(new java.awt.Font("Arial", 0, 12));
        nextWeek.setForeground(new java.awt.Color(255, 255, 255));
        nextWeek.setBorderPainted(false);
        nextWeek.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        nextWeek.setFocusable(false);
        nextWeek.setBounds(130, 440, 110, 30);

        actualWeek.setFocusable(false);
        nextWeek.setFocusable(false);
        previousWeek.setFocusable(false);

        tab.setSize(5 * 170 - 1, 12 * 30);

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

        //panel.setBackground(Color.white);
        colorActualDay();
        displayEvent();
        displayWelcome();

        tableau.addMouseListener(new Mouse());

        profile = new JButton("Profile");
        profile.setBackground(new java.awt.Color(18, 92, 117));
        profile.setForeground(Color.white);
        size = profile.getPreferredSize();
        profile.setBounds(15 + insets.left, 10 + insets.top, size.width, size.height);
        panel.add(profile);
        profile.setFocusable(false);

        profileMenu = new JPopupMenu();
        profileMenu.add(profileMenuItem = new JMenuItem("Profile"));
        if (numSécuPatient == 0) {
            profileMenu.add(statMenuItem = new JMenuItem("Statistiques"));

            statMenuItem.addActionListener(new ButtonListener());

        }
        profileMenu.add(logoutMenuItem = new JMenuItem("Logout"));

        profile.addActionListener(new ButtonListener());
        profileMenuItem.addActionListener(new ButtonListener());
        logoutMenuItem.addActionListener(new ButtonListener());

        if (numSécuPatient == 0) {

            ArrayList<Patient> historiqueList = new ArrayList<>();
            Patient patientTemp;
            String col[] = {"Sécurité Sociale", "Nom", "Prénom", "Age", "Sexe", "Antécédants"};

            String nom, prenom, sexe, ante;
            int numSecu;
            int age;

            DefaultTableModel modelHistorique = new DefaultTableModel(col, 0);

            historiquePatient = new JTable(modelHistorique);

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
                numSecu = historiqueList.get(k).getInsuranceNumber();

                Object[] data = {numSecu, nom, prenom, age, sexe, ante};

                modelHistorique.addRow(data);
            }

            JScrollPane hisPatient = new JScrollPane(historiquePatient);

            panel.add(hisPatient);
            hisPatient.setSize(5 * 170 - 1, 12 * 30 - 7);

            size = hisPatient.getSize();

            hisPatient.setBounds(540 + insets.left, 730 + insets.top, size.width, size.height);

            historiquePatient.setRowHeight(30);
            historiquePatient.addMouseListener(new Mouse1());

            DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
            centerRenderer.setHorizontalAlignment(JLabel.CENTER);

            for (int i = 0; i < col.length; ++i) {
                historiquePatient.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
            }

            historiquePatient.getTableHeader().setFont(new Font("Arial", Font.BOLD, 12));

            for (int i = 0; i < col.length; ++i) {
                historiquePatient.getColumnModel().getColumn(i).setHeaderRenderer(new HorizontalAlignmentHeaderRenderer(SwingConstants.CENTER));
            }
            historiquePatient.getTableHeader().setResizingAllowed(false);
            historiquePatient.setDefaultEditor(Object.class, null);
            historiquePatient.getTableHeader().setReorderingAllowed(false);
        }

        if (numSécuDocteur == 0) {

            addRdv = new JButton("Nouveau rendez-vous");
            cancelResearch = new JButton("Annuler la recherche de rendez-vous");

            addRdv.setBackground(new java.awt.Color(18, 92, 117));
            addRdv.setFont(new java.awt.Font("Arial", 0, 12));
            addRdv.setForeground(Color.white);
            addRdv.setBorderPainted(false);
            addRdv.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
            addRdv.setFocusable(false);

            cancelResearch.setBackground(new java.awt.Color(102, 102, 102));
            cancelResearch.setFont(new java.awt.Font("Arial", 0, 12));
            cancelResearch.setBorderPainted(false);
            cancelResearch.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
            cancelResearch.setFocusable(false);

            size = addRdv.getSize();
            addRdv.setBounds(100 + insets.left, 300 + insets.top, 200, 30);

            size = cancelResearch.getSize();
            cancelResearch.setBounds(100 + insets.left, 260 + insets.top, 250, 30);
            cancelResearch.setVisible(false);

            panel.add(addRdv);
            panel.add(cancelResearch);

            addRdvMenu = new JPopupMenu();
            doctorItems = new JMenu[DoctorList.size()];
            speItems = new JMenuItem[doctorItems.length];

            for (int i = 0; i < DoctorList.size(); ++i) {
                addRdvMenu.add(doctorItems[i] = new JMenu("Docteur " + DoctorList.get(i).getName()));

            }
            for (int i = 0; i < doctorItems.length; ++i) {
                doctorItems[i].add(speItems[i] = new JMenuItem(DoctorList.get(i).getSpeciality()));
                speItems[i].addActionListener(new ButtonListener());

            }

            addRdv.addActionListener(new ButtonListener());

            cancelResearch.addActionListener(new ButtonListener());

        }

        for (int i = 0; i < RdvList.size(); ++i) {
            System.out.println(RdvList.get(i).getNumberRDV());
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
        deleteRdv = new JButton("Supprimer rendez-vous");
        deleteRdv.setBackground(new java.awt.Color(18, 92, 117));
        deleteRdv.setForeground(Color.white);

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

        panelInfo.setBackground(new java.awt.Color(218, 227, 230));

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

        if (researchRdv == true) {
            newRdv();
        }

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

        if (researchRdv == true) {
            newRdv();
        }

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

        if (researchRdv == true) {
            newRdv();
        }

    }

    public void colorActualDay() {
        /*
        String date;

        calendar = Calendar.getInstance();

        date = format.format(calendar.getTime());

        calendar.add(Calendar.DATE, 0);

        TableColumnModel column = tableau.getColumnModel();

        for (int i = 0; i < days.length; ++i) {
            if (date.equals(days[i])) {
                column.getColumn(i).setCellRenderer(new ColumnColorRenderer(Color.lightGray));
            }
        }*/

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
        welcome.setBounds(800 + insetsW.left, 10 + insetsW.top, size.width + 50, size.height);

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
        profilPanel.setBackground(new java.awt.Color(218, 227, 230));

        JLabel titre = new JLabel("VOS INFORMATIONS");
        profilPanel.add(titre);
        Insets insetsTitle = profilPanel.getInsets();
        Dimension size = titre.getPreferredSize();
        titre.setBounds(230 + insetsTitle.left, 10 + insetsTitle.top, size.width + 100, size.height);

        deleteAccount = new JButton("SUPPRIMER VOTRE COMPTE");

        profilPanel.add(deleteAccount);
        Insets insetsD = profilPanel.getInsets();
        size = deleteAccount.getPreferredSize();
        deleteAccount.setBounds(150 + insetsD.left, 400 + insetsD.top, size.width + 100, size.height);
        deleteAccount.setFocusable(false);
        deleteAccount.setBackground(new java.awt.Color(178, 34, 34));
        deleteAccount.setForeground(Color.white);
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

    public void newRdv() {

        String rdvImpString;
        int horaire;

        for (int i = 0; i < RdvList.size(); ++i) {
            if (RdvList.get(i).getDoctor().getInsuranceNumber() == numSecuDocTemp) {
                listNewRdv.add(RdvList.get(i));
            }

            if (RdvList.get(i).getPatient().getInsuranceNumber() == numSécuPatient) {

                if (!listNewRdv.contains(RdvList.get(i))) {
                    listNewRdv.add(RdvList.get(i));
                }
            }
        }

        for (int i = 0; i < listNewRdv.size(); ++i) {
            System.out.println("RDV " + listNewRdv.get(i).getNumberRDV());
        }

        tableau.setShowHorizontalLines(true);
        tableau.setShowVerticalLines(true);

        CustomRenderer r = new CustomRenderer();

        TableColumnModel columnModel = tableau.getColumnModel();
        int cc = columnModel.getColumnCount();
        for (int c = 0; c < cc; c++) {
            TableColumn column = columnModel.getColumn(c);
            column.setCellRenderer(r);
        }

        for (int i = 0; i < listNewRdv.size(); ++i) {

            for (int k = 0; k < time.length; ++k) {

                for (int j = 0; j < days.length; ++j) {

                    rdvImpString = format.format(listNewRdv.get(i).getDate());
                    horaire = listNewRdv.get(i).getTime().getHours();

                    if (time[k].getHour() == horaire && days[j].equals(rdvImpString)) {

                        colorX = k;
                        colorY = j;

                        r.setHighlighted(colorX, colorY, true);

                    }

                }
            }
        }

    }

    public void newRdvPopup(int row, int col) {

        popNewRdvFrame = new JFrame("Nouveaux rendez-vous");

        panelNewRdv.removeAll();

        setDefaultCloseOperation(frame.DISPOSE_ON_CLOSE);

        JLabel Lnom = new JLabel();

        JLabel Lprenom = new JLabel();

        JLabel Lage = new JLabel();

        JLabel Lraison = new JLabel("Raison du rendez-vous: ");
        Lraison.setBounds(25, 200, 200, 30);

        raisonField = new JTextField("raison");

        JLabel Lsexe = new JLabel();

        JLabel LnumRdv = new JLabel();

        JLabel LnumSecu = new JLabel();
        LnumSecu.setBounds(25, 300, 500, 30);

        JLabel Ldate = new JLabel();
        JLabel Lheure = new JLabel();

        validerButton.setBounds(200, 450, 200, 30);
        validerButton.addActionListener(new ButtonListener());

        panelNewRdv.add(Lnom);
        panelNewRdv.add(Lprenom);
        panelNewRdv.add(Lage);
        panelNewRdv.add(Lraison);
        panelNewRdv.add(raisonField);
        panelNewRdv.add(Lsexe);
        panelNewRdv.add(LnumSecu);
        panelNewRdv.add(Ldate);
        panelNewRdv.add(Lheure);
        panelNewRdv.add(LnumRdv);
        panelNewRdv.add(validerButton);

        validerButton.addActionListener(new ButtonListener());

        panelNewRdv.setLayout(null);

        popNewRdvFrame.add(panelNewRdv);
        panelNewRdv.setVisible(true);

        popNewRdvFrame.setSize(600, 550);
        popNewRdvFrame.setLocationRelativeTo(null);
        popNewRdvFrame.setVisible(true);

        int intRdv = 0;
        String newNumRdvString;

        for (int k = 0; k < RdvList.size(); ++k) {
            intRdv = Integer.parseInt(RdvList.get(k).getNumberRDV().substring(3));
        }

        intRdv += 1;

        newNumRdvString = "RDV" + String.valueOf(intRdv);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("EEEE d MMMM yyyy");
        LocalDate dateTime = LocalDate.parse(days[col], formatter);

        System.out.println(dateTime);

        Doctor d = new Doctor();

        for (int l = 0; l < DoctorList.size(); ++l) {
            if (numSecuDocTemp == DoctorList.get(l).getInsuranceNumber()) {
                d = DoctorList.get(l);
            }
        }

        for (int i = 0; i < PatientList.size(); ++i) {

            if (PatientList.get(i).getInsuranceNumber() == numSécuPatient) {

                LnumRdv.setText("Numéro de rendez-vous: " + newNumRdvString);
                LnumRdv.setBounds(25, 0, 300, 30);

                Lnom.setText("Last Name : " + PatientList.get(i).getLastName());
                Lnom.setBounds(25, 50, 300, 30);

                Lprenom.setText("Name : " + PatientList.get(i).getName());
                Lprenom.setBounds(25, 100, 300, 30);

                Lage.setText("Age : " + String.valueOf(PatientList.get(i).getBorn()) + " ans");
                Lage.setBounds(25, 150, 300, 30);

                Lsexe.setText("Sexe : " + PatientList.get(i).getSexe());
                Lsexe.setBounds(25, 250, 300, 30);

                raisonField.setBounds(155, 200, 400, 30);

                LnumSecu.setText("Insurance Number : " + String.valueOf(PatientList.get(i).getInsuranceNumber()));
                LnumSecu.setBounds(25, 300, 500, 30);

                Ldate.setText("Date: " + days[col]);
                Ldate.setBounds(25, 350, 500, 30);

                Lheure.setText("Heure: " + time[row]);
                Lheure.setBounds(25, 400, 500, 30);

                String raison = raisonField.getText();

                System.out.println(raison);

                rdvNew = new RDV(d, PatientList.get(i), Date.valueOf(dateTime), Time.valueOf(time[row]), raison, newNumRdvString);
            }

        }
    }

    private class CustomRenderer extends DefaultTableCellRenderer {

        private final Set<Point> highlightedCells = new HashSet<Point>();

        void setHighlighted(int r, int c, boolean highlighted) {

            if (highlighted) {
                highlightedCells.add(new Point(r, c));
            } else {
                highlightedCells.remove(new Point(r, c));
            }
        }

        private boolean isHighlighted(int r, int c) {
            return highlightedCells.contains(new Point(r, c));
        }

        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {

            if (isHighlighted(row, column)) {
                setBackground(Color.RED);
            } else {
                setBackground(Color.GREEN);
            }
            return this;
        }
    }

    public void patientUpdate(int row, int col) {

        frameAnte = new JFrame("PatientUpdate");
        
        frameAnte.removeAll();

        setDefaultCloseOperation(frame.DISPOSE_ON_CLOSE);

        JLabel Lnom = new JLabel();

        JLabel Lprenom = new JLabel();

        JLabel Lage = new JLabel();

        JLabel Lantecedent = new JLabel("Antécédents: ");
        Lantecedent.setBounds(25, 200, 100, 30);

        antnew = new JTextField();

        JLabel Lsexe = new JLabel();

        JLabel LnumSecu = new JLabel();
        LnumSecu.setBounds(25, 300, 500, 30);

        update.setBounds(250, 350, 100, 30);
        update.addActionListener(new ButtonListener());
        update.setBackground(new java.awt.Color(18, 92, 117));
        update.setForeground(Color.white);

        panelAnte.add(Lnom);
        panelAnte.add(Lprenom);
        panelAnte.add(Lage);
        panelAnte.add(Lantecedent);
        panelAnte.add(antnew);
        panelAnte.add(Lsexe);
        panelAnte.add(LnumSecu);
        panelAnte.add(update);

        panelAnte.setLayout(null);

        frameAnte.add(panelAnte);
        panelAnte.setVisible(true);

        frameAnte.setSize(600, 500);
        frameAnte.setLocationRelativeTo(null);
        frameAnte.setVisible(true);

        for (int i = 0; i < PatientList.size(); ++i) {

            String test = Integer.toString(PatientList.get(i).getInsuranceNumber());

            if (test.equals(historiquePatient.getValueAt(row, col).toString())) {
                System.out.println(historiquePatient.getValueAt(row, col));

                Lnom.setText("Last Name : " + PatientList.get(i).getLastName());
                Lnom.setBounds(25, 50, 300, 30);

                Lprenom.setText("Name : " + PatientList.get(i).getName());
                Lprenom.setBounds(25, 100, 300, 30);

                Lage.setText("Age : " + String.valueOf(PatientList.get(i).getBorn()));
                Lage.setBounds(25, 150, 300, 30);

                Lsexe.setText("Sexe : " + PatientList.get(i).getSexe());
                Lsexe.setBounds(25, 250, 300, 30);

                antnew.setText(PatientList.get(i).getAntecedent());
                antnew.setBounds(115, 200, 400, 30);

                LnumSecu.setText("Insurance Number : " + String.valueOf(PatientList.get(i).getInsuranceNumber()));
                LnumSecu.setBounds(25, 300, 500, 30);

                p = new Patient(PatientList.get(i).getName(), PatientList.get(i).getLastName(), PatientList.get(i).getInsuranceNumber(), PatientList.get(i).getBorn(), PatientList.get(i).getPassWord(), PatientList.get(i).getAntecedent(), PatientList.get(i).getSexe());

            }

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

    private class HorizontalAlignmentHeaderRenderer implements TableCellRenderer {

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

    private class NoSelectionModel extends DefaultListSelectionModel {

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
            if (e.getSource() == statMenuItem) {
                final Tartiflette fromageStat = new Tartiflette("Statistique utilisation");
                fromageStat.pack();
                //fromageStat.setSize(750, 550);
                RefineryUtilities.centerFrameOnScreen(fromageStat);
                fromageStat.setVisible(true);
            }

            if (e.getSource() == deleteAccount) {

                int dialogButton = JOptionPane.YES_NO_OPTION;
                int dialogResult = JOptionPane.showConfirmDialog(null, "Voulez-vous vraiment supprimer votre compte ?", "Warning", dialogButton);

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
            if (e.getSource() == addRdv) {
                addRdvMenu.show(addRdv, addRdv.getWidth() / 2 - 50, addRdv.getHeight());
            }

            if (e.getSource() == update) {
                patientDAO.update(p, antnew.getText());
                frameAnte.dispose();
                JOptionPane.showMessageDialog(null, "The antecedents have been updated.");

            }

            for (int i = 0; i < doctorItems.length; ++i) {
                if (e.getSource() == speItems[i]) {

                    researchRdv = true;
                    cancelResearch.setVisible(true);
                    listNewRdv.clear();
                    numSecuDocTemp = DoctorList.get(i).getInsuranceNumber();
                    System.out.println("temp num " + numSecuDocTemp);
                    newRdv();
                }
            }

            if (e.getSource() == cancelResearch) {
                panel.repaint();
                researchRdv = false;
                displayEvent();
                cancelResearch.setVisible(false);

            }

            if (e.getSource() == validerButton) {
                rdvDao.create(rdvNew);

                panel.repaint();
                researchRdv = false;
                cancelResearch.setVisible(false);

                displayEvent();
                popNewRdvFrame.dispose();
                JOptionPane.showMessageDialog(null, "Le rendez-vous a bien été ajouté à votre emploi du temps");
            }

        }
    }

    private class Mouse1 implements MouseListener {

        @Override
        public void mouseClicked(MouseEvent e) {

            row2 = historiquePatient.rowAtPoint(e.getPoint());

            col2 = historiquePatient.columnAtPoint(e.getPoint());

            if (e.getClickCount() == 2) {
                patientUpdate(row2, col2);
                System.out.println(row2 + " " + col2);

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

    private class Mouse implements MouseListener {

        @Override
        public void mouseClicked(MouseEvent e) {

            row = tableau.rowAtPoint(e.getPoint());

            col = tableau.columnAtPoint(e.getPoint());

            if (researchRdv == false) {
                if (e.getClickCount() == 1) {
                    quickInfo(row, col);
                }

                if (e.getClickCount() == 2) {
                    infoPanel(row, col);
                }
            }
            if (researchRdv == true) {

                TableCellRenderer renderer = tableau.getCellRenderer(row, col);
                Component c = tableau.prepareRenderer(renderer, row, col);
                System.out.println(c.getBackground());

                if (c.getBackground() == Color.RED) {
                    JOptionPane.showMessageDialog(null, "Impossible de réserver un rendez-vous sur ce créneau horaire !");
                }
                if (c.getBackground() == Color.GREEN) {
                    newRdvPopup(row, col);
                }
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
