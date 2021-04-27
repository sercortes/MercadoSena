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
   
   private String idUsuario;
   private String nombreUsuario;
   private String urlPersona;
   private String nombreProducto;
   private Timestamp fechaPublicada;
   
   
   private usuarioDTO uDTO;
   private personaNaturalDTO perNaturalDTO;
   private Producto producto;
   
   private String vista;

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

    public usuarioDTO getuDTO() {
        return uDTO;
    }

    public void setuDTO(usuarioDTO uDTO) {
        this.uDTO = uDTO;
    }

    public personaNaturalDTO getPerNaturalDTO() {
        return perNaturalDTO;
    }

    public void setPerNaturalDTO(personaNaturalDTO perNaturalDTO) {
        this.perNaturalDTO = perNaturalDTO;
    }

    public Producto getProducto() {
        return producto;
    }

    public void setProducto(Producto producto) {
        this.producto = producto;
    }

    public String getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(String idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }

    public String getUrlPersona() {
        return urlPersona;
    }

    public void setUrlPersona(String urlPersona) {
        this.urlPersona = urlPersona;
    }

    public String getNombreProducto() {
        return nombreProducto;
    }

    public void setNombreProducto(String nombreProducto) {
        this.nombreProducto = nombreProducto;
    }

    public Timestamp getFechaPublicada() {
        return fechaPublicada;
    }

    public void setFechaPublicada(Timestamp fechaPublicada) {
        this.fechaPublicada = fechaPublicada;
    }

    public String getVista() {
        return vista;
    }

    public void setVista(String vista) {
        this.vista = vista;
    }

    @Override
    public String toString() {
        return "preguntasDTO{" + "idPregunta=" + idPregunta + ", pregunta=" + pregunta + ", estadoPregunta=" + estadoPregunta + ", idUsuarioPregunta=" + idUsuarioPregunta + ", idProducto=" + idProducto + ", respuesta=" + respuesta + ", nombreUsuarioPregunta=" + nombreUsuarioPregunta + ", apellidoUsuarioPregunta=" + apellidoUsuarioPregunta + ", usuarioResponde=" + usuarioResponde + ", idUsuario=" + idUsuario + ", nombreUsuario=" + nombreUsuario + ", urlPersona=" + urlPersona + ", nombreProducto=" + nombreProducto + ", fechaPublicada=" + fechaPublicada + ", uDTO=" + uDTO + ", perNaturalDTO=" + perNaturalDTO + ", producto=" + producto + ", vista=" + vista + '}';
    }


    
}
