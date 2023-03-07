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
    private Ordenador o;

    public consumidor(PilaLenta p, Ordenador o) {
        this.o = o;
        r = new Random();
        r.setSeed(System.nanoTime());
        this.p = p;
    }

    @Override
    public void run() {
        int intento = 0;
        int elemento;
        for (int i = 0; i < 15; i++) {
            try {
                do{
                intento = o.compruebaConsumidor(intento);
                sleep(abs(r.nextInt()) % 2000 + 1000);
                elemento = (int) p.Desapila();
                o.finaliza();
                   }while(elemento == -1);
                System.out.println("El consumidor [" + Thread.currentThread().getId() + "] desapila " + elemento);
            } catch (Exception ex) {
                //System.out.println(ex.getMessage());
                break;
            }

        }
        System.out.println("Termina consumidor " + Thread.currentThread().getId());

    }
}
