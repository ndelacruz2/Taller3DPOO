package uniandes.dpoo.aerolinea.modelo;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;

import uniandes.dpoo.aerolinea.exceptions.InformacionInconsistenteException;
import uniandes.dpoo.aerolinea.exceptions.VueloSobrevendidoException;
import uniandes.dpoo.aerolinea.modelo.cliente.Cliente;
import uniandes.dpoo.aerolinea.persistencia.CentralPersistencia;
import uniandes.dpoo.aerolinea.persistencia.IPersistenciaAerolinea;
import uniandes.dpoo.aerolinea.persistencia.IPersistenciaTiquetes;
import uniandes.dpoo.aerolinea.persistencia.TipoInvalidoException;
import uniandes.dpoo.aerolinea.tiquetes.Tiquete;
import uniandes.dpoo.aerolinea.modelo.Ruta;
/**
 * En esta clase se organizan todos los aspectos relacionados con una Aerolínea.
 * 
 * Por un lado, esta clase cumple un rol central como estructurador para todo el resto de elementos: directa o indirectamente, todos están contenidos y se pueden acceder a
 * través de la clase Aerolínea.
 * 
 * Por otro lado, esta clase implementa algunas funcionalidades adicionales a su rol como estructurador, para lo cual se apoya en las otras clases que hacen parte del
 * proyecto.
 */
public class Aerolinea
{
    private List<Avion> aviones;
    private Map<String, Ruta> rutas;
    private List<Vuelo> vuelos;
    private Map<String, Cliente> clientes;

    public Aerolinea() {
        aviones = new ArrayList<>();
        rutas = new HashMap<>();
        vuelos = new ArrayList<>();
        clientes = new HashMap<>();
    }

    public void agregarRuta( Ruta ruta )
    {
        this.rutas.put( ruta.getCodigoRuta(), ruta );
    }

    public void agregarAvion( Avion avion )
    {
        this.aviones.add(avion);
    }

    public void agregarCliente( Cliente cliente )
    {
        this.clientes.put(cliente.getIdentificador(), cliente);
    }

    public boolean existeCliente( String identificadorCliente )
    {
        return this.clientes.containsKey(identificadorCliente);
    }

    
    public Cliente getCliente( String identificadorCliente )
    {
        return this.clientes.get( identificadorCliente );
    }

    public Collection<Avion> getAviones( )
    {
        return aviones;
    }

    public Collection<Ruta> getRutas( )
    {
        return rutas.values( );
    }

    public Ruta getRuta( String codigoRuta )
    {
        return rutas.get(codigoRuta);
    }

    public Collection<Vuelo> getVuelos( )
    {
        return vuelos;
    }

    public Vuelo getVuelo( String codigoRuta, String fechaVuelo )
    {
        for (Vuelo vuelo : vuelos) 
        {
        	if (vuelo.getRuta().getCodigoRuta().equals(codigoRuta) && vuelo.getFecha().equals(fechaVuelo))
        	{
        		return vuelo;
        	}
        }
        return null;
    }

    public Collection<Cliente> getClientes( )
    {
        return clientes.values( );
    }


    public Collection<Tiquete> getTiquetes( )
    {
    	List<Tiquete> todosLosTiquetes = new LinkedList<>();
    	for (Vuelo vuelo : vuelos) 
    	{
    		if (vuelo != null) 
    		{
    		todosLosTiquetes.addAll(vuelo.getTiquetes());
    		}
    	}
        return todosLosTiquetes;

    }

    // ************************************************************************************
    //
    // Estos son los métodos que están relacionados con la persistencia de la aerolínea
    //
    // ************************************************************************************

    /**
     * Carga toda la información de la aerolínea a partir de un archivo
     * @param archivo El nombre del archivo.
     * @param tipoArchivo El tipo del archivo. Puede ser CentralPersistencia.JSON o CentralPersistencia.PLAIN.
     * @throws TipoInvalidoException Se lanza esta excepción si se indica un tipo de archivo inválido
     * @throws IOException Lanza esta excepción si hay problemas leyendo el archivo
     * @throws InformacionInconsistenteException Lanza esta excepción si durante la carga del archivo se encuentra información que no es consistente
     */
    
