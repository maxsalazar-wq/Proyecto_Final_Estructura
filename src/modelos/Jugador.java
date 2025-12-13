package modelos;

import logica.Inventario;

public class Jugador {
    private String nombre;
    private int vidaActual;
    private int vidaMaxima;
    
    // El jugador "posee" el inventario (Composición/Agregación)
    private Inventario inventario; 

    public Jugador(String nombre, int vidaMaxima) {
        this.nombre = nombre;
        this.vidaMaxima = vidaMaxima;
        this.vidaActual = vidaMaxima; // Empieza con la vida llena
        this.inventario = new Inventario();
    }

    // --- Lógica del Jugador ---
    
    public void recibirDanio(int cantidad) {
        this.vidaActual -= cantidad;
        if (this.vidaActual < 0) this.vidaActual = 0;
    }

    public void curar(int cantidad) {
        this.vidaActual += cantidad;
        if (this.vidaActual > this.vidaMaxima) this.vidaActual = this.vidaMaxima;
    }

    public boolean estaVivo() {
        return this.vidaActual > 0;
    }

    // --- Getters y Setters ---

    public String getNombre() { return nombre; }
    
    public int getVidaActual() { return vidaActual; }
    
    public int getVidaMaxima() { return vidaMaxima; }
    
    // Este método es crucial: permite acceder al inventario DE ESTE jugador
    public Inventario getInventario() {
        return inventario;
    }
}