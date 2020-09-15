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


