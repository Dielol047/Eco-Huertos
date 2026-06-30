package Interfaz;
import Modelo.*;
import Negocio.*;

import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Scanner;

/**
 * Interfaz gráfica de AGROCICLO.
 *
 * Usa EXACTAMENTE las mismas variables y conexiones que tu Main:
 *   - Listas: misSelecciones, parcelas, voluntarios, trabajadores, contadorPersonal
 *   - Gestores: GestorSuelo, GestorPersonal, GestorEstadistica, GestorBiodata
 *   - Contexto: ContextoAgroCiclo.setTaxonomia(...)
 *
 * El flujo principal (taxonomía -> cultivo -> lista -> menú) es gráfico.
 * Las operaciones que internamente usan Scanner (Suelo / Personal / Clima)
 * y los reportes se ejecutan tal cual, a través de una consola integrada:
 * System.out se redirige al área de texto y el campo de entrada alimenta el Scanner.
 */
public class AgroCicloGUI extends JFrame {

    // ===== Mismos datos que el Main =====
    private final ArrayList<Cultivos> misSelecciones = new ArrayList<>();
    private final ArrayList<Parcela> parcelas = new ArrayList<>();
    private final ArrayList<Voluntario> voluntarios = new ArrayList<>();
    private final ArrayList<Trabajador> trabajadores = new ArrayList<>();
    private final int[] contadorPersonal = {1};

    // ===== Mismos gestores que el Main =====
    private final GestorSuelo gestorSuelo = new GestorSuelo();
    private final GestorPersonal gestorPer = new GestorPersonal();
    private final GestorEstadistica gestorEst = new GestorEstadistica();
    private final GestorBiodata gestorBio = new GestorBiodata();

    private Cultivos cultivoActivo;
    private String taxonomiaSeleccionada = "";

    // ===== Puente de consola (para reutilizar los métodos con Scanner) =====
    private Scanner scanner;
    private PipedOutputStream entradaGUI;   // la GUI escribe aquí; el Scanner lee
    private JTextArea consola;
    private JTextField campoEntrada;
    private JButton botonEnviar;
    private volatile boolean operacionEnCurso = false;

    // ===== Navegación =====
    private final CardLayout cards = new CardLayout();
    private final JPanel panelCentro = new JPanel(cards);
    private final DefaultListModel<String> modeloLista = new DefaultListModel<>();
    private JList<String> listaCultivos;
    private JLabel lblInfo;
    private JLabel lblCultivoActivo;
    private JTextField txtNombre;
    private JTextField txtKm2;
    private final ArrayList<JButton> botonesMenu = new ArrayList<>();

    private static final Color VERDE = new Color(34, 139, 87);
    private static final Color VERDE_OSC = new Color(24, 92, 58);

    public AgroCicloGUI() {
        super("AGROCICLO — Sistema de Gestión de Cultivos");
        configurarConsolaYScanner();
        construirUI();
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(940, 680);
        setLocationRelativeTo(null);
        System.out.println("=== BIENVENIDO A AGROCICLO ===");
        cards.show(panelCentro, "TAXONOMIA");
    }

    // -------------------------------------------------------------------------
    // Puente consola <-> Scanner
    // -------------------------------------------------------------------------
    private void configurarConsolaYScanner() {
        consola = new JTextArea();
        consola.setEditable(false);
        consola.setFont(new Font("Monospaced", Font.PLAIN, 13));
        consola.setBackground(new Color(18, 22, 26));
        consola.setForeground(new Color(120, 230, 140));
        consola.setCaretColor(Color.WHITE);

        // Redirige System.out y System.err al área de texto
        PrintStream ps = new PrintStream(new SalidaConsola(consola), true, StandardCharsets.UTF_8);
        System.setOut(ps);
        System.setErr(ps);

        // Conecta un Scanner a un pipe que la GUI alimenta desde el campo de entrada
        try {
            PipedInputStream pis = new PipedInputStream(1 << 16);
            entradaGUI = new PipedOutputStream(pis);
            scanner = new Scanner(pis, "UTF-8").useLocale(Locale.US);
        } catch (IOException e) {
            throw new RuntimeException("No se pudo iniciar la consola: " + e.getMessage());
        }
    }

    private void enviarEntrada() {
        String texto = campoEntrada.getText();
        campoEntrada.setText("");
        consola.append(texto + "\n");                       // eco visual
        consola.setCaretPosition(consola.getDocument().getLength());
        try {
            entradaGUI.write((texto + "\n").getBytes(StandardCharsets.UTF_8));
            entradaGUI.flush();
        } catch (IOException ex) {
            System.out.println("--- Error de entrada: " + ex.getMessage() + " ---");
        }
    }

