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
    private final int id;
    private final Lienzo c;

    public Furgo(Tunel t, int id, Lienzo c) {
        tunel = t;
        this.id = id;
        this.c = c;
    }

    @Override
    public void run() {
        dibujaEntrada(id);
        int numeroTunel = tunel.EntraFurgo(id);
        
        
        //Sección Crítica
        System.out.println("Lavando furgoneta");
        borraEntrada(id);
        dibujaLavado(numeroTunel, id);
        try {
            sleep(abs(r.nextInt() % (TIEMPO_MAX - TIEMPO_MIN) + TIEMPO_MIN));
        } catch (InterruptedException ex) {
            Logger.getLogger(Coche.class.getName()).log(Level.SEVERE, null, ex);
        }

        tunel.SaleFurgo(numeroTunel, id);
        borraLavado(numeroTunel, id);
    }

    private void dibujaEntrada(int id) {
        c.dibujaEntrada(id, 'f');
    }

    private void borraEntrada(int id) {
        c.borraEntrada(id, 'f');
    }

    private void dibujaLavado(int tunel, int id) {
        c.dibujaLavado(tunel, id, 'f');
    }

    private void borraLavado(int tunel, int id) {
        c.borraLavado(tunel, id, 'f');
    }

}
