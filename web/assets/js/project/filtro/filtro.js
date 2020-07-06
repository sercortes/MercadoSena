var todosProductos, resultadoBusqueda = [];
$(document).ready(function () {

    productosMasVendidos();
    consultaCiudad('#ciudadBucar', 'ciudadCriBusqueda');
    listarCategorias('#categoriasBuscar', 'categoriasCriBuscar', 'categorias');
    consultarTodosProductos();

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

function verProducto(id,event) {
    if(event!==null && event!==undefined){
    event.preventDefault();}
    alert(id);
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
                mostrarProductos(todosProductos,0);
                console.log(todosProductos);
            }
        }
    })
}

function filtrar() {

    var ciudadCriBuscar = $('#ciudadCriBusqueda').val(), catCriBuscar = $('#categoriasCriBuscar').val(), nomProBuscar = $('#nombreProductoFiltar').val();

    if (ciudadCriBuscar !== '' && catCriBuscar === '' && nomProBuscar === '') {
        ciudadCriBuscar = parseInt(ciudadCriBuscar);
        for (var i = 0; i < todosProductos.length; i++) {

            if (todosProductos[i].producto.idCiudad === ciudadCriBuscar) {
                resultadoBusqueda.push(todosProductos[i]);
            }
        }
    }
    if (catCriBuscar !== '' && ciudadCriBuscar === '' && nomProBuscar === '') {

        for (var i = 0; i < todosProductos.length; i++) {

            if (todosProductos[i].producto.categorys.idcategoria === catCriBuscar) {
                resultadoBusqueda.push(todosProductos[i]);
            }
        }

    }
    if (nomProBuscar !== '' && catCriBuscar === '' && ciudadCriBuscar === '') {
        for (var i = 0; i < todosProductos.length; i++) {
            if (todosProductos[i].producto.nombreProducto === nomProBuscar) {
                resultadoBusqueda.push(todosProductos[i]);
            }
        }
    }

    //opciones con ciudad
    if (ciudadCriBuscar !== '' && catCriBuscar !== '' && nomProBuscar === '') {
        ciudadCriBuscar = parseInt(ciudadCriBuscar);
        resultadoBusqueda = [];

        for (var i = 0; i < todosProductos.length; i++) {
            if (todosProductos[i].producto.idCiudad === ciudadCriBuscar && todosProductos[i].producto.categorys.idcategoria === catCriBuscar) {
                resultadoBusqueda.push(todosProductos[i]);
            }
        }
    }
    if (ciudadCriBuscar !== '' && catCriBuscar === '' && nomProBuscar !== '') {
        ciudadCriBuscar = parseInt(ciudadCriBuscar);
        resultadoBusqueda = [];
        for (var i = 0; i < todosProductos.length; i++) {
            if (todosProductos[i].producto.idCiudad === ciudadCriBuscar && todosProductos[i].producto.nombreProducto === nomProBuscar) {
                resultadoBusqueda.push(todosProductos[i]);
            }
        }
    }
    if (ciudadCriBuscar !== '' && catCriBuscar !== '' && nomProBuscar !== '') {
        ciudadCriBuscar = parseInt(ciudadCriBuscar);
        resultadoBusqueda = [];
        for (var i = 0; i < todosProductos.length; i++) {
            if (todosProductos[i].producto.idCiudad === ciudadCriBuscar && todosProductos[i].producto.nombreProducto === nomProBuscar && todosProductos[i].producto.categorys.idcategoria === catCriBuscar) {
                resultadoBusqueda.push(todosProductos[i]);
            }
        }
    }

//opciones categoría
    if (catCriBuscar !== '' && ciudadCriBuscar === '' && nomProBuscar !== '') {
        resultadoBusqueda = [];
        for (var i = 0; i < todosProductos.length; i++) {
            if (todosProductos[i].producto.nombreProducto === nomProBuscar && todosProductos[i].producto.categorys.idcategoria === catCriBuscar) {
                resultadoBusqueda.push(todosProductos[i]);
            }
        }
    }
    if (catCriBuscar === '' && ciudadCriBuscar === '' && nomProBuscar === '') {
        resultadoBusqueda = [];
        resultadoBusqueda = todosProductos;

    }
    mostrarProductos(resultadoBusqueda,1);

    resultadoBusqueda = [];

}


