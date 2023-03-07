package proyecto1;

import static java.lang.Math.abs;
import static java.lang.Thread.sleep;
import java.util.Random;
import java.util.concurrent.Semaphore;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Alberto Fernández
 */
public class Grande implements Runnable {

    private int id;
    private Semaphore m;
    private Random r;

    public Grande(Semaphore m, int id) {
        r = new Random();
        this.id = id;
        this.m = m;
    }

    @Override
    public void run() {
        try {
            System.out.println("Comienza a funcionar la cargadora grande " + id);
            int i = 0;
            while (i < 7) {
                m.CargaMucho();
                sleep(abs(r.nextInt()) % (4000 - 2000) + 2000);
                i++;
            }
        } catch (InterruptedException ex) {
            Logger.getLogger(Pequenya.class.getName()).log(Level.SEVERE, null, ex);
        }

        System.out.println("Cargadora Grande" + id + " ha finalizado.");
    }

}
