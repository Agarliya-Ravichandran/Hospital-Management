/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package thejobmain;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 *
 * @author Liya
 */
public class Appointment {

    private int appointmentId;
    private int jobSeekerId;
    private int consultantId;
    private Date appointmentDate;
    private String appointmentStatus;

    private String jobSeekerName;
    private String jobSeekerContact;
    private String jobSeekerDob;
    private String jobSeekerCountry;
    private List<String> jobSeekerField;

    public Appointment() {
        // Default constructor
    }

    public Appointment(int appointmentId, int jobSeekerId, int consultantId, Date appointmentDate, String appointmentStatus) {
        this.appointmentId = appointmentId;
        this.jobSeekerId = jobSeekerId;
        this.consultantId = consultantId;
        this.appointmentDate = appointmentDate;
        this.appointmentStatus = appointmentStatus;
    }

    public Appointment(int appointmentId, int jobSeekerId, int consultantId, String appointmentDate, String appointmentStatus) {
        this.appointmentId = appointmentId;
        this.jobSeekerId = jobSeekerId;
        this.consultantId = consultantId;

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd"); // Adjust the date format as needed
        try {
            this.appointmentDate = dateFormat.parse(appointmentDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        this.appointmentStatus = appointmentStatus;
    }

    public int getAppointmentId() {
        return appointmentId;
    }

    public void setAppointmentId(int appointmentId) {
        this.appointmentId = appointmentId;
    }

    public int getJobSeekerId() {
        return jobSeekerId;
    }

    public void setJobSeekerId(int jobSeekerId) {
        this.jobSeekerId = jobSeekerId;
    }

    public int getConsultantId() {
        return consultantId;
    }

    public void setConsultantId(int consultantId) {
        this.consultantId = consultantId;
    }

    public Date getAppointmentDate() {
        return appointmentDate;
    }

    public void setAppointmentDate(Date appointmentDate) {
        this.appointmentDate = appointmentDate;
    }

    public String getAppointmentStatus() {
        return appointmentStatus;
    }

    public void setAppointmentStatus(String appointmentStatus) {
        this.appointmentStatus = appointmentStatus;
    }

    public String getJobSeekerName() {
        return jobSeekerName;
    }

    public void setJobSeekerName(String jobSeekerName) {
        this.jobSeekerName = jobSeekerName;
    }

    public String getJobSeekerContact() {
        return jobSeekerContact;
    }

    public void setJobSeekerContact(String jobSeekerContact) {
        this.jobSeekerContact = jobSeekerContact;
    }

    public String getJobSeekerDob() {
        return jobSeekerDob;
    }

    public void setJobSeekerDob(String jobSeekerDob) {
        this.jobSeekerDob = jobSeekerDob;
    }

    public String getJobSeekerCountry() {
        return jobSeekerCountry;
    }

    public void setJobSeekerCountry(String jobSeekerCountry) {
        this.jobSeekerCountry = jobSeekerCountry;
    }

    public List<String> getJobSeekerField() {
        return jobSeekerField;
    }

    public void setJobSeekerField(List<String> jobSeekerField) {
        this.jobSeekerField = jobSeekerField;
    }

}
