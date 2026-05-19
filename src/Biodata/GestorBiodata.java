package Biodata;

public class GestorBiodata {
    public String proyectarMaduracion(LoteCompost lote) {
        System.out.println("[Modulo Biodata] Calculando maduracion base taxonomica...");
        System.out.println("-> Procesando lote de residuos originados por: " + lote.getGeneroOrigen());
        return "20/07/2026"; // Fecha simulada temporal
    }
}
