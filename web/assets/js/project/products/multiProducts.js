let arrayProducts = []
let contador = 1;

$(document).on('click', '#addProduct', function (e) {
    
    let idColor = document.getElementById('color').value
    let cantidad = document.getElementById('cantidad').value
    
    let textColor = document.getElementById('color')
    let nombreColor = textColor.options[textColor.selectedIndex].text
    $(".remove").remove();
    e.preventDefault();

    if (idColor == '') {
        checkInputGlobal('color', 'selecione un color, por favor');
        return false
    }
    if (cantidad == '') {
        checkInputGlobal('cantidad', 'Escriba un cantidad, por favor');
        return false
    }
    
    if (arrayProducts.length>0) {
        let busque = arrayProducts.find(element => element.color === idColor)
        if (busque !== undefined) {
            checkInputGlobal('color', 'El color ya ha sido selecionado');
            return false;
        }
    }
    
    $('#addProduct').attr('disabled', true);

    let product = {
        id:contador,
        color: idColor,
        cantidad: cantidad,
        nombreColor:nombreColor
    }

    arrayProducts.push(product);

    let str = ``;

    str += `<div class="form-row" id="product${contador}" idProducto="${contador}" Color="${idColor}">
                        <div class="form-group col-md-5">
                            <label for="exampleInputEmail1">Color:</label>
                            <input type="text" class="form-control" value="${nombreColor}" minlength="2" max="40" placeholder="Color del producto" disabled>
                        </div>
                        <div class="form-group col-md-5">
                            <label for="exampleInputEmail1">Cantidad:</label>
                            <input type="number" class="form-control" value="${cantidad}" minlength="0" max="100000000" placeholder="Stock" disabled>
                        </div>
                      <div class="form-group col-md-2">
                            <label for="exampleInputEmail1">Opciones:</label>
                            <button type="button" class="deleteProduct btn btn-primary d-flex align-items-end pt-2"><i class="fas fa-minus-circle fa-1x"></i></button>
                        </div>
                    </div>`;
    document.getElementById('output').innerHTML += str
    document.getElementById('color').value = ''
    document.getElementById('cantidad').value = ''
    contador++;
    $('#addProduct').attr('disabled', false);
    
})

$(document).on('click', '.deleteProduct', function(e){
    $(this).attr('disabled', true);
    e.preventDefault()
    let parent = $(this)[0].parentElement.parentElement
    let idProducto = $(parent).attr('idProducto')
    document.getElementById('product'+idProducto).remove()
    let color = $(parent).attr('Color')
    arrayProducts = arrayProducts.filter(item => item.color !== color);
    $(this).attr('disabled', false);
})

