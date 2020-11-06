/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.sena.mercado.util;

import co.edu.sena.mercado.dto.usuarioDTO;
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

    public boolean envCorreo(String dest, String clave, String codigo) throws MessagingException{
        try {

            Properties props = new Properties();
            props.setProperty("mail.smtp.host", "smtp.gmail.com");
            props.setProperty("mail.smtp.starttls.enable", "true");
            props.setProperty("mail.smtp.port", "587");
            props.setProperty("mail.smtp.user", "mercadosena2020@gmail.com");
            props.setProperty("mail.smtp.auth", "true");

            Session session = Session.getDefaultInstance(props);
            session.setDebug(true);

            MimeMessage message = new MimeMessage(session);

            message.setFrom(new InternetAddress("mercadosena2020@gmail.com"));

            message.addRecipient(Message.RecipientType.TO, new InternetAddress(dest));

            message.setSubject("Activación cuenta");
            message.setText(
                    " <div style='padding: 20px;border: solid 2px green;border-radius: 10px;padding-bottom: 0px;'>"
                    + "            <h3 style='text-align: center'>Mercado sena</h3>"
                    + "            <p>De parte de todo el equipo te damos la bienvenida a nuestro sistema, para poder activar tu cuenta por favor sigue los pasos:</p>"
                    + "            <p><b>Usuario:</b>" + dest + "</p>"
                    + "            <p><b>clave:</b>" + clave + "</p>"
                    + "<a style='color: rgb(0, 128, 0);' href='http://181.48.181.131/MercadoSena/activarCuenta?usuario=" + dest + "&codigo=" + codigo + "'><p>Click aquí para activar tu cuenta</p></a>"
                    +"            <footer style=' background: rgb(252, 252, 252);height: 39px;padding: 15px;text-align: center;'>"
                    + "                <div >"
                    + "                    <p style='color: rgb(117, 117, 117);'>SENA 2020 - CGMLTI</p>"
                    + "                </div>"
                    + "            </footer>"
                    + "        </div>",
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
            throw new MessagingException();
        }

    }

    public boolean correoRec(usuarioDTO usuario) {
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
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(usuario.getCorreoUsu()));

            message.setSubject("Recuperación de contraseña");
            message.setText(
                    " <div style='padding: 20px;border: solid 2px green;border-radius: 10px;padding-bottom: 0px;'>"
                    + "            <h3 style='text-align: center'>Mercado sena</h3>"
                    + "            <p>Nuestro equipo le informa que se ha restablecido su contraseña, por su seguridad le recomendamos cambiarla</p>"
                    + "            <p><b>Usuario:</b>" + usuario.getCorreoUsu() + "</p>"
                    + "            <p><b>clave:</b>" + usuario.getClaveUsu() + "</p>"
                    + "            <footer style=' background: rgb(252, 252, 252);height: 39px;padding: 15px;text-align: center;'>"
                    + "                <div >"
                    + "                    <p style='color: rgb(117, 117, 117);'>SENA 2020 - CGMLTI</p>"
                    + "                </div>"
                    + "            </footer>"
                    + "        </div>",
                    "ISO-8859-1",
                    "html");

            Transport t = session.getTransport("smtp");

            t.connect("mercadosena2020@gmail.com", "m3rc4d0$3n4");

            t.sendMessage(message, message.getAllRecipients());

            t.close();
            //System.out.println("xxx____correo enviado");
            return true;

        } catch (MessagingException e) {

            System.out.println("xxxxxxxxxxxx____correo No enviado " + e);
            return false;
        }

    }

}
