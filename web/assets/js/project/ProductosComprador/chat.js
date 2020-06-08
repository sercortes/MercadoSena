var interacion;
var idProducto;
$(document).on('click', '.botonChat', function (e) {
    e.preventDefault()
    if ($('#nombreUsuarioInicio').val()!=='no'){
        let parent = $(this)[0].parentElement.parentElement;
        idpro = $(parent).attr('idEmpresa');
        idProducto = $(parent).attr('idProducto');
        //getEmpresa(idPro);
        $('#preguntarModal').modal('show');
        interacion = 0;
    }else{
        modalPreguntaRegistro();
    }
    console.log($('#nombreUsuarioInicio').val());
})

function getEmpresa(idpro) {

    $.ajax({
        type: "POST",
        url: './getInfoCompanyByProduct',
        async: true,
        data: {
            idProducto: idpro
        },
        datatype: 'json'
    }).done(function (data) {

        console.log(data);

    })

}

(function () {
    var Message;
    Message = function (arg) {
        this.text = arg.text, this.message_side = arg.message_side;
        this.draw = function (_this) {
            return function () {
                var $message;
                $message = $($('.message_template').clone().html());
                $message.addClass(_this.message_side).find('.text').html(_this.text);
                $('.messages').append($message);
                return setTimeout(function () {
                    return $message.addClass('appeared');
                }, 0);
            };
        }(this);
        return this;
    };
    $(function () {
       
        var getMessageText, message_side, sendMessage;
        message_side = 'right';
        getMessageText = function () {
            var $message_input;
            $message_input = $('.message_input');
            enviarMensaje($message_input.val());
            
            

            return $message_input.val();
        };
        sendMessage = function (text) {
            var $messages, message;
            if (text.trim() === '') {
                return;
            }
            $('.message_input').val('');
            $messages = $('.messages');
            message_side = message_side === 'left' ? 'right' : 'left';
            message = new Message({
                text: text,
                message_side: message_side
            });
            message.draw();
            return $messages.animate({scrollTop: $messages.prop('scrollHeight')}, 300);
        };
        $('.send_message').click(function (e) {

            if (interacion < 1) {
                interacion++;
                generate();
            }
        });
        $('.message_input').keyup(function (e) {
            if (e.which === 13) {
                return sendMessage(getMessageText());
            }
        });
        sendMessage('Hola ' + $('#nombreUsuarioInicio').val());
        function generate() {
            sendMessage(getMessageText());
        }

        function enviarMensaje(mensaje) {


            if (mensaje !== null && mensaje !=='') {
                $.ajax({
                    url: './registro',
                    data: {
                        accion: 'registroPregunta',
                        mensaje: mensaje,
                        idProducto: idProducto
                    },
                    type: 'POST',
                    success: function (data) {
                        console.log(data);
                        console.log(typeof data);
                        if (data === 'true') {
                            setTimeout(() => sendMessage('Hemos enviado su mensaje al vendedor, quién pronto se pondrá en contacto.'), 1000);
                        } else {
                            interacion = interacion - 1;
                            messageError('Error al enviar el mensaje');
                        }
                    },
                    error: function (jqXHR, textStatus, errorThrown) {
                        interacion = interacion - 1;
                        messageInfo('Ha ocurrido un error con el servidor,favor intentar más tarde.');


                    }})
            }


        }



//        return setTimeout(function () {
//            return sendMessage('I\'m fine, thank you!');
//        }, 2000);
    });
}.call(this));

function modalRegistroSi() {
    modalPreguntaRegistro();
    consultarDatosFormulario();
}
function modalPreguntaRegistro() {
    $('#modalPreguntaRegistro').toggle();
    $('#bloqueo').toggle();
}


function consultaPreguntas(e) {
    e.preventDefault();
    
}

function limpiarPlantilla(){
 var lista=  $('#listaPreguntas').children();
 if(lista.length===3){
   $('#listaPreguntas').children().last().remove();
   $('#listaPreguntas').children().last().remove();
 }
 interacion=0;
}