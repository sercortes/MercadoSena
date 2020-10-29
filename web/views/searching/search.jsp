
<%@include file="/views/template/head.jspf"%>

<link type="text/css" rel="stylesheet" href="./assets/files/css-loader.css" async>

<%@include file="/views/template/header.jspf"%>
<%@include file="/views/searching/buscador.jspf"%>

  <div class="container">
    <div class="row p-2">
        <div class="col-lg-12 pt-2">
            <div id="tituloPagina">
            <h3 class="titulos hvr-icon-pop"><i class="fas fa-gifts naranja"></i> Descubrir</h3>
            </div>
        </div>
    </div>    
</div>
 
<%@include file="/views/searching/htmlSearch.jspf"%>
<%@include file="/views/searching/modalVerProducto.jspf"%>
<%@include file="/views/searching/modalPreguntar.jspf"%>


<c:if test="${ empty USER.idRol}">

<%@include file="/views/login/login.jspf"%>
<%@include file="/views/registro/registro.jspf"%>
<%@include file="/views/registro/registroEmpresa.jspf"%>
<%@include file="/views/recuperarClave/recuperarClave.jspf"%>

</c:if>

<%@include file="/views/template/footer.jspf"%>