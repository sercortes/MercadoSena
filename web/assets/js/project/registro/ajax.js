
function modalRegistro() {

    $('#modalRegistro').show(400);
}

$('#registroUsuario').submit(function (e) {

    // $('#registrarUsuario').click(function(e) {
    e.preventDefault();
    e.stopPropagation();

    var formulario = $("#registroUsuario");
    var datosVal = [
        nombreUsuario = $('#nombreUsuario').val(),
        apellidoUsuario = $('#apellidoUsuario').val(),
        correoUsuario = $('#correoUsuario').val(),
        celularUsuario = $('#celularUsuario').val(),
        documentoUsuario = $('#documentoUsuario').val(),
        ciudadUsuario = $('#ciudadUsuario').val(),
        tipoDocUsuario = $('#tipoDocUsuario').val,
        generoUsuario = $('#generoUsuario').val()

    ];

    for (var i = 0; i < formulario.datosVal; i++) {
        $(datosVal[i]).removeClass('is-invalid');
    }

    if ($('#registroUsuario')[0].checkValidity() && valCampos(datosVal) && validarClave()) {

        if (validarLetras(nombreUsuario) === false) {
            messageInfo('Por favor ingrese solo letras, sin tíldes ni caracteres especiales');
        } else if (validarLetras(apellidoUsuario) === false) {
            messageInfo('Por favor ingrese solo letras');
        } else {

            let terminos = $('#terminosYcondiciones').is(":checked");
            if (terminos === false) {
                messageInfo('Por favor, acepte los terminos y condiciones, sin tíldes ni caracteres especiales')
                return false
            }

            $('#carga').addClass('is-active');
            event.preventDefault();
            event.stopPropagation();
            event.stopImmediatePropagation();
            var datos = $('form#registroUsuario').serialize();
            var btn = document.getElementById('registrarUsuario');
            btn.disabled = true;
            $('#carga').addClass('is-active');
            $.ajax({
                url: "./registro?accion=registrarUsuario&" + datos,
                type: 'POST',
                contentType: false,
                processData: false,
                error: function (jqXHR, textStatus, errorThrown) {
                    btn.disabled = false;
                    $('#carga').removeClass('is-active');
                    modalRegistro();
                    messageInfo('Ha ocurrido un error con el servidor, favor intentar más tarde.');
                },
                success: function (data) {

                    $('#carga').removeClass('is-active');
                    modalRegistro();
                    if (data === 1) {
                        messageOk('Hemos enviado un correo con sus datos de ingreso y el link de activación para su cuenta.');
                        $('#exampleModa3').modal('hide');
                    } else if (data === 2) {
                        messageInfo('¡El Correo se encuentra registrado!');
                    } else if (data === 3) {
                        messageError('Error en la conexión, intente más tarde');
                    }

                    limpiarFormulario('#registroUsuario');
                    formulario.addClass('was-validated');
                    btn.disabled = false;
                }
            })

        }

    } else {

        formulario.addClass('was-validated');

    }
})

function validarLetras(telefono) {
    if (/^([a-z A-Z-ñáéíóú ])*$/.test(telefono)) {
        return true;
    } else {
        return false;
    }
}

function validarNumero(telefono) {
    if (/^([0-9])*$/.test(telefono)) {
        return true;
    } else {
        return false;
    }
}

function validarEmail(correo) {
    if (/^[a-zA-Z0-9._-]+@[a-zA-Z0-9.-]+\.([a-zA-Z]{2,4})+$/.test(correo)) {
        return true;
    } else {
        return false;
    }
}

function validarClave() {
    var con1 = $('#clave1').val();
    var con2 = $('#clave2').val();
    if (con1.length >= 10) {
        $('#spValidar1').empty();
        if (con2.length >= 10) {
            if (con1 !== con2) {

                $('#spValidar2').empty();
                $('#spValidar2').html('Las cotraseñas no conciden');

                return false;
            } else {
                $('#spValidar2').empty();
                $('#spValidar1').empty();
                return true;
            }
        } else {
            $('#spValidar2').empty();
            $('#spValidar2').html('Mínimo 10 carácteres');

        }
    } else {
        $('#spValidar1').empty();
        $('#spValidar1').html('Mínimo 10 carácteres');
    }

}

document.getElementById('registroUsuario').addEventListener('input', e => {

    e.preventDefault();
    var form = $("#registroUsuario");
    if (form[0].checkValidity() === false) {
        event.preventDefault();
        event.stopPropagation();
    } else {
        event.preventDefault();
        event.stopPropagation();
    }
    form.addClass('was-validated');

})

function valCampos(campos) {
    var rta = true;
    for (var i = 0; i < campos.length; i++) {
        if (campos[i] === '' || campos[i] === null) {
            rta = false;
            // alert('akskhhd');
            return rta;
        }
    }
    return rta;
}

function limpiarFormulario(formularioRec) {
    var formulario = document.querySelector(formularioRec);
    var campos = formulario.querySelectorAll("input, select");

    for (var i = 0; i < campos.length; i++) {
        var campo1 = campos[i];

        if (campo1.nodeName === "INPUT" && campo1.type !== "submit" && campo1.type !== "hidden")
            campo1.value = "";
        else if (campo1.nodeName === "SELECT")
            campo1.selectedIndex = 0;
    }
    // campo1[0].focus();
}

function cerrarmodla() {
    location.reload();
}
