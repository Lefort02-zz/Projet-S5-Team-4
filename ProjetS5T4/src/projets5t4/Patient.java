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
public class Patient {
    
    private double numSecuPatient;
    private String nomPatient, prenomPatient, antecedent, password;
    private int age;

    public Patient() {
    }

    public Patient(double numSecuPatient, String nomPatient, String prenomPatient, String antecedent, String password, int age) {
        this.numSecuPatient = numSecuPatient;
        this.nomPatient = nomPatient;
        this.prenomPatient = prenomPatient;
        this.antecedent = antecedent;
        this.password = password;
        this.age = age;
    }

    

    public double getNumSecuPatient() {
        return numSecuPatient;
    }

    public void setNumSecuPatient(double numSecuPatient) {
        this.numSecuPatient = numSecuPatient;
    }

    public String getNomPatient() {
        return nomPatient;
    }

    public void setNomPatient(String nomPatient) {
        this.nomPatient = nomPatient;
    }

    public String getPrenomPatient() {
        return prenomPatient;
    }

    public void setPrenomPatient(String prenomPatient) {
        this.prenomPatient = prenomPatient;
    }

    public String getAntecedent() {
        return antecedent;
    }

    public void setAntecedent(String antecedent) {
        this.antecedent = antecedent;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
    
    
}
