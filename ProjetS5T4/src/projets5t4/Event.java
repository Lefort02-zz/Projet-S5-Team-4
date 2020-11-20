/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projets5t4;

import java.time.LocalTime;

/**
 *
 * @author Gaspard Lefort-Louet
 */
public class Event {
    
    private LocalTime hours;
    private String doctor, reason;
    private String date;

    public Event(LocalTime hours, String doctor, String reason, String date) {
        this.hours = hours;
        this.doctor = doctor;
        this.reason = reason;
        this.date = date;
    }

    public LocalTime getHours() {
        return hours;
    }

    public void setHours(LocalTime hours) {
        this.hours = hours;
    }

    public String getDoctor() {
        return doctor;
    }

    public void setDoctor(String doctor) {
        this.doctor = doctor;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
    
    
    
}
