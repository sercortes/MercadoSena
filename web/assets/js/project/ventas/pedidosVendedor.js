$(document).ready(function () {
    consultaPedidos();
})

$('#verUsuario').click(function (e) {
    e.preventDefault();
    datosUsuario(1);
})

function consultaPedidos() {
    $.ajax({
        type: "POST",
        url: './gestionarPedidos',
        data: {
            accion: 'pedidosVendedor'
        },
        dataType: 'json',
        error: function (jqXHR, textStatus, errorThrown) {
            messageInfo('Ha ocurrido un error con el servidor, favor intentar más tarde.');
        }, success: function (data) {
            console.log(data);
            generarLista(data);
        }
    })

}

function generarLista(data) {
    var pedidos = '';
    var cst=0;
    if (data.length > 0 && data !== null) {
        for (var i = 0; i < data.length; i++) {
//console.log(data[i].prodImagen.imagenes.length);
         cst=(data[i].prodPedidoDTO.cantidad)*(data[i].prodImagen.producto.valorProducto);

            pedidos += '<div class="card" style="border-bottom: solid 1px rgba(0, 0, 0, 0.4588235294117647);">' +
                    '<div  class="card-header bg-white shadow-sm border-0">' +
                    '<h6 class="mb-0 font-weight-bold"><a href="#" data-toggle="collapse" data-target="#'+i+'" aria-expanded="false" aria-controls="1" class="d-block position-relative collapsed text-dark text-uppercase collapsible-link py-2">' + data[i].ventaDTO.fechaVenta + '</a></h6>' +
                    '</div>' +
                    '<div id="'+i+'"  class="collapse ">' +
                    '<div class="card-body p-5" style="padding: 1.5rem !important;">' +
                    '<h6>' + data[i].prodImagen.producto.nombreProducto + '</h6>' +
                    '<dl>' +
                    '<dt>Cantidad solicitada:</dt>' +
                    '<dd>' + data[i].prodPedidoDTO.cantidad + '</dd>' +
                    '</dl>' +
                    '<dl>' +
                    '<dt>Valor por unidad:</dt>' +
                    '<dd>$' + data[i].prodImagen.producto.valorProducto + '</dd>' +
                    '</dl>' +
                    '<dl>' +
                    '<dt>Valor total:</dt>' +
                    '<dd>$' + cst + '</dd>' +
                    '</dl>' +
                    '<dl>' +
                    ' <dt>Descripción del producto:</dt>' +
                    '<dd>' +
                    '<p class="text-muted">' + data[i].prodImagen.producto.descripcionProducto + '</p>' +
                    '</dd>' +
                    '</dl>' +
                    '<dl>' +
                    '<dt>Imágenes:</dt><br>';
            
            if (data[i].prodImagen.imagenes.length > 0) {

                pedidos += '<div id="myCarousel'+i+'" class="carousel slide" data-ride="carousel">' +
                        '<ul class="carousel-indicators">' +
                        '<li data-target="#myCarousel'+i+'" data-slide-to="0" class="active"></li>';

                for (var j = 1; j < data[i].prodImagen.imagenes.length; j++) {
                   // alert('mas de una');
                    pedidos += ' <li data-target="#myCarousel'+i+'" data-slide-to="'+j+'"></li>';
                }
                pedidos += '</ul>' +
                        '<div class="carousel-inner">' +
                        ' <div class="carousel-item active" style="min-height: 50px;max-height: 500px;background: white;text-align: center;">' +
                        '<img  src="' + data[i].prodImagen.imagenes[0].url + '" alt="Imagen del producto" >' +
                        '</div>';

                for (var j = 1; j < data[i].prodImagen.imagenes.length; j++) {
                    pedidos += '<div class="carousel-item" style="min-height: 50px; max-height: 500px;background: white;text-align: center;">' +
                            '<img  src="' + data[i].prodImagen.imagenes[j].url + '" alt="Imagen del producto" >' +
                            ' </div>';
                }
                pedidos += '' +
                        '<a class="carousel-control-prev" href="#myCarousel'+i+'" data-slide="prev">' +
                        ' <span class="carousel-control-prev-icon"></span>' +
                        ' </a>' +
                        '<a class="carousel-control-next" href="#myCarousel'+i+'" data-slide="next">' +
                        '<span class="carousel-control-next-icon"></span>' +
                        ' </a>' +
                        '</div>'+
                        '</div>';

            } else {

            }
            pedidos += '</dl>' +
                    '<dl>' +
                    '<dt>Información del cliente:</dt>' +
                    '<dd><a href="#" id="verUsuario" onclick="datosUsuario(' + data[i].compradorDTO.idPersona + ')" >click aquí para ver la información del cliente...</a></dd>' +
                    '</dl>' +
                    '<dl>' +
                    '<dt>Estado del pedido:</dt>' +
                    '<p class="text-muted">Por favor al terminar el proceso con el cliente seleccione un estado</p>' +
                    '<input type="submit" class="btn btn-danger" value="No concretado" style="margin: 10px">' +
                    '<input type="submit" class="btn btn-success" value="Concretado" style="margin: 10px">' +
                    ' </dl>' +
                    '</div>' +
                    ' </div>' +
                    '</div>';

        }
    }
    $('#pedidos').empty();
    $('#pedidos').html(pedidos);
    
}


function datosUsuario(idUsuario) {
    $("#modalUsuario").modal("show");

    let datos;

//  $.ajax({
//        type: "POST",
//        url: './getInfoCompanyByProduct',
//        async: false,
//        data:{
//            idProducto:data
//        },
//        datatype: 'json'
//    }).done(function (data) {
//  
//          datos = data
//
//    })

    $('#imagenUsuario').html('<img id="fotoPerfil" class="fotoPerfil"  src="' + datos.foto + '" width="200px" height="200"/>');
    $('#nombreUsu').text();
    $('#celUsu').text();
    $('#telUsu').text();
    $('#correoUsu').text();
    $('#ciuUsu').text();
    $('#dirUsu').text();



}

