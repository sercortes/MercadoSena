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

    if (checkSession()) {
        getNotifys()
    }

    checkSaless()
    updateIconNumber()

})

function checkSaless() {

    if (getRol() == 3) {
        getNotifySales()
    }

}

function checkInputGlobal(input, mensaje) {
    $('#' + input).focus().after(`<div class='remove'><font color='red'>${mensaje}</font><div>`);    
}

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
        title: 'Ok',
        text: message
    })
}

function messageInfo(message) {
    Swal.fire({
        icon: 'info',
        title: 'Espera',
        text: message
    })

}
function messageError(message) {
    Swal.fire({
        icon: 'error',
        title: 'Error',
        text: message
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


function checkData() {

    let estatus = '';

    $.ajax({
        type: "POST",
        url: './checkUpdateData',
        async: false,
        datatype: 'json'
    }).done(function (data) {

        estatus = data

    })

    return estatus;

}

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

function getProductByidComplete(id) {

    $('#cargas').addClass('is-active');
    let pro = ''

    $.ajax({
        type: "POST",
        url: './getProductoComplete',
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

function checkBloqueo() {

    let estatus = '';

    $.ajax({
        type: "POST",
        url: './getComprasIncomplete',
        async: false,
        datatype: 'json'
    }).done(function (data) {

        estatus = data

    })

    return estatus;

}

function queryEmphy() {
    $('#cargas').removeClass('is-active');
    let str =
            `<div class="col-lg-3">
                </div>
            <div class="col-lg-6">
            <div class="alert alert-warning alert-dismissible fade show" role="alert">
            Sin resultados! <strong>${document.getElementById('nombreProductoFiltar').value}</strong>
            <button type="button" class="close" data-dismiss="alert" aria-label="Close">
            <span aria-hidden="true">&times;</span>
            </button>
            </div>
            </div>`
    document.getElementById('tabla').innerHTML = str;
}

function checkProducts() {

    let arraf = JSON.parse(localStorage.getItem('objects'));
    let datas = '';

    $.ajax({
        type: 'POST',
        url: './checkProducts',
        datatype: 'json',
        async: false,
        data: {
            arrayP: JSON.stringify(arraf)
        }
    }).done(function (data) {

        if (data) {
            datas = true
        } else {
            cleanCar()
            datas = false
        }

    })

    return datas;

}

function mensajeRedirectHome(mensaje) {

    Swal.fire({
        title: 'Error',
        text: mensaje,
        icon: 'info',
        showCancelButton: false,
        confirmButtonColor: '#3085d6',
        cancelButtonColor: '#d33',
        confirmButtonText: 'Ok'
    }).then((result) => {
        window.location.replace("../Store/");
    })

}

function checkByuss2() {

    $.ajax({
        type: "POST",
        url: './CheckBuys',
        async: true,
        datatype: 'json'
    }).done(function (data) {

    })

}

function getNotifys() {

    $.ajax({
        url: './notifyNormal',
        type: 'POST',
        dataType: 'json',
        success: function (data) {

            if (data <= 0) {
                $('#numberchat').text('')
                return false
            }

            if (data >= 10) {
                $('#numberchat').text('+10')
            } else {
                $('#numberchat').text(data)
            }

        }
    })

}

function getNotifySales() {

    $.ajax({
        url: './notifySales',
        type: 'POST',
        dataType: 'json',
        success: function (data) {

            if (data <= 0) {
                $('#numbersales').text('')
                return false
            }

            if (data >= 10) {
                $('#numbersales').text('+10')
            } else {
                $('#numbersales').text(data)
            }

        }
    })

}

$(document).on('click', '#salirr', function (e) {

    e.preventDefault()
    onClose()
    window.location.replace('./logout');

})

function dynamicSort(property) {
    var sortOrder = 1;
    if (property[0] === "-") {
        sortOrder = -1;
        property = property.substr(1);
    }
    return function (a, b) {
        /* next line works with strings and numbers, 
         * and you may want to customize it to your needs
         */
        var result = (a[property] < b[property]) ? -1 : (a[property] > b[property]) ? 1 : 0;
        return result * sortOrder;
    }
}