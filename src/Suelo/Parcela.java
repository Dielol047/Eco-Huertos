package Suelo;

public class Parcela {
    private String idParcela;
    private double areaM2;
    private String tipoDeSuelo;
    private String estado;
    private String ultimoCultivo;

    public Parcela(String id, double area, String tipoSuelo) {
        this.idParcela = id;
        this.areaM2 = area;
        this.tipoDeSuelo = tipoSuelo;
        this.estado = "Disponible";
    }
    public String getIdParcela() { return idParcela; }
    public String getUltimoCultivo() { return ultimoCultivo; }
    public void setUltimoCultivo(String cultivo) { this.ultimoCultivo = cultivo; }
    public String getEstado() { return estado; }

}
