package Modelo;

public class Cultivos {
    private static int contadorIDs = 1;
    private int id;
    private String nombre;
    private String categoria;
    private double factorK;
    private double baseTermica;
    private int tasaMaduracion;
    private double areaKm2; // Nuevo atributo

    public Cultivos(String categoria,String nombre, double metrosCuadrados) {
        this.categoria=categoria;
        this.nombre = nombre;
        this.areaKm2 = areaKm2;
        // Asignamos valores desde el contexto
        ContextoAgroCiclo.setTaxonomia(nombre);
        this.factorK = ContextoAgroCiclo.getFactorK();
        this.baseTermica = ContextoAgroCiclo.getBaseTermica();
        this.tasaMaduracion = ContextoAgroCiclo.getTasaMaduracion();
        ContextoAgroCiclo.getTasaMaduracion();
        this.id = contadorIDs++;
    }

    // Getters
    public String getNombre() { return nombre; }
    public String getCategoria() { return categoria; }
    public double getFactorK() { return factorK; }
    public double getBaseTermica() { return baseTermica; }
    public int getTasaMaduracion() { return tasaMaduracion; }
    public double getAreaKm2() { return areaKm2; }
    public int getId() {
        return id;
    }
}