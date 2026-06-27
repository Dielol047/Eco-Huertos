package Modelo;

public class ContextoAgroCiclo {
    private static String taxonomiaSeleccionada = "Genérica";

    public static void setTaxonomia(String taxonomia) {
        taxonomiaSeleccionada = taxonomia;
    }

    public static String getTaxonomia() {
        return taxonomiaSeleccionada;
    }

    // --- MÉTODOS DE CONSULTA TÉCNICA CENTRALIZADOS ---

    public static double getFactorK() {
        switch (taxonomiaSeleccionada) {
            case "Solanum": return 3.5;
            case "Musáceas": return 4.2;
            case "Cucurbitáceas": return 2.8;
            case "Leguminosas": return 1.5;
            case "Brasicáceas": return 2.2;
            default: return 1.0;
        }
    }

    public static double getBaseTermica() {
        switch (taxonomiaSeleccionada) {
            case "Solanaceae": return 10.0;
            case "Musáceas": return 14.0;
            case "Cucurbitáceas": return 12.0;
            case "Leguminosas": return 8.0;
            case "Brasicáceas": return 5.0;
            default: return 10.0;
        }
    }

    public static int getTasaMaduracion() {
        switch (taxonomiaSeleccionada) {
            case "Solanum": return 100;
            case "Musáceas": return 270;
            case "Cucurbitáceas": return 80;
            case "Leguminosas": return 90;
            case "Brasicáceas": return 60;
            default: return 90;
        }
    }
}