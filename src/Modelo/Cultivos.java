package Modelo;
import Negocio.GestorBiodata;


public class Cultivos {
    private static int contadorIDs = 1;
    private int id;
    private String nombre;
    private String categoria;
    private double factorK;
    private double baseTermica;
    private int tasaMaduracion;
    private double areaKm2; // Nuevo atributo
    private Parcela parcelaAsignada;
    private int consumoNutrientes;
    private int diasMaduracion;
    private String caracteristica;

    public Cultivos(String categoria,String nombre, double metrosCuadrados) {
        this.categoria=categoria;
        this.nombre = nombre;
        this.areaKm2 = areaKm2;
        // Asignamos valores desde el contexto
        GestorBiodata gestor = new GestorBiodata();
        GestorBiodata.FamiliaBotanica familia = gestor.obtenerFamiliaPorCategoria(categoria);
        
        this.consumoNutrientes = familia.getConsumoNutrientesBase();
        this.diasMaduracion = familia.getDiasMaduracionPromedio();
        this.caracteristica = familia.getCaracteristica();
        ContextoAgroCiclo.setTaxonomia(nombre);
        this.factorK = ContextoAgroCiclo.getFactorK();
        this.baseTermica = ContextoAgroCiclo.getBaseTermica();
        this.tasaMaduracion = ContextoAgroCiclo.getTasaMaduracion();
        ContextoAgroCiclo.getTasaMaduracion();
        this.id = contadorIDs++;
        this.parcelaAsignada = null;
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
    public void setParcelaAsignada(Parcela parcela) {
        this.parcelaAsignada = parcela;
    }
    public Parcela getParcelaAsignada() {
        return parcelaAsignada;
    }
     public int getConsumoNutrientes() { return consumoNutrientes; }
    public int getDiasMaduracion() { return diasMaduracion; }
    public String getCaracteristica() { return caracteristica; }

}