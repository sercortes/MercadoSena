
$(function () {

})

$(document).on('click', '#addCategoria', function (e) {

    e.preventDefault()
    $(".remove").remove();
    let categoria = document.getElementById('categoria').value
    if (categoria == '' || categoria.length > 400 || categoria.length <= 2) {
        checkInputGlobal('categoria', 'Escriba una categoría válida')
        return false
    }

    $('#cargas').addClass('is-active');
    $("#addCategoria").attr("disabled", true);
    generar('./createCategory', categoria, 'categoría')


})

$(document).on('click', '#addMarca', function (e) {

    e.preventDefault()
    $(".remove").remove();
    let marca = document.getElementById('marca').value
    if (marca == '' || marca.length > 400 || marca.length <= 2) {
        checkInputGlobal('marca', 'Escriba una marca válida')
        return false
    }
    $('#cargas').addClass('is-active');
    $("#addMarca").attr("disabled", true);
    generar('./createBrand', marca, 'marca')

})

function generar(url, palabra, anuncio) {

    $.ajax({
        url: url,
        type: 'POST',
        dataType: 'json',
        data: {nombre: palabra},
        async: true,
        success: function (data) {
            if (data) {
                messageOk('La ' + anuncio + ' ha sido creada')
                cleanForms()
            } else {
                messageInfo('Error ya ha sido creado un'+anuncio+'con ese nombre')
                cleanForms()
            }

        }
    })

}

function cleanForms() {
    document.getElementById('categoria').value = ''
    document.getElementById('marca').value = ''
    $('#cargas').removeClass('is-active');
    $("#addMarca").attr("disabled", false);
    $("#addCategoria").attr("disabled", false);
}



