package logica;

import modelos.Objeto;
import java.util.List;

public class Ordenador {

    // Método público que llamaremos desde la ventana
    public void ordenarPorValor(List<Objeto> lista) {
        if (lista == null || lista.isEmpty()) return;
        quickSort(lista, 0, lista.size() - 1);
    }

    // Algoritmo QuickSort (Divide y Vencerás)
    private void quickSort(List<Objeto> lista, int bajo, int alto) {
        if (bajo < alto) {
            // Encontrar el índice de partición
            int pi = particion(lista, bajo, alto);

            // Ordenar recursivamente los elementos antes y después de la partición
            quickSort(lista, bajo, pi - 1);
            quickSort(lista, pi + 1, alto);
        }
    }

    // Método auxiliar para dividir la lista
    private int particion(List<Objeto> lista, int bajo, int alto) {
        // Elegimos el último elemento como pivote (podría ser cualquiera)
        Objeto pivote = lista.get(alto);
        int i = (bajo - 1); // Índice del elemento más pequeño

        for (int j = bajo; j < alto; j++) {
            // Condición de ordenamiento: Mayor valor primero (Descendente)
            // Si quieres Ascendente, cambia a: lista.get(j).getValor() < pivote.getValor()
            if (lista.get(j).getValor() > pivote.getValor()) {
                i++;
                intercambiar(lista, i, j);
            }
        }

        // Colocar el pivote en su posición correcta
        intercambiar(lista, i + 1, alto);
        return i + 1;
    }

    // Método auxiliar para intercambiar elementos en la lista
    private void intercambiar(List<Objeto> lista, int i, int j) {
        Objeto temp = lista.get(i);
        lista.set(i, lista.get(j));
        lista.set(j, temp);
    }
}