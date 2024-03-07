package uniandes.dpoo.aerolinea.tiquetes;
import java.util.HashSet;
import java.util.Set;

import uniandes.dpoo.aerolinea.modelo.Vuelo;
import uniandes.dpoo.aerolinea.modelo.cliente.Cliente;

/**
 * Esta clase representa al módulo del sistema que es capaz de generar nuevos tiquetes, asignándole a cada uno un código único.
 */
public class GeneradorTiquetes
{
    private static Set<String> codigos = new HashSet<>( );

    public static Tiquete generarTiquete( Vuelo vuelo, Cliente cliente, int tarifa )
    {
    	String codigo = generarCodigoUnico();
        Tiquete tiquete = new Tiquete(codigo, vuelo, cliente, tarifa);
        registrarTiquete(tiquete);
        return tiquete;
    }
    
    private static String generarCodigoUnico()
    {
        String codigo;
        do
        {
            codigo = generarCodigoAleatorio();
        } while (codigos.contains(codigo));
        return codigo;
    }
    
    private static String generarCodigoAleatorio()
    {
        int numero = (int) (Math.random() * 10e7);
        String codigo = String.format("%07d", numero);
        return codigo;
    }


    /**
     * Registra que un cierto tiquete ya fue vendido, para que el generador de tiquetes no vaya a generar otro tiquete con el mismo código
     * @param unTiquete El tiquete existente
     */
    public static void registrarTiquete( Tiquete unTiquete )
    {
    	String codigo = unTiquete.getCodigo();
    	codigos.add(codigo);
    }

    /**
     * Revisa si ya existe un tiquete con el código dado
     * @param codigoTiquete El código que se quiere consultar
     * @return Retorna true si ya se tenía registrado un tiquete con el código dado
     */
    public static boolean validarTiquete( String codigoTiquete )
    {
    	return codigos.contains(codigoTiquete);
    }
}
