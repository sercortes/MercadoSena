/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.sena.mercado.dto;

import java.sql.Timestamp;

/**
 *
 * @author DELL
 */
public class respuestaDTO {
   int idRespuesta;
   String respuesta;
   int idEmpresa;
   int idPregunta;
   String pregunta;
   
   private Timestamp fecha;
   private String visto;

    public respuestaDTO() {
    }

    public respuestaDTO(int idRespuesta, String respuesta, int idEmpresa, int idPregunta, String pregunta) {
        this.idRespuesta = idRespuesta;
        this.respuesta = respuesta;
        this.idEmpresa = idEmpresa;
        this.idPregunta = idPregunta;
        this.pregunta = pregunta;
    }

    public String getPregunta() {
        return pregunta;
    }

    public void setPregunta(String pregunta) {
        this.pregunta = pregunta;
    }

 

    public int getIdRespuesta() {
        return idRespuesta;
    }

    public void setIdRespuesta(int idRespuesta) {
        this.idRespuesta = idRespuesta;
    }

    public String getRespuesta() {
        return respuesta;
    }

    public void setRespuesta(String respuesta) {
        this.respuesta = respuesta;
    }

    public int getIdEmpresa() {
        return idEmpresa;
    }

    public void setIdEmpresa(int idEmpresa) {
        this.idEmpresa = idEmpresa;
    }

    public int getIdPregunta() {
        return idPregunta;
    }

    public void setIdPregunta(int idPregunta) {
        this.idPregunta = idPregunta;
    }

    public Timestamp getFecha() {
        return fecha;
    }

    public void setFecha(Timestamp fecha) {
        this.fecha = fecha;
    }

    public String getVisto() {
        return visto;
    }

    public void setVisto(String visto) {
        this.visto = visto;
    }

    @Override
    public String toString() {
        return "respuestaDTO{" + "idRespuesta=" + idRespuesta + ", respuesta=" + respuesta + ", idEmpresa=" + idEmpresa + ", idPregunta=" + idPregunta + ", pregunta=" + pregunta + ", fecha=" + fecha + ", visto=" + visto + '}';
    }

    
    
}
