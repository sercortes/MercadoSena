var bande = 0
var firstTiime = 0

$(function () {

    $('#barraBusqueda').hide()

    $('#myCarousel').carousel({
        interval: 3000,
    })

})

function redirect() {
    if (sessionStorage.getItem('idCompany') == null) {
        window.location.replace('./logout');
    }
}

$(document).on('click', '#logoutFire', function () {

    sessionStorage.removeItem('idCompany')
    sessionStorage.removeItem('falls')

})

$(document).on('click', '#buttonSearch', function () {
    
    if (bande === 0) {
        
        $('#barraBusqueda').show('slow')
        
        bande = 1
        if (firstTiime === 0) {
            consultaCiudadS();
            listarCategoriasS();
            vendedoresS();
        }
        firstTiime++

    } else {
        $('#barraBusqueda').hide('slow')
        bande = 0
    }

})

function messageOk(message) {
    Swal.fire({
        icon: 'success',
        html: '<h4 style="color:#060e06">' + message + '</h4>',
        showConfirmButton: true,
        width: '25%'
    })
}

function messageInfo(message) {
    Swal.fire({
        icon: 'info',
        html: '<h4 style="color:#060e06">' + message + '</h4>',
        showConfirmButton: true,
        width: '25%'
    })
}
function messageError(message) {
    Swal.fire({
        icon: 'error',
        html: '<h4 style="color:#060e06">' + message + '</h4>',
        showConfirmButton: true,
        width: '25%'
    })
}

function cerrar(id) {

    $(id).hide(400)
    $('#bloqueo').hide();

}

function checkSession() {

    let estatus = '';

    $.ajax({
        type: "POST",
        url: './checkSession',
        async: false,
        datatype: 'json'
    }).done(function (data) {

        if (data === false) {
            estatus = false
        }else{
            estatus = true
        }

    })
    
    return estatus;

}
