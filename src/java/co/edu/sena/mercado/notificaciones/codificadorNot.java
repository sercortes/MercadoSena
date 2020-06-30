/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.sena.mercado.notificaciones;

import java.io.IOException;
import java.io.Writer;
import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonWriter;
import javax.websocket.EncodeException;
import javax.websocket.Encoder;
import javax.websocket.EndpointConfig;

/**
 *
 * @author DELL
 */
public class codificadorNot implements Encoder.TextStream<notificacion> {

    @Override
    public void encode(notificacion object, Writer writer) throws EncodeException, IOException {

        JsonObject json = Json.createObjectBuilder().add("idUsuarioPregunta", object.getIdUsuarioPregunta()).add("idUsuario", object.getIdUsuario()).
                add("idProducto", object.getIdProducto()).add("idEmpresa", object.getIdEmpresa()).add("tipoConsulta",object.getTipoConsulta()).build();
        try (JsonWriter jsonWriter = Json.createWriter(writer)) {
            jsonWriter.writeObject(json);
        }

    }

    @Override
    public void init(EndpointConfig config) {
    }

    @Override
    public void destroy() {
    }

}
