
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@include file="/views/template/head.jspf"%>

<c:if test="${ empty USER.idRol}">
    <c:redirect url="./home"/>
</c:if>

<link type="text/css" rel="stylesheet" href="./assets/files/css-loader.css" async>

<%@include file="/views/template/header.jspf"%>

<%@include file="/views/searching/buscador.jspf"%>

<input type="hidden" value="misPedidos" id="nombreVista">

<div class="container py-5">
    <div class="col-lg-9 mx-auto text-black text-center">
        <h1 class="display-4">Mis Contactos</h1>     
    </div>
    <div class="p-5 bg-white rounded shadow mb-5">
        
        <!-- Rounded tabs -->
        <ul id="myTab" role="tablist" class="nav nav-tabs nav-pills flex-column flex-sm-row text-center bg-light border-0 rounded-nav">
            <li class="nav-item flex-sm-fill">
                <a id="pedPendientes-tab" data-toggle="tab" href="#home" role="tab" aria-controls="mostarPedidos" aria-selected="true" class="nav-link border-0 text-uppercase font-weight-bold active">Pendientes</a>
            </li>
            <li class="nav-item flex-sm-fill">
                <a id="pedConcretados-tab" data-toggle="tab" href="#" role="tab" aria-selected="false" class="nav-link border-0 text-uppercase font-weight-bold">Concretados</a>
            </li>
            <li class="nav-item flex-sm-fill">
                <a id="pedNoCon-tab" data-toggle="tab" href="#" role="tab"  aria-selected="false" class="nav-link border-0 text-uppercase font-weight-bold" data-original-title="hola">No concretados</a>
            </li>
        </ul>
        <div id="myTabContent" class="tab-content">
            <div id="mostarPedidos" role="tabpanel" aria-labelledby="home-tab" class="tab-pane fade px-4 py-5 show active" style="padding-left: 0rem !important;padding-right: 0rem !important;">

                <div class="row">
                    <div class="col-lg-9 mx-auto">
                        <div id="accordionExample" class="accordion shadow">
                            <div id="pedidos">


                            </div>
                        </div>


                    </div>
                </div>

            </div>

        </div>
    </div>



</div>

<div id="carga" class="loader loader-bouncing"></div>
<%@include file="/views/ventas/modalVerVendedor.jspf"%>
<%@include file="/views/template/footer.jspf"%>
<script src="./assets/js/project/ventas/pedidosVendedor.js" type="text/javascript"></script>
