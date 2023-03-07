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

    private int libres = 5, esperaadulto = 0, adultos = 0;
    ReentrantLock mutex = new ReentrantLock();
    Condition colaadulto = mutex.newCondition();
    Condition colaninyo = mutex.newCondition();

    public void entraadulto() throws InterruptedException {
        mutex.lock();
        try {
            esperaadulto++;
            if (libres == 0) {
                colaadulto.await();
            }
            libres--;
            esperaadulto--;
            adultos++;
            System.out.println("----> ADULTO " + Thread.currentThread().getName() + " Libres = " + libres);
        } finally {
            mutex.unlock();
        }
    }

    public void saleadulto() {
        mutex.lock();
        try {
            libres++;
            adultos--;
            System.out.println("<---- FIN ADULTO " + Thread.currentThread().getName() + " Libres = " + libres);
            if (esperaadulto > 0) {
                colaadulto.signal();
            } else if (libres >= 2) {
                colaninyo.signal();
            }
        } finally {
            mutex.unlock();
        }
    }

    public boolean entraninyo() throws InterruptedException {
        mutex.lock();
        try {
            if (adultos != 0) {
                if (libres < 2) {
                    colaninyo.await();
                }
                libres -= 2;
                System.out.println("                           ----> NINYO " + Thread.currentThread().getName() + ". Libres = " + libres);
                return true;
            } else {
                return false;
            }
        } finally {
            mutex.unlock();
        }
    }

    public void saleninyo() {
        mutex.lock();
        try {
            libres += 2;
            System.out.println("                           <---- FIN NINYO " + Thread.currentThread().getName() + ". Libres = " + libres);
            if (esperaadulto > 0) {
                colaadulto.signal();
                colaadulto.signal();             
            } else {
                colaninyo.signal();
            }
        } finally {
            mutex.unlock();
        }
    }
}
