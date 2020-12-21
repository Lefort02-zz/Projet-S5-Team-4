import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import projets5t4.Patient;


public class RdvPopUp extends JFrame  {
	
	
	public void init(Patient p)
	{
	
	JFrame frameRdvPopUp = new JFrame("RDV POPup");
	
	JLabel numRdv = new JLabel("Numéro de Rendez-vous : " );
	numRdv.setBounds(25, 50, 100, 30);
	
	JLabel lastName = new JLabel("Nom : " + p .getLastName());
	lastName.setBounds(25, 100, 100, 30);
	
	JLabel name = new JLabel("Prénom : " + p.getName());
	name.setBounds(25, 150, 100, 30);
	
	JLabel insuranceNumber = new JLabel("Numéro de sécu : " + p.getInsuranceNumber());
	insuranceNumber.setBounds(25, 200, 100, 30);
	
	JLabel age = new JLabel("Âge : " + p.getBorn());
	age.setBounds(25, 250, 100, 30);
	
	JLabel sexe = new JLabel("Sexe : " + p.getSexe());
	sexe.setBounds(25, 300, 100, 30);
	
	JLabel date = new JLabel("Date : ");
	date.setBounds(25, 350, 100, 30);
	
	JLabel horaire = new JLabel("Horaire : ");
	horaire.setBounds(25, 400, 100, 30);
	
	JLabel raison = new JLabel("Raison du Rendez-vous ?");
	raison.setBounds(25, 450, 100, 30);
	
	JTextField raisonString = new JTextField(50);
	raisonString.setBounds(25, 500, 300, 30);
	
	JButton ValiderRDV = new JButton("Valider le Rendez-vous");
	ValiderRDV.setBounds(175, 550, 100, 30);
	
	frameRdvPopUp.add(numRdv);frameRdvPopUp.add(lastName);
	frameRdvPopUp.add(name);frameRdvPopUp.add(insuranceNumber);
	frameRdvPopUp.add(age);frameRdvPopUp.add(sexe);
	frameRdvPopUp.add(date);frameRdvPopUp.add(horaire);
	frameRdvPopUp.add(raison);frameRdvPopUp.add(raisonString);
	
	frameRdvPopUp.setLayout(null);  
	frameRdvPopUp.setSize(500,700);
	frameRdvPopUp.setVisible(true);
	
	
	}
	
	public static void main(String[]args)
	{
		RdvPopUp test = new RdvPopUp();
		//test.init();
	}
	
}
