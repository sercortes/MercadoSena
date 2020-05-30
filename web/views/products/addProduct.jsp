

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@include file="/views/AdminTemplate/head.jspf"%>

<link type="text/css" rel="stylesheet" href="./assetsAdmin/files/material.css" async>
<link type="text/css" rel="stylesheet" href="./assetsAdmin/files/css-loader.css" async>
<link href="./assetsAdmin/files/image-uploader.min.css" rel="stylesheet" type="text/css" async>



<%@include file="/views/AdminTemplate/menu.jspf"%>



<div class="container-fluid pb-4">

    <div class="row d-flex justify-content-center">

        <div class="col-10 pb-4 card p-4">
        <h5 class="titulos card-title hvr-icon-pop text-center pb-3"><i class="fas fa-gifts"></i> Nuevo Producto</h5>

            <form class="needs-validation" action="UploadProduct" method="POST" name="formProduct" id="formProduct" enctype="multipart/form-data" acceptcharset="UTF-8">


                <div class="form-row">
                    <div class="form-group col-md-4">
                        <label for="exampleInputEmail1">Nombre:</label>
                        <input type="text" class="form-control was-validated" minlength="4" maxlength="150" id="name" name="name" placeholder="Nombre de su producto" required>
                        <div class="valid-feedback">
                            :D
                        </div>
                        <div class="invalid-feedback">
                            Escriba un nombre de mínimo 4 carácteres
                        </div>
                    </div>
                    <div class="form-group col-md-4">
                         <label for="exampleInputEmail1">Cantidad:</label>
                        <input type="number" class="form-control was-validated" minlength="0" max="100000000" id="cantidad" name="cantidad" placeholder="Stock" oninput="validity.valid||(value='');" required>
                        <div class="valid-feedback">
                            :D
                        </div>
                        <div class="invalid-feedback">
                            Escriba precio válido
                        </div>
                     
                    </div>
                      <div class="form-group col-md-4">
                        <label for="exampleInputEmail1">Precio:</label>
                        <input type="number" class="form-control was-validated" minlength="0" max="1500000000000000" id="price" name="price" placeholder="$" oninput="validity.valid||(value='');" required>
                        <div class="valid-feedback">
                            :D
                        </div>
                        <div class="invalid-feedback">
                            Escriba precio válido
                        </div>
                    </div>
                </div>


                <div class="form-row">
                   
                    <div class="form-group col-md-4">
                         <label for="validationTooltip03">Categoría:</label>
                        <select name="category" class="form-control" id="category" tabindex="4" required>
                            <option value="">No</option>
                        </select>
                        <div class="invalid-feedback">
                            Escriba una categoría
                        </div> 
                    </div>
                     <div class="form-group col-md-4">
                        <label for="exampleInputEmail1">Marca:</label>
                        <input type="text" class="form-control was-validated" minlength="2" maxlength="150" id="marca" name="marca" placeholder="Marca de su producto" required>
                        <div class="valid-feedback">
                            :D
                        </div>
                        <div class="invalid-feedback">
                            Escriba una marca de mínimo 2 carácteres
                        </div>
                    </div>
                    <div class="form-group col-md-4">
                         <label for="exampleInputEmail1">Fecha de vencimiento:</label>
                        <input type="date" class="form-control was-validated"  id="fechaV" name="fechaV">
                        <small id="emailHelp" class="form-text text-muted">No es obligatorio.</small>
                    </div>
                </div>

                <div class="form-row">
                   
                    
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
                    <label class="active">Imagenes:</label> 
                    <div class="input-images-1" style="padding-top: .5rem;"></div>
                    <a id="reset" href="#" class="btn btn-info btn-sm float-right" title="Limpiar imagenes"><i class="fas fa-eraser"></i> Borrar</a>
                    <small id="emailHelp" class="form-text text-muted">Puedes subir máximo 5 elementos. no pueden superar los 8MB</small>
                </div>

                <hr class="sidebar-divider">

                <button id="send" type="submit" class="btn btn-primary hvr-push float-right"><i class="fas fa-save fa-1x"></i> Guardar</button>
      
            </form>


        </div>
    </div>

    <div id="carga" class="loader loader-bouncing"></div>

</div>


<%@include file="/views/AdminTemplate/footer.jspf"%>

<script src="./assetsAdmin/files/image-uploader.min.js" charset="utf-8"></script>
<script src="./assetsAdmin/js/project/util/global.js" charset="utf-8"></script>
<script src="./assetsAdmin/js/project/products/uploadFiles.js" charset="utf-8"></script>
