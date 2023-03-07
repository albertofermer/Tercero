/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Aparcamientos;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 *
 * @author pedro
 */
public class Parking {

    private int numcoches = 0, numbus = 0, numcochesenbus, busesperando = 0;
    private int capacidadcoches, capacidadcochesenbus, capacidadbuses;
    ReentrantLock mutex;
    Condition colacoche, colabus;

    public Parking(int capacidadcoches, int capacidadcochesenbus, int capacidadbuses) {
        this.capacidadcoches = capacidadcoches;
        this.capacidadbuses = capacidadbuses;
        this.capacidadcochesenbus = capacidadcochesenbus;
        mutex = new ReentrantLock();
        colacoche = mutex.newCondition();
        colabus = mutex.newCondition();

    }

    public int entraCoche() throws InterruptedException {

        int cual;
        mutex.lock();
        try {
            if (numcoches >= capacidadcoches && (busesperando > 0 || numcochesenbus >= capacidadcochesenbus)) {
                colacoche.await();
            }
            if (numcoches < capacidadcoches) {
                numcoches++;
                cual = 1;
            } else {
                numcochesenbus++;
                cual = 2;
            }
            return cual;
        } finally {
            mutex.unlock();
        }
    }

    public  void saleCoche(int cual) {
        mutex.lock();
        try {
            if (cual == 1) {
                numcoches--;
                colacoche.signal();
            } else {
                numcochesenbus--;
                if (busesperando > 0) {
                    if (numcochesenbus == 0) {
                        colabus.signal();
                    }
                } else {
                    colacoche.signal();
                }
            }
        } finally {
            mutex.unlock();
        }
    }

    public  void entraBus() throws InterruptedException {
        mutex.lock();
        try {
            busesperando++;
            if (numbus >= capacidadbuses || numcochesenbus > 0) {
                colabus.await();
            }
            numbus++;
        } finally {
            mutex.unlock();
        }
    }

    public  void saleBus() {
        mutex.lock();
        try {
            numbus--;
            busesperando--;
            if (busesperando > 0) {
                colabus.signal();
            } else {
                colacoche.signal();
                colacoche.signal();
            }
        } finally {
            mutex.unlock();
        }
    }
}
