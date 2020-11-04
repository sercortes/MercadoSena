var bande = 0
var firstTiime = 0

$(function () {

    $('#barraBusqueda').hide()

    $('#myCarousel').carousel({
        interval: 3000,
    })

//    if (window.location.pathname !== "/MercadoSena/") {
//        oculMost()
//    }

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
        title: message,
        showConfirmButton: true
    })
}

function messageInfo(message) {
    Swal.fire({
        icon: 'info',
        html: '<h4 style="color:#595959">' + message + '</h4>',
        showConfirmButton: true
    })
}
function messageError(message) {
    Swal.fire({
        icon: 'error',
        title: message,
        showConfirmButton: true
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
    console.log(estatus)
    return estatus;

}
