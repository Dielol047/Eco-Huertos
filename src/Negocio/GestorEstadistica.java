package Negocio;

import Modelo.Cultivos;
import java.util.Scanner;

public class GestorEstadistica implements Analizar {
     private Cultivos ultimoCultivo; 
     public void setUltimoCultivo(Cultivos c) {
    this.ultimoCultivo = c;
}
    public void mostrarEstadisticasClimaticas(Cultivos cultivo, Scanner scanner) {
        this.ultimoCultivo = cultivo; 
        if (cultivo.tieneEstadisticas()) {
            mostrarResumen(cultivo);
            String opcion = "";
            while (true) {
                System.out.print("\n¿Desea editar las estadísticas climáticas? (s/n): ");
                opcion = scanner.next().toLowerCase();
                if (opcion.equals("s") || opcion.equals("n")) break;
                System.out.println("--- Error: Entrada inválida. Ingrese 's' o 'n'. ---");
            }
            if (opcion.equals("n")) return;
        }

        System.out.println("\n--- REGISTRO DE DATOS CLIMÁTICOS (" + cultivo.getNombre() + ") ---");

        cultivo.setTempMin(solicitarValidado(scanner, "Ingrese Temperatura Mínima (°C): ", "min", 0));
        cultivo.setTempMax(solicitarValidado(scanner, "Ingrese Temperatura Máxima (°C): ", "max", cultivo.getTempMin()));
        cultivo.setTempMedia(solicitarValidado(scanner, "Ingrese Temperatura Media Diaria (°C): ", "media", cultivo.getTempMin(), cultivo.getTempMax()));
        cultivo.setPrecipitacion(solicitarValidado(scanner, "Ingrese Precipitación Acumulada (mm): ", "precip", 0));
        cultivo.setHumedad(solicitarValidado(scanner, "Ingrese Humedad Relativa Media (0-100%): ", "hum", 0, 100));
        cultivo.setRadiacion(solicitarValidado(scanner, "Ingrese Radiación Solar PAR (μmol/m²/s): ", "rad", 0));
        
        cultivo.setEstadisticasCargadas(true);
        mostrarResumen(cultivo);
    }

    private void mostrarResumen(Cultivos cultivo) {
        System.out.println("\n--- MÓDULO DE ESTADÍSTICAS CLIMÁTICAS (" + cultivo.getNombre() + ") ---");
        System.out.println("1) TEMPERATURA POR FASES:");
        System.out.println("   - Maduración promedio: " + cultivo.getDiasMaduracion() + " días");
        System.out.println("   - Media Diaria: " + cultivo.getTempMedia() + "°C");
        System.out.println("   - Rango: " + cultivo.getTempMin() + "°C (Min) / " + cultivo.getTempMax() + "°C (Max)");
        System.out.println("\n2) PRECIPITACIONES: " + cultivo.getPrecipitacion() + " mm");
        System.out.println("\n3) HUMEDAD RELATIVA: " + cultivo.getHumedad() + "%");
        System.out.println("\n4) RADIACIÓN SOLAR (PAR): " + cultivo.getRadiacion() + " μmol/m²/s");
        System.out.println("-------------------------------------------------------------");
    }

