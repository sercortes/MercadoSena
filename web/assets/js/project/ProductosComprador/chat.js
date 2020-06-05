var interacion 

$(document).on('click', '.botonChat', function (e){
    e.preventDefault()
    
    let parent = $(this)[0].parentElement.parentElement
    let idPro = $(parent).attr('idEmpresa')
    console.log(idPro)
    getEmpresa(idPro)
    
    $('#preguntarModal').modal('show')
    interacion = 0
})

function getEmpresa(idpro){
    
     $.ajax({
        type: "POST",
        url: './getInfoCompanyByProduct',
        async: true,
        data:{
            idProducto:idpro
        },
        datatype: 'json'
    }).done(function (data) {

        console.log(data)
        
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
         var interacion = 0;
        var getMessageText, message_side, sendMessage;
        message_side = 'right';
        getMessageText = function () {
            var $message_input;
            $message_input = $('.message_input');
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
            return $messages.animate({ scrollTop: $messages.prop('scrollHeight') }, 300);
        };
        $('.send_message').click(function (e) {
            
                            if (interacion < 1) {
                                interacion++
                                generate()
                            }
        });
        $('.message_input').keyup(function (e) {
            if (e.which === 13) {
                return sendMessage(getMessageText());
            }
        });
        sendMessage('Hola Sergio :D');
        function generate() {
            sendMessage(getMessageText());
            enviarMensaje();
            setTimeout(()=> sendMessage('El Mensaje ha sido enviado :D'), 1000)
        }
//        return setTimeout(function () {
//            return sendMessage('I\'m fine, thank you!');
//        }, 2000);
    });
}.call(this));

function enviarMensaje(){
    console.log('enviar al servidor')
}