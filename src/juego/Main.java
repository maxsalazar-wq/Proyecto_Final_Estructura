package juego;

import javax.swing.SwingUtilities;

public class Main {
    public static void main(String[] args) {
        // Ejecutar la interfaz en el hilo seguro de Swing
        SwingUtilities.invokeLater(() -> {
            // Instanciamos y mostramos la ventana separada
            VentanaJuego ventana = new VentanaJuego();
            ventana.setVisible(true);
        });
    }
}