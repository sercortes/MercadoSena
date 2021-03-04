<%-- 
    Document   : pagoscar
    Created on : 23/02/2021, 11:45:49 AM
    Author     : sergi
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <title>Proceso de compra | CarWay</title>
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <link rel="stylesheet" type="text/css"
              href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css">
        <link rel="stylesheet" type="text/css" href="./assets/styles/index.css">
        <script
        src="https://secure.mlstatic.com/sdk/javascript/v1/mercadopago.js"></script>
        <script
        src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>
        <script src="https://secure.mlstatic.com/sdk/javascript/v1/mercadopago.js"></script>


        <script type="text/javascript" src="./assets/js/project/carrito/index.js" defer></script>
    </head>
    <body>
        <main>    
            <!-- Shopping Cart -->
            <section class="shopping-cart dark">          
                <div class="container" id="container">
                    <div class="block-heading">
                        <h2>¿Cómo quieres pagar?</h2>
                    </div>
                    <div class="product">
                        <div class="info">
                            <div class="product-details">
                                <div class="row justify-content-md-center">
                                    <div class="col-md-4 product-detail">
                                        <span id="unit-price" style="display: none">${valor}</span>
                                    </div>
                                    <div class="col-md-3 product-detail">
                                        <input type="hidden" id="quantity" class="form-control">
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="row justify-content-md-center">
                        <div class="col-4">
                            <div class="summary">
                                <img src="https://cdn-products.eneba.com/payments/v2/color/checkout.svg" alt="Tarjeta de crédito" id="imgtarjeta">
                                <h3>Tarjeta de crédito</h3>
                                <p id="letrap">Paga con tarjeta de crédito Maestro, Mastercard o Visa</p>
                                <div class="summary-item">
                                    <span class="text">Total</span><span class="price"
                                                                         id="cart-total"></span>
                                    <hr>
                                    <button class="btn btn-danger btn-lg btn-block"
                                            id="checkout-btn">Ir a pagar</button>
                                </div>
                            </div>
                        </div>
                        <div class="col-4">
                            <div class="summarys">
                                <img src="https://cdn-products.eneba.com/payments/v2/color/dlocal_pse.svg" alt="Transferencia bancaria" id="imgtarjeta2"> 
                                <h3 class="summrys-h3">Transferencia desde PSE</h3>
                                <p id="letrap">Paga mediante transferencias bancarias locales.</p>
                                <div class="summarys-item">
                                    <span class="text">Total</span><span class="price"
                                                                         id="carts-total"></span>
                                    <hr>
                                    <button class="btn btn-danger btn-lg btn-block"
                                            id="checkouts-btn" >Pagar con PSE</button>
                                </div>
                            </div>  
                        </div>
                    </div>
                </div>
            </section>
            <!-- Payment -->
            <section class=" payment-form dark">
                <div class="container_payment">
                    <div class="block-heading">
                        <h2>Pago con tarjeta</h2>
                    </div>
                    <div class="form-payment">
                        <div class="products" style="display: none">
                            <h2 class="title">
                                Resumen
                                </h3>
                                <div class="item">
                                    <span class="price" id="summary-price"></span>
                                    <p class="item-name">
                                        Book x <span id="summary-quantity"></span>
                                    </p>
                                </div>
                                <div class="total">
                                    Total<span class="price" id="summary-total"></span>
                                </div>
                        </div>
                        <div class="payment-details">
                            <form action="process_payment" method="post" id="paymentForm">
                                <input name="accionT" value="Tarjetadecredito" type="hidden" />
                                <h3 class="title">Detalles del comprador</h3>
                                <div class="row">
                                    <div class="form-group col">
                                        <label for="email">E-Mail</label> <input id="email"
                                                                                 name="email" type="email" class="form-control" required></select>
                                    </div>
                                </div>
                                <div class="row">
                                    <div class="form-group col-sm-5">
                                        <label for="docType">Tipo de Documento</label> <select id="docType"
                                                                                               name="docType" data-checkout="docType" type="text"
                                                                                               class="form-control"></select>
                                    </div>
                                    <div class="form-group col-sm-7">
                                        <label for="docNumber">Número del Documento</label> <input
                                            id="docNumber" name="docNumber" data-checkout="docNumber"
                                            type="number" class="form-control" />
                                    </div>
                                </div>
                                <br>
                                <h3 class="title">Detalles de tarjeta</h3>
                                <div class="row">
                                    <div class="form-group col-sm-8">
                                        <label for="cardholderName">Titular de la tarjeta</label> <input
                                            id="cardholderName" data-checkout="cardholderName" type="text"
                                            class="form-control">
                                    </div>
                                    <div class="form-group col-sm-4">
                                        <label for="">Fecha de caducidad</label>
                                        <div class="input-group expiration-date">
                                            <input type="text" class="form-control" placeholder="MM"
                                                   id="cardExpirationMonth" data-checkout="cardExpirationMonth"
                                                   onselectstart="return false" onpaste="return false"
                                                   onCopy="return false" onCut="return false"
                                                   onDrag="return false" onDrop="return false" autocomplete=off>
                                            <span class="date-separator">/</span> <input type="text"
                                                                                         class="form-control" placeholder="YY" id="cardExpirationYear"
                                                                                         data-checkout="cardExpirationYear"
                                                                                         onselectstart="return false" onpaste="return false"
                                                                                         onCopy="return false" onCut="return false"
                                                                                         onDrag="return false" onDrop="return false" autocomplete=off>
                                        </div>
                                    </div>
                                    <div class="form-group col-sm-8">
                                        <label for="cardNumber">Número de tarjeta</label> <input type="number" 
                                                                                                 class="form-control input-background" id="cardNumber"
                                                                                                 data-checkout="cardNumber" onselectstart="return false"
                                                                                                 onpaste="return false" onCopy="return false"
                                                                                                 onCut="return false" onDrag="return false"
                                                                                                 onDrop="return false" autocomplete=off>
                                    </div>
                                    <div class="form-group col-sm-4">
                                        <label for="securityCode">CVV</label> <input id="securityCode"
                                                                                     data-checkout="securityCode" type="number" class="form-control"
                                                                                     onselectstart="return false" onpaste="return false"
                                                                                     onCopy="return false" onCut="return false"
                                                                                     onDrag="return false" onDrop="return false" autocomplete=off>
                                    </div>
                                    <div id="issuerInput" class="form-group col-sm-12 hidden">
                                        <label for="issuer">Issuer</label> <select id="issuer"
                                                                                   name="issuer" data-checkout="issuer" class="form-control"></select>
                                    </div>
                                    <div class="form-group col-sm-12">
                                        <label for="installments">Cuotas</label> <select
                                            type="text" id="installments" name="installments"
                                            class="form-control">

                                        </select>
                                    </div>
                                    <div class="form-group col-sm-12">
                                        <input type="hidden" name="transactionAmount" id="amount"
                                               value="10" /> <input type="hidden" name="paymentMethodId"
                                               id="paymentMethodId" /> <input type="hidden"
                                               name="description" id="description" /> <br>
                                        <p id="letrap">Al hacer clic en "Comprar", admito que he leído y aceptado los Términos y condiciones</p>
                                        <button type="submit" class="btn btn-danger btn-block">Comprar</button>

                                        <br> <a id="go-back"> <svg
                                                xmlns="http://www.w3.org/2000/svg" width="12" height="12"
                                                viewBox="0 0 10 10" class="chevron-left">
                                            <path fill="#009EE3" fill-rule="nonzero"
                                                  id="chevron_left"
                                                  d="M7.05 1.4L6.2.552 1.756 4.997l4.449 4.448.849-.848-3.6-3.6z"></path>
                                            </svg> Volver al carrito de compras
                                        </a>
                                    </div>
                                </div>
                            </form>
                        </div>
                    </div>
                </div>
            </section>
            <!--PSE-->
            <section class="payment-form dark">
                <div class="container_payments">
                    <div class="block-heading">
                        <h2>Pago por PSE</h2>

                    </div>
                    <div class="form-payment">
                        <div class="payment-details">
                            <form action="process_payment" method="post" id="paymentForm">
                                <input name="accionT" value="pagarpse" type="hidden" />
                                <h3 class="title">Detalles del comprador</h3>
                                <div class="row">
                                    <div class="form-group col">
                                        <label for="email">E-Mail</label> <input id="email"
                                                                                 name="emails" type="email" class="form-control" required>
                                    </div>
                                    
                                </div>
                        
                                
                                 <div class="row">
                                    
                                    <div class="form-group col">
                                         <label for="Nombre">Nombre</label> <input id="Nombre"
                                                                                 name="Nombre" type="text" class="form-control col" required>
                                    </div>
                                    <div class="form-group col">
                                        <label for="Apellido">Apellido</label> <input id="Apellido"
                                                                                 name="Apellido" type="text" class="form-control col" required>
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
                                <hr>
                                <div class="">
                                    <label for="docType">¿Cual es tu banco?</label>
                                    <select id="selectdebanco" name="selectdebanco" class="form-control"></select>
                                    <br>

                                    <label for="docType">Tipo de persona</label>
                                    <select id="TPersona"  class="form-control" name="TPersona">
                                        <option value="0">Persona natural</option>   
                                        <option value="1">Persona jurídica</option>  
                                    </select>
                                </div>
                                <div class="form-group">
                                      <input type="hidden" name="valorpagar" value="${valor}">
                                    <input type="hidden" name="transactionAmount" id="amount"
                                           value="10" /> <input type="hidden" name="paymentMethodId"
                                           id="paymentMethodId" /> <input type="hidden"
                                           name="description" id="description" /> <br>
                                    <p id="letrap3">Al hacer clic en "Comprar", admito que he leído y aceptado los Términos y condiciones</p>
                                    <button type="submit" class="btn btn-danger btn-block">Comprar</button>

                                    <br> <a id="go-backs"> <svg
                                            xmlns="http://www.w3.org/2000/svg" width="12" height="12"
                                            viewBox="0 0 10 10" class="chevron-left">
                                        <path fill="#009EE3" fill-rule="nonzero"
                                              id="chevron_left"
                                              d="M7.05 1.4L6.2.552 1.756 4.997l4.449 4.448.849-.848-3.6-3.6z"></path>
                                        </svg> Volver al carrito de compras
                                    </a>
                                </div>
                            </form>
                        </div>
                    </div>
                </div>
            </section>
        </main>
        <footer>
            <div class="footer_logo">

                <a href="../Store/"><img src="./assets/images/icons/LOGO3.png" style="width: 165px; margin-left: 20px;" class="img-fluid" alt="Responsive image"></a>
                <br>
                <div class="footer_logo"><img id="horizontal_logo" src="./assets/images/icons/logo_mercado.png"></div>
            </div>
            <div class="footer_text">
                <p>CAR WAY - 2021</p>

            </div>
        </footer>
    </body>
</html>
