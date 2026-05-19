package Modelo;

public class Voluntario extends Persona{
    private int nivelExperiencia;
    private String certificacion;
    private String horarioDisponibilidad;

    public Voluntario(String id, String nombre, String contacto, int nivelExp, String certificacion) {
        super(id, nombre, contacto);
        this.nivelExperiencia = nivelExp;
        this.certificacion = certificacion;
    }
    public int getNivelExperiencia() { return nivelExperiencia; }
    public void setNivelExperiencia(int nivel) { this.nivelExperiencia = nivel; }
    public String getCertificacion() { return certificacion; }

}
