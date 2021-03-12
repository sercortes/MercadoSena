var num = 1
var input = ''
var $pagination = $('#pagination'),
        totalRecords = 0,
        records = [],
        displayRecords = [],
        recPerPage = 4,
        page = 1,
        totalPages = 0,
        initiateStartPageClick = true


$(document).on('click', '.page-item', function () {
    let currentPage = $pagination.twbsPagination('getCurrentPage');
    console.log(currentPage)
    localStorage.setItem('page', currentPage);
})

$(function () {

    $('#cargas').addClass('is-active');
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

        $('#cargas').removeClass('is-active');

        if (data == undefined) {
            queryEmphyP()
            return false
        }

        records = data
        totalRecords = data.length
        totalPages = Math.ceil(totalRecords / recPerPage)

        apply_pagination()

    })

}

function apply_pagination() {

    $pagination.twbsPagination({
        totalPages: totalPages,
        visiblePages: 4,
        onPageClick: function (event, page) {
            displayRecordsIndex = Math.max(page - 1, 0) * recPerPage;
            endRec = (displayRecordsIndex) + recPerPage;
            displayRecords = records.slice(displayRecordsIndex, endRec);
            generateTableBuscadorP()
        }
    });
}

function generateTableBuscadorP() {

    let select = document.getElementById('tabla');
    let str = ``
    let num = 1

    for (var item of displayRecords) {

        str += `<div class="col-lg-3">
          <figure class="rounded p-3 bg-white shadow-sm" idProducto="${item.idProducto}">`
        str += `<div id="carouselExampleControls${num}" class="carousel slide hijueputa" data-ride="carousel">
                    <div class="carousel-inner" id="caruselOne${num}">`
        str += getImages(item.idProducto, num)
        str += `</div>`
        str += `<figcaption class="p-3 card-img-bottom">
                <hr>
              <h2 class="h5 text-left text-muted mb-0 img-fluid fit-text">${item.nombreProducto.toString().substr(0, 36)}</h2>
              <h2 class="h5 text-left font-weight-bold mb-2">$ ${money(item.valorProducto)}</h2>
            </figcaption>
       
      <div class="col-lg-12 mb-5 p-0">
       <a data-toggle="collapse" href="#collapseExamples${num}" role="button" aria-expanded="false" aria-controls="collapseExample1" class="btn btn-primary btn-block py-2 shadow-sm with-chevron">
          <p class="d-flex align-items-center justify-content-between mb-0 px-3 py-2"><strong class="text-uppercase">Descripción</strong><i class="fa fa-angle-down"></i></p>
        </a>
        <div id="collapseExamples${num}" class="collapse shadow-sm">
          <div class="card">
            <div class="card-body">
              <p class="font-italic mb-0 text-muted">${item.descripcionProducto.toString().substr(0, 50)}</p>
            </div>
          </div>
        </div>
      </div>
         <div class="text-right">
                            <a href="#" class="delete btn btn-danger"><i class="fas fa-minus-square"></i></a>
                            <a href="#" class="editProduct btn btn-warning"><i class="fas fa-edit"></i></a>
                            <a href="#" class="watchMyProducts btn btn-primary"><i class="far fa-images"></i></a>
                        </div>
          </figure>
       
        </div>`
        num++

    }

    select.innerHTML = str;
}

$(document).on('click', '.watchMyProducts', function (e) {

    e.preventDefault()
    let parent = $(this)[0].parentElement.parentElement
    let idPro = $(parent).attr('idProducto')
    let producto = records.find(element => element.idProducto === idPro)
    $('#detailsProductVendedor').modal('show')
    detailsProduct(producto);

})

function detailsProduct(producto) {
//    $('#modalTittle').text(producto.nombreProducto.toString())
    caruselImagenes(producto.imagenes)
    textProduct(producto)
}

$(document).on('click', '.delete', function (e) {

    e.preventDefault()

    let parent = $(this)[0].parentElement.parentElement
    let idPro = $(parent).attr('idProducto')

    Swal.fire({
        title: '¿Está seguro?',
        text: "¡No se pueden revertir los cambios!",
        icon: 'warning',
        showCancelButton: true,
        confirmButtonColor: '#3085d6',
        cancelButtonColor: '#d33',
        confirmButtonText: 'Si, Eliminar!'
    }).then((result) => {

        if (result.value) {
            deleteOk(idPro)
        }

    })

})


function deleteOk(id) {

    $('#cargas').addClass('is-active');

    $.ajax({
        type: "POST",
        url: './delProduct',
        datatype: 'json',
        data: {
            id: id
        }
    }).done(function (data) {

        if (data) {
            messageOk('Eliminado con éxito')
            $pagination.twbsPagination('destroy');
            listarProductoByVendedor()
        } else {
            messageError('=( up ocurrio un error')
        }

        $('#cargas').removeClass('is-active')

    })


}

$(document).on('click', '.editProduct', function (e) {

    e.preventDefault()

    let parent = $(this)[0].parentElement.parentElement
    let idPro = $(parent).attr('idProducto')
    $('#modalEdit').modal('show')

    let producto = records.find(element => element.idProducto === idPro)
    console.log(producto)

    generateImages(producto.imagen)
    document.getElementById('idProductoE').value = producto.idProducto
    document.getElementById('nameE').value = producto.nombreProducto
    document.getElementById('marcaE').value = producto.marcaProducto
    getCategorias(producto.idCategoriaFK, producto.categorys.nombreCategoria)
    document.getElementById('priceE').value = producto.valorProducto
    document.getElementById('cantidadE').value = producto.stockProducto
    document.getElementById('descripE').value = producto.descripcionProducto
    document.getElementById('enviosE').value = producto.diasEnvios
    document.getElementById('medidasE').value = producto.medidaProducto
    document.getElementById('empaqueE').value = producto.empaqueProducto
    document.getElementById('embalajeE').value = producto.embalajeProducto
    document.getElementById('ventajasE').value = producto.ventajaProducto

})

function queryEmphyP() {
    let select = document.getElementById('tabla');
    let str = `<div class="col-lg-3">
            </div>
                <div class="col-lg-6">
                <div class="alert alert-warning alert-dismissible fade show" role="alert">
              No hay elementos!<strong> Publique un producto </strong>
              <button type="button" class="close" data-dismiss="alert" aria-label="Close">
                <span aria-hidden="true">&times;</span>
              </button>
            </div>   
          </div>`
    select.innerHTML = str;
}


