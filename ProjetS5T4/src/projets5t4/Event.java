/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projets5t4;

import java.sql.Date;
import java.time.LocalTime;
import java.util.Calendar;

/**
 *
 * @author Gaspard Lefort-Louet
 */
public class Event {
    
    private LocalTime hours;
    private String reason;
    private Date date;
    private double numSecuDocteur, numSecuPatient, numRDV;

    public Event() {
    }

    public Event(LocalTime hours, String reason, double numRDV, Date date, double numSecuDocteur) {
        this.hours = hours;
        this.reason = reason;
        this.numRDV = numRDV;
        this.date = date;
        this.numSecuDocteur = numSecuDocteur;
        this.numSecuPatient = numSecuPatient;
    }

    public double getNumSecuDocteur() {
        return numSecuDocteur;
    }

    public void setNumSecuDocteur(double numSecuDocteur) {
        this.numSecuDocteur = numSecuDocteur;
    }

    public double getNumSecuPatient() {
        return numSecuPatient;
    }

    public void setNumSecuPatient(double numSecuPatient) {
        this.numSecuPatient = numSecuPatient;
    }

    public LocalTime getHours() {
        return hours;
    }

    public void setHours(LocalTime hours) {
        this.hours = hours;
    }

    public double getNumRDV() {
        return numRDV;
    }

    public void setNumRDV(double numRDV) {
        this.numRDV = numRDV;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
    
    
    
}
