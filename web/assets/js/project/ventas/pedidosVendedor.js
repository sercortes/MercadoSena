var todosPedidos, vista;
$(document).ready(function () {
    vista = $('#nombreVista').val();
    consultaPedidos(true);
    //generarLista(todosPedidos, 'En espera');
})

$('.verUsuario').click(function (e) {

    e.preventDefault();

})

function consultaPedidos(hacer) {
    $('#carga').addClass('is-active');
    var tipoUsu;
    if (vista === 'pedidos') {
        tipoUsu = 'vendedor';
    } else if (vista === 'misPedidos') {
        tipoUsu = 'comprador';
    }
    $.ajax({
        type: "POST",
        url: './gestionarPedidos',
        data: {
            accion: 'pedidosVendedor',
            tipoUsu: tipoUsu
        },
        dataType: 'json',
        error: function (jqXHR, textStatus, errorThrown) {
            $('#carga').removeClass('is-active');
            messageInfo('Ha ocurrido un error con el servidor, favor intentar más tarde.');
        }, success: function (data) {
            $('#carga').removeClass('is-active');
            console.log(data);
            todosPedidos = data;
            if (hacer) {
                generarLista(todosPedidos, 'En espera');
            }
        }
    })
}

$('#pedPendientes-tab').click(function (e) {
    e.preventDefault();
    generarLista(todosPedidos, 'En espera');
})
$('#pedConcretados-tab').click(function (e) {
    e.preventDefault();
    generarLista(todosPedidos, 'Concretada');
})
$('#pedNoCon-tab').click(function (e) {
    e.preventDefault();
    generarLista(todosPedidos, 'No concretada');
})

function generarLista(dataR, estado) {
    $('#carga').addClass('is-active');
    var data = [];
    var pedidos = '';
    var cst = 0;

    for (var i = 0; i < dataR.length; i++) {
        if (dataR[i].estadoVenta === estado) {
            data.push(dataR[i]);
        }
    }

    if (data.length > 0 && data !== null) {
        for (var i = 0; i < data.length; i++) {

            cst = (data[i].prodPedidoDTO.cantidad) * (data[i].prodImagen.producto.valorProducto);

            pedidos += '<div class="card" style="border-bottom: solid 1px rgba(0, 0, 0, 0.4588235294117647);">' +
                    '<div  class="card-header bg-white shadow-sm border-0">' +
                    '<h6 class="mb-0 font-weight-bold"><a href="#" data-toggle="collapse" data-target="#' + i + '" aria-expanded="false" aria-controls="1" class="d-block position-relative collapsed text-dark text-uppercase collapsible-link py-2">' + data[i].ventaDTO.fechaVenta + '</a></h6>' +
                    '</div>' +
                    '<div id="' + i + '"  class="collapse ">' +
                    '<div class="card-body p-5" style="padding: 1.5rem !important;">' +
                    '<dt>Producto solicitado:</dt>' +
                    '<h6>' + data[i].prodImagen.producto.nombreProducto + '</h6>' +
                    '</dl>' +
                    '<dl>' +
                    '<dt>Cantidad solicitada:</dt>' +
                    '<dd>' + data[i].prodPedidoDTO.cantidad + '</dd>' +
                    '</dl>' +
                    '<dl>' +
                    '<dt>Valor por unidad:</dt>' +
                    '<dd>$' + data[i].prodImagen.producto.valorProducto.toString().replace(/(\d)(?=(\d\d\d)+(?!\d))/g, "$1.") + '</dd>' +
                    '</dl>' +
                    '<dl>' +
                    '<dt>Valor total:</dt>' +
                    '<dd>$' + cst.toString().replace(/(\d)(?=(\d\d\d)+(?!\d))/g, "$1.") + '</dd>' +
                    '</dl>' +
                    '<dl>' +
                    ' <dt>Descripción del producto:</dt>' +
                    '<dd>' +
                    '<p class="text-muted">' + data[i].prodImagen.producto.descripcionProducto + '</p>' +
                    '</dd>' +
                    '</dl>' +
                    '<dl>',
                    pedidos += '</dl>' +
                    '<dl>';
            if (vista === 'pedidos') {
                pedidos += '<dt>Información del cliente:</dt>';
                if (data[i].ventaDTO.contactoVenta === '1' || data[i].ventaDTO.contactoVenta === 1) {

                    pedidos += '<dd><a href="#" style="border-bottom: solid 1px rgb(252, 115, 30);" class="verUsuario" onclick="datosUsuario(event,' + data[i].compradorDTO.idPersona + ')" >click aquí para ver la información del cliente...</a></dd>';
                } else {
                    pedidos += '<br><dd><b><i class="fa fa-exclamation-circle" aria-hidden="true" style="color: rgb(216, 98, 25); font-size: 20px;"></i> El usuario no desea que veas sus datos.</b></dd>';
                }
            } else {
                pedidos += '<dt>Información del vendedor:</dt>' + '<dd><a href="#" style="border-bottom: solid 1px rgb(252, 115, 30);" class="verUsuario" onclick="datosUsuario(event,' + data[i].compradorDTO.idEmpresa + ')" >click aquí para ver la información del vendedor...</a></dd>';
            }
            pedidos += '</dl>' +
                    '<dl>' +
                    '<dt>Estado del pedido:</dt>';

            if (data[i].estadoVenta === 'En espera' && vista === 'pedidos') {

                pedidos += '<p class="text-muted">Por favor al terminar el proceso con el cliente seleccione un estado</p>' +
                        '<select id="estadoPedido' + data[i].ventaDTO.idVenta + '" class="form-control" style="width: 50%;display: initial;" onchange="validarBtn(' + data[i].ventaDTO.idVenta + ')">';

                for (var y = 0; y < data[i].listaEstadoPedido.length; y++) {
                    if (data[i].listaEstadoPedido[y].idEstado === data[i].ventaDTO.idEstadoVentaFK) {
                        pedidos += '<option value="' + data[i].listaEstadoPedido[y].idEstado + '" selected>' + data[i].listaEstadoPedido[y].nombreEstado + '</option>';
                    }
                    pedidos += '<option value="' + data[i].listaEstadoPedido[y].idEstado + '">' + data[i].listaEstadoPedido[y].nombreEstado + '</option>';

                }
                pedidos += '</select> <input type="submit" class="btn btn-success" value="Actualizar" disabled style="margin: 10px;margin-top: 5px;" onclick="actualizarEstadoPed(' + data[i].ventaDTO.idVenta + ')">';

            } else {
                pedidos += '<p class="text-muted">' + data[i].estadoVenta + '</p>';
            }



            if (data[i].prodImagen.imagenes.length > 0) {

                pedidos += '<dt>Imágenes:</dt><br>';
                pedidos += '<div id="myCarousel' + i + '" class="carousel slide" data-ride="carousel">' +
                        '<ul class="carousel-indicators" style="position: inherit !important;">' +
                        '<li data-target="#myCarousel' + i + '" data-slide-to="0" class="active"></li>';

                for (var j = 1; j < data[i].prodImagen.imagenes.length; j++) {

                    pedidos += ' <li data-target="#myCarousel' + i + '" data-slide-to="' + j + '"></li>';
                }
                pedidos += '<div class="carousel-inners">' +
                        ' <div class="carousel-item active" style="min-height: 50px;max-height: 500px;background: white;text-align: center;width: 120%;margin-left: -15%;">' +
                        '<img  src="' + data[i].prodImagen.imagenes[0].url + '" alt="Imagen del producto" >' +
                        '</div>';
                for (var j = 1; j < data[i].prodImagen.imagenes.length; j++) {
                    pedidos += '<div class="carousel-item" style="min-height: 50px; max-height: 500px;background: white;text-align: center;width: 120%;margin-left: -15%;">' +
                            '<img  src="' + data[i].prodImagen.imagenes[j].url + '" alt="Imagen del producto" >' +
                            ' </div>';
                }
                pedidos += '</ul>'; 


                
                pedidos += '' +
                        '<a class="carousel-control-prev" href="#myCarousel' + i + '" data-slide="prev">' +
                        ' <span class="carousel-control-prev-icon"></span>' +
                        ' </a>' +
                        '<a class="carousel-control-next" href="#myCarousel' + i + '" data-slide="next">' +
                        '<span class="carousel-control-next-icon"></span>' +
                        ' </a>' +
                        '</div>' +
                        '</div>';

            }

            pedidos += ' </dl>' +
                    '</div>' +
                    ' </div>' +
                    '</div>';

        }
    } else {
        pedidos += '<h3 style="text-align: center;padding: 27px;font-size: 27px;">No hay elementos en esta categoría.</h3>';
    }
    $('#carga').removeClass('is-active');
    $('#pedidos').empty();
    $('#pedidos').html(pedidos);

}


