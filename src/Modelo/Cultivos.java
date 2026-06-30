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
    private double tempMedia, tempMin, tempMax;
    private double precipitacionAcumulada;
    private double humedadRelativaMedia;
    private double radiacionSolarPAR;
    private boolean estadisticasCargadas = false;

    private double cantidadRecolectada;
    private double cantidadDistribuida;
    private double cantidadSobrante;

    public Cultivos(String categoria,String nombre, double metrosCuadrados) {
        this.categoria=categoria;
        this.nombre = nombre;
        this.areaKm2 = metrosCuadrados;
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
         this.cantidadRecolectada = 0;
        this.cantidadDistribuida = 0;
        this.cantidadSobrante = 0;
    }
   
     public double getCantidadRecolectada() { return cantidadRecolectada; }
    public double getCantidadDistribuida() { return cantidadDistribuida; }
    public double getSobranteActual() { return cantidadSobrante; }

    public boolean tieneEstadisticas() { return estadisticasCargadas; }
    public void setEstadisticasCargadas(boolean valor) { this.estadisticasCargadas = valor; }

    public double getTempMedia() { return tempMedia; }
    public void setTempMedia(double v) { tempMedia = v; }
    public double getTempMin() { return tempMin; }
    public void setTempMin(double v) { tempMin = v; }
    public double getTempMax() { return tempMax; }
    public void setTempMax(double v) { tempMax = v; }
    public double getPrecipitacion() { return precipitacionAcumulada; }
    public void setPrecipitacion(double v) { precipitacionAcumulada = v; }
    public double getHumedad() { return humedadRelativaMedia; }
    public void setHumedad(double v) { humedadRelativaMedia = v; }
    public double getRadiacion() { return radiacionSolarPAR; }
    public void setRadiacion(double v) { radiacionSolarPAR = v; }

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