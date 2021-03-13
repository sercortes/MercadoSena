var arraytotal = []

$(function () {

    console.time('loop');
    $('#cargas').addClass('is-active');
    productosRamdom()
    generateTable()

})

function productosRamdom() {

    $.ajax({
        type: "POST",
        url: './getProductsByDateTimeBasic',
        async: true,
        datatype: 'json'
    }).done(function (data) {
        
        arraytotal = data
        generatesTable(data)

    })

}

function generatesTable(data) {
    
    $('#cargas').removeClass('is-active');
    
    $('#example').dataTable({
        "order": [],
        "lengthChange": true,
        "aaData": data,
        "columns": [
            {
                "mData": "imagenUnitaria",
                "mRender": function (data, type, row) {
                    return `<img src="${data}" alt="" width="70" class="img-fluid rounded shadow-sm">`
                }
            },
            {
                "mData": "nombreProducto",
            },
            {
                "mData": "valorProducto",
                "className": "align-middle", 
                "mRender": function (data, type, row) {
                    return `<strong>$${money(data)}</strong>`
                }
            },
            {
                "data": "color",
                "className": "align-middle"
            },
            {
                "mData": "stockProducto",
                "className": "align-middle", 
                "mRender": function (data, type, row) {
                    return `<strong>${data}</strong>`
                    }
            },
            {
                "mData": "idProductoColor", "mData": "idProducto",
                "className": "align-middle", 
                "mRender": function (data, type, row) {
                   return `<div class="align-middle" idProductoColor="${row.idProductoColor}" idProducto="${row.idProducto}">
                    <a id="" href="#" class="watchVendedor text-dark"><i class="fas fa-tasks naranja fa-2x"></i></a>
                    <a id="addItemVendedor" href="#" class="text-dark"><i class="fas fa-plus-square naranja fa-2x"></i></a>
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
          initComplete: function () {
            this.api().columns().every( function () {
                var that = this;
                $( 'input', this.footer() ).on( 'keyup change clear', function () {
                    if ( that.search() !== this.value ) {
                        that
                            .search( this.value )
                            .draw();
                    }
                } );
            } );
        }
    })

    console.timeEnd('loop');

}

$(document).on('click', '#addItemVendedor', function (e) {

    e.preventDefault();
    let parent = $(this)[0].parentElement
    let idProducto = $(parent).attr('idProductoColor')
    let producto = arraytotal.find(element => element.idProductoColor === idProducto);
    messageAddCar('Agregado')
//    let cantidad = parseInt(parentsOne.querySelector('.selectss').value)
    addCar(producto, 1) 
})


$(document).on('click', '.watchVendedor', function (e) {

    e.preventDefault()
    let parent = $(this)[0].parentElement
    let idPro = $(parent).attr('idProducto')
    let producto = arraytotal.find(element => element.idProducto === idPro);
    $('#detailsProduct').modal('show')
    producto.imagenes = []
    let pro = {
        url:producto.imagenUnitaria
    }
    producto.imagenes.push(pro)
    detailsProductsVendedor(producto)

})

function detailsProductsVendedor(producto) {
    caruselImagenes(producto.imagenes)
    textProductVendedor(producto)
}
function textProductVendedor(item) {
    
    let colors = getColors(item.idProducto)

    let str = '';
    str += `<div id="detail" class="text-justify pt-2" precioProducto="${item.valorProducto}" idEmpresa="${item.idEmpresaFK}" idProducto="${item.idProducto}">
                <h2 class="h4 font-weight-bold mb-2 text-center">${item.nombreProducto}</h2>
            <hr>`
    
        str += `<a id="" type="button" href="#" class="addItemVendedor btn btn-primary btn-xs float-right hvr-push">`;
        str += `<i class="fas fa-shopping-cart"></i> Añadir al carrito</a>`;
        str += `<select class="form-control float-right" id="cantidadSelect" style="width:auto;height:auto;margin-right: 2%;">`;
        for (var i = 1; i <= item.stockProducto; i++) {
            str += `<option>${i}</option>`
        }
        str += `</select>`
        str += `<select class="form-control float-right" id="colorSelect" style="width:auto;height:auto;margin-right: 2%;">`;
        str += `<option value="${item.idProductoColor}">${item.color}</option>`
        for (var it of colors) {
            if (it.idColor !== item.idProductoColor) {
                str += `<option value="${it.idColor}">${it.nombreColor}</option>` 
            }
        }
        str += `</select>`

    str += `<p class="font-weight-bold text-muted h5 text-left">$ ${money(item.valorProducto)}</p>
              <h4 class="mb-0 pb-2 text-left">Marca: ${item.marcaProducto}</h4>
              <h5 class="font-weight-bold text-muted h6 text-left">Color: ${item.color}</h5>
           <div class="card shadow-sm">
            <div class="card-body">
              <p class="mb-0 text-small text-muted textoDes text-left">Descripción</p>
              <p class="mb-0 text-small text-muted textoDes text-left">${item.descripcionProducto}</p>
            </div>
          </div>`
    
    str +=`<hr>
        <div class="col-lg-12 mb-5 p-0">
       <a data-toggle="collapse" href="#collapseExample${item.idProducto}" role="button" aria-expanded="false" aria-controls="" class="btn btn-primary btn-block py-2 shadow-sm with-chevron">
          <p class="d-flex align-items-center justify-content-between mb-0 px-3 py-2"><strong class="text-uppercase">Información Adicional</strong><i class="fa fa-angle-down"></i></p>
        </a>
        <div id="collapseExample${item.idProducto}" class="collapse shadow-sm">
          <div class="card">
            <div class="card-body">
             <p class="mb-0 text-small text-muted">Días de envío: ${item.diasEnvios}</p>
         <p class="mb-0 text-small text-muted">Medidas : ${item.medidaProducto}</p>
         <p class="mb-0 text-small text-muted">Empaque : ${item.empaqueProducto}</p>
         <p class="mb-0 text-small text-muted">Embalaje : ${item.embalajeProducto}</p>
         <p class="mb-0 text-small text-muted textoDes text-left">${item.ventajaProducto}</p>
         <p class="mb-0 text-small text-muted textoDes text-left">Garantía:${item.garantia}</p>
                </div>
            </div>
          </div>
        </div>
      </div>
    </div>`
    document.getElementById('details').innerHTML = str
}




$(document).on('click', '.addItemVendedor', function (e) {
    
    e.preventDefault();
    let parent = $(this)[0].parentElement
    let idProducto = $(parent).attr('idProducto')
    let producto = arraytotal.find(element => element.idProducto === idProducto);
    this.disabled = true
    let cantidad = parseInt($('#cantidadSelect').val())
    if (isNaN(cantidad)) { 
        messageInfo('agotado')
        return false
    }
    messageAddCar('Agregado')
    let colors = $('#colorSelect').val();
    producto.idProductoColor = colors
    let textColor = document.getElementById('colorSelect')
    let nombreColor = textColor.options[textColor.selectedIndex].text
    producto.color = nombreColor
    addCar(producto, cantidad)

})

function generateTable(){
    $('#example tfoot th').each( function () {
        var title = $(this).text();
        if (title === 'Categoría') {
            $(this).html( '<input type="text" class="form-control"" placeholder="Accesorios" />' );
        }else if(title === 'Nombre'){
            $(this).html( '<input type="text" class="form-control"" placeholder="LLaveros" />' );
        }else if(title === 'Color'){
            $(this).html( '<input type="text" class="form-control"" placeholder="Rojo" />' );
        }else if (title === 'Valor producto') {
             $(this).html( '<input type="number" class="form-control"" placeholder="10000" />' );
        }
    } );
}

function money(dolar) {
    return dolar.toString().replace(/(\d)(?=(\d\d\d)+(?!\d))/g, "$1.")
}

$("#modalFactura").on("hidden.bs.modal", function () {
    
    $('#cargas').addClass('is-active');
    $('#example').DataTable().destroy();
    productosRamdom()

});