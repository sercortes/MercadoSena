var todosProductos, resultadoBusqueda = [];
$(document).ready(function () {

    productosMasVendidos();
    consultaCiudad('#ciudadBucar', 'ciudadCriBusqueda');
    listarCategorias('#categoriasBuscar', 'categoriasCriBuscar', 'categorias');
    consultarTodosProductos();
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
            // messageInfo('Error');
            // location.reload();

        }, success: function (data, textStatus, jqXHR) {
            $('#carga').removeClass('is-active');
            if (data !== null) {
                selects(data, idDiv, idInput, '', accion);
            }
        }

    })
}

function productosMasVendidos() {

    $('#carga').addClass('is-active');
    $.ajax({
        url: "./filtro",
        type: 'POST',
        data: {
            accion: 'producMasSolicitados'
        }, dataType: 'json',
        error: function (jqXHR, textStatus, errorThrown) {
            //messageInfo('error');
            //location.reload();
        }, success: function (data, textStatus, jqXHR) {
            if (data !== null && data !== '') {
                console.log(data);
                carruselImagenes(data);
                $('#carga').removeClass('is-active');
            }
        }
    })
}



function consultarTodosProductos() {
    $('#carga').addClass('is-active');
    $.ajax({
        url: "./filtro",
        type: 'POST',
        dataType: 'json',
        data: {
            accion: 'consultarTodosProductos'
        }, error: function (jqXHR, textStatus, errorThrown) {
            $('#carga').removeClass('is-active');
            messageInfo('Ha ocurrido un error con el servidor, favor intentar más tarde.');
        }, success: function (data, textStatus, jqXHR) {
            $('#carga').removeClass('is-active');
            if (data !== null && data !== '') {
                todosProductos = data;
                mostrarProductos(todosProductos, 0);

                console.log(todosProductos);
            }
        }
    })
}

