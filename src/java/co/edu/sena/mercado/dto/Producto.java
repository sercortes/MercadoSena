/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.sena.mercado.dto;

import java.sql.Date;
import java.util.ArrayList;

public class Producto {

    private String idProducto;
    private String nombreProducto;
    private Double valorProducto;
    private int stockProducto;
    private String marcaProducto;
    private String descripcionProducto;

    private String diasEnvios;
    private String medidaProducto;
    private String empaqueProducto;
    private String embalajeProducto;
    private String ventajaProducto;

    private String idEmpresaFK;
    private String idCategoriaFK;

    private ImagenesProducto imagenesProducto;
    private Categorys categorys;
    private String nombreCategoria;
    private String nombreEmpresa;
    private int idCiudad;
    private String color;
    private String garantia;

    private String ciudad;
    
    private int cantidad;
    
    private String cantidadColores;
    
    private String imagenUnitaria;

    public Producto() {
    }

    public Producto(String nombreProducto, Double valorProducto, int stockProducto, String marcaProducto, String descripcionProducto, String idEmpresaFK, String idCategoriaFK, String nombreEmpresa, int idCiudad) {
        this.nombreProducto = nombreProducto;
        this.valorProducto = valorProducto;
        this.stockProducto = stockProducto;
        this.marcaProducto = marcaProducto;
        this.descripcionProducto = descripcionProducto;
        this.idEmpresaFK = idEmpresaFK;
        this.idCategoriaFK = idCategoriaFK;
        this.nombreEmpresa = nombreEmpresa;
        this.idCiudad = idCiudad;
    }

    public int getIdCiudad() {
        return idCiudad;
    }

    public void setIdCiudad(int idCiudad) {
        this.idCiudad = idCiudad;
    }

    public String getNombreEmpresa() {
        return nombreEmpresa;
    }

    public void setNombreEmpresa(String nombreEmpresa) {
        this.nombreEmpresa = nombreEmpresa;
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

//    public Date getFechaVencimiento() {
//        return fechaVencimiento;
//    }
//
//    public void setFechaVencimiento(String fechaVencimiento) {
//        this.fechaVencimiento = Date.valueOf(fechaVencimiento);
//    }
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

    public String getDiasEnvios() {
        return diasEnvios;
    }

    public void setDiasEnvios(String diasEnvios) {
        this.diasEnvios = diasEnvios;
    }

    public String getMedidaProducto() {
        return medidaProducto;
    }

    public void setMedidaProducto(String medidaProducto) {
        this.medidaProducto = medidaProducto;
    }

    public String getEmpaqueProducto() {
        return empaqueProducto;
    }

    public void setEmpaqueProducto(String empaqueProducto) {
        this.empaqueProducto = empaqueProducto;
    }

    public String getEmbalajeProducto() {
        return embalajeProducto;
    }

    public void setEmbalajeProducto(String embalajeProducto) {
        this.embalajeProducto = embalajeProducto;
    }

    public String getVentajaProducto() {
        return ventajaProducto;
    }

    public void setVentajaProducto(String ventajaProducto) {
        this.ventajaProducto = ventajaProducto;
    }

    public String getNombreCategoria() {
        return nombreCategoria;
    }

    public void setNombreCategoria(String nombreCategoria) {
        this.nombreCategoria = nombreCategoria;
    }

    public Producto(String idProducto, String nombreProducto, Double valorProducto, int stockProducto, String marcaProducto, String descripcionProducto, String diasEnvios, String medidaProducto, String empaqueProducto, String embalajeProducto, String ventajaProducto, String idEmpresaFK, String idCategoriaFK) {
        this.idProducto = idProducto;
        this.nombreProducto = nombreProducto;
        this.valorProducto = valorProducto;
        this.stockProducto = stockProducto;
        this.marcaProducto = marcaProducto;
        this.descripcionProducto = descripcionProducto;
        this.diasEnvios = diasEnvios;
        this.medidaProducto = medidaProducto;
        this.empaqueProducto = empaqueProducto;
        this.embalajeProducto = embalajeProducto;
        this.ventajaProducto = ventajaProducto;
        this.idEmpresaFK = idEmpresaFK;
        this.idCategoriaFK = idCategoriaFK;
    }

    public String getCiudad() {
        return ciudad;
    }

    public void setCiudad(String ciudad) {
        this.ciudad = ciudad;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public String getImagenUnitaria() {
        return imagenUnitaria;
    }

    public void setImagenUnitaria(String imagenUnitaria) {
        this.imagenUnitaria = imagenUnitaria;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getGarantia() {
        return garantia;
    }

    public void setGarantia(String garantia) {
        this.garantia = garantia;
    }

    public String getCantidadColores() {
        return cantidadColores;
    }

    public void setCantidadColores(String cantidadColores) {
        this.cantidadColores = cantidadColores;
    }

    @Override
    public String toString() {
        return "Producto{" + "idProducto=" + idProducto + ", nombreProducto=" + nombreProducto + ", valorProducto=" + valorProducto + ", stockProducto=" + stockProducto + ", marcaProducto=" + marcaProducto + ", descripcionProducto=" + descripcionProducto + ", diasEnvios=" + diasEnvios + ", medidaProducto=" + medidaProducto + ", empaqueProducto=" + empaqueProducto + ", embalajeProducto=" + embalajeProducto + ", ventajaProducto=" + ventajaProducto + ", idEmpresaFK=" + idEmpresaFK + ", idCategoriaFK=" + idCategoriaFK + ", imagenesProducto=" + imagenesProducto + ", categorys=" + categorys + ", nombreCategoria=" + nombreCategoria + ", nombreEmpresa=" + nombreEmpresa + ", idCiudad=" + idCiudad + ", color=" + color + ", garantia=" + garantia + ", ciudad=" + ciudad + ", cantidad=" + cantidad + ", cantidadColores=" + cantidadColores + ", imagenUnitaria=" + imagenUnitaria + '}';
    }

}
