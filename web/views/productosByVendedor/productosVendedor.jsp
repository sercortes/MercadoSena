
<%@include file="/views/template/head.jspf"%>
<%@include file="/views/template/header.jspf"%>

<c:if test="${USER.idRol != 3 || empty USER.idRol}">
    <c:redirect url="./home"/>
</c:if>

  <div class="container">
    <div class="row p-2">
        
        <div class="col-lg-12 pt-2">
            <div id="tituloPagina">
            <h3 class="titulos hvr-icon-pop"><i class="fas fa-shipping-fast naranja"></i> Reportar venta</h3>
            </div>
        </div>
        
          <div class="col-lg-12 p-5 bg-white rounded shadow-sm mb-5">

          <!-- Shopping cart table -->
          <div class="table-responsive">
            <table class="table table-striped">
                <thead class="bg-primary">
                <tr>
                  <th scope="col" class="border-0">
                    <div class="p-2 px-3 text-uppercase">Producto</div>
                  </th>
                  <th scope="col" class="border-0">
                    <div class="py-2 text-uppercase">Precio</div>
                  </th>
                  <th scope="col" class="border-0">
                    <div class="py-2 text-uppercase">Cantidad</div>
                  </th>
                  <th scope="col" class="border-0">
                    <div class="py-2 text-uppercase">Opciones</div>
                  </th>
                </tr>
              </thead>
              <tbody id="tabla">
               
              </tbody>
            </table>
          </div>
          <!-- End -->
        </div>
          
    </div>
       <div id="cargas" class="loader loader-bouncing"></div>
</div>

<%@include file="/views/login/login.jspf"%>
<%@include file="/views/registro/registro.jspf"%>
<%@include file="/views/recuperarClave/recuperarClave.jspf"%>
<%@include file="/views/template/footer.jspf"%>

<script src="./assets/js/pagination/jquery.dataTables.min.js"></script>
<script src="./assets/js/project/ventasVendedor/showProducts.js" charset="utf-8"></script>
