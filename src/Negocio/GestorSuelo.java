package Negocio;

import Modelo.Parcela;
import Modelo.Cultivos; // <--- TE FALTA ESTA
import java.util.ArrayList; // <--- Y ESTA
import java.util.Scanner;   // <--- Y ESTA

public class GestorSuelo {
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
        // Aquí puedes agregar tu lógica condicional con los nuevos atributos
        if (!p.getDisponible()) {
            System.out.println("-> Alerta: La parcela no está disponible.");
            return false;
        }

        return true;
    }
    // Agrega este método en GestorSuelo.java
public void gestionarSuelo(Cultivos activo, ArrayList<Parcela> parcelas, java.util.Scanner scanner) {
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
                    System.out.println("Arenoso: Seco, filtra rápido.\nArcilloso: Retiene agua, difícil manejo.\nFranco: Ideal.\nLimoso: Propensos a erosión.\nOrgánico: Muy fértil.");
                }
                default -> System.out.println("--- Error: Opción 1-6. ---");
            }
        } else {
            System.out.println("--- Error: Entrada inválida. ---");
            scanner.next();
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
    public void registrarParcela(Parcela p) {
        System.out.println("[GestorSuelo] Guardando nueva parcela: " + p.getIdParcela());
}}