package Estadistica;

public class GestorEstadistica {
    public void procesarEntrega(Cosecha c, Comedor com, double kilos) {
        System.out.println("[Modulo Estadistica] Calculando prioridad de distribucion...");
        System.out.println("-> Entregando a " + com.getNombre() + " (Vulnerabilidad prioritaria)");
        c.registrarDistribucion(kilos);
        System.out.println("-> Sobrante actualizado en sistema: " + c.getSobranteActual() + " kg.");
    }
}
