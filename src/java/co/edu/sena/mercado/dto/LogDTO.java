/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.sena.mercado.dto;

import java.sql.Date;
import java.sql.Timestamp;

/**
 *
 * @author equipo
 */
public class LogDTO {
    
    private String idLog;
    private String usuario;
    private String idEvento;
    private String tipoFK;
    private String nombreTipoFK;
    private Timestamp fecha;

    public LogDTO() {
    }

    
    
    public String getIdLog() {
        return idLog;
    }

    public void setIdLog(String idLog) {
        this.idLog = idLog;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getIdEvento() {
        return idEvento;
    }

    public void setIdEvento(String idEvento) {
        this.idEvento = idEvento;
    }

    public String getTipoFK() {
        return tipoFK;
    }

    public void setTipoFK(String tipoFK) {
        this.tipoFK = tipoFK;
    }

    public String getNombreTipoFK() {
        return nombreTipoFK;
    }

    public void setNombreTipoFK(String nombreTipoFK) {
        this.nombreTipoFK = nombreTipoFK;
    }

    public Timestamp getFecha() {
        return fecha;
    }

    public void setFecha(Timestamp fecha) {
        this.fecha = fecha;
    }

    @Override
    public String toString() {
        return "LogDTO{" + "idLog=" + idLog + ", usuario=" + usuario + ", idEvento=" + idEvento + ", tipoFK=" + tipoFK + ", nombreTipoFK=" + nombreTipoFK + ", fecha=" + fecha + '}';
    }
    
}
