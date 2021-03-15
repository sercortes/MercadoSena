<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@include file="/views/template/head.jspf"%>

<c:if test="${ empty USER.idRol}">
    <c:redirect url="./home"/>
</c:if>


<link href="./assets/files/image-uploader.css" rel="stylesheet" type="text/css" async>
<link type="text/css" rel="stylesheet" href="./assets/files/material.css" async>

<%@include file="/views/template/header.jspf"%>

<div class="container">
    <br>
    <div class="block-heading">
        <h2 class="text-center">PAGO</h2>
        <br>
    </div>
    <div class="row">
        <div>
            <div class="col-sm-12">
                <div class="card">
                    <div  class="card-body"   style="box-shadow: 2px 2px 5px #999; padding: 15px 15px 15px 15px;">
                        <div class="row">
                            <div class="col-3" id="imgpag">
                                <div class="col" id="imgcard">
                                    <img src="https://cdn-products.eneba.com/payments/v2/color/checkout.svg" alt="Tarjeta de crédito">
                                </div>
                            </div>
                            <div class="col-7">
                                <label class="form-check-label" for="flexRadioDefault1">
                                    <h5 class="card-title" >Tarjeta de crédito</h5>
                                    <p class="card-text" id="textpag">Paga con tarjeta de crédito Maestro, Mastercard o Visa. </p>
                                </label>
                            </div>
                            <div class="col" style="margin-top: 3%;">
                                <div class="form-check">
                                    <input class="form-check-input" type="radio"  name="flexRadioDefault" id="flexRadioDefault1" >
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <br>
            <div class="col-sm-12" >
                <div class="card" style="margin-bottom: 5%;">
                    <div class="card-body"  style="box-shadow: 1px 1px 5px #999; padding: 15px 15px 15px 15px;">
                        <div class="row">
                            <div class="col-3" id="imgpag">
                                <div class="col" id="imgcard2">
                                    <img width="65" height="50" src="./assets/images/icons/BotonPSE.png" alt="Transferencia bancaria" style="margin-left: 10%;margin-top: 4%;">
                                </div>
                            </div>
                            <div class="col-7">
                                <label class="form-check-label" for="flexRadioDefault2">
                                    <h5 class="card-title" >Transferencia bancaria</h5>
                                    <p class="card-text" id="textpag">Paga mediante transferencias bancarias locales. </p>
                                </label>
                            </div>
                            <div class="col" style="margin-top: 3%;">
                                <div class="form-check">
                                    <input class="form-check-input" type="radio" name="flexRadioDefault" id="flexRadioDefault2">
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <div class="col-sm-8" style="max-width: 640px; margin-bottom: 5%;">
            <div class="row no-gutters" style="box-shadow: 1px 1px 5px #999;">
                <div class="table-responsive">
                    <table class="table table-striped">
                        <thead class="bg-primary">
                            <tr>
                                <th scope="col" class="border-0">
                                    <div class="p-1 px-3 text-uppercase">Producto</div>
                                </th>
                                <th scope="col" class="border-0">
                                    <div class="p-1 px-3 text-uppercase">Nombre</div>
                                </th>
                                <th scope="col" class="border-0">
                                    <div class="py-1 text-uppercase">Color</div>
                                </th>
                                <th scope="col" class="border-0">
                                    <div class="py-1 text-uppercase">Cantidad</div>
                                </th>
                                <th scope="col" class="border-0">
                                    <div class="py-1 text-uppercase">Precio</div>
                                </th>
                            </tr>
                        </thead>
                        <tbody id="cards">

                        </tbody>
                    </table>
                    <div class="container">
                        <div class="row">
                            <div id="totals" class="col-12">

                            </div>
                        </div>
                    </div>
                </div>
            </div>

        </div>

    </div>

    <!-- Modal Tarjeta De Credito-->
    <div class="modal fade" id="ModalTarjeta" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
        <div class="modal-dialog" role="document">
            <div class="modal-content">
                <div class="modal-header">
                    <h4 class="modal-title" id="exampleModalLabel">Tarjeta de credito</h4>
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                        <span aria-hidden="true">&times;</span>
                    </button>
                </div>
                <hr style="margin-top: 2px; margin-bottom: 2px;">
                <div class="modal-body">
                    <form action="process_payment" method="post"  onsubmit="return checkSubmit();">
                        <input name="accionT" value="Tarjetadecredito" type="hidden" />
                        <input id="ventaId" name="ventaId" value="0" type="hidden" />

                        <div class="row">
                            <div class="form-group col">
                                <label for="email">Correo electrónico</label> <input id="emails" name="emails" type="email" class="form-control" required>
                            </div>
                        </div>
                        <p class="card-text" id="textpag">Datos de la tarjeta de credito</p>
                        <div class="container" id="container-tarjeta">
                            <div class="row">
                                <div class="form-group col-8">
                                    <label>Nombre del titular de la tarjeta</label>
                                    <input class="form-control" type="text" id="cardhold" name="cardhold" required>
                                </div>
                                <div class="form-group col-sm-4">
                                    <label for="">Fecha de caducidad</label>
                                    <div class="input-group expiration-date">
                                        <input type="number" class="form-control" placeholder="MM"  name="cardExpirationMonth"
                                               id="cardExpirationMonth" data-checkout="cardExpirationMonth"
                                               onselectstart="return false" onpaste="return false"
                                               onCopy="return false" onCut="return false"
                                               onDrag="return false" onDrop="return false" autocomplete=off>
                                        <span class="date-separator">/</span> <input type="number"
                                                                                     class="form-control" placeholder="YY" name="cardExpirationYear" id="cardExpirationYear"
                                                                                     data-checkout="cardExpirationYear"
                                                                                     onselectstart="return false" onpaste="return false"
                                                                                     onCopy="return false" onCut="return false"
                                                                                     onDrag="return false" onDrop="return false" autocomplete=off>
                                    </div>
                                </div>
                            </div>
                            <div class="row">
                                <div class="form-group col-8">
                                    <label>Número de tarjeta</label>
                                    <input class="form-control" type="number" id="cardNumber" name="cardNumber" required>
                                </div>
                                <div class="form-group col-sm-4">
                                    <label>CVV</label>
                                    <input class="form-control" type="number" id="CVV" name="CVV" required>
                                </div>  
                            </div>
                        </div>
                        <br>
                        <div class="row" id="TCARD">
                            <input type="checkbox" class="form-check-input" id="checkPSE">
                            <label class="form-check-label" for="exampleCheck1"><p id="textpag">Al hacer clic en "Comprar", admito que he leído y aceptado los Términos y condiciones</p></label>
                        </div>
                        <br>
                        <div class="modal-footer">
                            <input type="submit" id="pagotarjeta" class="btn btn-primary" value="Comprar">
                        </div>
                    </form>
                </div>

            </div>
        </div>
    </div>
    <!-- Modal PSE -->
    <div class="modal fade" id="ModalPSE" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
        <div class="modal-dialog" role="document">
            <div class="modal-content">
                <div class="modal-header">
                    <h4 class="modal-title" id="exampleModalLabel">PSE</h4>
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                        <span aria-hidden="true">&times;</span>
                    </button>
                </div>
                <hr style="margin-top: 2px; margin-bottom: 2px;">
                <div class="modal-body">
                    <form action="process_payment" method="post" onsubmit="return checkSubmit2();">
                        <input name="accionT" value="pagarpse" type="hidden" />
                        <input id="ventaId" name="ventaId" value="0" type="hidden" />

                        <div class="row">
                            <div class="form-group col">
                                <label for="email">Correo electrónico</label> <input id="email" name="email" type="email" class="form-control" required>
                            </div>
                        </div>
                        <div class="row">
                            <div class="form-group col">
                                <label for="Nombre">Nombre</label> <input id="Nombre" name="Nombre" type="text" class="form-control col" required>
                            </div>
                            <div class="form-group col">
                                <label for="Apellido">Apellido</label> <input id="Apellido" name="Apellido" type="text" class="form-control col" required>
                            </div>
                        </div>
                        <div class="row">
                            <div class="form-group col">
                                <label for="docType">Tipo de Documento</label>
                                <select id="docTypes" name="docTypes" data-checkout="docTypes" type="text"class="form-control">
                                    <option value="CC">C.C</option>
                                    <option value="CE">C.E</option>
                                </select>
                            </div>
                            <div class="form-group col">
                                <label for="docNumber">Número del Documento</label> <input
                                    id="docNumbers" name="docNumbers" data-checkout="docNumbers"
                                    type="number" class="form-control" />
                            </div>
                        </div>
                        <div class="divselect">
                            <label for="docType">¿Cual es tu banco?</label>
                            <select id="selectdebanco" name="selectdebanco" class="form-control"></select>
                            <br>
                            <label for="docType">Tipo de persona</label>
                            <select id="TPersona"  class="form-control" name="TPersona">
                                <option value="0">Persona natural</option>   
                                <option value="1">Persona jurídica</option>  
                            </select>
                        </div> 
                        <div class="row" id="TPSE">
                            <input type="checkbox" class="form-check-input" id="checkPSE">
                            <label class="form-check-label" for="exampleCheck1"><p id="textpagt">Al hacer clic en "Comprar", admito que he leído y aceptado los Términos y condiciones</p></label>
                        </div>
                        <div class="modal-footer">
                            <input type="submit" id="pagoPSE" class="btn btn-primary" value="Comprar" >
                        </div>
                    </form>
                </div>

            </div>
        </div>
    </div>

</div>
<%@include file="/views/template/footer.jspf"%>