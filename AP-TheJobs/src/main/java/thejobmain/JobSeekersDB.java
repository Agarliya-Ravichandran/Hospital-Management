/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package thejobmain;

import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.core.Response;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import java.sql.DriverManager;

/**
 *
 * @author Liya
 */
public class JobSeekersDB {

    private static final String DB_URL = "jdbc:mysql://localhost:3306/thejobsdb";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "The_jobs123_ap";

    public JobSeekers getJobSeekerDetails(int jobSeekerId) {
        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
            String query = "SELECT * FROM jobseekers WHERE js_id = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setInt(1, jobSeekerId);
                ResultSet resultSet = preparedStatement.executeQuery();

                if (resultSet.next()) {
                    int jsId = resultSet.getInt("js_id");
                    String jsName = resultSet.getString("js_name");
                    String jsDob = resultSet.getString("js_dob");
                    String jsContact = resultSet.getString("js_contact");
                    String jsCountry = resultSet.getString("js_country");
                    String jsEmail = resultSet.getString("js_email");
                    String jsUsername = resultSet.getString("js_username");
                    String jsField = resultSet.getString("js_field");

                    JobSeekers jobSeeker = new JobSeekers(jsId, jsName, jsDob, jsContact, jsCountry, jsEmail, jsUsername, jsField);
                    return jobSeeker;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean registerJobSeeker(JobSeekers jobSeeker) {
        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
            String insertSql = "INSERT INTO jobseekers (js_name, js_dob, js_country, js_field, js_contact, js_email, js_username, js_password) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
            try (PreparedStatement preparedStatement = connection.prepareStatement(insertSql)) {
                preparedStatement.setString(1, jobSeeker.getJsName());
                preparedStatement.setString(2, jobSeeker.getJsDob());
                preparedStatement.setString(3, jobSeeker.getJsCountry());
                preparedStatement.setString(4, String.join(",", jobSeeker.getJsField()));
                preparedStatement.setString(5, jobSeeker.getJsContact());
                preparedStatement.setString(6, jobSeeker.getJsEmail());
                preparedStatement.setString(7, jobSeeker.getJsUsername());
                String hashedPassword = hashPassword(jobSeeker.getJsPassword());
                preparedStatement.setString(8, hashedPassword);

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
            String query = "SELECT COUNT(*) FROM jobseekers WHERE js_username = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setString(1, username);
                ResultSet resultSet = preparedStatement.executeQuery();

                if (resultSet.next() && resultSet.getInt(1) > 0) {
                    return true;
                }

                return false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public JobSeekers loginJobSeeker(String username, String password) {
        JobSeekers jobSeeker = null;

        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
            String query = "SELECT * FROM jobseekers WHERE js_username = ? AND js_password = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setString(1, username);
                preparedStatement.setString(2, password);
                ResultSet resultSet = preparedStatement.executeQuery();

                if (resultSet.next()) {
                    jobSeeker = new JobSeekers();
                    jobSeeker.setJsId(resultSet.getInt("js_id"));
                    jobSeeker.setJsName(resultSet.getString("js_name"));
                    jobSeeker.setJsDob(resultSet.getString("js_dob"));
                    jobSeeker.setJsEmail(resultSet.getString("js_email"));
                    jobSeeker.setJsContact(resultSet.getString("js_contact"));

                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return jobSeeker;
    }

    public Consultants viewConsultantDetails(int consultantId) {
        Consultants consultant = null;
        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
            String query = "SELECT con_name, con_dob, con_country, con_email, con_contact, con_specialized_fields FROM consultants WHERE con_id = ?";
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

                    List<String> specializedFieldsList = Arrays.asList(conSpecializedFields.split(","));

                    consultant = new Consultants(conName, conDob, conCountry, conEmail, conContact);
                    consultant.setConSpecializedFields(specializedFieldsList);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return consultant;
    }

    public boolean scheduleAppointment(int jobSeekerId, int consultantId, Date appointmentDate) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String appointmentDateString = dateFormat.format(appointmentDate);

        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
            String insertSql = "INSERT INTO appointments (js_id, con_id, app_date, app_status) VALUES (?, ?, ?, ?)";
            try (PreparedStatement preparedStatement = connection.prepareStatement(insertSql)) {
                preparedStatement.setInt(1, jobSeekerId);
                preparedStatement.setInt(2, consultantId);
                preparedStatement.setString(3, appointmentDateString);
                preparedStatement.setString(4, "Requested");

                int rowsAffected = preparedStatement.executeUpdate();

                return rowsAffected == 1;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean cancelAppointment(int jobSeekerId, int appointmentId) {
        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
            String updateSql = "UPDATE appointments SET app_status = 'Cancelled' WHERE js_id = ? AND app_id = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(updateSql)) {
                preparedStatement.setInt(1, jobSeekerId);
                preparedStatement.setInt(2, appointmentId);

                int rowsAffected = preparedStatement.executeUpdate();

                return rowsAffected == 1;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public JobSeekers getJobSeekerProfile(int jobSeekerId) {
        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
            String query = "SELECT * FROM jobseekers WHERE js_id = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setInt(1, jobSeekerId);
                ResultSet resultSet = preparedStatement.executeQuery();

                if (resultSet.next()) {
                    int jsId = resultSet.getInt("js_id");
                    String jsName = resultSet.getString("js_name");
                    String jsDob = resultSet.getString("js_dob");
                    String jsContact = resultSet.getString("js_contact");
                    String jsCountry = resultSet.getString("js_country");
                    String jsEmail = resultSet.getString("js_email");
                    String jsUsername = resultSet.getString("js_username");
                    String jsField = resultSet.getString("js_field");

                    JobSeekers jobSeeker = new JobSeekers(jsId, jsName, jsDob, jsContact, jsCountry, jsEmail, jsUsername, jsField);
                    return jobSeeker;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean updateJobSeeker(int jobSeekerId, String newName, String newContact, String newDob, String newCountry) {
        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
            String updateSql = "UPDATE jobseekers SET js_name = ?, js_contact = ?, js_dob = ?, js_country = ? WHERE js_id = ?";

            try (PreparedStatement preparedStatement = connection.prepareStatement(updateSql)) {
                preparedStatement.setString(1, newName);
                preparedStatement.setString(2, newContact);
                preparedStatement.setString(3, newDob);
                preparedStatement.setString(4, newCountry);
                preparedStatement.setInt(5, jobSeekerId);

                int rowsAffected = preparedStatement.executeUpdate();

                return rowsAffected == 1;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean deleteJobSeeker(int jobSeekerId) {
        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
            String deleteSql = "DELETE FROM jobseekers WHERE js_id = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(deleteSql)) {
                preparedStatement.setInt(1, jobSeekerId);

                int rowsAffected = preparedStatement.executeUpdate();

                return rowsAffected == 1;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<Appointment> getAppointment(int jobSeekerId) {
        List<Appointment> appointments = new ArrayList<>();
        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
            String query = "SELECT * FROM appointments WHERE js_id = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setInt(1, jobSeekerId);
                ResultSet resultSet = preparedStatement.executeQuery();

                while (resultSet.next()) {
                    int appointmentId = resultSet.getInt("app_id");
                    int consultantId = resultSet.getInt("con_id");
                    String appointmentDate = resultSet.getString("app_date");
                    String appointmentStatus = resultSet.getString("app_status");

                    Appointment appointment = new Appointment(appointmentId, jobSeekerId, consultantId, appointmentDate, appointmentStatus);
                    appointments.add(appointment);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return appointments;
    }

    public boolean logout(int jobSeekerId) {

        return false;
    }
}
