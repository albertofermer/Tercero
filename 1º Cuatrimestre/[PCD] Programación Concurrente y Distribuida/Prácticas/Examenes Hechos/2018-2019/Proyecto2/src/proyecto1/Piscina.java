package proyecto1;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Mantendrá el estado de ocupación de la piscina e implementará los métodos:
 * Capacidad: 5 personas Material: 6 aletas y 5 palas
 *
 * @author Alberto Fernández
 */
public class Piscina {

    private int personasDentro;
    private int cantidadAletas;
    private int cantidadPalas;
    private Lock l;
    private Condition cola;

    public Piscina() {
        personasDentro = 0;
        cantidadAletas = 6;
        cantidadPalas = 5;
        l = new ReentrantLock(true);
        cola = l.newCondition();
    }

    public void entraPiscina(int id) throws InterruptedException {
        l.lock();
        try {
            while (personasDentro == 5) {
                System.out.println("Nadador " + id + " está esperando");
                cola.await();
            }
            System.out.println("Nadador " + id + " entra en la piscina");
            personasDentro++;

        } finally {
            l.unlock();
        }

    }

    public void salePiscina(int id) {
        l.lock();
        try {
            System.out.println("Nadador " + id + " sale de la piscina");
            personasDentro--;
            cola.signal();
        } finally {
            l.unlock();
        }

    }

    public void cogeMaterial(int id) throws InterruptedException {
        l.lock();
        try {

            while (cantidadAletas < 2 || cantidadPalas < 2) {
                System.out.println("No hay recursos suficientes para el nadador " + id);
                cola.await();
            }
            System.out.println("El nadador " + id + " coge el material");
            cantidadAletas -= 2;
            cantidadPalas -= 2;
        } finally {
            l.unlock();
        }

    }

    public void sueltaMaterial(int id) {
        l.lock();
        try {
            System.out.println("El nadador " + id + " deja el material");
            cantidadAletas += 2;
            cantidadPalas += 2;
            cola.signal();
        } finally {
            l.unlock();
        }

    }
}
