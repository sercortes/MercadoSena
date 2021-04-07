$(function () {

    $('.datepicker').datepicker({
        clearBtn: true,
        format: 'yyyy/mm/dd',
    });

    $('#fechaFinal').datepicker({
        format: 'yyyy/mm/dd',
    }).datepicker("setDate", 'now');

});

$(document).on('click', '#generarInfo', function (e) {

    e.preventDefault()
    let fechaI = document.getElementById('fechaInicial').value
    let fechaFinal = document.getElementById('fechaFinal').value
    let tipo = document.getElementById('tipoInforme').value

    if (fechaI == '') {
        messageInfo('Selecione una Fecha Inicial')
        return false
    }

    if (fechaI > fechaFinal) {
        messageInfo('Selecione fecha válidas')
        return false
    }

    let a = new Date(fechaI)
    let b = new Date(fechaFinal)

    let days = dateDiffInDays(a, b)

    let data = {
        fechaI: fechaI,
        fechaFinal: fechaFinal,
        tipo: tipo
    }

    if (tipo == '1' || tipo == '2') {

        if (days > 31) {
            
            query(data, 'graphicMonth')


        } else {
            
            query(data, 'graphicDays')

        }
        
    }else{
        
        if (days > 31) {
            
            query(data, 'graphicTotalMoth')

        } else {
            
            query(data, 'graphicTotalDays')

        }
        
    }


})

function query(data, url) {

    $.ajax({
        type: "POST",
        url: './' + url,
        data: {
            fechaI: data.fechaI,
            fechaFinal: data.fechaFinal,
            tipo: data.tipo
        },
        async: true,
        datatype: 'json'
    }).done(function (data) {

        if (data.length <= 0) {
            messageInfo('consulta sin resultados')
        } else {
            graficar(data)
        }

    })

}

function graficar(data) {

    var labels = data.map(function (e) {
        return e.label;
    });
    var data = data.map(function (e) {
        return e.value;
    });

    $('#graficoCanvas').html('<canvas id="grafico" width="400px" style="min-height:300px;max-height: 2000px" ></canvas>');
    let canvas = document.getElementById('grafico').getContext('2d');
    var chart = new Chart(canvas, {
        type: 'bar',
        data: {
            labels: labels,
            datasets: [{
                    label: '# Ventas',
                    data: data,
                    backgroundColor: 'rgba(0, 119, 204, 0.3)'
                }]
        }
    });

    $('#modalInforme').modal('show')

}

const _MS_PER_DAY = 1000 * 60 * 60 * 24;

// a and b are javascript Date objects
function dateDiffInDays(a, b) {
    // Discard the time and time-zone information.
    const utc1 = Date.UTC(a.getFullYear(), a.getMonth(), a.getDate());
    const utc2 = Date.UTC(b.getFullYear(), b.getMonth(), b.getDate());

    return Math.floor((utc2 - utc1) / _MS_PER_DAY);
}