<%-- 
    Document   : newjsp
    Created on : 5/06/2020, 08:32:50 PM
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

<div class="container-fluid pl-0 pr-0" id="container-wrapper">

    <div class="row" id="fondo-cool">
        <div class="col-lg-11 mx-auto">
            <!-- FIRST EXAMPLE ===================================-->
            <div class="row pt-4" id="">

                <section class="py-5 header">
                    <div class="container py-4">


                        <div class="row">
                            <div class="col-md-3" >
                                <!-- Tabs nav -->
                                <div class="nav flex-column nav-pills nav-pills-custom" id="v-pills-tab" role="tablist" aria-orientation="vertical">
                                    <a onclick="consultarPreguntas()" class="nav-link mb-3 p-3 shadow active ocultarRespuesta" id="v-pills-home-tab" data-toggle="pill" href="#v-pills-home" role="tab" aria-controls="v-pills-home" aria-selected="true">
                                        <i class="fa fa-user-circle-o mr-2"></i>
                                        <span class="font-weight-bold small text-uppercase" >Preguntas</span></a>

                                        <a  onclick="consultarRespuestas()" class="nav-link mb-3 p-3 shadow ocultarRespuesta" id="v-pills-profile-tab" data-toggle="pill" href="#v-pills-profile" role="tab" aria-controls="v-pills-profile" aria-selected="false">
                                        <i class="fa fa-calendar-minus-o mr-2"></i>
                                        <span class="font-weight-bold small text-uppercase">Respuestas </span><b id="noRespuestas" style="font-size: xx-small;" class="notificaciones2"></b></a>
                                       

                                </div>
                            </div>


                            <div class="col-md-9">
                                <!-- Tabs content -->
                                <div class="tab-content" id="v-pills-tabContent">
                                    <div class="tab-pane fade shadow rounded bg-white show active p-5 ocultarRespuesta" id="v-pills-home" role="tabpanel" aria-labelledby="v-pills-home-tab">
                                        <h4 class="font-italic mb-4">Preguntas</h4>
                                        <di id="preguntas" class="preguntas">
                                            
                                        </di>
                                    </div>

                                    <div class="tab-pane fade shadow rounded bg-white p-5 mostrarRespuesta" id="v-pills-profile" role="tabpanel" aria-labelledby="v-pills-profile-tab">
                                        <h4 class="font-italic mb-4">Respuestas</h4>
                                         <di id="preguntas" class="preguntas">
                                            
                                        </di>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </section>

            </div>
        </div>
    </div>



    <div id="cargas" class="loader loader-bouncing"></div>

</div>
</div>




<%@include file="/views/preguntas/modalVerProductoPregunta.jspf"%>
<%@include file="/views/template/footer.jspf"%>


<script src="./assets/js/pagination/pager.js" charset="utf-8"></script>
<script src="./assets/js/project/preguntas/preguntas.js" charset="utf-8"></script>







