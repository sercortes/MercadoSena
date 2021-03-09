function addCar(item, cantidad) {

    let arraf = JSON.parse(localStorage.getItem('objects'));
    let exts = arraf.filter(x => x.idProducto === item.idProducto).length > 0

    if (exts) {

        let producto = arraf.find(element => element.idProducto === item.idProducto);
        arraf = arraf.filter(x => x.idProducto !== item.idProducto)

        if (producto.cantidad + cantidad > item.stockProducto) {
            messageInfo('No hay sufuciente cantidad de producto')
            $('#detailsProduct').modal('hide')
            showCar()
            return false
        }

        producto.cantidad = producto.cantidad + cantidad
        arraf.push(producto)
        localStorage.setItem('objects', JSON.stringify(arraf));

    } else {
        item.cantidad = cantidad;
        arraf.push(item);
        localStorage.setItem('objects', JSON.stringify(arraf));
    }

}


function showCar() {

    $('#modalCar').modal('show')
    let arraf = JSON.parse(localStorage.getItem('objects'));
    let total = 0
    let str = ''
    for (var item of arraf) {
        str += `<tr><td>`
        if (item.imagenUnitaria !== undefined) {
            str += `<img src="${item.imagenUnitaria}" alt="" width="70" class="img-fluid rounded shadow-sm">`
        } else {
            str += `<img src="${item.imagenes[0].url}" alt="" width="70" class="img-fluid rounded shadow-sm">`
        }
        str += `</td><td> 
                                
                                <div class="ml-3 d-inline-block align-middle">
                                    <h5 class="mb-0">
                                        <p class="mb-0 text-dark d-inline-block align-middle text-justify">${item.nombreProducto}</p>
                                        <span class="text-muted font-weight-normal font-italic d-block">Categoría: ${item.categorys.nombreCategoria}</span>
                                </div>
                            </div>
                        </td>
                        <td class="border-0 align-middle pl-3"><strong>${item.color}</strong></td>
                        <td class="border-0 align-middle pl-5"><strong>${item.cantidad}</strong></td>
                        <td class="border-0 align-middle pl-4" idProducto="${item.idProducto}">
                            <a id="delete" href="#" class="text-dark pl-3"><i class="fa fa-trash"></i></a></td>
                        <td class="border-0 align-middle"><strong>${item.valorProducto.toString().replace(/(\d)(?=(\d\d\d)+(?!\d))/g, "$1.")}</strong></td>
                    </tr>`
        total += item.cantidad * item.valorProducto
    }
    document.getElementById('card').innerHTML = str

    str = `<li class="d-flex justify-content-between py-3 border-bottom"><strong class="text-muted">Subtotal </strong><strong>${money(total)}</strong></li>
                                <li class="d-flex justify-content-between py-3 border-bottom"><strong class="text-muted">Total</strong><strong>${money(total)}</strong></li>
                                <input type="hidden" id="valor" value="${money(total)}">
                                <!--<li class="d-flex justify-content-between py-3 border-bottom"><strong class="text-muted">Total</strong>-->
                                    <!--<h5 class="font-weight-bold float-right">$45.000.00</h5>-->
                                </li>`
    document.getElementById('total').innerHTML = str

}

$(document).on('click', '#delete', function (e) {
    e.preventDefault()
    let parent = $(this)[0].parentElement
    let idProducto = $(parent).attr('idProducto')
    let arraf = JSON.parse(localStorage.getItem('objects'));
    arraf = arraf.filter(item => item.idProducto !== idProducto);
    localStorage.setItem('objects', JSON.stringify(arraf));
    showCar()
});

$(document).on('click', '#buttonCars', function (e) {

    showCar()

});

$("#modalCar").on('hidden.bs.modal', function () {

    updateIconNumber()

});

function updateIconNumber() {

    let arraf = JSON.parse(localStorage.getItem('objects'));
    let str = ''
    let ex = document.getElementById('buttonCars')

    if (arraf.length === 0) {
        if (ex !== null) {
            document.getElementById("buttonCars").remove();
        }
        return false
    }

    if (ex !== null) {
        document.getElementById('number').innerHTML
                = `<i class="fas fa-cart-arrow-down"></i> ${arraf.length}`
    } else {

        str = ` <li class="nav-item" id="buttonCars">
                <a id="number" class="nav-link encabezadoOpciones" href="#">
                        <i class="fas fa-cart-arrow-down"></i> ${arraf.length}</a>
            </li>`
        document.getElementById('navbars').innerHTML += str

    }
}

$("#btnpagar").click(function () {

    if (!checkSession()) {
        modalPreguntaRegistro();
        return false
    }

    let rol = getRol()

    if (rol == 2) {

        if (checkData()) {
            messageInfo('Necesitamos tu información para completar la compra')
            $('#modalUpdateData').modal('show')
            $('#modalCar').modal('hide')
            return false
        }

        valor = document.getElementById('valor').value;
        location.href = "process_payment?valor=" + valor;
    }



});

function money(dolar) {
    return dolar.toString().replace(/(\d)(?=(\d\d\d)+(?!\d))/g, "$1.")
}
