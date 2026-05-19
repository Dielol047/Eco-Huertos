package Modelo;

public class Trabajador extends Persona{
    private String cargo;
    private double salario;
    private String turno;

    public Trabajador(String id, String nombre, String contacto, String cargo) {
        super(id, nombre, contacto);
        this.cargo = cargo;
    }
    public String getCargo() { return cargo; }
}
