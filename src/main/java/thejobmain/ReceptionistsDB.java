/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package thejobmain;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Liya
 */
public class ReceptionistsDB {

    private static final String DB_URL = "jdbc:mysql://localhost:3306/thejobsdb";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "The_jobs123_ap";

    public ReceptionistsDB() {
    }

    public boolean registerReceptionist(Receptionists receptionist) {
        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
            String query = "INSERT INTO receptionists (rec_name, rec_dob, rec_contact, rec_email, rec_username, rec_password) VALUES (?, ?, ?, ?, ?, ?)";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setString(1, receptionist.getRecName());
                preparedStatement.setString(2, receptionist.getRecDob());
                preparedStatement.setString(3, receptionist.getRecContact());
                preparedStatement.setString(4, receptionist.getRecEmail());
                preparedStatement.setString(5, receptionist.getRecUsername());
                String hashedPassword = hashPassword(receptionist.getRecPassword());
                preparedStatement.setString(6, hashedPassword);

                int rowsInserted = preparedStatement.executeUpdate();
                return rowsInserted > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
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
            String query = "SELECT COUNT(*) FROM receptionists WHERE rec_username = ?";
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

    public Receptionists loginReceptionist(String username, String password) {
        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
            String query = "SELECT * FROM receptionists WHERE rec_username = ? AND rec_password = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setString(1, username);
                preparedStatement.setString(2, password);
                ResultSet resultSet = preparedStatement.executeQuery();

                if (resultSet.next()) {
                    Receptionists receptionist = new Receptionists();
                    receptionist.setRecId(resultSet.getInt("rec_id"));
                    receptionist.setRecName(resultSet.getString("rec_name"));
                    receptionist.setRecDob(resultSet.getString("rec_dob"));
                    receptionist.setRecContact(resultSet.getString("rec_contact"));
                    receptionist.setRecEmail(resultSet.getString("rec_email"));

                    return receptionist;
                }

                return null;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null; 
        }
    }


    public Receptionists viewReceptionistProfile(int recId) {
        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
            String query = "SELECT * FROM receptionists WHERE rec_id = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setInt(1, recId);
                ResultSet resultSet = preparedStatement.executeQuery();

                if (resultSet.next()) {
                    int id = resultSet.getInt("rec_id");
                    String name = resultSet.getString("rec_name");
                    String dob = resultSet.getString("rec_dob");
                    String contact = resultSet.getString("rec_contact");
                    String email = resultSet.getString("rec_email");
                    String username = resultSet.getString("rec_username");
                    String password = resultSet.getString("rec_password");

                    return new Receptionists(id, name, dob, contact, email, username, password);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean updateReceptionist(int recId, String name, String dob, String contact, String email, String username, String password) {
        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
            String query = "UPDATE receptionists SET rec_name = ?, rec_dob = ?, rec_contact = ?, rec_email = ?, rec_username = ?, rec_password = ? WHERE rec_id = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setString(1, name);
                preparedStatement.setString(2, dob);
                preparedStatement.setString(3, contact);
                preparedStatement.setString(4, email);
                preparedStatement.setString(5, username);
                preparedStatement.setString(6, password);
                preparedStatement.setInt(7, recId);

                int rowsAffected = preparedStatement.executeUpdate();

                return rowsAffected > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean deleteReceptionist(int recId) {
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

    public List<JobSeekers> viewJobSeekersDetails() {
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

    public List<Consultants> viewConsultantsDetails() {
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

    public List<Appointment> viewAllAppointments() {
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

    public boolean logout(int recId) {

        return true;
    }

}
