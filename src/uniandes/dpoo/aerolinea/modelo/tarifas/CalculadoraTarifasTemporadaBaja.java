package uniandes.dpoo.aerolinea.modelo.tarifas;
import uniandes.dpoo.aerolinea.modelo.*;
import uniandes.dpoo.aerolinea.modelo.cliente.*;


abstract class CalculadoraTarifasTemporadaBaja extends CalculadoraTarifas {
    protected final int COSTO_POR_KM_NATURAL = 600;
    protected final int COSTO_POR_KM_CORPORATIVO = 900;
    protected final double DESCUENTO_PEQ = 0.02;
    protected final double DESCUENTO_MEDIANAS = 0.1;
    protected final double DESCUENTO_GRANDES = 0.2;

    @Override
    public int calcularCostoBase(Vuelo vuelo, Cliente cliente) {

        Ruta ruta = vuelo.getRuta();
        Aeropuerto origen = ruta.getOrigen();
        Aeropuerto destino = ruta.getDestino();
        int distancia = calcularDistancia(origen, destino);
        int costoBase = cliente.esClienteCorporativo() ? COSTO_POR_KM_CORPORATIVO * distancia : COSTO_POR_KM_NATURAL * distancia;
        
        return costoBase;
    }
    
    private int calcularDistancia(Aeropuerto origen, Aeropuerto destino) {

        double latitudOrigen = Math.toRadians(origen.getLatitud());
        double longitudOrigen = Math.toRadians(origen.getLongitud());
        double latitudDestino = Math.toRadians(destino.getLatitud());
        double longitudDestino = Math.toRadians(destino.getLongitud());
        
        
        double deltaLatitud = latitudDestino - latitudOrigen;
        double deltaLongitud = longitudDestino - longitudOrigen;
        double a = Math.pow(Math.sin(deltaLatitud / 2), 2)
                   + Math.cos(latitudOrigen) * Math.cos(latitudDestino) * Math.pow(Math.sin(deltaLongitud / 2), 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        double distanciaEnKm = Aeropuerto.getRadioTerrestre() * c;
        
        return (int) Math.round(distanciaEnKm);}
    
    @Override
    public double calcularPorcentajeDescuento(Cliente cliente) {
        double porcentajeDescuento;

        switch (cliente.getTipoCliente()) {
            case "Pequeña":
                porcentajeDescuento = DESCUENTO_PEQ;
                break;
            case "Mediana":
                porcentajeDescuento = DESCUENTO_MEDIANAS;
                break;
            case "Grande":
                porcentajeDescuento = DESCUENTO_GRANDES;
                break;
            default:
                porcentajeDescuento = 0;
                break;
        }
        return porcentajeDescuento;
    }
}