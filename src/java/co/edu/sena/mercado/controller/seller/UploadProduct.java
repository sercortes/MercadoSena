package co.edu.sena.mercado.controller.seller;

import co.edu.sena.mercado.dao.ImagenesProductosDAO;
import co.edu.sena.mercado.dao.ProductoDAO;
import co.edu.sena.mercado.dto.ImagenesProducto;
import co.edu.sena.mercado.dto.Producto;
import co.edu.sena.mercado.util.Conexion;
import com.google.gson.Gson;
import com.mysql.jdbc.StringUtils;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.util.Streams;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.io.IOUtils;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

/**
 *
 * @author serfin
 */
public class UploadProduct extends HttpServlet {
    
    private final String UPLOAD_DIRECTORY = "/opt/lampp/htdocs/sergio";
    private final String SERVER_UPLOAD = "http://localhost/sergio/";
    private static final long serialVersionUID = 1L;
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, UnsupportedEncodingException {
        
        response.setContentType("application/json");
        try {
            new Gson().toJson(uploadForm(request, response), response.getWriter());
        } catch (SQLException ex) {
            Logger.getLogger(UploadProduct.class.getName()).log(Level.SEVERE, null, ex);
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

    private boolean uploadForm(HttpServletRequest request, HttpServletResponse response)
            throws UnsupportedEncodingException, IOException, SQLException {
        
       
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        
        boolean codigo = false;
        String folder = "";
        
        //Objeto producto para la bd
        //lista contiene las rutas de las imagenes
        Producto producto = new Producto();
        List<ImagenesProducto> lista = new ArrayList<>();

        // instancia clase conexión
        Conexion conexion = new Conexion();
        Connection cone = conexion.getConnection();
        
        ProductoDAO productoDAO = new ProductoDAO(cone);
        ImagenesProductosDAO imagenesProductosDAO = new ImagenesProductosDAO(cone);
        
        // desactivando en autocommit
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
                
                producto.setIdEmpresaFK("1");
    
                //Insert Producto
                folder = Integer.toString(productoDAO.insertReturn(producto));
              
                for (FileItem item : multiparts) {
                    
                    if (!item.isFormField()) {
                        // writen files and get List images
                        lista = getLista(item, folder, (ArrayList<ImagenesProducto>) lista);
                    }
                    
                }
                
                // insert imagenes productos
                for (ImagenesProducto item : lista) {
                    imagenesProductosDAO.insertReturn(item);
                }
                
                cone.commit();
                codigo = true;
                
            } catch (Exception e) {
                
                cone.rollback();
                codigo = false;
                System.out.println(e);
                
            } 
            
        } else {
            
            codigo = false;
            
        }
        
        return codigo;
        
    }
    
    private Producto getParams(FileItem item, Producto producto) throws UnsupportedEncodingException {
        
        switch (item.getFieldName()) {
            
            case "name":
                producto.setNombreProducto(item.getString("UTF-8"));
                break;
            case "fechaV":
                if (StringUtils.isEmptyOrWhitespaceOnly(item.getString("UTF-8"))) {
                      //  producto.setFechaVencimiento("2020-01-01");
                }else{
                        producto.setFechaVencimiento(item.getString("UTF-8"));
                }
                break;
            case "price":
                producto.setValorProducto(Double.parseDouble(item.getString("UTF-8")));
                break;
            case "cantidad":
                producto.setStockProducto(Integer.parseInt(item.getString("UTF-8")));
                break;
            case "marca":
                producto.setMarcaProducto(item.getString("UTF-8"));
                break;
            case "category":
                producto.setIdCategoriaFK(item.getString("UTF-8"));
                break;
            case "descrip":
                producto.setDescripcionProducto(item.getString("UTF-8"));
                break;
            
        }
        
        return producto;
        
    }
    
    public List<ImagenesProducto> getLista(FileItem item, String folder, ArrayList<ImagenesProducto> lista) throws Exception {
        
        ImagenesProducto imagenesProducto;
        
        String name = new File(item.getName()).getName();
        File tempFile = new File(UPLOAD_DIRECTORY + File.separator + folder + File.separator + name);
        
        if (tempFile.exists()) {
            tempFile.delete();
        }
        
        item.write(tempFile);
        
        imagenesProducto = new ImagenesProducto();
        imagenesProducto.setUrl(SERVER_UPLOAD + folder + File.separator + tempFile.getName());
        imagenesProducto.setIdProductoFK(folder);
        lista.add(imagenesProducto);
        
        return lista;
        
    }
    
}