var colores = []
$(document).on('click', '.editQuantity', function (e) {

    e.preventDefault()
    let parent = $(this)[0].parentElement.parentElement
    let idPro = $(parent).attr('idProducto')
    let producto = records.find(element => element.idProducto === idPro)
    console.log(producto)
    $('#modalEdit2').modal('show')

    document.getElementById('salida').innerHTML =
            `<div class="alert alert-warning alert-dismissible fade show" role="alert">
                <p>${producto.nombreProducto}.</p>
                <hr>
                <p class="mb-0">Referencia:${producto.referencia}</p>
              </div>`

    let productoCom = getProductByid(producto.idProducto)
    colores = productoCom.listaColores
    console.log(productoCom)
    let str = ''
    for (var item of productoCom.listaColores) {
        str += `<div class="form-row" id="product${item.idColor}" idProducto="${item.idColor}" Color="${item.idColor}">
                        <div class="form-group col-md-5">
                            <input type="text" class="form-control" value="${item.nombreColor}" minlength="2" max="40" placeholder="Color del producto" disabled>
                        </div>
                        <div class="form-group col-md-5">
                            <input type="number" class="form-control cantidad" value="${item.cantidad}" minlength="0" max="100000000" placeholder="Stock">
                        </div>
                        <div class="col-md-2 mt-1">
                             <div class="btn-group" role="group" aria-label="Basic example">
                                <button type="button" class="btn btn-secondary del">-</button>
                                <button type="button" class="btn btn-primary add">+</button>
                            </div>
                        </div>                  
                    </div><hr>`;
    }
    document.getElementById('colores').innerHTML = str;

})

$(document).on('click', '.add', function (e) {

    e.preventDefault()
    let parent = $(this)[0].parentElement.parentElement.parentElement
    let input = parent.querySelector('.cantidad')
    let valor = parseInt(input.value)
    if (valor >= 100000000) {
        return false
    }
    valor++
    input.value = valor
    let idPro = $(parent).attr('idProducto')
    let producto = colores.find(element => element.idColor === idPro)
    producto.cantidad = valor

})

$(document).on('click', '.del', function (e) {

    e.preventDefault()
    let parent = $(this)[0].parentElement.parentElement.parentElement
    let input = parent.querySelector('.cantidad')
    let valor = parseInt(input.value)
    if (valor <= 0) {
        return false
    }
    valor--
    input.value = valor
    let idPro = $(parent).attr('idProducto')
    let producto = colores.find(element => element.idColor === idPro)
    producto.cantidad = valor

})


$(document).on('change', '.cantidad', function (e) {

    e.preventDefault()
    let parent = $(this)[0].parentElement.parentElement
    let input = parent.querySelector('.cantidad')
    let valor = parseInt(input.value)
    if (valor >= 100000000) {
        input.value = 1
        return false
    }
    if (valor <= 0) {
        input.value = 1
        return false
    }
    let idPro = $(parent).attr('idProducto')
    let producto = colores.find(element => element.idColor === idPro)
    producto.cantidad = valor

})

$(document).on('click', '#saveColors', function (e) {

    e.preventDefault()
    $('#cargas').addClass('is-active');
    $.ajax({
        type: "POST",
        enctype: 'multipart/form-data',
        url: "updateStockColors?arrayP=" + JSON.stringify(colores),
        success: function (data) {
            if (data !== 0) {
                messageOk('Actualizado con éxito')
                mensajess()
                $pagination.twbsPagination('destroy');
                listarProductoByVendedor()
            } else {
                messageInfo('Error')
                mensajess()
            }
        }
    });


})

function mensajess() {

    $('#modalEdit2').modal('hide')
    $('#cargas').removeClass('is-active');
}