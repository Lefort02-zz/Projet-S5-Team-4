/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projets5t4;

import java.sql.*;
import javax.sql.*;

/**
 *
 * @author Gaspard Lefort-Louet
 */
public class SQL {
    
    private static Connection conn;

    public static Connection getInstance()
    {
        try {
            
            String url = "jdbc:mysql://localhost:3306/ProjetTeam4";
            String user = "root";
            String password = "Augustin01";
            
            conn=DriverManager.getConnection(url, user, password);
            
            Statement stmt = conn.createStatement();
            
            System.out.println("Connection Successfull");

            
        } catch (SQLException e) {
            
            System.out.println(e.getMessage());
        }
        
        return conn;
    }
        
}
