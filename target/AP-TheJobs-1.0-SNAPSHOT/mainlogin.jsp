<%-- 
    Document   : main
    Created on : Aug 15, 2023, 2:04:41 PM
    Author     : Liya
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">

        <title>Sign Up</title>
        <!-- Font Icon -->
        <link rel="stylesheet"
              href="fonts/material-icon/css/material-design-iconic-font.min.css">

        <!-- Main css -->
        <link rel="stylesheet" href="css/style.css">

    </head>
    <body>
        <div class="main">

            <!-- Sing in  Form -->
            <section class="sign-in">
                <div class="container">
                    <div class="signin-content">
                        <div class="signin-image">
                            <figure>
                                <img src="images/signin-image.jpg" alt="sing up image">
                            </figure>
                            <a href="registration.jsp" class="signup-image-link">Create an
                                account</a>
                        </div>

                        <div class="signin-form">
                            <h2 class="form-title">Sign In</h2>
                            <form method="POST" action="/api/users/login" class="register-form" id="login-form">

                                <div class="custom-select" class="mb-1">
                                    <select>
                                        <option value="0">Select User:</option>
                                        <option value="1">Job Seeker</option>
                                        <optgroup label="Staff">
                                            <option value="2">Consultant</option>
                                            <option value="3">Receptionist</option>
                                            <option value="4">Management</option>
                                    </select>
                                </div>
                                <br>
                                <div class="form-group">
                                    <label for="username"><i
                                            class="zmdi zmdi-account material-icons-name"></i></label> <input
                                        type="text" name="username" id="username"
                                        placeholder="Username" />
                                </div>
                                <div class="form-group">
                                    <label for="password"><i class="zmdi zmdi-lock"></i></label> <input
                                        type="password" name="password" id="password"
                                        placeholder="Password" />
                                </div>

                                <div class="form-group">
                                    <input type="checkbox" name="remember-me" id="remember-me"
                                           class="agree-term" /> <label for="remember-me"
                                           class="label-agree-term"><span><span></span></span>Remember
                                        me</label>
                                </div>
                                <div class="form-group form-button">
                                    <input type="submit" name="signin" id="signin"
                                           class="form-submit" value="Login" />
                                </div>
                            </form>
                        </div>
                    </div>
                </div>
            </section>

        </div>


        <!-- JS -->
        <script src="vendor/jquery/jquery.min.js"></script>
        <script src="js/main.js"></script>
    </body>

</html>
