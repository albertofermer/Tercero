package proyecto1;

import static java.lang.Math.abs;
import java.util.Random;
import java.util.concurrent.Semaphore;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Alberto Fernández
 */
public class Cinta extends Thread {

    private Semaphore semaforo;
    private Random r;

    public Cinta(Semaphore m) {
        r = new Random();
        semaforo = m;
    }

    @Override
    public void run() {
        while (true) {
            try {
                int cantidad = abs(r.nextInt())% (5000 - 2000) + 2000;
                semaforo.Rellena(cantidad);
                sleep(abs(r.nextInt()) % (5000 - 1000) + 1000);
            } catch (InterruptedException ex) {
                System.out.println("La cinta ha terminado");
                break;
            }

        }
    }
}
