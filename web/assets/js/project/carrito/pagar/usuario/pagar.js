idVenta = 0

function buyProducts(metodo) {

    let arraf = JSON.parse(localStorage.getItem('objects'));
    let datas = '';

    $.ajax({
        type: 'POST',
        url: './generateSale',
        datatype: 'json',
        async: false,
        data: {
            metodo: metodo, arrayP: JSON.stringify(arraf)
        }
    }).done(function (data) {
        
        if (data !== 0 || data !== 00) {
                cleanCar()
                datas = false
            } else {
                cleanCar()
                datas = true
            }

    })

    return datas;

}


$("#btnpagar").click(function () {

    if (!checkSession()) {
        modalPreguntaRegistro();
        return false;
    }

    if (checkBloqueo() >= 4) {
        messageInfo('Acceso restringido, tienes compras sin completar');
        return false;
    }

    if (checkData()) {
        messageInfo('Necesitamos tu información para completar la compra');
        $('#modalUpdateData').modal('show');
        $('#modalCar').modal('hide');
        return false;
    }
         
     $.post(location.href = "process_payment");
    
});





$(document).on('click', '#btnUpdateData', function (e) {

    $(".remove").remove();
    e.preventDefault()
    let documento = document.getElementById('documento').value
    let direccion = document.getElementById('direccion').value
    let celular = document.getElementById('celular').value
    let telefono = document.getElementById('telefono').value

    if (documento === '') {
        checkInput('documento', 'Digite su número de documento')
        return false
    }

    if (documento.length > 15 || documento.length <= 5) {
        checkInput('documento', 'Digite un número de documento válido')
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

        $('#cargas').removeClass('is-active');
        $("#btnUpdateData").attr("disabled", false);
        
        if (data) {
            
            Swal.fire({
                title: 'OK',
                text: 'Actualizado',
                icon: 'info',
                showCancelButton: false,
                confirmButtonColor: '#3085d6',
                cancelButtonColor: '#d33',
                confirmButtonText: 'Ok'
            }).then((result) => {
                $.post(location.href = "process_payment");
            })
            
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
