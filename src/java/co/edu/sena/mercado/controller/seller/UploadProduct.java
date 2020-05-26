package co.edu.sena.mercado.controller.seller;

import com.google.gson.Gson;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
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

    private final String UPLOAD_DIRECTORY = "/home/serfin/Documentos";
    private static final long serialVersionUID = 1L;
   
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        response.setContentType("application/json");
        new Gson().toJson(uploadForm(request, response), response.getWriter());
        
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
               throws UnsupportedEncodingException, IOException {
        
           request.setCharacterEncoding("UTF-8");
        
        boolean codigo = false;
          
        if (ServletFileUpload.isMultipartContent(request)) {
            
            try{
            
                List<FileItem> multiparts = new ServletFileUpload(new DiskFileItemFactory()).parseRequest(request);
                
                for(FileItem item : multiparts){
                    
                    if (item.isFormField()) {
                        //reading others inputs
                            System.out.println(item.getFieldName());
                            System.out.println(item.getString());
                    }
                    
                    if (!item.isFormField()) {
                        String name = new File(item.getName()).getName();
                        item.write(new File(UPLOAD_DIRECTORY + File.separator + name));
                    }
                    
                }
                
                codigo = true;
                
            }catch(Exception e){
                
                System.out.println(e);
                codigo = false;
                
            }
            
        }else{
        
            codigo = false;
            
        }
        
            return codigo;
        
    }
    
}