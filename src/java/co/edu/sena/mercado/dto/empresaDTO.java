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
public class empresaDTO {
    int idEmpresa;
    int esEmpresa;
    String nombreEmpresa;
    String DirEmpresa;
    String telEmpresa;
    String CelEmpresa;
    String correoEmpresa;
    String nombreCiudad;
    int idCiudad;
    int idUsuario;

    public empresaDTO() {
    }

    public empresaDTO(int idEmpresa, int esEmpresa, String nombreEmpresa, String DirEmpresa, String telEmpresa, String CelEmpresa, String correoEmpresa, String nombreCiudad, int idCiudad, int idUsuario) {
        this.idEmpresa = idEmpresa;
        this.esEmpresa = esEmpresa;
        this.nombreEmpresa = nombreEmpresa;
        this.DirEmpresa = DirEmpresa;
        this.telEmpresa = telEmpresa;
        this.CelEmpresa = CelEmpresa;
        this.correoEmpresa = correoEmpresa;
        this.nombreCiudad = nombreCiudad;
        this.idCiudad = idCiudad;
        this.idUsuario = idUsuario;
    }

    public String getNombreCiudad() {
        return nombreCiudad;
    }

    public void setNombreCiudad(String nombreCiudad) {
        this.nombreCiudad = nombreCiudad;
    }

  

    public int getIdEmpresa() {
        return idEmpresa;
    }

    public void setIdEmpresa(int idEmpresa) {
        this.idEmpresa = idEmpresa;
    }

    public int getEsEmpresa() {
        return esEmpresa;
    }

    public void setEsEmpresa(int esEmpresa) {
        this.esEmpresa = esEmpresa;
    }

    public String getNombreEmpresa() {
        return nombreEmpresa;
    }

    public void setNombreEmpresa(String nombreEmpresa) {
        this.nombreEmpresa = nombreEmpresa;
    }

    public String getDirEmpresa() {
        return DirEmpresa;
    }

    public void setDirEmpresa(String DirEmpresa) {
        this.DirEmpresa = DirEmpresa;
    }

    public String getTelEmpresa() {
        return telEmpresa;
    }

    public void setTelEmpresa(String telEmpresa) {
        this.telEmpresa = telEmpresa;
    }

    public String getCelEmpresa() {
        return CelEmpresa;
    }

    public void setCelEmpresa(String CelEmpresa) {
        this.CelEmpresa = CelEmpresa;
    }

    public String getCorreoEmpresa() {
        return correoEmpresa;
    }

    public void setCorreoEmpresa(String correoEmpresa) {
        this.correoEmpresa = correoEmpresa;
    }

    public int getIdCiudad() {
        return idCiudad;
    }

    public void setIdCiudad(int idCiudad) {
        this.idCiudad = idCiudad;
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    @Override
    public String toString() {
        return "empresaDTO{" + "idEmpresa=" + idEmpresa + ", esEmpresa=" + esEmpresa + ", nombreEmpresa=" + nombreEmpresa + ", DirEmpresa=" + DirEmpresa + ", telEmpresa=" + telEmpresa + ", CelEmpresa=" + CelEmpresa + ", correoEmpresa=" + correoEmpresa + ", idCiudad=" + idCiudad + ", idUsuario=" + idUsuario + '}';
    }
    
    
}
