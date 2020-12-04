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

public abstract class Person {

    protected String Name;
    protected String lastName;
    protected String sexe;
    protected int insuranceNumber;
    protected int born;
    protected String password;

    public Person(String name, String lastName, int insuranceN, int born, String passW, String sexe) {
        this.Name = name;
        this.lastName = lastName;
        this.insuranceNumber = insuranceN;
        this.born = born;
        this.password = passW;
        this.sexe = sexe;
    }

    public Person() {
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getName() {
        return this.Name;
    }

    public void setName(String Name) {
        this.Name = Name;
    }

    public int getInsuranceNumber() {
        return this.insuranceNumber;
    }

    public int getBorn() {
        return this.born;
    }

    public String getPassWord() {
        return this.password;
    }

    public void setPassWord(String passW) {
        this.password = passW;
    }

    public String getSexe() {
        return sexe;
    }

    public void setSexe(String sexe) {
        this.sexe = sexe;
    }
    
    
    

}