    /** OutputStream que vuelca lo impreso por los gestores a la consola gráfica. */
    static class SalidaConsola extends OutputStream {
        private final JTextArea area;
        private final ByteArrayOutputStream buffer = new ByteArrayOutputStream();
        private int mostrados = 0;
        SalidaConsola(JTextArea area) { this.area = area; }

        @Override public synchronized void write(int b) { buffer.write(b); }
        @Override public synchronized void write(byte[] b, int off, int len) { buffer.write(b, off, len); }
        @Override public synchronized void flush() {
            String completo = new String(buffer.toByteArray(), StandardCharsets.UTF_8);
            if (completo.length() > mostrados) {
                String nuevo = completo.substring(mostrados);
                mostrados = completo.length();
                SwingUtilities.invokeLater(() -> {
                    area.append(nuevo);
                    area.setCaretPosition(area.getDocument().getLength());
                });
            }
        }
    }

    // -------------------------------------------------------------------------
    // Construcción de la interfaz
    // -------------------------------------------------------------------------
    private void construirUI() {
        setLayout(new BorderLayout());

        add(crearBanner(), BorderLayout.NORTH);

        panelCentro.add(crearPanelTaxonomia(), "TAXONOMIA");
        panelCentro.add(crearPanelCultivo(),  "CULTIVO");
        panelCentro.add(crearPanelLista(),    "LISTA");
        panelCentro.add(crearPanelMenu(),     "MENU");

        JSplitPane split = new JSplitPane(JSplitPane.VERTICAL_SPLIT, panelCentro, crearPanelConsola());
        split.setResizeWeight(0.58);
        split.setDividerLocation(360);
        add(split, BorderLayout.CENTER);
    }

    private JComponent crearBanner() {
        JPanel banner = new JPanel(new BorderLayout());
        banner.setBackground(VERDE_OSC);
        banner.setBorder(new EmptyBorder(12, 18, 12, 18));

        JLabel titulo = new JLabel("\uD83C\uDF31  AGROCICLO");
        titulo.setForeground(Color.WHITE);
        titulo.setFont(new Font("SansSerif", Font.BOLD, 22));

        lblInfo = new JLabel("Seleccione una rama taxonómica para comenzar");
        lblInfo.setForeground(new Color(210, 235, 220));
        lblInfo.setFont(new Font("SansSerif", Font.PLAIN, 13));

        banner.add(titulo, BorderLayout.WEST);
        banner.add(lblInfo, BorderLayout.EAST);
        return banner;
    }

    // ----- Pantalla 1: Taxonomía -----
    private JComponent crearPanelTaxonomia() {
        JPanel p = new JPanel(new BorderLayout(0, 14));
        p.setBorder(new EmptyBorder(20, 24, 20, 24));

        JLabel t = titulo("Seleccione la rama taxonómica de trabajo");
        p.add(t, BorderLayout.NORTH);

        JPanel grid = new JPanel(new GridLayout(0, 1, 0, 10));
        String[][] ramas = {
            {"Solanum",        "Tomate, papa, pimiento, berenjena"},
            {"Musáceas",       "Plátano, banano, guineo"},
            {"Cucurbitáceas",  "Zapallo, calabaza, pepino, melón, sandía"},
            {"Leguminosas",    "Frijol, arveja, haba, lenteja"},
            {"Brasicáceas",    "Brócoli, col, coliflor, rábano, nabos"}
        };
        for (String[] r : ramas) {
            JButton b = botonPrimario("<html><b>" + r[0] + "</b><br><span style='font-size:9px'>" + r[1] + "</span></html>");
            b.addActionListener(e -> seleccionarTaxonomia(r[0]));
            grid.add(b);
        }
        p.add(grid, BorderLayout.CENTER);

        JButton guia = new JButton("Ver guía taxonómica completa");
        guia.addActionListener(e -> mostrarGuia());
        p.add(guia, BorderLayout.SOUTH);
        return p;
    }

    private void seleccionarTaxonomia(String taxonomia) {
        taxonomiaSeleccionada = taxonomia;
        ContextoAgroCiclo.setTaxonomia(taxonomia);
        lblInfo.setText("Rama actual: " + taxonomia);
        System.out.println(">>> Sistema configurado exitosamente para: " + taxonomia);
        txtNombre.setText("");
        txtKm2.setText("");
        cards.show(panelCentro, "CULTIVO");
        txtNombre.requestFocusInWindow();
    }

