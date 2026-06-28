package Negocio;

import Modelo.Voluntario;
import Modelo.Trabajador;
import Modelo.Cultivos;
import java.util.ArrayList;
import java.util.Scanner;

public class GestorPersonal {

    // --- MÉTODOS DE REGISTRO ---
    public void registrarVoluntario(Cultivos activo, ArrayList<Voluntario> voluntarios, Scanner scanner, int idContador) {
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
            System.out.println("1. Asistente de Campo | 2. Monitor de Plagas | 3. Inspector de Suelos | 4. Guía Logístico");
            System.out.print("Opción (1-4): ");
            if (scanner.hasNextInt()) {
                opcionCargoVol = scanner.nextInt(); scanner.nextLine();
                cargoVol = switch (opcionCargoVol) {
                    case 1 -> "Asistente de Campo";
                    case 2 -> "Monitor de Plagas";
                    case 3 -> "Inspector de Suelos";
                    case 4 -> "Guía Logístico";
                    default -> "";
                };
                if (cargoVol.isEmpty()) System.out.println("--- Error: Opción fuera de rango (1-4). ---");
            } else { scanner.next(); scanner.nextLine(); }
        }
        Voluntario v = new Voluntario(Integer.toString(idContador), nomVol, contVol, activo.getId(), cargoVol);
        voluntarios.add(v);
        System.out.println("Voluntario registrado exitosamente con ID: " + v.getId());
    }

    public void registrarTrabajador(Cultivos activo, ArrayList<Trabajador> trabajadores, Scanner scanner, int idContador) {
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
            System.out.println("1. Admin. de Campo | 2. Técnico Agrícola | 3. Encargado Riego | 4. Sup. Cosecha");
            System.out.print("Opción (1-4): ");
            if (scanner.hasNextInt()) {
                opcionCargoTra = scanner.nextInt(); scanner.nextLine();
                cargoTra = switch (opcionCargoTra) {
                    case 1 -> "Administrador de Campo";
                    case 2 -> "Técnico Agrícola";
                    case 3 -> "Encargado de Riego";
                    case 4 -> "Supervisor de Cosecha";
                    default -> "";
                };
                if (cargoTra.isEmpty()) System.out.println("--- Error: Opción fuera de rango (1-4). ---");
            } else { scanner.next(); scanner.nextLine(); }
        }
        Trabajador t = new Trabajador(Integer.toString(idContador), nomTra, contTra, cargoTra, activo.getId());
        trabajadores.add(t);
        System.out.println("Trabajador registrado exitosamente con ID: " + t.getId());
    }

       // --- MENÚ PRINCIPAL DEL GESTOR ---
    public void gestionarPersonal(Cultivos activo, ArrayList<Voluntario> voluntarios, ArrayList<Trabajador> trabajadores, Scanner scanner, int[] contador) {
         
        int opcionPersonal = 0;
        while (opcionPersonal != 4) {
            System.out.println("\n--- MENU PERSONAL ---");
            System.out.println("1. Registrar Voluntario | 2. Registrar Trabajador | 3. Ver y editar | 4. Volver");
            System.out.print("Opción (1-4): ");

            if (scanner.hasNextInt()) {
                opcionPersonal = scanner.nextInt(); scanner.nextLine();
                switch (opcionPersonal) {
                    case 1 -> { registrarVoluntario(activo, voluntarios, scanner, contador[0]); contador[0]++; }
                    case 2 -> { registrarTrabajador(activo, trabajadores, scanner, contador[0]); contador[0]++; }
                    case 3 -> {
                        System.out.println("\n--- GESTIÓN DE PERSONAL ---");
                        if (voluntarios.isEmpty() && trabajadores.isEmpty()) {
                            System.out.println("No hay personal registrado.");
                        } else {
                            if (!voluntarios.isEmpty()) {
                                System.out.println("\n>> Voluntarios (Cultivo: " + activo.getNombre() + "):");
                                for (int i = 0; i < voluntarios.size(); i++) {
                                    Voluntario v = voluntarios.get(i);
                                    // FILTRO: Solo si coincide con el cultivo activo
                                    if (v.getIdCultivo() == activo.getId()) {
                                        System.out.println("V" + (i + 1) + ". ID: " + v.getId() + " | Nombre: " + v.getNombre() + " | Contacto: " + v.getContacto() + " | Cargo: " + v.getCargo());
                                    }
                                }
                            }
                            if (!trabajadores.isEmpty()) {
                                System.out.println("\n>> Trabajadores (Cultivo: " + activo.getNombre() + "):");
                                for (int i = 0; i < trabajadores.size(); i++) {
                                    Trabajador t = trabajadores.get(i);
                                    // FILTRO: Solo si coincide con el cultivo activo
                                    if (t.getIdCultivo() == activo.getId()) {
                                        System.out.println("T" + (i + 1) + ". ID: " + t.getId() + " | Nombre: " + t.getNombre() + " | Contacto: " + t.getContacto() + " | Cargo: " + t.getCargo());
                                    }
                                }
                            }
                            
                            // ... resto del código de respuestas (s/n) y edición ...
                            String respuesta = "";
                            while (!respuesta.equals("s") && !respuesta.equals("n")) {
                                System.out.print("\n¿Desea editar a alguien? (s/n): ");
                                respuesta = scanner.next().toLowerCase();
                                if (!respuesta.equals("s") && !respuesta.equals("n")) {
                                    System.out.println("--- Error: Ingrese 's' para sí o 'n' para no. ---");
                                }
                            }
                            
                            if (respuesta.equals("s")) {
                                String codigo = "";
                                boolean codigoValido = false;
                                                    while (!codigoValido) {
                                    System.out.print("Ingrese el código (ej: V1 o T1): ");
                                    codigo = scanner.next().toUpperCase();
                                    scanner.nextLine();
                                    
                                    if (codigo.matches("[VT][0-9]+")) {
                                        int idx = Integer.parseInt(codigo.substring(1)) - 1;
                                        if (codigo.startsWith("V") && idx >= 0 && idx < voluntarios.size()) {
                                            // VALIDAR CULTIVO
                                            if (voluntarios.get(idx).getIdCultivo() == activo.getId()) {
                                                codigoValido = true;
                                            } else {
                                                System.out.println("--- Error: El voluntario no pertenece a este cultivo. ---");
                                            }
                                        } else if (codigo.startsWith("T") && idx >= 0 && idx < trabajadores.size()) {
                                            // VALIDAR CULTIVO
                                            if (trabajadores.get(idx).getIdCultivo() == activo.getId()) {
                                                codigoValido = true;
                                            } else {
                                                System.out.println("--- Error: El trabajador no pertenece a este cultivo. ---");
                                            }
                                        } else {
                                            System.out.println("--- Error: El código no existe. ---");
                                        }
                                    } else {
                                        System.out.println("--- Error: Formato inválido. Use V1, T2, etc. ---");
                                    }
                                }
                                
                                if (codigo.startsWith("V")) {
                                    int idx = Integer.parseInt(codigo.substring(1)) - 1;
                                    Voluntario v = voluntarios.get(idx);
                                    System.out.println("\n--- EDITAR VOLUNTARIO ---");
                                    System.out.println("1. Nombre");
                                    System.out.println("2. Contacto");
                                    System.out.println("3. Cargo");
                                    System.out.println("4. Salir");
                                    
                                    int opcionEditar;
                                    do {
                                        opcionEditar = -1;
                                        while (opcionEditar < 1 || opcionEditar > 4) {
                                            System.out.print("Seleccione campo a editar (1-4): ");
                                            if (scanner.hasNextInt()) {
                                                opcionEditar = scanner.nextInt();
                                                scanner.nextLine();
                                                if (opcionEditar < 1 || opcionEditar > 4) {
                                                    System.out.println("--- Error: Opción fuera de rango (1-4). ---");
                                                }
                                            } else {
                                                System.out.println("--- Error: Ingrese un número válido. ---");
                                                scanner.next();
                                                scanner.nextLine();
                                            }
                                        }
                                        
                                        switch (opcionEditar) {
                                            case 1 -> {
                                                String nomVol = "";
                                                while (nomVol.isEmpty()) {
                                                    System.out.print("Nuevo nombre (solo letras): ");
                                                    nomVol = scanner.nextLine();
                                                    if (!nomVol.matches("[a-zA-ZáéíóúÁÉÍÓÚñÑ\\s]+")) {
                                                        System.out.println("--- Error: El nombre no puede contener números. ---");
                                                        nomVol = "";
                                                    }
                                                }
                                                v.setNombre(nomVol);
                                                System.out.println("Nombre actualizado!");
                                            }
                                            case 2 -> {
                                                String contVol = "";
                                                while (contVol.isEmpty()) {
                                                    System.out.print("Nuevo contacto (solo números): ");
                                                    contVol = scanner.nextLine();
                                                    if (!contVol.matches("[0-9]+")) {
                                                        System.out.println("--- Error: El contacto solo puede contener números. ---");
                                                        contVol = "";
                                                    }
                                                }
                                                v.setContacto(contVol);
                                                System.out.println("Contacto actualizado!");
                                            }
                                            case 3 -> {
                                                System.out.println("1. Asistente de Campo | 2. Monitor de Plagas | 3. Inspector de Suelos | 4. Guía Logístico");
                                                int c;
                                                while (true) {
                                                    if (scanner.hasNextInt()) {
                                                        c = scanner.nextInt();
                                                        scanner.nextLine();
                                                        if (c >= 1 && c <= 4) break;
                                                        System.out.println("--- Error: Opción fuera de rango (1-4). ---");
                                                    } else {
                                                        System.out.println("--- Error: Ingrese un número válido. ---");
                                                        scanner.next();
                                                        scanner.nextLine();
                                                    }
                                                }
                                                String nuevoCargo = switch(c){case 1->"Asistente de Campo";case 2->"Monitor de Plagas";case 3->"Inspector de Suelos";default->"Guía Logístico";};
                                                v.setCargo(nuevoCargo);
                                                System.out.println("Cargo actualizado!");
                                            }
                                            case 4 -> System.out.println("Saliendo de edición...");
                                        }
                                    } while (opcionEditar != 4);
                                } else if (codigo.startsWith("T")) {
                                    int idx = Integer.parseInt(codigo.substring(1)) - 1;
                                    Trabajador t = trabajadores.get(idx);
                                    System.out.println("\n--- EDITAR TRABAJADOR ---");
                                    System.out.println("1. Nombre");
                                    System.out.println("2. Contacto");
                                    System.out.println("3. Cargo");
                                    System.out.println("4. Salir");
                                    
                                    int opcionEditar;
                                    do {
                                        opcionEditar = -1;
                                        while (opcionEditar < 1 || opcionEditar > 4) {
                                            System.out.print("Seleccione campo a editar (1-4): ");
                                            if (scanner.hasNextInt()) {
                                                opcionEditar = scanner.nextInt();
                                                scanner.nextLine();
                                                if (opcionEditar < 1 || opcionEditar > 4) {
                                                    System.out.println("--- Error: Opción fuera de rango (1-4). ---");
                                                }
                                            } else {
                                                System.out.println("--- Error: Ingrese un número válido. ---");
                                                scanner.next();
                                                scanner.nextLine();
                                            }
                                        }
                                        
                                        switch (opcionEditar) {
                                            case 1 -> {
                                                String nomTra = "";
                                                while (nomTra.isEmpty()) {
                                                    System.out.print("Nuevo nombre (solo letras): ");
                                                    nomTra = scanner.nextLine();
                                                    if (!nomTra.matches("[a-zA-ZáéíóúÁÉÍÓÚñÑ\\s]+")) {
                                                        System.out.println("--- Error: El nombre no puede contener números. ---");
                                                        nomTra = "";
                                                    }
                                                }
                                                t.setNombre(nomTra);
                                                System.out.println("Nombre actualizado!");
                                            }
                                            case 2 -> {
                                                String contTra = "";
                                                while (contTra.isEmpty()) {
                                                    System.out.print("Nuevo contacto (solo números): ");
                                                    contTra = scanner.nextLine();
                                                    if (!contTra.matches("[0-9]+")) {
                                                        System.out.println("--- Error: El contacto solo puede contener números. ---");
                                                        contTra = "";
                                                    }
                                                }
                                                t.setContacto(contTra);
                                                System.out.println("Contacto actualizado!");
                                            }
                                            case 3 -> {
                                                System.out.println("1. Administrador de Campo | 2. Técnico Agrícola | 3. Encargado de Riego | 4. Supervisor de Cosecha");
                                                int c;
                                                while (true) {
                                                    if (scanner.hasNextInt()) {
                                                        c = scanner.nextInt();
                                                        scanner.nextLine();
                                                        if (c >= 1 && c <= 4) break;
                                                        System.out.println("--- Error: Opción fuera de rango (1-4). ---");
                                                    } else {
                                                        System.out.println("--- Error: Ingrese un número válido. ---");
                                                        scanner.next();
                                                        scanner.nextLine();
                                                    }
                                                }
                                                String nuevoCargo = switch(c){case 1->"Administrador de Campo";case 2->"Técnico Agrícola";case 3->"Encargado de Riego";default->"Supervisor de Cosecha";};
                                                t.setCargo(nuevoCargo);
                                                System.out.println("Cargo actualizado!");
                                            }
                                            case 4 -> System.out.println("Saliendo de edición...");
                                        }
                                    } while (opcionEditar != 4);
                                }
                                System.out.println("¡Edición completada!");
                            }
                        }
                    }
                    case 4 -> System.out.println("Regresando...");
                    default -> System.out.println("--- Error: Opción fuera de rango (1-4). ---");
                }
            } else {
                System.out.println("--- Error: Entrada inválida. ---");
                scanner.next();
                scanner.nextLine();
            }
        
    }
    }
    }
    
 
    
    
    
