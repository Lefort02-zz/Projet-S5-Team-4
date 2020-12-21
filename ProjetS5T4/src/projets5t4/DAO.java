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
    
        public DAO(Connection conn)
        {
                this.connect = conn;
        }
        

        public abstract void create(T obj);  //création d'un objet dans la base de donnée
  
        public abstract void delete(int numSécu); //suppression d'un élè_ment de la base de donnée par rapport à un int
        
        public abstract void delete(String numRdv);  //suppression d'un élè_ment de la base de donnée par rapport à un String
  
        public abstract boolean update(T obj, String S);  //Mise à jour d'une ligne de la base de donnée

        public abstract List<T> find();  //Récupération de toutes les informations de la base de donnée
        
    
}
