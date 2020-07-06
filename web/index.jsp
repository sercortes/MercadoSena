

<%@include file="/views/template/head.jspf"%>
<link type="text/css" rel="stylesheet" href="./assets/files/css-loader.css" async>
<%@include file="/views/template/header.jspf"%>

<%@include file="/views/init/carusel.jspf"%>



<div  class="">

    <div class="row" >
        <div class="col-lg-11 mx-auto">
            <div style="width: 100%; text-align:center;margin-top: 20px; " id="tituloResultado"><h3 style="color: black;font-size: 24px;font-weight: 600;font-family: cursive;">Todos los productos</h3></div>
            <center>
                <div class="row pt-4" id="tabla" style="border-radius: 36px;border-top: solid 2px rgba(94, 179, 25, 0.7490196078431373);"> 



                </div>
            </center>
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
</div>
<div id="carga" class="loader loader-bouncing"></div>


<%@include file="/views/searching/modalVerProducto.jspf"%>
<%@include file="/views/searching/modalPreguntar.jspf"%>

<%@include file="/views/login/login.jspf"%>
<%@include file="/views/registro/registro.jspf"%>
<%@include file="/views/registro/registroEmpresa.jspf"%>
<%@include file="/views/recuperarClave/recuperarClave.jspf"%>

<%@include file="/views/template/footer.jspf"%>
<script src="./assets/js/project/ProductosComprador/chat.js" type="text/javascript"></script>
<script src="./assets/js/project/filtro/filtro.js" type="text/javascript"></script>
<script src="./assets/js/project/filtro/productosHtml.js" type="text/javascript"></script>
