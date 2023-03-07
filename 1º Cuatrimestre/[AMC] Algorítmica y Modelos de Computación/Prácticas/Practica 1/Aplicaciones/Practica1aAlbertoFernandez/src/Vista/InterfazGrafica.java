package Vista;

import Controlador.Controlador;
import Modelo.Algoritmos;
import Modelo.Punto;
import static Modelo.Punto.distancia;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Image;
import java.awt.Toolkit;
import java.text.DecimalFormat;
import javax.swing.ImageIcon;

/**
 *
 * @author Alberto Fernández
 */
public class InterfazGrafica extends javax.swing.JFrame {

    DecimalFormat df = new DecimalFormat("####0.00");
    Controlador c;
    Algoritmos a;
    InformacionAvanzada ia;
    final String COLORAQUA = "#058D9B";

    /**
     * Creates new form InterfazGrafica
     * @param c
     * @param ia
     */
//    public InterfazGrafica() {
//        initComponents();
//        //CanvasPuntos = new CanvasPuntos();
//        //this.add(CanvasPuntos);
//        
//            }
    public InterfazGrafica(Controlador c, InformacionAvanzada ia) {

        this.c = c;
        this.ia = ia;
        //ver(c.getPuntos()); //Puntos vacios...
        this.a = new Algoritmos(this, Controlador.getvAleatorio(), Controlador.getInformacionAvanzada(), Controlador.getvMensaje());
        initComponents();
         this.setIconImage(new ImageIcon(getClass().getResource("icono.png")).getImage());
        canvas1.setPreferredSize(Toolkit.getDefaultToolkit().getScreenSize());
        a.setCanvas(canvas1);
    }

    public Canvas getCanvas() {
        return this.canvas1;
    }

    public Algoritmos getAlgoritmo() {
        return this.a;
    }

    public void escribirPuntos(int[] indice) {
        this.PuntoS.setText("Punto[" + indice[0] + "]:");
        this.PuntoT.setText("Punto[" + indice[1] + "]:");
        this.PuntoU.setText("Punto[" + indice[2] + "]:");
    }

    public void escribirEtiquetasPuntos(Punto x, Punto y, Punto z) {
        this.EtiquetaPuntoS.setText("" + x);
        this.EtiquetaPuntoT.setText("" + y);
        this.EtiquetaPuntoU.setText("" + z);
    }

    public void escribirEtiquetasDistancia(Punto x, Punto y, Punto z) {
        this.EtiquetaDistanciaST.setText(df.format(distancia(x, y)));
        this.EtiquetaDistanciaSU.setText(df.format(distancia(x, z)));
    }

