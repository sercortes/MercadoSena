<%-- 
    Document   : activarCuenta
    Created on : 31/05/2020, 06:45:00 PM
    Author     : DELL
--%>
<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html lang="es"  style=" min-height: 100%;">
    <head>
        <title>ActivarCuenta</title>
        <meta charset="utf-8">
        <meta name="description" content="shop project">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <link href="./assets/images/icons/icon.png" rel="icon" async>

        <link rel="stylesheet" type="text/css" href="./assets/styles/bootstrap4/bootstrap.css" async>


        <link href="./assets/vendor/fontawesome-free/css/all.min.css" rel="stylesheet" type="text/css" async>

        <link rel="stylesheet" type="text/css" href="./assets/styles/main_styles.css">
        <link rel="stylesheet" type="text/css" href="./assets/styles/responsive.css">
        <link rel="stylesheet" type="text/css" href="./assets/styles/hover-min.css" async>


    </head>

    <body >


        <div class="super_container" style="margin-top: -20px;">

            <!-- Header -->

            <header class="header">

                <!-- Header Main -->

                <div class="header_main">
                    <div class="container-fluid"  >
                        <center style="padding-top: 35px;">
                            <div class="col-lg-3 col-10 col-3 order-1 pr-0">
                                <div class="logo_container">
                                    <a href="./"><img src="./assets/images/icons/logo.png" class="img-fluid" alt="Responsive image"></a>
                                </div>
                            </div>
                        </center>
                    </div>
                </div>
            </header>



            <h1 style="text-align: center">Activación de cuenta</h1>
            <c:if test="${activa}">
                <div class="divActivacion" >
                    <p class="textoActivacion">Su cuenta ha sido activada excitosamente, vuelve para iniciar sesión.</p>
                    <i class="fa fa-check-circle" aria-hidden="true" style="font-size: 45px;"></i>
                </div>

            </c:if>
            <c:if test="${!activa}">
                <div class="divActivacion" style="background: rgba(255, 103, 18, 0.76);">
                    <i class="fa fa-exclamation-triangle" style="font-size: 45px;"></i>
                    <p class="textoActivacion"> Ha ocurrido un error al activar su cuenta, favor verificar los datos proporcinados.</p>
                    
                </div>
            </c:if>
            <center>
                    <a href="./" style="font-size: 20px; color: black; text-align: center"><i class="fa fa-home" style="color: #ff6712; font-size: 30px;"></i> Volver al inicio</a>
            </center>
        </div>

        <footer class="py-4 flex-shrink-0" style="background-color: #fcfcfc; position: absolute; width: 100%;bottom: 0">
            <div class="container text-center">
                <a href="#" class="text-muted">SENA 2020 - CGMLTI</a>
            </div>
        </footer>




        <script src="./assets/js/jquery-3.5.1.min.js"></script>
        <script src="./assets/styles/bootstrap4/popper.js"></script>
        <script src="./assets/styles/bootstrap4/bootstrap.min.js"></script>
        <script src="./assets/js/sweet.js"></script>
        <!-- -->
        <script src="./assets/js/project/comprador/ajax.js" charset="utf-8"></script>

        <!-- plugin responsive sidebar-->
        <script src="./assets/js/TweenMax.min.js"></script>
        <script src="./assets/js/custom.js"></script>

        <script src="./assets/js/project/util/functions.js"></script>

        <script src="./assets/js/project/notificaciones/notificaciones.js" type="text/javascript"></script>
    </body>

</html>