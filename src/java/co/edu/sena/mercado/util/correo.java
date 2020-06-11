/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.sena.mercado.util;

import java.util.Properties;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/**
 *
 * @author DELL
 */
public class correo {
    
       public boolean envCorreo(String dest, String clave,String codigo) {
        try {

            Properties props = new Properties();

// Nombre del host de correo, es smtp.gmail.com
            props.setProperty("mail.smtp.host", "smtp.gmail.com");

// TLS si está disponible
            props.setProperty("mail.smtp.starttls.enable", "true");

// Puerto de gmail para envio de correos
            props.setProperty("mail.smtp.port", "587");

// Nombre del usuario
            props.setProperty("mail.smtp.user", "mercadosena2020@gmail.com");

// Si requiere o no usuario y password para conectarse.
            props.setProperty("mail.smtp.auth", "true");

           Session session = Session.getDefaultInstance(props);
            session.setDebug(true);

            MimeMessage message = new MimeMessage(session);

// Quien envia el correo
            message.setFrom(new InternetAddress("mercadosena2020@gmail.com"));

// A quien va dirigido
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(dest));

            message.setSubject("Activación cuenta");
            message.setText(
                    "<p>Bienvenido al sistema mercado Sena:</p>"
                    + "<p>Usuario: <b>" + dest + "</b></p>"
                    + "<p>Nueva clave: <b>" + clave + "</b></p>"
                    + "<p>Click aquí para activar su cuenta: <a href='http://localhost:8080/MercadoSena/activarCuenta?usuario="+dest+"&codigo="+codigo+"'>Activar cuenta</a></p>",
                    "ISO-8859-1",
                    "html");

            Transport t = session.getTransport("smtp");

            t.connect("mercadosena2020@gmail.com", "m3rc4d0$3n4");

            t.sendMessage(message, message.getAllRecipients());

            t.close();
            //System.out.println("xxx____correo enviado");
            return true;

        } catch (MessagingException e) {

            System.out.println("xxx____correo No enviado " + e);
            return false;
        }

    }

      
      
    
}
