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
public abstract class DAO<T> {
    
    protected Connection connect = null;
        

        public DAO(Connection conn){
                this.connect = conn;
        }
        

        public abstract boolean create(T obj);
  
        public abstract boolean delete(T obj);
  
        public abstract boolean update(T obj);

        public abstract T find(double id);
        
    
}
