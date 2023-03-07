package pkg2021.pkg2022;

import static java.lang.Math.abs;
import static java.lang.Thread.sleep;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Reoresentará, mediante un hilo, cada una de las furgonetas que llegan al
 * túnel de lavado. El hilo se creará implementado la interfaz Runnable. El
 * tiempo de lavado será aleatorio entre 1 y 3 segundos.
 *
 * @author Alberto Fernández
 */
public class Furgo implements Runnable {

    private Tunel tunel;
    private int TIEMPO_MIN = 5000;
    private int TIEMPO_MAX = 10000;
    private Random r = new Random();

    public Furgo(Tunel t) {
        tunel = t;
    }

    @Override
    public void run() {
        int numeroTunel = tunel.EntraFurgo();

        //Sección Crítica
        System.out.println("Lavando furgoneta");
        try {
            sleep(abs(r.nextInt() % (TIEMPO_MAX - TIEMPO_MIN) + TIEMPO_MIN));
        } catch (InterruptedException ex) {
            Logger.getLogger(Coche.class.getName()).log(Level.SEVERE, null, ex);
        }

        tunel.SaleFurgo(numeroTunel);
    }

}
