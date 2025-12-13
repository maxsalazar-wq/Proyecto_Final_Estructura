package juego;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.util.PriorityQueue;
import java.util.Random;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import modelos.*;
import logica.*;

public class VentanaJuego extends JFrame {

    // --- COLORES Y FUENTES (ESTILO VISUAL) ---
    private final Color COLOR_FONDO = new Color(43, 43, 43);       // Gris oscuro
    private final Color COLOR_PANEL = new Color(60, 63, 65);       // Gris un poco más claro
    private final Color COLOR_TEXTO = new Color(230, 230, 230);    // Blanco suave
    private final Color COLOR_VERDE_TERMINAL = new Color(0, 255, 100); 
    private final Color COLOR_BOTON_ACCION = new Color(75, 110, 175); // Azul suave
    private final Color COLOR_BOTON_PELIGRO = new Color(180, 80, 80); // Rojo suave
    private final Font FUENTE_UI = new Font("Segoe UI", Font.PLAIN, 14);
    private final Font FUENTE_MAPA = new Font("Monospaced", Font.BOLD, 18);

    // Componentes Visuales
    private DefaultListModel<Objeto> modeloInventario; 
    private JList<Objeto> listaInventario;             
    private JTextArea registroJuego;
    private JProgressBar barraVida;
    private JTextArea areaMapaVisual;

    // Lógica
    private Jugador jugador;
    private Inventario inventarioLogico;
    private PriorityQueue<Enemigo> colaEnemigos;
    private Buscador buscadorRecursivo;
    private Historial historial;

    public VentanaJuego() {
        // Inicializar Lógica
        jugador = new Jugador("Aventurero", 100);
        inventarioLogico = jugador.getInventario(); 
        historial = new Historial(); 
        colaEnemigos = new PriorityQueue<>();
        buscadorRecursivo = new Buscador();

        // Datos iniciales
        inventarioLogico.agregarObjeto(new Objeto("Daga Oxidada", 5, 1.0));
        inventarioLogico.agregarObjeto(new Objeto("Antorcha", 2, 0.5));
        colaEnemigos.add(new Enemigo("Goblin", 10));
        colaEnemigos.add(new Enemigo("Jefe Orco", 80)); 
        colaEnemigos.add(new Enemigo("Esqueleto", 40));

        inicializarVentana();
        inicializarComponentes();
    }

