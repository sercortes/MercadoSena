$(document).ready(consultarRolInicio());

function consultarRolInicio() {

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
        pregunta += '<p style="color:black;" idPregunta=' + preguntas[i].idPregunta + ' >' + preguntas[i].pregunta + '</p>';
        if (preguntas[i].estadoPregunta === 0) {
            pregunta += '<input type="text" id="' + preguntas[i].idPregunta + '"> <input type="submit" value="Responder" onclick="responderPregunta(' + preguntas[i].idPregunta + ')">';
        }
        pregunta += '<hr>';
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
    for (var i = 0; i < respuestas.length; i++) {
        respuesta += '<p style="color:black;" idPregunta=' + respuestas[i].idPregunta + ' ><strong>' + respuestas[i].pregunta + '</strong></p>';
        respuesta += '<p style="color:black;" >'+respuestas[i].respuesta+' </p>';
        respuesta += '<hr>';
    }

    $('.preguntas').empty();
    $('.preguntas').html(respuesta);
}

function consultarNoRespuestas(){
    
    setInterval( function (){
            
    consultaNotiRespuestas('si')},3000);
}