<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>User Registration</title>
        <link rel="stylesheet" href="styles.css">
        <style>
            body {
                font-family: Arial, sans-serif;
                background-image: url('');
                background-size: cover;
                background-position: center;
                margin: 0;
                padding: 0;
                display: flex;
                justify-content: center;
                align-items: center;
                height: 100vh;
            }

            .registration-form {
                background-color: rgba(255, 255, 255, 0.9);
                padding: 20px;
                border-radius: 10px;
                box-shadow: 0px 0px 10px 0px #000;
                text-align: center;
                max-width: 400px;
                width: 100%;
            }

            input[type="text"], select {
                width: 100%;
                padding: 10px;
                margin: 10px 0;
                border: 1px solid #ccc;
                border-radius: 5px;
                font-size: 16px;
            }

            input[type="submit"] {
                background-color: #007bff;
                color: white;
                border: none;
                border-radius: 5px;
                padding: 10px 20px;
                font-size: 18px;
                cursor: pointer;
            }

            input[type="submit"]:hover {
                background-color: #0056b3;
            }

            /* Adjust field spacing and size */
            .registration-form input[type="text"], .registration-form select {
                margin: 5px 0;
                padding: 8px;
            }
        </style>
    </head>
    <body>
        <div class="registration-form">
            <h2>Registration</h2>
            <form id="registrationForm" action="/api/users/register" method="post">
                <label for="userType">Select User Type:</label>
                <select name="userType" id="userType">
                    <option value="jobSeeker">Job Seeker</option>
                    <option value="consultant">Consultant</option>
                    <option value="receptionist">Receptionist</option>
                    <option value="management">Management Staff</option>
                </select>
                <div id="commonFields">
                    <input type="text" name="name" placeholder="Name">
                    <input type="text" name="dateOfBirth" placeholder="Date of Birth (YYYY-MM-DD)">
                    <input type="text" name="contact" placeholder="Contact">
                    <input type="text" name="email" placeholder="Email">

                </div>

                <div id="receptionistFields" style="display: none;">
                    <input type="text" name="username" placeholder="Username">
                    <input type="text" name="password" placeholder="Password">      
                </div>

                <div id="managementstaffFields" style="display: none;">
                    <input type="text" name="username" placeholder="Username">
                    <input type="text" name="password" placeholder="Password">      
                </div>


                <div id="jobSeekerFields" style="display: none;">
                    <input type="text" name="country" placeholder="Country">
                    <select name="jobField">
                        <option value="" disabled selected>Select Job Field</option>
                        <option value="IT">IT</option>
                        <option value="Engineering">Engineering</option>
                        <option value="Healthcare">Healthcare</option>
                        <option value="Healthcare">Architecture</option>
                        <option value="Healthcare">Medicine</option>
                        <option value="Healthcare">Business(Management & Administration</option>
                        <option value="Healthcare">Law</option>
                        <option value="Healthcare">Arts</option>
                        <option value="Healthcare">Sales</option>
                    </select>
                    <input type="text" name="username" placeholder="Username">
                    <input type="text" name="password" placeholder="Password">
                </div>


                <div id="consultantFields" style="display: none;">
                    <input type="text" name="country" placeholder="Country">
                    <select name="jobField">
                        <option value="" disabled selected>Select specialized field</option>
                        <option value="IT">IT</option>
                        <option value="Engineering">Engineering</option>
                        <option value="Healthcare">Healthcare</option>
                        <option value="Healthcare">Architecture</option>
                        <option value="Healthcare">Medicine</option>
                        <option value="Healthcare">Business(Management & Administration</option>
                        <option value="Healthcare">Law</option>
                        <option value="Healthcare">Arts</option>
                        <option value="Healthcare">Sales</option>
                    </select>
                    <input type="text" name="yearsOfExperience" placeholder="Years of Experience">
                    <input type="text" name="availableTime" placeholder="Available Time">
                    <input type="text" name="username" placeholder="Username">
                    <input type="text" name="password" placeholder="Password">
                </div>
                <input type="submit" value="Register">

            </form>
        </div>




        <script>
            function toggleFields() {
                const userTypeSelect = document.getElementById("userType");
                const jobSeekerFields = document.getElementById("jobSeekerFields");
                const consultantFields = document.getElementById("consultantFields");
                const receptionistFields = document.getElementById("receptionistFields");
                const managementFields = document.getElementById("managementstaffFields"); // Corrected ID

                if (userTypeSelect.value === "jobSeeker") {
                    jobSeekerFields.style.display = "block";
                    consultantFields.style.display = "none";
                    receptionistFields.style.display = "none";
                    managementFields.style.display = "none";
                } else if (userTypeSelect.value === "consultant") {
                    jobSeekerFields.style.display = "none";
                    consultantFields.style.display = "block";
                    receptionistFields.style.display = "none";
                    managementFields.style.display = "none";
                } else if (userTypeSelect.value === "receptionist") {
                    jobSeekerFields.style.display = "none";
                    consultantFields.style.display = "none";
                    receptionistFields.style.display = "block";
                    managementFields.style.display = "none";
                } else if (userTypeSelect.value === "management") {
                    jobSeekerFields.style.display = "none";
                    consultantFields.style.display = "none";
                    receptionistFields.style.display = "none";
                    managementFields.style.display = "block";
                } else {
                    jobSeekerFields.style.display = "none";
                    consultantFields.style.display = "none";
                    receptionistFields.style.display = "none";
                    managementFields.style.display = "none";
                }
            }

            const userTypeSelect = document.getElementById("userType");
            userTypeSelect.addEventListener("change", toggleFields);

            toggleFields();

            document.getElementById("registrationForm").addEventListener("submit", function (event) {
                event.preventDefault();

                // Simulate a successful registration for demo purposes
                const registrationSuccessful = true;

                if (registrationSuccessful) {
                    const userType = document.getElementById("userType").value;

                    if (userType === "jobSeeker") {
                        window.location.href = "/mywebapp/jsp/jobseekerhome.jsp";
                    } else if (userType === "consultant") {
                        window.location.href = "/mywebapp/jsp/consultanthome.jsp";
                    } else if (userType === "receptionist") {
                        window.location.href = "/mywebapp/jsp/receptionisthome.jsp";
                    } else if (userType === "management") {
                        window.location.href = "/mywebapp/jsp/managementhome.jsp";
                    }

                }
            });


        </script>


    </body>
</html>