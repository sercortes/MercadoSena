package co.edu.sena.mercado.controller.actualizacion;

import co.edu.sena.mercado.dao.EmpresasDAO;
import co.edu.sena.mercado.dao.PersonasNaturalDAO;
import co.edu.sena.mercado.dao.UsuarioDAOLogin;
import co.edu.sena.mercado.dao.empresaDAO;
import co.edu.sena.mercado.dao.personaNaturalDAO;
import co.edu.sena.mercado.dao.usuarioDAO;
import co.edu.sena.mercado.dto.empresaDTO;
import co.edu.sena.mercado.dto.personaNaturalDTO;
import co.edu.sena.mercado.dto.usuarioDTO;
import co.edu.sena.mercado.util.Conexion;
import co.edu.sena.mercado.util.codActivacion;
import co.edu.sena.mercado.util.correo;
import co.edu.sena.mercado.util.datosSesion;
import com.google.gson.Gson;
import com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.lang3.RandomStringUtils;

public class actualizaUsuEmp extends HttpServlet {

    private final String url_serve = "/home/equipo/servers/apache-tomcat-8.0.27/webapps/ROOT/usuarios/";
    private final String servidor = "http://192.168.20.48:8084/usuarios/";

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        System.out.println("actalizaemp no soporta GET");
        response.sendRedirect(request.getContextPath() + "/home");

    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        System.out.println("actalizaemp no soporta GET");
        response.sendRedirect(request.getContextPath() + "/home");
    }

    @Override

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String accion2 = request.getParameter("accion");
        switch (accion2) {
            case "actualizarPersonas":

                updateData(request, response);

                break;
            case "actualizarUsuariosCla":

                updateClave(request, response);

                break;

            case "actualizaDatosFaltantes":
                
                actualizarDatosFaltantes(request, response);

                break;

            case "recuperarClave":

                recoverPass(request, response);

                break;
            default:
                
                System.out.println("default actualiza usu");
                response.sendRedirect(request.getContextPath() + "/home");
                
                break;

        }
    }

    private void actualizarDatosFaltantes(HttpServletRequest request, HttpServletResponse response) throws IOException {

        HttpSession sesion = (HttpSession) request.getSession();
        usuarioDTO DTOusuarioDTO = (usuarioDTO) sesion.getAttribute("USER");

        Connection conn = null;
        EmpresasDAO empresaDAO = null;
        PersonasNaturalDAO perNaturalDAO = null;

        try {

            Conexion conexion = new Conexion();
            conn = conexion.getConnection();

            if (conn.getAutoCommit()) {
                conn.setAutoCommit(false);
            }

            empresaDAO = new EmpresasDAO(conn);
            perNaturalDAO = new PersonasNaturalDAO(conn);

            personaNaturalDTO personaDTO = getDatosPersonaNatutalDTO(request);
            perNaturalDAO.actualizarDatosFaltantes(personaDTO, DTOusuarioDTO.getIdUsuario());

            DTOusuarioDTO = setDatosSession(DTOusuarioDTO, personaDTO, new empresaDTO());
            sesion.setAttribute("USER", DTOusuarioDTO);
            conn.commit();
            new Gson().toJson(1, response.getWriter());

        } catch (MySQLIntegrityConstraintViolationException ex1) {

            System.out.println("ROLL BACK CONSTRAINT EXCEPTION");
            System.out.println(ex1);
            try {
                conn.rollback();
            } catch (SQLException ex3) {
                System.out.println(ex3);
            }
            new Gson().toJson(2, response.getWriter());

        } catch (Exception ex) {

            System.out.println("ROLL BACK GENERAL EXCEPTION");
            System.out.println(ex);
            try {
                conn.rollback();
            } catch (SQLException ex1) {
                System.out.println(ex1);
            }
            new Gson().toJson(3, response.getWriter());

        } finally {
            empresaDAO.CloseAll();
            perNaturalDAO.CloseAll();
        }

    }

    private usuarioDTO setDatosSession(usuarioDTO DTOusuarioDTO, personaNaturalDTO personaDTO, empresaDTO empresaDTO) {

        DTOusuarioDTO.getPersona().setNumCelularPer(personaDTO.getNumCelularPer());
        DTOusuarioDTO.getPersona().setTelPer(personaDTO.getTelPer());
        DTOusuarioDTO.getPersona().setDireccionPer(personaDTO.getDireccionPer());
        DTOusuarioDTO.getEmpresa().setCelEmpresa(empresaDTO.getCelEmpresa());
        DTOusuarioDTO.getEmpresa().setTelEmpresa(empresaDTO.getTelEmpresa());
        DTOusuarioDTO.getEmpresa().setDirEmpresa(empresaDTO.getDirEmpresa());
        DTOusuarioDTO.getEmpresa().setNombreEmpresa(empresaDTO.getNombreEmpresa());

        return DTOusuarioDTO;

    }

    private personaNaturalDTO getDatosPersonaNatutalDTO(HttpServletRequest request) {

        personaNaturalDTO personaDTO = new personaNaturalDTO();
        personaDTO.setNumCelularPer(request.getParameter("celularUsuario"));
        personaDTO.setTelPer(request.getParameter("telefonoUsuario"));
        personaDTO.setDireccionPer(request.getParameter("direccionUsuario"));
        return personaDTO;
    }

    private empresaDTO getDatosEmpresaDTO(HttpServletRequest request, usuarioDTO uDTO) {

        empresaDTO empresaDTO = new empresaDTO();
        empresaDTO.setCelEmpresa(request.getParameter("celularUsuario"));
        empresaDTO.setTelEmpresa(request.getParameter("telefonoUsuario"));
        empresaDTO.setDirEmpresa(request.getParameter("direccionUsuario"));

        if (request.getParameter("centro") != null) {
            empresaDTO.setCentro(request.getParameter("centro"));
            empresaDTO.setEsCentro(request.getParameter("perfil"));
        } else {
            empresaDTO.setCentro(uDTO.getEmpresa().getCentro());
            empresaDTO.setEsCentro(uDTO.getEmpresa().getEsCentro());
        }

        empresaDTO.setNombreEmpresa(request.getParameter("name"));
        return empresaDTO;
    }

    private void updateData(HttpServletRequest request, HttpServletResponse response) throws IOException {

        usuarioDTO usuarioDTO = (usuarioDTO) request.getSession().getAttribute("USER");
        personaNaturalDTO personaDTO = usuarioDTO.getPersona();

        ArrayList<String> lista1 = new ArrayList<>();
        Connection conn = null;
        PersonasNaturalDAO perNaturalDAO = null;
        try {
            FileItemFactory file = new DiskFileItemFactory();
            ServletFileUpload fileUpload = new ServletFileUpload(file);
            List items = fileUpload.parseRequest(request);
            for (int i = 0; i < items.size(); i++) {
                FileItem fileItem = (FileItem) items.get(i);
                if (!fileItem.isFormField()) {
                    String nombre = fileItem.getName();
                    if (nombre.equals("")) {
                    } else {
                        File f = new File(this.url_serve + usuarioDTO.getIdUsuario() + ".jpg");
                        if (f.exists() == true) {
                            f.delete();
                        }
                        System.out.println(f.toString());
                        fileItem.write(f);
                        personaDTO.setUrlImg(this.servidor + usuarioDTO.getIdUsuario() + ".jpg");
                    }
                } else {
                    lista1.add(new String(fileItem.getString().getBytes("ISO-8859-1"), "UTF-8"));
                }
            }

            personaDTO.setIdUsuario(usuarioDTO.getIdUsuario());
            personaDTO.setNombrePer(lista1.get(1));
            personaDTO.setApellidoPer(lista1.get(2));
            personaDTO.setIdCiudad(Integer.parseInt(lista1.get(3)));
            personaDTO.setIdGenero(Integer.parseInt((lista1.get(4))));
            personaDTO.setNumCelularPer(lista1.get(5));
            personaDTO.setTelPer(lista1.get(6));
            personaDTO.setDireccionPer(lista1.get(7));

            System.out.println("UPDATE DATA");
            System.out.println(personaDTO.toString());

            Conexion conexion = new Conexion();
            conn = conexion.getConnection();

            if (conn.getAutoCommit()) {
                conn.setAutoCommit(false);
            }

            perNaturalDAO = new PersonasNaturalDAO(conn);

            perNaturalDAO.updateData(personaDTO);
            conn.commit();

            response.getWriter().print(true);
        } catch (Exception e) {

            try {
                conn.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            e.printStackTrace();
            response.getWriter().print(false);

        } finally {
            perNaturalDAO.CloseAll();
        }

    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

    public String generatePassword() {
        return RandomStringUtils.random(10, 0, 20, true, true, "qw32rfHIJk9iQ8Ud7h0X".toCharArray());
    }

    private void updateClave(HttpServletRequest request, HttpServletResponse response) throws IOException {

        System.out.println("CLAVEE");
        usuarioDTO usuarioDTO = (usuarioDTO) request.getSession().getAttribute("USER");
        usuarioDTO.setClaveUsu(request.getParameter("clave0"));

        Connection conn = null;
        PersonasNaturalDAO perNaturalDAO = null;
        try {

            Conexion conexion = new Conexion();
            conn = conexion.getConnection();

            if (conn.getAutoCommit()) {
                conn.setAutoCommit(false);
            }

            perNaturalDAO = new PersonasNaturalDAO(conn);

            usuarioDTO ecuentrapass = perNaturalDAO.Enecuentracontraseña(usuarioDTO);
            if (ecuentrapass.getIdUsuario() == 0) {
                System.out.println("CLAVE ERRADA");
                throw new Exception();
            }
            usuarioDTO.setClaveUsu(request.getParameter("clave1"));
            perNaturalDAO.updatePass(usuarioDTO);

            conn.commit();

            response.getWriter().print(1);
        } catch (SQLException e) {

            try {
                conn.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            e.printStackTrace();
            response.getWriter().print(2);

        } catch (Exception e) {

            try {
                conn.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            e.printStackTrace();
            response.getWriter().print(3);

        } finally {
            perNaturalDAO.CloseAll();
        }

    }

    private void recoverPass(HttpServletRequest request, HttpServletResponse response) throws IOException {

        System.out.println("--------");
        System.out.println("REQUEST recover PASS");
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json");
        String gRecaptchaResponse = request.getParameter("rec");
        String correo = request.getParameter("email");
        System.out.println(correo);
        Catcha catcha = new Catcha();
        boolean verify = catcha.verify(gRecaptchaResponse);
        UsuarioDAOLogin usuarioDAOLogin = null;
        Connection conn = null;
        String pass = "";

        try {

            if (!verify) {
                System.out.println("MAL CATCHA");
                new Gson().toJson(0, response.getWriter());
                throw new Exception();
            }

            Conexion conexion = new Conexion();
            conn = conexion.getConnection();

            if (conn.getAutoCommit()) {
                conn.setAutoCommit(false);
            }

            usuarioDAOLogin = new UsuarioDAOLogin(conn);

            if (!usuarioDAOLogin.isUser(correo)) {
                new Gson().toJson(1, response.getWriter());
                throw new Exception();
            }
            pass = generatePassword();
            usuarioDAOLogin.updateHashPassword(pass, correo);
            correo co = new correo();
            co.recuperarCorreo(correo, pass);
            System.out.println("SEND---------------");
            System.out.println("-----");
            conn.commit();
            new Gson().toJson(3, response.getWriter());
        } catch (SQLException ex) {
            System.out.println("SQL ROLL BACK");
            try {
                conn.rollback();
            } catch (SQLException ex1) {
                ex1.printStackTrace();
            }
            new Gson().toJson(2, response.getWriter());
            ex.printStackTrace();

        } catch (Exception ex) {
            System.out.println("ROLL BACK");
            try {
                conn.rollback();
            } catch (SQLException ex1) {
                ex1.printStackTrace();
            }

            ex.printStackTrace();

        } finally {

            usuarioDAOLogin.CloseAll();

        }

    }

}
