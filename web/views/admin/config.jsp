
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@include file="/views/template/head.jspf"%>

<c:if test="${USER.idRol != 3 || empty USER.idRol}">
    <c:redirect url="./home"/>
</c:if>
<c:if test="${USER.correoUsu != 'vendedor@carway.co'}">
    <c:redirect url="./home"/>             
</c:if>

<%@include file="/views/template/header.jspf"%>
<%@include file="/views/searching/buscador.jspf"%>

<div class="container py-3">

    <div class="col-lg-9 mx-auto text-black text-center">
        <div id="tituloPagina">
            <h1 class="display-4">Ajustes</h1>   
        </div>
    </div>

    <div id="content" class="p-5 bg-white rounded shadow mb-5 row">

        <div class="col-md-6">
            <h4 class="text-left">Categoría</h4>
            <label for="inputPassword2" class="sr-only">Nueva Categoría:</label>
            <input type="text" id="categoria" class="form-control" placeholder="Nueva categoría">
            <hr>
            <button id="addCategoria" type="submit" class="btn btn-primary mb-2 float-right"><i class="fas fa-plus-circle"></i> Agregar</button>

        </div>
        <div class="col-md-6">
            <h4 class="text-left">Marca</h4>
            <label for="inputPassword2" class="sr-only">Nueva Marca:</label>
            <input type="text" id="marca" class="form-control" placeholder="Nueva marca">
            <hr>
            <button id="addMarca" type="submit" class="btn btn-primary mb-2 float-right"><i class="fas fa-plus-circle"></i> Agregar</button>

        </div>
        <div class="col-md-6">
            <hr>
            <h4 class="text-left">Texto Banner</h4>

            <div id="banners">

            </div>

            <button id="editBanner" type="submit" class="btn btn-primary mb-2 float-right"><i class="fas fa-plus-circle"></i> Cambiar</button>

        </div>

        <div class="col-md-6">
            <hr>
            <h4 class="text-left">Vendedor</h4>
            <h5 class="text-black">Crea un nuevo vendedor para administrar el sistema</h5>
            <button id="modalAdd" class="btn btn-primary mb-2 float-right"><i class="fas fa-plus-circle"></i> Nuevo</button>

        </div>
        <div class="container">
        <hr>
            <h3>Eventos sistema</h3>
            <div class="row">



                    <div class="col-lg-12 col-sm-12 col-xs-12 p-5 bg-white rounded shadow-sm mb-5 w-100">

                        <table id="example" class="table table-hover table-striped table-borderless table-responsive">

                            <thead class="gris text-center">
                                <tr class="bg-primary">
                                    <th class="align-middle" scope="col">Id</th>
                                    <th class="align-middle" scope="col">Nombre</th>
                                    <th class="align-middle" scope="col">Fecha</th>
                                    <th class="align-middle" scope="col">Usuario</th>
                                    <th class="text-left" scope="col">Detalles</th>
                                </tr>
                            </thead>
                            <tfoot class="gris text-center">
                                <tr class="bg-primary">
                                    <th class="align-middle" scope="col">Imagen</th>
                                    <th class="align-middle" scope="col">Nombre</th>
                                    <th class="align-middle" scope="col">Fecha</th>
                                    <th class="align-middle" scope="col">Usuario</th>
                                    <th class="align-middle" scope="col">Detalles</th>
                                </tr>
                            </tfoot>
                        </table>
                    </div>

            </div>
        </div>

    </div>

</div>

<%@include file="/views/searching/htmlSearch.jspf"%>
<%@include file="/views/searching/modalVerProducto.jspf"%>
<%@include file="/views/admin/modalAdd.jspf"%>
<%@include file="/views/admin/modalDeta.jspf"%>
<%@include file="/views/admin/modalVerProducto.jspf"%>
<%@include file="/views/template/footer.jspf"%>

<script src="./assets/js/pagination/jquery.dataTables.min.js"></script>
<script src="./assets/js/project/config/config.js" type="text/javascript"></script>
<script src="./assets/js/project/config/add.js" type="text/javascript"></script>
<script src="./assets/js/project/config/logs.js" type="text/javascript"></script>
<script src="./assets/js/project/config/logs2.js" type="text/javascript"></script>