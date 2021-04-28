package co.edu.sena.mercado.dto;

public class Mensaje {
    
    private String nombre;
    private String mensaje;
    private int numero;

    public Mensaje() {
    }

    public Mensaje(String nombre, String mensaje) {
        this.nombre = nombre;
        this.mensaje = mensaje;
    }

    public Mensaje(String nombre, String mensaje, int numero) {
        this.nombre = nombre;
        this.mensaje = mensaje;
        this.numero = numero;
    }
    
    

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public int getNumero() {
        return numero;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }

    @Override
    public String toString() {
        return "Mensaje{" + "nombre=" + nombre + ", mensaje=" + mensaje + ", numero=" + numero + '}';
    }
    
}
