/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.sena.mercado.controller.seller;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
        
        if (ServletFileUpload.isMultipartContent(request)) {
            
            try{
            
                List<FileItem> multiparts = new ServletFileUpload(new DiskFileItemFactory()).parseRequest(request);
                
                for(FileItem item : multiparts){
                    
                    if (!item.isFormField()) {
                        String name = new File(item.getName()).getName();
                        item.write(new File(UPLOAD_DIRECTORY + File.separator + name));
                    }
                    
                }
                
                request.setAttribute("MESSAGE", "File upload successfully.");
                
            }catch(Exception e){
                
                request.setAttribute("MESSAGE", "File upload failed due to "+e);
                
            }
            
        }else{
        
            request.setAttribute("MESSAGE", "Sorry this servlet only handles upload request.");
            
        }
        
        request.getRequestDispatcher("/views/AdminSeller/products/addProduct.jsp").forward(request, response);
        
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
