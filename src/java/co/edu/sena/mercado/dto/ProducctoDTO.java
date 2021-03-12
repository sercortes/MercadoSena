
package co.edu.sena.mercado.dto;

import com.google.gson.annotations.SerializedName;

public class ProducctoDTO {
    
    @SerializedName("idProducto")
    private String idProducto;
    @SerializedName("cantidad")
    private int cantidad;
    @SerializedName("valorProducto")
    private Double valorUnitario;
    @SerializedName("idProductoColor")
    private String idProductoColor;
    
    private int Stock;

    public ProducctoDTO() {
    
    }

    public ProducctoDTO(String idProducto, int cantidad, Double valorUnitario) {
        this.idProducto = idProducto;
        this.cantidad = cantidad;
        this.valorUnitario = valorUnitario;
    }

    
    
    public String getIdProducto() {
        return idProducto;
    }

    public void setIdProducto(String idProducto) {
        this.idProducto = idProducto;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public Double getValorUnitario() {
        return valorUnitario;
    }

    public void setValorUnitario(Double valorUnitario) {
        this.valorUnitario = valorUnitario;
    }

    public int getStock() {
        return Stock;
    }

    public void setStock(int Stock) {
        this.Stock = Stock;
    }

    public String getIdProductoColor() {
        return idProductoColor;
    }

    public void setIdProductoColor(String idProductoColor) {
        this.idProductoColor = idProductoColor;
    }

    @Override
    public String toString() {
        return "ProducctoDTO{" + "idProducto=" + idProducto + ", cantidad=" + cantidad + ", valorUnitario=" + valorUnitario + ", idProductoColor=" + idProductoColor + ", Stock=" + Stock + '}';
    }
    
}
