$(document).ready(consultarPreguntas());
function  consultarPreguntas(){
    $.ajax({
        url:'./registro?accion=listarPreguntas',
        type: 'POST',
        dataType: 'json',
        contentType: false,
        processData: false,
        error: function (jqXHR, textStatus, errorThrown) {
            messageInfo('Ha ocurrido un error con el servidor, favor intentar más tarde');
          
        },success: function (data) {
            if(data!=='false'){
            generarPreguntas(data);
        }else{
             messageInfo('Ha ocurrido un error con el servidor, favor intentar más tarde');
        }
        }
    })
}

function  generarPreguntas(preguntas){
   
    var pregunta='';
    for(var i=0;i<preguntas.length;i++){
        pregunta+='<p style="color:black;" idPregunta='+preguntas[i].idPregunta+' >'+preguntas[i].pregunta+'</p><hr>';
    }
    console.log(pregunta);
    $('#preguntas').empty();
    $('#preguntas').html(pregunta);
}

