
package co.edu.sena.mercado.dto;

import com.google.gson.annotations.SerializedName;

public class ProducctoDTO {
    
    @SerializedName("idProducto")
    private String idProducto;
    @SerializedName("cantidad")
    private int cantidad;
    @SerializedName("valorProducto")
    private Double valorUnitario;

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

    @Override
    public String toString() {
        return "ProducctoDTO{" + "idProducto=" + idProducto + ", cantidad=" + cantidad + ", valorUnitario=" + valorUnitario + '}';
    }
    
}
