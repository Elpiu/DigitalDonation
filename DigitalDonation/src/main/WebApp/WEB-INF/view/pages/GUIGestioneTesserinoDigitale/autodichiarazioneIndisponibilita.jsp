<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@taglib prefix="z" tagdir="/WEB-INF/tags" %>

<z:layout pageTitle="autodichiarazioneIndisponibilita">
<!-- Page Wrapper -->
 <div id="wrapper">

    <!-- Content Wrapper -->
    <div id="content-wrapper" class="d-flex flex-column">

        <!-- Main Content -->
        <div id="content">

            <!-- Begin Page Content -->
            <div class="container-fluid">

                <!-- Page Heading -->
                <div class="d-sm-flex align-items-center justify-content-between mb-4">
                    <div class="card-body">
                        <!-- indietro -->
                        <a href="./dashboardDonatore" role="button"> <i class="fas fa-arrow-left float-left icone"></i></a>

                        <!-- titolo -->
                        <h1 class="h3 mb-2 text-gray-800" style="text-align: center">Autodichiarazione d'indisponibilità</h1>

                    </div>
                </div>
                <div class="container">

                    <div class="card o-hidden border-0 shadow-lg my-5">
                        <div class="card-body p-0">
                            <!-- Nested Row within Card Body -->
                            <div class="row justify-content-md-center">
                                <div class="col-lg-7">
                                    <div class="p-5">

                                        <div class="text-center">
                                            <h3 class="h4 text-gray-900 mb-4">Compila il form per l'autodichiarazione d'indisponibilità</h3>
                                        </div>
                                            <%--@elvariable id="autodichiarazioneForm" type="it.unisa.is.c09.digitaldonation.utils.form.AutodichiarazioneIndisponibilitaForm"--%>
                                        <form:form action="./autodichiarazioneIndisponibilita" method="post" modelAttribute="autodichiarazioneForm" cssClass="user" enctype="application/x-www-form-urlencoded">
                                        <div class="form-group">
                                                <p style="color:#4e73df;">Data prossima disponibilità: </p>
                                                <c:choose>
                                                    <c:when test="${DataProssimaDisponibilitaError == null}">
                                                        <form:input type="date" class="form-control form-control-user" id="dataDisponibilita" placeholder="Data prossima disponibilità:" path="dataProssimaDisponibilita"/>
                                                    </c:when>
                                                    <c:otherwise>
                                                        <form:input type="date" class="form-control form-control-user is-invalid" id="dataDisponibilita" placeholder="Data prossima disponibilità:" path="dataProssimaDisponibilita"/>
                                                        <span class="myError">${DataProssimaDisponibilitaError}</span>
                                                    </c:otherwise>
                                                </c:choose>

                                            </div>
                                            <div class="form-group">
                                                <p style="color:#4e73df;">Motivazione di indisponibilità: </p>
                                                <c:choose>
                                                    <c:when test="${MotivazioniIndisponibilitaError == null}">
                                                        <form:textarea class="textarea form-control" id="exampleMotivazione" placeholder="es. Abuso di sostanze stupefacenti" rows="4" path="motivazioni"/>
                                                    </c:when>
                                                    <c:otherwise>
                                                        <form:textarea class="textarea form-control" id="exampleMotivazione" placeholder="es. Abuso di sostanze stupefacenti" rows="4" path="motivazioni"/>
                                                        <span class="myError">${MotivazioniIndisponibilitaError}</span>
                                                    </c:otherwise>
                                                </c:choose>

                                            </div>
                                            <br>
                                            <input type="submit" value="Conferma" class="btn btn-primary btn-user btn-block">
                                            </form:form>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
                <!-- /.container-fluid -->
            </div>
        </div>
        <!-- End of Main Content -->

        <!-- ======= Footer ======= -->
        <footer id="footer" class="sticky-footer bg-white">
            <div align="center" class="container my-auto">
                <h3 style="color:#6C757D";>Digital Donation</h3>

                <p style="color:#6C757D;"> Grazie per aver visitato il nostro sito!<br>
                    Qui trovi una piccola descrizione del nostro team, visita la pagina
                    <a href="./goAboutUs"> About us </a>
                </p>


                <div class="copyright" style="color:#6C757D;">
                    <h6 >&copy; Copyright 2021</h6><br>
                    Il trattamento dei dati personali è svolto nel rispetto dei diritti sulla privacy in base alla legge n. 675 del 31 dicembre 1996
                    <br> e dei diritti per condivisione dei dati personali come stabilito dalla legge n 675/96 del 2003.
                    <br>


                </div>
                <div class="credits">
                </div>
            </div>

        </footer>
        <!-- End Footer -->

    </div>
    <!-- End of Content Wrapper -->

 </div>
 <!-- End of Page Wrapper -->
</z:layout>