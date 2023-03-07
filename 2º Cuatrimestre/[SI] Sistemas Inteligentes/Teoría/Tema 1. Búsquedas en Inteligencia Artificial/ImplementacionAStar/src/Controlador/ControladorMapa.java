package Controlador;

import Modelo.Mapa;
import Vista.MapaGrafico;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import javax.swing.SwingWorker;

/**
 *
 * @author Alberto Fernández
 */
public class ControladorMapa implements ActionListener {

    private MapaGrafico mg = null;

    public ControladorMapa(int x, int y) {

        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                mg = new MapaGrafico(x, y);
                mg.setVisible(true);
                addListener();
            }
        });

    }

    public void addListener() {
        mg.botonEmpezar.addActionListener(this);
        mg.LimpiarBoton.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        switch (e.getActionCommand()) {
            case "Limpiar":
                mg.limpiarTipos();
                break;
            case "Buscar":
                if (mg.contieneValor(1) && mg.contieneValor(2)) {
                    final SwingWorker worker = new SwingWorker() {
                        @Override
                        protected Object doInBackground() throws Exception {
                            Mapa m = new Mapa(mg.getXInicial(), mg.getYInicial(), mg.getXDestino(), mg.getYDestino(), mg.getMapaPropiedades());
                            mg.limpiarRecorrido();
                            try {
                                mg.pintarRuta(m.getRuta());
                            } catch (InterruptedException ex) {
                                Logger.getLogger(ControladorMapa.class.getName()).log(Level.SEVERE, null, ex);
                            }
                            return null;
                        }
                    };
                    worker.execute();
                } else {
                    JOptionPane.showMessageDialog(null, "Es necesario colocar una casilla de inicio y otra objetivo.");
                }
                break;

            default:
                throw new AssertionError();
        }

    }

}
