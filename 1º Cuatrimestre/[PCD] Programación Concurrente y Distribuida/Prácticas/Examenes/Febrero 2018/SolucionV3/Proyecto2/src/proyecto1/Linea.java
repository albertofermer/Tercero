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
public class Linea {

    private boolean librecoser = true;
    private int librecorte = 2, esperapantalon = 0;
    ReentrantLock mutex = new ReentrantLock();
    Condition colacorte = mutex.newCondition();
    Condition colacosercamisa = mutex.newCondition();
    Condition colacoserpantalon = mutex.newCondition();

    public void EntraCorte() {
        mutex.lock();
        try {
            if (librecorte == 0) {
                try {
                    colacorte.await();
                } catch (InterruptedException ex) {
                    Logger.getLogger(Linea.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            librecorte--;
        } finally {
            mutex.unlock();
        }
    }

    public void CoserCamisa() {
        mutex.lock();
        try {
            if (!librecoser) {
                try {
                    colacosercamisa.await();
                } catch (InterruptedException ex) {
                    Logger.getLogger(Linea.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            librecoser = false;
            librecorte++;
            colacorte.signal();
        } finally {
            mutex.unlock();
        }
    }

    public void CoserPantalon() {
        mutex.lock();
        try {
            esperapantalon++;
            if (!librecoser) {
                try {
                    colacoserpantalon.await();
                } catch (InterruptedException ex) {
                    Logger.getLogger(Linea.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            esperapantalon--;
            librecoser = false;
            librecorte++;
            colacorte.signal();
        } finally {
            mutex.unlock();
        }
    }

    public void SaleCoser() {
        mutex.lock();
        try {
            librecoser = true;
            if(esperapantalon>0) colacoserpantalon.signal();
            else colacosercamisa.signal();
        } finally {
            mutex.unlock();
        }
    }
}
