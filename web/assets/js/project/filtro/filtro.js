$(document).ready(function () {

    if (window.location.pathname === '/MercadoSena/') {
        productosRamdom();
    }

    if (window.location.pathname === '/MercadoSena/home') {
        productosRamdom();
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

function centross() {

    $.ajax({
        url: "./filtro",
        type: 'POST',
        async: true,
        data: {
            accion: 'listarCentros'
        }, dataType: 'json',
        error: function (data) {

        }, success: function (data, textStatus, jqXHR) {
            let srt = ``
            srt = '<option value="">Centros...</option>'
            for (var item of data) {
                srt += `<option value="${item.idCentro}">${item.nombreCentro}</option>`
            }
            document.getElementById('idCentro').innerHTML = srt
        }
    })

}

$(document).on('click', '#searching', function (e) {

    e.preventDefault();
    var nombreProductoFiltar = $('#nombreProductoFiltar').val();
    let ciudades = $('#ciudadCriBusqueda').val();
    let categorias = $('#categoriasCriBuscar').val();
    let vendedores = document.getElementById('idCentro').value;

    if (nombreProductoFiltar === '' && ciudades === ''
            && categorias === '' && vendedores === '') {

        messageInfo('Espera, Escribe una palabra clave por favor');
        document.getElementById('nombreProductoFiltar').focus();
        return false;

    }

    if (nombreProductoFiltar.length >= 30) {

        messageInfo('Espera, palabra muy larga');
        document.getElementById('nombreProductoFiltar').value = '';
        document.getElementById('nombreProductoFiltar').focus();
        return false;

    }

    if (!regularExpresion(nombreProductoFiltar)) {
        messageInfo('Espera, palabra invalida');
        document.getElementById('nombreProductoFiltar').value = '';
        document.getElementById('nombreProductoFiltar').focus();
        return false;
    }

    document.getElementById('searching').disabled = true;
    $('#cargas').addClass('is-active');
    let data = {
        word: nombreProductoFiltar,
        categorias: categorias,
        ciudades: ciudades,
        vendedores: vendedores
    };

    let url = '';

    if (nombreProductoFiltar !== '' && categorias === ''
            && ciudades === '' && vendedores === '') {

        url = './getProductsByWord';
        query(data, url);

    } else if (nombreProductoFiltar === '' && categorias !== ''
            && ciudades === '' && vendedores === '') {

        url = './getProductsByCategory';
        query(data, url);

    } else if (nombreProductoFiltar === '' && categorias === ''
            && ciudades !== '' && vendedores === '') {

        url = './getProductsByCity';
        query(data, url);

    } else if (nombreProductoFiltar === '' && categorias === ''
            && ciudades === '' && vendedores !== '') {

        url = './getProductsBySeller';
        query(data, url);

    } else if (nombreProductoFiltar !== '' && categorias !== ''
            && ciudades === '' && vendedores === '') {

        url = './getProductsByNameCategory';
        query(data, url);

    } else if (nombreProductoFiltar !== '' && categorias !== ''
            && ciudades !== '' && vendedores === '') {

        url = './getProductsByNameCategoryCity';
        query(data, url);

    } else if (nombreProductoFiltar === '' && categorias !== ''
            && ciudades !== '' && vendedores === '') {

        url = './getProductsByCategoryCity';
        query(data, url);

    } else if (nombreProductoFiltar !== '' && categorias === ''
            && ciudades !== '' && vendedores === '') {

        url = './getProductsByNameCity';
        query(data, url);

    } else if (nombreProductoFiltar === '' && categorias === ''
            && ciudades !== '' && vendedores !== '') {

        url = './getProductsByCitySeller';
        query(data, url);

    } else if (nombreProductoFiltar !== '' && categorias === ''
            && ciudades !== '' && vendedores !== '') {

        url = './getProductsByNameCitySeller';
        query(data, url);

    } else if (nombreProductoFiltar === '' && categorias !== ''
            && ciudades === '' && vendedores !== '') {

        url = './getProductsByCategorySeller';
        query(data, url);

    } else if (nombreProductoFiltar !== '' && categorias !== ''
            && ciudades === '' && vendedores !== '') {

        url = './getProductsByNameCategorySeller';
        query(data, url);

    } else if (nombreProductoFiltar !== '' && categorias === ''
            && ciudades === '' && vendedores !== '') {

        url = './getProductsByNameSeller';
        query(data, url);

    } else if (nombreProductoFiltar !== '' && categorias !== ''
            && ciudades !== '' && vendedores !== '') {

        url = './getProductsByNameCategoryCitySeller';
        query(data, url);

    } else if (nombreProductoFiltar === '' && categorias !== ''
            && ciudades !== '' && vendedores !== '') {

        url = './getProductsByCategoryCitySeller';
        query(data, url);

    } else {

        console.log('filtro mix');
//        alert('----_----SERVER')

    }

})

function query(datos, url) {

    $.ajax({

        url: url,
        type: 'POST',
        async: true,
        datatype: 'json',
        data: {
            word: datos.word,
            categorias: datos.categorias,
            ciudades: datos.ciudades,
            vendedores: datos.vendedores
        },
        success: function (data) {

            webPageAnimations()
            generatePageQuery(data, 4)
            document.getElementById('searching').disabled = false;

        }
    });

}

function webPageAnimations() {

    $('.contenido').hide('slow')
    document.getElementById('tituloPagina').innerHTML =
            `<h3 class="titulos text-center"><i class="fas fa-search naranja"></i> Busqueda</h3>`
    $('#cargas').removeClass('is-active');

}

function regularExpresion(data) {
    let reg = /^[a-zA-Z0-9-̣\s]*$/
    return reg.test(data)
}
