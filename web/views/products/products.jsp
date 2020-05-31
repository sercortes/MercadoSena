
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@include file="/views/template/head.jspf"%>

<link href="./assets/files/image-uploader.css" rel="stylesheet" type="text/css" async>
<link type="text/css" rel="stylesheet" href="./assets/files/material.css" async>
<link type="text/css" rel="stylesheet" href="./assets/files/css-loader.css" async>


<%@include file="/views/template/header.jspf"%>

<div class="container-fluid" id="container-wrapper">

    <div class="container">
    <div class="row p-2">
        <div class="col-lg-12">
            <h3 class="titulos hvr-icon-pop"><i class="fas fa-gifts naranja"></i> Productos</h3>
            <a type="button" href="./newProduct" class="btn btn-primary float-right hvr-push"><i class="fas fa-plus-square"></i> Agregar</a>
        </div>
    </div>    
</div>


    <div id="fondo-rojo" class="">
        
        <style>
 
        </style>

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
    <nav aria-label="Page navigation example">
        <ul id="pagination" class="pagination"></ul>
    </nav>

</div>
</div>


</div>
  </div>


    <%@include file="/views/products/modalVerProducto.jspf"%>
    <%@include file="/views/products/modal2.jspf"%>
    
<%@include file="/views/login/login.jspf"%>
<%@include file="/views/template/footer.jspf"%>
  
<script src="./assets/files/image-uploader.min.js" charset="utf-8"></script>
<script src="./assets/js/project/util/global.js" charset="utf-8"></script>


<script src="./assets/js/project/products/showProducts.js" charset="utf-8"></script>
<script src="./assets/js/project/products/updateProduct.js" charset="utf-8"></script>
        
<script src="./assets/js/pagination/pager.js" charset="utf-8"></script>

<script src="./assets/js/project/comprador/ajax.js"></script>





    
  