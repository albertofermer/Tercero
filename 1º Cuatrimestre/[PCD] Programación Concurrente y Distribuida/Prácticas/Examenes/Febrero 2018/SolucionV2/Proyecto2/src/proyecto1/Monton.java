/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proyecto1;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 *
 * @author pedro
 */
public class Monton {

    int cantidad = 4, esperagrande = 0;
    ReentrantLock mutex = new ReentrantLock();
    Condition colagrande = mutex.newCondition();
    Condition colachica = mutex.newCondition();

    public void CargaPoco() throws InterruptedException {
        mutex.lock();
        try {
            if (cantidad == 0) {
                colachica.await();
            }
            cantidad--;
        } finally {
            mutex.unlock();
        }
    }

    public void CargaMucho() throws InterruptedException {
        mutex.lock();
        try {
            if (cantidad < 2) {
                colagrande.await();
            }
            cantidad = cantidad - 2;
        } finally {
            mutex.unlock();
        }
    }

    public void Rellena(int cuanto) {
        mutex.lock();
        try {
            cantidad = cantidad + cuanto; //  Minimo hay dos, que es lo menos que deja
            System.out.println("Dejo " + cuanto + " y hay " + cantidad);
            colagrande.signal();
            if (cantidad == 3) {
                colachica.signal();
            } else if (cantidad > 3) {
                colachica.signal();
                colachica.signal();
            }
        } finally {
            mutex.unlock();
        }
    }
}
