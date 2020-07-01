/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.sena.mercado.dto;

import java.util.ArrayList;

/**
 *
 * @author DELL
 */


public class pedidoDTO {
  CompradorDTO compradorDTO;
  EmpresaPedidoDTO empPedidoDTO;
  VentaDTO ventaDTO;
  String estadoVenta;
  //info producto
  productoPedidosDTO prodPedidoDTO;
  productoImagenesDTO prodImagen;
  ArrayList<estadoVentaDTO> listaEstadoPedido;

    public pedidoDTO() {
    }

    public pedidoDTO(CompradorDTO compradorDTO, EmpresaPedidoDTO empPedidoDTO, VentaDTO ventaDTO, String estadoVenta, productoPedidosDTO prodPedidoDTO, productoImagenesDTO prodImagen, ArrayList<estadoVentaDTO> listaEstadoPedido) {
        this.compradorDTO = compradorDTO;
        this.empPedidoDTO = empPedidoDTO;
        this.ventaDTO = ventaDTO;
        this.estadoVenta = estadoVenta;
        this.prodPedidoDTO = prodPedidoDTO;
        this.prodImagen = prodImagen;
        this.listaEstadoPedido = listaEstadoPedido;
    }

    public ArrayList<estadoVentaDTO> getListaEstadoPedido() {
        return listaEstadoPedido;
    }

    public void setListaEstadoPedido(ArrayList<estadoVentaDTO> listaEstadoPedido) {
        this.listaEstadoPedido = listaEstadoPedido;
    }

    public CompradorDTO getCompradorDTO() {
        return compradorDTO;
    }

    public void setCompradorDTO(CompradorDTO compradorDTO) {
        this.compradorDTO = compradorDTO;
    }

    public EmpresaPedidoDTO getEmpPedidoDTO() {
        return empPedidoDTO;
    }

    public void setEmpPedidoDTO(EmpresaPedidoDTO empPedidoDTO) {
        this.empPedidoDTO = empPedidoDTO;
    }

    public VentaDTO getVentaDTO() {
        return ventaDTO;
    }

    public void setVentaDTO(VentaDTO ventaDTO) {
        this.ventaDTO = ventaDTO;
    }

    public String getEstadoVenta() {
        return estadoVenta;
    }

    public void setEstadoVenta(String estadoVenta) {
        this.estadoVenta = estadoVenta;
    }

    public productoPedidosDTO getProdPedidoDTO() {
        return prodPedidoDTO;
    }

    public void setProdPedidoDTO(productoPedidosDTO prodPedidoDTO) {
        this.prodPedidoDTO = prodPedidoDTO;
    }

    public productoImagenesDTO getProdImagen() {
        return prodImagen;
    }

    public void setProdImagen(productoImagenesDTO prodImagen) {
        this.prodImagen = prodImagen;
    }

    @Override
    public String toString() {
        return "pedidoDTO{" + "compradorDTO=" + compradorDTO + ", empPedidoDTO=" + empPedidoDTO + ", ventaDTO=" + ventaDTO + ", estadoVenta=" + estadoVenta + ", prodPedidoDTO=" + prodPedidoDTO + ", prodImagen=" + prodImagen + '}';
    }
  
  
  
}
