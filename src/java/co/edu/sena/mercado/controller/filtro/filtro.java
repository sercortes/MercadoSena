/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.sena.mercado.controller.filtro;

import co.edu.sena.mercado.dao.ImagenesProductosDAO;
import co.edu.sena.mercado.dao.ProductoDAO;
import co.edu.sena.mercado.dao.ProductosPedidosDAO;
import co.edu.sena.mercado.dao.empresaDAO;
import co.edu.sena.mercado.dto.Centro;
import co.edu.sena.mercado.dto.ImagenesProducto;
import co.edu.sena.mercado.dto.MarcaDTO;
import co.edu.sena.mercado.dto.Producto;
import co.edu.sena.mercado.dto.empresaDTO;
import co.edu.sena.mercado.dto.productoImagenesDTO;
import co.edu.sena.mercado.dto.productoPedidosDTO;
import co.edu.sena.mercado.dto.productosMasSolicitadosDTO;
import co.edu.sena.mercado.util.Conexion;
import com.google.gson.Gson;
import java.io.IOException;
import java.sql.Connection;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author DELL
 */
public class filtro extends HttpServlet {

    ArrayList<productoPedidosDTO> listaProcPed = new ArrayList<>();
    ArrayList<productosMasSolicitadosDTO> listaProdMasSol = new ArrayList<>();
    ArrayList<ImagenesProducto> listaImagenes = new ArrayList<>();
    productoPedidosDTO prodPedido = new productoPedidosDTO();
    productosMasSolicitadosDTO prodMasPedido = new productosMasSolicitadosDTO();
    ArrayList<Producto> listaProductos = new ArrayList<>();
    Producto productoDTO;
    ArrayList<productoImagenesDTO> listaProductoImagenes = new ArrayList<>();
    productoImagenesDTO productoImagenesDTO;

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");

    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        String accion = request.getParameter("accion");
        switch (accion) {
            case "producMasSolicitados":
                listaImagenes = new ArrayList<>();
                listaProcPed = new ArrayList<>();
                listaProdMasSol = new ArrayList<>();
                Conexion conexion = new Conexion();
                Connection conn = conexion.getConnection();
                ProductosPedidosDAO productosPedidosDAO = new ProductosPedidosDAO(conn);

                listaProcPed = productosPedidosDAO.produstosMasSolicitados();

                int cantidad = listaProcPed.size();
                if (cantidad > 10) {
                    cantidad = 10;
                    for (int i = 0; i < cantidad; i++) {

                        prodPedido = new productoPedidosDTO();
                        prodPedido = listaProcPed.get(i);
                        listaProcPed.add(i, prodPedido);

                    }
                }
                for (int i = 0; i < cantidad; i++) {
                    conexion = new Conexion();
                    Connection con = conexion.getConnection();
                    prodPedido = new productoPedidosDTO();
                    prodMasPedido = new productosMasSolicitadosDTO();
                    ImagenesProductosDAO imagenesProdDAO = new ImagenesProductosDAO(con);
                    prodPedido = listaProcPed.get(i);
                    listaImagenes = imagenesProdDAO.getImagenesByProduc(prodPedido.getIdProductoFK());

                    prodMasPedido.setProductoPedidoDTO(prodPedido);
                    prodMasPedido.setListaImagenes(listaImagenes);
                    listaProdMasSol.add(prodMasPedido);

                    imagenesProdDAO.CloseAll();

                }
                response.setContentType("application/json");
                new Gson().toJson(listaProdMasSol, response.getWriter());

                productosPedidosDAO.CloseAll();
                break;
            case "consultarTodosProductos":
                listaImagenes = new ArrayList<>();
                ArrayList<ImagenesProducto> listaTempImagenes = new ArrayList<>();
                listaProductoImagenes = new ArrayList<>();
                listaProductos = new ArrayList<>();
                Conexion con = new Conexion();
                Connection c = con.getConnection();
                ProductoDAO productoDAO = new ProductoDAO(c);
//                Conexion co=new Conexion();
//                Connection cio=co.getConnection();
                ImagenesProductosDAO imagenesDAO = new ImagenesProductosDAO(c);
//                listaProductos = productoDAO.todosProductosConVendedor();
                listaImagenes = imagenesDAO.consultarTodas();

                for (Producto productosDTO : listaProductos) {
                    productoImagenesDTO = new productoImagenesDTO();
                    listaTempImagenes = new ArrayList<>();
                    for (ImagenesProducto imagenesDTO : listaImagenes) {
                        if (productosDTO.getIdProducto().equals(imagenesDTO.getIdProductoFK())) {
                            listaTempImagenes.add(imagenesDTO);
                        }
                    }
                    productoImagenesDTO.setImagenes(listaTempImagenes);
                    productoImagenesDTO.setProducto(productosDTO);
                    listaProductoImagenes.add(productoImagenesDTO);
                }
                response.setContentType("application/json");
                new Gson().toJson(listaProductoImagenes, response.getWriter());
                break;
            case "listarMarcas":
                
                listaMarcas(request, response);

                break;

            default:
                System.out.println("ACCION SERVLET FILTRO NO EXISTE");
               
        }

    }

    private void listaMarcas(HttpServletRequest request, HttpServletResponse response) throws IOException {
        
                empresaDAO empresaDAO = new empresaDAO();
                ArrayList<MarcaDTO> listaEmpresa = new ArrayList<>();
                listaEmpresa = empresaDAO.listarMarcas();
                response.setContentType("application/json");
                new Gson().toJson(listaEmpresa, response.getWriter());
        
    }
    
        @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
