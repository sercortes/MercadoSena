/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.sena.mercado.dto;

/**
 *
 * @author serfin
 */
public class CompradorDTO {
    
    private String idComprador;
    private String idPersona;
    private String idEmpresa;

    public CompradorDTO() {
    }

    public CompradorDTO(String idPersona, String idEmpresa) {
        this.idPersona = idPersona;
        this.idEmpresa = idEmpresa;
    }
    
    public CompradorDTO(String idComprador, String idPersona, String idEmpresa) {
        this.idComprador = idComprador;
        this.idPersona = idPersona;
        this.idEmpresa = idEmpresa;
    }

    public String getIdComprador() {
        return idComprador;
    }

    public void setIdComprador(String idComprador) {
        this.idComprador = idComprador;
    }

    public String getIdPersona() {
        return idPersona;
    }

    public void setIdPersona(String idPersona) {
        this.idPersona = idPersona;
    }

    public String getIdEmpresa() {
        return idEmpresa;
    }

    public void setIdEmpresa(String idEmpresa) {
        this.idEmpresa = idEmpresa;
    }

    @Override
    public String toString() {
        return "CompradorDTO{" + "idComprador=" + idComprador + ", idPersona=" + idPersona + ", idEmpresa=" + idEmpresa + '}';
    }
    
}