function filtrar() {

    var vendedorCriBuscar = $('#vendedorCriBuscar').val(), ciudadCriBuscar = $('#ciudadCriBusqueda').val(), catCriBuscar = $('#categoriasCriBuscar').val(), nomProBuscar = $('#nombreProductoFiltar').val();

//por ciudad
    if (ciudadCriBuscar !== '' && catCriBuscar === '' && nomProBuscar === '' && vendedorCriBuscar === '') {
        ciudadCriBuscar = parseInt(ciudadCriBuscar);
        for (var i = 0; i < todosProductos.length; i++) {

            if (todosProductos[i].producto.idCiudad === ciudadCriBuscar) {
                resultadoBusqueda.push(todosProductos[i]);
            }
        }
    }
    //por categoria
    if (catCriBuscar !== '' && ciudadCriBuscar === '' && nomProBuscar === '' && vendedorCriBuscar === '') {

        for (var i = 0; i < todosProductos.length; i++) {

            if (todosProductos[i].producto.categorys.idcategoria === catCriBuscar) {
                resultadoBusqueda.push(todosProductos[i]);
            }
        }

    }
    //por nombre
    if (nomProBuscar !== '' && catCriBuscar === '' && ciudadCriBuscar === '' && vendedorCriBuscar === '') {
        for (var i = 0; i < todosProductos.length; i++) {
            if (todosProductos[i].producto.nombreProducto === nomProBuscar) {
                resultadoBusqueda.push(todosProductos[i]);
            }
        }
    }
    //por vendedor
    if (nomProBuscar === '' && catCriBuscar === '' && ciudadCriBuscar === '' && vendedorCriBuscar !== '') {

        for (var i = 0; i < todosProductos.length; i++) {
            if (todosProductos[i].producto.idEmpresaFK === vendedorCriBuscar) {

                resultadoBusqueda.push(todosProductos[i]);
            }
        }
    }

    //por ciudad,cate,vendedor
    if (ciudadCriBuscar !== '' && catCriBuscar !== '' && nomProBuscar === '' && vendedorCriBuscar !== '') {
        ciudadCriBuscar = parseInt(ciudadCriBuscar);
        resultadoBusqueda = [];

        for (var i = 0; i < todosProductos.length; i++) {
            if (todosProductos[i].producto.idCiudad === ciudadCriBuscar && todosProductos[i].producto.categorys.idcategoria === catCriBuscar && todosProductos[i].producto.idEmpresaFK === vendedorCriBuscar) {
                resultadoBusqueda.push(todosProductos[i]);
            }
        }
    }
    //por ciudad,cate
    if (ciudadCriBuscar !== '' && catCriBuscar !== '' && nomProBuscar === '' && vendedorCriBuscar === '') {
        ciudadCriBuscar = parseInt(ciudadCriBuscar);
        resultadoBusqueda = [];

        for (var i = 0; i < todosProductos.length; i++) {
            if (todosProductos[i].producto.idCiudad === ciudadCriBuscar && todosProductos[i].producto.categorys.idcategoria === catCriBuscar ) {
                resultadoBusqueda.push(todosProductos[i]);
            }
        }
    }
    //por ciudad,nombre
    if (ciudadCriBuscar !== '' && catCriBuscar === '' && nomProBuscar !== '' && vendedorCriBuscar === '') {
        ciudadCriBuscar = parseInt(ciudadCriBuscar);
        resultadoBusqueda = [];

        for (var i = 0; i < todosProductos.length; i++) {
            if (todosProductos[i].producto.idCiudad === ciudadCriBuscar && todosProductos[i].producto.nombreProducto === nomProBuscar ) {
                resultadoBusqueda.push(todosProductos[i]);
            }
        }
    }
    //por ciudad,nombre,vendedor
    if (ciudadCriBuscar !== '' && catCriBuscar === '' && nomProBuscar !== '' && vendedorCriBuscar !== '') {
        ciudadCriBuscar = parseInt(ciudadCriBuscar);
        resultadoBusqueda = [];
        for (var i = 0; i < todosProductos.length; i++) {
            if (todosProductos[i].producto.idCiudad === ciudadCriBuscar && todosProductos[i].producto.nombreProducto === nomProBuscar && todosProductos[i].producto.idEmpresaFK === vendedorCriBuscar) {
                resultadoBusqueda.push(todosProductos[i]);
            }
        }
    }
    //ciudad,cat,nombre,vendedor
    if (ciudadCriBuscar !== '' && catCriBuscar !== '' && nomProBuscar !== '' && vendedorCriBuscar !== '') {
        ciudadCriBuscar = parseInt(ciudadCriBuscar);
        resultadoBusqueda = [];
        for (var i = 0; i < todosProductos.length; i++) {
            if (todosProductos[i].producto.idCiudad === ciudadCriBuscar && todosProductos[i].producto.nombreProducto === nomProBuscar && todosProductos[i].producto.categorys.idcategoria === catCriBuscar && todosProductos[i].producto.idEmpresaFK === vendedorCriBuscar) {
                resultadoBusqueda.push(todosProductos[i]);
            }
        }
    }
    //ciudad,cat,nombre
    if (ciudadCriBuscar !== '' && catCriBuscar !== '' && nomProBuscar !== '' && vendedorCriBuscar === '') {
        ciudadCriBuscar = parseInt(ciudadCriBuscar);
        resultadoBusqueda = [];
        for (var i = 0; i < todosProductos.length; i++) {
            if (todosProductos[i].producto.idCiudad === ciudadCriBuscar && todosProductos[i].producto.nombreProducto === nomProBuscar && todosProductos[i].producto.categorys.idcategoria === catCriBuscar ) {
                resultadoBusqueda.push(todosProductos[i]);
            }
        }
    }

//ciudad,vendedor
    if (ciudadCriBuscar !== '' && catCriBuscar === '' && nomProBuscar === '' && vendedorCriBuscar !== '') {
        ciudadCriBuscar = parseInt(ciudadCriBuscar);
        resultadoBusqueda = [];
        for (var i = 0; i < todosProductos.length; i++) {
            if (todosProductos[i].producto.idCiudad === ciudadCriBuscar && todosProductos[i].producto.idEmpresaFK === vendedorCriBuscar) {
                resultadoBusqueda.push(todosProductos[i]);
            }
        }
    }
    //cate,vendedor
    if (ciudadCriBuscar === '' && catCriBuscar !== '' && nomProBuscar === '' && vendedorCriBuscar !== '') {
        ciudadCriBuscar = parseInt(ciudadCriBuscar);
        resultadoBusqueda = [];
        for (var i = 0; i < todosProductos.length; i++) {
            if (todosProductos[i].producto.idCiudad === ciudadCriBuscar && todosProductos[i].producto.idEmpresaFK === vendedorCriBuscar) {
                resultadoBusqueda.push(todosProductos[i]);
            }
        }
    }
//cat,nombre,
    if (catCriBuscar !== '' && ciudadCriBuscar === '' && nomProBuscar !== '' && vendedorCriBuscar === '') {
        resultadoBusqueda = [];
        for (var i = 0; i < todosProductos.length; i++) {
            if (todosProductos[i].producto.nombreProducto === nomProBuscar && todosProductos[i].producto.categorys.idcategoria === catCriBuscar) {
                resultadoBusqueda.push(todosProductos[i]);
            }
        }
    }

  //nombre vendedor

    if (ciudadCriBuscar === '' && catCriBuscar === '' && nomProBuscar !== '' && vendedorCriBuscar !== '') {
        ciudadCriBuscar = parseInt(ciudadCriBuscar);
        resultadoBusqueda = [];
        for (var i = 0; i < todosProductos.length; i++) {
            if (todosProductos[i].producto.idCiudad === ciudadCriBuscar && todosProductos[i].producto.nombreProducto === nomProBuscar && todosProductos[i].producto.categorys.idcategoria === catCriBuscar && todosProductos[i].producto.idEmpresaFK === vendedorCriBuscar) {
                resultadoBusqueda.push(todosProductos[i]);
            }
        }
    }
//todos
    if (catCriBuscar === '' && ciudadCriBuscar === '' && nomProBuscar === '' && vendedorCriBuscar === '') {
        resultadoBusqueda = [];
        resultadoBusqueda = todosProductos;

    }
    mostrarProductos(resultadoBusqueda, 1);

    resultadoBusqueda = [];

}

function verProducto(id, event) {

    var producto = [];
    if (event !== null && event !== undefined) {
        event.preventDefault();
    }
    for (var i = 0; i < todosProductos.length; i++) {
        id = id.toString()

        if (todosProductos[i].producto.idProducto === id) {
            producto = todosProductos[i];
            i = todosProductos.length;
        }
    }

    generarCaruselImagenesProducto(producto.imagenes);
    generarTextoProducto(producto.producto);
    $('#detailsProduct').modal('show');
//    setTimeout(() => $('.hijueputa').carousel({
//            interval: 6100,
//        }), 1000)

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
            alert('error');
        }, success: function (data, textStatus, jqXHR) {
            console.log(data);
            selects(data, idDiv, idInput, valor, accion);
        }
    })

}

