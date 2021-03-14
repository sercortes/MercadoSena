var imagesG
var arregloIma
var arregloRes
var limpiarImagenes = 0

$(function(){

    

})

function getMarcassE(value, name) {
        
     let text = ``
     text = `<option value="${value}" selected>${name}</option>`

    $.ajax({
        url: "./filtro",
        type: 'POST',
        async: true,
        data: {
            accion: 'listarMarcas'
        }, dataType: 'json',
        error: function (data) {

        }, success: function (data, textStatus, jqXHR) {
            for (var item of data) {
                 if (item.idMarca !== value) {
                 text += `<option value="${item.idMarca}">${item.nombreMarca}</option>`
                 }
            }
            document.getElementById('marcaE').innerHTML = text
        }
    })

}

function getCategorias(value, name) {
    
    let text = ``
    text = `<option value="${value}" selected>${name}</option>`

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

        document.getElementById('categoryE').innerHTML = text;
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

                messageOk('El producto se actualizo de forma correcta');
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

    let number = document.getElementById('idProductoE').value
    let name = document.getElementById('nameE').value
    let desc = document.getElementById('descripE').value
    let price = document.getElementById('priceE').value
    let marca = document.getElementById('marcaE').value
    let category = document.getElementById('categoryE').value
    let precioV = document.getElementById('precioVendedor').value 
    let envio = document.getElementById('enviosE').value 
    let medidas = document.getElementById('medidasE').value  
    let ventajas = document.getElementById('ventajasE').value 
    let referencia = document.getElementById('referencia').value 
    let garantia = document.getElementById('garantia').value 

    if (number == '' || name == '' || desc == '' || name.length <= 2 ||
            desc.length <= 19 || price == '' ||
            marca == '' || category == '' || precioV == '' || envio == '' ||
            medidas == '' || ventajas == '' || referencia == '' || garantia == '') {
        return false
    }

    return true
}
