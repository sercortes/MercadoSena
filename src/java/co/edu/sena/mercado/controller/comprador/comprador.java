/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.sena.mercado.controller.comprador;

import co.edu.sena.mercado.dao.ciudadDAO;
import co.edu.sena.mercado.dao.personaNaturalDAO;
import co.edu.sena.mercado.dao.*;
import co.edu.sena.mercado.dto.ciudadDTO;
import co.edu.sena.mercado.dto.personaNaturalDTO;
import co.edu.sena.mercado.dto.*;
import com.google.gson.Gson;
import java.io.IOException;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author DELL
 */
public class comprador extends HttpServlet {
    
    personaNaturalDAO personaNaturalDAO=new personaNaturalDAO();
    personaNaturalDTO personaNaturalDTO=new personaNaturalDTO();
    ArrayList<personaNaturalDTO> listaPersona=new ArrayList<>();
    ciudadDAO ciudadDAO=new ciudadDAO();
    ciudadDTO ciudadDTO= new ciudadDTO();
    ArrayList<ciudadDTO> listaCiudad=new ArrayList<>();
    tipoDocumentoDAO tipoDocDAO=new tipoDocumentoDAO();
    tipoDocumentoDTO tipoDocDTO =new tipoDocumentoDTO();
    ArrayList<tipoDocumentoDTO> listaTipoDoc=new ArrayList<>();
    generoDAO generoDAO =new generoDAO();
    generoDTO generoDTO=new generoDTO();
    ArrayList<generoDTO> listaGenero=new ArrayList<>();
    rolUsuarioDAO rolDAO =new rolUsuarioDAO();
    rolDTO rolDTO=new rolDTO();
    ArrayList<rolDTO> listaRol=new ArrayList<>();
    usuarioDTO usuarioDTO =new usuarioDTO();
    usuarioDAO usuarioDAO=new usuarioDAO();
    ArrayList<usuarioDTO> listaUsuario=new ArrayList<>();
    
    
    

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
       
     
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        
        
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
        
        String accion=request.getParameter("accion");
        switch(accion){
            case "consultaTipoDoc":
                listaTipoDoc=new ArrayList<>();
                listaTipoDoc=tipoDocDAO.listarTipoDoc();
                System.out.println( new Gson().toJson(listaTipoDoc));
                 new Gson().toJson(listaTipoDoc, response.getWriter());
                
                break;
            case "consultaGenero":
                listaGenero=new ArrayList<>();
                listaGenero=generoDAO.listarGenero();
                new Gson().toJson(listaGenero,response.getWriter());
                break;
            case "consultaCiudad":
                listaCiudad=new ArrayList<>();
                listaCiudad=ciudadDAO.listarCiudad();
                new Gson().toJson(listaCiudad, response.getWriter());
                break;
            case "consultaRol":
                listaRol=rolDAO.listarRol();
                new Gson().toJson(listaRol, response.getWriter());
                break;
            case "registrarUsuario":
                usuarioDTO =new usuarioDTO();
                personaNaturalDTO=new personaNaturalDTO();
                //datos usuario
                usuarioDTO.setClaveUsu(request.getParameter("clave1"));
                usuarioDTO.setCorreoUsu(request.getParameter("correoUsuario"));
                usuarioDTO.setEstadoUsu("0");
                usuarioDTO.setIdRol(Integer.parseInt(request.getParameter("rol")));
                
                if(usuarioDAO.registroUsuario(usuarioDTO)){
                    //datos persona
                personaNaturalDTO.setApellidoPer(request.getParameter("apellidoUsuario"));
                personaNaturalDTO.setCorreoPer(request.getParameter("correoUsuario"));
                personaNaturalDTO.setDireccionPer(request.getParameter("direccionUsuario"));
                personaNaturalDTO.setIdCiudad(Integer.parseInt(request.getParameter("ciudadUsuario")));
                personaNaturalDTO.setIdGenero(Integer.parseInt(request.getParameter("generoUsuario")));
                personaNaturalDTO.setIdTipoDoc(Integer.parseInt(request.getParameter("tipoDocUsuario")));
                personaNaturalDTO.setNumCelularPer(request.getParameter("celularUsuario"));
                personaNaturalDTO.setNombrePer(request.getParameter("nombreUsuario"));
                personaNaturalDTO.setNumeroDocPer(request.getParameter("documentoUsuario"));
                personaNaturalDTO.setTelPer(request.getParameter("telefonoUsuario"));
                
                usuarioDTO=usuarioDAO.buscarUsuario(personaNaturalDTO.getCorreoPer(), usuarioDTO.getClaveUsu());
                personaNaturalDTO.setIdUsuario(usuarioDTO.getIdUsuario());
                
                if(personaNaturalDAO.registrarPersona(personaNaturalDTO)){
                    //enviar correo
                    response.getWriter().print(true);
                }else{
                    usuarioDAO.eliminarUsuario(personaNaturalDTO.getCorreoPer(), usuarioDTO.getClaveUsu());
                     response.getWriter().print(false);
                }
                }else{
                response.getWriter().print(false);
                usuarioDTO= new usuarioDTO();
                }
                
                //System.out.println("......."+usuarioDTO.toString());

                break;
           
                default:
                    throw new AssertionError("Esa accion no existe");
                
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
