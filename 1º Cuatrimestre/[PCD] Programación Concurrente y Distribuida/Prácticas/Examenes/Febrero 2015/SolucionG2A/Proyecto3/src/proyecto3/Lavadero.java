/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package proyecto3;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author pedro
 */
public class Lavadero {

    private boolean cochelibre = true, ttlibre = true;
    Lock mutex = new ReentrantLock();
    int ttencola = 0;
    Condition colacoche = mutex.newCondition();
    Condition colatt = mutex.newCondition();

    public int Llegacoche() {
        int coge = 0;
        try {
            mutex.lock();
            while (!cochelibre && !ttlibre) {
                colacoche.await();
            }
            if (cochelibre) {
                cochelibre = false;
                coge = 1;
            } else {
                ttlibre = false;
                coge = 2;
            }
        } catch (InterruptedException ex) {
            Logger.getLogger(Lavadero.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            mutex.unlock();
        }
        return coge;
    }

    public void LlegaTT() {
        try {
            mutex.lock();
            ttencola++;
            while (!ttlibre) {
                colatt.await();
            }
            ttencola--;
            ttlibre = false;
        } catch (InterruptedException ex) {
            Logger.getLogger(Lavadero.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            mutex.unlock();
        }
    }

    public void SaleCoche(int cual) {
        try {
            mutex.lock();
            if (cual == 1) {
                cochelibre = true;
                colacoche.signal();
            } else {
                SaleTT();
            }
        } finally {
            mutex.unlock();
        }
    }

    public void SaleTT() {
        try {
            mutex.lock();
            ttlibre = true;
            if (ttencola > 0) {
                colatt.signal();
            } else {
                colacoche.signal();
            }
        } finally {
            mutex.unlock();
        }
    }
}
