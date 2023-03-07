/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proyecto3;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 *
 * @author usuario
 */
public class Sala {

    private boolean libre = true;
    int esperandoconcita = 0;
    int esperandosincita = 0;
    int entraconcita=0;
    ReentrantLock mutex = new ReentrantLock();
    Condition colaconcita = mutex.newCondition();
    Condition colasincita = mutex.newCondition();

    public void ConcitaIN() throws Exception {
        mutex.lock();
        try {
            esperandoconcita++;
            while (!libre) {
                colaconcita.await();
            }
            esperandoconcita--;
            if(esperandosincita>0) entraconcita++;
            libre = false;
        } finally {
            mutex.unlock();
        }
    }

    public void ConcitaOUT() {
        mutex.lock();
        try {
            libre = true;
            if ((esperandosincita > 0 && entraconcita>=2) || esperandoconcita ==0) {
                colasincita.signal();
            } else {
                colaconcita.signal();
            }
        } finally {
            mutex.unlock();
        }
    }

    public void SincitaIN() throws Exception {
        mutex.lock();
        try {
            esperandosincita++;
            while (!libre) {
                colasincita.await();
            }
            esperandosincita--;
            entraconcita=0;
            libre = false;
        } finally {
            mutex.unlock();
        }
    }

    public synchronized void SincitaOUT() {
        mutex.lock();
        try {
            libre = true;
            if (esperandoconcita > 0) {
                colaconcita.signal();
            } else {
                colasincita.signal();
            }
        } finally {
            mutex.unlock();
        }
    }
}
