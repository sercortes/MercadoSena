package co.edu.sena.mercado.controller.chat;

import co.edu.sena.mercado.dto.Mensaje;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.websocket.EncodeException;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

@ServerEndpoint(value = "/chat", encoders = {EncoderMensaje.class}, decoders = {DecoderMensaje.class})

public class MiChat {

    private static final List<Session> conectados = new ArrayList<>();
    
    @OnOpen
    public void inicio(Session session){
        conectados.add(session);
    }
    
    @OnClose
    public void salir(Session session){
        conectados.remove(session);
    }
    
    @OnMessage
    public void mensaje(Mensaje mensaje) throws IOException, EncodeException{
        for(Session session: conectados){
            session.getBasicRemote().sendObject(mensaje);
        }
    }
    
    @OnError
    public void onError(Session session, Throwable thr) {
        System.out.println("ERROR SOCKET CERRADO");
    }
    
}
