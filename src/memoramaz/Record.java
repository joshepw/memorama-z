//Paquetes y Librerias
package memoramaz;

import java.io.Serializable;
import java.util.ArrayList;

public class Record implements Serializable{
    
    // Propiedades
    private final String nombre;
    private final int puntos;
    private final int intentos;
    private final int fallas;
    private final static ArrayList<Record> records = new ArrayList();
    
    public Record(String nombre, int puntos, int intentos, int fallas){
        this.nombre   = nombre;
        this.puntos   = puntos;
        this.intentos = intentos;
        this.fallas   = fallas;
    }
    
    public String getName(){
        return nombre;
    }
    
    public int getPuntos() {
        return puntos;
    }
    
    public void saveOldRecord(){
        records.add(this);
    }
    
    @Override
    public String toString(){
        return puntos + " Puntos | " + intentos + " Intentos | " + fallas + " Fallas";
    }
}
