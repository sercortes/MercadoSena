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
var textoInfoFech;
function generarInforme(event) {
    event.preventDefault();
    var tipo;
    var tipoInforme = $('#tipoInforme').val();
    var fechaInicial = $('#fechaInicial').val();
    var fechaFinal = $('#fechaFinal').val();
    if (fechaInicial === '') {
        tipo = 1;
        textoInfoFech = 'general';
    }
    if (fechaInicial !== '' && fechaFinal === '') {
        tipo = 2;
        textoInfoFech = 'para el día ' + fechaInicial;
    }
    if (fechaInicial !== '' && fechaFinal !== '') {
        tipo = 3;
        textoInfoFech = 'entre las fechas ' + fechaInicial + ' y ' + fechaFinal;
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
                parametrosGraficarPro(data)
                $('#fechaInicial').val('');
                $('#fechaFinal').val('');
                $('#textoInfo').empty();
                $('#textoInfo').text('Informe ' + textoInfoFech + ' de todos sus productos:');
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
                parametrosGraficarPed(data);
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
    if (fechaIni === '' && fechaFinal !== '') {
        $("#generarInfo").prop('disabled', true);
        $('#avisoFechaIni').text('Por favor complete este campo!!');
    } else {
        $("#generarInfo").prop('disabled', false);
        $('#avisoFechaIni').text('');
    }
}
function mostrarFiltroPed() {

    var tipoInforme = $('#tipoInforme').val();
    if (tipoInforme === '2') {
        $('#divFitroPedido').show();
    } else {
        $('#divFitroPedido').hide()
    }
}
var opciones = {

    scales: {
        xAxes: [{gridLines: {display: false},
                ticks: {
                    beginAtZero: true
                }
            }]
    }

};
function parametrosGraficarPro(productos) {
    var tipoGrafico = $('#tipoGrafico').val();
    var nombres = [], ventas = [], colores = [];
    var tipoGrafico = $('#tipoGrafico').val();
    if (tipoGrafico === '0') {
        $('#tituloGrafico').empty();
        $('#graficoCanvas').empty();
    } else {
        $('#tituloGrafico').html('Gráfico:');

    }

    for (var i = 0; i < productos.length; i++) {
        nombres.push(productos[i].nombre);
        ventas.push(productos[i].cantidadVendida);
        colores.push('rgba(252, 115, 30, 0.72)');
        colores.push('rgba(134, 198, 83, 0.77)');
        colores.push('rgba(103, 119, 239,0.72)');
        colores.push('rgba(58, 186, 244, 0.82)');
        colores.push('rgba(252, 84, 75, 0.61)');
    }
    if (tipoGrafico === '1') {
        graficar(nombres, ventas, 'Unidades vendidas', 'horizontalBar', colores, opciones);
    }
    if (tipoGrafico === '2') {
        graficar(nombres, ventas, 'Unidades vendidas', 'doughnut', colores);
    }

}
function parametrosGraficarPed(pedidos) {
    var filtro = $('#filtroPedidos').val();
    var tipoGrafico = $('#tipoGrafico').val();
    if (tipoGrafico === '0') {
        $('#tituloGrafico').empty();
        $('#graficoCanvas').empty();
    } else {
        $('#tituloGrafico').html('Gráfico:');

    }
    if (filtro === '0') {
        var con = 0;
        var nombres = ['Concretados', 'No concretados', 'En espera'], ventas = [0, 0, 0], colores = ['rgb(139, 230, 131)', 'rgba(243, 33, 14, 0.5)', 'rgb(193, 201, 228)'];
        for (var i = 0; i < pedidos.length; i++) {
            if (pedidos[i].idEstado === 2) {
                con = ventas[0];
                con = con + 1;

                ventas[0] = con;
            }
            if (pedidos[i].idEstado === 3) {
                ventas[1] = ventas[1] + 1;
            }
            if (pedidos[i].idEstado === 1) {
                ventas[2] = ventas[2] + 1;
            }

        }
        if (tipoGrafico === '1') {

            graficar(nombres, ventas, 'Pedidos', 'horizontalBar', colores, opciones);
        }
        if (tipoGrafico === '2') {

            graficar(nombres, ventas, 'Pedidos', 'doughnut', colores);
        }
        $('#textoInfo').empty();
        $('#textoInfo').text('Informe ' + textoInfoFech + ' de todos sus pedidos:');

    } else {

        var nombress = [], ventass = [], coloress = [];
        for (var i = 0; i < pedidos.length; i++) {

            nombress.push(pedidos[i].fechaVenta + ' - ' + pedidos[i].nombreProducto);
            ventass.push(pedidos[i].cantidadProductos);
            coloress.push('rgba(252, 115, 30, 0.72)');
            coloress.push('rgba(134, 198, 83, 0.77)');
            coloress.push('rgba(103, 119, 239,0.72)');
            coloress.push('rgba(58, 186, 244, 0.82)');
            coloress.push('rgba(252, 84, 75, 0.61)');
        }

        if (tipoGrafico === '1') {


            graficar(nombress, ventass, 'Unidades pedidas', 'horizontalBar', coloress, opciones)
        }
        if (tipoGrafico === '2') {
            graficar(nombress, ventass, 'Unidades pedidas', 'doughnut', coloress)
        }



    }



}