var todosProductos, resultadoBusqueda = [];
var nombres = [];

$(document).ready(function () {

    if (window.location.pathname === '/MercadoSena/') {
        productosRamdom()
    }

})

$('#desplegarMenu').click(function () {

    $('.busquedaAvanzada').toggle();

})

function consultaCiudadS() {
    $.ajax({
        url: "./registro?accion=consultaCiudad",
        type: 'POST',
        dataType: 'json',
        async: true,
        error: function (e) {
            $('#bloqueo').hide();
            $('#modalRegistro').hide();
            // $('#carga').removeClass('is-active');
            messageInfo('Ha ocurrido un error con el servidor, favor intentar más tarde.')
        },
        success: function (data) {
            let srt = ``
            srt = '<option value="">Ciudad ...</option>'
            for (var item of data) {
                srt += `<option value="${item.idCiudad}">${item.nombreCiudad}</option>`
            }
            document.getElementById('ciudadCriBusqueda').innerHTML = srt
        }
    })

}

function listarCategoriasS() {
    $.ajax({
        url: "./registro",
        type: 'POST',
        dataType: 'json',
        async: true,
        data: {
            accion: 'listarCategorias'
        }, error: function () {

        }, success: function (data) {
            let srt = ``
            srt = '<option value="">Categorías...</option>'
            for (var item of data) {
                srt += `<option value="${item.idcategoria}">${item.nombreCategoria}</option>`
            }
            document.getElementById('categoriasCriBuscar').innerHTML = srt
        }
    })
}

function vendedoresS() {

    $.ajax({
        url: "./filtro",
        type: 'POST',
        async: true,
        data: {
            accion: 'listarVendedores'
        }, dataType: 'json',
        error: function (data) {

        }, success: function (data, textStatus, jqXHR) {
            let srt = ``
            srt = '<option value="">Vendedores...</option>'
            for (var item of data) {
                srt += `<option value="${item.idEmpresa}">${item.nombreEmpresa}</option>`
            }
            document.getElementById('vendedorCriBuscar').innerHTML = srt
        }
    })

}

$(document).on('click', '#searching', function (e) {

    e.preventDefault();
    var nombreProductoFiltar = $('#nombreProductoFiltar').val();
    let ciudades = $('#ciudadCriBusqueda').val()
    let categorias = $('#categoriasCriBuscar').val()
    let vendedores = $('#vendedorCriBuscar').val()

    console.log(ciudades)
    console.log(categorias)
    console.log(vendedores)

    if (nombreProductoFiltar === '' && ciudades === '' 
            && categorias === '' && vendedores === '') {
        
        messageInfo('Espera, Escribe una palabra clave por favor');
        document.getElementById('nombreProductoFiltar').focus()
        return false
        
    }

     var btn = document.getElementById('searching');
     btn.disabled = true;
     $('#cargas').addClass('is-active');
    
    if(nombreProductoFiltar !== '' && categorias === '' 
            && ciudades === '' && vendedores === ''){
     
        queryWord(nombreProductoFiltar)
        
    }else if(nombreProductoFiltar !== '' && categorias !== '' 
            && ciudades === '' && vendedores === ''){
     
       console.log('X nombre y categoria') 
        
    }else if(nombreProductoFiltar !== '' && categorias !== '' 
            && ciudades !== '' && vendedores === ''){
     
       console.log('X nombre y categoria y ciudad') 
        
    }else if(nombreProductoFiltar !== '' && categorias !== ''
            && ciudades !== '' && vendedores !== ''){
        
        console.log('filtro con todas opciones')
    
    }else{
        
        console.log('filtro mix')
        
    }
    


})

function queryWord(nombreProductoFiltar){
    
        $.ajax({

            url: "./getProductsByWord",
            type: 'POST',
            async: true,
            datatype: 'json',
            data: {
                word: nombreProductoFiltar
            },
            success: function (data) {

                webPageAnimations()
                generatePageQuery(data, 4)
                btn.disabled = false;

            }
        });

}

function webPageAnimations() {

    $('.contenido').hide('slow')
    document.getElementById('tituloPagina').innerHTML =
            `<h3 class="titulos text-center"><i class="fas fa-search naranja"></i> Busqueda</h3>`
    $('#cargas').removeClass('is-active');

}
