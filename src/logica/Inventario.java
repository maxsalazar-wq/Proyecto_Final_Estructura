package logica;

import modelos.Objeto;
import java.util.ArrayList;
import java.util.List;

public class Inventario {
    
    // Estructura Lineal: ArrayList
    private List<Objeto> objetos;

    public Inventario() {
        this.objetos = new ArrayList<>();
    }

    public void agregarObjeto(Objeto obj) {
        objetos.add(obj);
    }

    public void eliminarObjeto(int indice) {
        if (indice >= 0 && indice < objetos.size()) {
            objetos.remove(indice);
        }
    }

    public Objeto obtenerObjeto(int indice) {
        if (indice >= 0 && indice < objetos.size()) {
            return objetos.get(indice);
        }
        return null;
    }

    // Método para obtener la lista cruda (útil para la UI o el ordenamiento)
    public List<Objeto> getListaObjetos() {
        return objetos;
    }
}