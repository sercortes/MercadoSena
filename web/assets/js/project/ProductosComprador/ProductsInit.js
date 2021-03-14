var colores = ''
var $pagination = $('#pagination'),
        totalRecords = 0,
        records = [],
        displayRecords = [],
        recPerPage = 4,
        page = 1,
        totalPages = 0,
        initiateStartPageClick = true

$(function () {

    if (window.location.pathname === '/Store/Searching...') {
        $('.collapse').collapse()
        $('#caruselDetails').carousel({
            interval: 2100,
        })
        $('#cargas').addClass('is-active');
        $('#pagee').show()
        listarProductoByDateTime()
    }

})

function listarProductoByDateTime() {

    $.ajax({
        type: "POST",
        url: './getProductsByDateTime',
        async: true,
        datatype: 'json'
    }).done(function (data) {

        generatePageQuery(data, 4)

    })

}

function generatePageQuery(data, pages) {

    $('#cargas').removeClass('is-active');
    $pagination.twbsPagination('destroy');
    recPerPage = pages

    if (data == undefined) {
        queryEmphy()
        return false
    }

    records = data
    totalRecords = data.length
    totalPages = Math.ceil(totalRecords / recPerPage)
    apply_pagination()
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

function getImages(data, nume) {

    let str = ''
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
    str += `</div>`
    if (data.length > 1) {
        str += `<a class="carousel-control-prev" href="#carouselExampleControls${nume}" role="button" data-slide="prev">
                        <span class="carousel-control-prev-icon" aria-hidden="true"></span>
                        <span class="sr-only">Anterior</span>
                    </a>
                    <a class="carousel-control-next" href="#carouselExampleControls${nume}" role="button" data-slide="next">
                        <span class="carousel-control-next-icon" aria-hidden="true"></span>
                        <span class="sr-only">Siguiente</span>
                    </a>`
    }

    return str

}

function generateTableBuscador() {

    let str = ``
    let num = 1

    for (var item of displayRecords) {

        str += `<div class="col-lg-3">
          <figure class="rounded p-3 bg-white shadow-sm" idProducto="${item.idProducto}" idEmpresa="${item.idEmpresaFK}">`
        str += `<div id="carouselExampleControls${num}" class="carousel slide hijueputa" data-ride="carousel">
                    <div class="carousel-inner" id="caruselOne${num}">`
        str += getImages(item.listaImagenes, num)
        str += `</div>
            <figcaption class="p-2 card-img-bottom">
        <hr>
              <h2 class="letrasbanner text-left text-muted mb-0 img-fluid fit-text">${item.nombreProducto.toString().substr(0, 36)}</h2>
              <h2 class="h5 text-left font-weight-bold mb-2 precios">$ ${money(item.valorProducto)}</h2>
            </figcaption>
      <div class="col-lg-12 mb-4 p-0">
       <a data-toggle="collapse" href="#collapseExamples${num}" role="button" aria-expanded="false" aria-controls="" class="btn btn-primary btn-block py-2 shadow-sm with-chevron">
          <p class="d-flex align-items-center justify-content-between mb-0 px-3 py-2"><strong class="text-uppercase">Descripción</strong><i class="fa fa-angle-down"></i></p>
        </a>
        <div id="collapseExamples${num}" class="collapse shadow-sm">
          <div class="card">
            <div class="card-body" idProducto="${item.idProducto}" idEmpresa="${item.idEmpresaFK}">
              <p class="mb-0 text-muted textoDes">${item.descripcionProducto.toString().substr(0, 150)} <a href="#" class="watch naranja">Ver más <i class="fas fa-angle-double-right fa-lg naranja iconodemas"></i></a></p>
            </div>
          </div>
        </div>
      </div>`

        str += `<div class="text-right" idProductoColor="${item.idProductoColor}">
                            <a href="#" class="botonChat btn btn-primary"><i class="fas fa-comments"></i></a>
                            <a href="#" class="watch btn btn-primary"><i class="fas fa-images"></i></a>
                            <a href="#" class="addProductOne btn btn-primary"><i class="fas fa-plus-square"></i></a>
                        </div>`
        str += `</figure>
        </div>`
        num++

    }

    document.getElementById('tabla').innerHTML = str;
    setTimeout(() => $('.hijueputa').carousel({
            interval: 3100,
        }), 1000)

    console.timeEnd('loop');

}

$(document).on('click', '.watch', function (e) {

    e.preventDefault()
    let parent = $(this)[0].parentElement.parentElement
    let idPro = $(parent).attr('idProducto')
    let producto = records.find(element => element.idProducto === idPro);  
    $('#detailsProduct').modal('show')
    detailsProduct(producto)

})

function detailsProduct(producto) {
  
    caruselImagenes(producto.listaImagenes)
    textProduct(producto)

}

function textProduct(item) {

    let rol = getRol()
    let producto = getProductByid(item.idProducto)
    colores = producto.listaColores

    let str = '';
    str += `<div id="detail" class="text-justify pt-2" precioProducto="${item.valorProducto}" idEmpresa="${item.idEmpresaFK}" idProducto="${item.idProducto}">
                <h2 class="h4 font-weight-bold mb-2 text-center">${item.nombreProducto}</h2>
            <hr>`

    if (rol == 3) {
        str += `<a id="" type="button" href="#" class="addItemVendedor btn btn-primary btn-xs float-right hvr-push">`;
    } else {
        str += `<a id="addItem" type="button" href="#" class="btn btn-primary btn-xs float-right hvr-push">`;
    }

    str += `<i class="fas fa-shopping-cart"></i> Añadir al carrito</a>
            <select class="form-control float-right" id="cantidadSelect" style="width:auto;height:auto;margin-right: 2%;">`;
    for (var i = 1; i <= item.stockProducto; i++) {
        str += `<option>${i}</option>`
    }
    str += `</select>
            <select class="form-control float-right" id="colorSelect" style="width:auto;height:auto;margin-right: 2%;">`;
    str += `<option value="${item.idProductoColor}">${item.color}</option>`
    for (var it of producto.listaColores) {
        if (it.idColor !== item.idProductoColor) {
            str += `<option value="${it.idColor}">${it.nombreColor}</option>`
        }
    }
    str += `</select>`

    str += `<p class="font-weight-bold text-muted h5 text-left">$ ${money(item.valorProducto)}</p>
              <h4 class="mb-0 pb-2 text-left">Marca: ${producto.marcaProducto}</h4>
             <div class="row">
                <div class="col-md-9">
                    <h5 class="font-weight-bold text-muted h6 text-left">Referencia: ${producto.referencia}</h5>
                </div>
                <div class="col-md-3">
                    <h5 class="font-weight-bold text-muted h6 text-right">Color: ${item.color}</h5>
               </div>
            </div>
           <div class="card shadow-sm">
            <div class="card-body">
              <p class="mb-0 text-small text-muted textoDes text-left">Descripción</p>
              <p class="mb-0 text-small text-muted textoDes text-left">${item.descripcionProducto}</p>
            </div>
          </div>`

    str += `<hr>
        <div class="col-lg-12 mb-5 p-0">
       <a data-toggle="collapse" href="#collapseExample${item.idProducto}" role="button" aria-expanded="false" aria-controls="" class="btn btn-primary btn-block py-2 shadow-sm with-chevron">
          <p class="d-flex align-items-center justify-content-between mb-0 px-3 py-2"><strong class="text-uppercase">Información Adicional</strong><i class="fa fa-angle-down"></i></p>
        </a>
        <div id="collapseExample${item.idProducto}" class="collapse shadow-sm">
          <div class="card">
            <div class="card-body">
         <p class="mb-0 text-small text-muted textoDes text-left"><b>Garantía : </b>${producto.garantia}</p>
             <p class="mb-0 text-small text-muted"><b>Días de envío : </b> ${producto.diasEnvios}</p>
         <p class="mb-0 text-small text-muted textoDes text-left">${producto.ventajaProducto}</p>
         <p class="mb-0 text-small text-muted"><b>Medidas : </b> ${producto.medidaProducto}</p>
         <p class="mb-0 text-small text-muted"><b>Empaque : </b>${producto.empaqueProducto}</p>
         <p class="mb-0 text-small text-muted"><b>Embalaje : </b>${producto.embalajeProducto}</p>
                </div>
            </div>
          </div>
        </div>
      </div>
    </div>`
    document.getElementById('details').innerHTML = str
}

function caruselImagenes(data) {
    let str = ''
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

    document.getElementById('carusel').innerHTML = str

    if (data.length > 1) {
        document.getElementById('controlescarru').innerHTML =
                ` <a class="carousel-control-prev" href="#caruselDetails" role="button" data-slide="prev">
                        <span class="carousel-control-prev-icon" aria-hidden="true"></span>
                        <span class="sr-only">Anterior</span>
                    </a>
                    <a class="carousel-control-next" href="#caruselDetails" role="button" data-slide="next">
                        <span class="carousel-control-next-icon" aria-hidden="true"></span>
                        <span class="sr-only">Siguiente</span>
                    </a>`

        setTimeout(() => $('.slides').carousel({
                interval: 4100,
            }), 1000)

    } else {
        document.getElementById('controlescarru').innerHTML = ``
    }

}

$(document).on('click', '#addItem', function (e) {

    e.preventDefault();
    let ass = getRol()
    if (ass == 3) {
        messageInfo('No puedes agregar el articulo')
        return false
    }
    let parent = $(this)[0].parentElement
    let idProducto = $(parent).attr('idProducto')
    let producto = records.find(element => element.idProducto === idProducto);
    this.disabled = true
    let cantidad = parseInt($('#cantidadSelect').val())
    if (isNaN(cantidad)) {
        messageInfo('Agotado')
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

$(document).on('click', '.addProductOne', function (e) {

    e.preventDefault()
    let ass = getRol()
    if (ass == 3) {
        messageInfo('No puedes agregar el producto')
        return false
    }
    let parent = $(this)[0].parentElement
    let idProducto = $(parent).attr('idProductoColor')
    let producto = records.find(element => element.idProductoColor === idProducto);
    messageAddCar('Agregado')
    addCar(producto, 1)

})

$(document).on('change', '#colorSelect', function () {
    
    let idProductoColor = $(this).val()
    let obj = colores.find(item => item.idColor === idProductoColor)
    
    let str = ``
  
        for (var i = 1; i <= obj.cantidad; i++) {
            str += `<option>${i}</option>`
        }

    document.getElementById('cantidadSelect').innerHTML = str

});

