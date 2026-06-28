import Modelo.*;
import Negocio.*;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Locale;
import Negocio.GestorBiodata;

public class Main {
       static boolean salirDefinitivo = false;
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in).useLocale(Locale.US);
        ArrayList<Cultivos> misSelecciones = new ArrayList<>();
        ArrayList<Parcela> parcelas = new ArrayList<>();
        ArrayList<Voluntario> voluntarios = new ArrayList<>();
ArrayList<Trabajador> trabajadores = new ArrayList<>();
        int[] contadorPersonal = {1};
       

        GestorSuelo gestorSuelo = new GestorSuelo();
        GestorPersonal gestorPer = new GestorPersonal();
        GestorEstadistica gestorEst = new GestorEstadistica();

        System.out.println("=== BIENVENIDO A AGROCICLO ===");

        while (!salirDefinitivo) {
            String taxonomia = "";
            while (taxonomia.equals("")) {
                System.out.println("Seleccione la rama taxonómica de trabajo:");
                System.out.println("1. Solanum | 2. Musáceas | 3. Cucurbitáceas | 4. Leguminosas | 5. Brasicáceas | 6. Guia taxonomica");
                System.out.print("Opción (1-6): ");

                if (scanner.hasNextInt()) {
                    int sel = scanner.nextInt();
                    if (sel >= 1 && sel <= 5) {
                        taxonomia = switch(sel) {
                            case 1 -> "Solanum";
                            case 2 -> "Musáceas";
                            case 3 -> "Cucurbitáceas";
                            case 4 -> "Leguminosas";
                            case 5 -> "Brasicáceas";
                            default -> "";
                        };
                    } else if (sel == 6) {
                        System.out.println("\n--- CATÁLOGO TAXONÓMICO Y CARACTERÍSTICAS ---");
                        System.out.println("1. Solanaceae: Tomate, papa, pimiento, berenjena.");
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

            
                        // ... después de crear el objeto Cultivos en el Main
            Cultivos nuevoCultivo = new Cultivos(taxonomia, nombreCultivo, km2);
            misSelecciones.add(nuevoCultivo);
            
         
             boolean volverATaxonomia = false;
            while (!volverATaxonomia && !salirDefinitivo) {
                System.out.println("\n--- LISTADO DE CULTIVOS ---");
                for (int i = 0; i < misSelecciones.size(); i++) {
                    Cultivos c = misSelecciones.get(i);
                    System.out.println((i + 1) + ". ID: " + c.getId() + " - " + c.getNombre() + " (" + c.getCategoria() + ")");
                }
                System.out.print("Elija el número del cultivo (1-" + misSelecciones.size() + "): ");
    
                System.out.println("0. Añadir nuevo cultivo");
                System.out.print("Elija una opción: ");
                
                if (scanner.hasNextInt()) {
                    int seleccionIdx = scanner.nextInt() - 1;
                    if (seleccionIdx == -1) {
                        volverATaxonomia = true;
                    } else if (seleccionIdx >= 0 && seleccionIdx < misSelecciones.size()) {
                        Cultivos activo = misSelecciones.get(seleccionIdx);
                        ContextoAgroCiclo.setTaxonomia(activo.getCategoria());
                        System.out.println(">>> Trabajando con: " + activo.getNombre());
                        ejecutarMenuPrincipal(activo, gestorSuelo, gestorPer, gestorEst, parcelas, voluntarios, trabajadores, scanner, contadorPersonal);
                    } else {
                        System.out.println("--- Error: Número fuera de rango. ---");
                    }
                } else {
                
                    System.out.println("--- Error: Entrada inválida. ---");
                    scanner.next();
                }
            }
        } // cierra while (!salirDefinitivo)
    } // cierra main

           public static void ejecutarMenuPrincipal(Cultivos activo, GestorSuelo gs, GestorPersonal gp, GestorEstadistica ge, ArrayList<Parcela> p, ArrayList<Voluntario> v, ArrayList<Trabajador> t, Scanner sc, int[] cp) {
        while (true) {
            System.out.println("\n--- Datos Cultivos (" + activo.getNombre() + ") ---");
            System.out.println("1. Suelo | 2. Personal | 3. Estadística | 4. Optimizar | 5. Volver a Cultivos | 6. Salir");
            System.out.print("Opción: ");
            if (sc.hasNextInt()) {
                int op = sc.nextInt();
                switch (op) {
                     case 1 -> gs.gestionarSuelo(activo, p, sc);
                    case 2 -> gp.gestionarPersonal(activo, v, t, sc, cp);
                    case 3 -> ge.mostrarEstadisticasClimaticas(activo, sc);
                    case 4 -> System.out.println("Optimizar...");
                    case 5 -> { return; }
                    case 6 -> { salirDefinitivo = true; return; }
                    default -> System.out.println("--- Error: Opción inválida. ---");
                }
            } else {
                sc.next();
            }
        }
    }
} // cierra class Main