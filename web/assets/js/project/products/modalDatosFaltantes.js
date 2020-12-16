
function validarNumero(telefono) {
    if (/^([0-9]{5,13})*$/.test(telefono)) {
        return true;
    } else {
        return false;
    }
}


document.getElementById('datosfaltantes').addEventListener('input', e => {

    e.preventDefault();
    var form = $("#datosfaltantes");
    if (form[0].checkValidity() === false) {
        event.preventDefault();
        event.stopPropagation();
    } else {
        event.preventDefault();
        event.stopPropagation();
    }
    form.addClass('was-validated');

})

$('#datosfaltantes').submit(function (e) {

    e.preventDefault();
    e.stopPropagation();

    var formulario = $("#datosfaltantes");
    var datosVal = [
        documentoper = $('#name').val(),
        celularper = $('#celularUsuario').val(),
        telefonoper = $('#telefonoUsuario').val(),
        direccionper = $('#direccionUsuario').val()
    ];

    var arrayinputs = ["#name", "#celularUsuario", "#telefonoUsuario", '#direccionUsuario'];

    for (var i = 0; i < arrayinputs.length; i++) {
        $(arrayinputs[i]).removeClass('is-invalid');
    }
    //console.log(datosVal);

    if (documentoper === null || documentoper === '') {
        messageError('Por favor ingrese número de documento valido', '#name');
    } else if (celularper === null || celularper === '') {
        messageError('Por favor ingrese su primer nombre', '#celularUsuario');
    } else if (validarNumero(celularper) === false) {
        messageError('Por favor ingrese solo números', '#celularUsuario');
    } else if (telefonoper === null || telefonoper === '') {
        messageError('Por favor ingrese número', '#telefonoUsuario');
    } else if (validarNumero(telefonoper) === false) {
        messageError('Por favor ingrese solo numeros', '#telefonoper');
    } else if (direccionper === null || direccionper === '') {
        messageError('Por favor ingrese la direcion', '#direccionUsuario');
    } else {

        var arrayinputs = ["#name", "#celularUsuario", "#telefonoUsuario", '#direccionUsuario'];

        for (var i = 0; i < arrayinputs.length; i++) {
            $(arrayinputs[i]).removeClass('is-invalid').addClass('is-valid');
        }

        if ($('#datosfaltantes')[0].checkValidity() && valCampos(datosVal)) {
            event.preventDefault();
            event.stopPropagation();
            event.stopImmediatePropagation();
            var datos = $('#datosfaltantes').serialize();
            var btn = document.getElementById('senddatosfaltantes');
            $('#cargando').addClass('is-active');
            btn.disabled = true;
            $.ajax({
                url: "./actualizaUsuEmp?accion=actualizaDatosFaltantes&" + datos,
                type: 'POST',
                contentType: false,
                processData: false, error: function (jqXHR, textStatus, errorThrown) {
                    $('#cargando').removeClass('is-active');
                    messageInfo('Ha ocurrido un error con el servidor, favor intentar más tarde.');
                    btn.disabled = false;
                },
                success: function (data) {
                    $('#cargando').removeClass('is-active');
                    if (data === 'true') {
                        mensaje('Se actulizaron los datos correctamente!!');
                        setTimeout(function () {
                            $("#modaldatosfalltantes").modal('hide');
                        }, 1800);
                    } else {
                        messageError('El documento de identidad ya existe', '#documentoUsuario');
                        $('#documentoUsuario').removeClass('was-validated').addClass('form-control is-invalid');
                    }
                    formulario.addClass('was-validated');
                    btn.disabled = false;
                }
            });
        } else {
            formulario.addClass('was-validated');
        }

    }
});

function mensaje(mensaje) {
    Swal.fire({
        position: 'center',
        icon: 'success',
        title: 'Datos actulizados!',
        html: '<h4 style="color:#449d48;">' + mensaje + '</h4>',
        showCancelButton: false,
        showConfirmButton: false,
        allowOutsideClick: false,
        allowEscapeKey: false,
        allowEnterKey: false,
        padding: '2rem',
        width: '25%',
        timer: 1800
    });

}

function mensajesdeErrors(mensaje, caja) {
    Swal.fire({
        position: 'center',
        icon: 'error',
        title: 'Datos invalidos',
        html: '<h4 style="color:#f27474;">' + mensaje + '</h4>',
        showCancelButton: false,
        showConfirmButton: false,
        allowOutsideClick: false,
        allowEscapeKey: false,
        allowEnterKey: false,
        padding: '2rem',
        width: '25%',
        timer: 1200

    });
    $(caja).removeClass('border-success is-valid').addClass('is-invalid');

}