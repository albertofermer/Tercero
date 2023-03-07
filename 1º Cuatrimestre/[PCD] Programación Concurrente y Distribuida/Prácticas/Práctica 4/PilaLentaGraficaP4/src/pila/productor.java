package pila;

import static java.lang.Math.abs;
import java.util.Random;

/**
 *
 * @author Alberto Fernández
 */
public class productor extends Thread {

    private Random r;
    private PilaLenta p;
    //private int id;

    public productor(PilaLenta p) {
        //this.id = id;
        this.r = new Random();
        r.setSeed(System.nanoTime());
        this.p = p;
    }

    @Override
    public void run() {

        for (int i = 0; i < 15; i++) {
            int elemento;
            elemento = abs(r.nextInt() % 100);

            try {

                sleep(abs(r.nextInt()) % 2000 + 1000);
                p.Apila(elemento);
                System.out.println("Productor [" + getId() + "] apila " + elemento);

            } catch (Exception ex) {
                System.out.println(ex.getMessage());
                break;

            }

        }
        System.out.println("Termina productor " + getId());

    }

}
