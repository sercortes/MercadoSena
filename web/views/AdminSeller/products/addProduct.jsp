

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@include file="/views/AdminTemplate/head.jspf"%>

<link type="text/css" rel="stylesheet" href="https://fonts.googleapis.com/icon?family=Material+Icons" async>
<link href="./assetsAdmin/files/image-uploader.min.css" rel="stylesheet" type="text/css" async>


<%@include file="/views/AdminTemplate/menu.jspf"%>



<div class="container-fluid" id="container-wrapper">

    <div class="row d-flex justify-content-center">
        <div class="col-12">
            <h3 class="titulos hvr-icon-pop"><i class="fas fa-plus-square hvr-icon-pop"></i> Nuevo Producto</h3>

        </div>
    </div>

    <hr class="sidebar-divider">

    <div class="row d-flex justify-content-center">
        <div class="col-8">

            <form action="UploadProduct" method="POST" name="formProduct" id="formProduct" enctype="multipart/form-data">

                <div class="form-group">
                    <label for="exampleInputEmail1">Nombre Producto:</label>
                    <input type="text" class="form-control" id="name" name="name" placeholder="Nombre de su producto">
                </div>

                <div class="input-field">
                    <label class="active">Imagenes producto</label> <a id="reset" href="#" class="hvr-push"><i class="fas fa-eraser fa-2x"></i> </a>
                    <div class="input-images-1" style="padding-top: .5rem;"></div>
                </div>

                <hr class="sidebar-divider">

                <button type="submit" class="btn btn-primary btn-lg hvr-push float-right"><i class="far fa-file-excel fa-1x"></i> Cargar archivo</button>
            </form>

            <hr class="sidebar-divider">

        </div>
    </div>


</div>


<%@include file="/views/AdminTemplate/footer.jspf"%>
<script src="./assetsAdmin/files/image-uploader.min.js"></script>
<script src="./assetsAdmin/js/project/util/global.js"></script>
<script src="./assetsAdmin/js/project/products/uploadFiles.js" charset="utf-8"></script>
