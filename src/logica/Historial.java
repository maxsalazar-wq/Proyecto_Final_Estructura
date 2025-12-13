package logica;

import java.util.Stack;
import modelos.Objeto;

// Esta clase gestiona el "Control Z" del juego usando una Pila (LIFO)
public class Historial {
    
    // Enum para saber qué tipo de acción guardamos
    public enum TipoAccion {
        OBTUVO_ITEM,
        MOVIMIENTO, // (Placeholder por si quieres expandir)
        COMBATE     // (Placeholder)
    }

    // Clase interna para guardar el "recuerdo"
    public static class Recuerdo {
        public TipoAccion tipo;
        public Objeto objetoRelacionado; // Guardamos el objeto para poder borrarlo si deshacemos

        public Recuerdo(TipoAccion tipo, Objeto obj) {
            this.tipo = tipo;
            this.objetoRelacionado = obj;
        }
    }

    private Stack<Recuerdo> pilaAcciones;

    public Historial() {
        pilaAcciones = new Stack<>();
    }

    public void agregarAccion(TipoAccion tipo, Objeto obj) {
        pilaAcciones.push(new Recuerdo(tipo, obj));
    }

    public boolean estaVacio() {
        return pilaAcciones.isEmpty();
    }

    // Saca la última acción para revertirla
    public Recuerdo deshacerUltimaAccion() {
        if (!pilaAcciones.isEmpty()) {
            return pilaAcciones.pop();
        }
        return null;
    }
}