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
public class pagoJSON {
    
    @SerializedName("status_message")
    private String estado;
    
    @SerializedName("payment_method_type")
    private String metodo;
    
    @SerializedName("status")
    private String statusCard;

    public pagoJSON() {
    }

    public pagoJSON(String estado) {
        this.estado = estado;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getMetodo() {
        return metodo;
    }

    public void setMetodo(String metodo) {
        this.metodo = metodo;
    }

    public String getStatusCard() {
        return statusCard;
    }

    public void setStatusCard(String statusCard) {
        this.statusCard = statusCard;
    }

    @Override
    public String toString() {
        return "pagoJSON{" + "estado=" + estado + ", metodo=" + metodo + ", statusCard=" + statusCard + '}';
    }


    
}
