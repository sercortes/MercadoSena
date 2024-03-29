
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@include file="/views/template/head.jspf"%>

<c:if test="${USER.idRol != 3 || empty USER.idRol}">
    <c:redirect url="./home"/>
</c:if>

<link href="./assets/files/image-uploader.css" rel="stylesheet" type="text/css" async>
<link type="text/css" rel="stylesheet" href="./assets/files/material.css" async>

<%@include file="/views/template/header.jspf"%>
<%@include file="/views/searching/buscador.jspf"%>


<div class="container-fluid pl-0 pr-0" id="container-wrapper">

    <div class="container">
        <div class="row p-2">
            <div class="col-lg-12 pt-2">
                <div id="tituloPagina">
                <h3 class="titulos hvr-icon-pop"><i class="fas fa-gifts naranja"></i> Mis productos </h3>
                <a type="button" href="./newProduct" class="btn btn-primary float-right hvr-push"><i class="fas fa-plus-square"></i> Nuevo</a>
                </div>
            </div>
        </div>    
    </div>
</div>


<%@include file="/views/searching/htmlSearch.jspf"%>
<%@include file="/views/products/modalVerProducto.jspf"%>
<%@include file="/views/products/modalEdit.jspf"%>
<%@include file="/views/products/modalEdit2.jspf"%>
<%@include file="/views/products/modalEdit3.jspf"%>

<%@include file="/views/template/footer.jspf"%>

<script src="./assets/files/image-uploader.min.js" charset="utf-8"></script>
<script src="./assets/js/project/products/showProducts.js" charset="utf-8"></script>
<script src="./assets/js/project/products/updateProduct.js" charset="utf-8"></script>
<script src="./assets/js/project/products/colorsStock.js" charset="utf-8"></script>
<script src="./assets/js/project/products/newColor.js" charset="utf-8"></script>

<!-- quitar-->
<script src="./assets/js/project/comprador/ajax.js"></script>

