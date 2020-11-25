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
    $('#laX').show();
    var tipo;
    var tipoInforme = $('#tipoInforme').val();
    var fechaInicial = $('#fechaInicial').val();
    var fechaFinal = $('#fechaFinal').val();
    var filtro1 = $('#filtroPedidos').val();
    if ($('#tipoGrafico').val() !== '0') {
        $('#paginaGrafico').addClass('page-break');
    } else {
        $('#paginaGrafico').removeClass('page-break');
    }

    if (fechaInicial === '') {
        tipo = 0;
        textoInfoFech = 'general';
    }
    
    if(filtro1 === '1'){
        tipo = 1;
        textoInfoFech = 'general';
    }
    
    if(filtro1 === '2'){
        tipo = 2;
        textoInfoFech = 'general';
    }
    if (fechaInicial !== '' && fechaFinal === '') {
        tipo = 3;
        textoInfoFech = 'para el día ' + fechaInicial;
    }
    
    if (fechaInicial !== '' && fechaFinal === '' && filtro1 === '1') {
        tipo = 4;
        textoInfoFech = 'para el día ' + fechaInicial;
    }
    
     if (fechaInicial !== '' && fechaFinal === '' && filtro1 === '2') {
        tipo = 5;
        textoInfoFech = 'para el día ' + fechaInicial;
    }
    
    if (fechaInicial !== '' && fechaFinal !== '') {
        tipo = 6;
        textoInfoFech = 'entre las fechas ' + fechaInicial + ' y ' + fechaFinal;
    }
    
    if (fechaInicial !== '' && fechaFinal !== '' && filtro1 === '1') {
        tipo = 7;
        textoInfoFech = 'entre las fechas ' + fechaInicial + ' y ' + fechaFinal;
    }
    
    if (fechaInicial !== '' && fechaFinal !== '' && filtro1 === '2') {
        tipo = 8;
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
    var nombres = [], ventas = [], colores = [], color = [];
    var colore = "#";
    var simbolos = "0123456789ABCDEF";
    var tipoGrafico = $('#tipoGrafico').val();
    if (tipoGrafico === '0') {
        $('#tituloGrafico').empty();
        $('#graficoCanvas').empty();
    } else {
        $('#tituloGrafico').html('Diagrama:');

    }
    var n = 0;

    while (n < productos.length) {
        for (var i = 0; i < 6; i++) {
            colore = colore + simbolos[Math.floor(Math.random() * 16)];
        }
        color.push(colore);
        colore = "#";
        n++;

    }

    for (var i = 0; i < productos.length; i++) {
        nombres.push(productos[i].nombre);
        ventas.push(productos[i].cantidadVendida);
        colores.push(color[i]);


    }

    if (tipoGrafico === '1') {
        graficar(nombres, ventas, 'Unidades vendidas', 'horizontalBar', 'rgba(252, 115, 30, 0.72)', opciones);
        $('#graficoCanvas').addClass('grafico');
        $('#graficoCanvas').removeClass('graficoCircular');


    }
    if (tipoGrafico === '2') {
        graficar(nombres, ventas, 'Unidades vendidas', 'doughnut', colores);
        $('#graficoCanvas').addClass('grafico');
        $('#graficoCanvas').addClass('graficoCircular');
    }

}
function parametrosGraficarPed(pedidos) {
    var filtro = $('#filtroPedidos').val();
    var tipoGrafico = $('#tipoGrafico').val();
    if (tipoGrafico === '0') {
        $('#tituloGrafico').empty();
        $('#graficoCanvas').empty();
    } else {
        $('#tituloGrafico').html('Diagrama:');

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
            $('#graficoCanvas').addClass('grafico');
            $('#graficoCanvas').removeClass('graficoCircular');
        }
        if (tipoGrafico === '2') {

            graficar(nombres, ventas, 'Pedidos', 'doughnut', colores);
            $('#graficoCanvas').addClass('grafico');
            $('#graficoCanvas').addClass('graficoCircular');
        }
        $('#textoInfo').empty();
        $('#textoInfo').text('Informe' + textoInfoFech + ' de todos sus pedidos:');

    } else {

        var nombress = [], ventass = [], coloress = [], colorr = [];
        var coloree = "#";
        var simboloss = "0123456789ABCDEF";

        var n = 0;

        while (n < pedidos.length) {
            for (var i = 0; i < 6; i++) {
                coloree = coloree + simboloss[Math.floor(Math.random() * 16)];
            }
            colorr.push(coloree);
            coloree = "#";
            n++;

        }

        for (var i = 0; i < pedidos.length; i++) {

            nombress.push(pedidos[i].fechaVenta + ' - ' + pedidos[i].nombreProducto);
            ventass.push(pedidos[i].cantidadProductos);
            coloress.push(colorr[i]);

        }

        if (tipoGrafico === '1') {

            graficar(nombress, ventass, 'Unidades pedidas', 'horizontalBar', coloress, opciones);
            $('#graficoCanvas').addClass('grafico');
            $('#graficoCanvas').removeClass('graficoCircular');
        }
        if (tipoGrafico === '2') {
            graficar(nombress, ventass, 'Unidades pedidas', 'doughnut', coloress);
            $('#graficoCanvas').addClass('grafico');
            $('#graficoCanvas').addClass('graficoCircular');
        }



    }



}