/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package proyecto2;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author pedro
 */
public class Vibradora {

    private int dentroFE = 0, dentroIno = 0, esperaFE = 0, esperaInox = 0 ;
    ReentrantLock mutex = new ReentrantLock();
    Condition colaFE = mutex.newCondition();
    Condition colaINOX = mutex.newCondition();

    public void entraInox() {
        mutex.lock();
        try {
            if (dentroIno > 2 || dentroFE > 0) {
                try {
                    esperaInox++;
                    colaINOX.await();
                    esperaInox--;
                } catch (InterruptedException ex) {
                    Logger.getLogger(Vibradora.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            dentroIno++;
        } finally {
            mutex.unlock();
        }
    }

    public void saleInox() {
        mutex.lock();
        try {
            dentroIno--;
            if (esperaInox > 0) {
                colaINOX.signal();
            } else if (dentroIno == 0) {
                colaFE.signal();
                colaFE.signal();
            }
        } finally {
            mutex.unlock();
        }
    }

    public void entraHierro() {
        mutex.lock();
        try {
            if (dentroIno > 0 || dentroFE > 1) {
                try {
                    esperaFE++;
                    colaFE.await();
                    esperaFE--;

                } catch (InterruptedException ex) {
                    Logger.getLogger(Vibradora.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            dentroFE++;
        } finally {
            mutex.unlock();
        }
    }

    public void saleHierro() {
        mutex.lock();
        try {
            dentroFE--;
            if (esperaFE > 0) {
                colaFE.signal();
            } else if (dentroFE == 0) {
                for (int i = 0; i < 3; i++) {
                    colaINOX.signal();
                }
            }
        } finally {
            mutex.unlock();
        }
    }
}
