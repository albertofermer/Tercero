package pkg2021.pkg2022;

import static java.lang.Math.abs;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Representará mediante un hilo, cada uno de los coches que llegan al túnel de
 * lavado. El hilo se creará heredando de la clase Thread. El tiempo de lavado
 * será aleatorio entre 1 y 3 segundos.
 *
 * @author Alberto Fernández
 */
public class Coche extends Thread {

    private Tunel tunel;
    private int TIEMPO_MIN = 5000;
    private int TIEMPO_MAX = 10000;
    private Random r = new Random();
    private final int id;
    private final Lienzo c;

    public Coche(Tunel t, int id, Lienzo c) {
        tunel = t;
        this.id = id;
        this.c = c;
    }

    @Override
    public void run() {
        
        dibujaEntrada(id);
        int numeroTunel = tunel.EntraCoche(id);

        //Sección Crítica
        System.out.println("Lavando coche");
        borraEntrada(id);
        dibujaLavado(numeroTunel, id);
        try {
            sleep(abs(r.nextInt() % (TIEMPO_MAX - TIEMPO_MIN) + TIEMPO_MIN));
        } catch (InterruptedException ex) {
            Logger.getLogger(Coche.class.getName()).log(Level.SEVERE, null, ex);
        }

        tunel.SaleCoche(numeroTunel, id);
        borraLavado(numeroTunel, id);
    }

    private void dibujaEntrada(int id) {
        c.dibujaEntrada(id, 'c');
    }

    private void borraEntrada(int id) {
        c.borraEntrada(id, 'c');
    }

    private void dibujaLavado(int tunel, int id) {
        c.dibujaLavado(tunel, id, 'c');
    }

    private void borraLavado(int tunel, int id) {
        c.borraLavado(tunel, id, 'c');
    }
}
