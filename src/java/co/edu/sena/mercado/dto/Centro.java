/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.sena.mercado.dto;

/**
 *
 * @author equipo
 */
public class Centro {
    
    private String idCentro;
    private String nombreCentro;

    public Centro() {
    }

    public Centro(String idCentro, String nombreCentro) {
        this.idCentro = idCentro;
        this.nombreCentro = nombreCentro;
    }

    public String getIdCentro() {
        return idCentro;
    }

    public void setIdCentro(String idCentro) {
        this.idCentro = idCentro;
    }

    public String getNombreCentro() {
        return nombreCentro;
    }

    public void setNombreCentro(String nombreCentro) {
        this.nombreCentro = nombreCentro;
    }

    @Override
    public String toString() {
        return "Centro{" + "idCentro=" + idCentro + ", nombreCentro=" + nombreCentro + '}';
    }
    
    
    
}
