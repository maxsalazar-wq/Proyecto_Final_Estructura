Proyecto Final Estructura de Datos
El proyecto es un juego de exploración y gestión de inventario desarrollado en Java utilizando la biblioteca gráfica Swing.

El proyecto fue diseñado para demostrar la implementación práctica de estructuras de datos lineales y no lineales, algoritmos de ordenamiento y recursividad.

**Descripción del Proyecto**

El jugador controla a un personaje que debe explorar un mapa generado, recolectar tesoros y combatir enemigos. El núcleo del juego integra conceptos avanzados de programación:

* **Recursividad:** Utilizada para la mecánica de "Explorar", revelando el mapa mediante un algoritmo de *Flood Fill* (Relleno por difusión).
* **Estructuras de Datos:**
  **ArrayList (Lineal):** Gestión dinámica del inventario del jugador.
  **Stack / Pila (Lineal):** Sistema de "Deshacer" (Undo) para revertir la obtención de objetos (LIFO).
  **PriorityQueue (No Lineal):** Sistema de combate donde los enemigos atacan según su nivel de amenaza (prioridad).
* **Algoritmos de Ordenamiento:** Implementación de **QuickSort** para ordenar el inventario por valor monetario.

Requisitos y Dependencias

* **Java Development Kit (JDK):** Versión 8 o superior.
* **Sistema Operativo:** Windows, macOS o Linux.
* **IDE Recomendado:** Eclipse, VSCode.
* No requiere bibliotecas externas (solo librerías estándar de Java `java.awt` y `javax.swing`).

Cómo Compilar y Ejecutar

1. Ubicación
Asegúrate de estar en la carpeta src del proyecto.

2. Compilación
Compila todos los archivos `.java` manteniendo la estructura de paquetes.

3. Ejecucion
Ejecutar el codigo en la clase Main (src/juego/Main).

Manual de Uso
Interfaz Principal
Panel Central (Mapa): Muestra la mazmorra.

☺: Jugador.

██: Muro.

$$: Tesoro.

..: Suelo explorado.

Panel Derecho (Inventario): Lista de objetos obtenidos.

Panel Inferior (Log): Registro de texto de todas las acciones.

Controles
Movimiento: Usa las Flechas del Teclado (⬆️, ⬇️, ⬅️, ➡️) para mover al personaje por el mapa.

Explorar Todo: Ejecuta el algoritmo recursivo para revelar todas las casillas conectadas y contar el área disponible.

Atacar Enemigo: Lucha contra el siguiente enemigo en la Cola de Prioridad.

Deshacer Loot: Si recoges un objeto por error, este botón usa la Pila (Stack) para revertir la acción y eliminar el objeto.

Ordenar Inventario: Organiza tus objetos del más valioso al menos valioso usando QuickSort.

Tirar Objeto: Selecciona un ítem de la lista y pulsa el botón para eliminarlo permanentemente.
