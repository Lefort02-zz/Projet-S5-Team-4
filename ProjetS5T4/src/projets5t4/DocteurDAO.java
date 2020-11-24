/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projets5t4;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author Gaspard Lefort-Louet
 */
public class DocteurDAO extends DAO<Docteur> {

    public DocteurDAO(Connection conn) {
        super(conn);
    }

    @Override
    public boolean create(Docteur obj) {

        return false;
    }

    @Override
    public boolean delete(Docteur obj) {

        return false;
    }

    @Override
    public boolean update(Docteur obj) {
        return false;

    }

    @Override
    public Docteur find(double numSecu) {

        Docteur docteur = new Docteur();

        try {
            ResultSet result = this.connect.createStatement(
                    ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_READ_ONLY
            ).executeQuery(
                    "SELECT * FROM docteur WHERE numSécuDocteur = " + numSecu
            );
            if (result.first()) {
                docteur = new Docteur(numSecu, result.getInt("age_Docteur"), result.getString("nom_Docteur"), result.getString("prénom_Docteur"), result.getString("spécialité_Docteur"), result.getString("passwordDocteur"));

            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return docteur;
    }

}
