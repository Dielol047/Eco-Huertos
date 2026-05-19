package Modelo;

public class Especie {
    private String idEspecie;
    private String nombreComun;
    private String generoOrigen; // La familia o genero taxonomico principal

    public Especie(String id, String nombre, String genero) {
        this.idEspecie = id;
        this.nombreComun = nombre;
        this.generoOrigen = genero;
    }

    public String getGeneroOrigen() { return generoOrigen; }
}
