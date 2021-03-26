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
public class Banner {

    @SerializedName("idBanner")
    private String idBanner;
    @SerializedName("frase")
    private String frase;
    private String descripcion;
    private String url;
    private String boton;

    public Banner() {
    }

    public Banner(String idBanner, String frase, String descripcion, String url, String boton) {
        this.idBanner = idBanner;
        this.frase = frase;
        this.descripcion = descripcion;
        this.url = url;
        this.boton = boton;
    }

    public String getIdBanner() {
        return idBanner;
    }

    public void setIdBanner(String idBanner) {
        this.idBanner = idBanner;
    }

    public String getFrase() {
        return frase;
    }

    public void setFrase(String frase) {
        this.frase = frase;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getBoton() {
        return boton;
    }

    public void setBoton(String boton) {
        this.boton = boton;
    }

    @Override
    public String toString() {
        return "Banner{" + "idBanner=" + idBanner + ", frase=" + frase + ", descripcion=" + descripcion + ", url=" + url + ", boton=" + boton + '}';
    }

}
