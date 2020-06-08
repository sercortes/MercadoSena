/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.sena.mercado.dto;

/**
 *
 * @author serfin
 */
public class EmpresaPedidoDTO {
    
    private String empresaPedido;
    private String idEmpresaFK;
    private String idVentaFK;

    public EmpresaPedidoDTO() {
    }

    public String getEmpresaPedido() {
        return empresaPedido;
    }

    public void setEmpresaPedido(String empresaPedido) {
        this.empresaPedido = empresaPedido;
    }

    public String getIdEmpresaFK() {
        return idEmpresaFK;
    }

    public void setIdEmpresaFK(String idEmpresaFK) {
        this.idEmpresaFK = idEmpresaFK;
    }

    public String getIdVentaFK() {
        return idVentaFK;
    }

    public void setIdVentaFK(String idVentaFK) {
        this.idVentaFK = idVentaFK;
    }

    @Override
    public String toString() {
        return "EmpresaPedidoDTO{" + "empresaPedido=" + empresaPedido + ", idEmpresaFK=" + idEmpresaFK + ", idVentaFK=" + idVentaFK + '}';
    }
    
}
