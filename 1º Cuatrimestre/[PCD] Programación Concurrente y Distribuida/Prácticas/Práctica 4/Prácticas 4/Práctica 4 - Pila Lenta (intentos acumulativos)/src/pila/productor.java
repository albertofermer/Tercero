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
    private Ordenador o;

    public productor(PilaLenta p, Ordenador o) {
        this.o = o;
        this.r = new Random();
        r.setSeed(System.nanoTime());
        this.p = p;
    }

    @Override
    public void run() {
        int intento = 0;
        for (int i = 0; i < 15; i++) {
            int elemento;
            elemento = abs(r.nextInt() % 100);

            try {
                intento = o.compruebaProductor(intento);
                sleep(abs(r.nextInt()) % 2000 + 1000);
                p.Apila(elemento);
                o.finaliza();
                System.out.println("El Productor [" + getId() + "] apila " + elemento);
            } catch (Exception ex) {
                //System.out.println(ex.getMessage());
                break;

            }

        }
        System.out.println("Termina productor " + getId());

    }

}
