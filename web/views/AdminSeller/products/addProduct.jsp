

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
                            Escriba un nombre de m�nimo 4 car�cteres
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
                        <input type="number" class="form-control was-validated" minlength="0" max="1500000000000000" id="price" name="price" placeholder="$$" oninput="validity.valid||(value='');" required>
                        <div class="valid-feedback">
                            :D
                        </div>
                        <div class="invalid-feedback">
                            Escriba precio v�lido
                        </div>
                    </div>
                    <div class="form-group col-md-6">
                        <label for="exampleInputEmail1">Cantidad del producto:</label>
                        <input type="number" class="form-control was-validated" minlength="0" max="100000000" id="cantidad" name="cantidad" placeholder="Stock" oninput="validity.valid||(value='');" required>
                        <div class="valid-feedback">
                            :D
                        </div>
                        <div class="invalid-feedback">
                            Escriba precio v�lido
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
                            Escriba una marca de m�nimo 3 car�cteres
                        </div>
                    </div>
                    <div class="form-group col-md-6">
                        <label for="validationTooltip03">Categoria:</label>
                        <select name="category" class="form-control" id="category" tabindex="4" required>
                            <option value="">No</option>
                        </select>
                    </div>
                </div>

                <div class="form-group">
                    <label for="exampleFormControlTextarea1">Descripci�n:</label>
                    <textarea class="form-control" id="descrip" name="descrip" rows="3" placeholder="Descripci�n de su producto" minlength="20" maxlength="500" required></textarea>
                    <small id="emailHelp" class="form-text text-muted">Si su producto tiene informaci�n adicional coloquela en este campo.</small>
                    <div class="valid-feedback">
                        :D
                    </div>
                    <div class="invalid-feedback">
                        Escriba una descripci�n de m�nimo 20 car�cteres
                    </div>
                </div>

                <div class="input-field">
                    <label class="active">Imagenes producto:</label> 
                    <div class="input-images-1" style="padding-top: .5rem;"></div>
                    <a id="reset" href="#" class="btn btn-info btn-sm float-right" title="Limpiar imagenes"><i class="fas fa-eraser"></i> Borrar</a>
                    <small id="emailHelp" class="form-text text-muted">Puedes subir m�ximo 5 elementos.</small>
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
