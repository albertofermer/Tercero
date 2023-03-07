package pila;

import static java.lang.Math.abs;
import static java.lang.Thread.sleep;
import java.util.Random;

/**
 *
 * @author Alberto Fernández
 */
public class consumidor implements Runnable {

    private PilaLenta p;
    private Random r;

    public consumidor(PilaLenta p) {
        r = new Random();
        r.setSeed(System.nanoTime());
        this.p = p;
    }

//    @Override
//    public void run() {
//        int elemento;
//        for (int i = 0; i < 15; i++) {
//
//            try {
//                sleep(abs(r.nextInt()) % 3000 + 1000);
//                elemento = (int) p.Desapila();
//
//            } catch (Exception ex) {
//                System.out.println(ex.getMessage());
//                break;
//            }
//
//        }
//
//    }
    @Override
    public void run() {
        int elemento;
        for (int i = 0; i < 15; i++) {

            try {
                sleep(abs(r.nextInt()) % 2000 + 1000);
                System.out.println("El consumidor [" + Thread.currentThread().getId() + "] desapila " + p.Desapila());

            } catch (Exception ex) {
                System.out.println(ex.getMessage());
                break;
            }

        }
        System.out.println("Termina consumidor " + Thread.currentThread().getId());

    }
}
