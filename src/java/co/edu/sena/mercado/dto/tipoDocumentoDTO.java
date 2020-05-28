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
public class tipoDocumentoDTO {
    int idTipoDoc;
    String tipoDoc;

    public tipoDocumentoDTO() {
    }

    public tipoDocumentoDTO(int idTipoDoc, String tipoDoc) {
        this.idTipoDoc = idTipoDoc;
        this.tipoDoc = tipoDoc;
    }

    public int getIdTipoDoc() {
        return idTipoDoc;
    }

    public void setIdTipoDoc(int idTipoDoc) {
        this.idTipoDoc = idTipoDoc;
    }

    public String getTipoDoc() {
        return tipoDoc;
    }

    public void setTipoDoc(String tipoDoc) {
        this.tipoDoc = tipoDoc;
    }

    @Override
    public String toString() {
        return "tipoDocumentoDTO{" + "idTipoDoc=" + idTipoDoc + ", tipoDoc=" + tipoDoc + '}';
    }
    
    
}
