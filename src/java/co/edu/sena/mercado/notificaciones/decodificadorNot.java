/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.sena.mercado.notificaciones;

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
 * @author DELL
 */
public class decodificadorNot implements Decoder.TextStream<notificacion> {

    @Override
    public notificacion decode(Reader reader) throws DecodeException, IOException {

        notificacion notificacion = new notificacion();
        try (JsonReader jsonReader = Json.createReader(reader)) {
            JsonObject json = jsonReader.readObject();
            notificacion.setIdUsuarioPregunta(json.getInt("idUsuarioPregunta"));
            notificacion.setIdUsuario(json.getInt("idUsuario"));
            notificacion.setIdProducto(json.getInt("idProducto"));
            notificacion.setIdEmpresa(json.getInt("idEmpresa"));
            notificacion.setTipoConsulta(json.getString("tipoConsulta"));

        }
        return notificacion;

    }

    @Override
    public void init(EndpointConfig config) {
    }

    @Override
    public void destroy() {
    }

}
