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

<div class="container-fluid pl-0 pr-0" id="container-wrapper" style="min-height: 1000px;">

    <div class="container">
        <div class="row p-2">
            <div class="col-lg-12">
                <h3 class="titulos ">Sus nuevos mensajes</h3>
                <di id="preguntas">
                    
                </di>
                
            </div>
        </div>    
    </div>


        <div id="cargas" class="loader loader-bouncing"></div>

    </div>
</div>




<%@include file="/views/template/footer.jspf"%>


<script src="./assets/js/pagination/pager.js" charset="utf-8"></script>
<script src="./assets/js/project/preguntas/preguntas.js" charset="utf-8"></script>







