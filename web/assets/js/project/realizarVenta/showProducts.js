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
                "className": "align-middle selectsss", 
                "mRender": function (data, type, row) {
                    let str = ``
                    str += `<select class="form-control float-right selectss" id="cantidadSelect" style="width:auto;height:auto;margin-right: 2%;">`;
                    for (var i = 1; i <= data; i++) {
                        str += `<option>${i}</option>`
                    }
                    return str
                }
            },
            {
                "mData": "idProductoColor",
                "className": "align-middle", 
                "mRender": function (data, type, row) {
                   return `<div class="align-middle" idProductoColor="${data}">
                    <a id="addItemVendedor" href="#" class="text-dark"><i class="fas fa-plus-square fa-3x naranja"></i></a>
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
    var parentsOne = $(this)[0].parentElement.parentElement.parentElement
    let producto = arraytotal.find(element => element.idProductoColor === idProducto);
    messageAddCar('Agregado')
    let cantidad = parseInt(parentsOne.querySelector('.selectss').value)
    addCar(producto, cantidad)

//     console.log(parentsOne.childNodes[4])
//     console.log(parentsOne.querySelector('.selectss'))
     
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
    productosRamdom()
});