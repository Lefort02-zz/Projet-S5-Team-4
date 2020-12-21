/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projets5t4;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JOptionPane;
import javax.swing.border.Border;

import projets5t4.PanelSignUp.ButtonListener;
import projets5t4.PanelSignUp.CheckBoxListener;

/**
 *
 * @author Alex
 */
public class Login extends javax.swing.JFrame {

    /**
     * Creates new form Login
     */
    public Login() {
        initComponents();

        // center the form
        this.setLocationRelativeTo(null);

        // Create a black border for the minimise and close jLabel
       /* Border label_border = BorderFactory.createMatteBorder(1,1,1,1,Color.black);
         jLabel_close.setBorder(label_border);
         jLabel_minimize.setBorder(label_border); */
    }

    private class CheckBoxListener1 implements ItemListener {

        public void itemStateChanged(ItemEvent e) {

            if (sexM.isSelected()) {
                sexF.setSelected(false);
                sValue = "male";

            }
        }
    }

    private class CheckBoxListener2 implements ItemListener {

        public void itemStateChanged(ItemEvent e) {

            if (sexF.isSelected()) {
                sValue = "female";
                sexM.setSelected(false);
            }

        }
    }

    private class CheckBoxListener3 implements ItemListener {

        public void itemStateChanged(ItemEvent e) {
            if (doc.isSelected()) {
                lI.setText("Speciality");
                lI.setBounds(80, 300, 100, 30);
                System.out.println(tIN.getText() + "\n" + tN.getText() + "\n" + tLN.getText() + "\n" + tA.getText() + "\n" + tI.getText());
            } else {
                lI.setText("Antecedent(s)");
                lI.setBounds(55, 300, 100, 30);
                System.out.println(tIN.getText() + "\n" + tN.getText() + "\n" + tLN.getText() + "\n" + tA.getText() + "\n" + tI.getText());
            }
        }
    }

    private class ButtonListener1 implements ActionListener {

        public void actionPerformed(ActionEvent e) {
            
            try {

                DAO<Doctor> docteurDAO = new DocteurDAO(SQL.getInstance());
                List<Doctor> DoctorList = new ArrayList<Doctor>();
                DoctorList = docteurDAO.find();

                System.out.println(jTextField_SocialNumber.getText());
                System.out.println(jPassword_Login.getText());

                for (int y = 0; y < DoctorList.size(); y++) {
                    if (DoctorList.get(y).getInsuranceNumber() == Integer.parseInt(jTextField_SocialNumber.getText())) {
                        
                        if (DoctorList.get(y).getPassWord().equals(jPassword_Login.getText())) {
                            
                            System.out.println("ENTER");
                            PanelProfil calendrier = new PanelProfil(DoctorList.get(y));
                            dispose();
                        }

                    } else {
                    	JOptionPane.showMessageDialog(null, "This id or the password isn't correct.");
                    }
                }

                DAO<Patient> patientDAO = new PatientDAO(SQL.getInstance());
                List<Patient> patientList = new ArrayList<Patient>();
                patientList = patientDAO.find();

                for (int i = 0; i < patientList.size(); i++) {

                    if (patientList.get(i).getInsuranceNumber() == Integer.parseInt(jTextField_SocialNumber.getText())) {
                        
                        if (patientList.get(i).getPassWord().equals(jPassword_Login.getText())) {
                            PanelProfil calendrier = new PanelProfil(patientList.get(i));
                            dispose();
                        }

                    } else {
                        JOptionPane.showMessageDialog(null, "This id or the password isn't correct.");
                    }
                }
            } catch (Exception exp) {
            	JOptionPane.showMessageDialog(null, "This id or the password isn't correct.");
            }
        }
    }

    private class ButtonListener implements ActionListener {