    public void escribirInformacionAvanzada(String tipoAlgoritmo, long TiempoFin, int repeticiones) {
        this.BotonInformacionAvanzada.setEnabled(true);
        ia.NombreAlgoritmo.setText(tipoAlgoritmo);
        ia.repeticiones.setText(Integer.toString(repeticiones));

        if (TiempoFin > 1e9) {
            ia.TiempoEjec.setText("" + (TiempoFin) / 1e9 + " s");
        } else if (TiempoFin > 1e6) {
            ia.TiempoEjec.setText("" + (TiempoFin) / 1e6 + " ms");
        } else {
            ia.TiempoEjec.setText("" + (TiempoFin) + " ns");
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel2 = new javax.swing.JPanel();
        canvas1 = new CanvasPuntos(a);
        jPanel1 = new javax.swing.JPanel();
        jPanel5 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        BotonAyuda = new javax.swing.JButton();
        jLabel5 = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        BotonFichero = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        PuntoS = new javax.swing.JLabel();
        PuntoT = new javax.swing.JLabel();
        PuntoU = new javax.swing.JLabel();
        EtiquetaPuntoS = new javax.swing.JLabel();
        EtiquetaPuntoT = new javax.swing.JLabel();
        EtiquetaPuntoU = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        EtiquetaDistanciaST = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        EtiquetaDistanciaSU = new javax.swing.JLabel();
        filler2 = new javax.swing.Box.Filler(new java.awt.Dimension(0, 0), new java.awt.Dimension(0, 0), new java.awt.Dimension(32767, 0));
        Fichero = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        dmin = new javax.swing.JLabel();
        filler3 = new javax.swing.Box.Filler(new java.awt.Dimension(0, 0), new java.awt.Dimension(0, 0), new java.awt.Dimension(32767, 0));
        jPanel7 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        BotonAleatorio = new javax.swing.JButton();
        jPanel6 = new javax.swing.JPanel();
        BotonBuscar = new javax.swing.JButton();
        BotonSalir = new javax.swing.JButton();
        Algoritmo = new javax.swing.JComboBox<>();
        jSeparator1 = new javax.swing.JSeparator();
        jLabel4 = new javax.swing.JLabel();
        BotonInformacionAvanzada = new javax.swing.JButton();
        esExperimental = new javax.swing.JCheckBox();
        Repeticiones = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Práctica 1 - [AMC]");
        setExtendedState(6);
        setFocusCycleRoot(false);
        setMinimumSize(new java.awt.Dimension(600, 600));
        setName("Ventana"); // NOI18N

        jPanel2.setBackground(new java.awt.Color(255, 204, 51));

        //canvas1 = new CanvasPuntos();
        canvas1.setBackground(new java.awt.Color(255, 255, 255));

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(canvas1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(canvas1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        getContentPane().add(jPanel2, java.awt.BorderLayout.CENTER);

        jPanel1.setBackground(new java.awt.Color(0, 153, 204));
        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder("Informacion"));
        jPanel1.setLayout(new javax.swing.BoxLayout(jPanel1, javax.swing.BoxLayout.LINE_AXIS));

        jPanel5.setBackground(new java.awt.Color(153, 255, 255));
        jPanel5.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jPanel3.setBackground(Color.decode(COLORAQUA));
        jPanel3.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));

        BotonAyuda.setBounds(10,10,20,20);
        BotonAyuda.setBackground(new java.awt.Color(204, 0, 102));
        ImageIcon imagen = new ImageIcon("src\\Vista\\ayuda.png");
        Image img = imagen.getImage();
        Image newimg = img.getScaledInstance(BotonAyuda.getWidth(), BotonAyuda.getHeight(), Image.SCALE_SMOOTH);
        ImageIcon icono = new ImageIcon(newimg);
        BotonAyuda.setIcon(icono);
        BotonAyuda.setMnemonic('H');
        BotonAyuda.setActionCommand("Ayuda");
        BotonAyuda.setBorderPainted(false);
        BotonAyuda.setContentAreaFilled(false);
        BotonAyuda.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        BotonAyuda.setDisabledIcon(icono);
        BotonAyuda.setDisabledSelectedIcon(icono);
        BotonAyuda.setFocusPainted(false);
        BotonAyuda.setMargin(new java.awt.Insets(0, 0, 0, 0));
        BotonAyuda.setMaximumSize(new java.awt.Dimension(25, 25));
        BotonAyuda.setMinimumSize(new java.awt.Dimension(25, 25));
        BotonAyuda.setNextFocusableComponent(BotonAyuda);
        BotonAyuda.setPreferredSize(new java.awt.Dimension(20, 20));
        BotonAyuda.setPressedIcon(icono);
        BotonAyuda.setRolloverIcon(icono);
        BotonAyuda.setRolloverSelectedIcon(icono);
        BotonAyuda.setSelected(true);
        BotonAyuda.setSelectedIcon(icono);
        BotonAyuda.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BotonAyudaActionPerformed(evt);
            }
        });

        jLabel5.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(255, 255, 255));
        jLabel5.setText("Alberto Fernández Merchán");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 123, Short.MAX_VALUE)
                .addComponent(BotonAyuda, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(BotonAyuda, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel4.setBackground(Color.decode(COLORAQUA));
        jPanel4.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));

        BotonFichero.setText("Elegir un fichero ...");
        BotonFichero.setActionCommand("ElegirFichero");
        BotonFichero.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("Elige un fichero .tsp de tu PC");

        jLabel3.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setText("Fichero Seleccionado");

        PuntoS.setBackground(new java.awt.Color(255, 255, 255));
        PuntoS.setForeground(new java.awt.Color(255, 255, 255));
        PuntoS.setText("Punto S - ");

        PuntoT.setBackground(new java.awt.Color(255, 255, 255));
        PuntoT.setForeground(new java.awt.Color(255, 255, 255));
        PuntoT.setText("Punto T -");

        PuntoU.setForeground(new java.awt.Color(255, 255, 255));
        PuntoU.setText("Punto U -");

        EtiquetaPuntoS.setForeground(new java.awt.Color(255, 255, 255));
        EtiquetaPuntoS.setText("X: Y: Z:");

        EtiquetaPuntoT.setForeground(new java.awt.Color(255, 255, 255));
        EtiquetaPuntoT.setText("X: Y: Z:");

        EtiquetaPuntoU.setForeground(new java.awt.Color(255, 255, 255));
        EtiquetaPuntoU.setText("X: Y: Z:");

        jLabel9.setForeground(new java.awt.Color(255, 255, 255));
        jLabel9.setText("Distancia S-T:");

        EtiquetaDistanciaST.setForeground(new java.awt.Color(255, 255, 255));
        EtiquetaDistanciaST.setText("Distancia");

        jLabel10.setForeground(new java.awt.Color(255, 255, 255));
        jLabel10.setText("Distancia S-U: ");

        EtiquetaDistanciaSU.setForeground(new java.awt.Color(255, 255, 255));
        EtiquetaDistanciaSU.setText("Distancia");

        Fichero.setForeground(new java.awt.Color(255, 255, 255));
        Fichero.setText("---");

        jLabel6.setForeground(new java.awt.Color(255, 255, 255));
        jLabel6.setText("D. mínima");

        dmin.setForeground(new java.awt.Color(255, 255, 255));
        dmin.setText("---");

        jPanel7.setBackground(Color.decode(COLORAQUA));
        jPanel7.setForeground(Color.decode(COLORAQUA));

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("Genera un fichero .tsp aleatorio");

        BotonAleatorio.setText("Aleatorio");
        BotonAleatorio.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1)
                    .addComponent(BotonAleatorio))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(BotonAleatorio)
                .addContainerGap(21, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel2)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(BotonFichero)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(Fichero)
                            .addComponent(jLabel3))))
                .addGap(27, 27, 27)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(PuntoS)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(EtiquetaPuntoS)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel9)
                        .addGap(6, 6, 6))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addComponent(PuntoT)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(EtiquetaPuntoT)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(filler2, javax.swing.GroupLayout.DEFAULT_SIZE, 58, Short.MAX_VALUE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED))
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addComponent(PuntoU)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(EtiquetaPuntoU)
                                .addGap(54, 54, 54)))
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel6)
                            .addComponent(jLabel10))))
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(EtiquetaDistanciaST)
                    .addComponent(dmin)
                    .addComponent(EtiquetaDistanciaSU))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 36, Short.MAX_VALUE)
                .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(filler3, javax.swing.GroupLayout.PREFERRED_SIZE, 0, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel2)
                            .addComponent(PuntoS)
                            .addComponent(EtiquetaPuntoS)
                            .addComponent(jLabel9)
                            .addComponent(EtiquetaDistanciaST))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(BotonFichero)
                                .addComponent(jLabel3))
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(filler2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(PuntoT)
                                        .addComponent(EtiquetaPuntoT)
                                        .addComponent(jLabel10)
                                        .addComponent(EtiquetaDistanciaSU)))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(PuntoU)
                                        .addComponent(EtiquetaPuntoU)
                                        .addComponent(Fichero))
                                    .addComponent(jLabel6)
                                    .addComponent(dmin))))
                        .addContainerGap(32, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                        .addComponent(filler3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addContainerGap())))
        );

        jPanel6.setBackground(Color.decode(COLORAQUA));
        jPanel6.setForeground(new java.awt.Color(255, 255, 255));

        BotonBuscar.setText("Buscar");
        BotonBuscar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

        BotonSalir.setText("Salir");
        BotonSalir.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        BotonSalir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BotonSalirActionPerformed(evt);
            }
        });

        Algoritmo.setSelectedItem("Exhaustivo");
        Algoritmo.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Divide y Vencerás", "Exhaustivo"}));
        Algoritmo.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        Algoritmo.setName(""); // NOI18N

        jSeparator1.setForeground(new java.awt.Color(255, 255, 255));
        jSeparator1.setOrientation(javax.swing.SwingConstants.VERTICAL);

        jLabel4.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(255, 255, 255));
        jLabel4.setText("Elige el algoritmo:");

        BotonInformacionAvanzada.setText("Información Avanzada");
        BotonInformacionAvanzada.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BotonInformacionAvanzadaActionPerformed(evt);
            }
        });

        esExperimental.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        esExperimental.setText("Estudio Experimental");
        esExperimental.setToolTipText("Selecciona para repetir la búsqueda.");
        esExperimental.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));

        Repeticiones.setText("10");
        Repeticiones.setToolTipText("Número de veces que se repetirá la búsqueda.");
        Repeticiones.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                RepeticionesKeyTyped(evt);
            }
        });

        jLabel7.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(255, 255, 255));
        jLabel7.setText("Repeticiones");

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGap(37, 37, 37)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(esExperimental)
                    .addComponent(Algoritmo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4))
                .addGap(18, 18, 18)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(Repeticiones))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 38, Short.MAX_VALUE)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 2, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(BotonBuscar)
                    .addComponent(BotonInformacionAvanzada)
                    .addComponent(BotonSalir, javax.swing.GroupLayout.Alignment.TRAILING))
                .addContainerGap(59, Short.MAX_VALUE))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jSeparator1)
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addGap(14, 14, 14)
                        .addComponent(jLabel4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(Algoritmo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel7))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(esExperimental)
                            .addComponent(Repeticiones, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addContainerGap())
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addComponent(BotonBuscar)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(BotonInformacionAvanzada)
                        .addGap(12, 12, 12)
                        .addComponent(BotonSalir)
                        .addGap(0, 15, Short.MAX_VALUE))))
        );

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(44, 44, 44)
                .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(18, 18, 18)
                .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jPanel3, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel4, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel1.add(jPanel5);

        getContentPane().add(jPanel1, java.awt.BorderLayout.PAGE_END);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void BotonSalirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BotonSalirActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_BotonSalirActionPerformed

    private void BotonInformacionAvanzadaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BotonInformacionAvanzadaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_BotonInformacionAvanzadaActionPerformed

    private void BotonAyudaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BotonAyudaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_BotonAyudaActionPerformed

    private void RepeticionesKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_RepeticionesKeyTyped
        int key = evt.getKeyChar();

        boolean numeros = key >= 48 && key <= 57;

        if (!numeros) {
            evt.consume();
        }

        if (Repeticiones.getText().trim().length() == 3) {
            evt.consume();
        }
    }//GEN-LAST:event_RepeticionesKeyTyped

    /**
     * @param args the command line arguments
     */
