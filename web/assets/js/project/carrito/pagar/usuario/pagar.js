idVenta = 0

function buyProducts(metodo) {

    let arraf = JSON.parse(localStorage.getItem('objects'));

    $.ajax({
        type: 'POST',
        url: './generateSale',
        datatype: 'json',
        data: {
            metodo: metodo, arrayP: JSON.stringify(arraf)
        }, error: function (datas) {
            messageInfo('Error en los productos')
        },
        success: function (datas) {

            idVenta = datas
            if (datas !== 0) {
                document.getElementById('ventaId').value = datas
                document.getElementById('ventaIds').value = datas
                cleanCar()
            } else {
                location.href = "/Store";
            }

        }
    })

}

$("#btnpagar").click(function () {

    if (!checkSession()) {
        modalPreguntaRegistro();
        return false
    }

    if (checkData()) {
        messageInfo('Necesitamos tu información para completar la compra')
        $('#modalUpdateData').modal('show')
        $('#modalCar').modal('hide')
        return false
    }

    valor = document.getElementById('valor').value;
    location.href = "process_payment?valor=" + valor;

});

$(document).on('click', '#btnUpdateData', function (e) {

    $(".remove").remove();
    e.preventDefault()
    let documento = document.getElementById('documento').value
    let direccion = document.getElementById('direccion').value
    let celular = document.getElementById('celular').value
    let telefono = document.getElementById('telefono').value

    if (documento === '') {
        checkInput('documentoUsu', 'Digite su número de documento')
        return false
    }

    if (documento.length > 15 || documento.length <= 5) {
        checkInput('documentoUsu', 'Digite un número de documento válido')
        return false
    }

    if (direccion === '' || direccion.length < 5) {
        checkInput('direccion', 'Digite su dirección')
        return false
    }

    if (celular === '' || celular.length < 5) {
        checkInput('celular', 'Digite su número de celular')
        return false
    }

    if (telefono === '') {
        checkInput('telefono', 'Digite su número de teléfono')
        return false
    }

    $("#btnUpdateData").attr("disabled", true);
    $('#cargas').addClass('is-active');

    $.ajax({
        type: "POST",
        url: './UpdateDataPerson',
        async: true,
        data: {
            documento: documento,
            direccion: direccion,
            celular: celular,
            telefono: telefono
        },
        datatype: 'json'
    }).done(function (data) {
        
        console.log(data)
        $('#cargas').removeClass('is-active');
        $("#btnUpdateData").attr("disabled", false);

        if (data) {
            messageInfo('Datos actualizados')
            valor = document.getElementById('valor').value;
            location.href = "process_payment?valor=" + valor;
        } else {
            messageError('Error')
            $('#modalUpdateData').modal('hide')
        }

    })

})

function checkInput(input, mensaje) {
    $('#' + input).focus().after(`<div class='remove'><font color='red'>${mensaje}</font><div>`);    
}

function cleanCar() {
    var arrayBuy = []
    localStorage.setItem('objects', JSON.stringify(arrayBuy))
}

