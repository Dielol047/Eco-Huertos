package Negocio;

import Modelo.Cultivos;

public class GestorBiodata {

    public enum FamiliaBotanica {
        SOLANUM(100, 110, "Alto consumo"),
        MUSACEAS(150, 300, "Consumo intensivo"),
        CUCURBITACEAS(80, 75, "Consumo medio"),
        LEGUMINOSAS(20, 70, "Fijador de Nitrogeno"),
        BRASICACEAS(90, 60, "Consumo medio"),
        DESCONOCIDA(0, 0, "No clasificado"); // Por defecto

        private final int consumoNutrientesBase;
        private final int diasMaduracionPromedio;
        private final String caracteristica;

        FamiliaBotanica(int consumo, int dias, String carac) {
            this.consumoNutrientesBase = consumo;
            this.diasMaduracionPromedio = dias;
            this.caracteristica = carac;
        }

        public int getConsumoNutrientesBase() { return consumoNutrientesBase; }
        public int getDiasMaduracionPromedio() { return diasMaduracionPromedio; }
        public String getCaracteristica() { return caracteristica; }
    }

    // Método para obtener la familia basada en el string de la taxonomía del cultivo.
    public FamiliaBotanica obtenerFamiliaPorCategoria(String categoria) {
        if (categoria == null) return FamiliaBotanica.DESCONOCIDA;
        
        return switch (categoria.toUpperCase()) {
            case "SOLANUM" -> FamiliaBotanica.SOLANUM;
            case "MUSÁCEAS", "MUSACEAS" -> FamiliaBotanica.MUSACEAS;
            case "CUCURBITÁCEAS", "CUCURBITACEAS" -> FamiliaBotanica.CUCURBITACEAS;
            case "LEGUMINOSAS" -> FamiliaBotanica.LEGUMINOSAS;
            case "BRASICÁCEAS", "BRASICACEAS" -> FamiliaBotanica.BRASICACEAS;
            default -> FamiliaBotanica.DESCONOCIDA;
        };
    }

    // Método para mostrar o asignar la biodata a un cultivo específico
    public void gestionarBiodata(Cultivos cultivo) {
        System.out.println("\n--- BIODATA DEL CULTIVO ---");
        System.out.println("Cultivo: " + cultivo.getNombre());
        System.out.println("Categoría Taxonómica: " + cultivo.getCategoria());

        FamiliaBotanica familia = obtenerFamiliaPorCategoria(cultivo.getCategoria());

        System.out.println(">> Características Botánicas:");
        System.out.println("- Tipo de Consumo: " + familia.getCaracteristica());
        System.out.println("- Consumo de Nutrientes Base: " + familia.getConsumoNutrientesBase() + " unidades");
        System.out.println("- Días de Maduración Promedio: " + familia.getDiasMaduracionPromedio() + " días");
        System.out.println("---------------------------");
    }
}