    public void cargarAerolinea( String archivo, String tipoArchivo ) throws TipoInvalidoException, IOException, InformacionInconsistenteException
    {
    	IPersistenciaAerolinea cargador = CentralPersistencia.getPersistenciaAerolinea(tipoArchivo);
    	cargador.cargarAerolinea(archivo, this);
    }

    public void salvarAerolinea( String archivo, String tipoArchivo ) throws TipoInvalidoException, IOException
    {
        IPersistenciaAerolinea cargador = CentralPersistencia.getPersistenciaAerolinea(tipoArchivo);
        cargador.salvarAerolinea(archivo, this);
    }

    public void cargarTiquetes( String archivo, String tipoArchivo ) throws TipoInvalidoException, IOException, InformacionInconsistenteException
    {
        IPersistenciaTiquetes cargador = CentralPersistencia.getPersistenciaTiquetes( tipoArchivo );
        cargador.cargarTiquetes( archivo, this );
    }

    public void salvarTiquetes( String archivo, String tipoArchivo ) throws TipoInvalidoException, IOException
    {
        IPersistenciaTiquetes cargador = CentralPersistencia.getPersistenciaTiquetes( tipoArchivo );
        cargador.salvarTiquetes( archivo, this );
    }

    // ************************************************************************************
    //
    // Estos son los métodos que están relacionados con funcionalidades interesantes de la aerolínea
    //
    // ************************************************************************************

    /**
     * Agrega un nuevo vuelo a la aerolínea, para que se realice en una cierta fecha, en una cierta ruta y con un cierto avión.
     * 
     * Este método debe verificar que el avión seleccionado no esté ya ocupado para otro vuelo en el mismo intervalo de tiempo del nuevo vuelo. No es necesario verificar que
     * se encuentre en el lugar correcto (origen del vuelo).
     * 
     * @param fecha La fecha en la que se realizará el vuelo
     * @param codigoRuta La ruta que cubirá el vuelo
     * @param nombreAvion El nombre del avión que realizará el vuelo
     * @throws Exception Lanza esta excepción si hay algún problema con los datos suministrados
     */
    /** Aquí */
    
    public void programarVuelo(String fecha, String codigoRuta, String nombreAvion) throws Exception {
        Avion avion = null;
        for (Avion aero : aviones) {
            if (aero.getNombre().equals(nombreAvion)) {
                avion = aero;
                break;
            }
        }

        if (avion == null) {
            throw new Exception("No se encontró el avión especificado");
        }

        Ruta ruta = rutas.get(codigoRuta);
        if (ruta == null) {
            throw new Exception("No se encontró la ruta especificada");
        }

        Vuelo vuelo = new Vuelo(ruta, fecha, avion);
        vuelos.add(vuelo);
    }

    public void cargarDesdeJSON(JSONObject jsonObject, Avion avion, Ruta ruta) {
        JSONArray vuelosArray = jsonObject.getJSONArray("vuelos");
        for (int i = 0; i < vuelosArray.length(); i++) {
            JSONObject vueloObj = vuelosArray.getJSONObject(i);
            String fecha = vueloObj.getString("fecha");
            Vuelo vuelo = new Vuelo(ruta, fecha, avion);
            
            
            this.vuelos.add(vuelo);
        }
    }
        

	public JSONObject salvarAJSON() {
        JSONObject jsonObject = new JSONObject();
        JSONArray vuelosArray = new JSONArray();
        for (Vuelo vuelo : vuelos) {
            JSONObject vueloObj = new JSONObject();
            vueloObj.put("fecha", vuelo.getFecha());
            vueloObj.put("codigoRuta", vuelo.getRuta());
            vueloObj.put("nombreAvion", vuelo.getAvion());
            vuelosArray.put(vueloObj);
        }
        jsonObject.put("vuelos", vuelosArray);
        return jsonObject;
    }

}
