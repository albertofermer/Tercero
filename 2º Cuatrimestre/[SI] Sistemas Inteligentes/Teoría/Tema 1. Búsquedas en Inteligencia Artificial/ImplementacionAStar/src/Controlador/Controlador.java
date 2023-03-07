package Controlador;

import Vista.VentanaPrincipal;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 *
 * @author Alberto Fernández
 */
public class Controlador implements ActionListener {

    private VentanaPrincipal vp = new VentanaPrincipal();
    private ControladorMapa cm = null;

    public Controlador() {
        vp.setVisible(true);

        addListener();
    }

    public void addListener() {
        vp.generarBoton.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        switch (e.getActionCommand()) {
            case "Generar":
                java.awt.EventQueue.invokeLater(new Runnable() {
                    @Override
                    public void run() {
                        cm = new ControladorMapa(Integer.parseInt(vp.jTextField1.getText()), Integer.parseInt(vp.jTextField2.getText()));
                    }
                });

                break;
            default:
                throw new AssertionError();
        }

    }

}
