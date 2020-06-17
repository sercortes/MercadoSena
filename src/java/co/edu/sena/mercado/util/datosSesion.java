/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.sena.mercado.util;

import co.edu.sena.mercado.dao.empresaDAO;
import co.edu.sena.mercado.dao.personaNaturalDAO;
import co.edu.sena.mercado.dto.empresaDTO;
import co.edu.sena.mercado.dto.personaNaturalDTO;
import co.edu.sena.mercado.dto.usuarioDTO;

/**
 *
 * @author DELL
 */
public class datosSesion {

    public usuarioDTO consultarDatos(usuarioDTO Usuario) {

        personaNaturalDTO perDTO = new personaNaturalDAO().getDataById(Integer.toString(Usuario.getIdUsuario()));
        Usuario.setPersona(perDTO);
        empresaDTO emDTO = new empresaDAO().buscarEmpresa(Usuario.getIdUsuario());
        Usuario.setEmpresa(emDTO);

        return Usuario;

    }

}
