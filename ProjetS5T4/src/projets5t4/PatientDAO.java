/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projets5t4;

import java.sql.*;

/**
 *
 * @author Gaspard Lefort-Louet
 */
public class PatientDAO extends DAO<Patient> {

    public PatientDAO(Connection conn) {
        super(conn);
    }

    @Override
    public boolean create(Patient obj) {

        return false;
    }

    @Override
    public boolean delete(Patient obj) {

        return false;
    }

    @Override
    public boolean update(Patient obj) {
        return false;

    }

    @Override
    public Patient find(double numSecu) {
        
        Patient patient = new Patient();

        try {
            ResultSet result = this.connect.createStatement(
                    ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_READ_ONLY
            ).executeQuery(
                    "SELECT * FROM patient WHERE numSécuPatient = " + numSecu
            );
            if (result.first()) {
                patient = new Patient(numSecu, result.getString("nom_Patient"), result.getString("prénom_Patient"), result.getString("antécédent_Patient"), result.getString("passwordPatient"), result.getInt("age_Patient"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return patient;
    }

}
