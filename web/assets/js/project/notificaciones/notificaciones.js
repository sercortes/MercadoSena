/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

var not = 0;
$(document).ready(function () {

    setInterval(
            function () {
               // var URLactual = window.location;
               
                //not = 0;
                var rol = $('#nombreUsuarioInicio').data('rol');
                if (rol === 3) {
                    consultaNotiPreguntas();
                    consultaNotiRespuestas('no');
                    mostrarNot();
                } else if (rol === 2) {
//
                    consultaNotiRespuestas('no');
                    mostrarNot();
                }
                }, 5000
            );


})



function  consultaNotiPreguntas() {

    $.ajax({
        url: './registro?accion=consultaNotiPreguntas',
        type: 'POST',
        dataType: 'json',
        error: function (jqXHR, textStatus, errorThrown) {

        }, success: function (data) {
            if (data > 0) {
                not = not + data;
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
                if (hacer === 'si') {
                    $('#noRespuestas').text('+' + data);
                    $('#noRespuestas').show();
                    not=0;
                }
                not = not + data;
                // $('#nroNoti').text('+'+data);
            } else {
                $('#noRespuestas').text('');
            }
        }
    })

}

function  mostrarNot() {

    if (not > 0) {
        $('#nroNoti').show();
        $('#nroNoti').text('+' + not);
        not = 0;
    }
}