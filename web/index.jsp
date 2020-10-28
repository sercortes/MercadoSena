
<%@include file="/views/template/head.jspf"%>

<link type="text/css" rel="stylesheet" href="./assets/files/css-loader.css" async>

<%@include file="/views/template/header.jspf"%>

<%@include file="/views/searching/buscador.jspf"%>

<%@include file="/views/init/carusel.jspf"%>


<div  class="p-2">

    <div class="row pb-3" style="width: 100%" >
        <div class="col-lg-11 mx-auto">
            <div style="width: 100%; text-align:center;margin-top: 20px; " id="tituloResultado"><h3 style="color: black;font-size: 25px;">Te pueden Interesar</h3></div>
            <center>
                <div class="row pt-4 pb-3" id="tabla" style="border-top: solid 1px rgba(94, 179, 25, 0.7490196078431373);width: 100%;"> 


                </div>
            </center>
        </div>
    </div>

    <div class="separator my-3"></div>


</div>


<%@include file="/views/searching/htmlSearch.jspf"%>

<%@include file="/views/searching/modalVerProducto.jspf"%>
<%@include file="/views/searching/modalPreguntar.jspf"%>


<c:if test="${empty USER.idRol}">

    <%@include file="/views/login/login.jspf"%>
    <%@include file="/views/registro/registro.jspf"%>
    <%@include file="/views/registro/registroEmpresa.jspf"%>
    <%@include file="/views/recuperarClave/recuperarClave.jspf"%>

</c:if>


<%@include file="/views/template/footer.jspf"%>


<script src="./assets/js/project/filtro/productosInicio.js" type="text/javascript"></script>
