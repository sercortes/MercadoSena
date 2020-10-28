
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@include file="/views/template/head.jspf"%>
<link type="text/css" rel="stylesheet" href="./assets/files/css-loader.css" async>
<%@include file="/views/template/header.jspf"%>

<%@include file="/views/searching/buscador.jspf"%>

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

<script src="./assets/js/project/ProductosComprador/ProductsInit.js" charset="utf-8"></script>
