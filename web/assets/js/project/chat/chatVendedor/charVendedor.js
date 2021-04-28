var idPregunta = 0
var idUs = 0
var idPr = 0
var url = 0

var array = []

$(function () {
    
    getPreguntas()
    getNotifys()
    
})
function  getPreguntas() {
    $.ajax({
        url: './getAllPreguntas',
        type: 'POST',
        dataType: 'json',
        success: function (data) {
            
            drawChat(data)

        }
    })
}


function drawChat(data) {

    if (data.length === 0) {
        let str = `<div class="alert alert-warning alert-dismissible fade show" role="alert">
                    <strong>Ups!</strong> No existen mensajes.
                        <button type="button" class="close" data-dismiss="alert" aria-label="Close">
                          <span aria-hidden="true">&times;</span>
                        </button>
                    </div>`
        document.getElementById('personass').innerHTML = str
        return false;
    }

    let str = ``

    for (var item of data) {

        str += ` <a href="#" urlU="${item.urlPersona}" idUsuario="${item.idUsuarioPregunta}" idProducto="${item.idProducto}" class="usuarios list-group-item list-group-item-action text-white rounded-0">
                    <div class="media"><img src="${item.urlPersona}" alt="user" width="50" class="rounded-circle">
                        <div class="media-body ml-4">
                            <div class="d-flex align-items-center justify-content-between mb-1">
                                <h6 class="mb-0">${item.nombreUsuario}</h6><small class="small font-weight-bold">${item.fechaPublicada}</small>
                            </div>
                            <p class="font-italic mb-0 text-small">${item.nombreProducto}</p>
                        </div>
                    </div>
                 </a>`
    }

    document.getElementById('personass').innerHTML = str

}

$(document).on('click', '.usuarios', function (e) {

    e.preventDefault()
    $('.usuarios').removeClass('active')
    $(this).addClass('active')
    let parent = $(this)[0]
    idUs = $(parent).attr('idUsuario')
    idPr = $(parent).attr('idProducto')
    url = $(parent).attr('urlU')
    questionss()

})

function questionss() {

    $.ajax({
        url: './getPreguntasIndivi',
        type: 'POST',
        async: false,
        data: {
            idUsuario: idUs,
            idProducto: idPr
        },
        dataType: 'json',
        success: function (data) {
         
            for(let item of data){
                if (item.vista == '0') {
                   updateViewQuestion(item.idPregunta)
                }
            }
            generateQuestions(url, data)

        }
    })

}

function updateViewQuestion(idP){
    
    $.ajax({
        url: './updateQuestion',
        type: 'POST',
        async: true,
        data: {
            idP:idP
        },
        dataType: 'json',
        success: function (data) {
            
        }
    })

}


function generateQuestions(url, data) {

    let str = ``
    $('#chatPreguntas').html('')

    for (var item of data) {
        str += `<div class="media w-50 mb-3"><img src="${url}" alt="user" width="50" class="rounded-circle">
                    <div class="media-body ml-3">
                        <div class="bg-light rounded py-2 px-3 mb-2">
                            <p class="text-small mb-0 text-muted">${item.pregunta}</p>
                        </div>
                        <p class="small text-muted">${item.fechaPublicada}</p>
                    </div>
                </div>`
        $('#chatPreguntas').append(str)
        str = ``
        answers(item.idPregunta)
        idPregunta = item.idPregunta
    }

    var objDiv = document.getElementById("chatPreguntas");
    objDiv.scrollTop = objDiv.scrollHeight;

}

function answers(idPregunta) {


    $.ajax({
        url: './getRespuestasByQuestion',
        type: 'POST',
        async: false,
        data: {
            idPregunta: idPregunta
        },
        dataType: 'json',
        success: function (data) {

            drawRespuestas(data)

        }
    })

}

function drawRespuestas(data) {

    let str = ``

    for (var item of data) {
        str +=
                `<div class="media w-50 ml-auto mb-3">
                <div class="media-body">
                    <div class="bg-primary rounded py-2 px-3 mb-2">
                        <p class="text-small mb-0 text-white">${item.respuesta}</p>
                    </div>
                    <p class="small text-muted">${item.fecha}</p>
                </div>
            </div>`
    }
    $('#chatPreguntas').append(str)

}

$(document).on('click', '#button-addon2', function (e) {

    e.preventDefault();
    let res = document.getElementById('respuesta').value

    if (res == '' || res == null) {
        messageInfo('Escriba un mensaje')
        return false
    }

    if (res.length <= 5) {
        messageInfo('Escriba un mensaje válido')
        return false
    }

    if (idPregunta === 0) {
        messageInfo('Seleciona un chat')
        return false
    }

    let data = {
        respuesta: res,
        idPregunta: idPregunta
    }

    sendRespuesta(data);


})

function sendRespuesta(data) {

            $.ajax({
                url: './registrarRespuesta',
                type: 'POST',
                data: {
                    respuesta: data.respuesta,
                    idPregunta: data.idPregunta
                },
                dataType: 'json',
                success: function (data) {

                    if (data) {
                        enviar()
                        messageOk('Mensaje enviado')
                        questionss()
                        document.getElementById('respuesta').value = ""
//                        enviarNot('respuesta', 0);
                    } else {
                        messageError('Error al enviar su respuesta.');
                    }

                }
            })

}
