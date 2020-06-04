/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.sena.mercado.dao;

import co.edu.sena.mercado.dto.tipoDocumentoDTO;
import co.edu.sena.mercado.util.Conexion;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author DELL
 */
public class tipoDocumentoDAO {
     Conexion con=new Conexion();
   Connection cn;
   PreparedStatement ps;
   ResultSet rs;
   String consulta;
   tipoDocumentoDTO tipoDocumentoDTO=new tipoDocumentoDTO();
   ArrayList<tipoDocumentoDTO> listaTipoDocumento=new ArrayList<>();
   
   public boolean registroRol (tipoDocumentoDTO tipoDocumento){
       con=new Conexion();
   consulta="INSERT INTO tipodocumento( nombreTipoDoc) VALUES (?)";
       try {
           cn=con.getConnection();
           ps=cn.prepareStatement(consulta);
           ps.setString(1,tipoDocumento.getTipoDoc());
           ps.executeUpdate();
           //System.out.println("registro tipoDocumento realizado consulta "+ps.toString());
           return true;
       } catch (SQLException e) {
           System.out.println("error al registrar tipoDocumento "+e);
           System.out.println("consulta "+ps.toString());
           return false;
       }
   }
   
     public ArrayList<tipoDocumentoDTO> listarTipoDoc(){
       listaTipoDocumento=new ArrayList<>();
       con=new Conexion();
       consulta="select * from tipodocumento";
       try {
           cn=con.getConnection();
           ps=cn.prepareStatement(consulta);
           rs=ps.executeQuery();
           while(rs.next()){
              tipoDocumentoDTO=new tipoDocumentoDTO();
              tipoDocumentoDTO.setIdTipoDoc(rs.getInt("idTipoDoc"));
              tipoDocumentoDTO.setTipoDoc(rs.getString("nombreTipoDoc"));
              listaTipoDocumento.add(tipoDocumentoDTO);
           
           }
          // System.out.println(".........resultado "+listaTipoDocumento.toString());
          // System.out.println("......... consulta "+ps.toString());
           return listaTipoDocumento;
       } catch (SQLException e) {
            System.out.println(".........Error al listar el tipoDocumento "+e);
           System.out.println("......... consulta"+ps.toString());
           return null;
        }finally{
            Conexion.close(cn);
            Conexion.close(ps);
            Conexion.close(rs);
        }
   }
   
}
