var numero = 1
    var url = 'ws://'+window.location.host+'/Store/chat',
        ws = new WebSocket(url)
        
        ws.onopen = onOpen
        ws.onclose = onClose
        ws.onmessage = onMessage
        
        function onOpen(){
            console.log('Conectado')
        }
        
        function onClose(){
            console.log('desconectado')
        }
        
        function enviar(){
            var msg = {
                numero:numero++
            }
            ws.send(JSON.stringify(msg))
        }
        
        function onMessage(e){
            getNotifys()
        }
    

