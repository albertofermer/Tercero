package practica.pkg5.tienda;

import java.awt.Image;
import static java.lang.Math.abs;
import static java.lang.Thread.sleep;
import java.util.Random;
import javax.swing.ImageIcon;

/**
 *
 * @author Alberto Fernández
 */
public class Reparar implements Runnable {

    private Random r;
    private Tienda t;
    private long id;
    private ImageIcon imagen;

    public Reparar(Tienda t) {
        r = new Random();
        this.t = t;
        imagen = getIcono("src\\clienteR.png");

    }

    @Override
    public void run() {
        id = Thread.currentThread().getId();
        try {
            //ProtocoloEntrada
            t.EntraReparar(id, imagen);
            reparando();
            t.SaleReparar(id, imagen);
            //ProtocoloSalida
        } catch (InterruptedException e) {
            System.out.println("ERROR: Hilo interrumpido");
        }
    }

    public void reparando() throws InterruptedException {

        System.out.println("El cliente(r) " + id + " está reparando");
        int espera = (abs(r.nextInt()) % 2000) + 1000;
        //System.out.println("El cliente(r) espera " + espera);
        sleep(espera);
        System.out.println("El cliente(r) " + id + " ha acabado de reparar.");
    }

    private ImageIcon getIcono(String ruta) {

        ImageIcon img = new ImageIcon(ruta);
        Image imagen = img.getImage();
        Image newimg = imagen.getScaledInstance(150, 150, Image.SCALE_SMOOTH);
        ImageIcon icono = new ImageIcon(newimg);

        return icono;
    }

}
