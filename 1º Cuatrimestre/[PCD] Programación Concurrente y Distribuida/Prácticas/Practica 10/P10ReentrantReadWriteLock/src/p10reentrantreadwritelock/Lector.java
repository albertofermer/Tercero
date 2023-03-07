package p10reentrantreadwritelock;

import static java.lang.Math.abs;
import java.util.Random;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author usuario
 */
public class Lector extends Thread {

    private ReentrantReadWriteLock l = null;
    private Random r = null;

    public Lector(ReentrantReadWriteLock l) {
        r = new Random();
        this.l = l;
    }

    @Override
    public void run() {

        try {
            l.readLock().lock();
            try {
                leyendo();
                sleep(1 / 4 * 4000);
            } finally {
                l.readLock().unlock();
            }
//----------------------------------------------------
            if (abs(r.nextInt()) % 100 < 25) {
                l.writeLock().lock();
                //l.readLock().unlock(); eliminar linea 32
                try {
                    System.out.println("He cambiado de rol");
                    sleep(1 / 2 * 4000);
                } finally {
                    //l.readLock().lock(); eliminar linea 47
                    l.writeLock().unlock();
                }
            }
//-----------------------------------------------------
            l.readLock().lock();

            try {
                sleep(1 / 4 * 4000);
            } finally {
                l.readLock().unlock();
            }
        } catch (InterruptedException ex) {
            Logger.getLogger(Lector.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    private void leyendo() {
        System.out.println("Lector leyendo...");
    }

}
