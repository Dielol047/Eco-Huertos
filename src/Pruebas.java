

import Modelo.Cultivos;
import Modelo.Parcela;


public class Pruebas {
    public static void imprimirEstadoParcela(Parcela p) {
        System.out.println("--- DETALLE DEL OBJETO PARCELA ---");
        if (p == null) {
            System.out.println("La parcela no existe.");
            return;
        }

        System.out.println("ID Parcela: " + p.getIdParcela());
        System.out.println("Tipo de Suelo: " + p.getTipoDeSuelo());
        System.out.println("Estado: " + (p.getDisponible() ? "Disponible" : "Ocupada"));

        Cultivos c = p.getCultivoActual();
        if (c != null) {
            System.out.println("--- CULTIVO ASIGNADO ---");
            System.out.println("Nombre: " + c.getNombre());
            System.out.println("Área en km2: " + c.getAreaKm2());
            System.out.println("Factor K: " + c.getFactorK());
            System.out.println("Base Térmica: " + c.getBaseTermica());
            System.out.println("Tasa Maduración: " + c.getTasaMaduracion());
        } else {
            System.out.println("Cultivo: Vacío (null)");
        }
    }
}