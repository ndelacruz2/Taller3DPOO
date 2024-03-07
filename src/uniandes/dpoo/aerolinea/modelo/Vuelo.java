package uniandes.dpoo.aerolinea.modelo;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import uniandes.dpoo.aerolinea.tiquetes.Tiquete;
import uniandes.dpoo.aerolinea.tiquetes.GeneradorTiquetes;
import uniandes.dpoo.aerolinea.modelo.tarifas.CalculadoraTarifas;
import uniandes.dpoo.aerolinea.modelo.cliente.Cliente;
import uniandes.dpoo.aerolinea.exceptions.VueloSobrevendidoException;
public class Vuelo 
{
	private String fecha;
	public Ruta ruta;
	public Avion avion;
	private Map<String, Tiquete> tiquetes;
	
    public Vuelo(Ruta ruta, String fecha, Avion avion) 
    {
        this.ruta = ruta;
        this.fecha = fecha;
        this.avion = avion;
        this.tiquetes = new HashMap<>();
    }
    
    public Ruta getRuta() 
    {
        return ruta;
    }
    
    public String getFecha() 
    {
        return fecha;
    }
	
    public Avion getAvion() 
    {
        return avion;
    }
    
    public Collection<Tiquete> getTiquetes() {
        return tiquetes.values();
    }
    
    public int venderTiquetes(Cliente cliente, CalculadoraTarifas calculadora, int cantidad) throws VueloSobrevendidoException 
    {
   
        int precioTotal = calculadora.calcularTarifa(this, cliente) * cantidad;
        int espacioDisponible = avion.getCapacidad() - tiquetes.size();
        if (cantidad > espacioDisponible) {
            throw new VueloSobrevendidoException(this);
        }

        for (int i = 0; i < cantidad; i++) {
            Tiquete tiquete = GeneradorTiquetes.generarTiquete( this, cliente, calculadora.calcularTarifa(this, cliente));
            tiquetes.put(tiquete.getCodigo(), tiquete);
        }
        
        return precioTotal;
    }
    
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof Vuelo)) {
            return false;
        }
        Vuelo other = (Vuelo) obj;
        return this.ruta.equals(other.ruta) && this.fecha.equals(other.fecha) && this.avion.equals(other.avion);
    }
    

        public int getAsientosDisponibles() {
            return avion.getCapacidad() - tiquetes.size();
        }

        public void actualizarAsientosDisponibles(int cantidad) {
        }
}
