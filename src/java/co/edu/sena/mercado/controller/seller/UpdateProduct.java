/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.sena.mercado.controller.seller;

import co.edu.sena.mercado.dao.ImagenesProductosDAO;
import co.edu.sena.mercado.dao.ProductoDAO;
import co.edu.sena.mercado.dto.ImagenesProducto;
import co.edu.sena.mercado.dto.Producto;
import co.edu.sena.mercado.dto.usuarioDTO;
import co.edu.sena.mercado.util.Conexion;
import com.google.gson.Gson;
import com.mysql.jdbc.StringUtils;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.URL;
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
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.FilenameUtils;

/**
 *
 * @author serfin
 */
public class UpdateProduct extends HttpServlet {

    private final String UPLOAD_DIRECTORY = "/home/equipo/servers/apache-tomcat-8.0.27/webapps/ROOT/filess/";
    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        System.out.println("ACCEDER A UPDATE_PRODUCT POR GET");
        response.sendRedirect(request.getContextPath() + "/home");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, UnsupportedEncodingException {

        response.setContentType("application/json");

        if (request.getSession().getAttribute("USER") != null) {

            try {
                
                new Gson().toJson(updateForm(request, response), response.getWriter());
                
            } catch (SQLException ex) {
                System.out.println("=D"+ex);
                Logger.getLogger(UpdateProduct.class.getName()).log(Level.SEVERE, null, ex);
            }
            
        } else {
            
            System.out.println("NO valores sesión update product");
            response.sendRedirect(request.getContextPath() + "/home");
            
        }
    }

    private boolean updateForm(HttpServletRequest request, HttpServletResponse response) throws SQLException, UnsupportedEncodingException {

        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        
        usuarioDTO user = (usuarioDTO) request.getSession().getAttribute("USER");
        System.out.println("UPDATE PRODUCT");
        System.out.println(user.toString());

        int hasFiles = Integer.parseInt(request.getParameter("files"));

        boolean codigo = false;
        String folder = "";

        //Objeto producto para la bd
        //lista contiene las rutas de las imagenes
        Producto producto = new Producto();
        List<ImagenesProducto> lista = new ArrayList<>();
        List<ImagenesProducto> listaExistentes = new ArrayList<>();

        // instancia clase conexión
        Conexion conexion = new Conexion();
        Connection cone = conexion.getConnection();

        ProductoDAO productoDAO = new ProductoDAO(cone);
        ImagenesProductosDAO imagenesProductosDAO = new ImagenesProductosDAO(cone);
        //  desactivando en autocommit
        if (cone.getAutoCommit()) {
            cone.setAutoCommit(false);
        }
        if (ServletFileUpload.isMultipartContent(request)) {

            try {

                List<FileItem> multiparts = new ServletFileUpload(
                        new DiskFileItemFactory()).parseRequest(request);

                for (FileItem item : multiparts) {

                    if (item.isFormField()) {
                        //reading others inputs
                        producto = getParams(item, producto);
                    }

                }

                System.out.println(producto.toString());
                System.out.println("");
                folder = producto.getIdProducto();
                productoDAO.updateProduct(producto);

                if (hasFiles == 1) {

                    listaExistentes = imagenesProductosDAO.getImagenesByProducTotal(folder);

                    for (FileItem item : multiparts) {

                        if (!item.isFormField()) {
                            // writen files and get List images
                            lista = getLista(item, folder, (ArrayList<ImagenesProducto>) lista);
                        }

                    }

                    System.out.println("LImpiar " + request.getParameter("clean"));
                    boolean estado = request.getParameter("clean").equalsIgnoreCase("1");

                    if (lista.size() + listaExistentes.size() > 5 || estado) {

                        File tempFile;

                        URL url;
                        for (ImagenesProducto item : listaExistentes) {

                            url = new URL(item.getUrl());
                            String nameFile = FilenameUtils.getName(url.getPath());
                            tempFile = new File(UPLOAD_DIRECTORY + File.separator + folder + File.separator + nameFile);

                            if (tempFile.exists()) {
                                tempFile.delete();
                            }

                            imagenesProductosDAO.deleteByidImagen(item.getIdImagen());
                        }

                    }

                    // insert imagenes productos
                    for (ImagenesProducto item : lista) {
                        imagenesProductosDAO.insertReturn(item);
                    }

                }

                cone.commit();
                codigo = true;
                System.out.println("ACTUALIZADO UPDATE PROUCTS");

            } catch (Exception e) {
                
                e.printStackTrace();
                cone.rollback();
                codigo = false;
                System.out.println("ROLL BACK EDITAR PRODUCTO");

            } finally {
                
                productoDAO.CloseAll();
                imagenesProductosDAO.CloseAll();
                
            }

        } else {

            codigo = false;

        }

        return codigo;

    }

    private Producto getParams(FileItem item, Producto producto) throws UnsupportedEncodingException {

        switch (item.getFieldName()) {

            case "idProductoEs":
                producto.setIdProducto(item.getString("UTF-8"));
                break;
            case "nameE":
                producto.setNombreProducto(item.getString("UTF-8"));
                break;
            case "referencia":
                  producto.setReferencia(item.getString("UTF-8"));
               break;
            case "priceE":
                producto.setValorProducto(Double.parseDouble(item.getString("UTF-8")));
                break;
            case "cantidadE":
                producto.setStockProducto(Integer.parseInt(item.getString("UTF-8")));
                break;
            case "marcaE":
                producto.setMarcaProducto(item.getString("UTF-8"));
                break;
            case "categoryE":
                producto.setIdCategoriaFK(item.getString("UTF-8"));
                break;
            case "descripE":
                producto.setDescripcionProducto(item.getString("UTF-8"));
                break;
            case "enviosE":
               producto.setDiasEnvios(item.getString("UTF-8").trim());
                break;
            case "medidasE":
                 producto.setMedidaProducto(item.getString("UTF-8").trim());
                break;
            case "precioVendedor":
                producto.setPrecioVendedor(Double.parseDouble(item.getString("UTF-8").trim()));
                break;
            case "garantia":
                producto.setGarantia(item.getString("UTF-8").trim());
                break;
            case "empaqueE":
                if (StringUtils.isEmptyOrWhitespaceOnly(item.getString("UTF-8"))) {
                    //  producto.setFechaVencimiento("2020-01-01");
                } else {
                    producto.setEmpaqueProducto(item.getString("UTF-8"));
                }
                break;

            case "embalajeE":
                if (StringUtils.isEmptyOrWhitespaceOnly(item.getString("UTF-8"))) {
                    //  producto.setFechaVencimiento("2020-01-01");
                } else {
                    producto.setEmbalajeProducto(item.getString("UTF-8"));
                }
                break;
            case "ventajasE":
               producto.setVentajaProducto(item.getString("UTF-8").trim());
                break;

        }

        return producto;

    }

    public List<ImagenesProducto> getLista(FileItem item, String folder, ArrayList<ImagenesProducto> lista
    ) throws Exception {

        ImagenesProducto imagenesProducto;

        String name = new File(item.getName()).getName();
        File tempFile = new File(UPLOAD_DIRECTORY + File.separator + folder + File.separator + name);

        if (StringUtils.isNullOrEmpty(name)) {
            throw new Exception();
        }

        if (tempFile.exists()) {
            tempFile.delete();
        }

        item.write(tempFile);

        imagenesProducto = new ImagenesProducto();
        imagenesProducto.setUrl(folder + File.separator + tempFile.getName());
        imagenesProducto.setIdProductoFK(folder);
        lista.add(imagenesProducto);

        return lista;

    }

    
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

    
}
