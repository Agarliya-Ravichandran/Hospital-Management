/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package thejobmain;

/**
 *
 * @author Liya
 */
public class Management {
     private int staffId;
    private String staffName;
    private String staffDob;
    private String staffContact;
    private String staffEmail;
    private String staffUsername;
    private String staffPassword;

    // Constructors
    public Management() {
        // Default constructor
    }

    public Management(int staffId, String staffName, String staffDob, String staffContact, String staffEmail, String staffUsername, String staffPassword) {
        this.staffId = staffId;
        this.staffName = staffName;
        this.staffDob = staffDob;
        this.staffContact = staffContact;
        this.staffEmail = staffEmail;
        this.staffUsername = staffUsername;
        this.staffPassword = staffPassword;
    }
    public int getStaffId() {
        return staffId;
    }

    public void setStaffId(int staffId) {
        this.staffId = staffId;
    }

    public String getStaffName() {
        return staffName;
    }

    public void setStaffName(String staffName) {
        this.staffName = staffName;
    }

    public String getStaffDob() {
        return staffDob;
    }

    public void setStaffDob(String staffDob) {
        this.staffDob = staffDob;
    }

    public String getStaffContact() {
        return staffContact;
    }

    public void setStaffContact(String staffContact) {
        this.staffContact = staffContact;
    }

    public String getStaffEmail() {
        return staffEmail;
    }

    public void setStaffEmail(String staffEmail) {
        this.staffEmail = staffEmail;
    }

    public String getStaffUsername() {
        return staffUsername;
    }

    public void setStaffUsername(String staffUsername) {
        this.staffUsername = staffUsername;
    }

    public String getStaffPassword() {
        return staffPassword;
    }

    public void setStaffPassword(String staffPassword) {
        this.staffPassword = staffPassword;
    }
}
