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
public class Docteur {
    
    private double numSecuDocteur;
    private int age;
    private String nomDoc, prenomDoc, spéDoc, passwordDoc;

    public Docteur() {
    }

    public Docteur(double numSecuDocteur, int age, String nomDoc, String prenomDoc, String spéDoc, String passwordDoc) {
        this.numSecuDocteur = numSecuDocteur;
        this.age = age;
        this.nomDoc = nomDoc;
        this.prenomDoc = prenomDoc;
        this.spéDoc = spéDoc;
        this.passwordDoc = passwordDoc;
    }

    public double getNumSecuDocteur() {
        return numSecuDocteur;
    }

    public void setNumSecuDocteur(double numSecuDocteur) {
        this.numSecuDocteur = numSecuDocteur;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getNomDoc() {
        return nomDoc;
    }

    public void setNomDoc(String nomDoc) {
        this.nomDoc = nomDoc;
    }

    public String getPrenomDoc() {
        return prenomDoc;
    }

    public void setPrenomDoc(String prenomDoc) {
        this.prenomDoc = prenomDoc;
    }

    public String getSpéDoc() {
        return spéDoc;
    }

    public void setSpéDoc(String spéDoc) {
        this.spéDoc = spéDoc;
    }

    public String getPasswordDoc() {
        return passwordDoc;
    }

    public void setPasswordDoc(String passwordDoc) {
        this.passwordDoc = passwordDoc;
    }
    
    
}
