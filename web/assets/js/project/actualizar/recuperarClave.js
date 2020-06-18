function recuperaClave() {
     $('#exampleModal').modal('hide');
     consultaTipoDoc('','#tipoDocActu');
     modalRecuperar();
}

function  modalRecuperar(){
    $('#bloqueo').toggle();
    $('#modalRecuperarClave').toggle();
    limpiarFormulario('#recuperarClave');
}

document.getElementById('recuperarClave').addEventListener('input', e => {

    e.preventDefault();
    var form = $("#recuperarClave");
    if (form[0].checkValidity() === false) {
        event.preventDefault();
        event.stopPropagation();
    } else {
        event.preventDefault();
        event.stopPropagation();
    }
    form.addClass('was-validated');

})

$('#recuperarClave').submit(function (e) {

    e.preventDefault();
    e.stopPropagation();

    
    if ($('#recuperarClave')[0].checkValidity()) {
        event.preventDefault();
        event.stopPropagation();
        event.stopImmediatePropagation();
        var btn = document.getElementById('btnRecuperarClave');
        $('#carga').addClass('is-active');
        btn.disabled = true;
        var datos = $('#recuperarClave').serialize();
        $.ajax({
            url: "recuperarClave",
            type: 'POST',
            data: datos,
            error: function (jqXHR, textStatus, errorThrown) {
                $('#carga').removeClass('is-active');
                modalRecuperar();
                messageInfo('Ha ocurrido un error con el servidor, favor intentar más tarde.');
                 btn.disabled = false;
            }, success: function (data, textStatus, jqXHR) {
                btn.disabled = false;
                $('#carga').removeClass('is-active');
                modalRecuperar();
                if (data === true || data === 'true') {
                    
                    messageOk('Hemos enviado su nueva clave al correo registrado.');

                } else {
                    messageError('Usuario no encontrado, favor verificar datos');
                }
            }
        })
    } else {

        $('#datosActualizarpresona').addClass('was-validated');
    }
})