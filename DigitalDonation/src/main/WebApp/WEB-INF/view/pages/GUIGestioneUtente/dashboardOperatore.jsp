<%--
  Created by IntelliJ IDEA.
  User: angel
  Date: 29/12/2021
  Time: 18:55
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="z" tagdir="/WEB-INF/tags"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@page isErrorPage="true" %>

<!-- Custom fonts for this template-->
<link href="vendor/fontawesome-free/css/all.min.css" rel="stylesheet" type="text/css">
<link
        href="https://fonts.googleapis.com/css?family=Nunito:200,200i,300,300i,400,400i,600,600i,700,700i,800,800i,900,900i"
        rel="stylesheet">

<!-- Custom styles for this template-->
<link href="../../resources/css/sb-admin-2.min.css" rel="stylesheet">

<z:layout pageTitle="dashboardOperatore">
    <!-- Page Wrapper -->
    <div id="wrapper">

        <!-- Content Wrapper -->
        <div id="content-wrapper" class="d-flex flex-column">

            <!-- Main Content -->
            <div id="content">

                <!-- Topbar -->

                <nav class="navbar navbar-expand-lg navbar-light bg-white">

                    <!-- Logo -->
                    <a class="navbar-brand" href="#"><img class="rounded-circle" src=../../../../resources/img/logo.png alt="..." style="width:50px"></a>

                    <!-- Topbar scritta Digital Donation -->
                    <form
                            class="d-none d-sm-inline-block form-inline mr-auto ml-md-3 my-2 my-md-0 mw-100 navbar-search">
                        <div class="input-group">

                            <h1 class="h3 mb-0 text-gray-800 ">DIGITAL DONATION</h1>

                        </div>
                    </form>


                    <!-- Nav Item - Utente -->
                    <li class="nav-item dropdown no-arrow">
                        <a class="nav-link dropdown-toggle" href="#" id="userDropdown" role="button"
                           data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                            <span class="mr-2 d-none d-lg-inline text-gray-600 small">Douglas McGee</span>
                            <img class="img-profile rounded-circle"
                                 src="../../../../resources/img/undraw_profile.svg" style="width:50px">
                        </a>
                        <!-- Dropdown - Utente -->
                        <div class="dropdown-menu dropdown-menu-right shadow animated--grow-in"
                             aria-labelledby="userDropdown">

                            <!-- Topbar Logout -->
                            <div class="dropdown-divider"></div>
                            <a class="dropdown-item" href="#" data-toggle="modal" data-target="#logoutModal">
                                <i class="fas fa-sign-out-alt fa-sm fa-fw mr-2 text-gray-400"></i>
                                Logout
                            </a>
                        </div>
                    </li>

                </nav>
                <!-- End of Topbar -->

                <!-- Begin Page Content -->
                <div class="container-fluid">

                    <!-- Page Heading -->
                    <div class="d-sm-flex align-items-center justify-content-between mb-4">
                        <div class="card-body">
                            <h1 class="h3 mb-2 text-gray-800" style="text-align: center">Dashboard Operatore</h1>
                        </div>
                    </div>
                    <!-- Sezione Crea seduta -->


                    <div class="card shadow mb-4">
                        <div class="card-header py-3">
                            <h6 class="m-0 font-weight-bold text-primary">Crea seduta</h6>
                        </div>
                        <div class="card-body">
                            <h4 class="small font-weight-bold">Crea una nuova Seduta:

                                <!-- Bottom crea seduta -->
                                <span  class="float-right">
                                        <a class="btn btn-primary" href="schedulazioneSeduta.html" role="button">Crea seduta</a>
                                     </span>
                            </h4>

                        </div>
                    </div>
                    <!-- Sezione Elenco sedute -->

                    <div class="card shadow mb-4">
                        <div class="card-header py-3">
                            <h6 class="m-0 font-weight-bold text-primary">Elenco sedute</h6>
                        </div>
                        <div class="card-body">
                            <h4 class="small font-weight-bold">Monitore una seduta:

                                <!-- Bottom Elenco sedute -->
                                <span  class="float-right">
                                           <a class="btn btn-primary" href="monitoraggioSedute.html" role="button">Elenco sedute</a>
                                     </span>
                            </h4>

                        </div>
                    </div>

                    <!-- Sezione Creazione tesserino -->

                    <div class="card shadow mb-4">
                        <div class="card-header py-3">
                            <h6 class="m-0 font-weight-bold text-primary">Creazione tesserino</h6>
                        </div>
                        <div class="card-body">
                            <h4 class="small font-weight-bold">Crea un nuovo tesserino:

                                <!-- Bottom Crea Tesserino -->
                                <span  class="float-right">
                                        <a class="btn btn-primary" href=".html" role="button">Crea tesserino</a>
                                     </span>
                            </h4>

                        </div>
                    </div>



                    <!-- Per dividere a blocchi-->

                    <div class="row">


                    </div>

                </div>
                <!-- /.container-fluid -->

            </div>
            <!-- End of Main Content -->

            <!-- Footer -->
            <footer class="sticky-footer bg-white">
                <div class="container my-auto">
                    <div class="copyright text-center my-auto">
                        <span>Digital Donation 2021</span>
                    </div>
                </div>
            </footer>
            <!-- End of Footer -->

        </div>
        <!-- End of Content Wrapper -->

    </div>
    <!-- End of Page Wrapper -->

    <!-- Scroll to Top Button-->
    <a class="scroll-to-top rounded" href="#page-top">
        <i class="fas fa-angle-up"></i>
    </a>

    <!-- Logout Modal-->
    <div class="modal fade" id="logoutModal" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel"
         aria-hidden="true">
        <div class="modal-dialog" role="document">
            <div class="modal-content">
                <div class="modal-header">
                    <h5 class="modal-title" id="exampleModalLabel">Ready to Leave?</h5>
                    <button class="close" type="button" data-dismiss="modal" aria-label="Close">
                        <span aria-hidden="true">×</span>
                    </button>
                </div>
                <div class="modal-body">Select "Logout" below if you are ready to end your current session.</div>
                <div class="modal-footer">
                    <button class="btn btn-secondary" type="button" data-dismiss="modal">Cancel</button>
                    <a class="btn btn-primary" href="login.html">Logout</a>
                </div>
            </div>
        </div>
    </div>


    <!-- Bootstrap core JavaScript-->
    <script src="vendor/jquery/jquery.min.js"></script>
    <script src="vendor/bootstrap/js/bootstrap.bundle.min.js"></script>

    <!-- Core plugin JavaScript-->
    <script src="vendor/jquery-easing/jquery.easing.min.js"></script>

    <!-- Custom scripts for all pages-->
    <script src="js/sb-admin-2.min.js"></script>

    <!-- Page level plugins -->
    <script src="vendor/chart.js/Chart.min.js"></script>

    <!-- Page level custom scripts -->
    <script src="js/demo/chart-area-demo.js"></script>
    <script src="js/demo/chart-pie-demo.js"></script>

</z:layout>