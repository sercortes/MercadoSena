var arregloFinal = []
var $pagination = $('#pagination'),
        totalRecords = 0,
        records = [],
        displayRecords = [],
        recPerPage = 4,
        page = 1,
        totalPages = 0,
        initiateStartPageClick = true

$(function () {

    $('#cargas').addClass('is-active');
    $('.collapse').collapse()

    listarProductoByVendedor()


})

function listarProductoByVendedor() {

    $.ajax({
        type: "POST",
        url: './getProductsByDateTime',
        async: true,
        datatype: 'json'
    }).done(function (data) {

        $('#cargas').removeClass('is-active');

        if (data.length == 0) {
            queryEmphy()
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
            generateTableBuscador()
        }
    });
}

function getImages(idpro) {

    let str = ''

    $.ajax({
        type: "POST",
        url: './getImagesByProduct',
        async: false,
        data: {
            id: idpro
        },
        datatype: 'json'
    }).done(function (data) {

        let producto = records.find(item => item.idProducto === data[0].idProductoFK)
        producto.imagenes = data
        arregloFinal.push(producto)

        let num = 0;

        for (var item of data) {
            if (num === 0) {
                str += `<div class="carousel-item active">
                            <img class="img-fluid fit-image" src="${item.url}">
                        </div>`
            } else {
                str += `<div class="carousel-item ssss">
                            <img class="img-fluid fit-image" src="${item.url}">
                        </div>`
            }
            num++;
        }

    })
    return str
}

function getImagen(array) {
    let mensaje = ""
    if (array.length !== 0) {
        mensaje += `<img src="${array[0].url}" alt="" class="img-fluid fit-image">`
    }
    return mensaje;
}

function generateTableBuscador() {

    let select = document.getElementById('tabla');
    let str = ``
    let num = 1

    for (var item of displayRecords) {

        str += `<div class="col-lg-3">
          <figure class="rounded p-3 bg-white shadow-sm" idProducto="${item.idProducto}">`

        str += `<div id="carouselExampleControls${num}" class="carousel slide" data-ride="carousel">
                    <div class="carousel-inner" id="caruselOne">`
        str += getImages(item.idProducto)
        str += ` </div>
                    <a class="carousel-control-prev" href="#carouselExampleControls${num}" role="button" data-slide="prev">
                        <span class="carousel-control-prev-icon" aria-hidden="true"></span>
                        <span class="sr-only">Anterior</span>
                    </a>
                    <a class="carousel-control-next" href="#carouselExampleControls${num}" role="button" data-slide="next">
                        <span class="carousel-control-next-icon" aria-hidden="true"></span>
                        <span class="sr-only">Siguiente</span>
                    </a>
                </div>`

        str += `<figcaption class="p-3 card-img-bottom">
              <h2 class="h5 font-weight-bold mb-2">$ ${item.valorProducto.toString().replace(/(\d)(?=(\d\d\d)+(?!\d))/g, "$1.")}</h2>
              <h4 class="text-left text-muted">${item.nombreProducto}</h4>
            </figcaption>
       
      <div class="col-lg-12 mb-4 p-0">
       <a data-toggle="collapse" href="#collapseExample${num}" role="button" aria-expanded="false" aria-controls="collapseExample1" class="btn btn-primary btn-block py-2 shadow-sm with-chevron">
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
         <div class="text-right">
                            <a href="#" class="botonChat btn btn-primary"><i class="fas fa-comments"></i></a>
                            <a href="#" class="watch btn btn-primary"><i class="fas fa-images"></i> Ver más</a>
                        </div>
          </figure>
        </div>`
        num++
    }

    select.innerHTML = str;
}



$(document).on('click', '.watch', function (e) {

    e.preventDefault()
    let parent = $(this)[0].parentElement.parentElement
    let idPro = $(parent).attr('idProducto')
    let producto = arregloFinal.find(element => element.idProducto === idPro)
    $('#detailsProduct').modal('show')
    detailsProduct(producto)

})

function detailsProduct(producto) {
    caruselImagenes(producto.imagenes)
    textProduct(producto)
}

function textProduct(item) {
    let str = ''
    let element = document.getElementById('details')
    str += `<h2 class="h4 font-weight-bold mb-2 text-center">${item.nombreProducto}</h2>
              <p class="mb-0 text-small text-muted">Valor: $ ${item.valorProducto}</p>
              <p class="mb-0 text-small text-muted">Marca: ${item.marcaProducto}</p>
              <p class="mb-0 text-small text-muted">Categoría: ${item.categorys.nombreCategoria}</p>
              <p class="mb-0 text-small text-muted">Descripción : ${item.descripcionProducto}</p>`
    if (item.diasEnvios !== undefined) {
        str += `
        <hr>
        <div class="col-lg-12 mb-5 p-0">
       <a data-toggle="collapse" href="#collapseExample${item.idProducto}" role="button" aria-expanded="false" aria-controls="collapseExample1" class="btn btn-primary btn-block py-2 shadow-sm with-chevron">
          <p class="d-flex align-items-center justify-content-between mb-0 px-3 py-2"><strong class="text-uppercase">Información Adicional</strong><i class="fa fa-angle-down"></i></p>
        </a>
        <div id="collapseExample${item.idProducto}" class="collapse shadow-sm">
          <div class="card">
            <div class="card-body">
             <p class="mb-0 text-small text-muted">Días de envío: ${item.diasEnvios}</p>
         <p class="mb-0 text-small text-muted">Medidas : ${item.medidaProducto}</p>
         <p class="mb-0 text-small text-muted">Empaque : ${item.empaqueProducto}</p>
         <p class="mb-0 text-small text-muted">Embalaje : ${item.embalajeProducto}</p>
         <p class="mb-0 text-small text-muted">Ventajas : ${item.ventajaProducto}</p>
            </div>
          </div>
        </div>
      </div>`
    }
    element.innerHTML = str
}

function caruselImagenes(data) {
    let str = ''
    let ele = document.getElementById('carusel')
    let num = 0;
    for (var item of data) {
        if (num === 0) {
            str += `<div class="carousel-item active">
                            <img class="img-fluid fit-imageModal" src="${item.url}">
                        </div>`
        } else {
            str += `<div class="carousel-item ssss">
                            <img class="img-fluid fit-imageModal" src="${item.url}">
                        </div>`
        }
        num++;
    }
    ele.innerHTML = str

}

function queryEmphy() {
    let select = document.getElementById('tabla');
    let str =
            `<div class="col-lg-3">
</div>
    <div class="col-lg-6">
    <div class="alert alert-warning alert-dismissible fade show" role="alert">
  No hay elementos!<strong> :D</strong>
  <button type="button" class="close" data-dismiss="alert" aria-label="Close">
    <span aria-hidden="true">&times;</span>
  </button>
</div>
    
</div>`
    select.innerHTML = str;
}


