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
public class ciudadDTO {
    
    int idCiudad;
    String nombreCiudad;
    int idPais;

    public ciudadDTO() {
    }

    public ciudadDTO(int idCiudad, String nombreCiudad, int idPais) {
        this.idCiudad = idCiudad;
        this.nombreCiudad = nombreCiudad;
        this.idPais = idPais;
    }

    public int getIdCiudad() {
        return idCiudad;
    }

    public void setIdCiudad(int idCiudad) {
        this.idCiudad = idCiudad;
    }

    public String getNombreCiudad() {
        return nombreCiudad;
    }

    public void setNombreCiudad(String nombreCiudad) {
        this.nombreCiudad = nombreCiudad;
    }

    public int getIdPais() {
        return idPais;
    }

    public void setIdPais(int idPais) {
        this.idPais = idPais;
    }

    @Override
    public String toString() {
        return "ciudadDTO{" + "idCiudad=" + idCiudad + ", nombreCiudad=" + nombreCiudad + ", idPais=" + idPais + '}';
    }
    
    
    
}
