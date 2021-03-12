/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.sena.mercado.dto;

import com.google.gson.annotations.SerializedName;

/**
 *
 * @author equipo
 */
public class ProductoColor {
    
    private String idProductoColor;
    @SerializedName("color")
    private String idColorFK;
    private String idProductoFK;
    @SerializedName("cantidad")
    private int cantidad;

    public ProductoColor() {
    }

    public ProductoColor(String idProductoColor, String idColorFK, String idProductoFK, int cantidad) {
        this.idProductoColor = idProductoColor;
        this.idColorFK = idColorFK;
        this.idProductoFK = idProductoFK;
        this.cantidad = cantidad;
    }

    public String getIdProductoColor() {
        return idProductoColor;
    }

    public void setIdProductoColor(String idProductoColor) {
        this.idProductoColor = idProductoColor;
    }

    public String getIdColorFK() {
        return idColorFK;
    }

    public void setIdColorFK(String idColorFK) {
        this.idColorFK = idColorFK;
    }

    public String getIdProductoFK() {
        return idProductoFK;
    }

    public void setIdProductoFK(String idProductoFK) {
        this.idProductoFK = idProductoFK;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    @Override
    public String toString() {
        return "ProductoColor{" + "idProductoColor=" + idProductoColor + ", idColorFK=" + idColorFK + ", idProductoFK=" + idProductoFK + ", cantidad=" + cantidad + '}';
    }
    
}
