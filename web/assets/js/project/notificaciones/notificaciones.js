/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


$(document).ready(function () {

    setInterval(
            function () {
                var rol = $('#nombreUsuarioInicio').data('rol');
                if (rol === 3) {
                    consultaNotiPreguntas();
                    consultaNotiRespuestas('no');
                    mostrarNot();
                } else if (rol === 2) {

                    consultaNotiRespuestas('no');
                    mostrarNot();
                }
            }, 5000
            );


})

var not = 0;
function  consultaNotiPreguntas() {

    $.ajax({
        url: './registro?accion=consultaNotiPreguntas',
        type: 'POST',
        dataType: 'json',
        error: function (jqXHR, textStatus, errorThrown) {

        }, success: function (data) {
            if (data > 0) {
                not += data;
                // $('#nroNoti').text('+'+data);
            }
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
                if(hacer==='si'){
                    $('#noRespuestas').text('+'+data);
                    $('#noRespuestas').show();
                }
                not = not + data;
                // $('#nroNoti').text('+'+data);
            }else{
                $('#noRespuestas').text('');
            }
        }
    })

}

function  mostrarNot() {

    if (not > 0) {
        $('#nroNoti').show();
        $('#nroNoti').text('+' + not);
        not=0;
    }
}