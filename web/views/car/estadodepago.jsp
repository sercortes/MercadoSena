<%-- 
    Document   : estadodepago
    Created on : 25/02/2021, 09:46:08 PM
    Author     : sergi
--%>

<!DOCTYPE html>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@include file="/views/template/head.jspf"%>

<c:if test="${ empty USER.idRol}">
    <c:redirect url="./home"/>
</c:if>


<link href="./assets/files/image-uploader.css" rel="stylesheet" type="text/css" async>
<link type="text/css" rel="stylesheet" href="./assets/files/material.css" async>

<%@include file="/views/template/header.jspf"%>
            <!-- Shopping Cart -->
            <section class="shopping-cart">          
                <div class="container" id="container">
                    <div class="block-heading" id="titlepagoa">
                        <svg xmlns="http://www.w3.org/2000/svg" width="35" height="35" fill="currentColor" class="bi bi-check-circle" viewBox="0 0 16 16" style="color: #2bb54a;">
                        <path d="M8 15A7 7 0 1 1 8 1a7 7 0 0 1 0 14zm0 1A8 8 0 1 0 8 0a8 8 0 0 0 0 16z"/>
                        <path d="M10.97 4.97a.235.235 0 0 0-.02.022L7.477 9.417 5.384 7.323a.75.75 0 0 0-1.06 1.06L6.97 11.03a.75.75 0 0 0 1.079-.02l3.992-4.99a.75.75 0 0 0-1.071-1.05z"/>
                        <h2>Pago aprobado</h2></svg>
                    </div>
                    <div class="row justify-content-md-center">
                        <div class="col-4" id="tiketdepago">
                            <div class="summary-a">
                                <h3 style="text-align: center;">Ticket de compra</h3>                              
                                <hr>
                                <p id="letrap2">Datos de la tarjeta.</p>
                                <div class="summary-item">
                                    <span class="text">Nombre:</span><div id="datospago"><span class="datos-compra" id="datos-metodo">${full_name}</span></div>
                                </div>
                                <div class="summary-item">
                                    <span class="text">Metodo de pago:</span><div id="datospago"><span class="datos-compra" id="datos-metodo">${brand}</span></div>
                                </div>
                                <div class="summary-item">
                                    <span class="text">últimos cuatro dígitos:</span><div id="datospago"><span class="datos-compra" id="datos-description">*********${last_four}</span></div>
                                </div>
                                <hr>
                                <p id="letrap2">Detalles de la compra.</p>
                                <div class="summary-item">
                                    <span class="text">ID:</span><div id="datospago"><span class="datos-compra"  id="datos-id">${id}</span></div>
                                </div>
                                <div class="summary-item">
                                    <span class="text">Estado:</span><div id="datospago"><span class="datos-compra" id="datos-estado">${status}</span></div>
                                </div>
                                <div class="summary-item">
                                    <span class="text">Fecha de la compra:</span><div id="datospago"><span class="datos-compra" id="datos-fecha">${created_at}</span></div>
                                </div>
                                <div class="summary-item">
                                    <span class="text">Referencia:</span><div id="datospago"><span class="datos-description" id="datos-description">${reference}</span></div>
                                </div>
                                <hr>
                                <div class="summary-item">
                                    <span class="text">Total:</span><div id="datospago"><span class="datos-compra" id="datos-total">$ ${amount_in_cents}</span></div>
                                </div>

                                <input type="button" class="btn btn-secondary" value="Imprimir" style="margin-top: 5%;width: 100%;" onclick="javascript:window.print()" />
                            </div>
                        </div>
                    </div>
                </div>
            </section>
        </main>
       <%@include file="/views/template/footer.jspf"%>                                                                        
