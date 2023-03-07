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
public class Piscina {

    private int aletaslibres = 5, manoplaslibres = 5, piscinalibre = 5;
    ReentrantLock mutex = new ReentrantLock();
    Condition colapiscina = mutex.newCondition();
    Condition colaaletas = mutex.newCondition();
    Condition colamanoplas = mutex.newCondition();

    public void entrapiscina() {
        mutex.lock();
        try {
            if (piscinalibre == 0) {
                colapiscina.await();
            }
            System.out.println("----> ENTRA PISCINA " + Thread.currentThread().getName());
            piscinalibre--;
        } catch (InterruptedException ex) {
            Logger.getLogger(Piscina.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            mutex.unlock();
        }
    }

    public void salepiscina() {
        mutex.lock();
        try {
            piscinalibre++;
            System.out.println("<---- FIN PISCINA " + Thread.currentThread().getName());
            colapiscina.signal();
        } finally {
            mutex.unlock();
        }

    }

    public void cogematerial() {
        mutex.lock();
        try {
            if (aletaslibres < 2) {
                colaaletas.await();
            }
            aletaslibres -= 2;
            System.out.println("                           ----> ALETAS " + Thread.currentThread().getName() + " EN PISCINA");
            if (manoplaslibres < 2) {
                colamanoplas.await();
            }
            manoplaslibres -= 2;
            System.out.println("                           ----> MANOPLAS " + Thread.currentThread().getName() + " EN PISCINA");
        } catch (InterruptedException ex) {
            Logger.getLogger(Piscina.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            mutex.unlock();
        }

    }

    public void sueltamaterial() {
        mutex.lock();
        try {
            aletaslibres += 2;
            System.out.println("                           <---- FIN ALETAS " + Thread.currentThread().getName());
            colaaletas.signal();
            manoplaslibres += 2;
            System.out.println("                           <---- FIN MANOPLAS " + Thread.currentThread().getName());
            colamanoplas.signal();
        } finally {
            mutex.unlock();
        }

    }

}
