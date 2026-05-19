import Modelo.*;
import Negocio.*;
import Interfaz.*;

import java.util.Scanner;
public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        GestorBiodata gestorBio = new GestorBiodata();
        GestorSuelo gestorSue = new GestorSuelo();
        GestorPersonal gestorPer = new GestorPersonal();
        GestorEstadistica gestorEst = new GestorEstadistica();

        System.out.println("=== BIENVENIDO A AGROCICLO ===");
        System.out.print("Ingrese la rama taxonómica de trabajo hoy (ej. Musáceas, Solanum): ");
        String taxonomia = scanner.nextLine();
        System.out.println(">>> Sistema configurado correctamente para: " + taxonomia);

        while(true) {
            System.out.println("\n--- MENU PRINCIPAL (" + taxonomia + ") ---");
            System.out.println("1. Probar Modulo Suelo (Rotar Cultivo)");
            System.out.println("2. Probar Modulo Personal (Asignar Voluntario)");
            System.out.println("3. Probar Modulo Biodata (Proyectar Compost)");
            System.out.println("4. Probar Modulo Estadistica (Distribuir Cosecha)");
            System.out.println("5. Optimizar Cultivos actuales");
            System.out.println("6. Salir");
            System.out.print("Elija una opcion: ");

            int opcion = scanner.nextInt();

            if(opcion == 1) {
                Parcela p1 = new Parcela("P-101", 50.5, "Arcilloso");
                gestorSue.validarRotacion(p1, taxonomia);
            } else if(opcion == 2) {
                Voluntario v1 = new Voluntario("V-01", "Carlos Mendoza", "099", 3, "Basica");
                gestorPer.asignarTarea(v1, "Acondicionamiento de suelo");
            } else if(opcion == 3) {
                LoteCompost lote1 = new LoteCompost("LC-001", taxonomia);
                String fecha = gestorBio.proyectarMaduracion(lote1);
                System.out.println("Fecha estimada de maduración: " + fecha);
            } else if(opcion == 4) {
                Cosecha cos1 = new Cosecha("COS-100", 25.0);
                Comedor com1 = new Comedor("C-1", "Comedor Solidario Sur", 5);
                gestorEst.procesarEntrega(cos1, com1, 15.0);
            } else if(opcion == 5) {
                System.out.println(">>> Optimizando cultivos de la rama: " + taxonomia + "...");
                System.out.println("Algoritmo de optimización procesando datos del suelo...");
            } else if(opcion == 6) {
                System.out.println("Saliendo del sistema...");
                break;
            } else {
                System.out.println("Opción inválida.");
            }
        }

        scanner.close();

    }
}