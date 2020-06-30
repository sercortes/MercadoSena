/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.sena.mercado.notificaciones;


/**
 *
 * @author DELL
 */
public class notificacion {
    
    //pregunta
    int idUsuarioPregunta;
    int idProducto;
    int idUsuario;
    int idEmpresa;
    String tipoConsulta;

    public notificacion() {
    }

    public notificacion(int idUsuarioPregunta, int idProducto, int idUsuario, int idEmpresa, String tipoConsulta) {
        this.idUsuarioPregunta = idUsuarioPregunta;
        this.idProducto = idProducto;
        this.idUsuario = idUsuario;
        this.idEmpresa = idEmpresa;
        this.tipoConsulta = tipoConsulta;
    }

    public String getTipoConsulta() {
        return tipoConsulta;
    }

    public void setTipoConsulta(String tipoConsulta) {
        this.tipoConsulta = tipoConsulta;
    }

    

    public int getIdUsuarioPregunta() {
        return idUsuarioPregunta;
    }

    public void setIdUsuarioPregunta(int idUsuarioPregunta) {
        this.idUsuarioPregunta = idUsuarioPregunta;
    }

    public int getIdProducto() {
        return idProducto;
    }

    public void setIdProducto(int idProducto) {
        this.idProducto = idProducto;
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    public int getIdEmpresa() {
        return idEmpresa;
    }

    public void setIdEmpresa(int idEmpresa) {
        this.idEmpresa = idEmpresa;
    }
    
           
    
}
