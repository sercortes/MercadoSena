$(document).ready(function () {

    $('.input-images-1').imageUploader({
        maxSize: 2 * 1024 * 1024,
        maxFiles: 5
    });
    
    getCategorias()

    
});

document.getElementById('formProduct').addEventListener('input', e => {

    e.preventDefault()
    var form = $("#formProduct")
    if (form[0].checkValidity() === false) {
        event.preventDefault()
        event.stopPropagation()
    }
    form.addClass('was-validated');

})

$('#formProduct').submit(function (e) {

    e.preventDefault()

    var form = $("#formProduct")
    if (form[0].checkValidity() === false) {
        event.preventDefault()
        event.stopPropagation()
    }
    
    form.addClass('was-validated');

    if (!checkOne()) {
        messageInfo('seleccione las imagenes')
        return false
    }

    if (!checkextension()) {
        messageInfo('suba imagenes validas png o jpg')
        generateOtherDiv()
        return false
    }

    if (!checkSizeItems()) {
        messageInfo('Seleccione solo 5 imagenes')
        generateOtherDiv()
        return false
    }

    if (!checkSize()) {
        messageInfo('Los archivos estan muy grandes')
        generateOtherDiv()
        return false
    }

    if (!checkInputs()) {
        messageInfo('complete el formulario')
        return false
    }

    var form = $('#formProduct')[0]
    var data = new FormData(form)

    $.ajax({
        type: "POST",
        enctype: 'multipart/form-data',
        url: "UploadProduct",
        data: data,
        processData: false,
        contentType: false,
        cache: false,
        success: function (data) {

            console.log(data)
            
            $('#formProduct').trigger('reset')
            var form = $("#formProduct")
            form.removeClass('was-validated');

            generateOtherDiv()

            if (data) {

                messageOk('Subido con éxito')

            } else {

                messageInfo('Error')

            }


        },
        error: function (e) {

            messageError('=(' + e)

        }
    });





})

function getCategorias(){
    
    let cat = document.getElementById('category')

    let text = ``

    $.ajax({
        type: "POST",
        url: './getCategorys',
        datatype: 'json'
    }).done(function (data) {

        for (var item of data) {
            text += `<option value="${item.idcategoria}">${item.nombreCategoria}</option>`
        }

        cat.innerHTML += text;
    })
    
}


function generateOtherDiv() {

    $('.input-images-1 .has-files').remove()

    $('.input-images-1').imageUploader({
        maxSize: 2 * 1024 * 1024,
        maxFiles: 5
    });
}

$('#reset').on('click', function () {

    if ($('.input-images-1 .has-files').remove().length !== 0) {
        generateOtherDiv()
    }
})

function checkextension() {
    var file = document.getElementsByName("images[]");
    let array = file[0].files

    for (var item of array) {
        if (item.type !== 'image/png' && item.type !== 'image/jpeg') {
            return false
        }
    }

    return true
}

function checkSize() {
    var file = document.getElementsByName("images[]");
    let array = file[0].files
    let tam = 0
    for (var item of array) {
        tam += item.size
    }

    if (tam >= 1000000) {
        return false
    }
    return true

}

function checkSizeItems() {
    var file = document.getElementsByName("images[]");
    let array = file[0].files

    if (array.length >= 6) {
        return false
    }
    return true
}

function checkOne() {
    var file = document.getElementsByName("images[]");
    let array = file[0].files

    if (array.length <= 0) {
        return false
    }
    return true
}

function checkInputs() {
    
    let name = document.getElementById('name').value
    let desc = document.getElementById('descrip').value
    
    if (name == '' || desc == '' || name.length <= 2 || desc.length <= 19 ) {
        return false
    }
    return true
}

