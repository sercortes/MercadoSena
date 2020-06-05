/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.sena.mercado.controller.login;

import co.edu.sena.mercado.dao.empresaDAO;
import co.edu.sena.mercado.dao.personaNaturalDAO;
import co.edu.sena.mercado.dao.usuarioDAO;
import co.edu.sena.mercado.dto.empresaDTO;
import co.edu.sena.mercado.dto.personaNaturalDTO;
import co.edu.sena.mercado.dto.usuarioDTO;
import com.google.gson.Gson;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author serfin
 */
public class login extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            throw new Exception();
        } catch (Exception ex) {
            Logger.getLogger(login.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");

        String userParam = request.getParameter("email");
        String passParam = request.getParameter("pass");

        usuarioDTO UsuarioParam = new usuarioDTO(userParam, passParam);
        usuarioDAO userdao = new usuarioDAO();
        usuarioDTO usuario = userdao.login(UsuarioParam);
        
        response.setContentType("application/json");
        String[] restpuesta = new String[3];
        restpuesta[0] = "true";

        if ((usuario.getIdUsuario() > 0) && (usuario.getEstadoUsu().equals("1"))) {

            userdao.actEnteda(usuario.getNumIngreso() + 1, usuario.getIdUsuario());
            HttpSession session = request.getSession();
            
            //asignando persona
            personaNaturalDTO perDTO = new personaNaturalDAO().getDataById(Integer.toString(usuario.getIdUsuario()));
            usuario.setPersona(perDTO);
            
            if (usuario.getIdRol() == 3) {

                // asignando empresa
                empresaDTO emDTO = new empresaDAO().buscarEmpresa(usuario.getIdUsuario());
                restpuesta[2] = Integer.toString(emDTO.getIdEmpresa());
                usuario.setEmpresa(emDTO);
                    
                if (usuario.getNumIngreso() == 0) {
                    restpuesta[0] = "false";
                }

            }

            restpuesta[1] = "true";
            session.setAttribute("USER", usuario);
            System.out.println(usuario.toString());
            
            new Gson().toJson(restpuesta, response.getWriter());

        } else {

            new Gson().toJson(false, response.getWriter());

        }

    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
