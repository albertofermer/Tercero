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
public class Taller {

    private int operarios = 4, esperacamion = 0;
    ReentrantLock mutex = new ReentrantLock();
    Condition colacoche = mutex.newCondition();
    Condition colacamion = mutex.newCondition();

    public void entracoche() {
        mutex.lock();
        try {
            if (operarios == 0 || esperacamion > 0) {
                colacoche.await();
            }
            operarios--;
        } catch (InterruptedException ex) {
        } finally {
            mutex.unlock();
        }
    }

    public void salecoche() {
        mutex.lock();
        try {
            operarios++;
            if (esperacamion > 0) {
                if (operarios >= 2) {
                    colacamion.signal();
                }
            } else {
                colacoche.signal();
            }
        } finally {
            mutex.unlock();
        }
    }

    public void entracamion() {
        mutex.lock();
        try {
            esperacamion++;
            if (operarios < 2) {
                colacamion.await();
            }
            operarios = operarios - 2;
            esperacamion--;
        } catch (InterruptedException ex) {
        } finally {
            mutex.unlock();
        }
    }

    public void salecamion() {
        mutex.lock();
        try {
            operarios = operarios + 2;
            if (esperacamion > 0) {
                colacamion.signal();
            } else {
                colacoche.signal();
                colacoche.signal();
            }
        } finally {
            mutex.unlock();
        }
    }
}
