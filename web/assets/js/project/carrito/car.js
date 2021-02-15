function addCar(item) {
    
    let arraf = JSON.parse(localStorage.getItem('objects'));
    let exts = arraf.filter(x => x.idProducto === item.idProducto ).length > 0
    if (exts) {
        arraf = arraf.filter(x => x.idProducto !== item.idProducto)
        item.cantidad = item.cantidad+1
        arraf.push(item)
        localStorage.setItem('objects', JSON.stringify(arraf));
    }else{
        item.cantidad = 1;
        arraf.push(item);
        localStorage.setItem('objects', JSON.stringify(arraf));
    }
    console.log(item)

}
function showCar() {

    $('#modalCar').modal('show')
    let arraf = JSON.parse(localStorage.getItem('objects'));
    let total = 0
    let str = ''
    for (var item of arraf) {
        str += ` <tr>
                    <th scope="row" class="border-0">
                            <div class="p-2">
                                <img src="${item.imagenes[0].url}" alt="" width="70" class="img-fluid rounded shadow-sm">
                                <div class="ml-3 d-inline-block align-middle">
                                    <h5 class="mb-0">
                                        <a href="#" class="text-dark d-inline-block align-middle">${item.nombreProducto}</a></h5>
                                        <span class="text-muted font-weight-normal font-italic d-block">Categoría: ${item.categorys.nombreCategoria}</span>
                                </div>
                            </div>
                        </th>
                        <td class="border-0 align-middle"><strong>${item.cantidad}</strong></td>
                        <td class="border-0 align-middle pl-4" idProducto="${item.idProducto}">
                            <a id="delete" href="#" class="text-dark"><i class="fa fa-trash"></i></a></td>
                        <td class="border-0 align-middle"><strong>${item.valorProducto.toString().replace(/(\d)(?=(\d\d\d)+(?!\d))/g, "$1.")}</strong></td>
                    </tr>`
        total +=item.cantidad * item.valorProducto
    }
    document.getElementById('card').innerHTML = str
    
    str=`<li class="d-flex justify-content-between py-3 border-bottom"><strong class="text-muted">Subtotal </strong><strong>${money(total)}</strong></li>
                                <li class="d-flex justify-content-between py-3 border-bottom"><strong class="text-muted">Total</strong><strong>${money(total)}</strong></li>
                                <!--<li class="d-flex justify-content-between py-3 border-bottom"><strong class="text-muted">Total</strong>-->
                                    <!--<h5 class="font-weight-bold float-right">$45.000.00</h5>-->
                                </li>`
    document.getElementById('total').innerHTML = str
    
}

$(document).on('click', '#delete', function(e){
    e.preventDefault()
    let parent = $(this)[0].parentElement
    let idProducto = $(parent).attr('idProducto')
    let arraf = JSON.parse(localStorage.getItem('objects'));
    arraf = arraf.filter(item => item.idProducto !== idProducto);
    localStorage.setItem('objects', JSON.stringify(arraf));
    showCar()
})

function money(dolar){
    return dolar.toString().replace(/(\d)(?=(\d\d\d)+(?!\d))/g, "$1.")
}