var idUser = 0;
$(document).on('click', '#searchUser', function (e) {

    e.preventDefault()
    let documentoUser = document.getElementById('documentvalue').value
    if (documentoUser.length <= 4 || documentoUser === null || documentoUser === '') {
        messageInfo('Escriba el documento, por favor')
        return false
    }

    buscarCliente(documentoUser)

})

function buscarCliente(id) {

    $.ajax({
        url: "./getPersona",
        type: 'POST',
        dataType: 'json',
        data: {idUser: id},
        async: true
        , error: function () {

        }, success: function (data) {
            console.log(data)
            if (data.idPer === 0) {
                noExists(id)
                return false;
            }
            exist(data)

        }
    })

}

function selectPersona() {

    let metodoPago = document.getElementById('metodoPago').value
    let arraf = JSON.parse(localStorage.getItem('objects'));

    if (arraf.length <= 0) {
        messageInfo('No hay elementos en el carrito')
        return false
    }

    if (idUser === 0) {
        messageInfo('Selecione un usuario del sistema')
        return false
    }
    if (metodoPago == '') {
        $(".remove").remove();
        input('metodoPago', 'Seleccione el método de pago')
        return false
    }

    $('#cargas').addClass('is-active');
    $("#registrarVenta").attr("disabled", true);

    $.ajax({
        type: 'POST',
        url: './generateSaleByVendedor',
        datatype: 'json',
        data: {
            crear: 0, arrayP: JSON.stringify(arraf),
            metodoPago: metodoPago,
            idUserCompra: idUser
        }, error: function (datas) {
            messageInfo('Error en los productos')
        },
        success: function (datas) {

            console.log(datas)
            $('#cargas').removeClass('is-active');
            $("#registrarVenta").attr("disabled", false);

            if (datas === 00 || datas === 0) {
                messageInfo('Error')
            } else if (datas == 01) {
                input('documentoUsu', 'Número de documento ya utilizado, busque al cliente')
            } else {
                cleanModalResgistrarVenta(datas)
            }

        }
    })

}


function noExists(id) {

    $('#cabecera').hide()
    idUser = 0
    document.getElementById('ouputSearch').innerHTML =
            `<div class="alert alert-warning alert-dismissible fade show" role="alert">
                        <strong>!No existe el usuario con documento!</strong> ${id}
                        <button type="button" class="close" data-dismiss="alert" aria-label="Close">
                          <span aria-hidden="true">&times;</span>
                        </button>
                      </div>`

}

function exist(data) {

    idUser = data.idUsuario
    $('#cabecera').show()
    let str = ``
    str += `<tr class="" idPersona="${data.idUsuario}">
                    <th scope="row">${data.idPer}</th>
                    <td>${data.nombrePer}</td>
                    <td>${data.apellidoPer}</td>
                    <td><div class="form-check">
                        <input class="form-check-input" type="radio" name="exampleRadios" id="exampleRadios1" value="option1" checked>
                        <label class="form-check-label" for="exampleRadios1">
                          Selecionar
                        </label>
                      </div></td>
                  </tr>`

    document.getElementById('ouputSearch').innerHTML = str

}

function listarMetodos() {
    $.ajax({
        url: "./getMetodos",
        type: 'POST',
        dataType: 'json',
        async: true
        , error: function () {

        }, success: function (data) {
            let srt = ``
            srt = '<option value="">Método de pago...</option>'
            for (var item of data) {
                srt += `<option value="${item.idMetodo}">${item.nombre}</option>`
            }
            document.getElementById('metodoPago').innerHTML = srt
        }
    })
}

function listarTipoDocumento() {
    $.ajax({
        url: "./getTiposDocumento",
        type: 'POST',
        dataType: 'json',
        async: true
        , error: function (e) {
        }, success: function (data) {
            let srt = ``
            srt = '<option value="">Tipo de documento...</option>'
            for (var item of data) {
                srt += `<option value="${item.idTipoDoc}">${item.tipoDoc}</option>`
            }
            document.getElementById('tipoDocumento').innerHTML = srt
        }
    })
}

$(document).on('click', '#print', function (e){
    e.preventDefault()
    printElement(document.getElementById("printable"));
    
})

function printElement(elem) {
    var domClone = elem.cloneNode(true);
    
    var $printSection = document.getElementById("printSection");
    
    if (!$printSection) {
        var $printSection = document.createElement("div");
        $printSection.id = "printSection";
        document.body.appendChild($printSection);
    }
    
    $printSection.innerHTML = "";
    $printSection.appendChild(domClone);
    window.print();
}