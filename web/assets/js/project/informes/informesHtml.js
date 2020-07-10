function genTablaPro(datos) {
    console.log(datos);
    var tabla = '';
    if (datos.length > 0) {
        tabla =
                '<table class="table table-hover">' +
                ' <thead>' +
                ' <tr>' +
                ' <th>Nombre producto</th>' +
                '<th>Valor por unidad</th>' +
                '<th>Cantidad pedidos concretados</th>' +
                '<th>Productos vendidos</th>' +
                ' <th>Cantidad en bodega</th>' +
                '</tr>' +
                ' </thead>' +
                '<tbody>';
        for (var i = 0; i < datos.length; i++) {
            console.log(datos[i]);
            tabla += '<tr>' +
                    '<td>' + datos[i].nombre + '</td>' +
                    '<td>$' + datos[i].valoruni + '</td>' +
                    '<td>' + datos[i].pedidoVendido + '</td>' +
                    '<td>' + datos[i].cantidadVendida + '</td>' +
                    '<td>' + datos[i].catidadBodega + '</td></tr>';
        }

        tabla +=
                '</tbody>' +
                ' </table>';
    } else {
        tabla = '<h3>No se encontraron productos</h3>';
    }
    $('#informe').empty();
    $('#informe').html(tabla);
}
function genTablaPedi(datos) {

    var tabla = '';
    if (datos.length > 0) {
        tabla =
                '<table class="table table-hover">' +
                ' <thead>' +
                ' <tr>' +
                ' <th>Fecha del pedido</th>' +
                '<th>Producto pedido</th>' +
                '<th>Cantidad de productos pedidos</th>' +
                '<th>Valor por unidad del producto</th>' +
                '<th>Valor total del pedido</th>' +
                ' <th>Estado del pedido</th>' +
                '</tr>' +
                ' </thead>' +
                '<tbody>';
        for (var i = 0; i < datos.length; i++) {
            if (datos[i].idEstado === 3) {
                tabla += '<tr  class="table-danger">';
            }else if(datos[i].idEstado === 2){
                tabla+='<tr class="table-success">';
            }else{
                tabla+='<tr>';
            }
            
            tabla+=
                    '<td>' + datos[i].fechaVenta + '</td>' +
                    '<td>' + datos[i].nombreProducto + '</td>' +
                    '<td>' + datos[i].cantidadProductos + '</td>' +
                    '<td>$' + datos[i].valorProducto + '</td>' +
                    '<td>$' + datos[i].valor + '</td>' +
                    '<td>' + datos[i].nombreEstado + '</td></tr>';

        }

        tabla +=
                '</tbody>' +
                ' </table>';
    } else {
        tabla = '<h3>No se encontraron pedidos</h3>';
    }
    $('#informe').empty();
    $('#informe').html(tabla);
}


