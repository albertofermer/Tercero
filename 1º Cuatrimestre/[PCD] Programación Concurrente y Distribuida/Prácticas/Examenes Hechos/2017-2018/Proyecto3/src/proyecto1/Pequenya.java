package proyecto1;

import static java.lang.Math.abs;
import java.util.Random;
import java.util.concurrent.Semaphore;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Representa cada una de las cargadoras pequeñas. Thread. Hacen 10 cargas y
 * finaliza.
 *
 * @author Alberto Fernández
 */
public class Pequenya extends Thread {

    private int id;
    private Semaphore m;
    private Random r;

    public Pequenya(Semaphore m, int id) {
        r = new Random();
        this.id = id;
        this.m = m;
    }

    @Override
    public void run() {
        try {
            System.out.println("Comienza a funcionar la cargadora pequeña " + id);
            int i = 0;
            while (i < 10) {
                m.CargaPoco();
                sleep(abs(r.nextInt()) % (3000 - 1000) + 1000);
                i++;
            }
        } catch (InterruptedException ex) {
            Logger.getLogger(Pequenya.class.getName()).log(Level.SEVERE, null, ex);
        }

        System.out.println("Cargadora " + id + " ha finalizado.");
    }
}