    private double solicitarValidado(Scanner scanner, String mensaje, String tipo, double... limites) {
        double valor;
        while (true) {
            System.out.print(mensaje);
            while (!scanner.hasNextDouble()) {
                System.out.print("--- Error: Ingrese un valor numérico: ");
                scanner.next();
            }
            valor = scanner.nextDouble();
            try {
                switch (tipo) {
                    case "min" -> {
                        // Vacío para permitir negativos
                        }
                    case "max" -> {
                        if (valor < limites[0]) throw new IllegalArgumentException("Error: La máxima no puede ser menor a la mínima (" + limites[0] + "°C).");
                    }
                    case "media" -> {
                        if (valor < limites[0] || valor > limites[1]) 
                            throw new IllegalArgumentException("Error: La media debe estar entre " + limites[0] + " y " + limites[1] + ".");
                    }
                    case "hum" -> {
                        if (valor < 0 || valor > 100) throw new IllegalArgumentException("Error: La humedad debe estar entre 0 y 100%.");
                    }
                    case "precip" -> {
                        if (valor < 0) throw new IllegalArgumentException("Error: La precipitación no puede ser negativa.");
                    }
                    case "rad" -> {
                        if (valor < 0) throw new IllegalArgumentException("Error: La radiación solar no puede ser negativa.");
                    }
                }
                return valor;
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        }
    }
    @Override
    public void imprimirReportePrediccion(Cultivos c) {
        System.out.println("\n--- Reporte de Estadísticas ---");        
         if (c == null || !c.tieneEstadisticas()) {
        System.out.println("--- Error: No hay datos climáticos para " + (c != null ? c.getNombre() : "el cultivo") + " ---");
        return;
        }
     double tOpt = 22.0;   // Temperatura óptima base
    double parOpt = 1200.0; // Radiación PAR óptima base
    double baseRendimiento = 5000.0; // Base de producción (kg/km2)

    System.out.println("--- PRONÓSTICO INTEGRAL DE CLIMA Y RENDIMIENTO ---");

    switch (c.getCategoria().toUpperCase()) {
        case "SOLANUM" -> { tOpt = 24.0; parOpt = 1500.0; }
        case "MUSACEAS", "MUSÁCEAS" -> { tOpt = 27.0; parOpt = 1800.0; }
        case "CUCURBITACEAS", "CUCURBITÁCEAS" -> { tOpt = 25.0; parOpt = 1400.0; }
        case "LEGUMINOSAS" -> { tOpt = 20.0; parOpt = 1000.0; }
        case "BRASICACEAS", "BRASICÁCEAS" -> { tOpt = 18.0; parOpt = 900.0; }
    }

    // 3. Cálculo de Índices (Lógica de estrés agroclimático)
    
    // Índice de Temperatura (ITemp): Distancia al óptimo
    double rango = c.getTempMax() - c.getTempMin();
    if (rango <= 0) rango = 1.0; 
    double iTemp = 1.0 - (Math.abs(c.getTempMedia() - tOpt) / rango);
    iTemp = Math.max(0.0, Math.min(1.0, iTemp));

    // Índice de Balance Hídrico (IAgua): Cruce entre lluvia y humedad
    double iAgua = (c.getPrecipitacion() / 100.0) * (c.getHumedad() / 100.0);
    iAgua = Math.max(0.0, Math.min(1.0, iAgua));

    // Índice de Radiación (ILuz): Eficiencia lumínica
    double iLuz = c.getRadiacion() / parOpt;
    iLuz = Math.max(0.0, Math.min(1.0, iLuz));

    // 4. Estimación de Rendimiento (IEA - Índice de Eficiencia Agroclimática)
    double produccionEstimada = (baseRendimiento * c.getAreaKm2()) * (iTemp * iAgua * iLuz);

    // 5. Salida Consolidada
    System.out.println("\n--- MODELO IEA: RESULTADOS DEL ANÁLISIS ---");
    System.out.printf("Cultivo: %s | Área: %.2f km2\n", c.getNombre(), c.getAreaKm2());
    System.out.println("-----------------------------------------------------");
    System.out.printf("ITemp : %.2f\n", iTemp);
    System.out.printf("IAgua (Eficiencia Hídrica): %.2f\n", iAgua);
    System.out.printf("ILuz  (Intensidad PAR):    %.2f\n", iLuz);
    System.out.println("-----------------------------------------------------");
    System.out.printf("RENDIMIENTO POTENCIAL ESTIMADO: %.2f kg\n", produccionEstimada);
    System.out.println(".....................................................\n");
     System.out.println("\n--- DIAGNÓSTICO DE ALERTAS ---");

    // Estado ITemp
    String estadoTemp;
    if (iTemp >= 0.80)      estadoTemp = "ÓPTIMO";
    else if (iTemp >= 0.60) estadoTemp = "ADVERTENCIA";
    else if (iTemp >= 0.30) estadoTemp = "CRÍTICO";
    else                    estadoTemp = "EMERGENCIA TÉRMICA";
    System.out.println("Estado Temperatura (ITemp = " + String.format("%.2f", iTemp) + "): " + estadoTemp);

    // Recomendación hídrica
    if (iAgua < 0.30) {
        System.out.println(" Recomendación: Incrementar riego. Déficit hídrico detectado (IAgua = " + String.format("%.2f", iAgua) + ").");
    }
    else if (iAgua > 0.90) {
        System.out.println(" Alerta Hídrica: Saturación detectada (IAgua = " + String.format("%.2f", iAgua) + "). Riesgo de asfixia radicular.");
    }
    // Recomendación lumínica
    if (iLuz < 0.30) {
        System.out.println(" Recomendación: Mejorar exposición lumínica. Radiación insuficiente para la fotosíntesis (ILuz = " + String.format("%.2f", iLuz) + ").");
    }
    else if (iLuz > 1.20) {
        System.out.println(" Alerta Lumínica: Radiación excesiva (ILuz = " + String.format("%.2f", iLuz) + "). Riesgo de fotoinhibición/estrés radiativo.");
    }

    System.out.println(".....................................................\n");
}
}