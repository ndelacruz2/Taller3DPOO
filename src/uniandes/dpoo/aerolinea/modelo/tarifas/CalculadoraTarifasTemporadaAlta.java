package uniandes.dpoo.aerolinea.modelo.tarifas;
import uniandes.dpoo.aerolinea.modelo.Ruta;
import uniandes.dpoo.aerolinea.modelo.Vuelo;
import uniandes.dpoo.aerolinea.modelo.cliente.ClientaNatural;
import uniandes.dpoo.aerolinea.modelo.cliente.Cliente;

abstract class CalculadoraTarifasTemporadaAlta extends CalculadoraTarifas {
	protected final int COSTO_POR_KM = 1000;
	
    @Override
    public int calcularCostoBase(Vuelo vuelo, Cliente cliente) {
        Ruta ruta = vuelo.getRuta();
        int distancia = calcularDistanciaVuelo(ruta);
        return COSTO_POR_KM * distancia;
    }
    
    @Override
    public double calcularPorcentajeDescuento(Cliente cliente) {
        
        if (cliente.esClienteCorporativo()) {
            return 0.1; 
        } else if (cliente instanceof ClientaNatural && cliente.getHistorialVuelos().size() > 10) {
            return 0.05; 
        } else {
            return 0.0; 
        }
    }
}