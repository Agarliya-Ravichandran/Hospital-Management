/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package thejobmain;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Liya
 */
public class AppointmentDB {

    private static final String DB_URL = "jdbc:mysql://localhost:3306/thejobsdb";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "The_jobs123_ap";

     public boolean createAppointment(Appointment appointment) {
        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
            String query = "INSERT INTO appointments (job_seeker_id, consultant_id, appointment_date, appointment_status) VALUES (?, ?, ?, ?)";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setInt(1, appointment.getJobSeekerId());
                preparedStatement.setInt(2, appointment.getConsultantId());
                Timestamp appointmentDate = new Timestamp(appointment.getAppointmentDate().getTime());
                preparedStatement.setTimestamp(3, appointmentDate);
                preparedStatement.setString(4, appointment.getAppointmentStatus());

                int rowsInserted = preparedStatement.executeUpdate();
                return rowsInserted > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public Appointment readAppointment(int appointmentId) {
        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
            String query = "SELECT * FROM appointments WHERE appointment_id = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setInt(1, appointmentId);
                ResultSet resultSet = preparedStatement.executeQuery();

                if (resultSet.next()) {
                    int jobSeekerId = resultSet.getInt("job_seeker_id");
                    int consultantId = resultSet.getInt("consultant_id");
                    String appointmentDate = resultSet.getString("appointment_date");
                    String appointmentStatus = resultSet.getString("appointment_status");

                    return new Appointment(appointmentId, jobSeekerId, consultantId, appointmentDate, appointmentStatus);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean updateAppointment(Appointment appointment) {
        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
            String query = "UPDATE appointments SET job_seeker_id = ?, consultant_id = ?, appointment_date = ?, appointment_status = ? WHERE appointment_id = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setInt(1, appointment.getJobSeekerId());
                preparedStatement.setInt(2, appointment.getConsultantId());
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                String appointmentDateS = dateFormat.format(appointment.getAppointmentDate());
                preparedStatement.setString(3, appointmentDateS);
                preparedStatement.setString(4, appointment.getAppointmentStatus());
                preparedStatement.setInt(5, appointment.getAppointmentId());

                int rowsAffected = preparedStatement.executeUpdate();
                return rowsAffected > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean deleteAppointment(int appointmentId) {
        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
            String query = "DELETE FROM appointments WHERE appointment_id = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setInt(1, appointmentId);

                int rowsDeleted = preparedStatement.executeUpdate();
                return rowsDeleted > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public List<Appointment> getAllAppointments() {
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
}
