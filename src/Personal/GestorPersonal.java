package Personal;

public class GestorPersonal {
    public void asignarTarea(Voluntario v, String tarea) {
        System.out.println("[Modulo Personal] Ejecutando logica de asignacion...");
        System.out.println("-> Asignando tarea '" + tarea + "' al voluntario: " + v.getNombre());
    }
}
