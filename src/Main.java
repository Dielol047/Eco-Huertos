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
        System.out.println("=== AGROCICLO INICIALIZADO ===");
        System.out.println("Taxonomia base cargada.");

        while(true) {
            System.out.println("\nMENU PRINCIPAL (V.0.1)");
            System.out.println("1. Probar Modulo Suelo (Rotar Cultivo)");
            System.out.println("2. Probar Modulo Personal (Asignar Voluntario)");
            System.out.println("3. Probar Modulo Biodata (Proyectar Compost)");
            System.out.println("4. Probar Modulo Estadistica (Distribuir Cosecha)");
            System.out.println("5. Salir");
            System.out.print("Elija una opcion: ");

            int opcion = scanner.nextInt();

            if(opcion == 1) {
                Parcela p1 = new Parcela("P-101", 50.5, "Arcilloso");
                gestorSue.validarRotacion(p1, "Musáceas");
            }
            else if(opcion == 2) {
                Voluntario v1 = new Voluntario("V-01", "Carlos Mendoza", "099", 3, "Basica");
                gestorPer.asignarTarea(v1, "Acondicionamiento de suelo");
            }
            else if(opcion == 3) {
                LoteCompost lote1 = new LoteCompost("LC-001", "Solanum");
                String fecha = gestorBio.proyectarMaduracion(lote1);
                System.out.println("Fecha estimada: " + fecha);
            }
            else if(opcion == 4) {
                Cosecha cos1 = new Cosecha("COS-100", 25.0); // 25kg recolectados
                Comedor com1 = new Comedor("C-1", "Comedor Solidario Sur", 5);
                gestorEst.procesarEntrega(cos1, com1, 15.0);
            }
            else if(opcion == 5) {
                System.out.println("Saliendo del sistema...");
                break;
            } else {
                System.out.println("Opción inválida.");
            }
        }
        scanner.close();

    }
}