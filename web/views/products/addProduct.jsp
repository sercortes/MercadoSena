<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@include file="/views/template/head.jspf"%>
<link href="./assets/files/image-uploader.css" rel="stylesheet" type="text/css" async>
<link type="text/css" rel="stylesheet" href="./assets/files/material.css" async>
<%@include file="/views/template/header.jspf"%>
<%@include file="/views/searching/buscador.jspf"%>

<c:if test="${USER.idRol != 3 || empty USER.idRol}">
    <c:redirect url="./home"/>
</c:if>

<div class="container-fluid" id="">

    <div class="row d-flex justify-content-center pb-3" id="fondo-rojo">

        <div class="col-8 pb-4 pt-2 shadow-sm" style="background-color: white;margin-top: 0.7%;">
            <h3 class="titulos card-title hvr-icon-pop text-center pb-3"><i class="fas fa-gifts naranja"></i> Nuevo Producto</h3>

            <form class="needs-validation" action="UploadProduct" method="POST" name="formProduct" id="formProduct" enctype="multipart/form-data" acceptcharset="UTF-8">

                <div class="form-row">
                    <div class="form-group col-md-10">
                        <label for="exampleInputEmail1">Nombre:</label>
                        <input type="text" class="form-control was-validated" minlength="4" maxlength="150" id="name" name="name" placeholder="Nombre de su producto" required>

                        <div class="invalid-feedback">
                            Escriba un nombre de m�nimo 4 car�cteres
                        </div>
                    </div>


                </div>

                <div class="form-row">


                    <div class="form-group col-md-3">
                        <label for="validationTooltip03">Marca:</label>
                        <select id="marcaProductos" name="marca" class="form-control" tabindex="4" required>
                            <option value="">No</option>
                        </select>
                        <div class="invalid-feedback">
                            Falta la marca
                        </div> 
                    </div>

                    <div class="form-group col-md-3">
                        <label for="validationTooltip03">Categor�a:</label>
                        <select name="category" class="form-control" id="category" tabindex="4" required>
                            <option value="">No</option>
                        </select>
                        <div class="invalid-feedback">
                            Escriba una categor�a
                        </div> 
                    </div>
                    
                    <div class="form-group col-md-4">
                        <label for="exampleInputEmail1">Referencia:</label>
                        <input type="text" class="form-control was-validated" id="referencia" name="referencia" placeholder="CC2020" required>

                        <div class="invalid-feedback">
                            Escriba una referencia
                        </div>
                    </div>

                </div>

                <div class="form-row">


                    <div class="form-group col-md-5">
                        <label for="exampleInputEmail1">Precio:</label>
                        <input type="number" class="form-control was-validated" minlength="0" max="10000000" id="price" name="price" placeholder="$" oninput="validity.valid||(value='');" required>

                        <div class="invalid-feedback">
                            Escriba precio v�lido
                        </div>
                    </div>
                    <div class="form-group col-md-5">
                        <label for="exampleInputEmail1">Garant�a:</label>
                        <input type="text" class="form-control was-validated" minlength="2" max="40" id="garantia" name="garantia" placeholder="Garant�a del producto" required>

                        <div class="invalid-feedback">
                            Escriba una garant�a:
                        </div>

                    </div>

                </div>

                
                  <div class="form-row">

                        <div class="form-group col-md-5">
                            <label for="exampleInputEmail1">Color:</label>
                            
                             <select name="colors" class="form-control" id="color" tabindex="4">
                            <option value="">No</option>
                        </select>
                            
                            <div class="invalid-feedback">
                                Seleciona un color
                            </div>
                        </div>

                        <div class="form-group col-md-5">
                            <label for="exampleInputEmail1">Cantidad:</label>
                            <input type="number" class="form-control" minlength="0" max="100000000" id="cantidad" name="cantidad" placeholder="Stock">

                            <div class="invalid-feedback">
                                Escriba precio v�lido
                            </div>
                        </div>
                      
                      <div class="form-group col-md-2">
                            <label for="exampleInputEmail1">Opciones:</label>
                            <button id="addProduct" type="button" class="btn btn-primary hvr-push"><i class="fas fa-plus-square fa-1x"></i> A�adir</button>
                        </div>

                    </div>
                
                <div id="output">
                     
                    
                </div>
                

                <div class="form-group">
                    <label for="exampleFormControlTextarea1">Descripci�n:</label>
                    <textarea class="form-control" id="descrip" name="descrip" rows="3" placeholder="Descripci�n de su producto" minlength="20" maxlength="700" required></textarea>
                    <small id="emailHelp" class="form-text text-muted">Escriba una breve descripci�n del producto. m�ximo un parrafo</small>

                    <div class="invalid-feedback">
                        Escriba una descripci�n de m�nimo 20 car�cteres
                    </div>
                </div>

                <div class="input-field pb-3 pt-2">
                    <label class="active">Imagenes:</label> 
                    <div class="input-images-1"></div>
                    <button id="resets" class="btn btn-info btn-sm text-center" title="Limpiar imagenes"><i class="fas fa-eraser"></i> Borrar</button>
                    <small id="emailHelp" class="form-text text-muted">Puedes subir m�ximo 5 elementos. no pueden superar los 8MB</small>
                </div>


                <div>

                  

                    <div class="form-row">
                        <div class="form-group col-md-6">
                            <label for="exampleInputEmail1">D�as de Env�o:</label>
                            <input type="text" class="form-control was-validated" minlength="2" maxlength="150" id="envios" name="envios" placeholder="D�as de env�o" required>

                            <div class="invalid-feedback">
                                Escriba los d�as de env�o del producto
                            </div>
                        </div>
                        <div class="form-group col-md-6">


                            <label for="exampleInputEmail1">Medidas:</label>
                            <input type="text" class="form-control was-validated" minlength="2" maxlength="150" id="medidas" name="medidas" placeholder="Dimesiones de su producto" required>

                            <div class="invalid-feedback">
                                Escriba las medidas del producto
                            </div>

                        </div>

                    </div>


                    <div class="form-group">
                        <label for="exampleFormControlTextarea1">Ventajas:</label>
                        <textarea class="form-control" id="ventajas" name="ventajas" rows="3" placeholder="Ventajas de su producto" minlength="20" maxlength="700" required></textarea>
                        <small id="emailHelp" class="form-text text-muted">Si su producto tiene informaci�n adicional coloquela en este campo.</small>

                        <div class="invalid-feedback">
                            Escriba las ventajas con por lo menos 10 car�cteres
                        </div>
                    </div>   

                    <div class="form-row">

                        <div class="form-group col-md-6">
                            <label for="exampleInputEmail1">Empaque:</label>
                            <input type="text" class="form-control was-validated" minlength="2" max="40" id="empaque" name="empaque" placeholder="Empaque del producto">

                            <div class="invalid-feedback">
                                Escriba un empaque
                            </div>
                        </div>
                        <div class="form-group col-md-6">
                            <label for="exampleInputEmail1">Embalaje:</label>
                            <input type="text" class="form-control was-validated" minlength="2" max="40" id="embalaje" name="embalaje" placeholder="Embalaje del producto">

                            <div class="invalid-feedback">
                                Escriba un embalaje
                            </div>

                        </div>
                    </div>
                    <div class="form-group pb-2">
                        <button id="send" type="submit" class="btn btn-primary hvr-push float-right"><i class="fas fa-save fa-1x"></i> Guardar</button>
                        <a href="./Products" type="" class="btn btn-warning hvr-push float-left"><i class="fas fa-arrow-left"></i> Volver</a>
                    </div>
            </form>


        </div>
    </div>

    <div id="carga" class="loader loader-bouncing"></div>

</div>
</div>


<%@include file="/views/template/footer.jspf"%>

<script src="./assets/js/project/comprador/ajax.js" charset="utf-8"></script>
<script src="./assets/files/image-uploader.min.js" charset="utf-8"></script>
<script src="./assets/js/project/products/uploadFiles.js" charset="utf-8"></script>
<script src="./assets/js/project/products/multiProducts.js" charset="utf-8"></script>

