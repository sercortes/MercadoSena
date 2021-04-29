$(function () {

    $('#cargas').addClass('is-active');
    getLogs()

})


function getLogs() {

    $.ajax({
        url: './getLogs',
        type: 'POST',
        dataType: 'json',
        async: true,
        success: function (data) {

            gene(data)

        }
    })

}

function gene(data) {

    $('#cargas').removeClass('is-active');

    $('#example').dataTable({
        "autoWidth": false,
        "searching": false,
        "order": [],
        "lengthChange": true,
        "aaData": data,
        "paging": false,
        "ordering": false,
        "info": false,
        "columns": [
            {
                "className": "align-middle text-center",
                "mData": "idLog",
            },
            {
                "mData": "nombreTipoFK",
            },
            {
                "mData": "fecha",
            },
            {
//                "className": "align-middle text-center", 
                "mData": "usuario",
            },
            {
                "mData": "tipoFK", "mData": "idEvento",
//                "className": "align-middle",
                "className": "align-middle text-center",
                "mRender": function (data, type, row) {
                    return `<div class="align-middle" idTipo="${row.tipoFK}" idEvento="${row.idEvento}">
                    <a id="" href="#" class="see text-dark"><i class="fas fa-tasks naranja fa-2x"></i></a>
                  </div>`
                }
            },
        ],
        "language": {
            "lengthMenu": "Mostrar _MENU_ por página",
            "zeroRecords": "No encontrados",
            "info": "página _PAGE_ de _PAGES_",
            "infoEmpty": "No hay elementos",
            "infoFiltered": "(total _MAX_ )",
            "sSearch": "Buscar"
        },
    })

}

$(document).on('click', '.see', function (e) {

    e.preventDefault()
    let parent = $(this)[0].parentElement
    let id = $(parent).attr('idTipo')
    let idEvento = $(parent).attr('idEvento')
    if (id == 1) {
       
        geneP(idEvento)
       
    } else {
   
        $('#modalDetail').modal('show')
        infoVenta(idEvento)
    
    }

})

function infoVenta(idEvento){
    
     $.ajax({
        url: './getInfoVenta',
        type: 'POST',
        data: {idVenta:idEvento},
        dataType: 'json',
        async: true,
        success: function (data) {
            
            gense(data)

        }
    })
    
}

function gense(data){
    
            let str = ''
           
         str += `<div class="card">
          <div id="headingTwo" class="card-header bg-white shadow-sm border-0">
            <h6 class="mb-0 font-weight-bold"><a href="#" data-toggle="collapse" data-target="#collapseTwo" aria-expanded="true" aria-expanded="false" aria-controls="collapseTwo" class="d-block position-relative collapsed text-dark text-uppercase collapsible-link py-2">Venta #${data.idVenta}</a></h6>
          </div>
          <div id="collapseTwo" aria-labelledby="headingTwo" data-parent="#accordionExample" class="collapse show">
            <div class="card-body p-5">
              <div class="d-flex justify-content-center row">
                <div class="col-md-12">
                    <div class="receipt bg-white p-3 rounded"><img src="./assets/images/icons/LOGO3.png" width="220">
                        <i class="fas fa-receipt fa-5x float-right naranja"></i>
                        <h4 class="mt-2 mb-3"></h4>
                        <span class="fs-12 text-black-50 float-left"><b>Nombre: </b>${data.perDTO.nombrePer} ${data.perDTO.apellidoPer}</span><br>
                        <span class="fs-12 text-black-50 float-left"><b>Dirección: </b>${data.perDTO.direccionPer}</span>
                        <br>
                        <span class="fs-12 text-black-50 float-left"><b>Celular: </b>${data.perDTO.numCelularPer}</span>`
                        if (data.perDTO.telPer !== '') {
                            str +=`<br><span class="fs-12 text-black-50 float-left"><b>Teléfono: </b>${data.perDTO.telPer}</span>`
                        }
                        str +=`<hr>
                        <div class="d-flex flex-row justify-content-between align-items-center order-details">
                            <div><span class="d-block fs-12">Fecha</span><span class="font-weight-bold">${data.fechaVenta}</span></div>
                            <div><span class="d-block fs-12">Método de pago</span><span class="font-weight-bold">${data.formaPago}</span></div>
                            <div><span class="d-block fs-12">Estado</span><span class="font-weight-bold text-success">${data.idEstadoVentaFK}</span></div>
                        </div>
                        <hr>
                <table class="table table-responsive">
             <thead class="thead-light">
                            <tr>
                                <th scope="col" class="border-0">
                                    <div class="p-1 px-3 text-uppercase">Producto</div>
                                </th>
                                <th scope="col" class="border-0">
                                    <div class="p-1 px-3 text-uppercase">Ref</div>
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

        for (var items of data.listaProductos) {

            str += `<tr><td>
                <img src="${items.imagenUnitaria}" alt="" width="70" class="img-fluid rounded shadow-sm">
             </td>
                <td class="align-middle text-center">${items.referencia}</td>
                    <td>    
                    <div class="ml-3 d-inline-block align-middle">
                            <p class="mb-0 text-dark d-inline-block align-middle text-justify">${items.nombreProducto}</p>
                    </div>
                    </td>
                    <td class="align-middle text-center"><strong>${items.color}</strong></td>
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
                                    <div class="d-flex justify-content-between"><span>Subtotal</span><span class="font-weight-bold">$${money(data.valorVenta)}</span></div>
                                    <div class="d-flex justify-content-between mt-2"><span class="text-success">Descuento</span><span class="font-weight-bold text-success">$${money(data.descuento)}</span></div>
                                    <hr>
                                    <div class="d-flex justify-content-between mt-1"><span class="font-weight-bold">Total</span><span class="font-weight-bold text-success">$${money(data.valorVenta - data.descuento)}</span></div>
                                </div>
                            </div>
                        </div><span class="d-block"></span><span class="font-weight-bold text-success"></span>
                    </div>
                    <hr>
                </div>
            </div>
            </div>
          </div>
        </div>`
    
    document.getElementById('pedidos').innerHTML = str
    
}