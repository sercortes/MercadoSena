$(document).ready(consultarRolInicio());

function consultarRolInicio() {
not=0;
    var rol = $('#nombreUsuarioInicio').data('rol');
    $('#nroNoti').hide();
    if (rol === 3) {
        consultarPreguntas();
        consultarNoRespuestas();

    } else if (rol === 2) {
        consultarRespuestas();
        $('.ocultarRespuesta').hide();
        $('#v-pills-profile-tab').click();

    }

}

function  consultarPreguntas() {
    $.ajax({
        url: './registro?accion=listarPreguntas',
        type: 'POST',
        dataType: 'json',
        error: function (jqXHR, textStatus, errorThrown) {
            // messageInfo('Ha ocurrido un error con el servidor, favor intentar más tarde');

        }, success: function (data) {
            if (data !== 'false') {
                generarPreguntas(data);
            } else {
                messageInfo('Ha ocurrido un error con el servidor, favor intentar más tarde');
            }
        }
    })
}

function  generarPreguntas(preguntas) {

    var pregunta = '';
    for (var i = 0; i < preguntas.length; i++) {
        pregunta += '<p style="color:rgb(252, 115, 30);"  ><b>' + preguntas[i].nombreUsuarioPregunta + ' ' + preguntas[i].apellidoUsuarioPregunta + ':</b></p>';
        pregunta += '<p style="color:black;" idPregunta=' + preguntas[i].idPregunta + ' >' + preguntas[i].pregunta + '</p>';
        if (preguntas[i].estadoPregunta === 0) {
            pregunta += '<div class="divEnviar"><input placeholder="Responda aquí..." type="text" style="border: none;margin-left: 9px;" id="' + preguntas[i].idPregunta + '"> <button class="enviar" onclick="responderPregunta(' + preguntas[i].idPregunta + ')"><i class="fa fa-paper-plane"></i></button></div>';
        }
        pregunta += '<hr class="linea">';
    }

    $('.preguntas').empty();
    $('.preguntas').html(pregunta);
}

function responderPregunta(idPregunta) {
    var respuesta = $('#' + idPregunta).val();
    if (respuesta !== null && respuesta !== '') {
        $.ajax({
            url: './registro?accion=registroRespuesta&respuesta=' + respuesta + '&idPregunta=' + idPregunta,
            type: 'POST',
            dataType: 'json',
            error: function (jqXHR, textStatus, errorThrown) {
                // messageInfo('Ha ocurrido un error con el servidor, favor intentar más tarde.');
            }, success: function (data) {

                if (data) {
                    consultarPreguntas();
                } else {
                    messageError('Error al enviar su respuesta.');
                }
            }
        })
    }
}

function consultarRespuestas() {
    $('#noRespuestas').hide();
    $.ajax({
        url: './registro?accion=listarPreguntasRespuesta',
        type: 'POST',
        contentType: false,
        processData: false,
        error: function (jqXHR, textStatus, errorThrown) {
            messageInfo('Ha ocurrido un error con el servidor, favor intentar más tarde.');
        }, success: function (data) {
            generarRespuestas(data);
        }
    })
}

function  generarRespuestas(respuestas) {
    
    var respuesta = '';
    //console.log(respuestas);
    for (var i = 0; i < respuestas.length; i++) {
         respuesta += '<p style="color:rgb(252, 115, 30);"><b>Tú:</b></p><p style="color:black;" idPregunta=' + respuestas[i].idPregunta + ' >' + respuestas[i].pregunta + '</p>';
        respuesta += '<p style="color:rgb(252, 115, 30);"  ><b>' + respuestas[i].usuarioResponde +':</b></p>';
        respuesta += '<p class="respuesta" >' + respuestas[i].respuesta + ' </p>';
        respuesta += '<hr class="linea">';
    }

    $('.preguntas').empty();
    $('.preguntas').html(respuesta);
}

function consultarNoRespuestas() {

    setInterval(function () {

        consultaNotiRespuestas('si');
    }, 3000);
}