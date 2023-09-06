/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package thejobmain;

/**
 *
 * @author Liya
 */
public class Receptionists {

    private int recId;
    private String recName;
    private String recDob;
    private String recContact;
    private String recEmail;
    private String recUsername;
    private String recPassword;

    public Receptionists(int recId, String recName, String recDob, String recContact, String recEmail, String recUsername, String recPassword) {
        this.recId = recId;
        this.recName = recName;
        this.recDob = recDob;
        this.recContact = recContact;
        this.recEmail = recEmail;
        this.recUsername = recUsername;
        this.recPassword = recPassword;
    }

    Receptionists(int recId, String recName, String recDob, String recContact, String recEmail, String recUsername) {
    }

    public Receptionists() {
    }

    public int getRecId() {
        return recId;
    }

    public void setRecId(int recId) {
        this.recId = recId;
    }

    public String getRecName() {
        return recName;
    }

    public void setRecName(String recName) {
        this.recName = recName;
    }

    public String getRecDob() {
        return recDob;
    }

    public void setRecDob(String recDob) {
        this.recDob = recDob;
    }

    public String getRecContact() {
        return recContact;
    }

    public void setRecContact(String recContact) {
        this.recContact = recContact;
    }

    public String getRecEmail() {
        return recEmail;
    }

    public void setRecEmail(String recEmail) {
        this.recEmail = recEmail;
    }

    public String getRecUsername() {
        return recUsername;
    }

    public void setRecUsername(String recUsername) {
        this.recUsername = recUsername;
    }

    public String getRecPassword() {
        return recPassword;
    }

    public void setRecPassword(String recPassword) {
        this.recPassword = recPassword;
    }
}
