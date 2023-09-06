package com.mycompany.ap.thejobs.resources;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.Cookie;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.NewCookie;
import jakarta.ws.rs.core.Response;
import java.util.List;
import thejobmain.Appointment;
import thejobmain.Consultants;
import thejobmain.ConsultantsDB;
import thejobmain.JobSeekers;
import thejobmain.JobSeekersDB;
import thejobmain.Management;
import thejobmain.ManagementDB;
import thejobmain.Receptionists;
import thejobmain.ReceptionistsDB;

@Path("/users")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class UsersResource {

    @Context
    private HttpServletRequest request;

    public static final String USER_TYPE_JOB_SEEKER = "jobSeeker";
    public static final String USER_TYPE_CONSULTANT = "consultant";
    public static final String USER_TYPE_RECEPTIONIST = "receptionist";
    public static final String USER_TYPE_MANAGEMENT = "management";

    private JobSeekersDB jobSeekersDB;
    private ConsultantsDB consultantsDB;
    private ReceptionistsDB receptionistsDB;
    private ManagementDB managementDB;
    private JobSeekers jobSeeker;
    private Consultants consultant;
    private Receptionists receptionist;
    private Management management;

    public UsersResource() {

        this.jobSeeker = new JobSeekers();
        this.consultant = new Consultants();
        this.receptionist = new Receptionists();
        this.management = new Management();
        this.jobSeekersDB = new JobSeekersDB();
        this.consultantsDB = new ConsultantsDB();
        this.receptionistsDB = new ReceptionistsDB();
        this.managementDB = new ManagementDB();
    }

    @POST
    @Path("/register")
    public Response registerUser(
            @FormParam("userType") String userType,
            @FormParam("name") String name,
            @FormParam("dateOfBirth") String dateOfBirth,
            @FormParam("contact") String contact,
            @FormParam("email") String email,
            @FormParam("username") String username,
            @FormParam("password") String password,
            @FormParam("country") String country,
            @FormParam("jobField") List<String> jobField,
            @FormParam("yearsOfExperience") int yearsOfExperience,
            @FormParam("availableTime") String availableTime) {

        if (USER_TYPE_JOB_SEEKER.equals(userType)) {
            JobSeekers jobSeeker = new JobSeekers();
            jobSeeker.setJsName(name);
            jobSeeker.setJsDob(dateOfBirth);
            jobSeeker.setJsContact(contact);
            jobSeeker.setJsEmail(email);
            jobSeeker.setJsCountry(country);
            jobSeeker.setJsField(jobField);
            jobSeeker.setJsUsername(username);
            jobSeeker.setJsPassword(password);

            if (jobSeekersDB.isUsernameTaken(jobSeeker.getJsUsername())) {
                return Response.status(Response.Status.BAD_REQUEST).entity("Username is already taken").build();
            }

            if (!isValidJobSeeker(jobSeeker)) {
                return Response.status(Response.Status.BAD_REQUEST).entity("Invalid registration data").build();
            }

            boolean success = jobSeekersDB.registerJobSeeker(jobSeeker);
            if (success) {
                return Response.status(Response.Status.CREATED).entity("Job Seeker registered successfully").build();
            } else {
                return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Registration failed").build();
            }

        } else if (USER_TYPE_CONSULTANT.equals(userType)) {

            Consultants consultant = new Consultants();
            consultant.setConName(name);
            consultant.setConDob(dateOfBirth);
            consultant.setConContact(contact);
            consultant.setConEmail(email);
            consultant.setConCountry(country);
            consultant.setConSpecializedFields(jobField);
            consultant.setYearsOfExperience(yearsOfExperience);
            consultant.setConAvailableTime(availableTime);
            consultant.setConUsername(username);
            consultant.setConPassword(password);

            if (consultantsDB.isUsernameTaken(consultant.getConUsername())) {
                return Response.status(Response.Status.BAD_REQUEST).entity("Username is already taken").build();
            }

            if (!isValidConsultant(consultant)) {
                return Response.status(Response.Status.BAD_REQUEST).entity("Invalid registration data").build();
            }

            boolean success = consultantsDB.registerConsultant(consultant);
            if (success) {
                return Response.status(Response.Status.CREATED).entity("Consultant registered successfully").build();
            } else {
                return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Registration failed").build();

            }
        } else if (USER_TYPE_RECEPTIONIST.equals(userType)) {
            Receptionists receptionist = new Receptionists();
            receptionist.setRecName(name);
            receptionist.setRecDob(dateOfBirth);
            receptionist.setRecContact(contact);
            receptionist.setRecEmail(email);
            receptionist.setRecUsername(username);
            receptionist.setRecPassword(password);

            if (receptionistsDB.isUsernameTaken(receptionist.getRecUsername())) {
                return Response.status(Response.Status.BAD_REQUEST).entity("Username is already taken").build();
            }

            if (!isValidReceptionist(receptionist)) {
                return Response.status(Response.Status.BAD_REQUEST).entity("Invalid registration data").build();
            }

            boolean success = receptionistsDB.registerReceptionist(receptionist);
            if (success) {
                return Response.status(Response.Status.CREATED).entity("Receptionist registered successfully").build();
            } else {
                return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Registration failed").build();
            }
        } else if (USER_TYPE_MANAGEMENT.equals(userType)) {

            Management management = new Management();
            management.setStaffName(name);
            management.setStaffDob(dateOfBirth);
            management.setStaffContact(contact);
            management.setStaffEmail(email);
            management.setStaffUsername(username);
            management.setStaffPassword(password);

            if (managementDB.isUsernameTaken(management.getStaffUsername())) {
                return Response.status(Response.Status.BAD_REQUEST).entity("Username is already taken").build();
            }

            if (!isValidManagement(management)) {
                return Response.status(Response.Status.BAD_REQUEST).entity("Invalid registration data").build();
            }

            boolean success = managementDB.registerManagement(management);
            if (success) {
                return Response.status(Response.Status.CREATED).entity("Management user registered successfully").build();
            } else {
                return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Registration failed").build();
            }

        } else {
            return Response.status(Response.Status.BAD_REQUEST).entity("Invalid user type").build();
        }
    }

    private Response createResponse(boolean success, String message) {
        if (success) {
            return Response.status(Response.Status.CREATED).entity(message).build();
        } else {
            return Response.status(Response.Status.BAD_REQUEST).entity(message).build();
        }
    }

    private boolean isValidConsultant(Consultants consultant) {
        return !(consultant == null
                || consultant.getConName() == null || consultant.getConName().isEmpty()
                || consultant.getConDob() == null || consultant.getConDob().isEmpty()
                || consultant.getConCountry() == null || consultant.getConCountry().isEmpty()
                || consultant.getConSpecializedFields() == null || consultant.getConSpecializedFields().isEmpty()
                || consultant.getConContact() == null || consultant.getConContact().isEmpty()
                || consultant.getConEmail() == null || consultant.getConEmail().isEmpty()
                || consultant.getConUsername() == null || consultant.getConUsername().isEmpty()
                || consultant.getConPassword() == null || consultant.getConPassword().isEmpty());
    }

    private boolean isValidJobSeeker(JobSeekers jobSeeker) {
        return !(jobSeeker == null
                || jobSeeker.getJsName() == null || jobSeeker.getJsName().isEmpty()
                || jobSeeker.getJsDob() == null || jobSeeker.getJsDob().isEmpty()
                || jobSeeker.getJsCountry() == null || jobSeeker.getJsCountry().isEmpty()
                || jobSeeker.getJsField() == null || jobSeeker.getJsField().isEmpty()
                || jobSeeker.getJsContact() == null || jobSeeker.getJsContact().isEmpty()
                || jobSeeker.getJsEmail() == null || jobSeeker.getJsEmail().isEmpty()
                || jobSeeker.getJsUsername() == null || jobSeeker.getJsUsername().isEmpty()
                || jobSeeker.getJsPassword() == null || jobSeeker.getJsPassword().isEmpty());
    }

    private boolean isValidReceptionist(Receptionists receptionist) {
        return !(receptionist == null
                || receptionist.getRecName() == null || receptionist.getRecName().isEmpty()
                || receptionist.getRecDob() == null || receptionist.getRecDob().isEmpty()
                || receptionist.getRecContact() == null || receptionist.getRecContact().isEmpty()
                || receptionist.getRecEmail() == null || receptionist.getRecEmail().isEmpty()
                || receptionist.getRecUsername() == null || receptionist.getRecUsername().isEmpty()
                || receptionist.getRecPassword() == null || receptionist.getRecPassword().isEmpty());
    }

    private boolean isValidManagement(Management management) {
        return !(management == null
                || management.getStaffName() == null || management.getStaffName().isEmpty()
                || management.getStaffDob() == null || management.getStaffDob().isEmpty()
                || management.getStaffContact() == null || management.getStaffContact().isEmpty()
                || management.getStaffEmail() == null || management.getStaffEmail().isEmpty()
                || management.getStaffUsername() == null || management.getStaffUsername().isEmpty()
                || management.getStaffPassword() == null || management.getStaffPassword().isEmpty());
    }

    @POST
    @Path("/login")
    public Response loginUser(@FormParam("username") String username, @FormParam("password") String password) {

        JobSeekers jobSeeker = jobSeekersDB.loginJobSeeker(username, password);
        Consultants consultant = consultantsDB.loginConsultant(username, password);
        Receptionists receptionist = receptionistsDB.loginReceptionist(username, password);
        Management management = managementDB.loginManagement(username, password);

        if (jobSeeker != null || consultant != null || receptionist != null || management != null) {
            HttpSession session = request.getSession(true);
            session.setAttribute("loggedInUser", (jobSeeker != null) ? jobSeeker : (consultant != null) ? consultant
                    : (receptionist != null) ? receptionist : management);

            Cookie cookie = new Cookie("loggedInUser", (jobSeeker != null) ? "jobSeeker"
                    : (consultant != null) ? "consultant"
                            : (receptionist != null) ? "receptionist" : "management");
            NewCookie newCookie = new NewCookie(cookie);

            return Response.status(Response.Status.OK)
                    .entity("Login successful")
                    .cookie(newCookie)
                    .build();
        } else {
            return Response.status(Response.Status.UNAUTHORIZED).entity("Login failed").build();
        }
    }

    // ----------------Job Seekers---------------
    
    
    @GET
    @Path("/jobseekers/{id}/consultant-details")
    public Response viewConsultantDetails(@PathParam("id") int jobSeekerId) {
        Consultants consultant = consultantsDB.getConsultantDetails(jobSeekerId);
        if (consultant != null) {
            return Response.status(Response.Status.OK).entity(consultant).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).entity("Consultant details not found").build();
        }
    }

    @POST
    @Path("/jobseekers/{id}/schedule-appointment")
    public Response scheduleAppointment(@PathParam("id") int jobSeekerId, @QueryParam("consultantId") int consultantId, Appointment appointment) {
        boolean success = jobSeekersDB.scheduleAppointment(jobSeekerId, consultantId, appointment.getAppointmentDate());
        if (success) {
            return Response.status(Response.Status.CREATED).entity("Appointment scheduled successfully").build();
        } else {
            return Response.status(Response.Status.BAD_REQUEST).entity("Appointment scheduling failed").build();
        }
    }

    @POST
    @Path("/jobseekers/{id}/cancel-appointment")
    public Response cancelAppointment(@PathParam("id") int jobSeekerId, Appointment appointment) {
        // You need to implement this method
        boolean success = jobSeekersDB.cancelAppointment(jobSeekerId, appointment.getAppointmentId());
        if (success) {
            return Response.status(Response.Status.OK).entity("Appointment canceled successfully").build();
        } else {
            return Response.status(Response.Status.BAD_REQUEST).entity("Appointment cancellation failed").build();
        }
    }

    @GET
    @Path("/jobseekers/{id}/profile")
    public Response getJobSeekerProfile(@PathParam("id") int jobSeekerId) {
        JobSeekers jobSeeker = jobSeekersDB.getJobSeekerProfile(jobSeekerId);

        if (jobSeeker != null) {
            return Response.status(Response.Status.OK).entity(jobSeeker).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).entity("Job Seeker not found").build();
        }
    }

    @DELETE
    @Path("/jobseekers/{id}")
    public Response deleteJobSeeker(@PathParam("id") int jobSeekerId) {
        boolean success = jobSeekersDB.deleteJobSeeker(jobSeekerId);
        if (success) {
            return Response.status(Response.Status.OK).entity("JobSeeker deleted successfully").build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).entity("JobSeeker not found or deletion failed").build();
        }
    }

    @GET
    @Path("/jobseekers/{id}/appointments")
    public Response viewAppointment(@PathParam("id") int jobSeekerId) {
        List<Appointment> appointments = jobSeekersDB.getAppointment(jobSeekerId);
        if (appointments != null) {
            return Response.status(Response.Status.OK).entity(appointments).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).entity("Appointments not found").build();
        }
    }
    
    

    // ----------------Consultant---------------
    
    
    @GET
    @Path("/{id}/profile")
    public Response viewConsultantProfile(@PathParam("id") int consultantId) {
        Consultants consultant = consultantsDB.viewConsultantProfile(consultantId);
        if (consultant != null) {
            return Response.status(Response.Status.OK).entity(consultant).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).entity("Consultant profile not found.").build();
        }
    }

    @GET
    @Path("/{id}/appointments")
    public Response viewRequestedAppointments(@PathParam("id") int consultantId) {
        List<Appointment> appointments = consultantsDB.viewRequestedAppointments(consultantId);
        return Response.status(Response.Status.OK).entity(appointments).build();
    }

    @POST
    @Path("/{id}/appointments/{appointmentId}/confirm")
    public Response confirmAppointment(@PathParam("id") int consultantId, @PathParam("appointmentId") int appointmentId) {
        boolean success = consultantsDB.confirmAppointment(appointmentId, consultantId);
        if (success) {
            return Response.status(Response.Status.OK).entity("Appointment confirmed.").build();
        } else {
            return Response.status(Response.Status.BAD_REQUEST).entity("Failed to confirm appointment.").build();
        }
    }

    @POST
    @Path("/{id}/appointments/{appointmentId}/decline")
    public Response declineAppointment(@PathParam("id") int consultantId, @PathParam("appointmentId") int appointmentId) {
        boolean success = consultantsDB.declineAppointment(appointmentId, consultantId);
        if (success) {
            return Response.status(Response.Status.OK).entity("Appointment declined.").build();
        } else {
            return Response.status(Response.Status.BAD_REQUEST).entity("Failed to decline appointment.").build();
        }
    }

    @POST
    @Path("/{id}/appointments/{appointmentId}/cancel")
    public Response cancelAppointment(@PathParam("appointmentId") int appointmentId) {
        boolean success = consultantsDB.cancelAppointment(appointmentId);
        if (success) {
            return Response.status(Response.Status.OK).entity("Appointment canceled.").build();
        } else {
            return Response.status(Response.Status.BAD_REQUEST).entity("Failed to cancel appointment.").build();
        }
    }
    

    // ----------------Receptionist---------------
    
    
    @GET
    @Path("/{id}/profile")
    public Response viewReceptionistProfile(@PathParam("id") int recId) {
        Receptionists receptionist = receptionistsDB.viewReceptionistProfile(recId);
        if (receptionist != null) {
            return Response.status(Response.Status.OK).entity(receptionist).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).entity("Receptionist profile not found.").build();
        }
    }

    @PUT
    @Path("/{id}")
    public Response updateReceptionistProfile(@PathParam("id") int recId, Receptionists updatedReceptionist) {
        boolean success = receptionistsDB.updateReceptionist(
                recId,
                updatedReceptionist.getRecName(),
                updatedReceptionist.getRecDob(),
                updatedReceptionist.getRecContact(),
                updatedReceptionist.getRecEmail(),
                updatedReceptionist.getRecUsername(),
                updatedReceptionist.getRecPassword()
        );

        if (success) {
            return Response.status(Response.Status.OK).entity("Receptionist profile updated successfully").build();
        } else {
            return Response.status(Response.Status.BAD_REQUEST).entity("Profile update failed").build();
        }
    }

    @DELETE
    @Path("/{id}")
    public Response deleteReceptionist(@PathParam("id") int recId) {
        boolean success = receptionistsDB.deleteReceptionist(recId);
        if (success) {
            return Response.status(Response.Status.OK).entity("Receptionist deleted successfully").build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).entity("Receptionist not found").build();
        }
    }

    @GET
    @Path("/jobseekers")
    public List<JobSeekers> viewJobSeekersDetails() {
        return receptionistsDB.viewJobSeekersDetails();
    }

    @GET
    @Path("/consultants")
    public List<Consultants> viewConsultantsDetails() {
        return receptionistsDB.viewConsultantsDetails();
    }

    @GET
    @Path("/appointments")
    public List<Appointment> viewAllAppointments() {
        return receptionistsDB.viewAllAppointments();
    }

    
    
    // ----------------Management---------------
    
    
    @GET
    @Path("/{id}")
    public Response viewManagementProfile(@PathParam("id") int staffId) {
        Management management = managementDB.viewManagementProfile(staffId);
        if (management != null) {
            return Response.status(Response.Status.OK).entity(management).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).entity("Management profile not found").build();
        }
    }

    @PUT
    @Path("/{id}")
    public Response updateManagementProfile(@PathParam("id") int staffId, Management updatedManagement) {
        boolean success = managementDB.updateManagementProfile(staffId, updatedManagement);
        if (success) {
            return Response.status(Response.Status.OK).entity("Management profile updated successfully").build();
        } else {
            return Response.status(Response.Status.BAD_REQUEST).entity("Profile update failed").build();
        }
    }

    @DELETE
    @Path("/{id}")
    public Response deleteManagementProfile(@PathParam("id") int staffId) {
        boolean success = managementDB.deleteManagementProfile(staffId);
        if (success) {
            return Response.status(Response.Status.OK).entity("Management profile deleted successfully").build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).entity("Management profile not found or deletion failed").build();
        }
    }

    @GET
    @Path("/managers")
    public Response viewAllManagementProfiles() {
        List<Management> managementList = managementDB.viewAllManagementProfiles();
        if (!managementList.isEmpty()) {
            return Response.status(Response.Status.OK).entity(managementList).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).entity("No management profiles found").build();
        }
    }

    @GET
    @Path("/appointments")
    public Response viewAllAppointmentsM() {
        List<Appointment> appointments = managementDB.viewAllAppointmentsM();
        if (!appointments.isEmpty()) {
            return Response.status(Response.Status.OK).entity(appointments).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).entity("No appointments found").build();
        }
    }

    @GET
    @Path("/jobseekers")
    public Response viewAllJobSeekers() {
        List<JobSeekers> jobSeekers = managementDB.viewAllJobSeekers();
        if (!jobSeekers.isEmpty()) {
            return Response.status(Response.Status.OK).entity(jobSeekers).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).entity("No job seekers found").build();
        }
    }

    @GET
    @Path("/consultants")
    public Response viewAllConsultants() {
        List<Consultants> consultants = managementDB.viewAllConsultants();
        if (!consultants.isEmpty()) {
            return Response.status(Response.Status.OK).entity(consultants).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).entity("No consultants found").build();
        }
    }

    @GET
    @Path("/receptionists")
    public Response viewAllReceptionists() {
        List<Receptionists> receptionists = managementDB.viewAllReceptionists();
        if (!receptionists.isEmpty()) {
            return Response.status(Response.Status.OK).entity(receptionists).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).entity("No receptionists found").build();
        }
    }

    @DELETE
    @Path("/consultants/{id}")
    public Response deleteConsultant(@PathParam("id") int consultantId) {
        boolean success = managementDB.deleteConsultant(consultantId);
        if (success) {
            return Response.status(Response.Status.OK).entity("Consultant deleted successfully").build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).entity("Consultant not found or deletion failed").build();
        }
    }

    @DELETE
    @Path("/receptionists/{id}")
    public Response deleteReceptionistM(@PathParam("id") int receptionistId) {
        boolean success = managementDB.deleteReceptionistM(receptionistId);
        if (success) {
            return Response.status(Response.Status.OK).entity("Receptionist deleted successfully").build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).entity("Receptionist not found or deletion failed").build();
        }
    }

    @PUT
    @Path("/consultants/{id}")
    public Response updateConsultant(@PathParam("id") int consultantId, Consultants updatedConsultant) {
        boolean success = managementDB.updateConsultant(consultantId, updatedConsultant.getConName(), updatedConsultant.getConDob(),
                updatedConsultant.getConContact(), updatedConsultant.getConEmail(), updatedConsultant.getConSpecializedFields());
        if (success) {
            return Response.status(Response.Status.OK).entity("Consultant profile updated successfully").build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).entity("Consultant not found or profile update failed").build();
        }
    }

    @PUT
    @Path("/receptionists/{id}")
    public Response updateReceptionist(@PathParam("id") int recId, Receptionists updatedReceptionist) {
        boolean success = managementDB.updateReceptionist(recId, updatedReceptionist.getRecName(), updatedReceptionist.getRecDob(), updatedReceptionist.getRecContact(),
                updatedReceptionist.getRecEmail(), updatedReceptionist.getRecUsername(), updatedReceptionist.getRecPassword());
        if (success) {
            return Response.status(Response.Status.OK).entity("Receptionist profile updated successfully").build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).entity("Receptionist not found or profile update failed").build();
        }
    }

    @POST
    @Path("/logout")
    public Response logout() {
        HttpSession session = request.getSession(false); 

        if (session != null) {
            session.invalidate(); 
            return Response.status(Response.Status.OK).entity("Logged out successfully").build();
        } else {
            return Response.status(Response.Status.UNAUTHORIZED).entity("Not logged in").build();
        }
    }
}
