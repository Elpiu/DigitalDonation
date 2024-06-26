<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="z" tagdir="/WEB-INF/tags" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>


<z:layout pageTitle="dashboardDonatore">

    <!-- Page Wrapper -->
    <div id="wrapper">

        <!-- Content Wrapper -->
        <div id="content-wrapper" class="d-flex flex-column">

            <!-- Main Content -->
            <div id="content">


                <!-- Begin Page Content -->
                <div class="container-fluid">

                    <!-- Page Heading -->
                    <h1 class=" mb-4" style="text-align: center">Dashboard Donatore</h1>

                    <c:choose>
                        <c:when test="${success != null}">
                            <div class="alert alert-success" role="alert">
                                <h4 class="alert-heading"><i class="fas fa-calendar-check"
                                                             style="font-size: 36px"> </i> &nbsp <b>Complimenti!</b>  ${success}
                                </h4>
                            </div>
                        </c:when>
                    </c:choose>

                    <!-- Content Row -->
                    <div class="row ">

                        <!-- Profilo -->
                        <div class="col-xl-6 col-lg-12">
                            <div class="card shadow mb-4">
                                <!-- Card Header -->
                                <div
                                        class="card-header py-3 d-flex flex-row align-items-center justify-content-between">
                                    <h6 class="m-0 font-weight-bold text-primary">Profilo</h6>
                                </div>
                                <!-- Card Body -->
                                <div class="card-body">
                                    <div class="text-center">
                                        <img class="img-fluid px-3 px-sm-4 mt-3 mb-4" style="width: 24rem;"
                                             src="${tesserino.imgSource}" alt="Errore caricamento immagine">
                                        <h1 align="center" class="h3 mb-2 text-gray-800"> <c:out value="${utente.nome} ${utente.cognome}"></c:out></h1>
                                        <a class="btn btn-primary" href="./visualizzaTesserino" role="button">Vai al tuo tesserino</a>
                                    </div>
                                </div>
                                <div class="mt-4 mb-4 text-center small">

                                </div>
                            </div>
                        </div>


                        <!-- Attività -->
                        <div class="col-xl-6 col-lg-12">
                            <div class="card shadow mb-4">
                                <!-- Card Header -->
                                <div
                                        class="card-header py-3 d-flex flex-row align-items-center justify-content-between">
                                    <h6 class="m-0 font-weight-bold text-primary">Attività</h6>
                                </div>
                                <!-- Card Body -->
                                <div class="card-body">
                                    <div class="text-center">
                                        <img class="img-fluid px-5 " style="width: 24rem;"
                                             src="../../resources/img/list.png" alt="...">
                                        <h1 align="center" class="h3 mb-2 text-gray-800">Visualizza le sedute
                                            disponibili per te:</h1>
                                        <a class="btn btn-primary" href="./goSeduteDisponibili"
                                           role="button">Sedute</a>
                                        <h1><img class="img-fluid px-5 " style="width: 24rem;"
                                             src="../../resources/img/autodichiarazione.png" alt="..."></h1>
                                        <h1 align="center" class="h3 mb-2 text-gray-800">Compila la tua
                                            autodichiarazione di indisponibilità:</h1>
                                        <a class="btn btn-primary" href="./goAutodichiarazioneIndisponibilita" role="button">Compila</a>
                                        <h1>&nbsp;</h1>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>


                    <div class="row">

                        <!-- Requisiti per donare -->
                        <div class="col-xl-12 col-lg-12">
                            <div class="card shadow mb-4">
                                <div class="card-header py-3">
                                    <h6 class="m-0 font-weight-bold text-primary">Requisiti per donare</h6>
                                </div>
                                <div class="card-body">
                                    <div class="text-center" style="background: #dcebf4">
                                        <img class="img-fluid px-sm-4 mt-3 mb-4"
                                             src="../../resources/img/requisiti.svg" alt="..." width="1000" height="600">
                                    </div>
                                    <br>
                                    <h4 style="text-align: center"><b>Chi non può donare il sangue? </b></h4>
                                    <h5><p> Criteri di esclusione temporanea del donatore</p></h5>
                                    <p>

                                    <ul>
                                        <li>Parto – sospensione per 6 mesi</li>
                                        <li>Ciclo mestruale in atto – da valutare</li>
                                        <li>Allergie in atto</li>
                                        <li>Ulcera gastrica e/o duodenale in atto</li>
                                        <li> Alcune malattie infettive (da valutare alla guarigione)</li>
                                        <li> Agopuntura – 4 mesi (Se eseguiti con strumenti monouso)</li>
                                        <li> Foratura orecchie*/Piercing – 4 mesi</li>
                                        <li>Trasfusione di emocomponenti o emoderivati (immunoglobuline) – 4 mesi</li>
                                        <li> Assunzione di alcune tipologie di farmaci</li>
                                        <li> Rapporti sessuali a rischio – 4 mesi</li>
                                        <li>  Viaggi (da valutare)</li>

                                    </ul>
                                    </p>

                                    <h5><p> Criteri di esclusione permanente del donatore</p></h5>
                                    <p>

                                    <ul>
                                        <li>Alcolismo cronico</li>
                                        <li>Assunzione di sostanze stupefacenti, steroidi o ormoni a scopo di culturismo fisico</li>
                                        <li>Epatite B, C o ad eziologia indeterminata</li>
                                        <li>Infezione da HIV 1-2, da HTLV1-2</li>
                                        <li>Sifilide</li>
                                        <li>Trapianto cornea/dura madre</li>
                                        <li>Diabete insulino dipendente</li>
                                        <li>Ipertensione grave o in trattamento con B-bloccanti</li>
                                        <li>Nefropatie croniche</li>
                                        <li>Neoplasie maligne</li>
                                    </ul>
                                    </p>

                                </div>
                            </div>
                        </div>
                    </div>

                </div>

                <!-- /.container-fluid -->

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