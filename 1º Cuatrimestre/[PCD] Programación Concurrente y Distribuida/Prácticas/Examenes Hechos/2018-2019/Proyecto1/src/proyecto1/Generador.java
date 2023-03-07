package proyecto1;

import static java.lang.Thread.sleep;
import java.util.Random;

/**
 *
 * @author Alberto Fernández
 */
public class Generador {

    public static void main(String[] args) throws InterruptedException {

        Random r = new Random();
        Piscina p = new Piscina();
        Thread[] nadadores = new Thread[10];
        for (int i = 0; i < 10; i++) {
            nadadores[i] = new Thread(new Nadador(i, p));
            nadadores[i].start();
            sleep(r.nextInt() % (2000 - 1000) + 1000);
        }
        
        for (int i = 0; i < 10; i++) {
            nadadores[i].join();
        }
    }
}
