/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.sena.mercado.dao;

import co.edu.sena.mercado.dto.Banner;
import co.edu.sena.mercado.util.Conexion;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class BannerDAO {
    
    private Connection conn = null;
    private PreparedStatement ps = null;
    private ResultSet rs = null;

    public BannerDAO(Connection conn) {
        this.conn = conn;
    }
    
     public ArrayList<Banner> getMenu() {
        try {
            String sql = "SELECT idBaner, frase FROM banner ORDER BY idBaner asc LIMIT 3";
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();
            List<Banner> list = new ArrayList<Banner>();
            Banner banner;
            while (rs.next()) {
                banner = new Banner();
                banner.setIdBanner(rs.getString("idBaner"));
                banner.setFrase(rs.getString("frase"));
                list.add(banner);
            }
            return (ArrayList<Banner>) list;
        } catch (Exception e) {
            System.out.println(e);
            return null;
        }
    }
     
      public boolean editBanner(Banner banner) throws Exception {
        try {

            String sql = "UPDATE banner set frase = ? "
                    + "WHERE idBaner = ?";
            PreparedStatement ps = conn.prepareStatement(sql);

            ps.setString(1, banner.getFrase());
            ps.setString(2, banner.getIdBanner());

            int rows = ps.executeUpdate();
            boolean estado = rows > 0;
            return estado;
        } catch (Exception ex) {
            ex.printStackTrace();
            System.out.println("Error edit " + ex.getMessage());
            throw new Exception();
        }
    }
    
    public void CloseAll(){
        Conexion.close(conn);
        Conexion.close(ps);
        Conexion.close(rs);
    } 
    
}
