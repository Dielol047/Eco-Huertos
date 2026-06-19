package Modelo;

public class Parcela {
    private int idParcela;
    private String tipoDeSuelo;
    private Boolean disponible;
    private Cultivos cultivoActual;
    private int idCultivo;

    public Parcela(String tipoDeSuelo, Boolean disponible, Cultivos cultivoActual) {
        this.tipoDeSuelo = tipoDeSuelo;
        this.disponible = disponible;
        this.cultivoActual = cultivoActual;
        this.idCultivo = (cultivoActual != null) ? cultivoActual.getId() : -1;
        this.idParcela = this.idCultivo;
    }

    public Cultivos getCultivoActual() {
        return this.cultivoActual;
    }

    public int getIdParcela() {
        return this.idParcela;
    }

    public String getTipoDeSuelo() {
        return this.tipoDeSuelo;
    }

    public void setTipoDeSuelo(String tipoDeSuelo) {
        this.tipoDeSuelo = tipoDeSuelo;
    }

    public Boolean getDisponible() {
        return this.disponible;
    }

    public int getIdCultivo() {
        return this.idCultivo;
    }
}