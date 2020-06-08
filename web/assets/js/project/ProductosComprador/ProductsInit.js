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

    $('#caruselDetails').carousel({
        interval: 2100,
    })


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

    let idcompanyss = document.getElementById('companyss').value
    let select = document.getElementById('tabla');
    let str = ``
    let num = 1

    for (var item of displayRecords) {

        if (idcompanyss !== item.idEmpresaFK) {
            str += `<div class="col-lg-3">
          <figure class="rounded p-3 bg-white shadow-sm" idProducto="${item.idProducto}" idEmpresa="${item.idEmpresaFK}">`

            str += `<div id="carouselExampleControls${num}" class="carousel slide hijueputa" data-ride="carousel">
                    <div class="carousel-inner" id="caruselOne${num}">`
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
      </div>`

            str += `<div class="text-right">
                            <a href="#" class="botonChat btn btn-primary"><i class="fas fa-comments"></i></a>
                    <a href="#" class="watch btn btn-primary"><i class="fas fa-images"></i></a>
                        </div>`

            str += `</figure>
        </div>`
            num++
        }


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

    setTimeout(() => $('.hijueputa').carousel({
            interval: 6100,
        }), 1000)

}

function textProduct(item) {
    let str = ''
    let element = document.getElementById('details')
    str += `  <div id="detail" class="text-justify pt-2" precioProducto="${item.valorProducto}" idEmpresa="${item.idEmpresaFK}" idProducto="${item.idProducto}">
<h2 class="h4 font-weight-bold mb-2 text-center">${item.nombreProducto}</h2>
    <a id="meInteresa" type="button" href="#" class="btn btn-primary btn-xs float-right hvr-push">
                     <i class="fas fa-gift"></i> Me interesa</a>
    <select class="form-control float-right" id="cantidadSelect" style="width:auto;height:auto;">`
    for (var i = 1; i <= item.stockProducto; i++) {
        str += `<option>${i}</option>`
    }
    str += `</select>
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
            str += `<div class="carousel-item">
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

$(document).on('click', '#meInteresa', function (e) {
    e.preventDefault()
    let parent = $(this)[0].parentElement
    let idEmpresa = $(parent).attr('idEmpresa')
    let idProducto = $(parent).attr('idProducto')
    let precio = $(parent).attr('precioProducto')
    let cantidad = $('#cantidadSelect').val()
    
    let datos = { 
        idEmpresa: idEmpresa,
        idProducto: idProducto,
        Cantidad:cantidad,
        Precio:precio
    }

    Swal.fire({
        title: 'Pregunta?',
        text: 'Deseas que el vendedor pueda ver tus datos para contactarte!',
        icon: 'info',
        showCancelButton: true,
        showCloseButton: true,
        cancelButtonText: 'No',
        confirmButtonText: 'Si',
    }).then((result) => {
        if (result.value) {
            messageOk('enviado')
            generateTables(datos, 1)
            datosVendedor(idEmpresa)

        } else if (result.dismiss === Swal.DismissReason.cancel) {
            messageError('cancelado')
            generateTables(datos, 0)
            datosVendedor(idEmpresa)
            
        }
    })

})

function generateTables(datos, contacto){
    
    datos.contacto = contacto
    console.log(datos)
    
     $.ajax({
        type: "POST",
        url: './generateSale',
        async: true,
        data:{
            cantidad:datos.Cantidad,
            contacto:datos.contacto,
            idEmpresa:datos.idEmpresa,
            idProducto:datos.idProducto,
            precioProducto:datos.Precio
        },
        datatype: 'json'
    }).done(function (data) {
  
          console.log(data)

    })
    
}

function datosVendedor(data){
  
  let datos;
  
  $.ajax({
        type: "POST",
        url: './getInfoCompanyByProduct',
        async: false,
        data:{
            idProducto:data
        },
        datatype: 'json'
    }).done(function (data) {
  
          datos = data

    })
  

Swal.fire({
  title: '<strong>Datos Vendedor</strong>',
  icon: 'success',
  html:
    'Nombre: ' + datos.nombreEmpresa+
    '<hr> ' +
    'Dirección: ' + datos.DirEmpresa+
    '<hr>' +
    'Celular: ' + datos.CelEmpresa+ 
    '<hr> ' +
    'Teléfono: ' + datos.telEmpresa+ 
    '<hr> ' +
    'Email: ' + datos.correoEmpresa+'',
  showCloseButton: true,
  focusConfirm: false,
  confirmButtonText:
    '<i class="fas fa-handshake"></i> Genial!',
  confirmButtonAriaLabel: 'Thumbs up, great!',
   width: 600,
  backdrop: `
    rgba(0,0,123,0.4)
    left top
    no-repeat`
})


}