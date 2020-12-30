
<%@include file="/views/template/head.jspf"%>

<link type="text/css" rel="stylesheet" href="./assets/files/css-loader.css" async>

<%@include file="/views/template/header.jspf"%>
<%@include file="/views/searching/buscador.jspf"%>

  <div class="container">
    <div class="row p-2">
        <div class="col-lg-12 pt-2">
            <div id="tituloPagina">
                <h3 class="titulos hvr-icon-pop"><i class="fas fa-gifts naranja"></i> Productos por aprobar </h3>
            </div>
        </div>
    </div>    
</div>
 
<%@include file="/views/searching/htmlSearch.jspf"%>
<%@include file="/views/admin/modalVerProducto.jspf"%>
<%@include file="/views/admin/modal2.jspf"%>


<c:if test="${ empty USER.idRol}">

<%@include file="/views/login/login.jspf"%>
<%@include file="/views/registro/registro.jspf"%>
<%@include file="/views/registro/registroEmpresa.jspf"%>
<%@include file="/views/recuperarClave/recuperarClave.jspf"%>

</c:if>

<%@include file="/views/template/footer.jspf"%>
<script src="./assets/js/project/admin/showProducts.js" charset="utf-8"></script>
<script src="./assets/js/project/admin/check.js" charset="utf-8"></script>
<script src="./assets/js/pagination/pager.js" charset="utf-8"></script>
