function carruselImagenes(datos) {
    var imagenes = '';
//    imagenes += ' <ul class="carousel-indicators">';
//    //mostrar productos mas vendidos
//    for (var i = 0; i < datos.length; i++) {
//        if (i === 0) {
//            imagenes += '<li data-target="#myCarousel" data-slide-to="0" class="active"></li>';
//        } else {
//            imagenes += ' <li data-target="#myCarousel" data-slide-to="'+i+'"></li>';
//        }
//    }
//    imagenes += '</ul>';
    imagenes += '<div class="carousel-inner" style="background-image:url(./assets/images/products/fondo.jpg);text-align: center; height:500px">';
    for (var i = 0; i < datos.length; i++) {

        if (i === 0) {
            imagenes += '<div class="carousel-item active">' +
                    '<img title="Ver producto" onclick="verProducto(' + datos[i].listaImagenes[0].idProductoFK + ' )" src="' + datos[i].listaImagenes[0].url + '" alt="Imagen producto" width="1100"  class="imagenCarrusel" >' +
                    '</div>';
        } else {
            imagenes += '<div class="carousel-item">' +
                    '<img onclick="verProducto(' + datos[i].listaImagenes[0].idProductoFK + ' )" title="Ver producto" src="' + datos[i].listaImagenes[0].url + '" width="1100"  class="imagenCarrusel" alt="Imagen producto">' +
                    '</div>';
        }

    }
    imagenes += '</div>';
    $('#carruselImagenes').empty();
    $('#carruselImagenes').html(imagenes);

}

function mostrarProductos(productos,mosTit) {
    console.log(productos);
    resultadoBusqueda = [];
    console.log(resultadoBusqueda);

    let idcompanyss = document.getElementById('companyss').value;
    let select = document.getElementById('tabla');
    let str = ``
    let num = 1;

    if (productos.length > 0) {

        for (var item of productos) {
            var n = 0;
            str += `<div class="col-lg-3" style="margin-top:30px;">
          <figure class="rounded p-3 bg-white shadow-sm" idProducto="${item.producto.idProducto}" idEmpresa="${item.producto.idEmpresaFK}">`

            str += `<div id="carouselExampleControls${num}" class="carousel slide hijueputa" data-ride="carousel">
                    <div class="carousel-inner" id="caruselOne${num}">`;

            for (var ite of item.imagenes) {
                if (n === 0) {
                    str += `<div class="carousel-item active">
                            <img class="img-fluid fit-image" src="${ite.url}">
                        </div>`;
                } else {
                    str += `<div class="carousel-item ssss">
                            <img class="img-fluid fit-image" src="${ite.url}">
                        </div>`;
                }
                n++;
            }
            str += ` </div>
                    <a class="carousel-control-prev" href="#carouselExampleControls${num}" role="button" data-slide="prev">
                        <span class="carousel-control-prev-icon" aria-hidden="true"></span>
                        <span class="sr-only">Anterior</span>
                    </a>
                    <a class="carousel-control-next" href="#carouselExampleControls${num}" role="button" data-slide="next">
                        <span class="carousel-control-next-icon" aria-hidden="true"></span>
                        <span class="sr-only">Siguiente</span>
                    </a>
                </div>`;

            str += `<figcaption class="p-3 card-img-bottom">
              <h2 class="h5 font-weight-bold mb-2">$${item.producto.valorProducto}</h2>
              <h4 class="text-left text-muted">${item.producto.nombreProducto}</h4>
            </figcaption>
       
      <div class="col-lg-12 mb-4 p-0">
       <a data-toggle="collapse" href="#collapseExample${num}" role="button" aria-expanded="false" aria-controls="collapseExample1" class="btn btn-primary btn-block py-2 shadow-sm with-chevron">
          <p class="d-flex align-items-center justify-content-between mb-0 px-3 py-2"><strong class="text-uppercase">Descripción</strong><i class="fa fa-angle-down"></i></p>
        </a>
        <div id="collapseExample${num}" class="collapse shadow-sm">
          <div class="card">
            <div class="card-body">
              <p class="font-italic mb-0 text-muted">${item.producto.descripcionProducto}</p>
            </div>
          </div>
        </div>
      </div>`

            str += `<div class="text-right">
                            <a href="#" class="botonChat btn btn-primary"><i class="fas fa-comments"></i></a>
                    <a href="#" title="Ver detalles" class="watch btn btn-primary" onclick="verProducto(${item.producto.idProducto},event)"><i class="fas fa-images"></i></a>
                        </div>`

            str += `</figure>
        </div>`;
            num++;



        }
    } else {
        str = '<div style="width: 100%;padding: 20px;"><i class="fa fa-frown" style="font-size: 69px;" aria-hidden="true"></i><p style="color: black;font-size: 24px;font-weight: 600;font-family: cursive;">Lo sentimos, no se encontraron productos.</p></div>';
    }
    select.innerHTML = str;
    if(mosTit===1){
    $('#tituloResultado').html('<div style="width: 100%; text-align:center;margin-top: 20px; "><i class="fa fa-search" style="font-size: 69px;" aria-hidden="true"></i><h3 style="color: black;font-size: 24px;font-weight: 600;font-family: cursive;">Resultados de su búsqueda...</h3></div>');
    }
}
