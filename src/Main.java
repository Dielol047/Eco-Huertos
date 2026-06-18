import Modelo.*;
import Negocio.*;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Locale;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in).useLocale(Locale.US);
        ArrayList<Cultivos> misSelecciones = new ArrayList<>();
        ArrayList<Parcela> parcelas = new ArrayList<>();
        ArrayList<Voluntario> voluntarios = new ArrayList<>();
        ArrayList<Trabajador> trabajadores = new ArrayList<>();
        int contadorPersonal = 1;
        boolean salirDefinitivo = false;

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

            misSelecciones.add(new Cultivos(taxonomia, nombreCultivo, (int)km2));
            System.out.println("¡Objeto creado correctamente!");
            System.out.println("\n=== SELECCIÓN DE CULTIVO ACTIVO ===");
            
            for (int i = 0; i < misSelecciones.size(); i++) {
                Cultivos c = misSelecciones.get(i);
                System.out.println((i + 1) + ". ID: " + c.getId() + " - " + c.getNombre() + " (" + c.getCategoria() + ")");
            }

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
                    scanner.next();
                }
            }

            Cultivos activo = misSelecciones.get(seleccionIdx);
            ContextoAgroCiclo.setTaxonomia(activo.getCategoria());
            System.out.println(">>> Trabajando con: " + activo.getNombre());

            boolean volverATaxonomia = false;
            while (!volverATaxonomia && !salirDefinitivo) {
                System.out.println("\n--- MENU PRINCIPAL (" + activo.getNombre() + ") ---");
                System.out.println("1. Suelo | 2. Personal | 3. Biodata | 4. Estadística | 5. Optimizar | 6. Volver a Taxonomía | 7. Salir| 8. Ver Parcelas");
                System.out.print("Elija una opción: ");

                if (scanner.hasNextInt()) {
                    int opcion = scanner.nextInt();
                    switch (opcion) {
                        case 1:
                            int opcionSuelo = 0;
                            String suelo = "";
                            while (suelo.isEmpty()) {
                                System.out.println("\n--- SELECCIONE TIPO DE SUELO ---");
                                System.out.println("1. Arenoso");
                                System.out.println("2. Arcilloso");
                                System.out.println("3. Franco");
                                System.out.println("4. Limoso");
                                System.out.println("5. Organico");
                                System.out.println("6. Ver información de suelos");
                                System.out.print("Opción (1-6): ");

                                if (scanner.hasNextInt()) {
                                    opcionSuelo = scanner.nextInt();
                                    switch (opcionSuelo) {
                                        case 1 -> suelo = "Arenoso";
                                        case 2 -> suelo = "Arcilloso";
                                        case 3 -> suelo = "Franco";
                                        case 4 -> suelo = "Limoso";
                                        case 5 -> suelo = "Organico";
                                        case 6 -> {
                                            System.out.println("\n--- INFORMACIÓN DE SUELOS ---");
                                            System.out.println("1. Suelo Arenoso: Suelen ser secos, filtran muy rápido el agua y tienen pocos nutrientes, pero se calientan rápido.");
                                            System.out.println("2. Suelo Arcilloso: Retienen mucho el agua y los nutrientes, pero tienden a compactarse y ser pesados (difíciles de trabajar).");
                                            System.out.println("3. Suelo Franco (Limoso/Arcillo-Arenoso): Es el 'Golden Standard' o 'suelo ideal'. Es una mezcla equilibrada que retiene suficiente humedad pero permite el paso del aire y las raíces.");
                                            System.out.println("4. Suelo Limoso: Tienen partículas de tamaño intermedio, retienen humedad pero son propensos a la erosión si no tienen cobertura vegetal.");
                                            System.out.println("5. Suelo Orgánico (Turba o Humífero): Muy negros, muy fértiles y retienen muchísima humedad. Típicos de zonas que antes eran pantanosas o tienen mucha materia orgánica.");
                                            System.out.println("------------------------------\n");
                                        }
                                        default -> System.out.println("--- Error: Opción fuera de rango (1-6). ---");
                                    }
                                } else {
                                    System.out.println("--- Error: Entrada inválida. Ingrese un número. ---");
                                    scanner.next();
                                }
                            }

                            Parcela nuevaParcela = new Parcela(suelo, true, activo);
                            parcelas.add(nuevaParcela);
                            gestorSuelo.registrarParcela(nuevaParcela);
                            break;
                                                                                                               
                        case 2:
                            int opcionPersonal = 0;
                            while (opcionPersonal != 4) {
                                System.out.println("\n--- MENU PERSONAL ---");
                                System.out.println("1. Registrar Voluntario");
                                System.out.println("2. Registrar Trabajador");
                                System.out.println("3. Ver información de trabajadores");
                                System.out.println("4. Volver al menú principal");
                                System.out.print("Opción (1-4): ");

                                if (scanner.hasNextInt()) {
                                    opcionPersonal = scanner.nextInt();
                                    scanner.nextLine();

                                    switch (opcionPersonal) {
                                        case 1 -> {
                                            String nomVol = "";
                                            while (nomVol.isEmpty()) {
                                                System.out.print("Nombre (solo letras): ");
                                                nomVol = scanner.nextLine();
                                                if (!nomVol.matches("[a-zA-ZáéíóúÁÉÍÓÚñÑ\\s]+")) {
                                                    System.out.println("--- Error: El nombre no puede contener números. ---");
                                                    nomVol = "";
                                                }
                                            }

                                            String contVol = "";
                                            while (contVol.isEmpty()) {
                                                System.out.print("Contacto (solo números): ");
                                                contVol = scanner.nextLine();
                                                if (!contVol.matches("[0-9]+")) {
                                                    System.out.println("--- Error: El contacto solo puede contener números. ---");
                                                    contVol = "";
                                                }
                                            }

                                            int opcionCargoVol = 0;
                                            String cargoVol = "";
                                            while (cargoVol.isEmpty()) {
                                                System.out.println("\n--- SELECCIONE CARGO DE VOLUNTARIO ---");
                                                System.out.println("1. Asistente de Campo: Apoyo operativo básico.");
                                                System.out.println("2. Monitor de Plagas: Vigilancia y reporte de anomalías.");
                                                System.out.println("3. Inspector de Suelos: Recolección de muestras y datos.");
                                                System.out.println("4. Guía Logístico: Apoyo en inventario y movimiento de materiales.");
                                                System.out.print("Opción (1-4): ");

                                                if (scanner.hasNextInt()) {
                                                    opcionCargoVol = scanner.nextInt();
                                                    scanner.nextLine();
                                                    cargoVol = switch (opcionCargoVol) {
                                                        case 1 -> "Asistente de Campo";
                                                        case 2 -> "Monitor de Plagas";
                                                        case 3 -> "Inspector de Suelos";
                                                        case 4 -> "Guía Logístico";
                                                        default -> "";
                                                    };
                                                    if (cargoVol.isEmpty()) {
                                                        System.out.println("--- Error: Opción fuera de rango (1-4). ---");
                                                    }
                                                } else {
                                                    System.out.println("--- Error: Ingrese un número válido. ---");
                                                    scanner.next();
                                                    scanner.nextLine();
                                                }
                                            }

                                            Voluntario v = new Voluntario(Integer.toString(contadorPersonal++), nomVol, contVol, activo.getId(), cargoVol);
                                            voluntarios.add(v);
                                            gestorPer.asignarTarea(v, "Registro inicial");
                                            System.out.println("Voluntario registrado exitosamente con ID: " + v.getId());
                                        }
                                        case 2 -> {
                                            String nomTra = "";
                                            while (nomTra.isEmpty()) {
                                                System.out.print("Nombre (solo letras): ");
                                                nomTra = scanner.nextLine();
                                                if (!nomTra.matches("[a-zA-ZáéíóúÁÉÍÓÚñÑ\\s]+")) {
                                                    System.out.println("--- Error: El nombre no puede contener números. ---");
                                                    nomTra = "";
                                                }
                                            }

                                            String contTra = "";
                                            while (contTra.isEmpty()) {
                                                System.out.print("Contacto (solo números): ");
                                                contTra = scanner.nextLine();
                                                if (!contTra.matches("[0-9]+")) {
                                                    System.out.println("--- Error: El contacto solo puede contener números. ---");
                                                    contTra = "";
                                                }
                                            }

                                            int opcionCargoTra = 0;
                                            String cargoTra = "";
                                            while (cargoTra.isEmpty()) {
                                                System.out.println("\n--- SELECCIONE CARGO DE TRABAJADOR ---");
                                                System.out.println("1. Administrador de Campo: Encargado de la gestión general.");
                                                System.out.println("2. Técnico Agrícola: Especialista en cultivos y optimización.");
                                                System.out.println("3. Encargado de Riego: Especialista en sistemas hídricos.");
                                                System.out.println("4. Supervisor de Cosecha: Encargado de la recolección y calidad.");
                                                System.out.print("Opción (1-4): ");

                                                if (scanner.hasNextInt()) {
                                                    opcionCargoTra = scanner.nextInt();
                                                    scanner.nextLine();
                                                    cargoTra = switch (opcionCargoTra) {
                                                        case 1 -> "Administrador de Campo";
                                                        case 2 -> "Técnico Agrícola";
                                                        case 3 -> "Encargado de Riego";
                                                        case 4 -> "Supervisor de Cosecha";
                                                        default -> "";
                                                    };
                                                    if (cargoTra.isEmpty()) {
                                                        System.out.println("--- Error: Opción fuera de rango (1-4). ---");
                                                    }
                                                } else {
                                                    System.out.println("--- Error: Ingrese un número válido. ---");
                                                    scanner.next();
                                                    scanner.nextLine();
                                                }
                                            }

                                            Trabajador t = new Trabajador(Integer.toString(contadorPersonal++), nomTra, contTra, cargoTra, activo.getId());
                                            trabajadores.add(t);
                                            System.out.println("Trabajador registrado exitosamente con ID: " + t.getId());
                                        }
                                        case 3 -> {
                                            System.out.println("\n--- INFORMACIÓN DE TRABAJADORES ---");
                                            if (voluntarios.isEmpty() && trabajadores.isEmpty()) {
                                                System.out.println("No hay personal registrado.");
                                            } else {
                                                if (!voluntarios.isEmpty()) {
                                                    System.out.println("\n>> Voluntarios:");
                                                    for (Voluntario v : voluntarios) {
                                                        System.out.println("  ID: " + v.getId() + " | ID Cultivo: " + v.getIdCultivo() + " | Nombre: " + v.getNombre() + " | Contacto: " + v.getContacto() + " | Cargo: " + v.getCargo());
                                                    }
                                                }
                                                if (!trabajadores.isEmpty()) {
                                                    System.out.println("\n>> Trabajadores:");
                                                    for (Trabajador t : trabajadores) {
                                                        System.out.println("  ID: " + t.getId() + " | ID Cultivo: " + t.getIdCultivo() + " | Nombre: " + t.getNombre() + " | Contacto: " + t.getContacto() + " | Cargo: " + t.getCargo());
                                                    }
                                                }
                                            }
                                            System.out.println("------------------------------\n");
                                        }
                                        case 4 -> {}
                                        default -> System.out.println("--- Error: Opción fuera de rango (1-4). ---");
                                    }
                                } else {
                                    System.out.println("--- Error: Entrada inválida. Ingrese un número. ---");
                                    scanner.next();
                                }
                            }
                            break;
                        case 6: 
                            volverATaxonomia = true;
                            break;

                        case 7: 
                            volverATaxonomia = true;
                            salirDefinitivo = true;
                            break;

                        case 8:
                            if (parcelas.isEmpty()) {
                                System.out.println("--- Error: No hay parcelas registradas. ---");
                            } else {
                                for (Parcela p : parcelas) {
                                    Pruebas.imprimirEstadoParcela(p);
                                }
                            }
                            break;

                        default: System.out.println("--- Error: Opción inválida. ---");
                    }
                } else {
                    System.out.println("--- Error: Entrada inválida. ---");
                    scanner.next();
                }
            }
        }
        scanner.close();
    }
}