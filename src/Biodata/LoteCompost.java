package Biodata;

public class LoteCompost {
    private String idLote;
    private String generoOrigen;
    private double temperaturaActual;

    public LoteCompost(String idLote, String generoOrigen) {
        this.idLote = idLote;
        this.generoOrigen = generoOrigen;
        this.temperaturaActual = 25.0; // Temperatura base
    }

    public String getGeneroOrigen() { return generoOrigen; }
}
