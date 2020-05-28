/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.sena.mercado.dto;

/**
 *
 * @author DELL
 */
public class generoDTO {
    int idGenero;
    String genero;

    public generoDTO() {
    }

    public generoDTO(int idGenero, String genero) {
        this.idGenero = idGenero;
        this.genero = genero;
    }

    public int getIdGenero() {
        return idGenero;
    }

    public void setIdGenero(int idGenero) {
        this.idGenero = idGenero;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    @Override
    public String toString() {
        return "generoDTO{" + "idGenero=" + idGenero + ", genero=" + genero + '}';
    }
    
}
