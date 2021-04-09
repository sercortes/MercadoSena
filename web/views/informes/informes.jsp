<%-- 
    Document   : informes
    Created on : 9/07/2020, 02:56:08 PM
    Author     : DELL dads
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@include file="/views/template/head.jspf"%>

<c:if test="${USER.idRol != 3 || empty USER.idRol}">
    <c:redirect url="./home"/>
</c:if>

<link href="./assets/files/image-uploader.css" rel="stylesheet" type="text/css" async>
<link type="text/css" rel="stylesheet" href="./assets/files/material.css" async>
<link type="text/css" rel="stylesheet" href="./assets/files/css-loader.css" async>
<link href="./assets/styles/bootstrap4/bootstrap-datepicker.css" rel="stylesheet" type="text/css"/>

<%@include file="/views/template/header.jspf"%>

<%@include file="/views/searching/buscador.jspf"%>

<div class="container py-5">
    <header class="text-center">
        <div class="col-lg-9 mx-auto text-black text-center">
            <h1 class="display-4">Generar informes</h1>     
        </div>

    </header>
</div>

<div class="container">
    <div class="row">
        <div class="col-md-10 mx-auto">

                <h3>Completa los datos correspondientes para el tipo de informe que desea:</h3>
                <div class="mb-3">
                    <label>Tipo de informe que desea:</label>
                    <select id="tipoInforme" class="form-control">
                        <option value="1">Ventas Vendedor</option>
                        <option value="2">Ventas Cliente</option>
                        <option value="3">Total</option>
                    </select> 
                </div>
                
                <div class="row">
                <div class="form-group mb-3 col-md-6">
                    <label>Fecha inicial o día para el que desea el informe:</label>
                    <div class="datepicker date input-group p-0 shadow-sm">

                        <input type="text" placeholder="Fecha" class="form-control py-4 px-4" id="fechaInicial" onchange="" onkeyup="return false" onclick="">
                        <div class="input-group-append colorCursor"><span class="input-group-text px-4"><i class="fa fa-calendar colorCursor"></i></span></div>
                    </div>
                    <span class="invalido" id="avisoFechaIni"></span>
                </div>
                <div class="form-group mb-3 col-md-6">
                    <label>Fecha final (complete sólo si desea un rango):</label>
                    <div class="datepicker date input-group p-0 shadow-sm">

                        <input type="text" placeholder="fecha final" class="form-control py-4 px-4" id="fechaFinal" onchange="" onkeyup="return false">
                        <div class="input-group-append colorCursor"><span class="input-group-text px-4"><i class="fa fa-calendar colorCursor"></i></span></div>
                    </div>
                </div>
                </div>
                
                <div style="width: 100%;text-align: center;">
                    <input id="generarInfo" type="submit" value="Generar" class="btn btn-primary">
                </div>





        </div>
    </div>
</div>



<hr>


<div id="cargas" class="loader loader-bouncing"></div>





<%@include file="/views/informes/modalInforme.jspf" %>
<%@include file="/views/template/footer.jspf"%>
<script src="./assets/js/project/chart/Chart.js" type="text/javascript"></script>
<script src="./assets/js/project/informes/informes.js" type="text/javascript"></script>
<script src="./assets/styles/bootstrap4/bootstrap-datepicker.min.js" type="text/javascript"></script>
<script src="./assets/js/project/informes/informesHtml.js" type="text/javascript"></script>

<script src="./assets/js/kendoJs/jszip.min.js" type="text/javascript" charset="utf-8"></script>
<script src="./assets/js/kendoJs/kendo.all.min.js" type="text/javascript" charset="utf-8"></script>
<script src="./assets/js/project/informes/genPdf.js" type="text/javascript" charset="utf-8"></script>



