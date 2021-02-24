var records = []
var arrayFinal = []

$(function () {

    $('#cargas').addClass('is-active');
    productosRamdom()

})

function productosRamdom() {

    $.ajax({
        type: "POST",
        url: './getProductsRandom',
        async: true,
        datatype: 'json'
    }).done(function (data) {

        records = data;
        for (var item of data) {
            getImages(item.idProducto)
        }

        console.log(arrayFinal)
        generateTable()

    })

}

function getImages(idpro) {

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
        arrayFinal.push(producto)

    })

}

function generateTable() {

    $('#cargas').removeClass('is-active');
    let str = ``
    for (var item of arrayFinal) {
        str += ` <tr>
                  <th scope="row">
                    <div class="p-2">
                      <img src="${item.imagenes[0].url}" alt="" width="70" class="img-fluid rounded shadow-sm">
                      <div class="ml-3 d-inline-block align-middle">
                        <h5 class="mb-0"><a href="#" class="text-dark d-inline-block">${item.nombreProducto}</a></h5><span class="text-muted font-weight-normal font-italic">Categoría:${item.categorys.nombreCategoria}</span>
                      </div>
                    </div>
                  </th>
                  <td class="align-middle"><strong>$${money(item.valorProducto)}</strong></td>
                  <td class="align-middle"><strong>`
        str += `<select class="form-control float-right" id="cantidadSelect" style="width:auto;height:auto;margin-right: 2%;">`;
        for (var i = 1; i <= item.stockProducto; i++) {
            str += `<option>${i}</option>`
        }
        str += `</strong></td>
                  <td class="align-middle">
                    <a id="addItem" href="#" class="text-dark"><i class="fas fa-plus-square fa-3x naranja"></i></a>
                  </td>
                </tr>`
    }
    document.getElementById('tabla').innerHTML = str;

}

function money(dolar) {
    return dolar.toString().replace(/(\d)(?=(\d\d\d)+(?!\d))/g, "$1.")
}