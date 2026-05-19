package Personal;

public class Persona {
    protected String id;
    protected String nombre;
    protected String contacto;
    public Persona(String id, String nombre, String contacto){
        this.id = id;
        this.nombre = nombre;
        this.contacto = contacto;
    }
    public String getId() { return id; }
    public String getNombre() { return nombre; }
    public String getContacto() { return contacto; }
}
