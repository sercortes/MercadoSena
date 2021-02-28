var interacion;
var idProducto;
$(document).on('click', '.botonChat', function (e) {

    e.preventDefault()

    if (!checkSession()) {
        modalPreguntaRegistro();
        return false
    }

    let rol = getRol();
    $('#preguntarModal').modal('show');
    interacion = 0;
    let parent = $(this)[0].parentElement.parentElement;
    idProducto = $(parent).attr('idProducto');

    if (rol == 3) {
        $('#buttonChat').hide()
    } else {
        $('#buttonChat').show()
    }

})

$('#preguntarModal').on('shown.bs.modal', function (e) {
   document.getElementById('mensaje').focus()
})

$(document).on('click', '#send_message', function () {

    let message = document.getElementById('mensaje').value
    if (message === null || message === '' || message.length <= 3) {
        messageInfo('Escribe el mensaje')
        return false
    }
    if (message.length <= 4) {
        messageInfo('Escribe un mensaje válido')
        return false
    }
    if (message.length >= 266) {
        messageInfo('Escribe un mensaje más corto')
        return false
    }

    let st = `<li class="message right appeared">
                        <div class="avatar"></div>
                        <div class="text_wrapper">
                            <div class="text">${message}</div>
                        </div>
                    </li>`;
    
    $('#listaPreguntas').append(st)
    enviarMensaje(message)

})

function enviarMensaje(mensaje) {

    $.ajax({
        url: './registrarPregunta',
        data: {
            accion: 'registroPregunta',
            mensaje: mensaje,
            idProducto: idProducto
        },
        type: 'POST',
        success: function (data) {
            if (data === 'true') {
                setTimeout(() => sendMensajes(), 1000);
                limpiarPlantilla()
                document.getElementById('mensaje').value = ""
            } else {
                interacion = interacion - 1;
                messageError('Error al enviar el mensaje');
            }
            enviarNot('pregunta', 0);
        }})

}

function sendMensajes(){
    let app =`<li class="message left appeared">
                        <div class="avatar"></div>
                        <div class="text_wrapper">
                            <div class="text">Hemos enviado su mensaje al vendedor, quién pronto se pondrá en contacto.</div>
                        </div>
                    </li>`
   $('#listaPreguntas').append(app)
    
}

function modalRegistroSi() {
    modalPreguntaRegistro();
    //consultarDatosFormulario();
    $('#exampleModal').modal('show');
}
function modalPreguntaRegistro() {
    $('#modalCar').modal('hide')
    $('#modalPreguntaRegistro').toggle();
    $('#bloqueo').toggle();
}


function consultaPreguntas(e) {
    
    e.preventDefault();

}

function limpiarPlantilla() {
//    document.getElementById('mensaje').value = ''
    var lista = $('#listaPreguntas').children();
    if (lista.length === 3) {
        $('#listaPreguntas').children().last().remove();
        $('#listaPreguntas').children().last().remove();
    }
    interacion = 0;
}