        public void actionPerformed(ActionEvent e) {
            try {
                Person p;

                if (insuranceVerification() == true) {

                    if (doc.isSelected() == true) {

                        DAO<Doctor> docteurDAO = new DocteurDAO(SQL.getInstance());
                        docteurDAO.create(new Doctor(tN.getText(), tLN.getText(), Integer.parseInt(tIN.getText()), Integer.parseInt(tA.getText()), tPW.getText(), tI.getText(), sValue));
                        p = new Doctor(tN.getText(), tLN.getText(), Integer.parseInt(tIN.getText()), Integer.parseInt(tA.getText()), tPW.getText(), tI.getText(), sValue);
                    } else {
                        DAO<Patient> patientDAO = new PatientDAO(SQL.getInstance());
                        patientDAO.create(new Patient(tN.getText(), tLN.getText(), Integer.parseInt(tIN.getText()), Integer.parseInt(tA.getText()), tPW.getText(), tI.getText(), sValue));
                        p = new Patient(tN.getText(), tLN.getText(), Integer.parseInt(tIN.getText()), Integer.parseInt(tA.getText()), tPW.getText(), tI.getText(), sValue);
                    }

                    //Thread.sleep(3000);
                    System.out.println("<html><body>Message :" + "Sent.<br> \nYour account is now created.</body></html>");

                    PanelProfil calendrier = new PanelProfil(p);
                    dispose();

                } else {
                    System.out.println("<html><body>This person already exist,<br> please verify the insurance number.<html><body>");
                }

            } catch (Exception excp) {

                System.out.println("<html><body>Message :" + "Please verify your entry.<br> \nInsurance Number can only be composed of numbers, same situation for the age.</body></html>");

            }

        }

    }

    public boolean insuranceVerification() {
        boolean verification = false;

        if (doc.isSelected() == true) {
            DAO<Doctor> docteurDAO = new DocteurDAO(SQL.getInstance());
            List<Doctor> DoctorList = new ArrayList<Doctor>();

            DoctorList = docteurDAO.find();

            for (int i = 0; i < DoctorList.size(); i++) {

                if (DoctorList.get(i).getInsuranceNumber() == Integer.parseInt(tIN.getText())) {

                    verification = false;
                } else {
                    verification = true;
                }
            }
            return verification;
        } else {
            DAO<Patient> patientDAO = new PatientDAO(SQL.getInstance());
            List<Patient> patientList = new ArrayList<Patient>();

            patientList = patientDAO.find();

            for (int i = 0; i < patientList.size(); i++) {

                if (patientList.get(i).getInsuranceNumber() == Integer.parseInt(tIN.getText())) {

                    verification = false;
                } else {
                    verification = true;
                }
            }
            return verification;
        }
    }

    @SuppressWarnings("unchecked")
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jLabel8 = new javax.swing.JLabel();
        jLabel_close = new javax.swing.JLabel();
        jLabel_minimize = new javax.swing.JLabel();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel3 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jPassword_Login = new javax.swing.JPasswordField();
        jSeparator1 = new javax.swing.JSeparator();
        jSeparator2 = new javax.swing.JSeparator();
        jLabel_password = new javax.swing.JLabel();
        jLabel_SocialNumber = new javax.swing.JLabel();
        jLabel_ForgetPassword = new javax.swing.JLabel();
        jButton_SignIn = new javax.swing.JButton();
        jTextField_SocialNumber = new javax.swing.JTextField();
        jPanel4 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        send = new javax.swing.JButton();
        jTextField_CreatePasswordConfirmation = new javax.swing.JTextField();
        tIN = new javax.swing.JTextField();
        tA = new javax.swing.JTextField();
        tPW = new javax.swing.JTextField();
        tI = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        lI = new javax.swing.JLabel();
        jLabel_ShowValidationSocialNumber = new javax.swing.JLabel();
        jLabel_ShowValidationAge = new javax.swing.JLabel();
        tN = new javax.swing.JTextField();
        tLN = new javax.swing.JTextField();
        sexM = new javax.swing.JCheckBox();
        sexF = new javax.swing.JCheckBox();
        doc = new javax.swing.JCheckBox();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setUndecorated(true);

        jPanel1.setBackground(new java.awt.Color(18, 92, 117));

        jPanel2.setBackground(new java.awt.Color(218, 227, 230));

        jLabel8.setIcon(new javax.swing.ImageIcon("C:\\Users\\Gaspard Lefort-Louet\\iCloudDrive\\Desktop\\Github\\Projet-S5-Team-4\\ProjetS5T4\\src\\projets5t4\\logo 2.jpg")); // NOI18N

