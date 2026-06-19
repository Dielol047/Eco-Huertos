package Modelo;

public class Persona {
    protected String id;
    protected String nombre;
    protected String contacto;

    public Persona(String id, String nombre, String contacto) {
        this.id = id;
        setNombre(nombre);
        setContacto(contacto);
    }

    public String getId() { return id; }
    public String getNombre() { return nombre; }
    public String getContacto() { return contacto; }

    public void setNombre(String nombre) {
        if (nombre == null || !nombre.matches("[a-zA-ZáéíóúÁÉÍÓÚñÑ\\s]+")) {
            throw new IllegalArgumentException("El nombre no puede contener números ni caracteres especiales.");
        }
        this.nombre = nombre;
    }

    public void setContacto(String contacto) {
        if (contacto == null || !contacto.matches("[0-9]+")) {
            throw new IllegalArgumentException("El contacto solo puede contener números.");
        }
        this.contacto = contacto;
    }
}