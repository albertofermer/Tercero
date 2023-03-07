package practica7;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 *
 * @author usuario
 */
public class Revision {

    int numPortatilLibre;
    int esperandoPracticas;
    boolean ProfesoresConPortatilLibre;
    boolean ProfesorSinPortatilLibre;
    Lock lock;
    Condition teoria;
    Condition practicas;
    char quien;

    public Revision() {
        numPortatilLibre = 2;
        esperandoPracticas = 0;
        ProfesoresConPortatilLibre = true;
        ProfesorSinPortatilLibre = true;
        lock = new ReentrantLock(true);

        teoria = lock.newCondition();
        practicas = lock.newCondition();

    }

    public char entraTeoria() throws InterruptedException {
        lock.lock();

        try {
            if (!ProfesorSinPortatilLibre && (numPortatilLibre == 0)) {
                //System.out.println("El estudiante esta esperando teoria");
                teoria.await(); //Si estan todos los profesores ocupados espero
            }

            if (ProfesorSinPortatilLibre) {
                //atiende el que no tiene portatil
                ProfesorSinPortatilLibre = false;
                quien = 's';
            } else {
                //atiende uno de los que tiene portatil
                numPortatilLibre--;
                quien = 'p';
            }

        } finally {
            lock.unlock();
        }

        return quien;
    }

    public void entraPracticas() throws InterruptedException {
        lock.lock();
        try {
            esperandoPracticas++;
            if (numPortatilLibre == 0) {
                //System.out.println("El estudiante esta esperando practicas");
                practicas.await();
            }
            esperandoPracticas--;
            numPortatilLibre--;

        } finally {
            lock.unlock();
        }
    }

    public void saleTeoria(char quien) {
        lock.lock();
        try {
            if (quien == 's') { //Si le ha atendido el sin portatil
                ProfesorSinPortatilLibre = true;
                // y aviso de que he salido 
                teoria.signal();
            } else {
                numPortatilLibre++;
                //aviso a la cola de practicas de que he salido
                if (esperandoPracticas > 0) {
                    practicas.signal();
                } else {
                    teoria.signal();
                }
            }
        } finally {
            lock.unlock();
        }

        //System.out.println("El estudiante sale de teoria");
    }

    public void salePracticas() {

        lock.lock();
        try {
            numPortatilLibre++;
            if (esperandoPracticas > 0) {
                practicas.signal();
            } else {
                teoria.signal();
            }
        } finally {
            lock.unlock();
        }
        
        //System.out.println("El estudiante sale de practicas");
        //Siempre va a salir de uno con portatil
    }

}
