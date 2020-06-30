
var not = 0, notRes = 0, notPre = 0;
$(document).ready(consultarTodo());
function consultarTodo() {
    var rol = $('#nombreUsuarioInicio').data('rol');
    if (rol === 3) {
       
        consultaNotiPreguntas();
        consultaNotiRespuestas('no');

    } else if (rol === 2) {

        consultaNotiRespuestas('no');

    }
}

var url = 'ws://' + window.window.location.host + '/MercadoSena/not',
        ws = new WebSocket(url);
(function (window, document, JSON) {
    'use strict';


    ws.onopen = onOpen;
    ws.onclose = onClose;
    ws.onmessage = onMessage;

    function onOpen() {
        //console.log('conctado');
    }
    function onClose() {

        // console.log('desconectado');
    }

    function onMessage(evt) {
        var obj = JSON.parse(evt.data);
        if (obj.tipoConsulta === 'consultaNotiPreguntas') {
            var rol = $('#nombreUsuarioInicio').data('rol');
            if (rol === 3) {
                consultaNotiPreguntas();

                //mostrarNot();
            }
        } else if (obj.tipoConsulta === 'consultaNotiRespuestas') {
            // alert('hay una respuesta nueva');
            consultaNotiRespuestas('');
        }

    }


})(window, document, JSON);
function enviarNot(tipoCon) {
    var msg;
    if (tipoCon === 'pregunta') {

        msg = {
            idUsuarioPregunta: 0,
            idUsuario: 0,
            idProducto: 0,
            idEmpresa: 0,
            tipoConsulta: 'consultaNotiPreguntas'
        };


    } else if (tipoCon === 'respuesta') {
        msg = {
            idUsuarioPregunta: 0,
            idUsuario: 0,
            idProducto: 0,
            idEmpresa: 0,
            tipoConsulta: 'consultaNotiRespuestas'
        };
    }

    ws.send(JSON.stringify(msg));
}





function  consultaNotiPreguntas() {
    // alert('consultando..');
    $.ajax({
        url: './registro?accion=consultaNotiPreguntas',
        type: 'POST',
        dataType: 'json',
        error: function (jqXHR, textStatus, errorThrown) {

        }, success: function (data) {
             
            if (data > 0) {
                notPre = data;
                // $('#nroNoti').text('+'+data);
            }
//            console.log('......................consultaNotiPreguntas...........................')
//            console.log('notPre ' + notPre)
//            console.log('not ' + not)
            //mostrarNoti(data);
            mostrarNot();
        }
    })

}

function consultaNotiRespuestas(hacer) {


    $.ajax({
        url: './registro?accion=consultaNotiRespuestas',
        type: 'POST',
        dataType: 'json',
        error: function (jqXHR, textStatus, errorThrown) {

        }, success: function (data) {
            if (data > 0) {
                //console.log('......................consultaNotiRespuestas...........................')
                notRes = data;
                if (hacer === 'si') {
                    $('#noRespuestas').text('+' + data);
                    $('#noRespuestas').show();
                } else {
                    mostrarNot();
                }
               // console.log('notRes ' + notRes)
                //console.log('not ' + not)

                // $('#nroNoti').text('+'+data);
            } else {
                //$('.notificaciones').text('');
                
            }
        }
    })

}

function  mostrarNot() {

    //alert(not);
//    var nro = $('#nroNoti').text();
//    if(nro!=='' && nro!==null){
//    nro=nro.slice(1);
//    nro = parseInt(nro);
//    not = not + nro;     
//   }
//    console.log('............................mostrar not............................')
//    console.log('antes de sumar not ' + not)
//    console.log('not pre ' + notPre);
//    console.log('not res ' + notRes);
    var n = 0;
    n = not + notPre + notRes;
   // console.log(not + '+' + notPre + '+' + notRes + '=' + n);
    not = not + notPre + notRes;
   // console.log('despues de sumar de sumar not mostrar ' + not);
    if (not > 0) {
       
        if (not > 9) {
            $('.notificaciones').show();
            $('.notificaciones').text('9+');
        } else {
            $('.notificaciones').show();
            $('.notificaciones').text('+' + not);
        }
        not = 0;
    }
}


