/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projets5t4;

/**
 *
 * @author Gaspard Lefort-Louet
 */
import java.sql.Date;

public class Patient extends Person {

    private String antecedent;

    public Patient(String name, String lastName, int insuranceN, int born, String passW, String ant, String sexe) {
        super(name, lastName, insuranceN, born, passW, sexe);
        this.antecedent = ant;
    }

    public void setAntecedent(String ant) {
        this.antecedent += "\n" + ant;
    }

    public String getAntecedent() {
        return this.antecedent;
    }

}
