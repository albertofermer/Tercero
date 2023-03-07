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
public class Comprar extends Thread {

    private Random r;
    private Tienda t;
    private long id;
    private ImageIcon imagen;
    private char quienAtiende;

    public Comprar(Tienda t) {
        r = new Random();
        this.t = t;
        imagen = getIcono("src\\clienteC.png");
    }

    @Override
    public void run() {
        id = Thread.currentThread().getId();
        try {
            //ProtocoloEntrada
            quienAtiende = t.EntraComprar(id,imagen);
            comprando();
            t.SaleComprar(quienAtiende,id,imagen);
            //Protocolo de salida
        } catch (InterruptedException ex) {
            System.out.println("ERROR: Hilo interrumpido...");
        }
    }

    public void comprando() throws InterruptedException {

        System.out.println("El cliente(c) " + id + " está comprando");
        int espera = (abs(r.nextInt()) % 2000) + 1000;
        //System.out.println("El cliente(c) espera " + espera);
        sleep(espera);
        System.out.println("El cliente(c) " + id + " ha acabado de comprar.");
    }


    private ImageIcon getIcono(String ruta) {

        ImageIcon img = new ImageIcon(ruta);
        Image imagen = img.getImage();
        Image newimg = imagen.getScaledInstance(150, 150, Image.SCALE_SMOOTH);
        ImageIcon icono = new ImageIcon(newimg);

        return icono;
    }
}
