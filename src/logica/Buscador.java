package logica;

public class Buscador {

    // 0 = Suelo, 1 = Muro, 2 = Tesoro
    // MAPA AMPLIADO 15x15
    private int[][] mapa = {
        {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
        {1, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 2, 0, 1},
        {1, 0, 1, 1, 1, 0, 1, 0, 1, 1, 1, 1, 1, 0, 1},
        {1, 0, 1, 2, 1, 0, 0, 0, 0, 0, 0, 0, 1, 0, 1},
        {1, 0, 1, 1, 1, 1, 1, 1, 1, 0, 1, 0, 1, 0, 1},
        {1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 1},
        {1, 1, 1, 1, 1, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1},
        {1, 0, 0, 2, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 1},
        {1, 0, 1, 1, 1, 1, 1, 0, 1, 1, 1, 0, 1, 0, 1},
        {1, 0, 0, 0, 0, 0, 0, 0, 1, 2, 1, 0, 1, 0, 1},
        {1, 1, 1, 0, 1, 1, 1, 1, 1, 0, 1, 0, 1, 0, 1},
        {1, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1},
        {1, 0, 1, 1, 1, 0, 1, 1, 1, 1, 1, 1, 1, 0, 1},
        {1, 0, 0, 0, 2, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1},
        {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1}
    };
    
    private boolean[][] visitado;
    private int filas = 15;
    private int cols = 15;
    
    // Coordenadas del jugador
    private int jugadorX = 1;
    private int jugadorY = 1;

    public Buscador() {
        visitado = new boolean[filas][cols];
    }

    public boolean moverJugador(int deltaX, int deltaY) {
        int nuevoX = jugadorX + deltaX;
        int nuevoY = jugadorY + deltaY;

        if (nuevoX >= 0 && nuevoX < filas && nuevoY >= 0 && nuevoY < cols) {
            if (mapa[nuevoX][nuevoY] != 1) {
                jugadorX = nuevoX;
                jugadorY = nuevoY;
                if (mapa[nuevoX][nuevoY] == 2) {
                    mapa[nuevoX][nuevoY] = 0; 
                    return true; 
                }
            }
        }
        return false; 
    }

    // Algoritmo recursivo (Solo para el botón Explorar)
    public int iniciarExploracion(int inicioX, int inicioY) {
        visitado = new boolean[filas][cols];
        return explorarRecursivo(inicioX, inicioY);
    }

    private int explorarRecursivo(int x, int y) {
        if (x < 0 || x >= filas || y < 0 || y >= cols || mapa[x][y] == 1 || visitado[x][y]) {
            return 0;
        }
        visitado[x][y] = true;
        int conteo = 1;
        conteo += explorarRecursivo(x + 1, y);
        conteo += explorarRecursivo(x - 1, y);
        conteo += explorarRecursivo(x, y + 1);
        conteo += explorarRecursivo(x, y - 1);
        return conteo;
    }
    
    public String obtenerMapaTexto() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < filas; i++) {
            for (int j = 0; j < cols; j++) {
                if (i == jugadorX && j == jugadorY) {
                    sb.append("☺ "); 
                    continue; 
                }
                if (mapa[i][j] == 1) sb.append("██"); 
                else if (mapa[i][j] == 2) sb.append("$$"); 
                else sb.append(".."); 
            }
            sb.append("\n");
        }
        return sb.toString();
    }
}