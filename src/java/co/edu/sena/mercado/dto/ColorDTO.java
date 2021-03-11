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
public class ColorDTO {

    private String idColor;
    private String nombreColor;
    private String hexa;

    public ColorDTO() {
    }

    public ColorDTO(String idColor, String nombreColor, String hexa) {
        this.idColor = idColor;
        this.nombreColor = nombreColor;
        this.hexa = hexa;
    }

    public String getIdColor() {
        return idColor;
    }

    public void setIdColor(String idColor) {
        this.idColor = idColor;
    }

    public String getNombreColor() {
        return nombreColor;
    }

    public void setNombreColor(String nombreColor) {
        this.nombreColor = nombreColor;
    }

    public String getHexa() {
        return hexa;
    }

    public void setHexa(String hexa) {
        this.hexa = hexa;
    }

    @Override
    public String toString() {
        return "ColorDTO{" + "idColor=" + idColor + ", nombreColor=" + nombreColor + ", hexa=" + hexa + '}';
    }
    
}

