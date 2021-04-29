/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.sena.mercado.controller.login;

import co.edu.sena.mercado.dao.UsuarioDAOLogin;
import co.edu.sena.mercado.dao.empresaDAO;
import co.edu.sena.mercado.dao.personaNaturalDAO;
import co.edu.sena.mercado.dao.usuarioDAO;
import co.edu.sena.mercado.dto.empresaDTO;
import co.edu.sena.mercado.dto.personaNaturalDTO;
import co.edu.sena.mercado.dto.usuarioDTO;
import co.edu.sena.mercado.util.Conexion;
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
        System.out.println("login no soporta GET");
        response.sendRedirect(request.getContextPath() + "/home");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");
        response.setContentType("application/json");

        if (request.getParameter("fall") != null) {

            try {
                enterSystem(request, response);
            } catch (Exception ex) {
                System.out.println(ex);
            }

        } else {

            new Gson().toJson(00, response.getWriter());

        }

    }

    private void enterSystem(HttpServletRequest request, HttpServletResponse response) throws IOException, Exception {

        System.out.println("");
        String userParam = request.getParameter("email");
        String passParam = request.getParameter("pass");

        usuarioDTO UsuarioParam = new usuarioDTO(userParam, passParam);
        Conexion conexion = new Conexion();
        UsuarioDAOLogin userdao = new UsuarioDAOLogin(conexion.getConnection());
        usuarioDTO usuario = userdao.login(UsuarioParam);
        boolean isUser = userdao.isUser(userParam);
        userdao.CloseAll();

        System.out.println("Intento");
        System.out.println(usuario.toString());
        System.out.println("");

        if (!isUser) {
            new Gson().toJson(00, response.getWriter());
            throw new Exception();
        }

        if (!(usuario.getIdUsuario() > 0)) {
            new Gson().toJson(00, response.getWriter());
            throw new Exception();
        }

        if (!usuario.getEstadoUsu().equals("1")) {
            new Gson().toJson(10, response.getWriter());
            throw new Exception();
        }
        System.out.println("");
        System.out.println("INGRESO");
        System.out.println(request.getParameter("email"));
        System.out.println(request.getParameter("pass"));
        System.out.println("");
        
        HttpSession session = request.getSession();
        //asignando persona
        personaNaturalDTO perDTO = new personaNaturalDAO().getDataById(Integer.toString(usuario.getIdUsuario()));
        usuario.setPersona(perDTO);

        if (usuario.getIdRol() == 3) {
            // asignando empresa
            empresaDTO emDTO = new empresaDAO().buscarEmpresa(usuario.getIdUsuario());
            usuario.setEmpresa(emDTO);
        } else {
            usuario.setEmpresa(new empresaDTO());
        }

        session.setAttribute("USER", usuario);
        System.out.println("INGRESO");
        System.out.println(usuario.toString());
        new Gson().toJson(11, response.getWriter());

    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
