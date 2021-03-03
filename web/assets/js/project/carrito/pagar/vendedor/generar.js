
$("#btnpagar").click(function () {

    if (!checkSession()) {
        modalPreguntaRegistro();
        return false
    }

    $('#modalCar').modal('hide')
    $('#cabecera').hide()
    $('#modalMetodoPago').modal('show')
    listarMetodos()
    listarTipoDocumento()

});

$(document).on('click', '#registrarVenta', function (e) {

    e.preventDefault()
    let buscar = $('#buscar-home')[0].ariaSelected

    if (buscar === 'true') {
        selectPersona()
    } else {
        registroPersona()
    }

})

function registroPersona() {

    let arraf = JSON.parse(localStorage.getItem('objects'));

    if (arraf.length <= 0) {
        messageInfo('No hay elementos en el carrito')
        return false
    }

    if (!checkInputs2()) {
        messageInfo('complete el formulario')
        return false
    }

    let tipoD = document.getElementById('tipoDocumento').value
    let documentoUsu = document.getElementById('documentoUsu').value
    let nombre = document.getElementById('name').value
    let apellido = document.getElementById('surname').value
    let direccion = document.getElementById('direccion').value
    let metodoPago = document.getElementById('metodoPago').value
    $("#registrarVenta").attr("disabled", true);
    $('#cargas').addClass('is-active');

    $.ajax({
        type: 'POST',
        url: './generateSaleByVendedor',
        datatype: 'json',
        data: {
            crear: 1, arrayP: JSON.stringify(arraf),
            tipoD: tipoD,
            documentoUsu: documentoUsu,
            nombre: nombre,
            apellido: apellido,
            direccion: direccion,
            metodoPago: metodoPago
        }, error: function (datas) {
            messageInfo('Error en los productos')
        },
        success: function (datas) {

            console.log(datas)
            $('#cargas').removeClass('is-active');
            $("#registrarVenta").attr("disabled", false);

            if (datas === 00 || datas === 0) {
                messageInfo('Error')
            } else if (datas == 01) {
                input('documentoUsu', 'Número de documento ya utilizado, busque al cliente')
            } else {
                cleanModalResgistrarVenta(datas)
            }

        }
    })

}


function checkInputs2() {

    $(".remove").remove();

    let tipoD = document.getElementById('tipoDocumento').value
    let documentoUsu = document.getElementById('documentoUsu').value
    let nombre = document.getElementById('name').value
    let apellido = document.getElementById('surname').value
    let direccion = document.getElementById('direccion').value
    let metodoPago = document.getElementById('metodoPago').value

    if (tipoD == '') {
        input('tipoDocumento', 'selecione un tipo de documento')
        return false
    }

    if (documentoUsu == '') {
        input('documentoUsu', 'Falta el número de documento')
        return false
    }

    if (documentoUsu.length > 15 || documentoUsu.length <= 5) {
        input('documentoUsu', 'Digite un número de documento válido')
        return false
    }

    if (nombre == '') {
        input('name', 'Falta nombre del cliente')
        return false
    }

    if (apellido == '') {
        input('surname', 'Falta el apellido del cliente')
        return false
    }

    if (direccion == '') {
        input('direccion', 'Falta la dirección')
        return false
    }
    if (metodoPago == '') {
        input('metodoPago', 'Seleccione el método de pago')
        return false
    }

    return true
}

function input(input, mensaje) {
    $('#' + input).focus().after(`<div class='remove'><font color='red'>${mensaje}</font><div>`);    
}


function cleanCar() {
    var arrayBuy = []
    localStorage.setItem('objects', JSON.stringify(arrayBuy))
}

function cleanModalResgistrarVenta(data) {

    messageOk('Venta registrada')
    generateFactura(data)
    $('#metodoPago').prop('selectedIndex', 0);
    $('#formularioNewUser').trigger("reset");
    $('#modalMetodoPago').modal('hide')
    cleanCar()
    $('#modalFactura').modal('show')
    updateIconNumber()

}

function generateFactura(data) {

    let arraf = JSON.parse(localStorage.getItem('objects'));
    console.log(arraf)

    let str = ``

    str += `<div class="d-flex justify-content-center row">
                <div class="col-md-10">
                    <div class="receipt bg-white p-3 rounded"><img src="./assets/images/icons/LOGO3.png" width="120">
                        <h4 class="mt-2 mb-3">Ticket de compra</h4>
                        <h6 class="name">${data.perDTO.nombrePer} ${data.perDTO.apellidoPer},</h6>
                        <span class="fs-12 text-black-50">${data.perDTO.idPer}</span>
                        <span class="fs-12 text-black-50">${data.perDTO.direccionPer}</span>
                        <hr>
                        <div class="d-flex flex-row justify-content-between align-items-center order-details">
                            <div><span class="d-block fs-12">Fecha</span><span class="font-weight-bold">${new Date().toISOString().slice(0, 10)}</span></div>
                            <div><span class="d-block fs-12">Método de pago</span><span class="font-weight-bold">Efectivo</span></div>
                            <div><span class="d-block fs-12">Estado</span><span class="font-weight-bold text-success">Aprobada</span></div>
                        </div>
                        <hr>`

    for (var item of arraf) {
        str += `<div class="d-flex justify-content-between align-items-center product-details">`

        if (item.imagenUnitaria !== undefined) {
            str += `<div class="d-flex flex-row product-name-image"><img class="rounded" src="${item.imagenUnitaria}" width="80">`
        } else {
            str += `<div class="d-flex flex-row product-name-image"><img class="rounded" src="${item.imagenes[0].url}" width="80">`
        }

        str +=`<div class="d-flex flex-column justify-content-between ml-2">
                                    <div><span class="d-block font-weight-bold p-name">${item.nombreProducto}</span><span class="fs-12">${item.categorys.nombreCategoria} marca: ${item.marcaProducto}</span></div>
                                    <span class="fs-12">Cantidad: ${item.cantidad}</span>
                                </div>
                            </div>
                            <div class="product-price">
                                <h5>$${money(item.valorProducto * item.cantidad)}</h5>
                            </div>
                        </div>`
    }

    str += `<div class="mt-5 amount row">
                            <div class="d-flex justify-content-center col-md-6"><img src="https://i.imgur.com/AXdWCWr.gif" width="250" height="100"></div>
                            <div class="col-md-6">
                                <div class="billing">
                                    <div class="d-flex justify-content-between"><span>Subtotal</span><span class="font-weight-bold">$${money(data.valorVenta)}</span></div>
                                    <div class="d-flex justify-content-between mt-2"><span class="text-success">Descuento</span><span class="font-weight-bold text-success">$0</span></div>
                                    <hr>
                                    <div class="d-flex justify-content-between mt-1"><span class="font-weight-bold">Total</span><span class="font-weight-bold text-success">$${money(data.valorVenta)}</span></div>
                                </div>
                            </div>
                        </div><span class="d-block">Fecha</span><span class="font-weight-bold text-success">${new Date().toISOString().slice(0, 10)}</span><span class="d-block mt-3 text-black-50 fs-15">Gracias por confiar en nosotros.</span>
                        <hr>
                        <div class="d-flex justify-content-between align-items-center footer">
                            <div class="thanks"><span class="d-block font-weight-bold">Gracias por su compra</span><span>att. Car Way</span></div>
                            <div class="d-flex flex-column justify-content-end align-items-end"><span class="d-block font-weight-bold">Necesita ayuda</span><span></span></div>
                        </div>
                    </div>
                </div>
            </div>`
    document.getElementById('salidaFactura').innerHTML = str

}

