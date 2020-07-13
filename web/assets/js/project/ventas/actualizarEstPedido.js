function validarBtn(id) {
    var estado = $('#estadoPedido' + id).val();
    var btn = $('#estadoPedido' + id).next();

    if (estado === '1') {
        btn.prop('disabled', true);
    } else {
        btn.prop('disabled', false);
    }

}



function actualizarEstadoPed(id) {
    Swal.fire({
        title:'Confirmación',
        text:'¿Está seguro que desea actualizar el pedido?',
        showCancelButton:true,
        showCloseButton:true,
        cancelButtonText:'No',
        confirmButtonText:'Si'
    }).then((result)=>{
        if(result.value){
             var cant, idPro;
        for (var i = 0; i < todosPedidos.length; i++) {
            if (parseInt(todosPedidos[i].ventaDTO.idVenta) === id) {
                cant = todosPedidos[i].prodPedidoDTO.cantidad;
                idPro = todosPedidos[i].prodPedidoDTO.idProductoFK;

            }
            //todosPedidos[i].prodPedidoDTO
        }



        var estado = $('#estadoPedido' + id).val();
        $('#carga').addClass('is-active');
        $.ajax({
            url: "./gestionarPedidos",
            type: 'POST',
            data: {
                accion: 'actEstPed',
                idVenta: id,
                idEstado: estado,
                cantidadVendida: cant,
                idProducto: idPro
            }, error: function (jqXHR, textStatus, errorThrown) {
                $('#carga').removeClass('is-active');
                messageInfo('Ha ocurrido un error con el servidor, favor intentar más tarde.');

            }, success: function (data) {
                $('#carga').removeClass('is-active');
                if (data === 'true') {
                    messageOk('Se ha actualizado correctamente!!');
                    consultaPedidos(true);
                    consultaNotiPedidos('no');
                } else {
                    messageError('Ha ocurrido un error al  actualizar, favor verificar datos.');
                }
            }
        })
            
        }
    })
       
    
}