    private void mostrarGuia() {
        String guia =
            "1. Solanaceae: Tomate, papa, pimiento, berenjena.\n" +
            "2. Musáceas: Plátano, banano, guineo.\n" +
            "3. Cucurbitáceas: Zapallo, calabaza, pepino, melón, sandía.\n" +
            "4. Leguminosas: Frijol, arveja, haba, lenteja.\n" +
            "5. Brasicáceas: Brócoli, col, coliflor, rábano, nabos.";
        JOptionPane.showMessageDialog(this, guia,
                "Catálogo taxonómico y características", JOptionPane.INFORMATION_MESSAGE);
    }

    // ----- Pantalla 2: Crear cultivo -----
    private JComponent crearPanelCultivo() {
        JPanel p = new JPanel(new GridBagLayout());
        p.setBorder(new EmptyBorder(20, 24, 20, 24));
        GridBagConstraints g = new GridBagConstraints();
        g.insets = new Insets(8, 8, 8, 8);
        g.anchor = GridBagConstraints.WEST;
        g.fill = GridBagConstraints.HORIZONTAL;

        g.gridx = 0; g.gridy = 0; g.gridwidth = 2;
        p.add(titulo("Registrar nuevo cultivo"), g);
        g.gridwidth = 1;

        g.gridx = 0; g.gridy = 1; p.add(new JLabel("Nombre del cultivo:"), g);
        txtNombre = new JTextField(20);
        g.gridx = 1; p.add(txtNombre, g);

        g.gridx = 0; g.gridy = 2; p.add(new JLabel("Área en km² (mayor a 0):"), g);
        txtKm2 = new JTextField(20);
        g.gridx = 1; p.add(txtKm2, g);

        JButton crear = botonPrimario("Crear cultivo");
        crear.addActionListener(e -> crearCultivo());
        g.gridx = 1; g.gridy = 3; p.add(crear, g);

        JButton volver = new JButton("Volver a la lista");
        volver.addActionListener(e -> { if (!misSelecciones.isEmpty()) refrescarLista(); cards.show(panelCentro, misSelecciones.isEmpty() ? "TAXONOMIA" : "LISTA"); });
        g.gridx = 0; g.gridy = 3; p.add(volver, g);

        return p;
    }

