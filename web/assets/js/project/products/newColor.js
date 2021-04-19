$(document).on('click', '#newColor', function (e) {

    getColors()
    $('#modalEdit3').modal('show')
    document.getElementById('cantidadP').value = 0

})


$(document).on('click', '#addColor', function (e) {

    console.log(idPr)
    let color = document.getElementById('color').value
    let cantida = document.getElementById('cantidadP').value

    if (color == '') {
        messageInfo('selecione un color')
        return false
    }
    if (cantida == '') {
        messageInfo('Escriba una cantidad')
        return false
    }
    if (cantida <= 0) {
        messageInfo('Escriba una cantidad válida')
        return false
    }

    $.ajax({
        type: "POST",
        url: './newColor',
        data: {
            idPro: idPr,
            idColor: color,
            cantidad: cantida
        },
        datatype: 'json'
    }).done(function (data) {

        if (data !== 0) {
            messageOk('Agregado')
            listElements(idPr)
            $('#modalEdit3').modal('hide')
        } else {
            messageInfo('Color ya selecionado')
        }

    })


})


function getColors() {

    let cat = document.getElementById('color')

    let text = ``

    $.ajax({
        type: "POST",
        url: './getColors',
        datatype: 'json'
    }).done(function (data) {

        for (var item of data) {
            text += `<option value="${item.idColor}">${item.nombreColor}</option>`
        }

        cat.innerHTML += text;
    })

}