        jLabel_close.setFont(new java.awt.Font("Yu Gothic UI", 1, 22)); // NOI18N
        jLabel_close.setText(" X");
        //Au passage de la souris, le curseur devient une main
        jLabel_close.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLabel_close.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel_closeMouseClicked(evt);
            }

            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jLabel_closeMouseEntered(evt);
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                jLabel_closeMouseExited(evt);
            }
        });

        jLabel_minimize.setFont(new java.awt.Font("Yu Gothic UI", 1, 36)); // NOI18N
        jLabel_minimize.setText("-");
        //Au passage de la souris, le curseur devient une main
        jLabel_minimize.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLabel_minimize.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel_minimizeMouseClicked(evt);
            }

            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jLabel_minimizeMouseEntered(evt);
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                jLabel_minimizeMouseExited(evt);
            }
        });

        jPanel3.setBackground(new java.awt.Color(142, 201, 222));
        jPanel3.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255), 2));
        jPanel3.setForeground(new java.awt.Color(255, 255, 255));

        jLabel1.setFont(new java.awt.Font("Tahoma", 0, 48)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("Login");

        jPassword_Login.setBackground(new java.awt.Color(142, 201, 222));
        jPassword_Login.setFont(new java.awt.Font("Tahoma", 2, 14)); // NOI18N
        jPassword_Login.setForeground(new java.awt.Color(255, 255, 255));
        jPassword_Login.setText("password");
        jPassword_Login.setBorder(null);
        jPassword_Login.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jPassword_LoginFocusGained(evt);
            }

            public void focusLost(java.awt.event.FocusEvent evt) {
                jPassword_LoginFocusLost(evt);
            }
        });

        jSeparator1.setForeground(new java.awt.Color(255, 255, 255));

        jSeparator2.setForeground(new java.awt.Color(255, 255, 255));

        jLabel_password.setIcon(new javax.swing.ImageIcon("C:\\Users\\Gaspard Lefort-Louet\\iCloudDrive\\Desktop\\Github\\Projet-S5-Team-4\\ProjetS5T4\\src\\projets5t4\\icons8-ouvrir-32.png")); // NOI18N

        jLabel_SocialNumber.setIcon(new javax.swing.ImageIcon("C:\\Users\\Gaspard Lefort-Louet\\iCloudDrive\\Desktop\\Github\\Projet-S5-Team-4\\ProjetS5T4\\src\\projets5t4\\icons8-utilisateur-32.png")); // NOI18N

        jLabel_ForgetPassword.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel_ForgetPassword.setForeground(new java.awt.Color(255, 255, 255));
        jLabel_ForgetPassword.setText("Forget Password ?");
        jLabel_ForgetPassword.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

        jButton_SignIn.setBackground(new java.awt.Color(142, 201, 222));
        jButton_SignIn.addActionListener(new ButtonListener1());
        jButton_SignIn.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jButton_SignIn.setForeground(new java.awt.Color(255, 255, 255));
        jButton_SignIn.setText("Sign In");
        jButton_SignIn.setBorder(null);
        jButton_SignIn.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jButton_SignIn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jButton_SignInMouseEntered(evt);
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                jButton_SignInMouseExited(evt);
            }
        });
        jButton_SignIn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_SignInActionPerformed(evt);
            }
        });

        jTextField_SocialNumber.setBackground(new java.awt.Color(142, 201, 222));
        jTextField_SocialNumber.setFont(new java.awt.Font("Tahoma", 2, 14)); // NOI18N
        jTextField_SocialNumber.setForeground(new java.awt.Color(255, 255, 255));
        jTextField_SocialNumber.setText("Social security number");
        jTextField_SocialNumber.setBorder(null);
        jTextField_SocialNumber.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTextField_SocialNumberFocusGained(evt);
            }

            public void focusLost(java.awt.event.FocusEvent evt) {
                jTextField_SocialNumberFocusLost(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
                jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                        .addGap(280, 280, 280)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addComponent(jLabel_SocialNumber)
                                .addComponent(jLabel_password))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 234, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jTextField_SocialNumber, javax.swing.GroupLayout.PREFERRED_SIZE, 234, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jPassword_Login, javax.swing.GroupLayout.PREFERRED_SIZE, 234, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, 234, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addContainerGap(373, Short.MAX_VALUE))
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 166, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(360, 360, 360))
                                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                                        .addComponent(jButton_SignIn, javax.swing.GroupLayout.PREFERRED_SIZE, 144, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(32, 32, 32)
                                        .addComponent(jLabel_ForgetPassword)
                                        .addGap(279, 279, 279))))
        );
        jPanel3Layout.setVerticalGroup(
                jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel3Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(89, 89, 89)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(jLabel_SocialNumber, javax.swing.GroupLayout.DEFAULT_SIZE, 40, Short.MAX_VALUE)
                                .addComponent(jTextField_SocialNumber))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(31, 31, 31)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jPassword_Login, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel_password, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(47, 47, 47)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jButton_SignIn, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel_ForgetPassword, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addContainerGap(92, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Login", jPanel3);

        jPanel4.setBackground(new java.awt.Color(103, 168, 191));
        jPanel4.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255), 2));

        jLabel2.setFont(new java.awt.Font("Tahoma", 0, 48)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText(" Create a new account");

        send.setBackground(new java.awt.Color(103, 168, 191));
        send.addActionListener(new ButtonListener());
        send.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        send.setForeground(new java.awt.Color(255, 255, 255));
        send.setText("Sign Up");
        send.setBorder(null);
        send.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        send.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                sendMouseEntered(evt);
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                sendMouseExited(evt);
            }
        });

        jTextField_CreatePasswordConfirmation.setBackground(new java.awt.Color(103, 168, 191));
        jTextField_CreatePasswordConfirmation.setForeground(new java.awt.Color(255, 255, 255));
        jTextField_CreatePasswordConfirmation.setText("jTextField2");

        tIN.setBackground(new java.awt.Color(103, 168, 191));
        tIN.setForeground(new java.awt.Color(255, 255, 255));
        tIN.setText("jTextField2");
        tIN.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tINKeyPressed(evt);
            }
        });

        tA.setBackground(new java.awt.Color(103, 168, 191));
        tA.setForeground(new java.awt.Color(255, 255, 255));
        tA.setText("jTextField2");

        tPW.setBackground(new java.awt.Color(103, 168, 191));
        tPW.setForeground(new java.awt.Color(255, 255, 255));
        tPW.setText("jTextField2");

        tI.setBackground(new java.awt.Color(103, 168, 191));
        tI.setForeground(new java.awt.Color(255, 255, 255));
        tI.setText("jTextField2");

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setText("Insurance number");

        jLabel4.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(255, 255, 255));
        jLabel4.setText("Last name");

        jLabel5.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(255, 255, 255));
        jLabel5.setText("Name");

        jLabel6.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(255, 255, 255));
        jLabel6.setText("Password");

        jLabel7.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(255, 255, 255));
        jLabel7.setText("Age");

        jLabel9.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(255, 255, 255));
        jLabel9.setText("Password confirmation");

        lI.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        lI.setForeground(new java.awt.Color(255, 255, 255));
        lI.setText("Antecedents");

        jLabel_ShowValidationSocialNumber.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel_ShowValidationSocialNumber.setForeground(new java.awt.Color(255, 0, 0));

        jLabel_ShowValidationAge.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel_ShowValidationAge.setForeground(new java.awt.Color(255, 0, 0));

        tN.setBackground(new java.awt.Color(103, 168, 191));
        tN.setForeground(new java.awt.Color(255, 255, 255));
        tN.setText("jTextField2");

        tLN.setBackground(new java.awt.Color(103, 168, 191));
        tLN.setForeground(new java.awt.Color(255, 255, 255));
        tLN.setText("jTextField2");

        sexM.setText("Male");
        sexM.addItemListener(new CheckBoxListener1());

        sexF.setText("Female");
        sexF.addItemListener(new CheckBoxListener2());

        doc.setText("staff?");
        doc.addItemListener(new CheckBoxListener3());

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
                jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 491, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(197, 197, 197))
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(jPanel4Layout.createSequentialGroup()
                                                .addGap(365, 365, 365)
                                                .addComponent(send, javax.swing.GroupLayout.PREFERRED_SIZE, 144, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGroup(jPanel4Layout.createSequentialGroup()
                                                .addGap(180, 180, 180)
                                                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                        .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 107, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                        .addComponent(jLabel3)
                                                        .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                        .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 69, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                        .addComponent(lI, javax.swing.GroupLayout.PREFERRED_SIZE, 82, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                        .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 69, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                                        .addComponent(tIN, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 234, Short.MAX_VALUE)
                                                        .addComponent(tN)
                                                        .addComponent(tLN, javax.swing.GroupLayout.Alignment.LEADING)
                                                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel4Layout.createSequentialGroup()
                                                                .addComponent(tA, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                                        .addComponent(sexM, javax.swing.GroupLayout.Alignment.TRAILING)
                                                                        .addComponent(sexF, javax.swing.GroupLayout.Alignment.TRAILING))))))
                                .addGroup(jPanel4Layout.createSequentialGroup()
                                        .addContainerGap()
                                        .addComponent(jLabel9)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                .addComponent(tI, javax.swing.GroupLayout.PREFERRED_SIZE, 234, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addComponent(jTextField_CreatePasswordConfirmation, javax.swing.GroupLayout.PREFERRED_SIZE, 234, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addComponent(tPW, javax.swing.GroupLayout.PREFERRED_SIZE, 234, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                        .addComponent(jLabel_ShowValidationSocialNumber, javax.swing.GroupLayout.PREFERRED_SIZE, 231, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jLabel_ShowValidationAge, javax.swing.GroupLayout.PREFERRED_SIZE, 231, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addComponent(doc))
                        .addGap(0, 145, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
                jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel4Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 78, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel_ShowValidationSocialNumber)
                                .addComponent(tIN, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(tLN, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(tN, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(jPanel4Layout.createSequentialGroup()
                                        .addGap(18, 18, 18)
                                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                                .addComponent(jLabel7)
                                                .addComponent(jLabel_ShowValidationAge)
                                                .addComponent(tA, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addGroup(jPanel4Layout.createSequentialGroup()
                                        .addGap(5, 5, 5)
                                        .addComponent(sexM)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(sexF)))
                        .addGap(18, 18, Short.MAX_VALUE)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(tI, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(lI)
                                .addComponent(doc))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(tPW, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel6))
                        .addGap(18, 18, Short.MAX_VALUE)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jTextField_CreatePasswordConfirmation, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel9))
                        .addGap(26, 26, 26)
                        .addComponent(send, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap())
        );

        jTabbedPane1.addTab("Register", jPanel4);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
                jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                                        .addComponent(jLabel_minimize, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED))
                                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                                        .addComponent(jLabel8)
                                        .addGap(214, 214, 214)))
                        .addComponent(jLabel_close, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addComponent(jTabbedPane1)
        );
        jPanel2Layout.setVerticalGroup(
                jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(jPanel2Layout.createSequentialGroup()
                                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                                .addComponent(jLabel_close, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addComponent(jLabel_minimize, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGap(0, 0, Short.MAX_VALUE))
                                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                                        .addGap(0, 22, Short.MAX_VALUE)
                                        .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 518, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
                jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
                jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>                        

    private void jPassword_LoginFocusGained(java.awt.event.FocusEvent evt) {
        // TODO add your handling code here:
        //ça enlève le texte au moment du clique
        //ça enlève le texte au moment du clique si le texte est encore "password"
        String pass = String.valueOf(jPassword_Login.getPassword());
        if (pass.trim().toLowerCase().equals("password")) {
            jPassword_Login.setText("");
            jPassword_Login.setForeground(new Color(255, 255, 255));
        }
        /* pass.setText("");*/

        // set a white border to the jLabel password icon
        Border jLabel_icon = BorderFactory.createMatteBorder(2, 2, 2, 2, new Color(255, 255, 255));
        jLabel_password.setBorder(jLabel_icon);
    }

    private void jLabel_closeMouseClicked(java.awt.event.MouseEvent evt) {
        // TODO add your handling code here:
        // Ferme la page au moment du clique
        System.exit(0);
    }

    private void jLabel_minimizeMouseClicked(java.awt.event.MouseEvent evt) {
        // TODO add your handling code here:
        //Diminue la fenêtre Jframe au moment du clique sur le tiret 
        this.setState(1);
    }

    private void jLabel_minimizeMouseEntered(java.awt.event.MouseEvent evt) {
        // TODO add your handling code here:
        // Au passage de la souris ça devient blanc
       /* Border label_border = BorderFactory.createMatteBorder(1,1,1,1,Color.white);
         jLabel_minimize.setBorder(label_border); */
        jLabel_minimize.setForeground(Color.white);
    }

    private void jLabel_minimizeMouseExited(java.awt.event.MouseEvent evt) {
        // TODO add your handling code here:
        // après passage de la souris ça redevient noir ( à la base ça reste noir si on ne va pas dessus car c'est sa couleur à la base)
       /* Border label_border = BorderFactory.createMatteBorder(1,1,1,1,Color.black);
         jLabel_minimize.setBorder(label_border); */
        jLabel_minimize.setForeground(Color.black);
    }

    private void jLabel_closeMouseEntered(java.awt.event.MouseEvent evt) {
        // TODO add your handling code here:
        // même procédé que pour le jLabel_minimize pour la couleur qui change si on passe ou pas dessus 
       /* Border label_border = BorderFactory.createMatteBorder(1,1,1,1,Color.white);
         jLabel_close.setBorder(label_border);*/
        jLabel_close.setForeground(Color.white);
    }

    private void jLabel_closeMouseExited(java.awt.event.MouseEvent evt) {
        // TODO add your handling code here:
        /*Border label_border = BorderFactory.createMatteBorder(1,1,1,1,Color.black);
         jLabel_close.setBorder(label_border);*/
        jLabel_close.setForeground(Color.black);
    }

    private void jButton_SignInActionPerformed(java.awt.event.ActionEvent evt) {
        // TODO add your handling code here:
    }

    private void jButton_SignInMouseEntered(java.awt.event.MouseEvent evt) {
        // TODO add your handling code here:
        // Afficher la couleur en allant dessus
        jButton_SignIn.setBackground(new Color(0, 0, 0));
    }

    private void jButton_SignInMouseExited(java.awt.event.MouseEvent evt) {
        // TODO add your handling code here:
        // Affiche la couleur souhaitée sans aller dessus
        jButton_SignIn.setBackground(new Color(142, 201, 222));
    }

    // Même procédé que pour le bouton jButton_SignIn
    private void sendMouseEntered(java.awt.event.MouseEvent evt) {
        // TODO add your handling code here:
        send.setBackground(new Color(0, 0, 0));
    }

    private void sendMouseExited(java.awt.event.MouseEvent evt) {
        // TODO add your handling code here:
        send.setBackground(new Color(103, 168, 191));
    }

    private void jPassword_LoginFocusLost(java.awt.event.FocusEvent evt) {
        // TODO add your handling code here:
        //Si le password est égal a "password" ou a un vide
        //Alors on affichera "password" dans le text
        //Ou sinon ça laisse le texte mis par l'utilisateur
        String pass = String.valueOf(jPassword_Login.getPassword());

        if (pass.trim().equals("") || pass.trim().toLowerCase().equals("password")) {
            jPassword_Login.setText("password");
            jPassword_Login.setForeground(new Color(255, 255, 255));
        }

        // remove the white from the jLabel password icon
        jLabel_password.setBorder(null);
    }

    private void jTextField_SocialNumberFocusGained(java.awt.event.FocusEvent evt) {
        // TODO add your handling code here:
        // Clear the textfield on focus if the text is "Social security number"
        if (jTextField_SocialNumber.getText().trim().equals("Social security number")) {
            jTextField_SocialNumber.setText("");
            jTextField_SocialNumber.setForeground(new Color(255, 255, 255));
        }

        // set a white border to the jLabel icon
        Border jLabel_icon = BorderFactory.createMatteBorder(2, 2, 2, 2, new Color(255, 255, 255));
        jLabel_SocialNumber.setBorder(jLabel_icon);

    }

    private void jTextField_SocialNumberFocusLost(java.awt.event.FocusEvent evt) {
        // TODO add your handling code here:
        // if the text field is equal to "Social security nulber" or empty
        // we will set the "Social security number" text in the field
        // on focus lost event

        if (jTextField_SocialNumber.getText().trim().equals("")
                || jTextField_SocialNumber.getText().trim().equals("Social security number")) {
            jTextField_SocialNumber.setText("Social security number");
            jTextField_SocialNumber.setForeground(new Color(255, 255, 255));
        }

        // remove the white from the jLabel icon
        jLabel_SocialNumber.setBorder(null);
    }

    private void tINKeyPressed(java.awt.event.KeyEvent evt) {
        // TODO add your handling code here:
        try {
            int i = Integer.parseInt(tIN.getText());
            jLabel_ShowValidationSocialNumber.setText("");
        } catch (NumberFormatException el) {
            jLabel_ShowValidationSocialNumber.setText("Invalid number");
        }
    }

    // Variables declaration - do not modify                     
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JCheckBox doc;
    private javax.swing.JButton jButton_SignIn;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel lI;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JLabel jLabel_ForgetPassword;
    private javax.swing.JLabel jLabel_ShowValidationAge;
    private javax.swing.JLabel jLabel_ShowValidationSocialNumber;
    private javax.swing.JLabel jLabel_SocialNumber;
    private javax.swing.JLabel jLabel_close;
    private javax.swing.JLabel jLabel_minimize;
    private javax.swing.JLabel jLabel_password;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPasswordField jPassword_Login;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTextField jTextField_CreatePasswordConfirmation;
    private javax.swing.JTextField jTextField_SocialNumber;
    private javax.swing.JButton send;
    private javax.swing.JCheckBox sexF;
    private javax.swing.JCheckBox sexM;
    private javax.swing.JTextField tA;
    private javax.swing.JTextField tI;
    private javax.swing.JTextField tIN;
    private javax.swing.JTextField tLN;
    private javax.swing.JTextField tN;
    private javax.swing.JTextField tPW;
    private String sValue = "non-binary";
    // End of variables declaration                   
}
