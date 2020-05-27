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
public class ImagenesProducto {
    
    private String idImagen;
    private String url;
    private String idProductoFK;

    public ImagenesProducto() {
    }

    public ImagenesProducto(String url, String idProductoFK) {
        this.url = url;
        this.idProductoFK = idProductoFK;
    }

    public String getIdImagen() {
        return idImagen;
    }

    public void setIdImagen(String idImagen) {
        this.idImagen = idImagen;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getIdProductoFK() {
        return idProductoFK;
    }

    public void setIdProductoFK(String idProductoFK) {
        this.idProductoFK = idProductoFK;
    }
    
    
    
}