function datosUsuario(event, idUsuario) {
    event.preventDefault();
    var tipoUsu;

    if (vista === 'pedidos') {
        tipoUsu = 'comprador';
    } else if (vista === 'misPedidos') {
        tipoUsu = 'vendedor';
    }

    $('#carga').addClass('is-active');
    $.ajax({
        url: './gestionarPedidos',
        type: "POST",

        data: {
            accion: 'consultaUsu',
            idUsuario: idUsuario,
            tipoUsu: tipoUsu
        },
        datatype: 'json',
        error: function (jqXHR, textStatus, errorThrown) {
            $('#carga').removeClass('is-active');
            messageInfo('Ha ocurrido un error con el servidor, favor intentar más tarde.');
        }, success: function (data) {
            console.log(data);
            $('#carga').removeClass('is-active');
            llenarModalUsu(data);
        }
    })

}

function llenarModalUsu(datos) {
    if (vista === 'pedidos') {
        $("#modalUsuario").modal("show");
        $('#imagenUsuario').html('<img id="fotoPerfil" class="fotoPerfil"  src="' + datos.urlImg + '" width="200px" height="200"/>');
        $('#nombreUsu').text(datos.nombrePer + ' ' + datos.apellidoPer);
        $('#celUsu').text(datos.numCelularPer);
        $('#telUsu').text(datos.telPer);
        $('#correoUsu').text(datos.correoPer);
        $('#ciuUsu').text(datos.nombreCiudad);
        $('#dirUsu').text(datos.direccionPer);
    } else if (vista === 'misPedidos') {
        $("#modalUsuarioVendedor").modal("show");
        if (datos.esEmpresa === 0){
              $('#nombreUsu').text(datos.nombrePer + ' ' + datos.apellidoPer);
        }else{
              $('#nombreUsu').text(datos.nombreEmpresa);
        }   
        $('#celUsu').text(datos.CelEmpresa);
        $('#telUsu').text(datos.telEmpresa);
        $('#correoUsu').text(datos.correoEmpresa);
        $('#ciuUsu').text(datos.nombreCiudad);
        $('#dirUsu').text(datos.DirEmpresa);
    }

}