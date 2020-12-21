/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projets5t4;

import java.util.List;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/**
 *
 * @author Gaspard Lefort-Louet
 */
public class PatientDAO extends DAO<Patient> { //Hérite de la classe DAO

    public PatientDAO(Connection conn) {
        super(conn);
    }

    @Override
    public void create(Patient obj) {  ///Création d'un nouveau patient dans la base de donnée

        try {

            String sql = "INSERT INTO patient (numSécuPatient, nom, prénom, age, antécedent, mdp, sexe) VALUES (?,?,?,?,?,?,?)";
            PreparedStatement statementPatient = this.connect.prepareStatement(sql);  //Attribue chaque attribut du patient à la base de donnée
            statementPatient.setInt(1, obj.getInsuranceNumber());
            statementPatient.setString(2, obj.getLastName());
            statementPatient.setString(3, obj.getName());
            statementPatient.setInt(4, obj.getBorn());
            statementPatient.setString(5, obj.getAntecedent());
            statementPatient.setString(6, obj.getPassWord());
            statementPatient.setString(7, obj.getSexe());

            int row = statementPatient.executeUpdate();
            if (row > 0) {
                System.out.println("command executed");
            }

            statementPatient.close();

        } // Handle any errors that may have occurred.
        catch (SQLException e) {
            e.printStackTrace();
            System.out.println("DataBase connection error : patient existe deja");
        }

    }

    @Override
    public void delete(int numSecu) {  //Supprime un patient

        try {

            Statement stmt = this.connect.createStatement();

            stmt.execute("DELETE FROM patient WHERE numSécuPatient =" + numSecu);
            stmt.execute("DELETE FROM rdv WHERE numSécuPatient =" + numSecu);

            stmt.close();

        } // Handle any errors that may have occurred.
        catch (SQLException e) {
            e.printStackTrace();
            System.out.println("DataBase connection error");
        }

    }

    @Override
    public boolean update(Patient obj, String ant) { ///Met à jour les antécédents d'un patient
    	
    	 try {

             String sql = "UPDATE patient SET antécedent = '"+ant+"' WHERE numSécuPatient = '"+obj.getInsuranceNumber()+"'";
             PreparedStatement statementPatient = this.connect.prepareStatement(sql);

             int row = statementPatient.executeUpdate();
             
             if (row > 0) {
                 System.out.println("command executed");
             }

             statementPatient.close();

         } // Handle any errors that may have occurred.
         catch (SQLException e) {
             e.printStackTrace();
             System.out.println("DataBase connection error antecedent.");
         }
    	
    	
    	
        return false;

    }

    @Override
    public List<Patient> find() {  //Récupere tous les patients des la base de donnée

        List<Patient> PatientList = new ArrayList<Patient>();

        try {
            ResultSet rs = this.connect.createStatement(
                    ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_READ_ONLY
            ).executeQuery(
                    "SELECT * FROM patient"
            );

            while (rs.next()) {
                Patient pat = new Patient(rs.getString(2), rs.getString(3), rs.getInt(1), rs.getInt(4), rs.getString(6), rs.getString(5), rs.getString(7));
                PatientList.add(pat); //Ajoute un patient à l'arrayList
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return PatientList; //return la liste de patients
    }

    @Override
    public void delete(String numRdv) {
    }

}
