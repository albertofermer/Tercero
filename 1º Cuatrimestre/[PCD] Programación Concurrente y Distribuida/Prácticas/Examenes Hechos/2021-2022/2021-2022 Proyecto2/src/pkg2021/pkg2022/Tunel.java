package pkg2021.pkg2022;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Controlará la entrada de los vehículos e implementará los métodos:
 *
 * @author Alberto Fernández
 */
public class Tunel {

    private boolean tunelesLibres[];
    private int cochesEsperando;
    private int furgosEsperando;
    private Lock lock;
    private Condition colaCoches;
    private Condition colaFurgos;

    /**
     * Constructor de la clase
     */
    public Tunel() {

        lock = new ReentrantLock(true);
        colaCoches = lock.newCondition();
        colaFurgos = lock.newCondition();

        cochesEsperando = 0;
        furgosEsperando = 0;

        tunelesLibres = new boolean[3]; //Furgonetas solo pueden usar el 0 y el 2 (prioridad en el 2)

        for (int i = 0; i < tunelesLibres.length; i++) {
            tunelesLibres[i] = true;
        }
    }

    /**
     * Es invocado por los coches que quieren entrar al túnel
     *
     * @return
     */
    public int EntraCoche() { //prioridad en el tunel 0
        lock.lock();
        int i = 0;
        try {
            while (!tunelesLibres[0] && !tunelesLibres[1] && !tunelesLibres[2]) {
                try {
                    System.out.println("Coche esperando en el tunel de lavado");
                    cochesEsperando++;
                    colaCoches.await();
                } catch (InterruptedException ex) {
                    Logger.getLogger(Tunel.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            cochesEsperando--;
            boolean encontrado = false;
            while (!encontrado) {
                if (tunelesLibres[i]) {
                    tunelesLibres[i] = false;
                    encontrado = true;
                } else {
                    i++;
                }
            }
            System.out.println("Un coche ha ocupado el tunel " + i + ".");
        } finally {
            lock.unlock();
        }

        return i;
    }

    /**
     * Es invocado por las furgonetas que quieren entrar al túnel.
     *
     * @return
     */
    public int EntraFurgo() { //prioridad en el tunel 0
        lock.lock();
        int i = 0;
        try {

            while (!tunelesLibres[0] && !tunelesLibres[2]) {
                try {
                    System.out.println("Furgoneta esperando en el tunel de lavado");
                    furgosEsperando++;
                    colaFurgos.await();
                } catch (InterruptedException ex) {
                    Logger.getLogger(Tunel.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            furgosEsperando--;
            boolean encontrado = false;
            while (!encontrado) {
                if (tunelesLibres[i] && (i == 0 || i == 2)) {
                    tunelesLibres[i] = false;
                    encontrado = true;
                } else {
                    i++;
                }
            }

            System.out.println("Una furgoneta ha ocupado el tunel " + i + ".");

        } finally {
            lock.unlock();
        }
        return i;
    }

    /**
     * Invocado por los coches cuando salen del túnel.
     *
     * @param i
     */
    public void SaleCoche(int i) {
        lock.lock();
        try {
            System.out.println("Un coche ha salido del tunel " + i);
            tunelesLibres[i] = true;
            if (i <= 1) {
                if (cochesEsperando > 0) {
                    colaCoches.signal();
                } else {
                    colaFurgos.signal();
                }
            } else {
                if (furgosEsperando > 0) {
                    colaFurgos.signal();
                } else {
                    colaCoches.signal();
                }
            }
        } finally {

            lock.unlock();
        }

    }

    /**
     * Invocado por las furgonetas cuando salen del túnel.
     *
     * @param i
     */
    public void SaleFurgo(int i) {
        lock.lock();
        try {
            System.out.println("Una furgoneta ha salido del tunel " + i);
            tunelesLibres[i] = true;
            if (i == 0) {
                if (cochesEsperando > 0) {
                    colaCoches.signal();
                } else {
                    colaFurgos.signal();
                }
            } else {
                if (furgosEsperando > 0) {
                    colaFurgos.signal();
                } else {
                    colaCoches.signal();
                }
            }
        } finally {
            lock.unlock();
        }

    }
}
