

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@include file="/views/AdminTemplate/head.jspf"%>

<link type="text/css" rel="stylesheet" href="https://fonts.googleapis.com/icon?family=Material+Icons" async>
<link href="./assetsAdmin/files/image-uploader.min.css" rel="stylesheet" type="text/css" async>


<%@include file="/views/AdminTemplate/menu.jspf"%>



<div class="container-fluid" id="container-wrapper">

    <div class="row d-flex justify-content-center">
        <div class="col-12">
            <h5 class="titulos hvr-icon-pop"><i class="fas fa-plus-square hvr-icon-pop"></i> Nuevo Producto</h5>

        </div>
    </div>

    <hr class="sidebar-divider">

    <div class="row d-flex justify-content-center">
        <div class="col-8 pb-4 card p-4">

            <form class="needs-validation" action="UploadProduct" method="POST" name="formProduct" id="formProduct" enctype="multipart/form-data">


                <div class="form-row">
                    <div class="form-group col-md-6">
                        <label for="exampleInputEmail1">Nombre Producto:</label>
                        <input type="text" class="form-control was-validated" minlength="4" maxlength="150" id="name" name="name" placeholder="Nombre de su producto" required>
                        <div class="valid-feedback">
                            :D
                        </div>
                        <div class="invalid-feedback">
                            Escriba un nombre de mínimo 4 carácteres
                        </div>
                    </div>
                    <div class="form-group col-md-6">
                        <label for="exampleInputEmail1">Fecha de vencimiento:</label>
                        <input type="date" class="form-control was-validated"  id="fechaV" name="fechaV">
                        <small id="emailHelp" class="form-text text-muted">No es obligatorio.</small>
                    </div>
                </div>


                <div class="form-row">
                    <div class="form-group col-md-6">
                        <label for="exampleInputEmail1">Precio del producto:</label>
                        <input type="number" class="form-control was-validated" minlength="0" max="1500000000000000" id="price" name="price" placeholder="$" oninput="validity.valid||(value='');" required>
                        <div class="valid-feedback">
                            :D
                        </div>
                        <div class="invalid-feedback">
                            Escriba precio válido
                        </div>
                    </div>
                    <div class="form-group col-md-6">
                        <label for="exampleInputEmail1">Cantidad del producto:</label>
                        <input type="number" class="form-control was-validated" minlength="0" max="100000000" id="cantidad" name="cantidad" placeholder="Stock" oninput="validity.valid||(value='');" required>
                        <div class="valid-feedback">
                            :D
                        </div>
                        <div class="invalid-feedback">
                            Escriba precio válido
                        </div>
                    </div>
                </div>

                <div class="form-row">
                    <div class="form-group col-md-6">
                        <label for="exampleInputEmail1">Marca Producto:</label>
                        <input type="text" class="form-control was-validated" minlength="4" maxlength="150" id="marca" name="marca" placeholder="Marca de su producto" required>
                        <div class="valid-feedback">
                            :D
                        </div>
                        <div class="invalid-feedback">
                            Escriba una marca de mínimo 3 carácteres
                        </div>
                    </div>
                    <div class="form-group col-md-6">
                        <label for="validationTooltip03">Categoría:</label>
                        <select name="category" class="form-control" id="category" tabindex="4" required>
                            <option value="">No</option>
                        </select>
                        <div class="invalid-feedback">
                            Escriba una categoría
                        </div>
                    </div>
                </div>

                <div class="form-group">
                    <label for="exampleFormControlTextarea1">Descripción:</label>
                    <textarea class="form-control" id="descrip" name="descrip" rows="3" placeholder="Descripción de su producto" minlength="20" maxlength="500" required></textarea>
                    <small id="emailHelp" class="form-text text-muted">Si su producto tiene información adicional coloquela en este campo.</small>
                    <div class="valid-feedback">
                        :D
                    </div>
                    <div class="invalid-feedback">
                        Escriba una descripción de mínimo 20 carácteres
                    </div>
                </div>

                <div class="input-field">
                    <label class="active">Imagenes producto:</label> 
                    <div class="input-images-1" style="padding-top: .5rem;"></div>
                    <a id="reset" href="#" class="btn btn-info btn-sm float-right" title="Limpiar imagenes"><i class="fas fa-eraser"></i> Borrar</a>
                    <small id="emailHelp" class="form-text text-muted">Puedes subir máximo 5 elementos. no pueden superar los 8MB</small>
                </div>

                <hr class="sidebar-divider">

                <button type="submit" class="btn btn-primary hvr-push float-right"><i class="fas fa-save fa-1x"></i> Guardar</button>
            </form>


        </div>
    </div>


</div>


<%@include file="/views/AdminTemplate/footer.jspf"%>
<script src="./assetsAdmin/files/image-uploader.min.js"></script>
<script src="./assetsAdmin/js/project/util/global.js"></script>
<script src="./assetsAdmin/js/project/products/uploadFiles.js" charset="utf-8"></script>
