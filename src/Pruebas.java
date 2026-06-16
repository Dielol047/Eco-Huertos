import Modelo.Cultivos;
import Modelo.Parcela;

public class Pruebas {
    public static void imprimirEstadoParcela(Parcela p) {
        System.out.println("\n--- DETALLE DE PARCELA ---");
        if (p == null) return;

        System.out.println("ID Parcela: " + p.getIdParcela());
        System.out.println("Tipo Suelo: " + p.getTipoDeSuelo());
        System.out.println("ID Cultivo Asignado: " + p.getIdCultivo());

        Cultivos c = p.getCultivoActual();
        if (c != null) {
            System.out.println("Nombre Cultivo: " + c.getNombre());
        } else {
            System.out.println("Cultivo: Ninguno");
        }
    }
}