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
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Gaspard Lefort-Louet
 */
public class RDVDAO extends DAO<RDV> {

    public RDVDAO(Connection conn) {
        super(conn);
    }

    @Override
    public void create(RDV obj) {

        try {

            String sql = "INSERT INTO rdv (NuméroRDV, numSécuDocteur, numSécuPatient, Date, Horaire, raison) VALUES (?,?,?,?,?,?)";
            PreparedStatement statementRDV = this.connect.prepareStatement(sql);
            statementRDV.setString(1, obj.getNumberRDV());
            statementRDV.setInt(2, obj.getDoctor().getInsuranceNumber());
            statementRDV.setInt(3, obj.getPatient().getInsuranceNumber());
            statementRDV.setDate(4, obj.getDate());
            statementRDV.setTime(5, obj.getTime());
            statementRDV.setString(6, obj.getInformations());

            int row = statementRDV.executeUpdate();
            if (row > 0) {
                System.out.println("command executed");
            }

            statementRDV.close();

        } // Handle any errors that may have occurred.
        catch (SQLException e) {
            e.printStackTrace();
            System.out.println("DataBase connection error");
        }
    }

    @Override
    public void delete(String numRdv) {
        
          try {

            Statement stmt = this.connect.createStatement();
            stmt.execute("DELETE FROM rdv WHERE numéroRDV = \"" + numRdv + "\"");

            stmt.close();

        } 
        catch (SQLException e) 
        {
            e.printStackTrace();
        }
      }

    
    @Override
    public boolean update(RDV obj, String ant) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<RDV> find() {

        List<RDV> RDVList = new ArrayList<RDV>();

        DAO<Patient> patientDAO = new PatientDAO(SQL.getInstance());
        DAO<Doctor> docteurDAO = new DocteurDAO(SQL.getInstance());

        try {

            List<Doctor> DoctorList = docteurDAO.find();
            List<Patient> PatientList = patientDAO.find();

            ResultSet rs = this.connect.createStatement(
                    ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_READ_ONLY
            ).executeQuery(
                    "SELECT * FROM rdv"
            );

            String Text = "";
            Doctor doc = null;
            Patient pat = null;

            while (rs.next()) {
                for (int i = 0; i < DoctorList.size(); i++) {
                    if (rs.getInt(2) == DoctorList.get(i).getInsuranceNumber()) {
                        doc = DoctorList.get(i);
                    }
                }
                for (int i = 0; i < PatientList.size(); i++) {
                    if (rs.getInt(3) == PatientList.get(i).getInsuranceNumber()) {
                        pat = PatientList.get(i);
                    }
                }

                RDV rdv = new RDV(doc, pat, rs.getDate(4), rs.getTime(5), rs.getString(6), rs.getString(1));
                RDVList.add(rdv);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return RDVList;

    }

    @Override
    public void delete(int numSécu) {
        
         }

}
