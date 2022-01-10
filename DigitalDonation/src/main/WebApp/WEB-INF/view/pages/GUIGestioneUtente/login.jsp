<%@ page import="it.unisa.is.c09.digitaldonation.model.entity.Utente" %>
<%@ page import="it.unisa.is.c09.digitaldonation.model.entity.Donatore" %>
<%@ page import="it.unisa.is.c09.digitaldonation.model.entity.Operatore" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form" %>


<!--Gestione della sessione-->

<%
    Utente utente=(Utente)session.getAttribute("utente");
    if(utente instanceof Donatore) response.sendRedirect("GUIGestioneUtente/dashboardDonatore");
    if(utente instanceof Operatore) response.sendRedirect("GUIGestioneUtente/dashboardOperatore");
%>

<!--Fine gestione della sessione-->

<!DOCTYPE html>
<html lang="en">

<head>

    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <meta name="description" content="">
    <meta name="author" content="">

    <title>Login</title>

    <!-- Custom fonts for this template-->
    <link href="vendor/fontawesome-free/css/all.min.css" rel="stylesheet" type="text/css">
    <link href="https://fonts.googleapis.com/css?family=Nunito:200,200i,300,300i,400,400i,600,600i,700,700i,800,800i,900,900i"
          rel="stylesheet">

    <!-- Custom styles for this template-->
    <link href="../../resources/css/sb-admin-2.min.css" rel="stylesheet">
</head>

<body class="bg-gradient-primary">

<div class="container">

    <!-- Outer Row -->
    <div class="row justify-content-center">

        <div class="col-xl-10 col-lg-12 col-md-9">

            <div class="card o-hidden border-0 shadow-lg my-5">
                <div class="card-body p-0">
                    <!-- Nested Row within Card Body -->
                    <div class="row">
                        <img class="col-lg-6 d-none d-lg-block" src=../../../../resources/img/imglogin.png alt="...">

                        <div class="col-lg-6">
                            <div class="p-5">
                                <br>
                                <div class="text-center">
                                    <h1 class="h4 text-gray-900 mb-4">Benvenuto in Digital Donation!</h1>
                                </div>

                                <%--@elvariable id="loginForm" type="it.unisa.is.c09.digitaldonation.utils.form.LoginForm"--%>
                                <form:form action="./login" method="post" modelAttribute="loginForm" cssClass="user"
                                           enctype="application/x-www-form-urlencoded">
                                <div class="form-group">
                                    <h6 class="h6 text-gray-900 mb-4">Inserisci Email:</h6>
                                    <c:choose>
                                        <c:when test="${EmailError == null}">
                                            <c:choose>
                                                <c:when test="${EmailPrecedente == null}">
                                                    <form:input type="text" class="form-control form-control-user"
                                                                id="email" placeholder="Inserisci l'indirizzo email"
                                                                path="email"/>
                                                </c:when>
                                                <c:otherwise>
                                                    <form:input type="text" value="${EmailPrecedente}"
                                                                class="form-control form-control-user is-invalid"
                                                                id="email" placeholder="Inserisci l'indirizzo email"
                                                                path="email"/>
                                                </c:otherwise>
                                            </c:choose>
                                        </c:when>
                                        <c:otherwise>
                                            <form:input type="text" class="form-control form-control-user is-invalid"
                                                        id="email" placeholder="Inserisci l'indirizzo email"
                                                        path="email"/>
                                            <span class="myError">${EmailError}</span>
                                        </c:otherwise>
                                    </c:choose>
                                </div>

                                <div class="form-group">
                                    <h6 class="h6 text-gray-900 mb-4">Inserisci Password:</h6>
                                    <c:choose>
                                        <c:when test="${PasswordError == null}">
                                            <form:input type="password" name="password" id="inputPassword"
                                                   placeholder="Password" class="form-control form-control-user" path="password"/>
                                        </c:when>
                                        <c:otherwise>
                                            <form:input type="password" name="password" id="inputPassword"
                                                   placeholder="Password" class="form-control form-control-user is-invalid" path="password"/>
                                            <span class="myError">${PasswordError}</span>
                                        </c:otherwise>
                                    </c:choose>
                                </div>


                            <input type="submit" value="Login" class="btn btn-primary btn-user btn-block">

                            </div>
                            <br>

                            </form:form>

                        </div>
                    </div>
                </div>
            </div>
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

</body>

</html>
