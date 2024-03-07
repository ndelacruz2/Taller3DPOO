package uniandes.dpoo.aerolinea.persistencia;

import org.json.JSONObject;
import org.json.JSONTokener;
import uniandes.dpoo.aerolinea.modelo.Aerolinea;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class PersistenciaAerolineaJson implements IPersistenciaAerolinea {
    public void cargarAerolinea(String archivo, Aerolinea aerolinea) {
        try {
            
            FileReader reader = new FileReader(archivo);

           
            JSONTokener tokener = new JSONTokener(reader);
            JSONObject jsonObject = new JSONObject(tokener);

            aerolinea.cargarDesdeJSON(jsonObject);

            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void salvarAerolinea(String archivo, Aerolinea aerolinea) {
        try {
            JSONObject jsonObject = aerolinea.salvarAJSON();

            FileWriter writer = new FileWriter(archivo);
            jsonObject.write(writer);
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

