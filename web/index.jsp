<%@include file="/views/template/head.jspf"%>

<%@include file="/views/template/header.jspf"%>
<%@include file="/views/searching/buscador.jspf"%>
<%@include file="/views/init/carusel.jspf"%>

 <div class="container-fluid">
    <div class="row p-3">
        <div class="col-lg-12 pt-2">
            <div id="tituloPagina">
            <h3 class="titulos text-center"><i class="fas fa-store naranja"></i> Te pueden interesar</h3>
            </div>
        </div>
    </div>    
</div>


<%@include file="/views/searching/htmlSearch.jspf"%>
<%@include file="/views/searching/modalVerProducto.jspf"%>
<%@include file="/views/searching/modalPreguntar.jspf"%>



    <%@include file="/views/login/login.jspf"%>
    <%@include file="/views/registro/registro.jspf"%>
    
<c:if test="${empty USER.idRol}">
    
    <%@include file="/views/recuperarClave/recuperarClave.jspf"%>

</c:if>


<%@include file="/views/template/footer.jspf"%>

<script src="./assets/js/project/filtro/productosInicio.js" type="text/javascript"></script>
