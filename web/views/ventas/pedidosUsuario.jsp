<%-- 
    Document   : ventasVededor
    Created on : 26/06/2020, 06:30:53 PM
    Author     : DELL
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@include file="/views/template/head.jspf"%>

<c:if test="${USER.idRol != 2 || empty USER.idRol}">
    <c:redirect url="./home"/>
</c:if>

<%@include file="/views/template/header.jspf"%>
<%@include file="/views/searching/buscador.jspf"%>

<div class="container py-3">

    <div class="col-lg-9 mx-auto text-black text-center">
        <div id="tituloPagina">
            <h1 class="display-4">Mis compras</h1>   
        </div>
    </div>

    <div id="content" class="p-5 bg-white rounded shadow mb-5">
        <ul id="myTab" role="tablist" class="nav nav-tabs nav-pills flex-column flex-sm-row text-center bg-light border-0 rounded-nav">
            <li class="nav-item flex-sm-fill">
                <a id="pedPendientes" data-toggle="tab" href="#pedPendientes" role="tab" aria-controls="mostarPedidos" aria-selected="true" class="nav-link border-0 text-uppercase font-weight-bold active section">Pendientes</a>
            </li>
            <li class="nav-item flex-sm-fill">
                <a id="pedConcretados" data-toggle="tab" href="#pedConcretados" role="tab" aria-selected="false" class="nav-link border-0 text-uppercase font-weight-bold section">Concretados</a>
            </li>
            <li class="nav-item flex-sm-fill">
                <a id="pedNoConcretados" data-toggle="tab" href="#pedNoConcretados" role="tab"  aria-selected="false" class="nav-link border-0 text-uppercase font-weight-bold section" data-original-title="hola">No concretados</a>
            </li>
        </ul>
        <div id="myTabContent" class="tab-content">
            <div id="mostarPedidos" role="tabpanel" aria-labelledby="home-tab" class="tab-pane fade px-4 py-5 show active" style="padding-left: 0rem !important;padding-right: 0rem !important;">
                <div class="row">
                    <div class="col-lg-9 mx-auto">
                        <div id="accordionExample" class="accordion shadow-sm">
                            <div id="pedidos">
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <div id="pagee" class="row justify-content-center pb-2 pt-2">
                <nav aria-label="Page navigation example">
                    <ul id="pagination" class="pagination"></ul>
                </nav>
            </div>
        </div> 
    </div>



</div>

<%@include file="/views/searching/htmlSearch.jspf"%>
<%@include file="/views/searching/modalVerProducto.jspf"%>

<%@include file="/views/template/footer.jspf"%>

<script src="./assets/js/project/ventas/pedidosComprador.js" type="text/javascript"></script>