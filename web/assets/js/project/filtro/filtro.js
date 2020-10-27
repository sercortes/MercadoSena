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

        var btn = document.getElementById('senddatoslike');
        btn.disabled = true;
        $.ajax({

            url: "./getProductsByWord",
            type: 'POST',
            async: true,
            datatype: 'json',
            data: {
                word:nombreProductoFiltar
            },
            success: function (data) {

                    setTimeout(function () {
                          generatePageQuery(data)
                    }, 500);

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