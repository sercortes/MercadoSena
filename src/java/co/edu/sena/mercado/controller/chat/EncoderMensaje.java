/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.sena.mercado.controller.chat;

import co.edu.sena.mercado.dto.Mensaje;
import java.io.IOException;
import java.io.Writer;
import java.math.BigDecimal;
import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonWriter;
import javax.websocket.EncodeException;
import javax.websocket.Encoder;
import javax.websocket.EndpointConfig;

/**
 *
 * @author equipo
 */
public class EncoderMensaje implements Encoder.TextStream<Mensaje>{

    @Override
    public void encode(Mensaje t, Writer writer) throws EncodeException, IOException {
        JsonObject json = Json.createObjectBuilder()
//                .add("nombre", t.getNombre())
//                .add("mensaje", t.getMensaje())
                .add("numero", t.getNumero())
                .build();
        try (JsonWriter jsonWriter = Json.createWriter(writer)){
            
            jsonWriter.write(json);
            
        }
        
    }

    @Override
    public void init(EndpointConfig ec) {
    }

    @Override
    public void destroy() {
    }
    
}
