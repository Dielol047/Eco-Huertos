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
        GestorBiodata gestorBio = new GestorBiodata();
        System.out.println("=== BIENVENIDO A AGROCICLO ===");

        while (!salirDefinitivo) {
                        String taxonomia = "";
            while (taxonomia.equals("")) {
                System.out.println("Seleccione la rama taxonómica de trabajo:");
                System.out.println("1. Solanum | 2. Musáceas | 3. Cucurbitáceas | 4. Leguminosas | 5. Brasicáceas | 6. Guia taxonomica");
                System.out.print("Opción (1-6): ");

                try {
                    String entrada = scanner.next();
                    int sel = Integer.parseInt(entrada);

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
                        throw new IllegalArgumentException("Opción fuera de rango (1-6).");
                    }
                } catch (NumberFormatException e) {
                    System.out.println("--- Error: Entrada inválida. Ingrese un número entero. ---");
                } catch (IllegalArgumentException e) {
                    System.out.println("--- Error: " + e.getMessage() + " ---");
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
                try {
                    String entradaTmp = scanner.next();
                    km2 = Double.parseDouble(entradaTmp);
                    if (km2 <= 0) throw new IllegalArgumentException("El área debe ser mayor a 0.");
                } catch (NumberFormatException e) {
                    System.out.println("Error: Ingrese un valor numérico válido (use el punto para decimales).");
                } catch (IllegalArgumentException e) {
                    System.out.println("Error: " + e.getMessage());
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
    
                System.out.println("(0). Añadir nuevo cultivo,(-1).Salir del codigo");
                System.out.print("Elija una opción: ");
                                try {
                    String entradaStr = scanner.next();
                    int entrada = Integer.parseInt(entradaStr);
                    
                    if (entrada == -1) {
                        salirDefinitivo = true;
                    } else if (entrada == 0) {
                        volverATaxonomia = true;
                    } else {
                        int seleccionIdx = entrada - 1;
                        if (seleccionIdx >= 0 && seleccionIdx < misSelecciones.size()) {
                            Cultivos activo = misSelecciones.get(seleccionIdx);
                            ContextoAgroCiclo.setTaxonomia(activo.getCategoria());
                            System.out.println(">>> Trabajando con: " + activo.getNombre());
                            ejecutarMenuPrincipal(activo, gestorSuelo, gestorPer, gestorEst,gestorBio, parcelas, voluntarios, trabajadores, scanner, contadorPersonal);
                        } else {
                            throw new IndexOutOfBoundsException("Número fuera de rango.");
                        }
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Error: Ingrese un número entero.");
                } catch (IndexOutOfBoundsException e) {
                    System.out.println("Error: " + e.getMessage());
                }
            } // Cierra while (!volverATaxonomia...)
        } // Cierra while (!salirDefinitivo)
    } // Cierra main

   
    
    public static void ejecutarMenuPrincipal(Cultivos activo, GestorSuelo gs, GestorPersonal gp, GestorEstadistica ge,GestorBiodata gb, ArrayList<Parcela> p, ArrayList<Voluntario> v, ArrayList<Trabajador> t, Scanner sc, int[] cp) {
        while (true) {
            System.out.println("\n--- Datos Cultivos (" + activo.getNombre() + ") ---");
            System.out.println("1. Suelo | 2. Personal | 3. Clima | 4. Realizar Reporte | 5. Volver a Cultivos");
            System.out.print("Opción: ");
            
            try {
                String entrada = sc.next();
                int op = Integer.parseInt(entrada);

                switch (op) {
                    case 1 -> gs.gestionarSuelo(activo, p, sc);
                    case 2 -> gp.gestionarPersonal(activo, v, t, sc, cp);
                    case 3 -> ge.mostrarEstadisticasClimaticas(activo, sc);
                    case 4 -> {
                        System.out.println("\n--- Imprimiendo reporte ---");
                        ge.imprimirReportePrediccion(activo);
                         gs.imprimirReportePrediccion(activo);
                         gb.imprimirReportePrediccion(activo);
                         gp.imprimirReportePrediccion(activo);
                    }
                    case 5 -> { return; }
                    default -> System.out.println("--- Error: Opción inválida. Elija entre 1 y 5. ---");
                }
            } catch (NumberFormatException e) {
                System.out.println("--- Error: Entrada inválida. Por favor ingrese un número entero. ---");
            } catch (Exception e) {
                System.out.println("--- Error inesperado: " + e.getMessage() + " ---");
            }
        }
    }
}