/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package thejobmain;

import java.util.Date;
import java.util.List;


/**
 *
 * @author Liya
 */
public class JobSeekers {

    private int jsId;
    private String jsName;
    private String jsDob;
    private String jsCountry;
    private List<String> jsField; 
    private String jsContact;
    private String jsEmail;
    private String jsUsername;
    private String jsPassword;
    private Date appointmentDate;
    private List<Appointment> appointment; 

    // Constructors
    public JobSeekers() {
        // Default constructor
    }

    public JobSeekers(int jsId, String jsName, String jsDob, String jsCountry, List<String> jsField,
            String jsContact, String jsEmail, String jsUsername, String jsPassword,Date appointmentDate, List<Appointment> appointments) {
        this.jsId = jsId;
        this.jsName = jsName;
        this.jsDob = jsDob;
        this.jsCountry = jsCountry;
        this.jsField = jsField;
        this.jsContact = jsContact;
        this.jsEmail = jsEmail;
        this.jsUsername = jsUsername;
        this.jsPassword = jsPassword;
                this.appointmentDate = appointmentDate;
        this.appointment = appointments;
    }

  public JobSeekers(int jsId, String jsName, String jsDob,
            String jsContact,  String jsCountry, String jsEmail, String jsUsername, String jsField){
      
  }

    JobSeekers(int jsId, String jsName, String jsDob, String jsContact, String jsEmail, String jsUsername) {

    }

    // Getters and setters
    public int getJsId() {
        return jsId;
    }

    public void setJsId(int jsId) {
        this.jsId = jsId;
    }

    public String getJsName() {
        return jsName;
    }

    public void setJsName(String jsName) {
        this.jsName = jsName;
    }

    public String getJsDob() {
        return jsDob;
    }

    public void setJsDob(String jsDob) {
        this.jsDob = jsDob;
    }

    public String getJsCountry() {
        return jsCountry;
    }

    public void setJsCountry(String jsCountry) {
        this.jsCountry = jsCountry;
    }

    public List<String> getJsField() {
        return jsField;
    }

    public void setJsField(List<String> jsField) {
        this.jsField = jsField;
    }

    public String getJsContact() {
        return jsContact;
    }

    public void setJsContact(String jsContact) {
        this.jsContact = jsContact;
    }

    public String getJsEmail() {
        return jsEmail;
    }

    public void setJsEmail(String jsEmail) {
        this.jsEmail = jsEmail;
    }

    public String getJsUsername() {
        return jsUsername;
    }

    public void setJsUsername(String jsUsername) {
        this.jsUsername = jsUsername;
    }

    public String getJsPassword() {
        return jsPassword;
    }

    public void setJsPassword(String jsPassword) {
        this.jsPassword = jsPassword;
    }

    public List<Appointment> getAppointment() {
        return appointment;
    }

    public void setAppointment(List<Appointment> appointment) {
        this.appointment = appointment;
    }

    public Date getAppointmentDate() {
        return appointmentDate;
    }

    public void setAppointmentDate(Date appointmentDate) {
        this.appointmentDate = appointmentDate;
    }

}
