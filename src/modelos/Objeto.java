package modelos;

public class Objeto {
    private String nombre;
    private int valor; // Para ordenar por valor (Oro)
    private double peso; // Para ordenar por peso (Kg)

    public Objeto(String nombre, int valor, double peso) {
        this.nombre = nombre;
        this.valor = valor;
        this.peso = peso;
    }

    // --- Getters (Necesarios para el algoritmo de ordenamiento más adelante) ---
    public String getNombre() { return nombre; }
    public int getValor() { return valor; }
    public double getPeso() { return peso; }

    // --- Representación en texto para la UI ---
    @Override
    public String toString() {
        // Ejemplo: "Espada de Madera [V:10 | P:2.5kg]"
        return String.format("%s [V:%d | P:%.1fkg]", nombre, valor, peso);
    }
}