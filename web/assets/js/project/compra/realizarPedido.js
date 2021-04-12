//
//
//$(document).on('click', '#meInteresa', function (e) {
//    e.preventDefault()
//    if ($('#nombreUsuarioInicio').val() !== 'no') {
//
//
//        let parent = $(this)[0].parentElement
//        let idEmpresa = $(parent).attr('idEmpresa')
//        let idProducto = $(parent).attr('idProducto')
//        let precio = $(parent).attr('precioProducto')
//        let cantidad = $('#cantidadSelect').val()
//
//        let datos = {
//            idEmpresa: idEmpresa,
//            idProducto: idProducto,
//            Cantidad: cantidad,
//            Precio: precio
//        }
//        console.log(datos)
////
//        Swal.fire({
//            title: 'Pregunta?',
//            text: 'Deseas que el vendedor pueda ver tus datos para contactarte!',
//            icon: 'info',
//            showCancelButton: true,
//            showCloseButton: true,
//            cancelButtonText: 'No',
//            confirmButtonText: 'Si',
//        }).then((result) => {
//            if (result.value) {
//                messageOk('enviado')
//                generateTables(datos, 1)
//                datosVendedor(idEmpresa)
//
//            } else if (result.dismiss === Swal.DismissReason.cancel) {
//                messageError('cancelado')
//                generateTables(datos, 0)
//                datosVendedor(idEmpresa)
//
//            }
//        })
//    } else {
//        $('#detailsProduct').modal('hide');
//        modalPreguntaRegistro();
//    }
//
//})
//
//function generateTables(datos, contacto) {
//
//    datos.contacto = contacto
//    console.log(datos)
//
//    $.ajax({
//        type: "POST",
//        url: './generateSale',
//        async: true,
//        data: {
//            cantidad: datos.Cantidad,
//            contacto: datos.contacto,
//            idEmpresa: datos.idEmpresa,
//            idProducto: datos.idProducto,
//            precioProducto: datos.Precio
//        },
//        datatype: 'json'
//    }).done(function (data) {
//
//        console.log('ok')
//        enviarNot('pedidos', datos.idEmpresa);
//
//    }).fail(function (data) {
//
//
//    })
//
//}
//
//function datosVendedor(data) {
//
//    let datos;
//
//    $.ajax({
//        type: "POST",
//        url: './getInfoCompanyByProduct',
//        async: false,
//        data: {
//            idProducto: data
//        },
//        datatype: 'json'
//    }).done(function (data) {
//
//        datos = data
//
//    })
//
//
//    Swal.fire({
//        title: '<strong>Datos Vendedor</strong>',
//        icon: 'success',
//        html:
//                'Nombre: ' + datos.nombreEmpresa +
//                '<hr> ' +
//                'Dirección: ' + datos.DirEmpresa +
//                '<hr>' +
//                'Celular: ' + datos.CelEmpresa +
//                '<hr> ' +
//                'Teléfono: ' + datos.telEmpresa +
//                '<hr> ' +
//                'Email: ' + datos.correoEmpresa + '',
//        showCloseButton: true,
//        focusConfirm: false,
//        confirmButtonText:
//                '<i class="fas fa-handshake"></i> Genial!',
//        confirmButtonAriaLabel: 'Thumbs up, great!',
//        width: 600,
//        backdrop: `
//    rgba(0,0,123,0.4)
//    left top
//    no-repeat`
//    })
//
//
//}
//
//
