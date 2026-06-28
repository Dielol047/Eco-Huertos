package Negocio;

import Modelo.Cultivos;
import java.util.Scanner;

public class GestorEstadistica {

    public void mostrarEstadisticasClimaticas(Cultivos cultivo, Scanner scanner) {
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
                }
                return valor;
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        }
    }
}