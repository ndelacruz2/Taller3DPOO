package uniandes.dpoo.aerolinea.modelo.cliente;
import uniandes.dpoo.aerolinea.modelo.Vuelo;
import uniandes.dpoo.aerolinea.tiquetes.Tiquete;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class Cliente {

    private Map<String, Tiquete> tiquetesSinUsar;
    private Tiquete tiqueteUsado;
    public abstract List<Vuelo> getHistorialVuelos();
    
	public Cliente() 
	{
		tiquetesSinUsar = new HashMap<>();
	}
	
	public Cliente(String tipoCliente) 
	{
	}
	
	public abstract String getIdentificador();
    public abstract String getTipoCliente();
	

	public void agregarTiquete(Tiquete tiquete) 
	{
		tiquetesSinUsar.put(tiquete.getCodigo(), tiquete);
	}
	
	public int calcularValorTotalTiquetes() 
	{
		int total = 0;
		for(Tiquete tiquete : tiquetesSinUsar.values()) 
		{
			total += tiquete.getTarifa();
		}
		
		if (tiqueteUsado != null) 
		{
			total += tiqueteUsado.getTarifa();
		}
		return total;
	}
	public abstract boolean esClienteCorporativo();
	public void usarTiquetes (Vuelo vuelo) 
	{
		for (Tiquete tiquete : tiquetesSinUsar.values()) 
		{
            if (!tiquete.esUsado() && tiquete.getVuelo().equals(vuelo)) 
            {
                tiquete.marcarComoUsado();
                tiqueteUsado = tiquete;
                return;
            }
		}
	}
}