    private void crearCultivo() {
        String nombre = txtNombre.getText().trim();
        if (nombre.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Ingrese el nombre del cultivo.", "Dato faltante", JOptionPane.WARNING_MESSAGE);
            return;
        }
        double km2;
        try {
            km2 = Double.parseDouble(txtKm2.getText().trim());
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Ingrese un valor numérico válido (use el punto para decimales).",
                    "Área inválida", JOptionPane.WARNING_MESSAGE);
            return;
        }
        if (km2 <= 0) {
            JOptionPane.showMessageDialog(this, "El área debe ser mayor a 0.", "Área inválida", JOptionPane.WARNING_MESSAGE);
            return;
        }
        Cultivos nuevo = new Cultivos(taxonomiaSeleccionada, nombre, km2);
        misSelecciones.add(nuevo);
        refrescarLista();
        cards.show(panelCentro, "LISTA");
    }

    // ----- Pantalla 3: Lista de cultivos -----
    private JComponent crearPanelLista() {
        JPanel p = new JPanel(new BorderLayout(0, 12));
        p.setBorder(new EmptyBorder(20, 24, 20, 24));
        p.add(titulo("Listado de cultivos"), BorderLayout.NORTH);

        listaCultivos = new JList<>(modeloLista);
        listaCultivos.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        listaCultivos.setFont(new Font("SansSerif", Font.PLAIN, 14));
        p.add(new JScrollPane(listaCultivos), BorderLayout.CENTER);

        JPanel botones = new JPanel(new GridLayout(1, 3, 10, 0));
        JButton trabajar = botonPrimario("Trabajar con cultivo");
        trabajar.addActionListener(e -> trabajarConCultivo());
        JButton anadir = new JButton("Añadir nuevo cultivo");
        anadir.addActionListener(e -> { taxonomiaSeleccionada = ""; cards.show(panelCentro, "TAXONOMIA"); });
        JButton salir = new JButton("Salir");
        salir.addActionListener(e -> salirAplicacion());
        botones.add(trabajar); botones.add(anadir); botones.add(salir);
        p.add(botones, BorderLayout.SOUTH);
        return p;
    }

    private void refrescarLista() {
        modeloLista.clear();
        for (Cultivos c : misSelecciones) {
            modeloLista.addElement("ID " + c.getId() + "  —  " + c.getNombre() + "  (" + c.getCategoria() + ")");
        }
        if (!modeloLista.isEmpty()) listaCultivos.setSelectedIndex(modeloLista.size() - 1);
    }

    private void trabajarConCultivo() {
        int idx = listaCultivos.getSelectedIndex();
        if (idx < 0 || idx >= misSelecciones.size()) {
            JOptionPane.showMessageDialog(this, "Seleccione un cultivo de la lista.", "Sin selección", JOptionPane.WARNING_MESSAGE);
            return;
        }
        cultivoActivo = misSelecciones.get(idx);
        ContextoAgroCiclo.setTaxonomia(cultivoActivo.getCategoria());
        System.out.println(">>> Trabajando con: " + cultivoActivo.getNombre());
        lblCultivoActivo.setText("Cultivo activo: " + cultivoActivo.getNombre() + "  (" + cultivoActivo.getCategoria() + ")");
        lblInfo.setText("Trabajando con: " + cultivoActivo.getNombre());
        cards.show(panelCentro, "MENU");
    }

    // ----- Pantalla 4: Menú de operaciones -----


    private JComponent crearPanelMenu() {
        JPanel p = new JPanel(new BorderLayout(0, 14));
        p.setBorder(new EmptyBorder(20, 24, 20, 24));

        lblCultivoActivo = titulo("Cultivo activo");
        p.add(lblCultivoActivo, BorderLayout.NORTH);

        JPanel grid = new JPanel(new GridLayout(0, 1, 0, 10));
        JButton bSuelo = botonPrimario("1. Suelo");
        bSuelo.addActionListener(e -> ejecutarOperacion(
                () -> gestorSuelo.gestionarSuelo(cultivoActivo, parcelas, scanner)));

        JButton bPersonal = botonPrimario("2. Personal");
        bPersonal.addActionListener(e -> ejecutarOperacion(
                () -> gestorPer.gestionarPersonal(cultivoActivo, voluntarios, trabajadores, scanner, contadorPersonal)));

        JButton bClima = botonPrimario("3. Clima");
        bClima.addActionListener(e -> ejecutarOperacion(
                () -> gestorEst.mostrarEstadisticasClimaticas(cultivoActivo, scanner)));

        //JButton bReporte = botonPrimario("4. Realizar reporte");
        //bReporte.addActionListener(e -> ejecutarOperacion(this::imprimirReporte));

        JButton bReporte = botonPrimario("4. Realizar reporte");
        bReporte.addActionListener(e -> generarReporteEnVentana());

        JButton bVolver = new JButton("5. Volver a cultivos");
        bVolver.addActionListener(e -> cards.show(panelCentro, "LISTA"));

        botonesMenu.clear();
        for (JButton b : new JButton[]{bSuelo, bPersonal, bClima, bReporte}) { botonesMenu.add(b); grid.add(b); }
        grid.add(bVolver);
        p.add(grid, BorderLayout.CENTER);

        JLabel ayuda = new JLabel("<html><i>Suelo, Personal y Clima son interactivos: responda en el campo de la consola de abajo.</i></html>");
        ayuda.setForeground(Color.GRAY);
        p.add(ayuda, BorderLayout.SOUTH);
        return p;
    }

    private void imprimirReporte() {
        System.out.println("\n--- Imprimiendo reporte ---");
        gestorEst.imprimirReportePrediccion(cultivoActivo);
        gestorSuelo.imprimirReportePrediccion(cultivoActivo);
        gestorBio.imprimirReportePrediccion(cultivoActivo);
        gestorPer.imprimirReportePrediccion(cultivoActivo);
    }

    /** Ejecuta una operación de negocio en un hilo aparte para no congelar la ventana. */
    private void ejecutarOperacion(Runnable accion) {
        if (operacionEnCurso) return;
        operacionEnCurso = true;
        setBotonesMenu(false);
        campoEntrada.requestFocusInWindow();
        new Thread(() -> {
            try {
                accion.run();
            } catch (Exception ex) {
                System.out.println("--- Error inesperado: " + ex.getMessage() + " ---");
            } finally {
                operacionEnCurso = false;
                SwingUtilities.invokeLater(() -> setBotonesMenu(true));
            }
        }, "AgroCiclo-Operacion").start();
    }

    private void setBotonesMenu(boolean activo) {
        for (JButton b : botonesMenu) b.setEnabled(activo);
    }

    // ----- Consola inferior -----
    private JComponent crearPanelConsola() {
        JPanel p = new JPanel(new BorderLayout(0, 6));
        p.setBorder(new TitledBorder("Consola / Salida del sistema"));

        p.add(new JScrollPane(consola), BorderLayout.CENTER);

        JPanel fila = new JPanel(new BorderLayout(6, 0));
        campoEntrada = new JTextField();
        campoEntrada.addActionListener(e -> enviarEntrada());   // Enter envía
        botonEnviar = new JButton("Enviar");
        botonEnviar.addActionListener(e -> enviarEntrada());
        fila.add(new JLabel("Respuesta: "), BorderLayout.WEST);
        fila.add(campoEntrada, BorderLayout.CENTER);
        fila.add(botonEnviar, BorderLayout.EAST);
        p.add(fila, BorderLayout.SOUTH);
        return p;
    }

    private void salirAplicacion() {
        int r = JOptionPane.showConfirmDialog(this, "¿Salir de AgroCiclo?", "Confirmar",
                JOptionPane.YES_NO_OPTION);
        if (r == JOptionPane.YES_OPTION) dispose();
    }

    // ----- Utilidades de estilo -----
    private JLabel titulo(String texto) {
        JLabel l = new JLabel(texto);
        l.setFont(new Font("SansSerif", Font.BOLD, 17));
        l.setForeground(VERDE_OSC);
        return l;
    }

    private JButton botonPrimario(String texto) {
        JButton b = new JButton(texto);
        b.setBackground(VERDE);
        b.setForeground(Color.WHITE);
        b.setFocusPainted(false);
        b.setFont(new Font("SansSerif", Font.PLAIN, 14));
        b.setBorder(new EmptyBorder(10, 14, 10, 14));
        return b;
    }

    public static void main(String[] args) {
        //try { UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName()); } catch (Exception ignore) {}
        SwingUtilities.invokeLater(() -> new AgroCicloGUI().setVisible(true));
    }
    /*private void mostrarVentanaInfo(String titulo, String contenido) {
        JTextArea textArea = new JTextArea(20, 40);
        textArea.setText(contenido);
        textArea.setEditable(false);
        textArea.setFont(new Font("Monospaced", Font.PLAIN, 12));
        JScrollPane scroll = new JScrollPane(textArea);
        JOptionPane.showMessageDialog(this, scroll, titulo, JOptionPane.INFORMATION_MESSAGE);
    }*/

    /*public static void main(String[] args) {
    SwingUtilities.invokeLater(() -> {
        new AgroCicloGUI().setVisible(true);
    });
}*/
    private void mostrarVentanaInfo(String titulo, String contenido) {
        JTextArea textArea = new JTextArea(10, 40);
        textArea.setText(contenido);
        textArea.setEditable(false);
        textArea.setFont(new Font("Monospaced", Font.PLAIN, 12));
        JScrollPane scroll = new JScrollPane(textArea);
        JOptionPane.showMessageDialog(this, scroll, titulo, JOptionPane.INFORMATION_MESSAGE);
    }
    private String capturarSalida(Runnable accion) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        PrintStream ps = new PrintStream(baos);
        PrintStream original = System.out;
        System.setOut(ps);
        accion.run();
        System.out.flush();
        System.setOut(original);
        return baos.toString();
    }
    private void mostrarInfoSuelos() {
        String info = "INFORMACIÓN DE SUELOS\n\n" +
                "Arenoso: Se siente áspero y granuloso al tacto, es muy suelto y el agua se filtra de inmediato sin formar una masa.\n\n" +
                "Arcilloso: Muy pegajoso y moldeable cuando está húmedo (parece plastilina), se vuelve duro como piedra y se agrieta al secarse.\n\n" +
                "Franco: Textura equilibrada y suave; forma una bola firme al apretarla con la mano que se deshace fácilmente con un toque.";

        mostrarVentanaInfo("Definición de Suelos", info);
    }
    private void generarReporteEnVentana() {
        // Definir dónde guardaremos el texto temporalmente
        java.io.ByteArrayOutputStream bao = new java.io.ByteArrayOutputStream();
        java.io.PrintStream ps = new java.io.PrintStream(bao);
        java.io.PrintStream originalOut = System.out;

        // Redirigir la salida hacia nuestro buffer temporal
        System.setOut(ps);

        // Llamar a tu método original que hace los impresos (imprimirReporte)
        imprimirReporte();

        // Restaurar la salida original para no romper la consola de la interfaz
        System.out.flush();
        System.setOut(originalOut);

        // Obtener el texto capturado
        String resultado = bao.toString();

        // Mostrar en una ventana grande
        JTextArea textArea = new JTextArea(20, 60); // Tamaño grande
        textArea.setText(resultado);
        textArea.setEditable(false);
        textArea.setFont(new Font("Monospaced", Font.PLAIN, 13));
        JScrollPane scroll = new JScrollPane(textArea);

        JOptionPane.showMessageDialog(this, scroll, "Reporte Completo", JOptionPane.INFORMATION_MESSAGE);
    }
}
