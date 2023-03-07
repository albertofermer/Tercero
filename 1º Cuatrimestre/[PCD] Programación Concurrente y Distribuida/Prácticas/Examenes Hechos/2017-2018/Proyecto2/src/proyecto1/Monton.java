package proyecto1;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Gestiona la cantidad de mineral que hay en el montón. Inicialmente tiene 4
 * Tm.
 *
 * @author Alberto Fernández
 */
public class Monton {

    private int cantidad;
    private boolean grandeEsperando;
    private int pequeEsperando;
    private Lock l;
    private Condition peque;
    private Condition grande;

    public Monton() {
        l = new ReentrantLock(true);
        peque = l.newCondition();
        grande = l.newCondition();
        grandeEsperando = true;
        pequeEsperando = 2;
        cantidad = 4000;
    }

    public void CargaPoco() throws InterruptedException {
        l.lock();
        try {
            while (cantidad < 1000) {
                System.out.println("No ha suficiente material para que la cargadora pequeña funcione. " + cantidad);
                pequeEsperando++;
                peque.await();
            }

            cantidad -= 1000;
            System.out.println("-1000");
            pequeEsperando--;
        } finally {
            l.unlock();
        }

    }

    public void CargaMucho() throws InterruptedException {
        l.lock();
        try {
            while (cantidad < 2000) {
                System.out.println("No hay suficiente material para que la cargadora grande funcione. " + cantidad);
                grandeEsperando = true;
                grande.await();
            }

            cantidad -= 2000;
            System.out.println("-2000");
            grandeEsperando = false;
        } finally {
            l.unlock();
        }

    }

    public void Rellena(int cantidad) {
        l.lock();
        try {
            this.cantidad += cantidad;
            System.out.println("Se ha aumentado la cantidad del monton en " + cantidad);
            System.out.println("Actualmente hay: " + this.cantidad);

            if (grandeEsperando) {
                grande.signal();
                System.out.println("señal al grande");
            } else {
                peque.signal();
            }
        } finally {
            l.unlock();
        }

    }
}