//    public static void main(String args[]) {
//        /* Set the Nimbus look and feel */
//        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
//        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
//         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
//         */
//        try {
//            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
//                if ("Nimbus".equals(info.getName())) {
//                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
//                    break;
//                }
//            }
//        } catch (ClassNotFoundException ex) {
//            java.util.logging.Logger.getLogger(InterfazGrafica.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        } catch (InstantiationException ex) {
//            java.util.logging.Logger.getLogger(InterfazGrafica.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        } catch (IllegalAccessException ex) {
//            java.util.logging.Logger.getLogger(InterfazGrafica.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
//            java.util.logging.Logger.getLogger(InterfazGrafica.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        }
//        //</editor-fold>
//
//        /* Create and display the form */
//        java.awt.EventQueue.invokeLater(new Runnable() {
//            @Override
//            public void run() {
//                new InterfazGrafica().setVisible(true);
//            }
//        });
//    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    public javax.swing.JComboBox<String> Algoritmo;
    public javax.swing.JButton BotonAleatorio;
    public javax.swing.JButton BotonAyuda;
    public javax.swing.JButton BotonBuscar;
    public javax.swing.JButton BotonFichero;
    public javax.swing.JButton BotonInformacionAvanzada;
    public javax.swing.JButton BotonSalir;
    public javax.swing.JLabel EtiquetaDistanciaST;
    public javax.swing.JLabel EtiquetaDistanciaSU;
    public javax.swing.JLabel EtiquetaPuntoS;
    public javax.swing.JLabel EtiquetaPuntoT;
    public javax.swing.JLabel EtiquetaPuntoU;
    public javax.swing.JLabel Fichero;
    public javax.swing.JLabel PuntoS;
    public javax.swing.JLabel PuntoT;
    public javax.swing.JLabel PuntoU;
    public javax.swing.JTextField Repeticiones;
    public java.awt.Canvas canvas1;
    public javax.swing.JLabel dmin;
    public javax.swing.JCheckBox esExperimental;
    private javax.swing.Box.Filler filler2;
    private javax.swing.Box.Filler filler3;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JSeparator jSeparator1;
    // End of variables declaration//GEN-END:variables
}
