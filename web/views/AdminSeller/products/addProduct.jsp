

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@include file="/views/AdminTemplate/head.jspf"%>

<link type="text/css" rel="stylesheet" href="https://fonts.googleapis.com/icon?family=Material+Icons">
<link href="./assetsAdmin/files/image-uploader.min.css" rel="stylesheet" type="text/css" async>


<%@include file="/views/AdminTemplate/menu.jspf"%>



<div class="container-fluid" id="container-wrapper">

    <div class="row">
        <div class="col-lg-12">
            <h3 class="titulos hvr-icon-pop"><i class="fas fa-users-cog hvr-icon"></i> Nuevo Producto</h3>
        </div>
    </div>
   
    <hr class="sidebar-divider">

    <form action="UploadProduct" method="POST" name="form-example-1" id="form-example-1" enctype="multipart/form-data">


        <div class="input-field">
            <label class="active">Imagenes producto</label>
            <div class="input-images-1" style="padding-top: .5rem;"></div>
        </div>

        <hr class="sidebar-divider">

        <button type="submit" class="btn btn-primary btn-lg hvr-push"><i class="far fa-file-excel fa-1x"></i> Cargar archivo</button>
    </form>


    <hr class="sidebar-divider">

    <c:if test="${not empty MESSAGE}">
        <div class="alert alert-warning alert-dismissible fade show" role="alert">
            <strong></strong> ${MESSAGE}
            <button type="button" class="close" data-dismiss="alert" aria-label="Close">
                <span aria-hidden="true">&times;</span>
            </button>
        </div>
    </c:if>


</div>


<%@include file="/views/AdminTemplate/footer.jspf"%>
<script src="./assetsAdmin/files/image-uploader.min.js"></script>

<script>

    $(document).ready(function () {

        $('.input-images-1').imageUploader();

    });

</script>