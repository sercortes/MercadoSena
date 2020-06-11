/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.sena.mercado.dto;

import java.util.ArrayList;

/**
 *
 * @author DELL
 */
public class productoImagenesDTO {
    Producto producto;
    ArrayList<ImagenesProducto> imagenes;

    public productoImagenesDTO(Producto producto, ArrayList<ImagenesProducto> imagenes) {
        this.producto = producto;
        this.imagenes = imagenes;
    }

    public productoImagenesDTO() {
    }

    public Producto getProducto() {
        return producto;
    }

    public void setProducto(Producto producto) {
        this.producto = producto;
    }

    public ArrayList<ImagenesProducto> getImagenes() {
        return imagenes;
    }

    public void setImagenes(ArrayList<ImagenesProducto> imagenes) {
        this.imagenes = imagenes;
    }

    @Override
    public String toString() {
        return "productoImagenesDTO{" + "producto=" + producto + ", imagenes=" + imagenes + '}';
    }

  
}
