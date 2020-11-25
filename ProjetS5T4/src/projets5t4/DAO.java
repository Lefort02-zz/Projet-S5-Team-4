/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projets5t4;

import java.util.List;
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
        

        public abstract void create(T obj);
  
        public abstract void delete(int numSécu);
        
        public abstract void delete(String numRdv);
  
        public abstract boolean update(T obj);

        public abstract List<T> find();
        
    
}
