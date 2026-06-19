package Modelo;

public class Voluntario extends Persona {
    private int idCultivo;
    private String cargo;

    public Voluntario(String id, String nombre, String contacto, int idCultivo, String cargo) {
        super(id, nombre, contacto);
        this.idCultivo = idCultivo;
        setCargo(cargo);
    }

    public int getIdCultivo() { return idCultivo; }
    public String getCargo() { return cargo; }

    public void setCargo(String cargo) {
        if (cargo == null || cargo.trim().isEmpty()) {
            throw new IllegalArgumentException("El cargo no puede estar vacío.");
        }
        this.cargo = cargo;
    }
}