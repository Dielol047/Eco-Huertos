package Negocio;

import Modelo.Cultivos;

public class GestorBiodata implements Analizar {

    public enum FamiliaBotanica {
        SOLANUM(100, 110, "Alto consumo", "Escarabajo de la patata, Mosca blanca", 7),
        MUSACEAS(150, 300, "Consumo intensivo", "Picudo negro, Trips", 15),
        CUCURBITACEAS(80, 75, "Consumo medio", "Barrenador, Minador", 5),
        LEGUMINOSAS(20, 70, "Fijador de Nitrogeno", "Gorgojo, Pulgón verde", 10),
        BRASICACEAS(90, 60, "Consumo medio", "Oruga de la col, Pulgón ceroso", 4),
        DESCONOCIDA(0, 0, "No clasificado", "Desconocidas", 7);

        private final int consumoNutrientesBase;
        private final int diasMaduracionPromedio;
        private final String caracteristica;
        private final String plagasComunes;
        private final int frecuenciaMonitoreoDias;


         FamiliaBotanica(int consumo, int dias, String carac, String plagas, int monitoreo) {
            this.consumoNutrientesBase = consumo;
            this.diasMaduracionPromedio = dias;
            this.caracteristica = carac;
            this.plagasComunes = plagas;
            this.frecuenciaMonitoreoDias = monitoreo;
        }

        public int getConsumoNutrientesBase() { return consumoNutrientesBase; }
        public int getDiasMaduracionPromedio() { return diasMaduracionPromedio; }
        public String getCaracteristica() { return caracteristica; }
         public String getPlagasComunes() { return plagasComunes; }
        public int getFrecuenciaMonitoreoDias() { return frecuenciaMonitoreoDias; }
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
    /*public void gestionarBiodata(Cultivos cultivo) {
        System.out.println("\n--- BIODATA DEL CULTIVO ---");
        System.out.println("Cultivo: " + cultivo.getNombre());
        System.out.println("Categoría Taxonómica: " + cultivo.getCategoria());

        FamiliaBotanica familia = obtenerFamiliaPorCategoria(cultivo.getCategoria());

        System.out.println(">> Características Botánicas:");
        System.out.println("- Tipo de Consumo: " + familia.getCaracteristica());
        System.out.println("- Consumo de Nutrientes Base: " + familia.getConsumoNutrientesBase() + " unidades");
        System.out.println("- Días de Maduración Promedio: " + familia.getDiasMaduracionPromedio() + " días");
        System.out.println("---------------------------");
    }*/
    @Override
    public void imprimirReportePrediccion(Cultivos c) {
       System.out.println(" Reporte Biodata");
        
           FamiliaBotanica familia = obtenerFamiliaPorCategoria(c.getCategoria());
        double consumoBase = familia.getConsumoNutrientesBase();
        double diasMaduracion = familia.getDiasMaduracionPromedio();

        // 1. Índice de Presión Rotacional (IPR) - Ahora solo informativo
        double ipr = (diasMaduracion > 0) ? (consumoBase / diasMaduracion) : 0;

        // 2. Factor de Carga Nutricional (FCN) - Impacto total acumulado
        double fcn = consumoBase * Math.sqrt(diasMaduracion);
        int diasAbonar = (ipr > 0) ? (int)(100 / ipr) : 30; 
        if (familia == FamiliaBotanica.LEGUMINOSAS) diasAbonar += 15; // Las leguminosas fijan nitrógeno, duran más

        System.out.println("\n--- ANÁLISIS DE BIODATA Y IMPACTO ACUMULADO ---");
        System.out.println("Familia: " + familia.name() + " | Perfil: " + familia.getCaracteristica());
        System.out.printf("Impacto Nutricional Total (FCN): %.2f unidades\n", fcn);
        System.out.printf("Intensidad de Reduccion de Nutrientes (IPR):    %.2f unidades/día\n", ipr);
        
        System.out.println("-------------------------------------------------------------");
      
        System.out.println("\n CONTROL DE PLAGAS:");
        System.out.println(">> Riesgo principal: " + familia.getPlagasComunes());
        System.out.println(">> Acción: Realizar inspección visual cada " + familia.getFrecuenciaMonitoreoDias() + " días.");
        
        System.out.println("\n CICLO DE NUTRICIÓN:");
        System.out.println(">> Acción: Aplicar refuerzo de abono/compost cada " + diasAbonar + " días.");
        System.out.println("-------------------------------------------------------------");
        
         if (fcn > 1500) {
            System.out.println(" Alerta: Desgaste severo. Incrementar dosis de materia orgánica en el próximo abonado.");
        }
    }
        
        public void gestionarBiodata(Cultivos cultivo) {
        System.out.println("\n--- BIODATA DEL CULTIVO ---");
        FamiliaBotanica familia = obtenerFamiliaPorCategoria(cultivo.getCategoria());
        System.out.println("Cultivo: " + cultivo.getNombre());
        System.out.println("- Tipo: " + familia.getCaracteristica());
        System.out.println("- Plagas: " + familia.getPlagasComunes());
        System.out.println("- Monitoreo: cada " + familia.getFrecuenciaMonitoreoDias() + " días.");
    
        
    }
}
