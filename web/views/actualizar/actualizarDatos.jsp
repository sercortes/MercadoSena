

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@include file="/views/template/head.jspf"%>

<c:if test="${ empty USER.idRol}">
    <c:redirect url="./home"/>
</c:if>

<%@include file="/views/template/header.jspf"%>

<div class="container py-5">


    <div class="row">
        <div class="col-lg-7 mx-auto">
            <div class="bg-white rounded-lg shadow-sm p-5"  style="border: solid 1px rgb(238 82 68);">
                <!-- Credit card form tabs -->
                <ul role="tablist" class="nav bg-light nav-pills rounded-pill nav-fill mb-3">
                    <li class="nav-item">
                        <a data-toggle="pill" href="#nav-tab-persona" class="nav-link active rounded-pill" >
                            <!--<i class="fa fa-user" style="color: rgb(252, 115, 30)" ></i>-->
                            Modificar datos personales
                        </a>
                    </li>
                    <li class="nav-item">
                        <a data-toggle="pill" href="#nav-tab-usuario" class="nav-link rounded-pill" >
                           <!-- <i class="fa fa-user-circle" style="color: rgb(252, 115, 30)"></i>-->
                            Modificar contrase�a
                        </a>
                    </li>
                </ul>



                <div class="tab-content">

                    <!-- peresona-->
                    <div id="nav-tab-persona" class="tab-pane fade show active">

                        <form class="needs-validation" enctype="multipart/form-data;charset=UTF-8" id="datosActualizarpresona" >
                            <h4 style="color: rgb(238 82 68);margin-top: 36px;">Datos personales: </h4>
                            <input type="hidden" name="rolUsuario" id="rolUsuario" value="${USER.idRol}">


                            <div class="contenedorImagen">
                                <div id="previsualizar">
                                    <img id="fotoPerfil" class="fotoPerfil"  src="${USER.persona.urlImg}?${USER.persona.modifiData}" width="200px" height="200"/>                   
                                </div>
                                <div class="file">
                                    <p class="texto"><i class="fa fa-file-image" aria-hidden="true"></i></p>
                                    <input type="file" id="fotoUsuario" class="archivo" name="fotoUsuario" value="${USER.persona.urlImg}">
                                </div>
                            </div>

                            <label>Nombre:</label><br>
                            <input type="text" class="form-control was-validated" pattern="[a-zA-Z-������ ]{3,25}" value="${USER.persona.nombrePer}" minlength="1" maxlength="100" placeholder="Nombre" id="nombreUsuario" name="nombreUsuario" required>
                            <div class="invalid-feedback">
                                Completa este campo correctamente
                            </div>
                            <br> 
                            <label>Apellido:</label><br>
                            <input  value="${USER.persona.apellidoPer}" type="text" pattern="[a-zA-Z-������ ]{3,25}" placeholder="Apellido" class="form-control was-validated" minlength="1" maxlength="100" id="apellidoUsuario" name="apellidoUsuario" required>
                            <div class="invalid-feedback">
                                Completa este campo correctamente

                            </div>
                            <br>
                            <label>Seleccione su ciudad:</label><br>
                           
                            <input type="hidden" value="${USER.persona.idCiudad}" id="idCiudadd">
                                     <select id="ciudadUsuarioActualizar" name="ciudadUsuarioActualizar" class="form-control" tabindex="4" required>
                                    </select>

                            <hr>

                            <div class="titulo_boton">
                                Informaci�n adicional
                                <a style='cursor: pointer;' onClick="muestra_oculta('contenido')" title="" class="boton_mostrar">Mostrar / Ocultar</a>
                            </div>
                            <br>
                            <div id="contenido">
                                <label>Seleccione su g�nero:</label><br>
                                    <input type="hidden" value="${USER.persona.idGenero}" id="generoUsusario">
                                     <select id="generoUsuario" name="generoUsuario" class="form-control" tabindex="4" required>
                                    </select>
                                <div class="invalid-feedback">
                                    Completa este campo
                                </div>
                                <br> 
                                <label>Celular:</label><br>
                                <input  value="${USER.persona.numCelularPer}" type="text" pattern="[0-9]{5,13}" placeholder="N�mero" class="form-control was-validated" id="celularUsuario" name="celularUsuario">
                                <div class="invalid-feedback">
                                    Completa este campo correctamente
                                </div><br> 
                                <label>Tel�fono:</label><br>
                                <input  value="${USER.persona.telPer}" type="text" placeholder="Opcional" pattern="[0-9]{5,13}" class="form-control was-validated" id="telefonoUsuario"  name="telefonoUsuario" >
                                <div class="invalid-feedback">
                                    Completa este campo correctamente
                                </div><br> 
                                <label>Direcci�n:</label><br>
                                <input  value="${USER.persona.direccionPer}" type="text" placeholder="Opcional" class="form-control was-validated"  maxlength="100" id="direccionUsuario" name="direccionUsuario" >
                                <div class="invalid-feedback">
                                    Completa este campo correctamente
                                </div><br> 

                            </div>
                            <br>
                            <br>
                            <button class="btn btn-primary" id="actualizarPersona" style="height: 45px;">Actualizar</button>

                        </form>
                    </div>
                    <!-- fin -->

                    <!-- usuario -->
                    <div id="nav-tab-usuario" class="tab-pane fade">
                        <form id="actualizarUsuario" class="needs-validation">
                            <h4 style="color: rgb(238 82 68);margin-top: 36px;">Nueva contrase�a: </h4>
                            <!--<label>Correo:</label><br>-->
                            <input disabled type="hidden" value="${USER.correoUsu}" placeholder="Correo" class="form-control was-validated" minlength="1" maxlength="100" id="correoUsuario" name="correoUsuario" required >
                            <div class="invalid-feedback">
                                Completa este campo correctamente
                            </div><br>
                            <label>Escribe tu contrase�a actual:</label><br>
                            <input placeholder="*********************" type="password" id="clave0" minlength="10" class="form-control was-validated" name="clave0" required onkeyup="validarClave()"><span class="valid invalido" id="spValidar1"></span><br>
                            <label>Escriba su nueva contrase�a:</label><br>
                            <input placeholder="M�nimo 10 Car�teres" type="password" id="clave1" minlength="10" class="form-control was-validated" name="clave1" required onkeyup="validarClave()"><span class="valid invalido" id="spValidar1"></span><br>
                            <label>Confirme su contrase�a:</label><br>
                            <input placeholder="M�nimo 10 Car�teres" type="password" id="clave2" minlength="10" class="form-control was-validated" name="clave2" required onkeyup="validarClave()"><span class="valid invalido" id="spValidar2"></span>

                            <br>
                            <button class="btn btn-primary" id="btnActualizarUsuario" style="height: 45px;">Actualizar</button>
                        </form>
                    </div>
                    <!-- fin -->

                </div>
            </div>
        </div>
    </div>

     <div id="cargas" class="loader loader-bouncing"></div>

</div>
</div>
<script src="./assets/js/project/comprador/ajax.js" charset="utf-8"></script>

<%@include file="/views/template/footer.jspf"%>
<script src="./assets/js/project/actualizar/actualizarUsuEmp.js" type="text/javascript"></script>
<script src="./assets/js/project/actualizar/validarImagen.js" type="text/javascript"></script>
