
$(document).on('click', '#modalAdd', function (e) {
    e.preventDefault()
    $('#modalA').modal('show');
})



$(document).on('click', '#addVendedor', function (e) {
    e.preventDefault()
    $(".remove").remove();

    let name = document.getElementById('name').value
    let surname = document.getElementById('surname').value
    let email = document.getElementById('email').value

    if (name == '') {
        input('name', 'falta el nombre')
        return false
    }

    if (surname == '') {
        input('surname', 'falta el apellido')
        return false
    }

    if (email == '') {
        input('email', 'falta el correo')
        return false
    }

    if (!validarEmail(email)) {
        input('email', 'Ingresa un correo válido')
        return false
    }
    $('#cargas').addClass('is-active');
    $("#addVendedor").attr("disabled", true);

    $.ajax({
        type: "POST",
        url: './createSeller',
        data: {
            correoUsuario: email,
            nombreUsuario: name,
            apellidoUsuario: surname
        },
        async: true,
        datatype: 'json'
    }).done(function (data) {

        cleann()

        if (data === 1) {
            messageOk('Hemos enviado un correo con sus datos de ingreso para la cuenta.');
        } else if (data === 2) {
            messageInfo(email + ' ¡El Correo se encuentra registrado!');
        } else if (data === 3) {
            messageError('Error en la conexión, intente más tarde');
        }

    })

})

function cleann() {

    $('#cargas').removeClass('is-active');
    $("#addVendedor").attr("disabled", false);
    document.getElementById('name').value = ""
    document.getElementById('surname').value = ""
    document.getElementById('email').value = ""

}

function validarEmail(correo) {
    if (/^[a-zA-Z0-9._-]+@[a-zA-Z0-9.-]+\.([a-zA-Z]{2,4})+$/.test(correo)) {
        return true;
    } else {
        return false;
    }
}