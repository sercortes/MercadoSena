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

    public preguntasDTO() {
    }

    public preguntasDTO(int idPregunta, String pregunta, int estadoPregunta, int idUsuarioPregunta, int idProducto) {
        this.idPregunta = idPregunta;
        this.pregunta = pregunta;
        this.estadoPregunta = estadoPregunta;
        this.idUsuarioPregunta = idUsuarioPregunta;
        this.idProducto = idProducto;
    }

    public int getIdPregunta() {
        return idPregunta;
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
