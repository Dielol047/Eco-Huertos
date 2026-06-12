import Modelo.*;
import Negocio.*;
import Interfaz.*;
import Modelo.ContextoAgroCiclo;

import java.util.Scanner;
public class Main {
    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        GestorSuelo gestorSue = new GestorSuelo();
        GestorPersonal gestorPer = new GestorPersonal();
        GestorEstadistica gestorEst = new GestorEstadistica();

        System.out.println("=== BIENVENIDO A AGROCICLO ===");


        boolean seleccionValida = false;
        String taxonomia = "";

        // Cambiamos la lógica: el ciclo se repite mientras NO hayamos seleccionado una opción válida (1-5)
        while (taxonomia.equals("")) {

            System.out.println("Seleccione la rama taxonómica de trabajo:");
            System.out.println("1. Solanum | 2. Musáceas | 3. Cucurbitáceas | 4. Leguminosas | 5. Brasicáceas | 6. Guia taxonomica");
            System.out.print("Opción (1-6): ");

            if (scanner.hasNextInt()) {
                int sel = scanner.nextInt();

                if (sel >= 1 && sel <= 5) {
                    // Si elige del 1 al 5, asignamos la taxonomia (esto romperá el while)
                    taxonomia = switch(sel) {
                        case 1 -> "Solanum";
                        case 2 -> "Musáceas";
                        case 3 -> "Cucurbitáceas";
                        case 4 -> "Leguminosas";
                        case 5 -> "Brasicáceas";
                        default -> "";
                    };
                } else if (sel == 6) {
                    // Si elige 6, imprimimos la guía y NO asignamos nada a taxonomia
                    System.out.println("\n--- CATÁLOGO TAXONÓMICO Y CARACTERÍSTICAS ---");
                    System.out.println("1. Solanum: Tomate, papa, pimiento, berenjena.");
                    System.out.println("2. Musáceas: Plátano, banano, guineo.");
                    System.out.println("3. Cucurbitáceas: Zapallo, calabaza, pepino, melón, sandía.");
                    System.out.println("4. Leguminosas: Frijol, arveja, haba, lenteja.");
                    System.out.println("5. Brasicáceas: Brócoli, col, coliflor, rábano, nabos.");
                    System.out.println("----------------------------------------------\n");
                } else {
                    System.out.println("--- Error: Opción fuera de rango (1-6). ---");
                }
            } else {
                System.out.println("--- Error: Entrada inválida. Ingrese un número. ---");
                scanner.next();
            }
        }

        // Una vez que el while termina porque taxonomia dejó de estar vacía, continuamos:
        ContextoAgroCiclo.setTaxonomia(taxonomia);
        System.out.println(">>> Sistema configurado exitosamente para: " + taxonomia);
        ContextoAgroCiclo.setTaxonomia(taxonomia);
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