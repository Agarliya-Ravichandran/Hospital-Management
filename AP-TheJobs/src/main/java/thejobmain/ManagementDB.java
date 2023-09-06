/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package thejobmain;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Liya
 */
public class ManagementDB {

    private static final String DB_URL = "jdbc:mysql://localhost:3306/thejobsdb";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "The_jobs123_ap";

    public boolean registerManagement(Management management) {
        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
            String query = "INSERT INTO management (staff_name, staff_dob, staff_contact, staff_email, staff_username, staff_password) VALUES (?, ?, ?, ?, ?, ?)";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setString(1, management.getStaffName());
                preparedStatement.setString(2, management.getStaffDob());
                preparedStatement.setString(3, management.getStaffContact());
                preparedStatement.setString(4, management.getStaffEmail());
                preparedStatement.setString(5, management.getStaffUsername());
                String hashedPassword = hashPassword(management.getStaffPassword());
                preparedStatement.setString(6, hashedPassword);
                int rowsInserted = preparedStatement.executeUpdate();
                return rowsInserted > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;

        }
    }

    private String hashPassword(String password) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] hashedBytes = md.digest(password.getBytes(StandardCharsets.UTF_8));

            StringBuilder sb = new StringBuilder();
            for (byte b : hashedBytes) {
                sb.append(String.format("%02x", b));
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
    }

    public boolean isUsernameTaken(String username) {
        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
            String query = "SELECT COUNT(*) FROM management WHERE staff_username = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setString(1, username);
                ResultSet resultSet = preparedStatement.executeQuery();

                if (resultSet.next()) {
                    int count = resultSet.getInt(1);
                    return count > 0; 
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false; 
    }

    public Management loginManagement(String username, String password) {
        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
            String query = "SELECT * FROM management WHERE staff_username = ? AND staff_password = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setString(1, username);
                preparedStatement.setString(2, password);
                ResultSet resultSet = preparedStatement.executeQuery();

                if (resultSet.next()) {
                    Management management = new Management();
                    management.setStaffId(resultSet.getInt("staff_id"));
                    management.setStaffName(resultSet.getString("staff_name"));
                    management.setStaffDob(resultSet.getString("staff_dob"));
                    management.setStaffContact(resultSet.getString("staff_contact"));
                    management.setStaffEmail(resultSet.getString("staff_email"));

                    return management; 
                }

                return null; 
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null; 
        }
    }

    public Management viewManagementProfile(int staffId) {
        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
            String query = "SELECT * FROM management WHERE staff_id = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setInt(1, staffId);
                ResultSet resultSet = preparedStatement.executeQuery();

                if (resultSet.next()) {
                    int id = resultSet.getInt("staff_id");
                    String name = resultSet.getString("staff_name");
                    String dob = resultSet.getString("staff_dob");
                    String contact = resultSet.getString("staff_contact");
                    String email = resultSet.getString("staff_email");
                    String username = resultSet.getString("staff_username");
                    String password = resultSet.getString("staff_password");

                    return new Management(id, name, dob, contact, email, username, password);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean updateManagementProfile(int staffId, Management updatedManagement) {
        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
            String query = "UPDATE management SET staff_name = ?, staff_dob = ?, staff_contact = ?, staff_email = ?, staff_username = ?, staff_password = ? WHERE staff_id = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setString(1, updatedManagement.getStaffName());
                preparedStatement.setString(2, updatedManagement.getStaffDob());
                preparedStatement.setString(3, updatedManagement.getStaffContact());
                preparedStatement.setString(4, updatedManagement.getStaffEmail());
                preparedStatement.setString(5, updatedManagement.getStaffUsername());
                preparedStatement.setString(6, updatedManagement.getStaffPassword());
                preparedStatement.setInt(7, staffId);

                int rowsAffected = preparedStatement.executeUpdate();
                return rowsAffected > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean deleteManagementProfile(int staffId) {
        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
            String query = "DELETE FROM management WHERE staff_id = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setInt(1, staffId);

                int rowsAffected = preparedStatement.executeUpdate();
                return rowsAffected > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public List<Management> viewAllManagementProfiles() {
        List<Management> managementList = new ArrayList<>();
        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
            String query = "SELECT * FROM management";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                ResultSet resultSet = preparedStatement.executeQuery();

                while (resultSet.next()) {
                    int id = resultSet.getInt("staff_id");
                    String name = resultSet.getString("staff_name");
                    String dob = resultSet.getString("staff_dob");
                    String contact = resultSet.getString("staff_contact");
                    String email = resultSet.getString("staff_email");
                    String username = resultSet.getString("staff_username");
                    String password = resultSet.getString("staff_password");

                    Management management = new Management(id, name, dob, contact, email, username, password);
                    managementList.add(management);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return managementList;
    }

    public List<Appointment> viewAllAppointmentsM() {
        List<Appointment> appointments = new ArrayList<>();
        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
            String query = "SELECT * FROM appointments";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                ResultSet resultSet = preparedStatement.executeQuery();

                while (resultSet.next()) {
                    int appointmentId = resultSet.getInt("appointment_id");
                    int jobSeekerId = resultSet.getInt("job_seeker_id");
                    int consultantId = resultSet.getInt("consultant_id");
                    String appointmentDate = resultSet.getString("appointment_date");
                    String appointmentStatus = resultSet.getString("appointment_status");

                    Appointment appointment = new Appointment(appointmentId, jobSeekerId, consultantId, appointmentDate, appointmentStatus);

                    appointments.add(appointment);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return appointments;
    }

    public List<JobSeekers> viewAllJobSeekers() {
        List<JobSeekers> jobSeekersList = new ArrayList<>();
        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
            String query = "SELECT * FROM jobseekers";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                ResultSet resultSet = preparedStatement.executeQuery();

                while (resultSet.next()) {
                    int jsId = resultSet.getInt("js_id");
                    String jsName = resultSet.getString("js_name");
                    String jsDob = resultSet.getString("js_dob");
                    String jsContact = resultSet.getString("js_contact");
                    String jsEmail = resultSet.getString("js_email");
                    String jsUsername = resultSet.getString("js_username");

                    JobSeekers jobSeeker = new JobSeekers(jsId, jsName, jsDob, jsContact, jsEmail, jsUsername);
                    jobSeekersList.add(jobSeeker);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return jobSeekersList;
    }

    public List<Consultants> viewAllConsultants() {
        List<Consultants> consultantsList = new ArrayList<>();
        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
            String query = "SELECT * FROM consultants";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                ResultSet resultSet = preparedStatement.executeQuery();

                while (resultSet.next()) {
                    int conId = resultSet.getInt("con_id");
                    String conName = resultSet.getString("con_name");
                    String conDob = resultSet.getString("con_dob");
                    String conContact = resultSet.getString("con_contact");
                    String conEmail = resultSet.getString("con_email");
                    String conUsername = resultSet.getString("con_username");

                    Consultants consultant = new Consultants(conId, conName, conDob, conContact, conEmail, conUsername);
                    consultantsList.add(consultant);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return consultantsList;
    }

    public List<Receptionists> viewAllReceptionists() {
        List<Receptionists> receptionistsList = new ArrayList<>();
        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
            String query = "SELECT * FROM receptionists";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                ResultSet resultSet = preparedStatement.executeQuery();

                while (resultSet.next()) {
                    int recId = resultSet.getInt("rec_id");
                    String recName = resultSet.getString("rec_name");
                    String recDob = resultSet.getString("rec_dob");
                    String recContact = resultSet.getString("rec_contact");
                    String recEmail = resultSet.getString("rec_email");
                    String recUsername = resultSet.getString("rec_username");

                    Receptionists receptionist = new Receptionists(recId, recName, recDob, recContact, recEmail, recUsername);
                    receptionistsList.add(receptionist);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return receptionistsList;
    }

    public boolean deleteConsultant(int consultantId) {
        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
            String query = "DELETE FROM consultants WHERE con_id = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setInt(1, consultantId);
                int rowsAffected = preparedStatement.executeUpdate();

                return rowsAffected > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean deleteReceptionistM(int recId) {
        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
            String query = "DELETE FROM receptionists WHERE rec_id = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setInt(1, recId);
                int rowsAffected = preparedStatement.executeUpdate();

                return rowsAffected > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean updateConsultant(int conId, String conName, String conDob, String conContact, String conEmail, List<String> specializedFields) {
        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
            String query = "UPDATE consultants SET con_name = ?, con_dob = ?, con_contact = ?, con_email = ?, con_username = ?, con_password = ? WHERE con_id = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setString(1, conName);
                preparedStatement.setString(2, conDob);
                preparedStatement.setString(3, conContact);
                preparedStatement.setString(4, conEmail);
                String specializedFieldsStr = String.join(",", specializedFields);
                preparedStatement.setString(7, specializedFieldsStr);
                preparedStatement.setInt(8, conId);

                int rowsAffected = preparedStatement.executeUpdate();

                return rowsAffected > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean updateReceptionist(int recId, String recName, String recDob, String recContact, String recEmail, String recUsername, String recPassword) {
        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
            String query = "UPDATE receptionists SET rec_name = ?, rec_dob = ?, rec_contact = ?, rec_email = ?, rec_username = ?, rec_password = ? WHERE rec_id = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setString(1, recName);
                preparedStatement.setString(2, recDob);
                preparedStatement.setString(3, recContact);
                preparedStatement.setString(4, recEmail);
                preparedStatement.setString(5, recUsername);
                preparedStatement.setString(6, recPassword);
                preparedStatement.setInt(7, recId);

                int rowsAffected = preparedStatement.executeUpdate();

                return rowsAffected > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean logout(int staffId) {

        return true;
    }
}
