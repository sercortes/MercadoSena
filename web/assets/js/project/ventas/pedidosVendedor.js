var number = 1;
var papa = 0

$(function () {

    $.ajax({
        type: "POST",
        url: './CheckBuysAdmin',
        async: false,
        datatype: 'json'
    }).done(function (data) {

        console.log(data)

    })

    getVentasByVendedor(1)

})

function menu() {

    if (number == 1) {

        switch (papa) {
            case 'pedPendientes':
                getVentasByVendedor(1)
                break;
            case 'pedConcretados':
                getVentasByVendedor(2)
                break;
            case 'pedNoConcretados':
                getAllVentasByCustomerFailed()
                break;
            default:
                break;
        }

    } else {

        switch (papa) {
            case 'pedPendientes':
                getAllVentasByCustomer(1)
                break;
            case 'pedConcretados':
                getAllVentasByCustomer(2)
                break;
            case 'pedNoConcretados':
                getAllVentasByCustomerFailedTwo()
                break;
            default:
                break;
        }

    }

}

$(document).on('click', '.section', function (e) {

    e.preventDefault()
    papa = $(this)[0].id

    menu()


})

function drawVentas(data) {

    console.log(data)

    if (data.length <= 0) {
        queryNull()
        return false;
    }

    let str = ``
    let number = 0;
    for (var item of data) {
        str += `<div class="card">
          <div id="headingTwo" class="card-header bg-white shadow-sm border-0">
            <h6 class="mb-0 font-weight-bold"><a href="#" data-toggle="collapse" data-target="#collapseTwo${number}" aria-expanded="false" aria-controls="collapseTwo" class="d-block position-relative collapsed text-dark text-uppercase collapsible-link py-2">Venta #${item.idVenta}</a></h6>
          </div>
          <div id="collapseTwo${number}" aria-labelledby="headingTwo" data-parent="#accordionExample" class="collapse">
            <div class="card-body p-5">
              <div class="d-flex justify-content-center row">
                <div class="col-md-12">
                    <div class="receipt bg-white p-3 rounded"><img src="./assets/images/icons/LOGO3.png" width="220">
                        <i class="fas fa-receipt fa-5x float-right naranja"></i>
                        <h4 class="mt-2 mb-3">Detalles</h4>
                        <h6 class="name">${item.perDTO.nombrePer} ${item.perDTO.apellidoPer},</h6>
                        <span class="fs-12 text-black-50"><b>Dirección: </b>${item.perDTO.direccionPer}</span>
                        <br>
                        <span class="fs-12 text-black-50"><b>Celular: </b>${item.perDTO.numCelularPer}</span>`
        if (item.perDTO.telPer !== '') {
            str += `<br><span class="fs-12 text-black-50"><b>Teléfono: </b>${item.perDTO.telPer}</span>`
        }
        str += `<hr>
                        <div class="d-flex flex-row justify-content-between align-items-center order-details">
                            <div><span class="d-block fs-12">Fecha</span><span class="font-weight-bold">${item.fechaVenta}</span></div>
                            <div><span class="d-block fs-12">Método de pago</span><span class="font-weight-bold">${item.formaPago}</span></div>
                            <div><span class="d-block fs-12">Estado</span><span class="font-weight-bold text-success">${item.idEstadoVentaFK}</span></div>
                        </div>
                        <hr>
                <table class="table table-responsive table-striped">
             <thead class="thead-light">
                            <tr>
                                <th scope="col" class="border-0">
                                    <div class="p-1 px-3 text-uppercase">Producto</div>
                                </th>
                                <th scope="col" class="border-0">
                                    <div class="p-1 px-3 text-uppercase text-left">Ref</div>
                                </th>
                                <th scope="col" class="border-0">
                                    <div class="p-1 px-3 text-uppercase">Nombre</div>
                                </th>
                                   <th scope="col" class="border-0">
                                    <div class="py-1 text-uppercase">Color</div>
                                </th>
                                <th scope="col" class="border-0">
                                    <div class="py-1 text-uppercase">Pre. u</div>
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

        for (var items of item.listaProductos) {

            str += `<tr><td>
                <img src="${items.imagenUnitaria}" alt="" width="70" class="img-fluid rounded shadow-sm">
             </td>
                <td class="align-middle text-center">${items.referencia}</td>
                    <td>    
                    <div class="d-inline-block align-middle">
                            <p class="mb-0 text-dark d-inline-block align-middle text-justify">${items.nombreProducto}</p>
                    </div>
                    </td>
                    <td class="align-middle"><strong>${items.color}</strong></td>
                    <td class="align-middle text-center"><strong>${items.valorProducto}</strong></td>
                    <td class="align-middle text-center"><strong>${items.cantidad}</strong></td>
                    <td class="align-middle"><strong>${money(items.valorProducto * items.cantidad)}</strong></td>
                </tr>`

        }

        str += `</tbody></table>
                <div class="row">
                            <div class="d-flex justify-content-center col-md-6"></div>
                            <div class="col-md-6">
                                <div class="billing">
                                    <div class="d-flex justify-content-between"><span>Subtotal</span><span class="font-weight-bold">$${money(item.valorVenta)}</span></div>
                                    <div class="d-flex justify-content-between mt-2"><span class="text-success">Descuento</span><span class="font-weight-bold text-success">$${money(item.descuento)}</span></div>
                                    <hr>
                                    <div class="d-flex justify-content-between mt-1"><span class="font-weight-bold">Total</span><span class="font-weight-bold text-success">$${money(item.valorVenta - item.descuento)}</span></div>
                                </div>
                            </div>
                        <hr>
                        </div><span class="d-block"></span><span class="font-weight-bold text-success"></span>
                    </div>
                </div>
            </div>
            </div>
          </div>
        </div>`
        number++
    }
    document.getElementById('pedidos').innerHTML = str

}

$(document).on('change', '#selectTipe', function () {

    let tipo = $(this).val()
    if (tipo == '2') {
        number = 2
    } else {
        number = 1
    }

    menu()

});
function getVentasByVendedor(estado) {

    $.ajax({
        type: "POST",
        url: './getAllVentasByVendedor',
        async: true,
        data: {
            estado: estado
        },
        datatype: 'json'
    }).done(function (data) {

        drawVentas(data)

    })

}

function getAllVentasByCustomer(estado) {

    $.ajax({
        type: "POST",
        url: './getAllVentasByCus',
        async: true,
        data: {
            estado: estado
        },
        datatype: 'json'
    }).done(function (data) {

        drawVentas(data)

    })

}

function getAllVentasByCustomerFailed() {

    $.ajax({
        type: "POST",
        url: './getAllVentasByCus',
        async: true,
        datatype: 'json'
    }).done(function (data) {

        drawVentas(data)

    })

}

function getAllVentasByCustomerFailedTwo() {

    $.ajax({
        type: "POST",
        url: './getAllVentasByCusFailed',
        async: true,
        datatype: 'json'
    }).done(function (data) {

        drawVentas(data)

    })

}


function queryNull(){
    document.getElementById('pedidos').innerHTML =
        `<div class="alert alert-warning alert-dismissible fade show" role="alert">
            <strong>No hay elementos en esa categoría!</strong>
            <button type="button" class="close" data-dismiss="alert" aria-label="Close">
              <span aria-hidden="true">&times;</span>
            </button>
        </div>`
}