function geneP(idEvento) {

    $.ajax({
        url: './getInfoOneProduct',
        type: 'POST',
        data: {idPro: idEvento},
        dataType: 'json',
        async: true,
        success: function (data) {

            $('#detailsProduct').modal('show')
            let producto = data
            producto.imagenes = []
            let pro = {
                url: producto.imagenUnitaria
            }
            producto.imagenes.push(pro)
            detailsProductsVendedor(producto)

        }
    })

}

function detailsProductsVendedor(producto) {
    caruselImagenes(producto.imagenes)
    textProduct(producto)
}

