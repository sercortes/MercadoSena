<%-- 
    Document   : pagoscar
    Created on : 23/02/2021, 11:45:49 AM
    Author     : sergi
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <title>Template Code - Transparent Payment</title>
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <link rel="stylesheet" type="text/css"
              href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css">
        <link rel="stylesheet" type="text/css" href="./assets/styles/index.css">
    </head>
    <body>
        <main>
            <!-- Shopping Cart -->
            <section class="shopping-cart dark">
                <div class="container" id="container">
                    <div class="block-heading">
                        <h2>Ir a pagar</h2>
                    </div>

                    <div class="row">


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


                        <div class="col-md-12 col-lg-4">
                            <div class="summary">
                                <h3>Carro</h3>
                                <div class="summary-item">
                                    <span class="text">Total</span><span class="price"
                                                                         id="cart-total"></span>
                                    <hr>
                                </div>
                                <button class="btn btn-danger btn-lg btn-block"
                                        id="checkout-btn">Ir a pagar</button>
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
        </main>

        <%@include file="/views/template/footer.jspf"%>
        <script src="https://secure.mlstatic.com/sdk/javascript/v1/mercadopago.js"></script>
        <script type="text/javascript" src="./assets/js/project/carrito/index.js" defer></script>

    </body>
</html>
