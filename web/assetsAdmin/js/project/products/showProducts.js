var input = ''
var $pagination = $('#pagination'),
        totalRecords = 0,
        records = [],
        displayRecords = [],
        recPerPage = 3,
        page = 1,
        totalPages = 0,
        initiateStartPageClick = true

$(function () {

    listarProductoByVendedor()

    $('.collapse').collapse()
 

})

function listarProductoByVendedor() {


    $.ajax({
        type: "POST",
        url: './getProducts',
        async: true,
        datatype: 'json'
    }).done(function (data) {

        let arrayI = getImages()

        let arrayFinal = []

        for (var itemP of data) {
            itemP.imagen = []

            for (var itemI of arrayI) {
                if (itemP.idProducto === itemI.idProductoFK) {
                    itemP.imagen.push(itemI)
                }
            }
            arrayFinal.push(itemP)
        }

        console.log(arrayFinal)

        records = arrayFinal
        totalRecords = arrayFinal.length
        totalPages = Math.ceil(totalRecords / recPerPage)

        apply_pagination()
    })


}

function getImages() {

    let datas
    $.ajax({
        type: "POST",
        url: './getImages',
        async: false,
        datatype: 'json'
    }).done(function (data) {

        datas = data

    })

    return datas

}

function apply_pagination() {

    $pagination.twbsPagination({
        totalPages: totalPages,
        visiblePages: 4,
        onPageClick: function (event, page) {
            displayRecordsIndex = Math.max(page - 1, 0) * recPerPage;
            endRec = (displayRecordsIndex) + recPerPage;
            displayRecords = records.slice(displayRecordsIndex, endRec);
            generateTableBuscador()
        }
    });
}


function cambiarFecha() {
    $pagination.twbsPagination('destroy');
}


function generateTableBuscador() {

    let select = document.getElementById('tabla');
    let str = ``
    let num = 1

    for (var item of displayRecords) {

        str += `<div class="col-lg-4">
          <figure class="rounded p-3 bg-white shadow-lg" idProducto="${item.idProducto}">`
        str += '<td>' + getImagen(item.imagen) + '</td>'
        str += `<figcaption class="p-4 card-img-bottom">
              <h2 class="h5 font-weight-bold mb-2 font-italic">${item.nombreProducto}</h2>
              <p class="mb-0 text-small text-muted">Cantidad: ${item.stockProducto}</p>
              <p class="mb-0 text-small text-muted">Valor: ${item.valorProducto}</p>
              <p class="mb-0 text-small text-muted">Marca: ${item.marcaProducto}</p>
            </figcaption>
       
      <div class="col-lg-12 mb-5">
       <a data-toggle="collapse" href="#collapseExample${num}" role="button" aria-expanded="true" aria-controls="collapseExample1" class="btn btn-primary btn-block py-2 shadow-sm with-chevron">
          <p class="d-flex align-items-center justify-content-between mb-0 px-3 py-2"><strong class="text-uppercase">Descripción</strong><i class="fa fa-angle-down"></i></p>
        </a>
        <div id="collapseExample${num}" class="collapse shadow-sm">
          <div class="card">
            <div class="card-body">
              <p class="font-italic mb-0 text-muted">${item.descripcionProducto.toString().substr(0, 50)}</p>
            </div>
          </div>
        </div>
      </div>
        
         <div class="footer">
                            <a href="#" class="delete btn btn-danger"><i class="fas fa-minus-square"></i> Eliminar</a>
                            <a href="#" class="btn btn-warning"><i class="fas fa-edit"></i> Editar</a>
                            <a href="#" class="watch btn btn-primary"><i class="fas fa-laptop-medical"></i> Ver</a>
                        </div>
          </figure>
       
        </div>`
        num++
    }

    select.innerHTML = str;
}

function getImagen(array) {
    let mensaje = ""
    mensaje += `<img src="${array[0].url}" alt="" class="w-100 card-img-top">`
    return mensaje;
}

$(document).on('click', '.watch', function (e) {

    e.preventDefault()
    let parent = $(this)[0].parentElement.parentElement
    let idPro = $(parent).attr('idProducto')
    let producto = records.find(element => element.idProducto === idPro)

    $('#detailsProduct').modal('show')
    detailsProduct(producto)

})

function detailsProduct(producto) {
    $('#modalTittle').text(producto.nombreProducto.toString())
    caruselImagenes(producto.imagen)
    textProduct(producto)
}

function textProduct(item){
    let str = ''
    let element = document.getElementById('details')
    str += `<h2 class="h5 font-weight-bold mb-2 font-italic">${item.nombreProducto}</h2>
              <p class="mb-0 text-small text-muted">Cantidad: ${item.stockProducto}</p>
              <p class="mb-0 text-small text-muted">Valor: ${item.valorProducto}</p>
              <p class="mb-0 text-small text-muted">Marca: ${item.marcaProducto}</p>
              <p class="mb-0 text-small text-muted">Categoría: ${item.categorys.nombreCategoria}</p>`
    if (item.fechaVencimiento !== undefined) {
        str += `<p class="mb-0 text-small text-muted">Fecha de vencimiento: ${item.fechaVencimiento}</p>`
    }          
         str += `<p class="mb-0 text-small text-muted">Descripción: ${item.descripcionProducto}</p>`
    element.innerHTML = str
}

function caruselImagenes(data) {
    let str = ''
    let ele = document.getElementById('carusel')
    let num = 0;
    for (var item of data) {
        if (num === 0) {
            str += `<div class="carousel-item active">
                            <img class="d-block w-100" src="${item.url}">
                        </div>`
        } else {
            str += `<div class="carousel-item ssss">
                            <img class="d-block w-100" src="${item.url}">
                        </div>`
        }
        num++;
    }
    ele.innerHTML = str

}

$(document).on('click', '.delete', function (e) {
    e.preventDefault()
    let parent = $(this)[0].parentElement.parentElement
    let idPro = $(parent).attr('idProducto')


})
