import Modelo.*;
import Negocio.*;
import Interfaz.*;
import Modelo.ContextoAgroCiclo;
import Modelo.ElementoTaxonomico;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Locale;
public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in).useLocale(Locale.US);
        ArrayList<ElementoTaxonomico> misSelecciones = new ArrayList<>();
        boolean salirDefinitivo = false;


        GestorSuelo gestorSue = new GestorSuelo();
        GestorPersonal gestorPer = new GestorPersonal();
        GestorEstadistica gestorEst = new GestorEstadistica();

        System.out.println("=== BIENVENIDO A AGROCICLO ===");


        while (!salirDefinitivo) {
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
        ContextoAgroCiclo.setTaxonomia(taxonomia);
        System.out.println(">>> Sistema configurado exitosamente para: " + taxonomia);

            System.out.print("Nombre del cultivo: ");
            scanner.nextLine();
            String nombreCultivo = scanner.nextLine();

            double km2 = -1;
            while (km2 <= 0) {
                System.out.print("Ingrese la cantidad en km2 (mayor a 0): ");
                if (scanner.hasNextDouble()) {
                    km2 = scanner.nextDouble();
                    if (km2 <= 0) System.out.println("Error: Debe ser positivo.");
                } else {
                    System.out.println("Error: Ingrese un número válido.");
                    scanner.next();
                }
            }

            misSelecciones.add(new ElementoTaxonomico(taxonomia, nombreCultivo, (int)km2));
            System.out.println("¡Objeto creado correctamente!");
            System.out.println("\n=== SELECCIÓN DE CULTIVO ACTIVO ===");
            System.out.println("Actualmente hay " + misSelecciones.size() + " registrado(s).");
            for (int i = 0; i < misSelecciones.size(); i++) {
                System.out.println((i + 1) + ". " + misSelecciones.get(i).getNombre() + " (" + misSelecciones.get(i).getCategoria() + ")");
            }

            // Selección de cultivo
            int seleccionIdx = -1;
            while (seleccionIdx < 0 || seleccionIdx >= misSelecciones.size()) {
                System.out.print("Elija el número del cultivo para trabajar (1-" + misSelecciones.size() + "): ");
                if (scanner.hasNextInt()) {
                    seleccionIdx = scanner.nextInt() - 1;
                    if (seleccionIdx < 0 || seleccionIdx >= misSelecciones.size()) {
                        System.out.println("--- Error: El número seleccionado no está en la lista. ---");
                    }
                } else {
                    System.out.println("--- Error: Entrada inválida. Por favor ingrese un número. ---");
                    scanner.next(); // Limpiar entrada
                }
            }

            ElementoTaxonomico activo = misSelecciones.get(seleccionIdx);
            ContextoAgroCiclo.setTaxonomia(activo.getCategoria()); // Importante para el contexto
            System.out.println(">>> Trabajando con: " + activo.getNombre());

            boolean volverATaxonomia = false;
            while (!volverATaxonomia && !salirDefinitivo) {
                System.out.println("\n--- MENU PRINCIPAL (" + activo.getNombre() + ") ---");
                System.out.println("1. Suelo | 2. Personal | 3. Biodata | 4. Estadística | 5. Optimizar | 6. Volver a Taxonomía | 7. Salir");
                System.out.print("Elija una opción (1-7): ");

                if (scanner.hasNextInt()) {
                    int opcion = scanner.nextInt();
                    switch (opcion) {
                        case 1, 2, 3, 4, 5 -> System.out.println("Ejecutando lógica de opción " + opcion);
                        case 6 -> {
                            System.out.println("Regresando a Taxonomía...");
                            volverATaxonomia = true; // Esto rompe el bucle del menú y vuelve al while superior
                        }
                        case 7 -> {
                            System.out.println("Saliendo de AgroCiclo.");
                            volverATaxonomia = true;
                            salirDefinitivo = true; // Esto rompe el menú Y el bucle exterior
                        }
                        default -> System.out.println("--- Error: Opción inválida. ---");
                    }
                } else {
                    System.out.println("--- Error: Entrada inválida. ---");
                    scanner.next();
                }
            }
        } // Fin del while (!salirDefinitivo)
            scanner.close();
        }
    }


