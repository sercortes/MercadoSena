
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
    let descuento = document.getElementById('descuento').value
    let total = 0

    if (arraf.length <= 0) {
        messageInfo('No hay elementos en el carrito')
        return false
    }

    if (!checkInputs2()) {
        messageInfo('complete el formulario')
        return false
    }
    
    for(var item of arraf){
        total += item.valorProducto*item.cantidad;
    }
    
    if (descuento >= total) {
        input('descuento', 'descuento no válido')
        return false
    }
    
    if (descuento == '') {
        descuento = 0;
    }

    let tipoD = document.getElementById('tipoDocumento').value
    let documentoUsu = document.getElementById('documentoUsu').value
    let nombre = document.getElementById('name').value
    let apellido = document.getElementById('surname').value
    let direccion = document.getElementById('direccion').value
    let metodoPago = document.getElementById('metodoPago').value
    let celular = document.getElementById('celular').value
    let telefono = document.getElementById('telefono').value
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
            metodoPago: metodoPago,
            descuento:descuento,
            celular:celular,
            telefono:telefono
        }, error: function (datas) {
            messageInfo('Error en los productos')
            $('#cargas').removeClass('is-active');
        },
        success: function (datas) {

            console.log(datas)
            $('#cargas').removeClass('is-active');
            $("#registrarVenta").attr("disabled", false);

            if (datas === 00 || datas === 0) {
                messageInfo('Error')
            } else if (datas == 01) {
                input('documentoUsu', 'Número de documento ya existe, busque al cliente')
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
    let celular = document.getElementById('celular').value
    let telefono = document.getElementById('telefono').value

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
    
     if (celular == '') {
        input('celular', 'Ingrese el número de celular')
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
    $('#descuento').val('')
    generateFactura(data)
    $('#metodoPago').prop('selectedIndex', 0);
    $('#formularioNewUser').trigger("reset");
    $('#modalMetodoPago').modal('hide')
    $('#modalFactura').modal('show')
    cleanCar()
    updateIconNumber()
    $('#cargas').addClass('is-active');
    $('#example').DataTable().destroy();
    productosRamdom()

}

function generateFactura(data) {

    let arraf = JSON.parse(localStorage.getItem('objects'));
    console.log(arraf)
    console.log(data)

    let str = ``

    str += `<div class="d-flex justify-content-center row">
                <div class="col-md-10">
                    <div class="receipt bg-white p-3 rounded"><img src="./assets/images/icons/LOGO3.png" width="220">
                        <i class="fas fa-receipt fa-5x float-right naranja"></i>
                        <h4 class="mt-2 mb-3">Ticket de compra</h4>
                        <h6 class="name">${data.perDTO.nombrePer} ${data.perDTO.apellidoPer},</h6>
                        <span class="fs-12 text-black-50"><b>Dirección: </b>${data.perDTO.direccionPer}</span>
                        <br>
                        <h7 class="fs-12 text-black-50"><b>Celular: </b>${data.perDTO.numCelularPer}</h7>
                        <hr>
                        <div class="d-flex flex-row justify-content-between align-items-center order-details">
                            <div><span class="d-block fs-12">Fecha</span><span class="font-weight-bold">${new Date().toISOString().slice(0, 10)}</span></div>
                            <div><span class="d-block fs-12">Método de pago</span><span class="font-weight-bold">${data.nombreFormaPago}</span></div>
                            <div><span class="d-block fs-12">Estado</span><span class="font-weight-bold text-success">Aprobada</span></div>
                        </div>
                        <hr>`

    str += `<table class="table table-responsive table-striped">
             <thead class="thead-light">
                            <tr>
                                <th scope="col" class="border-0">
                                    <div class="p-1 px-3 text-uppercase">Producto</div>
                                </th>
                                 <th scope="col" class="border-0">
                                    <div class="p-1 px-3 text-uppercase">Ref.</div>
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
                                    <div class="py-1 text-uppercase">Total</div>
                                </th>
                            </tr>
                        </thead>
        <tbody id="card">`
    for (var item of arraf) {
        str += `<tr><td>`
        if (item.imagenUnitaria !== undefined) {
            str += `<img src="${item.imagenUnitaria}" alt="" width="70" class="img-fluid rounded shadow-sm">`
        } else {
            str += `<img src="${item.imagenes[0].url}" alt="" width="70" class="img-fluid rounded shadow-sm">`
        }
        str += `</td>
                        <td class="align-middle text-center"><strong>${item.referencia}</strong></td>
                        <td>    
                            <div class="ml-3 d-inline-block align-middle">
                                    <p class="mb-0 text-dark d-inline-block align-middle text-justify">${item.nombreProducto}</p>
                            </div>
                        </td>
                        <td class="align-middle text-center"><strong>${item.color}</strong></td>
                        <td class="align-middle text-center"><strong>${item.cantidad}</strong></td>
                        <td class="align-middle text-center"><strong>${money(item.valorProducto * item.cantidad)}</strong></td>
                    </tr>`
    }

    str += `</tbody></table>`

    str += `<div class="mt-3 amount row">
                            <div class="d-flex justify-content-center col-md-6"></div>
                            <div class="col-md-6">
                                <div class="billing">
                                    <div class="d-flex justify-content-between"><span>Subtotal</span><span class="font-weight-bold">$${money(data.valorVenta)}</span></div>
                                    <div class="d-flex justify-content-between mt-2"><span class="text-success">Descuento</span><span class="font-weight-bold text-success">$${money(data.descuento)}</span></div>
                                    <hr>
                                    <div class="d-flex justify-content-between mt-1"><span class="font-weight-bold">Total</span><span class="font-weight-bold text-success">$${money(data.valorVenta - data.descuento)}</span></div>
                                </div>
                            </div>
                        <hr>
                        </div><span class="d-block"></span><span class="font-weight-bold text-success"></span><span class="d-block mt-3 text-black-50 fs-15">Gracias por confiar en nosotros.</span>
                        <div class="d-flex justify-content-between align-items-center footer">
                            <div class="thanks"><span class="d-block font-weight-bold">CarWay</span><span></span></div>
                            <div class="d-flex flex-column justify-content-end align-items-end"><span class="d-block font-weight-bold"></span><span></span></div>
                        </div>
                    </div>
                </div>
            </div>`
    document.getElementById('salidaFactura').innerHTML = str

}

