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
        System.out.println("Seleccione la rama taxonómica de trabajo para hoy:");
        System.out.println("1. Solanum (Tomates, papas)");
        System.out.println("2. Musáceas (Plátanos, bananos)");
        System.out.println("3. Cucurbitáceas (Zapallos, pepinos)");
        System.out.println("4. Leguminosas (Frijoles, arvejas)");
        System.out.println("5. Brasicáceas (Coles, brócoli)");
        System.out.print("Opción: ");

        int sel = scanner.nextInt();
        String taxonomia = "";

        switch(sel) {
            case 1 -> taxonomia = "Solanum";
            case 2 -> taxonomia = "Musáceas";
            case 3 -> taxonomia = "Cucurbitáceas";
            case 4 -> taxonomia = "Leguminosas";
            case 5 -> taxonomia = "Brasicáceas";
            default -> taxonomia = "Genérica";
        }

        System.out.println(">>> Sistema configurado para: " + taxonomia);

        while(true) {
            System.out.println("\n--- MENU PRINCIPAL (" + taxonomia + ") ---");
            System.out.println("1. Módulo Suelo (Rotación)");
            System.out.println("2. Módulo Personal (Asignar)");
            System.out.println("3. Módulo Biodata (Compost)");
            System.out.println("4. Módulo Estadística (Cosecha)");
            System.out.println("5. Optimizar Cultivos");
            System.out.println("6. Salir");
            System.out.print("Elija una opción: ");

            int opcion = scanner.nextInt();

            if(opcion == 6) break;

            // Aquí llamarías a tus gestores según la opción...
            System.out.println("Procesando opción " + opcion + " para " + taxonomia + "...");
        }
        scanner.close();

    }
}