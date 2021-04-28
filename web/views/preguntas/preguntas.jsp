
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@include file="/views/template/head.jspf"%>

<c:if test="${ empty USER.idRol}">
    <c:redirect url="./home"/>
</c:if>

<%@include file="/views/template/header.jspf"%>

<div class="container-fluid pl-0 pr-0" id="container-wrapper">

    <div class="row" id="fondo-cool">
        <div class="col-lg-11 mx-auto">
            <!-- FIRST EXAMPLE ===================================-->
            <div class="row pt-4" id="">

                <section class="py-5 header">
                    <div class="container py-4 shadow-sm">

                        <div class="row rounded-lg overflow-hidden">
                            <!-- Users box-->
                            <div class="col-5 px-0">
                                <div class="bg-white">

                                    <div class="bg-gray px-4 py-2 bg-light">
                                        <p class="h5 mb-0 py-1 text-muted">Mensajes</p>
                                    </div>

                                    <div class="messages-box">
                                        <div id="personass" class="list-group rounded-0">

                                        </div>
                                    </div>
                                </div>
                            </div>
                            <!-- Chat Box-->
                            <div class="col-7 px-0">
                                <div class="px-4 py-5 chat-box bg-white" id="chatPreguntas">


                                </div>

                                <!-- Typing area -->
                                <form action="#" class="bg-primary">
                                    <div class="input-group text-muted border">
                                        <input id="respuesta" type="text" placeholder="Escribe un mensaje" aria-describedby="button-addon2" class="form-control rounded-0 border-0 py-4">
                                        <div class="input-group-append">
                                            <button id="button-addon2" type="submit" class="btn btn-link"> <i class="fa fa-paper-plane"></i></button>
                                        </div>
                                    </div>
                                </form>

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

<c:if test="${USER.idRol == 3}">
    <script src="./assets/js/project/chat/chatVendedor/charVendedor.js" charset="utf-8"></script>
</c:if>

<c:if test="${USER.idRol == 2}">
    <script src="./assets/js/project/chat/chatComprador/chatComprador.js" charset="utf-8"></script>
</c:if>







