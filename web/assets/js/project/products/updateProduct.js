/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

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
//var imagesG

//function generateImages(data) {
//
//     if ($('.input-images-1 .has-files').remove().length !== 0) {
//          $('.input-images-1 .has-files').remove()
//    }
//    
////    let arregloI = []
////    
////    for (var item of data){
////        var i = 0
////        let obj = {
////            id : i,
////            src : item.url
////        }
////        i++
////        arregloI.push(obj)
////    }
//
//
//$('.input-images-1').imageUploader({
//    imagesInputName: 'images',
//    preloadedInputName: 'images'
//});
//
//imagesG = $('.input-images-1').clone();
////$('.input-images-1').addClass('has')
//}



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
        messageInfo('seleccione las imágenes')
        return false
    }
    
    if (!checkextension()) {
        messageInfo('suba imágenes válidas png o jpg')
        generateOtherDiv()
        return false
    }

    if (!checkSizeItems()) {
        messageInfo('Seleccione solo 5 imágenes')
        generateOtherDiv()
        return false
    }

    if (!checkSize()) {
        messageInfo('Las imágenes estan muy grandes')
        generateOtherDiv()
        return false
    }

    if (!checkInputs()) {
        messageInfo('complete el formulario')
        return false
    }  
    
    
    var form = $('#formUpdate')[0]
    var data = new FormData(form)

    
    $('#carga').addClass('is-active');
    
      $.ajax({
        type: "POST",
        enctype: 'multipart/form-data',
        url: "UpdateProduct",
        data: data,
        processData: false,
        contentType: false,
        cache: false,
        success: function (data) {

        console.log(data)
            
    $('#carga').removeClass('is-active')
            if (data) {

                messageOk('Generado con éxito')

            } else {

                messageInfo('Error')

            }


        },
        error: function (e) {

        console.log(e)
            messageError('=(' + e)
             $('#carga').removeClass('is-active')

        }
    });



    
    
    
    

})

$(document).ready(function () {

    $('.input-images-1').imageUploader({
        maxSize: 2 * 1024 * 1024,
        maxFiles: 5
    });
    

imagesG = $('.input-images-1').clone();

});

$("#modal-top").on("show.bs.modal", function(){
   
    
})

$("#modal-top").on("hide.bs.modal", function () {

 if ($('.input-images-1 .has-files').remove().length !== 0) {
        generateOtherDiv()
    }
});

document.getElementById('resets').addEventListener('click', function(e){
    e.preventDefault()
    if ($('.input-images-1 .has-files').remove().length !== 0) {
        generateOtherDiv()
    }
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
