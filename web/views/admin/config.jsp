
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@include file="/views/template/head.jspf"%>

<c:if test="${USER.idRol != 3 || empty USER.idRol}">
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
            <h4 class="text-left">Texto Banner</h4>
            
            <div id="banners">
                
            </div>
            
            <button id="editBanner" type="submit" class="btn btn-primary mb-2 float-right"><i class="fas fa-plus-circle"></i> Cambiar</button>

        </div>

    </div>

</div>

<%@include file="/views/searching/htmlSearch.jspf"%>
<%@include file="/views/searching/modalVerProducto.jspf"%>

<%@include file="/views/template/footer.jspf"%>

<script src="./assets/js/project/config/config.js" type="text/javascript"></script>