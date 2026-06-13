package Negocio;

import Modelo.Parcela;

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
    public void registrarParcela(Parcela p) {
        System.out.println("[GestorSuelo] Guardando nueva parcela: " + p.getIdParcela());
}}