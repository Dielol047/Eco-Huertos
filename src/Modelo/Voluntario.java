package Modelo;

public class Voluntario extends Persona {
    private int idCultivo;
    private String cargo;

    public Voluntario(String id, String nombre, String contacto, int idCultivo, String cargo) {
        super(id, nombre, contacto);
        this.idCultivo = idCultivo;
        this.cargo = cargo;
    }

    public int getIdCultivo() { return idCultivo; }
    public String getCargo() { return cargo; }
}