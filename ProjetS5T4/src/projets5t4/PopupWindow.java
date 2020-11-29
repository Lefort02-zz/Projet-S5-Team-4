/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package projets5t4;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Insets;
import java.sql.Date;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;

/**
 *
 * @author Gaspard Lefort-Louet
 */


public class PopupWindow extends JFrame{
    
    JPanel panelInfo;
    JLabel LnumRdv, Lnom, Lprenom, Lage, Lantecedent, Linfo, Ldate, Lheure;
    JLabel numRdv, nom, prenom, age, antecedent, info, date, heure;

    public PopupWindow(int row, int col){
        
        JFrame info = new JFrame();
        
        panelInfo = new JPanel();
        
        int ageText;
        String text;
        Time heuretemp;
        Date datetemp = null;
        
        setTitle("Information sur votre rendez-vous");

        setSize(600, 500);

        // Specify what happens when the close button is clicked.
        setDefaultCloseOperation(info.DISPOSE_ON_CLOSE);

        add(panelInfo);

        // Display the window.
        setVisible(true);
        
        panelInfo.setLayout(null);
        
        LnumRdv = new JLabel("Numéro de rendez-vous: ");
        Lnom = new JLabel("Nom: ");
        Lprenom = new JLabel("Prénom: ");
        Lage = new JLabel("Age: ");
        Lantecedent = new JLabel("Antécédents: ");
        Linfo = new JLabel("Informations: ");
        Ldate = new JLabel("Date: ");
        Lheure = new JLabel("Heure: ");
        
        panelInfo.add(LnumRdv);
        panelInfo.add(Lnom);
        panelInfo.add(Lprenom);
        panelInfo.add(Lage);
        panelInfo.add(Lantecedent);
        panelInfo.add(Linfo);
        panelInfo.add(Ldate);
        panelInfo.add(Lheure);
        
        Insets insets = panelInfo.getInsets();
        Dimension size = LnumRdv.getPreferredSize();
        LnumRdv.setBounds(100 + insets.left, 100 + insets.top, size.width, size.height);
        Lnom.setBounds(100 + insets.left, 120 + insets.top, size.width, size.height);
        Lprenom.setBounds(100 + insets.left, 140 + insets.top, size.width, size.height);
        Lage.setBounds(100 + insets.left, 160 + insets.top, size.width, size.height);
        Lantecedent.setBounds(100 + insets.left, 180 + insets.top, size.width, size.height);
        Linfo.setBounds(100 + insets.left, 200 + insets.top, size.width, size.height);
        Ldate.setBounds(100 + insets.left, 220 + insets.top, size.width, size.height);
        Lheure.setBounds(100 + insets.left, 240 + insets.top, size.width, size.height);
        
        /*PanelProfil test = new PanelProfil(0);
        
        for (int i = 0; i < test.getRdvList().size(); ++i) {

            if (test.getNumSécuPatient() == 0 && test.getRdvList().get(i).getDoctor().insuranceNumber == test.getNumSécuDocteur()) {

                if (test.getRdvList().get(i).getNumberRDV() == test.getTableau().getValueAt(row, col).toString()) {

                    nom = test.getRdvList().get(i).getPatient().getLastName();
                    prenom = test.getRdvList().get(i).getPatient().getName();

                    ageText = test.getRdvList().get(i).getPatient().getBorn();

                    age = String.valueOf(ageText);

                    informations = test.getRdvList().get(i).getInformations();

                    antecedant = test.getRdvList().get(i).getPatient().getAntecedent();

                    numRDV = test.getRdvList().get(i).getNumberRDV();

                    heuretemp = test.getRdvList().get(i).getTime();

                    datetemp = test.getRdvList().get(i).getDate();
                }

            }
        }*/
    }
}
