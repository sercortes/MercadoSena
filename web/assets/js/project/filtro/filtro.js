

$(document).ready(function () {

    
    if (window.location.pathname === '/Store/' || window.location.pathname === '/Store/home') {
        console.time('loop');
        $('#cargas').addClass('is-active');
        productosRamdom();
        banner()
    }

})

function productosRamdom() {

    $.ajax({
        type: "POST",
        url: './getProductsRandom',
        async: true,
        datatype: 'json'
    }).done(function (data) {

        generatePageQuery(data, 12)

    })

}

function banner() {

    $.ajax({
        type: "POST",
        url: './banner',
        async: true,
        datatype: 'json'
    }).done(function (data) {

        let id = document.getElementsByClassName('letras')
         for (var i = 0; i < data.length; i++) {
             id[i].innerText = data[i].frase
        }

    })

}

$('#desplegarMenu').click(function () {

    $('.busquedaAvanzada').toggle();
})

$(document).on('click', '#searching', function (e) {

    e.preventDefault();
    var nombreProductoFiltar = $('#nombreProductoFiltar').val();
    let categorias = $('#categoriasCriBuscar').val();
    let marca = $('#marcaProducto').val();

    if (window.location.pathname === '/MercadoSena/ventasVendedor') {
        $('#content').hide()
    }

    if (nombreProductoFiltar === ''
            && categorias === '' && marca === '') {

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
        marca: marca
    };

    let url = '';
    
    if (nombreProductoFiltar !== '' && categorias === ''
            && marca === '') {

        url = './getProductsByWord';
        query(data, url);
            
    } else if (nombreProductoFiltar === '' && categorias !== ''
            && marca === '') {

        url = './getProductsByCategory';
        query(data, url);

    }else if (nombreProductoFiltar !== '' && categorias !== ''
            && marca === '') {

        url = './getProductsByNameCategory';
        query(data, url);

    }else if (nombreProductoFiltar === '' && categorias === ''
            && marca !== ''){
        
        url = './getProductsByMarca';
        query(data, url);

    }else if (nombreProductoFiltar !== '' && categorias === ''
            && marca !== ''){
        
        url = './getProductsByNameMarca';
        query(data, url)

    }else if (nombreProductoFiltar === '' && categorias !== ''
            && marca !== ''){
        
        url = './getProductsByCategoryMarca';
        query(data, url)

    }else if (nombreProductoFiltar !== '' && categorias !== ''
            && marca !== ''){
        
        url = './getProductsByNameCategoryMarca';
        query(data, url);

    } else {
        
        url = './getProductsByWord';
        query(data, url);

    }

})

function query(datos, url) {

    $('#cargas').addClass('is-active');
    console.time('loop');

    $.ajax({

        url: url,
        type: 'POST',
        async: true,
        datatype: 'json',
        data: {
            word: datos.word,
            categorias: datos.categorias,
            marca: datos.marca
        },
        success: function (data) {
            
            webPageAnimations()
            generatePageQuery(data, 4)
            document.getElementById('searching').disabled = false;
            $('#pagee').show()

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

function getMarcas() {

    $.ajax({
        url: "./filtro",
        type: 'POST',
        async: true,
        data: {
            accion: 'listarMarcas'
        }, dataType: 'json',
        error: function (data) {

        }, success: function (data, textStatus, jqXHR) {
            let srt = ``
            srt = '<option value="">Marca...</option>'
            for (var item of data) {
                srt += `<option value="${item.idMarca}">${item.nombreMarca}</option>`
            }
            document.getElementById('marcaProducto').innerHTML = srt
        }
    })

}
