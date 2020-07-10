$(function () {

    $('.datepicker').datepicker({
        clearBtn: true,
        format: "yyyy-mm-dd"
    });

    $('#reservationDate').on('change', function () {
        var pickedDate = $('input').val();
        $('#pickedDate').html(pickedDate);
    });
});

function generarInforme(event) {
    event.preventDefault();
    var tipo;
    var tipoInforme = $('#tipoInforme').val();
    var tipoGrafico = $('#tipoGrafico').val();
    var fechaInicial = $('#fechaInicial').val();
    var fechaFinal = $('#fechaFinal').val();

    if (fechaInicial === '') {
        tipo = 1;
    }
    if (fechaInicial !== '' && fechaFinal === '') {
        tipo = 2;
    }
    if (fechaInicial !== '' && fechaFinal !== '') {
        tipo = 3;
    }

    switch (tipoInforme) {
        case '1':
            consultaProductos(tipo, fechaInicial, fechaFinal);
            break;
        case '2':
            consultaPedidos(tipo, fechaInicial, fechaFinal);
            break;

        default:

            break;
    }


}
function consultaProductos(tipo, fechaInicial, fechaFinal) {
    $('#cargas').addClass('is-active');
    $.ajax({
        url: "./informes",
        data: {
            accion: 'consultaProductosVendedor',
            fechaInicial: fechaInicial,
            fechaFinal: fechaFinal,
            tipoConsulta: tipo
        },
        type: 'POST',
        dataType: 'json',
        error: function (jqXHR, textStatus, errorThrown) {
            $('#cargas').removeClass('is-active');
            messageInfo('Ha ocurrido un error en el servidor, favor intentar más tarde.');
        }, success: function (data, textStatus, jqXHR) {
            $('#cargas').removeClass('is-active');
            if (data !== null && data !== '') {
                console.log(data);
                genTablaPro(data);
                $('#fechaInicial').val('');
                $('#fechaFinal').val('');
            }
        }
    })
}

function consultaPedidos(tipo, fechaInicial, fechaFinal) {
    $('#cargas').addClass('is-active');
    $.ajax({
        type: "POST",
        url: './informes',
        dataType: 'json',
        data: {
            accion: 'consultaPedidosVendedor',
            fechaInicial: fechaInicial,
            fechaFinal: fechaFinal,
            tipoConsulta: tipo
        }, error: function (jqXHR, textStatus, errorThrown) {
            $('#cargas').removeClass('is-active');
            messageInfo('Ha ocurrido un error en el servidor, favor intentar más tarde.');
        }, success: function (data, textStatus, jqXHR) {
            $('#cargas').removeClass('is-active');
            if (data !== null && data !== '') {
                console.log(data);
                genTablaPedi(data);
                $('#fechaInicial').val('');
                $('#fechaFinal').val('');
            }
        }
    })

}

function validarFechas() {
    
    var fechaFinal = $('#fechaFinal').val();
    var fechaIni = $('#fechaInicial').val();
    console.log(fechaFinal);
    console.log(fechaFinal.length);
    if (fechaIni === '' && fechaFinal!=='') {
        $("#generarInfo").prop('disabled', true);
        $('#avisoFechaIni').text('Por favor complete este campo!!');
    } else {
        $("#generarInfo").prop('disabled', false);
        $('#avisoFechaIni').text('');
    }
}
