/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.sena.mercado.controller.checkBuy;

import co.edu.sena.mercado.controller.consumer.CRestPse;
import co.edu.sena.mercado.dao.VentaDAO;
import co.edu.sena.mercado.dto.Producto;
import co.edu.sena.mercado.dto.VentaDTO;
import co.edu.sena.mercado.dto.pagoJSON;
import co.edu.sena.mercado.util.Conexion;
import com.google.gson.Gson;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.MalformedURLException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

@WebListener
public class every implements ServletContextListener {

    private ScheduledExecutorService scheduler;

    @Override
    public void contextInitialized(ServletContextEvent event) {
        scheduler = Executors.newSingleThreadScheduledExecutor();
        scheduler.scheduleAtFixedRate(new SomeQuarterlyJob(), 0, 10, TimeUnit.MINUTES);
    }

    @Override
    public void contextDestroyed(ServletContextEvent event) {
        scheduler.shutdownNow();
    }

    public class SomeQuarterlyJob implements Runnable {

        @Override
        public void run() {
//            Thread hilo = new Thread(this);
//            hilo.start();
            System.out.println("MENSAJE");

            Conexion conexion = new Conexion();
            VentaDAO ventaDAO = new VentaDAO(conexion.getConnection());
            ArrayList<VentaDTO> lista = ventaDAO.getBuysNotUpdate();
            pagoJSON user = null;

                System.out.println("LISTA PARA ACTUALIZAR ELEMENTOS");
                System.out.println(lista.size());
                System.out.println("");
            for (VentaDTO item : lista) {
                
                try {
                    System.out.println("");
                    user = CRestPse.consultarPagoDos(item.getReferencia());
                    System.out.println(user.toString());

                    // other status PENDING
                    if (user.getStatusCard().equalsIgnoreCase("APPROVED")) {
                        item.setIdEstadoVentaFK("2");
                        ventaDAO.actualizarVenta(item);
                        List<Producto> listaP = new ArrayList<Producto>();
                        listaP = ventaDAO.getProductosByPrice(item.getIdVenta());
                        for (Producto items : listaP) {
                            try {
                                System.out.println(item.toString());
                                ventaDAO.actualizarCantidad(items);
                            } catch (SQLException ex) {
                                ex.printStackTrace();
                            }
                        }
                    } else if (user.getStatusCard().equalsIgnoreCase("DECLINED")) {
                        item.setIdEstadoVentaFK("3");
                        ventaDAO.actualizarVenta(item);
                    } else if (user.getStatusCard().equalsIgnoreCase("ERROR")) {
                        item.setIdEstadoVentaFK("4");
                        ventaDAO.actualizarVenta(item);
                    } else if (user.getStatusCard().equalsIgnoreCase("VOIDED")) {
                        item.setIdEstadoVentaFK("5");
                        ventaDAO.actualizarVenta(item);
                    }
                } catch (MalformedURLException ex) {
                    ex.printStackTrace();
                } finally {
                    ventaDAO.CloseAll();
                }
                
            }
            
        }

    }

}
