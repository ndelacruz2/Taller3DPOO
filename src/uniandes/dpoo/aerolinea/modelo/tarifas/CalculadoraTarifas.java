package uniandes.dpoo.aerolinea.modelo.tarifas;
import uniandes.dpoo.aerolinea.modelo.Vuelo;
import uniandes.dpoo.aerolinea.modelo.cliente.Cliente;
import uniandes.dpoo.aerolinea.modelo.Aeropuerto;
import uniandes.dpoo.aerolinea.modelo.Ruta;

public abstract class CalculadoraTarifas {
	public final double impuesto = 0.28;

    public int calcularTarifa(Vuelo vuelo, Cliente cliente) {
        int costoBase = calcularCostoBase(vuelo, cliente);
        int valorImpuestos = calcularValorImpuestos(costoBase);
        return costoBase + valorImpuestos;
    }
	
	protected int calcularDistanciaVuelo(Ruta ruta) {
        Aeropuerto origen = ruta.getOrigen();
        Aeropuerto destino = ruta.getDestino();
        
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
        
        return (int) Math.round(distanciaEnKm);
	}
	
    protected int calcularValorImpuestos(int costoBase) {
        return (int) Math.round(costoBase * costoBase);
	}

	protected abstract int calcularCostoBase(Vuelo vuelo, Cliente cliente);
	protected abstract double calcularPorcentajeDescuento(Cliente cliente); 

}