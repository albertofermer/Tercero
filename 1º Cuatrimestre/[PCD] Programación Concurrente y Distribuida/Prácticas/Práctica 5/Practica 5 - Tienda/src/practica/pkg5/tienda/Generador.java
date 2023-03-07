package practica.pkg5.tienda;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;
import static java.lang.Math.abs;
import static java.lang.Thread.sleep;
import java.util.Random;

/**
 *
 * @author Alberto Fernández
 */
public class Generador {

    static final int NUMEROCLIENTES = 10;
    static Dimension PANTALLA = Toolkit.getDefaultToolkit().getScreenSize();

    public static void main(String[] args) throws InterruptedException {
        
        Thread[] clientes = new Thread[NUMEROCLIENTES];
        //Crear ventana
        Visualizacion ventana = new Visualizacion();
        ventana.setSize(PANTALLA);
        CanvasTienda lienzo = new CanvasTienda();
        lienzo.setSize(ventana.getWidth(), ventana.getHeight());
        lienzo.setBackground(Color.decode("#5CBD70"));
        ventana.add(lienzo);
        ventana.setVisible(true);

        Tienda t = new Tienda(lienzo);
        Random r = new Random();

        for (int i = 0; i < NUMEROCLIENTES; i++) {
            int aleatorio = abs(r.nextInt()) % 10;
            if (aleatorio < 5) { // 50%
                clientes[i] = new Comprar(t);
                clientes[i].start();
                sleep(abs(r.nextInt() % 1000) + 1000);
            } else {
                clientes[i] = new Thread(new Reparar(t));
                clientes[i].start();
                sleep(abs(r.nextInt() % 1000) + 1000);
            }
        }

        //Termina cuando terminen los clientes
        for (int i = 0; i < NUMEROCLIENTES; i++) {
            clientes[i].join();
        }

        System.out.println("Han terminado los clientes");
        System.exit(0);
    }
}
