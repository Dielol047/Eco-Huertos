package Modelo;

public class ElementoTaxonomico {
    private String nombre;
    private String categoria;
    private double factorK;
    private double baseTermica;
    private int tasaMaduracion;
    private double metrosCuadrados; // Nuevo atributo

    public ElementoTaxonomico(String categoria,String nombre, double metrosCuadrados) {
        this.categoria=categoria;
        this.nombre = nombre;
        this.metrosCuadrados = metrosCuadrados;
        // Asignamos valores desde el contexto
        ContextoAgroCiclo.setTaxonomia(nombre);
        this.factorK = ContextoAgroCiclo.getFactorK();
        this.baseTermica = ContextoAgroCiclo.getBaseTermica();
        this.tasaMaduracion = ContextoAgroCiclo.getTasaMaduracion();
        ContextoAgroCiclo.getTasaMaduracion();
    }

    // Getters
    public String getNombre() { return nombre; }
    public String getCategoria() { return categoria; }
    public double getFactorK() { return factorK; }
    public double getBaseTermica() { return baseTermica; }
    public int getTasaMaduracion() { return tasaMaduracion; }
    public double getMetrosCuadrados() { return metrosCuadrados; }
}