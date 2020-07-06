$(document).ready(consultarRolInicio());
function consultarRolInicio() {
    not = 0;
    var rol = $('#nombreUsuarioInicio').data('rol');
    $('.notificaciones').text('');
    $('#nroNoti').hide();
    if (rol === 3) {
        consultarPreguntas();
        consultaNotiRespuestas('si');

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
        pregunta += '<p style="color:black; margin-bottom: -3px;" idPregunta=' + preguntas[i].idPregunta + ' >' + preguntas[i].pregunta + '</p>';
        pregunta += '<p style="color:rgb(252, 115, 30);  font-size: x-small"  ><a href="#" onclick="verProductoPregunta(event,' + preguntas[i].idProducto + ')">Ver producto</a></p>';
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
                    enviarNot('respuesta', 0);
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
        respuesta += '<p style="color:rgb(252, 115, 30);"  ><b>' + respuestas[i].usuarioResponde + ':</b></p>';
        respuesta += '<p class="respuesta" >' + respuestas[i].respuesta + ' </p>';
        respuesta += '<p style="color:rgb(252, 115, 30);  font-size: x-small"  ><a href="#" onclick="verProductoPregunta(event,'+ respuestas[i].idProducto + ')">Ver producto</a></p>';

        respuesta += '<hr class="linea">';
    }

    $('.preguntas').empty();
    $('.preguntas').html(respuesta);
}

//function consultarNoRespuestas() {
//
//    setInterval(function () {
//
//        consultaNotiRespuestas('si');
//    }, 3000);
//}

function verProductoPregunta(event, id) {
    //ajax
    event.preventDefault();
    $.ajax({
        url: './obtenerProducto',
        type: 'POST',
        data: {
            idProducto: id
        },
        dataType: 'json',
        error: function (jqXHR, textStatus, errorThrown) {
            messageInfo('Ha ocurrido un error con el servidor, favor intentar más tarde.');
        }, success: function (data) {

            //item.valorProducto
            $('#detailsProductPregunta').modal('show');
            detallesProducto(data);
        }
    })

//     

}

function detallesProducto(producto) {


    caruselImagenesProducto(producto.imagenes)
    textoProducto(producto.producto);

    setTimeout(() => $('.hijueputa').carousel({
            interval: 6100,
        }), 1000)

}

function textoProducto(item) {
    let str = ''
    let element = document.getElementById('details')
    str += `  <div id="detail" class="text-justify pt-2" precioProducto="${item.valorProducto}" idEmpresa="${item.idEmpresaFK}" idProducto="${item.idProducto}">
<h2 class="h4 font-weight-bold mb-2 text-center">${item.nombreProducto}</h2>
    <a id="meInteresa" type="button" href="#" class="btn btn-primary btn-xs float-right hvr-push">
                     <i class="fas fa-gift"></i> Me interesa</a>
    <select class="form-control float-right" id="cantidadSelect" style="width:auto;height:auto;">`
    for (var i = 1; i <= item.stockProducto; i++) {
        str += `<option>${i}</option>`
    }
    str += `</select>
              <p class="mb-0 text-small text-muted">Valor: $ ${item.valorProducto}</p>
              <p class="mb-0 text-small text-muted">Marca: ${item.marcaProducto}</p>
              <p class="mb-0 text-small text-muted">Categoría: ${item.categorys.nombreCategoria}</p>
              <p class="mb-0 text-small text-muted">Descripción : ${item.descripcionProducto}</p>`
    if (item.diasEnvios !== undefined) {
        str += `
        <hr>
        <div class="col-lg-12 mb-5 p-0">
       <a data-toggle="collapse" href="#collapseExample${item.idProducto}" role="button" aria-expanded="false" aria-controls="collapseExample1" class="btn btn-primary btn-block py-2 shadow-sm with-chevron">
          <p class="d-flex align-items-center justify-content-between mb-0 px-3 py-2"><strong class="text-uppercase">Información Adicional</strong><i class="fa fa-angle-down"></i></p>
        </a>
        <div id="collapseExample${item.idProducto}" class="collapse shadow-sm">
          <div class="card">
            <div class="card-body">
             <p class="mb-0 text-small text-muted">Días de envío: ${item.diasEnvios}</p>
         <p class="mb-0 text-small text-muted">Medidas : ${item.medidaProducto}</p>
         <p class="mb-0 text-small text-muted">Empaque : ${item.empaqueProducto}</p>
         <p class="mb-0 text-small text-muted">Embalaje : ${item.embalajeProducto}</p>
         <p class="mb-0 text-small text-muted">Ventajas : ${item.ventajaProducto}</p>
            </div>
          </div>
        </div>
      </div>
    </div>`
    }
    element.innerHTML = str;
}

function caruselImagenesProducto(data) {
    let str = ''
    let ele = document.getElementById('carusel')
    let num = 0;
    for (var item of data) {
        if (num === 0) {
            str += `<div class="carousel-item active">
                            <img class="img-fluid fit-imageModal" src="${item.url}">
                        </div>`
        } else {
            str += `<div class="carousel-item">
                            <img class="img-fluid fit-imageModal" src="${item.url}">
                        </div>`
        }
        num++;
    }
    ele.innerHTML = str;

}