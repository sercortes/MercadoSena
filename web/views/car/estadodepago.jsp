<%-- 
    Document   : estadodepago
    Created on : 25/02/2021, 09:46:08 PM
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
                        <svg xmlns="http://www.w3.org/2000/svg" width="35" height="35" fill="currentColor" class="bi bi-check-circle" viewBox="0 0 16 16" style="color: #2bb54a;">
                        <path d="M8 15A7 7 0 1 1 8 1a7 7 0 0 1 0 14zm0 1A8 8 0 1 0 8 0a8 8 0 0 0 0 16z"/>
                        <path d="M10.97 4.97a.235.235 0 0 0-.02.022L7.477 9.417 5.384 7.323a.75.75 0 0 0-1.06 1.06L6.97 11.03a.75.75 0 0 0 1.079-.02l3.992-4.99a.75.75 0 0 0-1.071-1.05z"/>
                        <h2>Pago aprobado</h2></svg>
                    </div>
                    <div class="row justify-content-md-center">
                        <div class="col-4">
                            <div class="summary-a">
                                <h3 style="text-align: center;">Ticket de compra</h3>                              
                                <hr>
                                <p id="letrap2">Datos de la tarjeta.</p>
                                <div class="summary-item">
                                    <span class="text">Nombre:</span><span class="datos-compra" id="datos-metodo">${name}</span>
                                </div>
                                <div class="summary-item">
                                    <span class="text">Documento:</span><span class="datos-compra" id="datos-metodo"><span class="text">${type} </span>${number}</span>
                                </div>
                                <div class="summary-item">
                                    <span class="text">Metodo de pago:</span><span class="datos-compra" id="datos-metodo">${payment_method_id}</span>
                                </div>
                                <div class="summary-item">
                                    <span class="text">últimos cuatro dígitos:</span><span class="datos-compra" id="datos-description">*********${last_four_digits}</span>
                                </div>
                                <hr>
                                <p id="letrap2">Detalles de la compra.</p>
                                <div class="summary-item">
                                    <span class="text">ID:</span><span class="datos-compra"  id="datos-id">${id}</span>
                                </div>
                                <div class="summary-item">
                                    <span class="text">Estado:</span><span class="datos-compra" id="datos-estado">${status}</span>
                                </div>
                                <div class="summary-item">
                                    <span class="text">Fecha de la compra:</span><span class="datos-compra" id="datos-fecha">${date_approved}</span>
                                </div>
                                <div class="summary-item">
                                    <span class="text">Descripción:</span><span class="datos-description" id="datos-description">${description}</span>
                                </div>
                                <hr>
                                <div class="summary-item">
                                    <span class="text">Total:</span><span class="datos-compra" id="datos-total">$ ${transaction_amount}</span>
                                </div>

                                <input type="button" class="btn btn-secondary" value="Imprimir" style="margin-top: 5%;width: 100%;" onclick="javascript:window.print()" />
                            </div>
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
