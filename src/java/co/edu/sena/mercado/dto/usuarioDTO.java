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
public class usuarioDTO {
    
    int idUsuario;
    String correoUsu;
    String claveUsu;
    String fechaClave;
    String estadoUsu;
    String codigo;
    int idRol;
    int numIngreso;
    
    private personaNaturalDTO persona;
    private empresaDTO empresa;
    
    private int actualizoEmpresa;

    public usuarioDTO() {
    }

    public usuarioDTO(int idUsuario, String correoUsu, String claveUsu, String fechaClave, String estadoUsu, String codigo, int idRol, int numIngreso, personaNaturalDTO persona, empresaDTO empresa, int actualizoEmpresa) {
        this.idUsuario = idUsuario;
        this.correoUsu = correoUsu;
        this.claveUsu = claveUsu;
        this.fechaClave = fechaClave;
        this.estadoUsu = estadoUsu;
        this.codigo = codigo;
        this.idRol = idRol;
        this.numIngreso = numIngreso;
        this.persona = persona;
        this.empresa = empresa;
        this.actualizoEmpresa = actualizoEmpresa;
    }

  

    public usuarioDTO(String correoUsu, String claveUsu) {
        this.correoUsu = correoUsu;
        this.claveUsu = claveUsu;
    }

    
    
    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

   

    
    
    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getCorreoUsu() {
        return correoUsu;
    }

    public void setCorreoUsu(String correoUsu) {
        this.correoUsu = correoUsu;
    }

    public String getClaveUsu() {
        return claveUsu;
    }

    public void setClaveUsu(String claveUsu) {
        this.claveUsu = claveUsu;
    }

    public String getFechaClave() {
        return fechaClave;
    }

    public void setFechaClave(String fechaClave) {
        this.fechaClave = fechaClave;
    }

    public String getEstadoUsu() {
        return estadoUsu;
    }

    public void setEstadoUsu(String estadoUsu) {
        this.estadoUsu = estadoUsu;
    }

    public int getIdRol() {
        return idRol;
    }

    public void setIdRol(int idRol) {
        this.idRol = idRol;
    }

    public personaNaturalDTO getPersona() {
        return persona;
    }

    public void setPersona(personaNaturalDTO persona) {
        this.persona = persona;
    }

    public empresaDTO getEmpresa() {
        return empresa;
    }

    public void setEmpresa(empresaDTO empresa) {
        this.empresa = empresa;
    }

    public int getActualizoEmpresa() {
        return actualizoEmpresa;
    }

    public void setActualizoEmpresa(int estado) {
        this.actualizoEmpresa = estado;
    }

    public int getNumIngreso() {
        return numIngreso;
    }

    public void setNumIngreso(int numIngreso) {
        this.numIngreso = numIngreso;
    }
    

    @Override
    public String toString() {
        return "usuarioDTO{" + "idUsuario=" + idUsuario + ", correoUsu=" + correoUsu + ", claveUsu=" + claveUsu + ", fechaClave=" + fechaClave + ", estadoUsu=" + estadoUsu + ", codigo=" + codigo + ", idRol=" + idRol + ", persona=" + persona + ", empresa=" + empresa + ", actualizoEmpresa=" + actualizoEmpresa + '}';
    }

  
    
}
