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

function getImages(idpro, nume) {

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
    })

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
        str += getImages(item.idProducto, num)
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

}

$(document).on('click', '.watch', function (e) {

    e.preventDefault()
    let parent = $(this)[0].parentElement.parentElement
    let idPro = $(parent).attr('idProducto')
    let producto = arregloFinal.find(element => element.idProducto === idPro);
    $('#detailsProduct').modal('show')
    detailsProduct(producto)

})

function detailsProduct(producto) {
    
    caruselImagenes(producto.imagenes)
    textProduct(producto)

}

function textProduct(item) {
    
    let colors = getColors(item.idProducto)
    let producto = getProductByid(item.idProducto)
    
    let str = '';
    str += `<div id="detail" class="text-justify pt-2" precioProducto="${item.valorProducto}" idEmpresa="${item.idEmpresaFK}" idProducto="${item.idProducto}">
                <h2 class="h4 font-weight-bold mb-2 text-center">${item.nombreProducto}</h2>
            <hr>`
        
        str += `<a id="addItem" type="button" href="#" class="btn btn-primary btn-xs float-right hvr-push">`;
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
    let producto = arregloFinal.find(element => element.idProducto === idProducto);
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
    let producto = arregloFinal.find(element => element.idProductoColor === idProducto);
    messageAddCar('Agregado')
    addCar(producto, 1)

})

function getColors(id) {

    $('#cargas').addClass('is-active');
    
    let color = []
    
    $.ajax({
        type: "POST",
        url: './getColorsByProduct',
        async: false,
        datatype: 'json',
        data: {
            idProducto: id
        }
    }).done(function (data) {
        
        color = data

        $('#cargas').removeClass('is-active')

    })

    return color

}


 $(document).on('change','#colorSelect',function(){
    
    let idProductoColor = $(this).val()
    let str = ``
    
    $.ajax({
        type: "POST",
        url: './getStockProduct',
        async: false,
        datatype: 'json',
        data: {
            idProductoColor: idProductoColor
        }
    }).done(function (data) {
        
        if (data == 0) {
            str += `<option value="No">Agotado</option>`
        }
        
         for (var i = 1; i <= data; i++) {
            str += `<option>${i}</option>`
        }

    })
    
 document.getElementById('cantidadSelect').innerHTML = str  
    
});

function getProductByid(id) {

    $('#cargas').addClass('is-active');
    
    let pro = ''
    
    $.ajax({
        type: "POST",
        url: './obtenerProducto',
        async: false,
        datatype: 'json',
        data: {
            idProducto: id
        }
    }).done(function (data) {
        
        pro = data

        $('#cargas').removeClass('is-active')

    })

    return pro

}