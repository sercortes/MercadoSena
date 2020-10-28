/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.sena.mercado.controller.actualizacion;

import co.edu.sena.mercado.dao.empresaDAO;
import co.edu.sena.mercado.dao.personaNaturalDAO;
import co.edu.sena.mercado.dao.usuarioDAO;
import co.edu.sena.mercado.dto.empresaDTO;
import co.edu.sena.mercado.dto.personaNaturalDTO;
import co.edu.sena.mercado.dto.usuarioDTO;
import co.edu.sena.mercado.util.codActivacion;
import co.edu.sena.mercado.util.correo;
import co.edu.sena.mercado.util.datosSesion;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

/**
 *
 * @author DELL
 */
public class actualizaUsuEmp extends HttpServlet {
    
    usuarioDAO usuarioDAO = new usuarioDAO();
    usuarioDTO usuarioDTO;
    personaNaturalDAO personaDAO = new personaNaturalDAO();
    personaNaturalDTO personaDTO;
    empresaDAO empresaDAO = new empresaDAO();
    empresaDTO empresaDTO = new empresaDTO();
    datosSesion datSesion = new datosSesion();
    codActivacion cod = new codActivacion();
    correo correo = new correo();
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        
    }
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
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
        String accion = request.getRequestURI();
        HttpSession sesion = (HttpSession) request.getSession();
        String accion2 = request.getParameter("accion");
        switch (accion2) {
            case "actualizarPersonas":
                
                personaDTO = new personaNaturalDTO();
                usuarioDTO = new usuarioDTO();
                usuarioDTO = (usuarioDTO) sesion.getAttribute("USER");
                personaDTO = usuarioDTO.getPersona();
                
                ArrayList<String> lista1 = new ArrayList<>();
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
                                System.out.println("XXXXXXXXXXXXXXXXXx");
                                File f = new File("/home/bienestar/Descargas/glassfish4/glassfish/domains/domain1/docroot/usuarios/" + usuarioDTO.getIdUsuario() + ".jpg");
                                if (f.exists() == true) {
                                    f.delete();
                                }
                                System.out.println(f.toString());
                                fileItem.write(f);
                                personaDTO.setUrlImg("http://181.48.181.131/usuarios/" + usuarioDTO.getIdUsuario() + ".jpg");
                            }
                        } else {
                            lista1.add(new String(fileItem.getString().getBytes("ISO-8859-1"), "UTF-8"));
                        }
                    }
                    personaDTO.setIdUsuario(usuarioDTO.getIdUsuario());
                    personaDTO.setNombrePer(lista1.get(1));
                    personaDTO.setApellidoPer(lista1.get(2));
                    personaDTO.setIdCiudad(Integer.parseInt(lista1.get(3)));
                    personaDTO.setIdTipoDoc(Integer.parseInt(lista1.get(4)));
                    personaDTO.setNumeroDocPer(lista1.get(5));
                    personaDTO.setIdGenero(Integer.parseInt((lista1.get(6))));
                    personaDTO.setNumCelularPer(lista1.get(7));
                    personaDTO.setTelPer(lista1.get(8));
                    personaDTO.setDireccionPer(lista1.get(9));
                    
                    empresaDTO.setNombreEmpresa(lista1.get(1));
                    empresaDTO.setIdCiudad(Integer.parseInt(lista1.get(3)));
                    empresaDTO.setCelEmpresa(lista1.get(7));
                    empresaDTO.setTelEmpresa(lista1.get(8));
                    empresaDTO.setDirEmpresa(lista1.get(9));
                    personaDAO.buscarCorreo(usuarioDTO.getIdUsuario());
                    empresaDTO.setCorreoEmpresa(personaDTO.getCorreoPer());
                    empresaDTO.setIdUsuario(usuarioDTO.getIdUsuario());
                    
                    empresaDTO empDAO = new empresaDAO().buscarEmpresa(usuarioDTO.getIdUsuario());
                    personaNaturalDTO perDAO = new personaNaturalDAO().buscarDocumenPerson(personaDTO.getNumeroDocPer(), personaDTO.getNumCelularPer());
                    if (personaDTO.getNumeroDocPer().equals(perDAO.getNumeroDocPer()) || personaDTO.getNumCelularPer().equals(perDAO.getNumCelularPer())) {
                        
                        if (perDAO.getNumeroDocPer().equals("") || perDAO.getNumCelularPer().equals("")) {
                            
                            personaDAO.actualizarPersona(personaDTO);
                            
                            if (empDAO.getEsEmpresa() < 1) {
                                
                                empresaDAO.actualizarEmpresa(empresaDTO, usuarioDTO.getIdUsuario());
                                
                            }
                            
                            response.getWriter().print(true);
                            sesion.removeAttribute("USER");
                            sesion.setAttribute("USER", datSesion.consultarDatos(usuarioDTO));
                            
                        } else if (personaDTO.getIdPer() == perDAO.getIdPer()) {
                            
                            personaDAO.actualizarPersona(personaDTO);
                            
                            if (empDAO.getEsEmpresa() < 1) {
                                
                                empresaDAO.actualizarEmpresa(empresaDTO, usuarioDTO.getIdUsuario());
                                
                            }
                            response.getWriter().print(true);
                            sesion.removeAttribute("USER");
                            sesion.setAttribute("USER", datSesion.consultarDatos(usuarioDTO));
                            
                        } else {
                            response.getWriter().print(false);
                        }
                        
                    } else {
                        
                        if (personaDAO.actualizarPersona(personaDTO)) {
                            
                            if (empDAO.getEsEmpresa() < 1) {
                                
                                empresaDAO.actualizarEmpresa(empresaDTO, usuarioDTO.getIdUsuario());
                                
                            }
                            
                            response.getWriter().print(true);
                            sesion.removeAttribute("USER");
                            sesion.setAttribute("USER", datSesion.consultarDatos(usuarioDTO));
                            
                        } else {
                            response.getWriter().print(false);
                        }
                    }
                    
                } catch (Exception e) {
                    System.out.println(e);
                    response.getWriter().print(false);
                }
                
                break;
            case "actualizarUsuarios":
                
                usuarioDTO = new usuarioDTO();
                usuarioDTO = (usuarioDTO) sesion.getAttribute("USER");
                usuarioDTO.setClaveUsu(request.getParameter("clave1"));
                if (usuarioDAO.actualizarUsuario(usuarioDTO)) {
                    
                    response.getWriter().print(true);
                    
                } else {
                    response.getWriter().print(false);
                }
                break;
            case "actualizarEmpresa":
                
                empresaDTO = new empresaDTO();
                usuarioDTO = (usuarioDTO) sesion.getAttribute("USER");
                
                empresaDTO.setNombreEmpresa(request.getParameter("nombreEmpresa"));
                empresaDTO.setCelEmpresa(request.getParameter("celularEmpresa"));
                empresaDTO.setTelEmpresa(request.getParameter("telefonoEmpresa"));
                empresaDTO.setCorreoEmpresa(request.getParameter("correoEmpresa"));
                empresaDTO.setDirEmpresa(request.getParameter("direccionEmpresa"));
                empresaDTO.setIdCiudad(Integer.parseInt(request.getParameter("idCiudadEmpresa")));
                empresaDTO.setEsEmpresa(1);
                empresaDTO.setIdUsuario(usuarioDTO.getIdUsuario());
                
                empresaDTO emDTO = new empresaDAO().buscarEmpresa(usuarioDTO.getIdUsuario());
                
                if (emDTO.getIdEmpresa() > 0) {

                    //de la sesion
                    if (empresaDAO.actualizarEmpresa(empresaDTO, usuarioDTO.getIdUsuario())) {

                        // sesion.removeAttribute("USER");
                        response.getWriter().print(true);
                        
                        usuarioDTO = datSesion.consultarDatos(usuarioDTO);
                        sesion.setAttribute("USER", usuarioDTO);
                    } else {
                        response.getWriter().print(false);
                    }
                    
                } else {
                    
                    if (empresaDAO.registroEmpresa(empresaDTO, usuarioDTO.getIdUsuario())) {

                        // sesion.removeAttribute("USER");
                        response.getWriter().print(true);
                        
                        usuarioDTO = datSesion.consultarDatos(usuarioDTO);
                        sesion.setAttribute("USER", usuarioDTO);
                    } else {
                        response.getWriter().print(false);
                    }
                    
                }
                break;
            case "actualizaDatosFaltantes":
                
                personaDTO = new personaNaturalDTO();
                empresaDTO = new empresaDTO();
                usuarioDTO = (usuarioDTO) sesion.getAttribute("USER");
                
                personaDTO.setIdUsuario(usuarioDTO.getIdUsuario());
                personaDTO.setNumeroDocPer(request.getParameter("documentoUsuario"));
                personaDTO.setNumCelularPer(request.getParameter("celularUsuario"));
                personaDTO.setTelPer(request.getParameter("telefonoUsuario"));
                personaDTO.setDireccionPer(request.getParameter("direccionUsuario"));
                
                empresaDTO.setCelEmpresa(request.getParameter("celularUsuario"));
                empresaDTO.setTelEmpresa(request.getParameter("telefonoUsuario"));
                empresaDTO.setDirEmpresa(request.getParameter("direccionUsuario"));
                empresaDTO.setIdUsuario(usuarioDTO.getIdUsuario());
                
                empresaDTO emDTOs = new empresaDAO().buscarEmpresa(usuarioDTO.getIdUsuario());
                personaNaturalDTO perDAOs = new personaNaturalDAO().getDataById(Integer.toString(usuarioDTO.getIdUsuario()));
                personaNaturalDTO perDAo = new personaNaturalDAO().buscarDocumenPerson(personaDTO.getNumeroDocPer(), personaDTO.getNumCelularPer());
                
                if (emDTOs.getEsEmpresa() < 1) {
                    
                    if (perDAOs.getNumCelularPer() == null || perDAOs.getTelPer() == null || perDAOs.getNumeroDocPer() == null) {
                        
                        if (personaDTO.getNumeroDocPer().equals(perDAo.getNumeroDocPer()) && perDAo.getIdUsuario() == emDTOs.getIdUsuario() || perDAo.getNumeroDocPer() == null) {
                            
                            if (perDAo.getIdUsuario() == 0 || perDAo.getNumCelularPer().equals(0) || perDAo.getTelPer().equals(0)) {
                                
                                if (empresaDAO.actualizarDatosFaltantes(empresaDTO, usuarioDTO.getIdUsuario())) {

                                    // sesion.removeAttribute("USER");
                                    personaDAO.actualizarDatosFaltantes(personaDTO, usuarioDTO.getIdUsuario());
                                    response.getWriter().print(true);
                                    
                                    usuarioDTO = datSesion.consultarDatos(usuarioDTO);
                                    sesion.setAttribute("USER", usuarioDTO);
                                } else {
                                    response.getWriter().print(false);
                                }
                                
                            } else if (perDAo.getNumCelularPer().equals("") || perDAo.getTelPer().equals("")) {
                                if (empresaDAO.actualizarDatosFaltantes(empresaDTO, usuarioDTO.getIdUsuario())) {

                                    // sesion.removeAttribute("USER");
                                    personaDAO.actualizarDatosFaltantes(personaDTO, usuarioDTO.getIdUsuario());
                                    response.getWriter().print(true);
                                    
                                    usuarioDTO = datSesion.consultarDatos(usuarioDTO);
                                    sesion.setAttribute("USER", usuarioDTO);
                                } else {
                                    response.getWriter().print(false);
                                }
                            } else {
                                response.getWriter().print(false);
                            }
                            
                        } else {
                            response.getWriter().print(false);
                        }
                        
                    } else if (perDAOs.getNumCelularPer().equals("") || perDAOs.getTelPer().equals("") || perDAOs.getNumeroDocPer().equals("")) {
                        
                        if (personaDTO.getNumeroDocPer().equals(perDAo.getNumeroDocPer()) && perDAo.getIdUsuario() == emDTOs.getIdUsuario() || perDAo.getNumeroDocPer().equals("")) {
                            
                            if (perDAo.getIdUsuario() == 0 || perDAo.getNumCelularPer().equals(0) || perDAo.getTelPer().equals(0)) {
                                
                                if (empresaDAO.actualizarDatosFaltantes(empresaDTO, usuarioDTO.getIdUsuario())) {

                                    // sesion.removeAttribute("USER");
                                    personaDAO.actualizarDatosFaltantes(personaDTO, usuarioDTO.getIdUsuario());
                                    response.getWriter().print(true);
                                    
                                    usuarioDTO = datSesion.consultarDatos(usuarioDTO);
                                    sesion.setAttribute("USER", usuarioDTO);
                                } else {
                                    response.getWriter().print(false);
                                }
                                
                            } else if (perDAo.getNumCelularPer().equals("") || perDAo.getTelPer().equals("") || perDAo.getNumeroDocPer().equals("")) {
                                if (empresaDAO.actualizarDatosFaltantes(empresaDTO, usuarioDTO.getIdUsuario())) {

                                    // sesion.removeAttribute("USER");
                                    personaDAO.actualizarDatosFaltantes(personaDTO, usuarioDTO.getIdUsuario());
                                    response.getWriter().print(true);
                                    
                                    usuarioDTO = datSesion.consultarDatos(usuarioDTO);
                                    sesion.setAttribute("USER", usuarioDTO);
                                } else {
                                    response.getWriter().print(false);
                                }
                            } else {
                                response.getWriter().print(false);
                            }
                            
                        } else {
                            response.getWriter().print(false);
                        }
                    }
                    
                } else {
                    System.out.println("no entro");
                }
                break;
            case "recuperarClave":
                String clave = cod.generarCod();
                usuarioDTO = new usuarioDTO();
                String documento = request.getParameter("numeroDocAct");
                String tipoDoc = request.getParameter("tipoDocUsuarioRec");
                usuarioDTO = personaDAO.buscarRecu(documento, tipoDoc);
                if (usuarioDTO != null) {
                    usuarioDTO.setClaveUsu(clave);
                    System.out.println("..................usuario " + usuarioDTO);
                    if (usuarioDAO.actualizarUsuario(usuarioDTO)) {
                        if (correo.correoRec(usuarioDTO)) {
                            response.getWriter().print(true);
                        } else {
                            response.getWriter().print(false);
                        }
                    } else {
                        response.getWriter().print(false);
                    }
                } else {
                    response.getWriter().print(false);
                }
                
                break;
            default:
                throw new AssertionError("xxxxxxxxxxxxxxxxxxxxxx esa accion no existe");
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
