package uniandes.dpoo.aerolinea.persistencia;

import uniandes.dpoo.aerolinea.modelo.Aerolinea;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class PersistenciaAerolineaPlaintext implements IPersistenciaAerolinea {
    @Override
    public void cargarAerolinea(String archivo, Aerolinea aerolinea) {
    }

    @Override
    public void salvarAerolinea(String archivo, Aerolinea aerolinea) {
        try {
            FileWriter fileWriter = new FileWriter(archivo);
            PrintWriter printWriter = new PrintWriter(fileWriter);

            printWriter.println(aerolinea.toString());

            printWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
    }
  }
}
