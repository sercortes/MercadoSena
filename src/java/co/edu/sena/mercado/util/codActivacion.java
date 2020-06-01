/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.sena.mercado.util;
import org.apache.commons.lang3.RandomStringUtils;
/**
 *
 * @author DELL
 */
public class codActivacion {
    
      public String generarCod(){
    return RandomStringUtils.random(20,0,62,true,true,"ABCDEFGHIJKLMNOPQRSTUVWXYZqwertyuioplkjhgfdsazxcvbnm1234567890".toCharArray());
    };
    
}
