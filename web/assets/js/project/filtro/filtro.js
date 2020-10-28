var todosProductos, resultadoBusqueda = [];
var nombres = [];

$(document).ready(function () {

    if(window.location.pathname === '/MercadoSena/'){
        productosRamdom()
    }
    
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

$(document).on('click', '#searching', function(e){
    
    e.preventDefault();
    var nombreProductoFiltar = $('#nombreProductoFiltar').val();

    if (nombreProductoFiltar === '' || nombreProductoFiltar === null) {
        mensajesdeErrors('¡Escribe tu búsqueda en el campo que figura en la parte superior de la pantalla!');
    } else {

        var btn = document.getElementById('searching');
        btn.disabled = true;
        $('#cargas').addClass('is-active'); 
        
        $.ajax({

            url: "./getProductsByWord",
            type: 'POST',
            async: true,
            datatype: 'json',
            data: {
                word:nombreProductoFiltar
            },
            success: function (data) {
                
                        webPageAnimations()
                        generatePageQuery(data, 4)

                btn.disabled = false;
            }
        });

    }
    
})

function webPageAnimations(){
    
      $('.contenido').hide('slow')
        document.getElementById('tituloPagina').innerHTML = 
                ` <h3 class="titulos text-center"><i class="fas fa-search naranja"></i> Busqueda</h3>`
        $('#cargas').removeClass('is-active');
    
}

function mensajesdeErrors(mensaje) {
    Swal.fire({
        position: 'center',
        icon: 'error',
        title: 'Busqueda fallida',
        html: '<h4 style="color:#f27474;">' + mensaje + '</h4>',
        showCancelButton: false,
        showConfirmButton: false,
        allowOutsideClick: false,
        allowEscapeKey: false,
        allowEnterKey: false,
        padding: '2rem',
        width: '25%',
        timer: 1700

    });
}