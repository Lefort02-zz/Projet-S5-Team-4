/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projets5t4;

import java.sql.Date;
import java.sql.Time;

/**
 *
 * @author Gaspard Lefort-Louet
 */
public class ProjetS5T4 {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here

        PanelProfil calendrier = new PanelProfil();

       /* DAO<Patient> patientDAO = new PatientDAO(SQL.getInstance());
        DAO<Docteur> docteurDAO = new DocteurDAO(SQL.getInstance());
        DAO<Event> eventDAO = new EventDAO(SQL.getInstance());
        
        
            Patient patient = patientDAO.find(1111);
            System.out.println("Num sécu: " + patient.getNumSecuPatient()+ "  nom: " + patient.getNomPatient()+ " prénom: " + patient.getPrenomPatient()+ " age: " + patient.getAge()+ " antécédents: " + patient.getAntecedent()
            
            + " password: " + patient.getPassword());
            
            Docteur docteur = docteurDAO.find(2222);
            System.out.println("Num sécu: " + docteur.getNumSecuDocteur()+ "  nom: " + docteur.getNomDoc()+ " prénom: " + docteur.getPrenomDoc()+ " age: " + docteur.getAge()+ " spécialité: " + docteur.getSpéDoc()
            
            + " password: " + docteur.getPasswordDoc());
            
            
            Event rdv = eventDAO.find(1555);
            System.out.println("Num RDV: " + rdv.getNumRDV()+ "  date: " + rdv.getDate()+ " heure: " + rdv.getHours()+ " raison: " + rdv.getReason());*/
        
        
        
        Doctor doc1 = new Doctor("jerome","duPont",15545,58,"123456","pied");
        
       
        
        DAO<Doctor> docteurDAO = new DocteurDAO(SQL.getInstance());
        docteurDAO.create(doc1);
        
        
        
        
        
    }

}
