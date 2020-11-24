/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projets5t4;

import java.sql.*;
import java.time.LocalTime;
import java.util.Calendar;
import java.sql.Time;

/**
 *
 * @author Gaspard Lefort-Louet
 */
public class EventDAO extends DAO<Event> {

    public EventDAO(Connection conn) {
        super(conn);
    }

    @Override
    public boolean create(Event obj) {

        return false;
    }

    @Override
    public boolean delete(Event obj) {

        return false;
    }

    @Override
    public boolean update(Event obj) {
        return false;

    }

    @Override
    public Event find(double numSécuPatient) {

        Event rdv = new Event();

        try {
            
            ResultSet result;
            result = this.connect.createStatement(
                    ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_READ_ONLY
            ).executeQuery(
                    "SELECT DISTINCT NuméroRDV, Date_RDV, heureRDV, raison, numSécuDocteur\n"
                            + "FROM rdv\n"
                            + "INNER JOIN patient\n"
                            + "WHERE rdv.numSécuPatient = " + numSécuPatient);
            if (result.first()) {
                rdv = new Event(result.getTime("heureRDV").toLocalTime(), result.getString("raison"), result.getDouble("NuméroRDV"), result.getDate("Date_RDV"), result.getDouble("numSécuDocteur"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return rdv;
    }

}
