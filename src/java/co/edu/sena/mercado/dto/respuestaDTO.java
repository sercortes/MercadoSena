/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.sena.mercado.dto;

/**
 *
 * @author DELL
 */
public class respuestaDTO {
   int idRespuesta;
   String respuesta;
   int idEmpresa;
   int idPregunta;

    public respuestaDTO() {
    }

    public respuestaDTO(int idRespuesta, String respuesta, int idEmpresa, int idPregunta) {
        this.idRespuesta = idRespuesta;
        this.respuesta = respuesta;
        this.idEmpresa = idEmpresa;
        this.idPregunta = idPregunta;
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

    @Override
    public String toString() {
        return "respuestaDTO{" + "idRespuesta=" + idRespuesta + ", respuesta=" + respuesta + ", idEmpresa=" + idEmpresa + ", idPregunta=" + idPregunta + '}';
    }
   
   
    
}
