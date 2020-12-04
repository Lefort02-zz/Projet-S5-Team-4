package projets5t4;

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




public class PanelSignUp
{

	public String value ="Antecedent(s)";
	private JFrame f= new JFrame("Sign Up");  
	private JTextField tIN,tN,tLN,tA,tI,tPW;
	private JLabel lIN,lN,lLN,lA,lI,lPW,global;
	private JButton send;
	private JCheckBox doc = new JCheckBox("If doctor, check it");
	
	
    private class CheckBoxListener implements ItemListener
    {
    	public void itemStateChanged(ItemEvent e)
    	{
    	
    	
    		if (doc.isSelected())
    		{
    			lI.setText("Speciality");
    			lI.setBounds(80, 300, 100, 30);
    			//global.setText(tIN.getText() +"\n"+tN.getText()+"\n"+tLN.getText()+"\n"+tA.getText()+"\n"+tI.getText());
    		}
    		else
    		{
    			lI.setText("Antecedent(s)");
    			lI.setBounds(55, 300, 100, 30);
    			//global.setText(tIN.getText() +"\n"+tN.getText()+"\n"+tLN.getText()+"\n"+tA.getText()+"\n"+tI.getText());
    		}
    	}
    }
    
    private class ButtonListener implements ActionListener
    {
	    public void  actionPerformed(ActionEvent e)
	    {
	        try
	        {
	        	
	        	if(insuranceVerification()==true)
	        	{
	        		
		        	if(doc.isSelected() == true)
		        	{
		        		
		        		DAO<Doctor> docteurDAO = new DocteurDAO(SQL.getInstance());
		        		docteurDAO.create(new Doctor(tN.getText(),tLN.getText(),Integer.parseInt(tIN.getText()),Integer.parseInt(tA.getText()),tPW.getText(),tI.getText()));
		        		
		        	}
		        	
		        	else
		        	{
		        		DAO<Patient> patientDAO = new PatientDAO(SQL.getInstance());
		        		patientDAO.create(new Patient(tN.getText(),tLN.getText(),Integer.parseInt(tIN.getText()),Integer.parseInt(tA.getText()),tPW.getText(),tI.getText()));
		        	}
		        	
		        	Thread.sleep(3000);
		        	global.setText("<html><body>"+ global.getText() + "Sent.<br> \nYour account is now created.</body></html>");
	        	}
	        	else
	        	{   
	        		global.setText("<html><body>This person already exist,<br> please verify the insurance number.<html><body>");
	        	}
	        	
	        	
	        }
	       catch(Exception excp)
	        {
	        	
	        	global.setText("<html><body>"+ global.getText() + "Please verify your entry.<br> \nInsurance Number can only be composed of numbers, same situation for the age.</body></html>");
	        	
	        }
	        
	        
	        
	    }
	    
    }
    
    
    public boolean insuranceVerification()
    {
    	boolean verification = false;
    	
    	if(doc.isSelected() == true)
	    	{
	    	 DAO<Doctor> docteurDAO = new DocteurDAO(SQL.getInstance());
	    	 List<Doctor> DoctorList = new ArrayList<Doctor>();
	    	 
	    	 DoctorList = docteurDAO.find();
	    	 
	    	 for(int i = 0; i < DoctorList.size();i++)
	    	 {
	    		 
	    		 if(DoctorList.get(i).getInsuranceNumber() == Integer.parseInt(tIN.getText()))
	    		 {
	    			 
	    			 verification = false;
	    		 }
	    		 
	    		 else
	    		 {
	    			 verification = true;
	    		 }
	    	 }
	    	 return verification;
	    	}
    	
    	else
	    	{
	    	 DAO<Patient> patientDAO = new PatientDAO(SQL.getInstance());
	       	 List<Patient> patientList = new ArrayList<Patient>();
	       	 
	       	 patientList = patientDAO.find();
	       	 
	       	 for(int i = 0; i < patientList.size();i++)
	       	 {
	       		
	       		
	       		 if(patientList.get(i).getInsuranceNumber() == Integer.parseInt(tIN.getText()))
	       		 {
	       			
	       			verification = false;
	       		 }
	       		 
	       		 else
	       		 {
	       			verification = true;
	       		 }
	       	 }
	       	 return verification;
	    	}
    }
	
	 public boolean init()
	 {
		 boolean typeVerification = false;
		 
	    lIN=new JLabel("Insurance Number");
	    lIN.setBounds(25, 100, 125, 30);
	    tIN=new JTextField(20);  
	    tIN.setBounds(150,100, 200,30);  //y : 100
	    
	    lN=new JLabel("Name");
	    lN.setBounds(100, 150, 50, 30);
	    tN=new JTextField(20);  
	    tN.setBounds(150,150, 200,30); //y : 150
	    
	    lLN=new JLabel("Last Name");
	    lLN.setBounds(75, 200, 125, 30);
	    tLN=new JTextField(20);  
	    tLN.setBounds(150,200, 200,30); //y : 200
	    
	    lA=new JLabel("Age");
	    lA.setBounds(110, 250, 50, 30);
	    tA=new JTextField(20);  
	    tA.setBounds(150,250, 62,30); //y : 250
	    
	    doc.setBounds(225, 250, 150, 30);
	    doc.addItemListener(new CheckBoxListener());
	    
	    
	    lI = new JLabel(value);
	    lI.setBounds(55, 300, 100, 30);
	    tI=new JTextField(20);  
	    tI.setBounds(150,300, 200,30);//y : 300
	    
	    lPW = new JLabel("Password");
	    lPW.setBounds(75, 350, 500, 30);
	    tPW = new JTextField(20);
	    tPW.setBounds(150, 350, 100, 30);
	    
	    global = new JLabel("Message(s) : ");
	    global.setBounds(150, 400, 500, 50);
	    
	    send = new JButton("Send");
	    send.setBounds(150, 475, 150, 40);
	    send.addActionListener(new ButtonListener());
	    
	    
	    f.add(tIN); f.add(tN); f.add(lIN); f.add(lN); 
	    f.add(lLN); f.add(tLN); f.add(lA); f.add(tA);
	    f.add(doc); f.add(lI); f.add(tI);
	    f.add(global); f.add(tPW);f.add(lPW);f.add(send);
	    //f.setExtendedState(JFrame.MAXIMIZED_BOTH);  
	    f.setSize(500,700);
	    f.setLayout(null);  
	    f.setVisible(true);
	    return typeVerification;
	 }
	 
	 public static void main(String[]args)
	 {
		 PanelSignUp ps1 = new PanelSignUp();
		 ps1.init();
		 
	 }


}
