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
public class Categorys {
    
    private String idcategoria;
    private String nombreCategoria;
    private String descripcion;

    public Categorys() {
    }

    public Categorys(String idcategoria, String nombreCategoria) {
        this.idcategoria = idcategoria;
        this.nombreCategoria = nombreCategoria;
    }

    public String getIdcategoria() {
        return idcategoria;
    }

    public void setIdcategoria(String idcategoria) {
        this.idcategoria = idcategoria;
    }

    public String getNombreCategoria() {
        return nombreCategoria;
    }

    public void setNombreCategoria(String nombreCategoria) {
        this.nombreCategoria = nombreCategoria;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    @Override
    public String toString() {
        return "Categorys{" + "idcategoria=" + idcategoria + ", nombreCategoria=" + nombreCategoria + ", descripcion=" + descripcion + '}';
    }
    
    
    
}
