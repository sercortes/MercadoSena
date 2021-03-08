var bande = 0
var firstTiime = 0

$(function () {

    $('#barraBusqueda').hide()

    $('#myCarousel').carousel({
        interval: 3000,
    })

    if (localStorage.getItem('objects') === null) {
        var arrayBuy = []
        localStorage.setItem('objects', JSON.stringify(arrayBuy))
    }

    updateIconNumber()

})


//function redirect() {
//    if (sessionStorage.getItem('idCompany') == null) {
//        window.location.replace('./logout');
//    }
//}

$(document).on('click', '#logoutFire', function () {

    sessionStorage.removeItem('idCompany')
    sessionStorage.removeItem('falls')

})

$(document).on('click', '#buttonSearch', function () {

    if (bande === 0) {

        $('#barraBusqueda').show('slow')

        bande = 1
        if (firstTiime === 0) {
            listarCategoriasS();
            getMarcas();
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

function messageAddCar(message) {
    Swal.fire({
        position: 'bottom-end',
        icon: 'success',
        title: message,
        showConfirmButton: false,
        timer: 1500
    }).then((result) => {

        $('#detailsProduct').modal('hide')
        showCar()

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
        } else {
            estatus = true
        }

    })

    return estatus;

}

function getRol() {

    let estatus = '';

    $.ajax({
        type: "POST",
        url: './getRol',
        async: false,
        datatype: 'json'
    }).done(function (data) {

        estatus = data

    })

    return estatus;
}


//$(document).on('click', '.facebook', function () {
//
//         window.open("https://www.facebook.com/sena");
//
//})
//
//$(document).on('click', '.telegram', function () {
//
//         window.open("https://www.facebook.com/sara");
//
//})