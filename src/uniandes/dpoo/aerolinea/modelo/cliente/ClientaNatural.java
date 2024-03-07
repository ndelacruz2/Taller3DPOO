package uniandes.dpoo.aerolinea.modelo.cliente;

import java.util.List;

import uniandes.dpoo.aerolinea.modelo.Vuelo;

public class ClientaNatural extends Cliente
{
	public final static String NATURAL = "Natural";
	private String nombre;
	
	public ClientaNatural(String nombre) 
	{
		super("Natural");
		this.nombre = nombre;
	}
	
	@Override
	public String getIdentificador() 
	{
		return nombre;
	}
	
	@Override
	public String getTipoCliente() 
	{
		return "Natural";
	}
	
	@Override
	public boolean esClienteCorporativo() {
	    return false; // los clientes naturales no son corporativos
	}
	
	@Override
    public List<Vuelo> getHistorialVuelos() {
        
        return getHistorialVuelos(); 
    }
}
