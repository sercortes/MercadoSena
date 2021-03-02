
$("#btnpagar").click(function () {

    if (!checkSession()) {
        modalPreguntaRegistro();
        return false
    }

    $('#modalCar').modal('hide')
    $('#modalMetodoPago').modal('show')
    listarMetodos()

});

function listarMetodos() {
    $.ajax({
        url: "./getMetodos",
        type: 'POST',
        dataType: 'json',
        async: true
        , error: function () {

        }, success: function (data) {
            console.log(data)
            let srt = ``
            srt = '<option value="">Método de pago...</option>'
            for (var item of data) {
                srt += `<option value="${item.idMetodo}">${item.nombre}</option>`
            }
            document.getElementById('metodoPago').innerHTML = srt
        }
    })
}

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
                messageInfo('No existe el usuario')
                return false;
            }

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
    })

}

function buyProducts(metodo) {

    let arraf = JSON.parse(localStorage.getItem('objects'));

    $.ajax({
        type: 'POST',
        url: './generateSale',
        datatype: 'json',
        data: {
            metodo: metodo, arrayP: JSON.stringify(arraf)
        }, error: function (datas) {
            messageInfo('Error en los productos')
        },
        success: function (datas) {

            if (datas !== 0) {

                cleanCar()

                if (metodo === 1 || metodo === 2) {
                    vendedor(datas)
                } else if (metodo === 3) {
                    redirect(datas)
                }

            } else {
                messageInfo('Error')
            }

        }
    })

}

$(document).on('click', '#efectivo', function (e) {

    e.preventDefault()
    this.disabled = true
    buyProducts(1)


})

$(document).on('click', '#transferec', function (e) {

    e.preventDefault()
    this.disabled = true
    buyProducts(2)

})

$(document).on('click', '#targetaC', function (e) {

    e.preventDefault()

    Swal.fire({
        text: "Continuar con el método de pago seleccionado",
        icon: 'info',
        showCancelButton: true,
        confirmButtonColor: '#3085d6',
        cancelButtonColor: '#CECECE',
        confirmButtonText: 'Continuar',
        cancelButtonText: 'Cambiar método de pago',
        reverseButtons: true
    }).then((result) => {
        if (result.value) {
            this.disabled = true
            buyProducts(3)
        }
    })


})

$(document).on('click', '#pagoPSE', function (e) {

    e.preventDefault()
    this.disabled = true
    messageInfo('pago pse')

})

function redirect(data) {

    location.href = "process_payment?valor=" + data;

}

function cleanCar() {
    var arrayBuy = []
    localStorage.setItem('objects', JSON.stringify(arrayBuy))
}

function vendedor() {

    Swal.fire({
        title: 'Perfecto',
        text: "Su pago ha sido registrado",
        icon: 'success',
        showCancelButton: false,
        confirmButtonColor: '#3085d6',
    }).then(function () {
        $('#modalMetodoPago').modal('hide')
    })

}