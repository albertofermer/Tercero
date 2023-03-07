/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package proyecto1;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author pedro
 */
public class Piscina {

    private int libresclub = 4, librelibre = 1, esperalibre = 0;
    ReentrantLock mutex = new ReentrantLock();
    Condition colalibre = mutex.newCondition();
    Condition colaclub = mutex.newCondition();

    public int entraclub() {
        int cual = 0;
        mutex.lock();
        try {
            if (libresclub == 0 && librelibre == 0) {
                colaclub.await();
            }
            if (libresclub > 0) {
                libresclub--;
                System.out.println("      ----> CLUB " + Thread.currentThread().getName() + " EN PISCINA 1");
                return 1;
            } else {
                librelibre--;
                System.out.println("      ----> CLUB " + Thread.currentThread().getName() + " EN PISCINA 2");
                return 2;
            }
        } catch (InterruptedException ex) {
            Logger.getLogger(Piscina.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            mutex.unlock();
        }
        return 0;
    }

    public void saleclub(int cual) {
        mutex.lock();
        try {
            if (cual == 1) {
                libresclub++;
                colaclub.signal();
            } else {
                librelibre++;
                if (esperalibre > 0) {
                    colalibre.signal();
                } else {
                    colaclub.signal();
                }
            }
            System.out.println("      <---- FIN CLUB " + Thread.currentThread().getName() + " " + cual);
        } finally {
            mutex.unlock();
        }
    }

    public void entralibre() {
        mutex.lock();
        try {
            esperalibre++;
            if (librelibre == 0) {
                colalibre.await();
            }
            esperalibre--;
            librelibre--;
            System.out.println("                           ----> LIBRE " + Thread.currentThread().getName() + " EN PISCINA");

        } catch (InterruptedException ex) {
            Logger.getLogger(Piscina.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            mutex.unlock();
        }
    }

    public void salelibre() {
        mutex.lock();
        try {
            librelibre++;
            System.out.println("                           <---- FIN LIBRE " + Thread.currentThread().getName());
            if (esperalibre > 0) {
                colalibre.signal();
            } else {
                colaclub.signal();
            }
        } finally {
            mutex.unlock();
        }
    }
}
