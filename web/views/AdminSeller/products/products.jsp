

<%@include file="/views/AdminTemplate/head.jspf"%>

<%@include file="/views/AdminTemplate/menu.jspf"%>



<div class="container-fluid pb-3" id="container-wrapper">




    <div class="row">
        <div class="col-lg-12">
            <h3 class="titulos hvr-icon-pop"><i class="fas fa-gifts"></i> Productos</h3>
            <a type="button" href="./newProduct" class="btn btn-primary float-right hvr-push"><i class="fas fa-plus-square"></i> Agregar</a>
        </div>
    </div>
    

</div>



<div class="row" id="fondo-rojo">
    <div class="col-lg-11 mx-auto">
      <!-- FIRST EXAMPLE ===================================-->
      <div class="row py-5" id="tabla">
          
        

        </div>
        </div>

      <div class="separator my-3"></div>

<div id="pager" class="col-sm-12">
    <nav aria-label="Page navigation example">
    <ul id="pagination" class="pagination"></ul>
    </nav>

    </div>

      <%@include file="/views/AdminSeller/products/modalVerProducto.jspf"%>
      
	<%@include file="/views/AdminTemplate/footer.jspf"%>

<script src="./assetsAdmin/js/project/products/showProducts.js" charset="utf-8"></script>
        
<script src="./assetsAdmin/js/pagination/pager.js" charset="utf-8"></script>
