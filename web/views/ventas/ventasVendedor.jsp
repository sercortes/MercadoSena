<%-- 
    Document   : ventasVededor
    Created on : 26/06/2020, 06:30:53 PM
    Author     : DELL
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@include file="/views/template/head.jspf"%>

<c:if test="${ empty USER.idRol}">
    <c:redirect url="./home"/>
</c:if>
<link type="text/css" rel="stylesheet" href="./assets/files/css-loader.css" async>

<%@include file="/views/template/header.jspf"%>

<style>
    /* Make the image fully responsive */
    .carousel-inner img {
        width: 50%;
        height: 50%;
    }
</style>

<div class="container py-5">

    <div class="p-5 bg-white rounded shadow mb-5">
        <!-- Rounded tabs -->
        <ul id="myTab" role="tablist" class="nav nav-tabs nav-pills flex-column flex-sm-row text-center bg-light border-0 rounded-nav">
            <li class="nav-item flex-sm-fill">
                <a id="home-tab" data-toggle="tab" href="#home" role="tab" aria-controls="mostarPedidos" aria-selected="true" class="nav-link border-0 text-uppercase font-weight-bold active">Pedidos pendientes</a>
            </li>
            <li class="nav-item flex-sm-fill">
                <a id="profile-tab" data-toggle="tab" href="#" role="tab" aria-selected="false" class="nav-link border-0 text-uppercase font-weight-bold">Pedidos concretados</a>
            </li>
            <li class="nav-item flex-sm-fill">
                <a id="contact-tab" data-toggle="tab" href="#" role="tab"  aria-selected="false" class="nav-link border-0 text-uppercase font-weight-bold" data-original-title="hola">Pedidos no concretados</a>
            </li>
        </ul>
        <div id="myTabContent" class="tab-content">
            <div id="mostarPedidos" role="tabpanel" aria-labelledby="home-tab" class="tab-pane fade px-4 py-5 show active" style="padding-left: 0rem !important;padding-right: 0rem !important;">

                    <div class="row">
                        <div class="col-lg-9 mx-auto">
                            <!-- Accordion -->
                            <div id="accordionExample" class="accordion shadow">
                                <div id="pedidos">
                                    <!-- Accordion item 1 -->
                    
                                </div>
                            </div>

                        
                    </div>
                </div>

            </div>
            <div id="profile" role="tabpanel" aria-labelledby="profile-tab" class="tab-pane fade px-4 py-5">

            </div>
            <div id="contact" role="tabpanel" aria-labelledby="contact-tab" class="tab-pane fade px-4 py-5">
            </div>
        </div>
        <!-- End rounded tabs -->
    </div>



</div>











<%@include file="/views/ventas/modalVerUsuario.jspf"%>
<%@include file="/views/template/footer.jspf"%>
<script src="./assets/js/project/ventas/pedidosVendedor.js" type="text/javascript"></script>
