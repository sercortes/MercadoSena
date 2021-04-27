
$(function () {

    getBanner()

})

$(document).on('click', '#addCategoria', function (e) {

    e.preventDefault()
    $(".remove").remove();
    let categoria = document.getElementById('categoria').value
    if (categoria == '' || categoria.length > 400 || categoria.length <= 2) {
        checkInputGlobal('categoria', 'Escriba una categoría válida')
        return false
    }

    $('#cargas').addClass('is-active');
    $("#addCategoria").attr("disabled", true);
    generar('./createCategory', categoria, 'categoría')


})

$(document).on('click', '#addMarca', function (e) {

    e.preventDefault()
    $(".remove").remove();
    let marca = document.getElementById('marca').value
    if (marca == '' || marca.length > 400 || marca.length <= 2) {
        checkInputGlobal('marca', 'Escriba una marca válida')
        return false
    }
    $('#cargas').addClass('is-active');
    $("#addMarca").attr("disabled", true);
    generar('./createBrand', marca, 'marca')

})

function generar(url, palabra, anuncio) {

    $.ajax({
        url: url,
        type: 'POST',
        dataType: 'json',
        data: {nombre: palabra},
        async: true,
        success: function (data) {
            if (data) {
                messageOk('La ' + anuncio + ' ha sido creada')
                cleanForms()
            } else {
                messageInfo('Error ya ha sido creado un'+anuncio+'con ese nombre')
                cleanForms()
            }

        }
    })

}

function cleanForms() {
    document.getElementById('categoria').value = ''
    document.getElementById('marca').value = ''
    $('#cargas').removeClass('is-active');
    $("#addMarca").attr("disabled", false);
    $("#addCategoria").attr("disabled", false);
}

function getBanner() {

    $.ajax({
        type: "POST",
        url: './banner',
        async: true,
        datatype: 'json'
    }).done(function (data) {

        let str = ``
        for(var item of data){   
            str +=  `<input type="text" class="form-control bannerInput" placeholder="" 
                      idBaner="${item.idBanner}" value="${item.frase}"><hr>`   
        }
        document.getElementById('banners').innerHTML = str

    })

}

$(document).on('click', '#editBanner', function (e) {

    e.preventDefault()
    let arraf = []
    let ban = document.getElementsByClassName('bannerInput');
   
    for(var item of ban){
        let ob = {
            idBanner:$(item).attr('idBaner'),
            frase:$(item).val()
        }
        arraf.push(ob)
    }
    
    for(var item of arraf){
        if (item.frase == '' || item.frase == undefined) {
            messageInfo('complete el campo')
            return false
        }
    }
    
    sendBanner(arraf)

})

function sendBanner(data){
    
      $.ajax({
        type: 'POST',
        url: './editBanner',
        datatype: 'json',
        async: true,
        data: {
            arrayP: JSON.stringify(data)
        }
    }).done(function (data) {
        
        if (data == 1) {
            messageOk('editado')
            getBanner()
        }else{
            messageError('Error')
        }

    })
    
}
