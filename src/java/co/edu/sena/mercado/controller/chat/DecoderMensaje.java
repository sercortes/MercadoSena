/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.sena.mercado.controller.chat;

import co.edu.sena.mercado.dto.Mensaje;
import java.io.IOException;
import java.io.Reader;
import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;
import javax.websocket.DecodeException;
import javax.websocket.Decoder;
import javax.websocket.EndpointConfig;

/**
 *
 * @author equipo
 */
public class DecoderMensaje implements Decoder.TextStream<Mensaje>{

    @Override
    public Mensaje decode(Reader reader) throws DecodeException, IOException {
        Mensaje mensaje = new Mensaje();
        try(JsonReader jsonReader = Json.createReader(reader)){
            JsonObject json = jsonReader.readObject();
//            mensaje.setNombre(json.getString("nombre"));
//            mensaje.setMensaje(json.getString("mensaje"));
            mensaje.setNumero(json.getInt("numero"));
        }
        return mensaje;
    }

    @Override
    public void init(EndpointConfig ec) {
    }

    @Override
    public void destroy() {
    }
    
}
