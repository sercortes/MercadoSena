function recuperaClave() {
    $('#exampleModal').modal('hide');
//    consultaTipoDoc('', '#tipoDocActu');
    modalRecuperar();
}

function  modalRecuperar() {
    $('#bloqueo').toggle();
    $('#modalRecuperarClave').toggle();
    limpiarFormulario('#recuperarClave');
}

$(document).on('click', '#btnRecuperarClave', function (e) {

    e.preventDefault();
    $('.remove').remove()
    let email = document.getElementById('correoE').value
    let catchas = grecaptcha.getResponse();

    if (email == '' || email.length <= 0) {
        checkInputGlobal('correoE', 'Ingrese el correo')
        return false
    }

    if (!ValidateEmails(email)) {
        checkInputGlobal('correoE', 'Ingrese un correo válido')
        return false
    }

    if (catchas.length == 0) {
        checkInputGlobal('mensajes', 'Complete la captcha')
        return false
    }

     $('#cargas').addClass('is-active');
     $('#btnRecuperarClave').attr('disabled', true);

    $.ajax({
        url: "./actualizaUsuEmp?accion=recuperarClave",
        type: 'POST',
        data: {
            rec: catchas,
            email: email
        }
    }).done(function (data) {

            modalRecuperar()
            $('#cargas').removeClass('is-active');
            $('#btnRecuperarClave').attr('disabled', false);
            grecaptcha.reset()
            
        switch (data) {
            case 0:
                messageInfo('Error catcha')
                break;
            case 1:
                messageInfo('usuario no existe')
                break;
            case 2:
                messageInfo('Errorr')
                break;
            case 3:
                messageInfo('hemos enviado un correo con las instrucciones para ingresar al sistema')
                break;

            default:

                break;
        }

    })


})
function ValidateEmails(mail)
{
    if (/^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@[a-zA-Z0-9-]+(?:\.[a-zA-Z0-9-]+)*$/.test(mail))
    {
        return (true)
    }
    return (false)
}