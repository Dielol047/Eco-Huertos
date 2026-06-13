package Modelo;

public class Parcela {
    private String idParcela;
    private String tipoDeSuelo;
    private Boolean disponible;

    // 1. Asegúrate de declarar la variable exactamente así:
    private Cultivos cultivoActual;

    public Parcela(String idParcela, String tipoDeSuelo, Boolean disponible, Cultivos cultivoActual) {
        this.idParcela = idParcela;
        this.tipoDeSuelo = tipoDeSuelo;
        this.disponible = disponible;
        // 2. Y asegúrate de que el 'this' coincida con la variable declarada arriba:
        this.cultivoActual = cultivoActual;
    }

    // 3. Y finalmente, que el getter use el mismo nombre:
    public Cultivos getCultivoActual() {
        return this.cultivoActual;
    }
    // Dentro de la clase Parcela
    public String getIdParcela() {
        return this.idParcela;
    }

    public String getTipoDeSuelo() {
        return this.tipoDeSuelo;
    }

    public Boolean getDisponible() {
        return this.disponible;
    }
}