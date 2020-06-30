/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.sena.mercado.servidorSocket;

import co.edu.sena.mercado.notificaciones.codificadorNot;
import co.edu.sena.mercado.notificaciones.decodificadorNot;
import co.edu.sena.mercado.notificaciones.notificacion;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.websocket.EncodeException;
import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

/**
 *
 * @author DELL
 */
@ServerEndpoint(value="/not",encoders = {codificadorNot.class},decoders = {decodificadorNot.class})

public class servidorSocket {
    
     static final List<Session> conectados=new ArrayList<>();
    @OnOpen
    public void inicio(Session sesion){
    conectados.add(sesion);
        //System.out.println(".......... conctados "+conectados.toString());
    }
    
    @OnClose
     public void salir(Session sesion){
    conectados.remove(sesion);
    }
     
     @OnMessage
      public void mensaje(notificacion  not) throws IOException,EncodeException{
          for(Session sesion: conectados){
          sesion.getBasicRemote().sendObject(not);
             // System.out.println("..........."+sesion.getBasicRemote());
          }
    }
    
}
