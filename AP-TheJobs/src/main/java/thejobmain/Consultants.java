/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package thejobmain;

import java.util.List;

/**
 *
 * @author Liya
 */
public class Consultants {

    private int conId;
    private String conName;
    private String conDob;
    private String conCountry;
    private String conContact;
    private String conEmail;
    private String conUsername;
    private String conPassword;
    private List<String> conSpecializedFields;
    private String conAvailableTime;
    private int yearsOfExperience;

    public Consultants() {
      
        
    }

    public Consultants(String conName, String conDob, String conCountry, String conEmail, String conContact) {
        
    }

    public Consultants(int conId, String conName, String conDob, String conCountry, String conContact, String conEmail,
            String conUsername, String conPassword, List<String> conSpecializedFields, String conAvailableTime,
            int yearsOfExperience) {
        this.conId = conId;
        this.conName = conName;
        this.conDob = conDob;
        this.conCountry = conCountry;
        this.conContact = conContact;
        this.conEmail = conEmail;
        this.conUsername = conUsername;
        this.conPassword = conPassword;
        this.conSpecializedFields = conSpecializedFields;
        this.conAvailableTime = conAvailableTime;
        this.yearsOfExperience = yearsOfExperience;
    }

    Consultants(int conId, String conName, String conDob, String conCountry, String conContact, String conEmail, String conUsername, String conSpecializedFields, String conAvailableTime, int yearsOfExperience) {
    }

    Consultants(String conName, String conDob, String conCountry, String conEmail, String conContact, String conAvailableTime, int yearsOfExperience, List<String> specializedFields) {
    }

    Consultants(int conId, String conName, String conDob, String conContact, String conEmail, String conUsername) {
    }

   
    // Getters and setters
    public int getConId() {
        return conId;
    }

    public void setConId(int conId) {
        this.conId = conId;
    }

    public String getConName() {
        return conName;
    }

    public void setConName(String conName) {
        this.conName = conName;
    }

    public String getConDob() {
        return conDob;
    }

    public void setConDob(String conDob) {
        this.conDob = conDob;
    }

    public String getConCountry() {
        return conCountry;
    }

    public void setConCountry(String conCountry) {
        this.conCountry = conCountry;
    }

    public String getConContact() {
        return conContact;
    }

    public void setConContact(String conContact) {
        this.conContact = conContact;
    }

    public String getConEmail() {
        return conEmail;
    }

    public void setConEmail(String conEmail) {
        this.conEmail = conEmail;
    }

    public String getConUsername() {
        return conUsername;
    }

    public void setConUsername(String conUsername) {
        this.conUsername = conUsername;
    }

    public String getConPassword() {
        return conPassword;
    }

    public void setConPassword(String conPassword) {
        this.conPassword = conPassword;
    }

    public List<String> getConSpecializedFields() {
        return conSpecializedFields;
    }

    public void setConSpecializedFields(List<String> conSpecializedFields) {
        this.conSpecializedFields = conSpecializedFields;
    }

    public String getConAvailableTime() {
        return conAvailableTime;
    }

    public void setConAvailableTime(String conAvailableTime) {
        this.conAvailableTime = conAvailableTime;
    }

    public int getYearsOfExperience() {
        return yearsOfExperience;
    }

    public void setYearsOfExperience(int yearsOfExperience) {
        this.yearsOfExperience = yearsOfExperience;
    }

}

