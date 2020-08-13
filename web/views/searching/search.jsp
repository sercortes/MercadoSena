
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@include file="/views/template/head.jspf"%>
<link type="text/css" rel="stylesheet" href="./assets/files/css-loader.css" async>
<%@include file="/views/template/header.jspf"%>

<div class="barraBusqueda" id="barraBusqueda">
    <p class="text-black" style="color: black; margin-bottom: 0px;padding: 5px;text-align: center;font-size: 16px;font-weight: 500;font-family: unset;">Para una búsqueda más personalizada selecciona un criterio <i id="desplegarMenu" class="fa fa-caret-down colorCursor" ></i></p>
    <nav class="navbar navbar-expand-sm " style="
         padding-bottom: 30px;
         ">
        <form style="width: 100%">
            <div class="busquedaAvanzada">
                <ul class="navbar-nav">
                    <li class="nav-item">

                        <div id="ciudadBucar" style="margin: 5px">

                        </div>
                    </li>
                    <li class="nav-item">


                        <div id="categoriasBuscar" style="margin: 5px"></div>
                    </li>
                    <li class="nav-item">
                        <div id="vendedores" style="margin: 5px">

                        </div>

                    </li>
                </ul>
            </div>
            <div class="header_search_form_container" style="    width: 80%; margin-left: 10%;">
                <input accesskey="" type="search" required="required" id="nombreProductoFiltar" class="header_search_input" placeholder="Nombre producto..." style="width: 100%" onkeyup="seleccionarNombres()">
                <a  id="mostrarResultados"> <button type="submit" title="Buscar" class="header_search_button trans_300" value="Buscar" onclick="filtrar(event)"><img src="./assets/images/icons/search.png" alt=""></button></a>
                <div class="predictivo" ></div>
            </div>
        </form>
    </nav>
</div>

  <div class="container">
    <div class="row p-2">
        <div class="col-lg-12">
            <h3 class="titulos hvr-icon-pop"><i class="fas fa-gifts naranja"></i> Descubrir</h3>
        </div>
    </div>    
</div>


  <div id="fondo-rojo" class="">
        
<div class="row" >
    <div class="col-lg-11 mx-auto">
        <!-- FIRST EXAMPLE ===================================-->
        <div class="row pt-4" id="tabla">



        </div>
    </div>
</div>

<div class="separator my-3"></div>

<div class="row justify-content-md-center pb-3">
<div id="pager" class="col-md-auto">
    <c:if test="${not empty USER}">
        <input type="hidden" id="companyss" value="${USER.empresa.idEmpresa}" disabled=""/>
    </c:if>
         <c:if test="${empty USER}">
        <input type="hidden" id="idcompanyss" value="0" disabled=""/>
        <input type="hidden" id="idUsers" value="0" disabled=""/>
        <input type="hidden" id="companyss" value="0" disabled=""/>
    </c:if>
        
    <nav aria-label="Page navigation example">
        <ul id="pagination" class="pagination"></ul>
    </nav>
</div>
</div>

     <div id="cargas" class="loader loader-bouncing"></div>

</div>

<%@include file="/views/searching/modalVerProducto.jspf"%>
<%@include file="/views/searching/modalPreguntar.jspf"%>


<c:if test="${ empty USER.idRol}">

<%@include file="/views/login/login.jspf"%>
<%@include file="/views/registro/registro.jspf"%>
<%@include file="/views/registro/registroEmpresa.jspf"%>
<%@include file="/views/recuperarClave/recuperarClave.jspf"%>

</c:if>


<%@include file="/views/template/footer.jspf"%>



<script src="./assets/js/project/ProductosComprador/chat.js" charset="utf-8"></script>
<script src="./assets/js/pagination/pager.js" charset="utf-8"></script> 
<script src="./assets/js/project/ProductosComprador/ProductsInit.js" charset="utf-8"></script>