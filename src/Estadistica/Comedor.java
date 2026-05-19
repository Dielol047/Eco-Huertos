package Estadistica;

public class Comedor {
    private String idComedor;
    private String nombre;
    private int indiceVulnerabilidad;

    public Comedor(String id, String nombre, int vulnerabilidad) {
        this.idComedor = id;
        this.nombre = nombre;
        this.indiceVulnerabilidad = vulnerabilidad;
    }

    public String getNombre() { return nombre; }

}
