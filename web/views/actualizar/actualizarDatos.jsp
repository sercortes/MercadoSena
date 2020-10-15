<%-- 
    Document   : actualizarDatos
    Created on : 15/06/2020, 03:40:35 PM
    Author     : DELL
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@include file="/views/template/head.jspf"%>

<c:if test="${ empty USER.idRol}">
    <c:redirect url="./home"/>
</c:if>


<link href="./assets/files/image-uploader.css" rel="stylesheet" type="text/css" async>
<link type="text/css" rel="stylesheet" href="./assets/files/material.css" async>
<link type="text/css" rel="stylesheet" href="./assets/files/css-loader.css" async>

<%@include file="/views/template/header.jspf"%>

<div class="container py-5">


    <div class="row">
        <div class="col-lg-7 mx-auto">
            <div class="bg-white rounded-lg shadow-sm p-5"  style="border: solid 1px rgb(252, 115, 30);">
                <!-- Credit card form tabs -->
                <ul role="tablist" class="nav bg-light nav-pills rounded-pill nav-fill mb-3">
                    <li class="nav-item">
                        <a data-toggle="pill" href="#nav-tab-persona" class="nav-link active rounded-pill" >
                            <i class="fa fa-user" style="color: rgb(252, 115, 30)" ></i>
                            Modificar datos personales
                        </a>
                    </li>
                    <li class="nav-item">
                        <a data-toggle="pill" href="#nav-tab-usuario" class="nav-link rounded-pill" >
                            <i class="fa fa-user-circle" style="color: rgb(252, 115, 30)"></i>
                            Modificar usuario
                        </a>
                    </li>
                    <li class="nav-item ocultar" >
                        <a data-toggle="pill" href="#nav-tab-empresa" class="nav-link rounded-pill" id="opcionEmpresa" >
                            <i class="fa fa-building" style="color: rgb(252, 115, 30)" ></i>
                            Registrar empresa
                        </a>
                    </li>
                </ul>



                <div class="tab-content">

                    <!-- peresona-->
                    <div id="nav-tab-persona" class="tab-pane fade show active">

                        <form class="needs-validation" enctype="multipart/form-data;charset=UTF-8" id="datosActualizarpresona" >
                            <h4 style="color: rgb(252, 115, 30);margin-top: 36px;">Datos personales: </h4>
                            <input type="hidden" name="rolUsuario" id="rolUsuario" value="${USER.idRol}">


                            <div class="contenedorImagen">
                                <div id="previsualizar">
                                    <img id="fotoPerfil" class="fotoPerfil"  src="${USER.persona.urlImg}" width="200px" height="200"/>                   
                                </div>
                                <div class="file">
                                    <p class="texto"><i class="fa fa-file-image" aria-hidden="true"></i></p>
                                    <input type="file" id="fotoUsuario" class="archivo" name="fotoUsuario" value="${USER.persona.urlImg}">
                                </div>
                            </div>

                            <label>Nombre:</label><br>
                            <input type="text" class="form-control was-validated" value="${USER.persona.nombrePer}" minlength="1" maxlength="100" placeholder="Nombre" id="nombreUsuario" name="nombreUsuario" required>
                            <div class="invalid-feedback">
                                Completa este campo correctamente
                            </div>
                            <br> 
                            <label>Apellido:</label><br>
                            <input  value="${USER.persona.apellidoPer}" type="text" placeholder="Apellido" class="form-control was-validated" minlength="1" maxlength="100" id="apellidoUsuario" name="apellidoUsuario" required>
                            <div class="invalid-feedback">
                                Completa este campo correctamente

                            </div>
                            <br>
                            <label>Seleccione su ciudad:</label><br>
                            <select required id="ciudadUsuarioActualizar" name="ciudadUsuarioActualizar" class="form-control">
                                <option value="">Seleccione...</option>
                                <c:forEach items="${listaCiudad}" var="ciudad"> 
                                    <c:if test="${ciudad.idCiudad==USER.persona.idCiudad}">
                                        <option value="${ciudad.idCiudad}" selected>${ciudad.nombreCiudad}</option> 
                                    </c:if>
                                    <c:if test="${ciudad.idCiudad!=USER.persona.idCiudad}">
                                        <option value="${ciudad.idCiudad}">${ciudad.nombreCiudad}</option> 
                                    </c:if>
                                </c:forEach>
                            </select>

                            <hr>

                            <div class="titulo_boton">
                                Información adicional
                                <a style='cursor: pointer;' onClick="muestra_oculta('contenido')" title="" class="boton_mostrar">Mostrar / Ocultar</a>
                            </div>
                            <br>
                            <div id="contenido">
                                   
                                <label>Tipo documento:</label><br>
                                <div id="tipoDoc">

                                    <input type="hidden" value="${USER.persona.idTipoDoc}" id="tipoDocUsusario">
                                </div>
                                <div class="invalid-feedback">
                                    Completa este campo
                                </div>
                                <br>
                                
                                <label>Documento:</label><br>
                                <input  value="${USER.persona.numeroDocPer}" type="number" placeholder="Documento" class="form-control was-validated" id="documentoUsuario" minlength="1" maxlength="50" name="documentoUsuario">
                                <div class="invalid-feedback">
                                    Completa este campo correctamente
                                </div><br> 
                                <label>Seleccione su género:</label><br>
                                <div id="genero">
                                    <input type="hidden" value="${USER.persona.idGenero}" id="generoUsusario">

                                </div>
                                <div class="invalid-feedback">
                                    Completa este campo
                                </div>
                                <br> 
                                <label>Celular:</label><br>
                                <input  value="${USER.persona.numCelularPer}" type="number" placeholder="Número" class="form-control was-validated" minlength="8" maxlength="50" id="celularUsuario" name="celularUsuario">
                                <div class="invalid-feedback">
                                    Completa este campo correctamente
                                </div><br> 
                                <label>Teléfono:</label><br>
                                <input  value="${USER.persona.telPer}" type="text" placeholder="Opcional" class="form-control was-validated" id="telefonoUsuario"  maxlength="50" name="telefonoUsuario" >
                                <div class="invalid-feedback">
                                    Completa este campo correctamente
                                </div><br> 
                                <label>Dirección:</label><br>
                                <input  value="${USER.persona.direccionPer}" type="text" placeholder="Opcional" class="form-control was-validated"  maxlength="100" id="direccionUsuario" name="direccionUsuario" >
                                <div class="invalid-feedback">
                                    Completa este campo correctamente
                                </div><br> 

                            </div>
                            <br>
                            <br>
                            <button class="botonRegistro" id="actualizarPersona">Actualizar</button>

                        </form>
                    </div>
                    <!-- fin -->

                    <!-- usuario -->
                    <div id="nav-tab-usuario" class="tab-pane fade">
                        <form id="actualizarUsuario" class="needs-validation">
                            <h4 style="color: rgb(252, 115, 30);margin-top: 36px;">Usuario: </h4>
                            <label>Correo:</label><br>
                            <input disabled type="email" value="${USER.correoUsu}" placeholder="Correo" class="form-control was-validated" minlength="1" maxlength="100" id="correoUsuario" name="correoUsuario" required >
                            <div class="invalid-feedback">
                                Completa este campo correctamente
                            </div><br>
                            <label>Escriba su nueva contraseña:</label><br>
                            <input placeholder="Mínimo 10 Caráteres" type="password" id="clave1" minlength="10" class="form-control was-validated" name="clave1" required onkeyup="validarClave()"><span class="valid invalido" id="spValidar1"></span><br>
                            <label>Confirme su contraseña:</label><br>
                            <input placeholder="Mínimo 10 Caráteres" type="password" id="clave2" minlength="10" class="form-control was-validated" name="clave2" required onkeyup="validarClave()"><span class="valid invalido" id="spValidar2"></span>

                            <br>
                            <button class="botonRegistro" id="btnActualizarUsuario">Actualizar</button>
                        </form>
                    </div>
                    <!-- fin -->

                    <!-- empresa -->
                    <div id="nav-tab-empresa" class="tab-pane fade ocultar">
                        <h4 style="color: rgb(252, 115, 30);margin-top: 36px;">Empresa: </h4>
                        <form id="actualizarEmpresa" class="needs-validation" >
                            <input type="hidden" name="esEmpresa" id="esEmpresa" value="${USER.empresa.esEmpresa}">
                            <label>Nombre:</label><br>
                            <input value="${USER.empresa.nombreEmpresa}" type="text" class="form-control was-validated" minlength="1" maxlength="100" placeholder="Nombre" id="nombreEmpresa" name="nombreEmpresa" required>
                            <div class="invalid-feedback">
                                Completa este campo correctamente
                            </div>
                            <br> 

                            <label>Celular:</label><br>
                            <input value="${USER.empresa.celEmpresa}" type="text" placeholder="Número" class="form-control was-validated" minlength="8" maxlength="50" id="celularEmpresa" name="celularEmpresa" required>
                            <div class="invalid-feedback">
                                Completa este campo correctamente
                            </div><br> 
                            <label>Teléfono:</label><br>
                            <input type="number" placeholder="Número" class="form-control was-validated" id="telefonoEmpresa"  maxlength="50" name="telefonoEmpresa" required value="${USER.empresa.telEmpresa}" >
                            <div class="invalid-feedback">
                                Completa este campo correctamente
                            </div><br> 
                            <label>Correo:</label><br>
                            <input type="text" placeholder="Correo" class="form-control was-validated"  maxlength="100" id="correoEmpresa" name="correoEmpresa" required value="${USER.empresa.correoEmpresa}" >
                            <div class="invalid-feedback">
                                Completa este campo correctamente
                            </div><br> 
                            <label>Seleccione su ciudad:</label><br>
                            <div id='ciudadEmpresa'>

                                <input type="hidden" id="ciudEmpresaActualizar" value="${USER.empresa.idCiudad}">

                            </div><br>
                            <label>Dirección:</label><br>
                            <input type="text" placeholder="Dirección" class="form-control was-validated" minlength="1" maxlength="100" id="direccionEmpresa" name="direccionEmpresa" required value="${USER.empresa.dirEmpresa}">
                            <div class="invalid-feedback">
                                Completa este campo correctamente

                            </div>
                            <br> 

                            <button class="botonRegistro" id="btnActualizarEmpresa">Registrar</button>

                        </form>
                    </div>
                    <!-- fin -->

                </div>
            </div>
        </div>
    </div>


    <div id="cargando" class="loader loader-bouncing"></div>

</div>
</div>
<script src="./assets/js/project/comprador/ajax.js" charset="utf-8"></script>

<%@include file="/views/template/footer.jspf"%>
<script src="./assets/js/project/actualizar/actualizarUsuEmp.js" type="text/javascript"></script>
<script src="./assets/js/project/actualizar/validarImagen.js" type="text/javascript"></script>
