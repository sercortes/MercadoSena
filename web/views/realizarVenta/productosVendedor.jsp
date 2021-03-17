
<%@include file="/views/template/head.jspf"%>
<%@include file="/views/template/header.jspf"%>

<%@include file="/views/realizarVenta/modalVerProducto.jspf"%>

<c:if test="${USER.idRol != 3 || empty USER.idRol}">
    <c:redirect url="./home"/>
</c:if>

<div class="container">
    <div class="row p-2">

        <div class="col-lg-12 pt-2">
            <div id="tituloPagina">
                <h3 class="titulos hvr-icon-pop"><i class="fas fa-shipping-fast naranja"></i> Realizar Venta</h3>
            </div>
        </div>

        <div class="col-lg-12 p-5 bg-white rounded shadow-sm mb-5">

            <table id="example" class="table table-hover table-striped table-borderless table-responsive">
                            
                <thead class="gris text-center">
                                <tr class="bg-primary">
                                    <th class="align-middle" scope="col">Imagen</th>
                                    <th class="align-middle" scope="col">Nombre</th>
                                    <th class="align-middle" scope="col">Referencia</th>
                                    <th class="text-left" scope="col">Valor producto</th>
                                    <th class="text-left" scope="col">Color</th>
                                    <th class="align-middle" scope="col">Cantidad</th>
                                    <th class="align-middle" scope="col">Opciones</th>
                                </tr>
                            </thead>
                            <tfoot class="gris text-center">
                                <tr class="bg-primary">
                                    <th class="align-middle" scope="col">Imagen</th>
                                    <th class="align-middle" scope="col">Nombre</th>
                                    <th class="align-middle" scope="col">Referencia</th>
                                    <th class="align-middle" scope="col">Valor producto</th>
                                    <th class="align-middle" scope="col">Color</th>
                                    <th class="align-middle" scope="col">Cantidad</th>
                                    <th class="align-middle" scope="col">Opciones</th>
                                </tr>
                            </tfoot>
                        </table>
            </div>
    </div>
    <div id="cargas" class="loader loader-bouncing"></div>
</div>

<%@include file="/views/login/login.jspf"%>
<%@include file="/views/registro/registro.jspf"%>
<%@include file="/views/recuperarClave/recuperarClave.jspf"%>
<%@include file="/views/template/footer.jspf"%>

<script src="./assets/js/pagination/jquery.dataTables.min.js"></script>
<script src="./assets/js/project/realizarVenta/showProducts.js" charset="utf-8"></script>
