/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.sena.mercado.dao;

import co.edu.sena.mercado.dto.*;
import co.edu.sena.mercado.util.Conexion;
import java.sql.*;
import java.util.*;

/**
 *
 * @author DELL
 */
public class rolUsuarioDAO {
    
   Conexion con=new Conexion();
   Connection cn;
   PreparedStatement ps;
   ResultSet rs;
   String consulta;
   rolDTO rolDTO=new rolDTO();
   ArrayList<rolDTO> listaRol=new ArrayList<>();
   
   public boolean registroRol (String rol){
       con=new Conexion();
   consulta="insert into rolusuario(nombre) values(?)";
       try {
           cn=con.getConnection();
           ps=cn.prepareStatement(consulta);
           ps.setString(1, rol);
           ps.executeUpdate();
          // System.out.println("registro rol realizado consulta "+ps.toString());
           return true;
       } catch (SQLException e) {
           System.out.println("error al registrar rol "+e);
           System.out.println("consulta "+ps.toString());
           return false;
       }
   }
   
   public ArrayList<rolDTO> listarRol(){
       listaRol=new ArrayList<>();
       con=new Conexion();
       consulta="select * from rolusuario";
       try {
           cn=con.getConnection();
           ps=cn.prepareStatement(consulta);
           rs=ps.executeQuery();
           while(rs.next()){
               rolDTO=new rolDTO();
               rolDTO.setIdRol(rs.getInt("idRol"));
               rolDTO.setRol(rs.getString("nombre"));
               listaRol.add(rolDTO);
           
           }
          // System.out.println(".........resultado "+listaRol.toString());
           //System.out.println("......... consulta "+ps.toString());
           return listaRol;
       } catch (SQLException e) {
            System.out.println(".........Error al listar el rol "+e);
           System.out.println("......... consulta"+ps.toString());
           return null;
        }finally{
            Conexion.close(cn);
            Conexion.close(ps);
            Conexion.close(rs);
        }
   }
}
