$(document).on('click', '#send', function (e) {

    e.preventDefault()
    console.log(idProducto)

    let aprobar = document.getElementById('aprobar').checked
    let eliminar = document.getElementById('eliminar').checked
    let comment = $('#observacion').val()

    if (aprobar == false && eliminar == false) {
        messageInfo('Selecione un estado para el producto')
        return false
    }

    if (comment == '' || comment == undefined || comment.length <= 5) {
        messageInfo('Escriba una observación de mínimo 6 caracteres')
        return false
    }

    updateProduct(idProducto, aprobar, comment)


})

function updateProduct(idProducto, aprobar, comment) {

    $.ajax({
        type: "POST",
        url: './updateStatusProduct',
        data: {
            idProducto: idProducto,
            aprobar: aprobar,
            comment: comment
        },
        async: true
    }).done(function (data) {

        if (data) {
            messageOk('actualizado')
            cleanForms()
        } else {
            messageInfo('Error')
        }

    })

}

function cleanForms() {
    getProductsCheck()
   $('#formularioEva').trigger('reset')
    $('#observacion').val('')
    $('#modal-top').modal('hide')
}