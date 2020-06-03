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

        if (usuario.getIdUsuario() > 0) {
           
            HttpSession session = request.getSession();
            personaNaturalDAO perDAO = new personaNaturalDAO();
            personaNaturalDTO perDTO = perDAO.getDataById(Integer.toString(usuario.getIdUsuario()));
            usuario.setPersona(perDTO);
            // get data company
            if (usuario.getIdRol() == 3) {
                
                empresaDAO empDAO = new empresaDAO();
                empresaDTO emDTO = new empresaDTO();
                emDTO = empDAO.buscarEmpresa(usuario.getIdUsuario());

                if (emDTO.getIdEmpresa() > 0) {
                    usuario.setActualizoEmpresa(1);
                    usuario.setEmpresa(emDTO);
                }else{
                    usuario.setActualizoEmpresa(0);
                }
                
            }

            session.setAttribute("USER", usuario);
            new Gson().toJson(true, response.getWriter());
           
            
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
