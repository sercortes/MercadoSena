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
        <link href="./assets/images/page/icon2.png" rel="icon" async>
        <link rel="stylesheet" type="text/css" href="./assets/styles/bootstrap4/bootstrap.css" async>
        <link href="./assets/vendor/fontawesome-free/css/all.min.css" rel="stylesheet" type="text/css" async>
        <link rel="stylesheet" type="text/css" href="./assets/styles/main_styles.css">
        <link rel="stylesheet" type="text/css" href="./assets/styles/responsive.css">
        <link rel="stylesheet" type="text/css" href="./assets/styles/hover-min.css" async>


    </head>

    <body>

        <div class="header_main p-3">
            <div class="container-fluid"  >
                <center style="padding-top: 35px;">
                    <div class="col-lg-3 col-10 col- pr-0">
                        <div class="logo_container">
                            <a href="./"><img src="./assets/images/icons/LOGO3.png" class="img-fluid" alt="Responsive image" style="width: 50%;"></a>
                        </div>
                    </div>
                </center>
            </div>
        </div>


        <div class="container-fluid" style="">


            <c:if test="${ACTIVA}">
                <div class="divActivacion" >
                    <p class="textoActivacion">Su cuenta ha sido activada exitosamente, vuelve para iniciar sesión.</p>
                    <i class="far fa-check-circle" aria-hidden="true" style="font-size: 45px;"></i>
                </div>

            </c:if>
            <c:if test="${!ACTIVA}">
                <div class="divActivacion" style="background: rgba(255, 103, 18, 0.76);">
                    <i class="fa fa-exclamation-triangle" style="font-size: 45px;"></i>
                    <p class="textoActivacion"> Enlace vencido, favor verificar los datos proporcinados.</p>

                </div>
            </c:if>
            <center>
                <a href="./" style="font-size: 20px; color: black; text-align: center"><i class="fa fa-home" style="color: #ff0000; font-size: 20px;"></i> Volver al inicio</a>
            </center>
        </div>

        <footer class="" style="">
            <div class="container text-center">
                <a href="#" class="text-muted">CarWay 2021</a>
            </div>
        </footer>


        <script src="./assets/js/jquery-3.5.1.min.js"></script>
        <script src="./assets/styles/bootstrap4/popper.js"></script>
        <script src="./assets/styles/bootstrap4/bootstrap.min.js"></script>
        <script src="./assets/js/sweet.js"></script>
        <!-- -->

        <!-- plugin responsive sidebar-->
        <script src="./assets/js/TweenMax.min.js"></script>
        <script src="./assets/js/custom.js"></script>
    </body>

</html>