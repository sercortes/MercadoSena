

<%@include file="/views/template/head.jspf"%>
<link type="text/css" rel="stylesheet" href="./assets/files/css-loader.css" async>
<%@include file="/views/template/header.jspf"%>

  <div class="container">
    <div class="row p-2">
        <div class="col-lg-12">
            <h3 class="titulos hvr-icon-pop"><i class="fas fa-gifts naranja"></i></h3>
            <a type="button" href="#" class="btn btn-primary float-right hvr-push"><i class="fas fa-laptop"></i></a>
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
    <nav aria-label="Page navigation example">
        <ul id="pagination" class="pagination"></ul>
    </nav>

</div>
</div>

     <div id="cargas" class="loader loader-bouncing"></div>

</div>

<%@include file="/views/searching/modalVerProducto.jspf"%>
<%@include file="/views/searching/modalPreguntar.jspf"%>

<%@include file="/views/login/login.jspf"%>


<%@include file="/views/template/footer.jspf"%>

<script src="./assets/js/pagination/pager.js" charset="utf-8"></script> 
<script src="./assets/js/project/ProductosComprador/ProductsInit.js" charset="utf-8"></script>
<script src="./assets/js/project/ProductosComprador/chat.js" charset="utf-8"></script>