package Modelo;

public class Cosecha {
    private String idCosecha;
    private double cantidadRecolectada;
    private double cantidadDistribuida;
    private double cantidadSobrante;

    public Cosecha(String id, double cantRecolectada) {
        this.idCosecha = id;
        this.cantidadRecolectada = cantRecolectada;
        this.cantidadDistribuida = 0;
        calcularSobrante();
    }

    public void registrarDistribucion(double cantidad) {
        if(cantidad <= cantidadSobrante) {
            this.cantidadDistribuida += cantidad;
            calcularSobrante();
        } else {
            System.out.println("Error: No hay suficiente sobrante para distribuir.");
        }
    }
    public double calcularSobrante() {
        this.cantidadSobrante = this.cantidadRecolectada - this.cantidadDistribuida;
        return this.cantidadSobrante;
    }
    public double getSobranteActual() { return cantidadSobrante; }

}
