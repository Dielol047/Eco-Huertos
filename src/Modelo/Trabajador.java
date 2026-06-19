package Modelo;

public class Trabajador extends Persona {
    private String cargo;
    private double salario;
    private String turno;
    private int idCultivo;

    public Trabajador(String id, String nombre, String contacto, String cargo, int idCultivo) {
        super(id, nombre, contacto);
        this.cargo = cargo;
        this.idCultivo = idCultivo;
    }

    public String getCargo() { return cargo; }
    public int getIdCultivo() { return idCultivo; }
    public void setCargo(String cargo) { this.cargo = cargo; }
}