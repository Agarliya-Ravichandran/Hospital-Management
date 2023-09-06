<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width,initial-scale=1,maximum-scale=1">
        <title>Modern Admin Dashboard</title>
        <link rel="stylesheet" href="css/styledash.css">
        <link rel="stylesheet" href="https://maxst.icons8.com/vue-static/landings/line-awesome/line-awesome/1.3.0/css/line-awesome.min.css">
    </head>
    <body>
        <input type="checkbox" id="menu-toggle">
        <div class="sidebar">
            <div class="side-header">
                <h3>the<span>Jobs</span></h3>
            </div>

            <div class="side-content">
                <div class="profile">
                    <h4>Welcome!</h4>
                    <small></small>
                </div>

                <div class="side-menu">
                    <ul>
                        <li>
                            <a href="/mywebapp/jsp/consultanthome.jsp" class="active">
                                <span class="las la-home"></span>
                                <small>Dashboard</small>
                            </a>
                        </li>
                        <li>
                            <a href="">
                                <span class="las la-user-alt"></span>
                                <small>My profile</small>
                            </a>
                        </li>
                        <li>
                            <a href="">
                                <span class="las la-user-alt"></span>
                                <small>Appointments</small>
                            </a>
                        </li>

                    </ul>
                </div>
            </div>
        </div>

        <div class="main-content">

            <header>
                <div class="header-content">
                    <label for="menu-toggle">
                        <span class="las la-bars"></span>
                    </label>

                    <div class="header-menu">
                        <label for="">
                            <span class="las la-search"></span>
                        </label>
                        <div class="user">
                            <div class="bg-img" style="background-image: url(images/the-jobs1.png)"></div>

                            <span class="las la-power-off"></span>
                            <span>Logout</span>
                        </div>
                    </div>
                </div>
            </header>


            <main>

                <div class="page-header">
                    <h1>Dashboard</h1>
                    <small>Home / Dashboard</small>
                </div>

                <div class="records table-responsive">

                    <div class="record-header">
                        <div class="add">
                            <select name="" id="">
                                <option value="0">Details:</option>
                                <option value="">Upcoming Appointments</option>

                            </select>
                            <button>View</button> &nbsp;
                            <button>Update</button> &nbsp;
                            <button>Delete</button>

                        </div>


                        <div class="browse">
                            <input type="search" placeholder="Search" class="record-search">
                        </div>
                    </div>

                    <div>
                        <table width="100%">
                            <thead>
                                <tr>
                                    <th>Id</th>
                                    <th><span class=""></span> Users</th>
                                    
                                </tr>
                            </thead>
                            <tbody>
                            </tbody>
                        </table>
                    </div>

                </div>

        </div>

    </main>

</div>
</body>
</html>