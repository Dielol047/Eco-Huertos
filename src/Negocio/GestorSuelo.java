package Negocio;
import Interfaz.AgroCicloGUI;

import Modelo.Parcela;
import Modelo.Cultivos;
import java.util.ArrayList;
import java.util.Scanner;

public class GestorSuelo implements Analizar {
    public boolean validarRotacion(Parcela p, String nuevoGenero) {
        System.out.println("[Modulo Suelo] Validando rotacion botánica...");
        System.out.println("-> Verificando parcela: " + p.getIdParcela());
        if (p.getCultivoActual() != null) {
            System.out.println("-> Área: " + p.getCultivoActual().getAreaKm2() + " km2");
        } else {
            System.out.println("-> Área: Parcela vacía (0 km2)");
        }

        System.out.println("-> Tipo de suelo: " + p.getTipoDeSuelo());
        System.out.println("-> Estado: " + (p.getDisponible() ? "Disponible" : "Ocupada"));
        
        if (!p.getDisponible()) {
            System.out.println("-> Alerta: La parcela no está disponible.");
            return false;
        }

        return true;
    }

    public void gestionarSuelo(Cultivos activo, ArrayList<Parcela> parcelas, Scanner scanner) {
         while (true) {
        if (activo.getParcelaAsignada() != null) {
            Parcela pExistente = activo.getParcelaAsignada();
            System.out.println("\n--- DETALLE DE PARCELA ---");
            System.out.println("ID Parcela: " + pExistente.getIdParcela());
            System.out.println("Tipo Suelo: " + pExistente.getTipoDeSuelo());
            System.out.println("ID Cultivo Asignado: " + pExistente.getIdCultivo());
            System.out.println("Nombre Cultivo: " + activo.getNombre());
            
            String editar = "";
            while (true) {
                System.out.print("\n¿Desea editar el tipo de suelo? (s/n): ");
                editar = scanner.next().toLowerCase();
                if (editar.equals("s") || editar.equals("n")) break;
                System.out.println("--- Error: Entrada inválida. Ingrese 's' o 'n'. ---");
            }
            if (editar.equals("n")) return;
        }

        int opcionSuelo = 0;
        String suelo = "";
        while (suelo.isEmpty()) {
            System.out.println("\n--- SELECCIONE TIPO DE SUELO ---");
            System.out.println("1. Arenoso | 2. Arcilloso | 3. Franco | 4. Limoso | 5. Organico | 6. Ver info");
            System.out.print("Opción (1-6): ");
              try {
                String entrada = scanner.next();
                opcionSuelo = Integer.parseInt(entrada);

                switch (opcionSuelo) {
                    case 1 -> suelo = "Arenoso";
                    case 2 -> suelo = "Arcilloso";
                    case 3 -> suelo = "Franco";
                    case 4 -> suelo = "Limoso";
                    case 5 -> suelo = "Organico";
                    case 6 -> {
                        String info = "INFORMACIÓN DE SUELOS\n\n" +
                                "Arenoso: Se siente áspero y granuloso al tacto, es muy suelto y el agua se filtra de inmediato sin formar una masa.\n\n" +
                                "Arcilloso: Muy pegajoso y moldeable cuando está húmedo (parece plastilina), se vuelve duro como piedra y se agrieta al secarse.\n\n" +
                                "Franco: Textura equilibrada y suave; forma una bola firme al apretarla con la mano que se deshace fácilmente con un toque.\n\n" +
                                "Limoso: Tacto suave y jabonoso similar a la harina o al talco, no es pegajoso pero se compacta levemente al humedecerse.\n\n" +
                                "Orgánico: Color oscuro o negro intenso, muy liviano y esponjoso, con restos vegetales visibles y olor característico a tierra de bosque.";

                        // 2. Llama directamente a la ventana aquí mismo
                        javax.swing.JOptionPane.showMessageDialog(null, info, "Definición de Suelos", javax.swing.JOptionPane.INFORMATION_MESSAGE);
                    }
                    default -> throw new IllegalArgumentException("Opción fuera de rango. Ingrese un número del 1 al 6.");
                }
            } catch (NumberFormatException e) {
                System.out.println("--- Error: Entrada inválida. Debe ingresar un número entero. ---");
            } catch (IllegalArgumentException e) {
                System.out.println("--- Error: " + e.getMessage() + " ---");
            }
        }

        if (activo.getParcelaAsignada() != null) {
            activo.getParcelaAsignada().setTipoDeSuelo(suelo);
            System.out.println("¡Tipo de suelo actualizado!");
        } else {
            Parcela nuevaParcela = new Parcela(suelo, true, activo);
            parcelas.add(nuevaParcela);
            activo.setParcelaAsignada(nuevaParcela);
            registrarParcela(nuevaParcela);
            System.out.println("¡Parcela creada y asignada!");
        }
    }
    }

    public void registrarParcela(Parcela p) {
        System.out.println("[GestorSuelo] Guardando nueva parcela: " + p.getIdParcela());
    }
   @Override
public void imprimirReportePrediccion(Cultivos c) {
    System.out.println(" Reporte Suelo");
     if (c == null || c.getParcelaAsignada() == null) {
        System.out.println("--- Reporte Suelo: Sin parcela asignada para " + (c != null ? c.getNombre() : "el cultivo") + " ---");
        return;
    }

     String tipoSuelo = c.getParcelaAsignada().getTipoDeSuelo().toLowerCase();
        double cs = switch (tipoSuelo) {
            case "arenoso" -> 0.50;
            case "arcilloso" -> 0.75;
            case "limoso" -> 0.90;
            case "organico" -> 1.20;
            default -> 1.00; // Franco
        };

        // Asumimos un índice hídrico base si el cultivo tiene estadísticas
        double iAgua = c.tieneEstadisticas() ? (c.getPrecipitacion() / 100.0) * (c.getHumedad() / 100.0) : 0.5;
        double eAbs = Math.min(1.0, iAgua * cs);
        double rc = (1.0 - cs) * 100.0;

        System.out.println("\n--- ANÁLISIS TÉCNICO DE SUELO (" + c.getParcelaAsignada().getTipoDeSuelo() + ") ---");
        System.out.printf("Coeficiente de Soporte (Cs): %.2f\n", cs);
        System.out.printf("Eficiencia de Absorción Hídrica (Eabs): %.2f\n", eAbs);
        System.out.printf("Riesgo de Compactación (Rc): %.2f%%\n", Math.max(0, rc));
        System.out.println("-------------------------------------------------------------");

        if (tipoSuelo.equals("arenoso")) {
            System.out.println(" Alerta: Baja retención hídrica. El agua se filtra rápidamente.");
        } else if (rc > 20) {
            System.out.println(" Alerta: Riesgo de compactación elevado. Posible déficit de oxígeno en raíces.");
        }
        else {
        System.out.println(" Suelo con buen drenaje y aireación. Condiciones óptimas.");
    }
    }


        
    
    
}



