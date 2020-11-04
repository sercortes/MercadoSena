var imagesG
var arregloIma
var arregloRes
var limpiarImagenes = 0

function getCategorias(value, name) {

    let cat = document.getElementById('categoryE')

    let text = `<option value="${value}" selected>${name}</option>`

    $.ajax({
        type: "POST",
        url: './getCategorys',
        datatype: 'json'
    }).done(function (data) {

        for (var item of data) {
            if (item.idcategoria !== value) {
                text += `<option value="${item.idcategoria}">${item.nombreCategoria}</option>`
            }
        }

        cat.innerHTML = text;
    })

}

function cleanInput() {
    generateDivClean()
}

function generateDivClean() {
    $('.input-images-1 .has-files').remove()

    $('.input-images-1').imageUploader({
        preloaded: arregloRes,
        imagesInputName: 'images',
        preloadedInputName: 'pre'
    });

}

function generateImages(data) {

    $('.input-images-1 .has-files').remove()

    let arregloI = []

    for (var item of data) {
        var i = 0
        let obj = {
            id: i,
            src: item.url
        }
        i++
        arregloI.push(obj)
    }

    arregloIma = arregloI
    arregloRes = arregloI

    $('.input-images-1').imageUploader({
        preloaded: arregloI,
        imagesInputName: 'images',
        preloadedInputName: 'pre'
    });
    imagesG = $('.input-images-1').clone();

}

$("#modal-top").on("hide.bs.modal", function () {
    $('.input-images-1').replaceWith(imagesG)

    cleans()

});

function cleans() {
    var form = $("#formUpdate")
    form.removeClass('was-validated');
}

document.getElementById('formUpdate').addEventListener('input', e => {

    e.preventDefault()
    var form = $("#formUpdate")
    if (form[0].checkValidity() === false) {
        event.preventDefault()
        event.stopPropagation()
    }
    form.addClass('was-validated');

})

document.getElementById('formUpdate').addEventListener('submit', e => {

    e.preventDefault()
    var form = $("#formUpdate")
    if (form[0].checkValidity() === false) {
        event.preventDefault()
        event.stopPropagation()
    }
    form.addClass('was-validated');

    if (!checkOne()) {
        messageInfo('Seleccione alguna imagen')
        return false;
    }

    if (!checkSizeItems()) {
        messageInfo('Seleccione solo 5 imágenes')
        cleanInput()
        return false
    }

    if (!checkextension()) {
        messageInfo('suba imágenes válidas png o jpg')
        cleanInput()
        return false
    }

    if (!checkSize()) {
        messageInfo('Las imágenes estan muy grandes')
        cleanInput()
        return false
    }

    if (!checkInputs()) {
        messageInfo('complete el formulario')
        return false
    }

    let files = ''

    if (checkIsNewFiles()) {
        files = 1;
    } else {
        files = 0
    }

    var form = $('#formUpdate')[0]
    var data = new FormData(form)

    $('#carga').addClass('is-active');

    $.ajax({
        type: "POST",
        enctype: 'multipart/form-data',
        url: "UpdateProduct?files=" + files+"&clean="+limpiarImagenes,
        data: data,
        processData: false,
        contentType: false,
        cache: false,
        success: function (data) {

            $('#carga').removeClass('is-active')
            if (data) {

                mensajeactulizacion('El producto se actulizo de formar correcta');
                var form = $("#formUpdate")
                form.removeClass('was-validated');
                $pagination.twbsPagination('destroy');
                listarProductoByVendedor()
                limpiarImagenes = 0

            } else {

                messageInfo('Error')

            }

        },
        error: function (e) {

            console.log(e)
            messageError('=(')
            $('#carga').removeClass('is-active')

        }
    });

})

function checkIsNewFiles() {

    var file = document.getElementsByName("images[]");
    let array = file[0].files

    if (array.length > 0) {
        return true
    }
    return false

}

document.getElementById('resets').addEventListener('click', function (e) {
    e.preventDefault()
    limpiarImagenes = 1
    if ($('.input-images-1 .has-files').remove().length !== 0) {
        generateOtherDiv()
    }
    arregloIma = []
    return false
})

function generateOtherDiv() {

    $('.input-images-1 .has-files').remove()

    $('.input-images-1').imageUploader({
        maxSize: 2 * 1024 * 1024,
        maxFiles: 5
    });
}

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

    if (tam >= 8000000) {
        return false
    }
    return true

}

function checkSizeItems() {
    var file = document.getElementsByName("images[]");
    let array = file[0].files

    if (array.length + arregloIma.length >= 6) {
        return false
    }
    return true
}


function checkOne() {
    var file = document.getElementsByName("images[]");
    let array = file[0].files
    if (array.length + arregloIma.length <= 0) {
        return false
    }
    return true
}

function checkInputs() {

    let name = document.getElementById('nameE').value
    let desc = document.getElementById('descripE').value
    let price = document.getElementById('priceE').value
    let cantidad = document.getElementById('cantidadE').value
    let marca = document.getElementById('marcaE').value
    let category = document.getElementById('categoryE').value

    if (name == '' || desc == '' || name.length <= 2 ||
            desc.length <= 19 || price == '' || cantidad == '' ||
            marca == '' || category == '') {
        return false
    }

    return true
}

function mensajeactulizacion(mensaje) {
    Swal.fire({
        position: 'center',
        icon: 'success',
        title: 'Datos Actulizados',
        html: '<h4 style="color:#449d48;">' + mensaje + '</h4>',
        showCancelButton: false,
        showConfirmButton: false,
        allowOutsideClick: false,
        allowEscapeKey: false,
        allowEnterKey: false,
        padding: '2rem',
        width: '25%',
        timer: 1600

    });
}