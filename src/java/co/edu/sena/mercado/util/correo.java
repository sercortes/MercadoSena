/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.sena.mercado.util;

import java.util.Properties;
import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/**
 *
 * @author DELL
 */
public class correo {
    
      public boolean envCorreo(String dest, String clave) {

        Properties propiedad = new Properties();
        propiedad.setProperty("mail.smtp.host", "smtp.gmail.com");
        propiedad.setProperty("mail.smtp.starttls.enable", "true");
        propiedad.setProperty("mail.smtp.port", "587");
        //propiedad.setProperty(¡);

//         Properties propiedades=new Properties();
//        propiedades.put("mail.smtp.auth", "true");
//        propiedades.put("mail.smtp.starttls.enable", "true");
//        propiedades.put("mail.smtp.host", "smtp.gmail.com");
//        propiedades.put("mail.amtp.port", "587");
        String cuerpoMensaje = "Bienvenido al sistema mercado Sena <br>"
                + "Tu usuario es: " + dest + "<br>"
                + "Tu contraseña es: " + clave + "<br>"
                + "te estamos esperando!!";

        Session sesion = Session.getDefaultInstance(propiedad);
        String correoEnvia = "mercadosena2020@gmail.com";
        String contrasena = "m3rc4d0$3n4";
        String receptor = dest;
        String asunto = "Registro Sistema de reportes";
        String mensaje = cuerpoMensaje;

        MimeMessage mail = new MimeMessage(sesion);
        try {
            mail.setFrom(new InternetAddress(correoEnvia));
            mail.addRecipient(Message.RecipientType.TO, new InternetAddress(receptor));
            mail.setSubject(asunto);
            mail.setText(mensaje);
            mail.setContent("<h3>" + cuerpoMensaje + "</h3>", "text/html");

            Transport transportar = sesion.getTransport("smtp");
            transportar.connect(correoEnvia, contrasena);
            transportar.sendMessage(mail, mail.getRecipients(Message.RecipientType.TO));

            transportar.close();
            // System.out.println("xxx____correo enviado");
            return true;

        } catch (Exception e) {
            System.out.println("xxx____correo No enviado " + e);
            return false;
        }

    }
    
}
