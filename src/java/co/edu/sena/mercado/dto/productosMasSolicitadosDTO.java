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
public class productosMasSolicitadosDTO {
     productoPedidosDTO productoPedidoDTO;
     ArrayList<ImagenesProducto>listaImagenes;
     Producto productoDTO;

    public productosMasSolicitadosDTO() {
    }

    public productosMasSolicitadosDTO(productoPedidosDTO productoPedidoDTO, ArrayList<ImagenesProducto> listaImagenes, Producto productoDTO) {
        this.productoPedidoDTO = productoPedidoDTO;
        this.listaImagenes = listaImagenes;
        this.productoDTO = productoDTO;
    }

    public productoPedidosDTO getProductoPedidoDTO() {
        return productoPedidoDTO;
    }

    public void setProductoPedidoDTO(productoPedidosDTO productoPedidoDTO) {
        this.productoPedidoDTO = productoPedidoDTO;
    }

    

    public ArrayList<ImagenesProducto> getListaImagenes() {
        return listaImagenes;
    }

    public void setListaImagenes(ArrayList<ImagenesProducto> listaImagenes) {
        this.listaImagenes = listaImagenes;
    }

    public Producto getProductoDTO() {
        return productoDTO;
    }

    public void setProductoDTO(Producto productoDTO) {
        this.productoDTO = productoDTO;
    }

    @Override
    public String toString() {
        return "productosMasSolicitadosDTO{" + "productoPedidoDTO=" + productoPedidoDTO + ", listaImagenes=" + listaImagenes + ", productoDTO=" + productoDTO + '}';
    }

    
     
     
}
