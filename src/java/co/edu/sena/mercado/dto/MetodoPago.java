/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.sena.mercado.dto;

/**
 *
 * @author equipo
 */
public class MetodoPago {
 
    private String idMetodo;
    private String nombre;

    public MetodoPago() {
    }

    public MetodoPago(String idMetodo, String nombre) {
        this.idMetodo = idMetodo;
        this.nombre = nombre;
    }

    public String getIdMetodo() {
        return idMetodo;
    }

    public void setIdMetodo(String idMetodo) {
        this.idMetodo = idMetodo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    @Override
    public String toString() {
        return "MetodoPago{" + "idMetodo=" + idMetodo + ", nombre=" + nombre + '}';
    }
    
}
