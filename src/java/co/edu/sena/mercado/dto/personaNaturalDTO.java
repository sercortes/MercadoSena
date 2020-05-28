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
public class personaNaturalDTO {
   
    int idPer;
    String numeroDocPer;
    String nombrePer;
    String apellidoPer;
    String correoPer;
    String direccionPer;
    String numCelularPer;
    String telPer;
    String urlImg;
    int idUsuario;
    int idGenero;
    int idTipoDoc;
    int idCiudad;

    public personaNaturalDTO() {
    }

    public personaNaturalDTO(int idPer, String numeroDocPer, String nombrePer, String apellidoPer, String correoPer, String direccionPer, String numCelularPer, String telPer, String urlImg, int idUsuario, int idGenero, int idTipoDoc, int idCiudad) {
        this.idPer = idPer;
        this.numeroDocPer = numeroDocPer;
        this.nombrePer = nombrePer;
        this.apellidoPer = apellidoPer;
        this.correoPer = correoPer;
        this.direccionPer = direccionPer;
        this.numCelularPer = numCelularPer;
        this.telPer = telPer;
        this.urlImg = urlImg;
        this.idUsuario = idUsuario;
        this.idGenero = idGenero;
        this.idTipoDoc = idTipoDoc;
        this.idCiudad = idCiudad;
    }

    public int getIdPer() {
        return idPer;
    }

    public void setIdPer(int idPer) {
        this.idPer = idPer;
    }

    public String getNumeroDocPer() {
        return numeroDocPer;
    }

    public void setNumeroDocPer(String numeroDocPer) {
        this.numeroDocPer = numeroDocPer;
    }

    public String getNombrePer() {
        return nombrePer;
    }

    public void setNombrePer(String nombrePer) {
        this.nombrePer = nombrePer;
    }

    public String getApellidoPer() {
        return apellidoPer;
    }

    public void setApellidoPer(String apellidoPer) {
        this.apellidoPer = apellidoPer;
    }

    public String getCorreoPer() {
        return correoPer;
    }

    public void setCorreoPer(String correoPer) {
        this.correoPer = correoPer;
    }

    public String getDireccionPer() {
        return direccionPer;
    }

    public void setDireccionPer(String direccionPer) {
        this.direccionPer = direccionPer;
    }

    public String getNumCelularPer() {
        return numCelularPer;
    }

    public void setNumCelularPer(String numCelularPer) {
        this.numCelularPer = numCelularPer;
    }

    public String getTelPer() {
        return telPer;
    }

    public void setTelPer(String telPer) {
        this.telPer = telPer;
    }

    public String getUrlImg() {
        return urlImg;
    }

    public void setUrlImg(String urlImg) {
        this.urlImg = urlImg;
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    public int getIdGenero() {
        return idGenero;
    }

    public void setIdGenero(int idGenero) {
        this.idGenero = idGenero;
    }

    public int getIdTipoDoc() {
        return idTipoDoc;
    }

    public void setIdTipoDoc(int idTipoDoc) {
        this.idTipoDoc = idTipoDoc;
    }

    public int getIdCiudad() {
        return idCiudad;
    }

    public void setIdCiudad(int idCiudad) {
        this.idCiudad = idCiudad;
    }

    @Override
    public String toString() {
        return "personaNaturalDTO{" + "idPer=" + idPer + ", numeroDocPer=" + numeroDocPer + ", nombrePer=" + nombrePer + ", apellidoPer=" + apellidoPer + ", correoPer=" + correoPer + ", direccionPer=" + direccionPer + ", numCelularPer=" + numCelularPer + ", telPer=" + telPer + ", urlImg=" + urlImg + ", idUsuario=" + idUsuario + ", idGenero=" + idGenero + ", idTipoDoc=" + idTipoDoc + ", idCiudad=" + idCiudad + '}';
    }
    
    
    
    
}