    private void inicializarVentana() {
        setTitle("Dungeon Looter - Edición Visual");
        setSize(1150, 800);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(15, 15)); // Más espacio entre paneles
        getContentPane().setBackground(COLOR_FONDO); // Fondo general oscuro
    }

    private void inicializarComponentes() {
        // ========================================================
        // 1. PANEL SUPERIOR (CABECERA)
        // ========================================================
        JPanel panelSuperior = new JPanel(new GridLayout(2, 1, 5, 5));
        panelSuperior.setBackground(COLOR_FONDO);
        panelSuperior.setBorder(BorderFactory.createEmptyBorder(10, 10, 0, 10));

        JLabel titulo = new JLabel("EXPLORADOR DE MAZMORRAS", SwingConstants.CENTER);
        titulo.setFont(new Font("Segoe UI", Font.BOLD, 26));
        titulo.setForeground(COLOR_TEXTO);
        
        JPanel panelEstado = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 0));
        panelEstado.setBackground(COLOR_FONDO);
        
        JLabel lblHeroe = new JLabel("Héroe: " + jugador.getNombre());
        lblHeroe.setFont(FUENTE_UI.deriveFont(Font.BOLD));
        lblHeroe.setForeground(Color.ORANGE);

        // Barra de vida estilizada
        barraVida = new JProgressBar(0, jugador.getVidaMaxima());
        barraVida.setValue(jugador.getVidaActual());
        barraVida.setStringPainted(true);
        barraVida.setForeground(new Color(220, 50, 50)); // Rojo sangre
        barraVida.setBackground(Color.DARK_GRAY);
        barraVida.setPreferredSize(new Dimension(300, 25));
        barraVida.setBorderPainted(false);
        
        panelEstado.add(lblHeroe);
        panelEstado.add(barraVida);
        
        panelSuperior.add(titulo);
        panelSuperior.add(panelEstado);
        add(panelSuperior, BorderLayout.NORTH);

        // ========================================================
        // 2. PANEL CENTRAL (MAPA)
        // ========================================================
        JPanel panelMapa = new JPanel(new BorderLayout());
        personalizarPanel(panelMapa, "Mapa Táctico (Usa flechas)");

        areaMapaVisual = new JTextArea();
        areaMapaVisual.setEditable(false);
        areaMapaVisual.setFont(FUENTE_MAPA); 
        areaMapaVisual.setBackground(new Color(20, 20, 20)); // Casi negro
        areaMapaVisual.setForeground(COLOR_VERDE_TERMINAL);
        areaMapaVisual.setText(buscadorRecursivo.obtenerMapaTexto());
        areaMapaVisual.setFocusable(false); // CRÍTICO: No robar foco
        
        // Panel contenedor para centrar el mapa y darle margen
        JPanel contenedorMapa = new JPanel(new GridBagLayout());
        contenedorMapa.setBackground(new Color(20, 20, 20));
        contenedorMapa.add(areaMapaVisual);
        
        panelMapa.add(new JScrollPane(contenedorMapa), BorderLayout.CENTER); // Scroll por si acaso
        add(panelMapa, BorderLayout.CENTER);

        // ========================================================
        // 3. PANEL DERECHO (INVENTARIO)
        // ========================================================
        JPanel panelDerecho = new JPanel(new BorderLayout(0, 10));
        personalizarPanel(panelDerecho, "Mochila");
        panelDerecho.setPreferredSize(new Dimension(320, 0));

        modeloInventario = new DefaultListModel<>();
        actualizarListaVisual();
        
        listaInventario = new JList<>(modeloInventario);
        listaInventario.setBackground(new Color(50, 50, 50));
        listaInventario.setForeground(Color.WHITE);
        listaInventario.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        listaInventario.setSelectionBackground(new Color(100, 100, 180));
        listaInventario.setSelectionForeground(Color.WHITE);
        listaInventario.setFocusable(false); // CRÍTICO: No robar foco

        JScrollPane scrollInv = new JScrollPane(listaInventario);
        scrollInv.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        panelDerecho.add(scrollInv, BorderLayout.CENTER);

        // Panel de botones de inventario
        JPanel btnInv = new JPanel(new GridLayout(2, 1, 5, 5));
        btnInv.setBackground(COLOR_PANEL);
        
        JButton btnOrdenar = crearBoton("Ordenar (Valor)", new Color(100, 150, 200));
        JButton btnUsar = crearBoton("Tirar Objeto", new Color(180, 100, 100));
        
        btnInv.add(btnOrdenar);
        btnInv.add(btnUsar);
        panelDerecho.add(btnInv, BorderLayout.SOUTH);
        
        add(panelDerecho, BorderLayout.EAST);

        // ========================================================
        // 4. PANEL INFERIOR (CONTROLES Y LOG)
        // ========================================================
        JPanel panelInferior = new JPanel(new BorderLayout(10, 10));
        panelInferior.setBackground(COLOR_FONDO);
        panelInferior.setPreferredSize(new Dimension(0, 200));
        panelInferior.setBorder(BorderFactory.createEmptyBorder(0, 10, 10, 10));

        // Botones de Acción Principal
        JPanel botonesAccion = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        botonesAccion.setBackground(COLOR_FONDO);

        JButton btnExplorar = crearBoton("🔍 Explorar Área", COLOR_BOTON_ACCION);
        JButton btnAtacar = crearBoton("⚔️ Atacar Enemigo", COLOR_BOTON_PELIGRO);
        JButton btnDeshacer = crearBoton("↩️ Deshacer Loot", new Color(200, 180, 50));
        // Texto oscuro para el botón amarillo para que se lea bien
        btnDeshacer.setForeground(Color.DARK_GRAY); 

        botonesAccion.add(btnExplorar);
        botonesAccion.add(btnAtacar);
        botonesAccion.add(btnDeshacer);
        panelInferior.add(botonesAccion, BorderLayout.NORTH);

        // Área de Log (Registro)
        registroJuego = new JTextArea();
        registroJuego.setEditable(false);
        registroJuego.setFocusable(false);
        registroJuego.setBackground(new Color(30, 30, 30));
        registroJuego.setForeground(Color.LIGHT_GRAY);
        registroJuego.setFont(new Font("Consolas", Font.PLAIN, 12));
        
        JScrollPane scrollLog = new JScrollPane(registroJuego);
        scrollLog.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(Color.GRAY), 
                "Registro de Eventos", 
                TitledBorder.LEFT, 
                TitledBorder.TOP, 
                FUENTE_UI, 
                Color.GRAY));
        scrollLog.setBackground(COLOR_FONDO);
        
        panelInferior.add(scrollLog, BorderLayout.CENTER);

        add(panelInferior, BorderLayout.SOUTH);

        // ========================
        // LISTENERS (Eventos)
        // ========================

        // --- BOTONES ---
        btnUsar.addActionListener(e -> {
            int seleccionado = listaInventario.getSelectedIndex();
            if (seleccionado != -1) {
                Objeto obj = modeloInventario.get(seleccionado);
                inventarioLogico.eliminarObjeto(seleccionado);
                actualizarListaVisual(); 
                logEvento("Has tirado: " + obj.getNombre());
            } else {
                logEvento("⚠️ Selecciona un objeto de la lista primero.");
            }
        });

        btnOrdenar.addActionListener(e -> {
            new Ordenador().ordenarPorValor(inventarioLogico.getListaObjetos());
            actualizarListaVisual();
            logEvento("Inventario ordenado por valor.");
        });

        btnExplorar.addActionListener(e -> {
            int casillas = buscadorRecursivo.iniciarExploracion(1, 1);
            areaMapaVisual.setText(buscadorRecursivo.obtenerMapaTexto());
            logEvento("Exploración completa. Salas reveladas: " + casillas);
            if (new Random().nextBoolean()) generarRecompensa("Hallazgo");
        });

        btnAtacar.addActionListener(e -> {
            if (colaEnemigos.isEmpty()) {
                logEvento("No hay enemigos cerca.");
                return;
            }
            Enemigo enemigo = colaEnemigos.poll();
            jugador.recibirDanio(enemigo.getNivelAmenaza() / 4);
            barraVida.setValue(jugador.getVidaActual());
            logEvento("¡Venciste a " + enemigo.getNombre() + "!");
            generarRecompensa("Botín de " + enemigo.getNombre());
            
            if (!jugador.estaVivo()) {
                JOptionPane.showMessageDialog(this, "GAME OVER");
                System.exit(0);
            }
        });

        btnDeshacer.addActionListener(e -> {
            if (historial.estaVacio()) {
                logEvento("Nada que deshacer.");
                return;
            }
            Historial.Recuerdo recuerdo = historial.deshacerUltimaAccion();
            if (recuerdo.tipo == Historial.TipoAccion.OBTUVO_ITEM) {
                inventarioLogico.getListaObjetos().remove(recuerdo.objetoRelacionado);
                actualizarListaVisual();
                logEvento("↩️ Deshacer: " + recuerdo.objetoRelacionado.getNombre() + " eliminado.");
            }
        });

        // --- TECLADO (MOVIMIENTO) ---
        this.setFocusable(true);
        this.requestFocusInWindow();
        
        this.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                int key = e.getKeyCode();
                boolean hayTesoro = false;

                if (key == KeyEvent.VK_UP) hayTesoro = buscadorRecursivo.moverJugador(-1, 0);
                else if (key == KeyEvent.VK_DOWN) hayTesoro = buscadorRecursivo.moverJugador(1, 0);
                else if (key == KeyEvent.VK_LEFT) hayTesoro = buscadorRecursivo.moverJugador(0, -1);
                else if (key == KeyEvent.VK_RIGHT) hayTesoro = buscadorRecursivo.moverJugador(0, 1);

                areaMapaVisual.setText(buscadorRecursivo.obtenerMapaTexto());

                if (hayTesoro) {
                    generarRecompensa("Suelo Encontrado");
                    logEvento("¡Pisaste un tesoro oculto ($$)!");
                }
            }
        });
    }

    // --- MÉTODOS DE ESTILO Y UTILIDAD ---

    // Crea botones bonitos y planos automáticamente
    private JButton crearBoton(String texto, Color colorFondo) {
        JButton btn = new JButton(texto);
        btn.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btn.setBackground(colorFondo);
        btn.setForeground(Color.WHITE); // Texto blanco por defecto
        btn.setFocusable(false); // CRÍTICO: Evita robar foco al teclado
        btn.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20)); // Padding interno
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR)); // Manito al pasar mouse
        
        // Truco para quitar el efecto 3D feo de Java antiguo
        btn.setFocusPainted(false);
        //btn.setBorderPainted(false); // Descomentar si quieres botones totalmente planos
        return btn;
    }

    // Estiliza los paneles con borde y título blanco
    private void personalizarPanel(JPanel panel, String titulo) {
        panel.setBackground(COLOR_PANEL);
        TitledBorder borde = BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(Color.GRAY), 
                titulo);
        borde.setTitleColor(COLOR_TEXTO);
        borde.setTitleFont(FUENTE_UI.deriveFont(Font.BOLD));
        panel.setBorder(borde);
    }

    private void generarRecompensa(String origen) {
        String[] nombres = {"Moneda de Oro", "Rubí", "Pergamino", "Poción", "Botas Viejas", "Diamante"};
        Random rand = new Random();
        String nombreItem = nombres[rand.nextInt(nombres.length)];
        Objeto nuevoObjeto = new Objeto(nombreItem, rand.nextInt(100) + 1, rand.nextDouble() * 5);
        
        inventarioLogico.agregarObjeto(nuevoObjeto);
        historial.agregarAccion(Historial.TipoAccion.OBTUVO_ITEM, nuevoObjeto);
        actualizarListaVisual();
        logEvento("¡Recompensa (" + origen + "): " + nombreItem + "!");
    }

    private void actualizarListaVisual() {
        modeloInventario.clear();
        for (Objeto obj : inventarioLogico.getListaObjetos()) {
            modeloInventario.addElement(obj);
        }
    }

    private void logEvento(String msg) {
        registroJuego.append("> " + msg + "\n");
        registroJuego.setCaretPosition(registroJuego.getDocument().getLength());
    }
}