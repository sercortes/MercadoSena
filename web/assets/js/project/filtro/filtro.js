var todosProductos, resultadoBusqueda = [];
var nombres = [];

$(document).ready(function () {

    productosRamdom()

})

$('#desplegarMenu').click(function () {

    $('.busquedaAvanzada').toggle();

    consultaCiudad('#ciudadBucar', 'ciudadCriBusqueda');
    listarCategorias('#categoriasBuscar', 'categoriasCriBuscar', 'categorias');
    vendedores('#vendedores', 'vendedorCriBuscar', 'vendedor', '');


})

function listarCategorias(idDiv, idInput, accion) {
    $('#carga').addClass('is-active');
    $.ajax({
        url: "./registro",
        type: 'POST',
        dataType: 'json',
        data: {
            accion: 'listarCategorias'
        }, error: function (jqXHR, textStatus, errorThrown) {

        }, success: function (data, textStatus, jqXHR) {
            if (data !== null) {
                selects(data, idDiv, idInput, '', accion);
            }
        }

    })
}

function vendedores(idDiv, idInput, accion, valor) {
    var vendedores = [];
    $('#carga').addClass('is-active');
    $.ajax({
        url: "./filtro",
        type: 'POST',
        data: {
            accion: 'listarVendedores'
        }, dataType: 'json',
        error: function (jqXHR, textStatus, errorThrown) {

        }, success: function (data, textStatus, jqXHR) {

            selects(data, idDiv, idInput, valor, accion);
        }
    })

}

$('#buscadorlike').submit(function (e) {

    e.preventDefault();
    e.stopPropagation();
    var nombreProductoFiltar = $('#nombreProductoFiltar').val();

    if (nombreProductoFiltar === '' || nombreProductoFiltar === null) {
        mensaje('Por favor complete el campo');
    } else {

        console.log(nombreProductoFiltar);
        var btn = document.getElementById('senddatoslike');
        var datos = $('#buscadorlike').serialize();
        btn.disabled = true;
        $.ajax({

            url: "./filtro?accion=buscadorlikes&" + datos,
            type: 'POST',
            contentType: false,
            processData: false, error: function (jqXHR, textStatus, errorThrown) {
                messageInfo('Ha ocurrido un error con el servidor, favor intentar más tarde.');
                btn.disabled = false;
            },
            success: function (data) {

                if (data === 'true') {
                    setTimeout(function () {
                        window.location = "./Searching...";
                    }, 1800);
                } else {
                    mensajesdeErrors('Este producto no disponible!!');
                }

                btn.disabled = false;
            }
        });

    }
});


function mensajesdeErrors(mensaje) {
    Swal.fire({
        position: 'center',
        icon: 'error',
        title: 'Producto fuera de stock',
        html: '<h4 style="color:#f27474;">' + mensaje + '</h4>',
        showCancelButton: false,
        showConfirmButton: false,
        allowOutsideClick: false,
        allowEscapeKey: false,
        allowEnterKey: false,
        padding: '2rem',
        width: '25%',
        timer: 1200

    });
}