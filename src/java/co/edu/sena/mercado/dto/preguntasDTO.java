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
public class preguntasDTO {
   int idPregunta;
   String pregunta;
   int estadoPregunta;
   int idUsuarioPregunta;
   int idProducto;
   String respuesta;
   String nombreUsuarioPregunta;
   String apellidoUsuarioPregunta;
   String usuarioResponde;

    public preguntasDTO() {
    }

    public preguntasDTO(int idPregunta, String pregunta, int estadoPregunta, int idUsuarioPregunta, int idProducto, String respuesta, String nombreUsuarioPregunta, String apellidoUsuarioPregunta, String usuarioResponde) {
        this.idPregunta = idPregunta;
        this.pregunta = pregunta;
        this.estadoPregunta = estadoPregunta;
        this.idUsuarioPregunta = idUsuarioPregunta;
        this.idProducto = idProducto;
        this.respuesta = respuesta;
        this.nombreUsuarioPregunta = nombreUsuarioPregunta;
        this.apellidoUsuarioPregunta = apellidoUsuarioPregunta;
        this.usuarioResponde = usuarioResponde;
    }

    public String getUsuarioResponde() {
        return usuarioResponde;
    }

    public void setUsuarioResponde(String usuarioResponde) {
        this.usuarioResponde = usuarioResponde;
    }

   

    public String getApellidoUsuarioPregunta() {
        return apellidoUsuarioPregunta;
    }

    public void setApellidoUsuarioPregunta(String apellidoUsuarioPregunta) {
        this.apellidoUsuarioPregunta = apellidoUsuarioPregunta;
    }

   

    public int getIdPregunta() {
        return idPregunta;
    }

    public String getRespuesta() {
        return respuesta;
    }

    public void setRespuesta(String respuesta) {
        this.respuesta = respuesta;
    }

   

    public String getNombreUsuarioPregunta() {
        return nombreUsuarioPregunta;
    }

    public void setNombreUsuarioPregunta(String nombreUsuarioPregunta) {
        this.nombreUsuarioPregunta = nombreUsuarioPregunta;
    }

   

    public void setIdPregunta(int idPregunta) {
        this.idPregunta = idPregunta;
    }

    public String getPregunta() {
        return pregunta;
    }

    public void setPregunta(String pregunta) {
        this.pregunta = pregunta;
    }

    public int getEstadoPregunta() {
        return estadoPregunta;
    }

    public void setEstadoPregunta(int estadoPregunta) {
        this.estadoPregunta = estadoPregunta;
    }

    public int getIdUsuarioPregunta() {
        return idUsuarioPregunta;
    }

    public void setIdUsuarioPregunta(int idUsuarioPregunta) {
        this.idUsuarioPregunta = idUsuarioPregunta;
    }

    public int getIdProducto() {
        return idProducto;
    }

    public void setIdProducto(int idProducto) {
        this.idProducto = idProducto;
    }

    @Override
    public String toString() {
        return "preguntasDTO{" + "idPregunta=" + idPregunta + ", pregunta=" + pregunta + ", estadoPregunta=" + estadoPregunta + ", idUsuarioPregunta=" + idUsuarioPregunta + ", idProducto=" + idProducto + '}';
    }
   
   
}
