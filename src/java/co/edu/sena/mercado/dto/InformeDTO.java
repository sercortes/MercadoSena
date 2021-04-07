
package co.edu.sena.mercado.dto;

import java.sql.Date;

public class InformeDTO {
    
    private String label;
    private String value;
    
    private String fechaI;
    private String fechaF;
    private String tipoV;
    private boolean grapDays;

    public InformeDTO() {
    }
    
    public InformeDTO(String label, String value) {
        this.label = label;
        this.value = value;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getFechaI() {
        return fechaI;
    }

    public void setFechaI(String fechaI) {
        this.fechaI = fechaI.substring(0, 10);
    }

    public String getFechaF() {
        return fechaF;
    }

    public void setFechaF(String fechaF) {
        this.fechaF = fechaF.substring(0, 10);
    }

    public String getTipoV() {
        return tipoV;
    }

    public void setTipoV(String tipoV) {
        this.tipoV = tipoV;
    }

    public boolean isGrapDays() {
        return grapDays;
    }

    public void setGrapDays(boolean grapDays) {
        this.grapDays = grapDays;
    }

    @Override
    public String toString() {
        return "InformeDTO{" + "label=" + label + ", value=" + value + ", fechaI=" + fechaI + ", fechaF=" + fechaF + ", tipoV=" + tipoV + ", grapDays=" + grapDays + '}';
    }
    
}
