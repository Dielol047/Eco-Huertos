import Modelo.*;
import Negocio.*;
import Interfaz.*;

import java.util.Scanner;
public class Main {
    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        GestorBiodata gestorBio = new GestorBiodata();
        GestorSuelo gestorSue = new GestorSuelo();
        GestorPersonal gestorPer = new GestorPersonal();
        GestorEstadistica gestorEst = new GestorEstadistica();

        System.out.println("=== BIENVENIDO A AGROCICLO ===");

        // CP1: Selector taxonómico con valor seguro por defecto
        System.out.println("Seleccione la rama taxonómica de trabajo para hoy:");
        System.out.println("1. Solanum | 2. Musáceas | 3. Cucurbitáceas | 4. Leguminosas | 5. Brasicáceas");
        System.out.print("Opción (1-5): ");

        String taxonomia;
        if (scanner.hasNextInt()) {
            int sel = scanner.nextInt();
            taxonomia = switch(sel) {
                case 1 -> "Solanum";
                case 2 -> "Musáceas";
                case 3 -> "Cucurbitáceas";
                case 4 -> "Leguminosas";
                case 5 -> "Brasicáceas";
                default -> {
                    System.out.println("--- Opción inválida. Asignando 'Genérica'. ---");
                    yield "Genérica";
                }
            };
        } else {
            System.out.println("--- Entrada no numérica. Asignando 'Genérica'. ---");
            scanner.next();
            taxonomia = "Genérica";
        }
        System.out.println(">>> Sistema configurado para: " + taxonomia);

        // Bucle principal con CP2 y CP3 integrados
        while(true) {
            System.out.println("\n--- MENU PRINCIPAL (" + taxonomia + ") ---");
            System.out.println("1. Suelo | 2. Personal | 3. Biodata | 4. Estadística | 5. Optimizar | 6. Salir");
            System.out.print("Elija una opción (1-6): ");

            // CP3: Estabilidad (se valida si es entero, evitando que el programa se cierre)
            if (scanner.hasNextInt()) {
                int opcion = scanner.nextInt();

                // CP2: Validación de rango [1-6]
                if (opcion >= 1 && opcion <= 6) {
                    if (opcion == 6) {
                        System.out.println("Saliendo de AgroCiclo.");
                        break;
                    }
                    // Ejecutor (placeholder para tus gestores)
                    System.out.println("Ejecutando lógica de la opción " + opcion + "...");
                } else {
                    System.out.println("--- Error: Opción fuera de rango (1-6). ---");
                }
            } else {
                System.out.println("--- Error: Ingrese solo valores numéricos. ---");
                scanner.next(); // Limpia la entrada inválida (CP3)
            }
        }
        scanner.close();
    }
}