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
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 *
 * @author Liya
 */
public class ConsultantsDB {

    private static final String DB_URL = "jdbc:mysql://localhost:3306/thejobsdb";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "The_jobs123_ap";

    public ConsultantsDB() {

    }

    public Consultants getConsultantDetails(int consultantId) {
        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
            String query = "SELECT * FROM consultants WHERE con_id = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setInt(1, consultantId);
                ResultSet resultSet = preparedStatement.executeQuery();

                if (resultSet.next()) {
                    int conId = resultSet.getInt("con_id");
                    String conName = resultSet.getString("con_name");
                    String conDob = resultSet.getString("con_dob");
                    String conCountry = resultSet.getString("con_country");
                    String conContact = resultSet.getString("con_contact");
                    String conEmail = resultSet.getString("con_email");
                    String conUsername = resultSet.getString("con_username");
                    String conSpecializedFields = resultSet.getString("con_specialized_fields");
                    String conAvailableTime = resultSet.getString("con_available_time");
                    int yearsOfExperience = resultSet.getInt("years_of_experience");

                    Consultants consultant = new Consultants(conId, conName, conDob, conCountry, conContact, conEmail,
                            conUsername, conSpecializedFields, conAvailableTime, yearsOfExperience);

                    return consultant;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    public boolean registerConsultant(Consultants consultant) {
        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
            String insertSql = "INSERT INTO consultants (con_name, con_dob, con_contact, con_email, con_specialized_fields, con_country, years_of_experience,"
                    + " con_available_time, con_username, con_password) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
            try (PreparedStatement preparedStatement = connection.prepareStatement(insertSql)) {
                preparedStatement.setString(1, consultant.getConName());
                preparedStatement.setString(2, consultant.getConDob());
                preparedStatement.setString(3, consultant.getConContact());
                preparedStatement.setString(4, consultant.getConEmail());
                preparedStatement.setString(5, String.join(",", consultant.getConSpecializedFields()));
                preparedStatement.setString(6, consultant.getConCountry());
                preparedStatement.setInt(7, consultant.getYearsOfExperience());
                preparedStatement.setString(8, consultant.getConAvailableTime());
                preparedStatement.setString(9, consultant.getConUsername());
                String hashedPassword = hashPassword(consultant.getConPassword());
                preparedStatement.setString(10, hashedPassword);
                int rowsAffected = preparedStatement.executeUpdate();

                return rowsAffected == 1;
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
            String query = "SELECT COUNT(*) FROM consultants WHERE con_username = ?";
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

    public Consultants loginConsultant(String username, String password) {
        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
            String query = "SELECT * FROM consultants WHERE con_username = ? AND con_password = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setString(1, username);
                preparedStatement.setString(2, password);
                ResultSet resultSet = preparedStatement.executeQuery();

                if (resultSet.next()) {
                    Consultants consultant = new Consultants();
                    consultant.setConId(resultSet.getInt("con_id"));
                    consultant.setConName(resultSet.getString("con_name"));
                    consultant.setConDob(resultSet.getString("con_dob"));
                    consultant.setConContact(resultSet.getString("con_contact"));
                    consultant.setConEmail(resultSet.getString("con_email"));
                    String specializedFieldsString = resultSet.getString("con_specialized_fields");
                    List<String> specializedFieldsList = Arrays.asList(specializedFieldsString.split(","));
                    consultant.setConSpecializedFields(specializedFieldsList);
                    consultant.setConCountry(resultSet.getString("con_country"));

                    return consultant;
                }

                return null;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public Consultants viewConsultantProfile(int consultantId) {
        Consultants consultant = null;
        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
            String query = "SELECT con_name, con_dob, con_country, con_email, con_contact, con_specialized_fields, con_available_time, years_of_experience FROM consultants WHERE con_id = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setInt(1, consultantId);
                ResultSet resultSet = preparedStatement.executeQuery();

                if (resultSet.next()) {
                    String conName = resultSet.getString("con_name");
                    String conDob = resultSet.getString("con_dob");
                    String conCountry = resultSet.getString("con_country");
                    String conEmail = resultSet.getString("con_email");
                    String conContact = resultSet.getString("con_contact");
                    String conSpecializedFields = resultSet.getString("con_specialized_fields");
                    String conAvailableTime = resultSet.getString("con_available_time");
                    int yearsOfExperience = resultSet.getInt("years_of_experience");

                    List<String> specializedFields = new ArrayList<>();
                    if (conSpecializedFields != null && !conSpecializedFields.isEmpty()) {
                        String[] fieldsArray = conSpecializedFields.split(",");
                        for (String field : fieldsArray) {
                            specializedFields.add(field);
                        }
                    }

                    consultant = new Consultants(conName, conDob, conCountry, conEmail, conContact, conAvailableTime, yearsOfExperience, specializedFields);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return consultant;
    }

    
    public List<Appointment> viewRequestedAppointments(int consultantId) {
        List<Appointment> appointments = new ArrayList<>();
        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
            String query = "SELECT a.appointmentId, a.jobSeekerId, a.appointmentDate, a.appointmentStatus, "
                    + "aj.js_name, j.js_contact, j.js_dob, j.js_country, j.js_field "
                    + "FROM appointments a "
                    + "INNER JOIN jobseekers j ON a.jobSeekerId = j.js_id "
                    + "WHERE a.consultantId = ? AND a.appointmentStatus = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setInt(1, consultantId);
                preparedStatement.setString(2, "Requested");
                ResultSet resultSet = preparedStatement.executeQuery();

                while (resultSet.next()) {
                    int appointmentId = resultSet.getInt("appointmentId");
                    int jobSeekerId = resultSet.getInt("jobSeekerId");
                    Date appointmentDate = resultSet.getTimestamp("appointmentDate");
                    String appointmentStatus = resultSet.getString("appointmentStatus");

                    String jsName = resultSet.getString("js_name");
                    String jsContact = resultSet.getString("js_contact");
                    String jsDob = resultSet.getString("js_dob");
                    String jsCountry = resultSet.getString("js_country");
                    String jsField = resultSet.getString("js_field");

                    Appointment appointment = new Appointment(appointmentId, jobSeekerId, consultantId, appointmentDate, appointmentStatus);

                    appointment.setJobSeekerName(jsName);
                    appointment.setJobSeekerContact(jsContact);
                    appointment.setJobSeekerDob(jsDob);
                    appointment.setJobSeekerCountry(jsCountry);
                    List<String> jsFields = new ArrayList<>();
                    jsFields.add(jsField);
                    appointment.setJobSeekerField(jsFields);

                    appointments.add(appointment);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return appointments;
    }

    public boolean confirmAppointment(int appointmentId, int consultantId) {
        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
            String updateSql = "UPDATE appointments SET app_status = 'Confirmed' WHERE app_id = ? AND con_id = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(updateSql)) {
                preparedStatement.setInt(1, appointmentId);
                preparedStatement.setInt(2, consultantId);

                int rowsAffected = preparedStatement.executeUpdate();

                return rowsAffected == 1;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean declineAppointment(int appointmentId, int consultantId) {
        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
            String updateSql = "UPDATE appointments SET app_status = 'Declined' WHERE app_id = ? AND con_id = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(updateSql)) {
                preparedStatement.setInt(1, appointmentId);
                preparedStatement.setInt(2, consultantId);

                int rowsAffected = preparedStatement.executeUpdate();

                return rowsAffected == 1;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }

    }

    public boolean cancelAppointment(int appointmentId) {
        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
            String updateSql = "UPDATE appointments SET app_status = 'Cancelled' WHERE app_id = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(updateSql)) {
                preparedStatement.setInt(1, appointmentId);

                int rowsAffected = preparedStatement.executeUpdate();

                return rowsAffected == 1;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean logout(int consultantId) {
        return false;
    }

}
