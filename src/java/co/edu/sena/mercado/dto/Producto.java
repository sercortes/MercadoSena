/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.sena.mercado.dto;

import java.sql.Date;

public class Producto {

    private String idProducto;
    private String nombreProducto;
    private Double valorProducto;
    private int stockProducto;
    private String marcaProducto;
    private Date fechaVencimiento;
    private String descripcionProducto;
    
    private String idEmpresaFK;
    private String idCategoriaFK;
    
    private ImagenesProducto imagenesProducto;
    private Categorys categorys;

    public Producto() {
    }

    public Producto(String nombreProducto, Double valorProducto, int stockProducto, String marcaProducto, String descripcionProducto, String idEmpresaFK, String idCategoriaFK) {
        this.nombreProducto = nombreProducto;
        this.valorProducto = valorProducto;
        this.stockProducto = stockProducto;
        this.marcaProducto = marcaProducto;
        this.descripcionProducto = descripcionProducto;
        this.idEmpresaFK = idEmpresaFK;
        this.idCategoriaFK = idCategoriaFK;
    }

    public Producto(String nombreProducto, Double valorProducto, int stockProducto, String marcaProducto, Date fechaVencimiento, String descripcionProducto, String idEmpresaFK, String idCategoriaFK) {
        this.nombreProducto = nombreProducto;
        this.valorProducto = valorProducto;
        this.stockProducto = stockProducto;
        this.marcaProducto = marcaProducto;
        this.fechaVencimiento = fechaVencimiento;
        this.descripcionProducto = descripcionProducto;
        this.idEmpresaFK = idEmpresaFK;
        this.idCategoriaFK = idCategoriaFK;
    }

    public Producto(String idProducto, String nombreProducto, Double valorProducto, int stockProducto, String marcaProducto, Date fechaVencimiento, String descripcionProducto, String idEmpresaFK, String idCategoriaFK) {
        this.idProducto = idProducto;
        this.nombreProducto = nombreProducto;
        this.valorProducto = valorProducto;
        this.stockProducto = stockProducto;
        this.marcaProducto = marcaProducto;
        this.fechaVencimiento = fechaVencimiento;
        this.descripcionProducto = descripcionProducto;
        this.idEmpresaFK = idEmpresaFK;
        this.idCategoriaFK = idCategoriaFK;
    }

    public String getIdProducto() {
        return idProducto;
    }

    public void setIdProducto(String idProducto) {
        this.idProducto = idProducto;
    }

    public String getNombreProducto() {
        return nombreProducto;
    }

    public void setNombreProducto(String nombreProducto) {
        this.nombreProducto = nombreProducto;
    }

    public Double getValorProducto() {
        return valorProducto;
    }

    public void setValorProducto(Double valorProducto) {
        this.valorProducto = valorProducto;
    }

    public int getStockProducto() {
        return stockProducto;
    }

    public void setStockProducto(int stockProducto) {
        this.stockProducto = stockProducto;
    }

    public String getMarcaProducto() {
        return marcaProducto;
    }

    public void setMarcaProducto(String marcaProducto) {
        this.marcaProducto = marcaProducto;
    }

    public Date getFechaVencimiento() {
        return fechaVencimiento;
    }

    public void setFechaVencimiento(String fechaVencimiento) {
        this.fechaVencimiento = Date.valueOf(fechaVencimiento);
    }

    public String getDescripcionProducto() {
        return descripcionProducto;
    }

    public void setDescripcionProducto(String descripcionProducto) {
        this.descripcionProducto = descripcionProducto;
    }

    public String getIdEmpresaFK() {
        return idEmpresaFK;
    }

    public void setIdEmpresaFK(String idEmpresaFK) {
        this.idEmpresaFK = idEmpresaFK;
    }

    public String getIdCategoriaFK() {
        return idCategoriaFK;
    }

    public void setIdCategoriaFK(String idCategoriaFK) {
        this.idCategoriaFK = idCategoriaFK;
    }

    public ImagenesProducto getImagenesProducto() {
        return imagenesProducto;
    }

    public void setImagenesProducto(ImagenesProducto imagenesProducto) {
        this.imagenesProducto = imagenesProducto;
    }

    public Categorys getCategorys() {
        return categorys;
    }

    public void setCategorys(Categorys categorys) {
        this.categorys = categorys;
    }

    @Override
    public String toString() {
        return "Producto{" + "idProducto=" + idProducto + ", nombreProducto=" + nombreProducto + ", valorProducto=" + valorProducto + ", stockProducto=" + stockProducto + ", marcaProducto=" + marcaProducto + ", fechaVencimiento=" + fechaVencimiento + ", descripcionProducto=" + descripcionProducto + ", idEmpresaFK=" + idEmpresaFK + ", idCategoriaFK=" + idCategoriaFK + ", imagenesProducto=" + imagenesProducto + ", categorys=" + categorys + '}';
    }


    
    
    
}
