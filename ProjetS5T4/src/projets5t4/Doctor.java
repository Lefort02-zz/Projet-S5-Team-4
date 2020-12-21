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
public class Doctor extends Person {  //création d'un docteur

    private String speciality;

     public Doctor(String name, String lastName, int insuranceN, int age, String passW, String spe, String sexe)
     {
         super(name,lastName,insuranceN,age,passW, sexe);
         this.speciality = spe;
     }

    public Doctor() {
    }

    

     public void setSpeciality(String spe)
     {
         this.speciality = spe;
     }

     public String getSpeciality()
     {
         return this.speciality;
     }
}