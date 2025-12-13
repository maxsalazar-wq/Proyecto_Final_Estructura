package modelos;

// Implementamos Comparable para que la PriorityQueue sepa a quién poner primero
public class Enemigo implements Comparable<Enemigo> {
    private String nombre;
    private int nivelAmenaza; // Mayor número = Más peligroso (Prioridad alta)

    public Enemigo(String nombre, int nivelAmenaza) {
        this.nombre = nombre;
        this.nivelAmenaza = nivelAmenaza;
    }

    public String getNombre() { return nombre; }
    public int getNivelAmenaza() { return nivelAmenaza; }

    @Override
    public String toString() {
        return nombre + " (Amenaza: " + nivelAmenaza + ")";
    }

    // Lógica de comparación para la Cola de Prioridad
    @Override
    public int compareTo(Enemigo otro) {
        // Orden Descendente: Queremos que el de MAYOR amenaza salga primero
        return Integer.compare(otro.nivelAmenaza, this.nivelAmenaza);
    }
}