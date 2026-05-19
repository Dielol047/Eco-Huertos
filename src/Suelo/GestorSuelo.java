package Suelo;

public class GestorSuelo {
    public boolean validarRotacion(Parcela p, String nuevoGenero) {
        System.out.println("[Modulo Suelo] Validando rotacion botánica...");
        System.out.println("-> Verificando parcela " + p.getIdParcela() + " para género " + nuevoGenero);
        return true;
    }
}
