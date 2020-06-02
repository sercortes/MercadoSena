

<%@include file="/views/template/head.jspf"%>
<link type="text/css" rel="stylesheet" href="./assets/files/css-loader.css" async>
<%@include file="/views/template/header.jspf"%>

<%@include file="/views/init/carusel.jspf"%>



<div class="container cuerpo pb-4 pt-4" >
    <div class="row h-100 align-items-right" >
        <div class="col-6 text-center">

        </div>
        <div class="col-6">

            <h1 class="font-weight-bold pt-4">¿Qué necesitas?</h1>
            <p class="lead">Tenemos los mejores productos</p>
            <a href="#" type="button" class="btn btn-primary btn-lg">detalles productos</a>

            <a href="./Searching..." type="button" class="btn btn-primary btn-lg">buscador</a>

                       <div id="carga" class="loader loader-bouncing"></div>

        </div>
    </div>
</div>




<%@include file="/views/login/login.jspf"%>
<%@include file="/views/registro/registro.jspf"%>

<%@include file="/views/template/footer.jspf"%>


