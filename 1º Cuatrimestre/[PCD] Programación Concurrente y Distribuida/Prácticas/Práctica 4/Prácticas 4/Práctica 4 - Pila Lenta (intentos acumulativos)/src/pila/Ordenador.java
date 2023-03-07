package pila;

/**
 *
 * @author Alberto Fernández
 */
public class Ordenador {

    private PilaLenta p;
    private int capacidad;

    public Ordenador(PilaLenta p, int capacidad) {
        this.p = p;
        this.capacidad = capacidad;

    }

    public synchronized int compruebaConsumidor(int intento) {
     
        while (p.GetNum() == 0 && intento < 3) {
            try {

                System.out.println("El consumidor " + Thread.currentThread().getId() + " está esperando: intento " + (intento + 1));
                wait();    
                intento++;
            } catch (InterruptedException ex) {
                //return;
            }
        }
        if (intento == 3) {
            System.out.println("Consumidor " + Thread.currentThread().getId() + " ha abandonado.");
            Thread.currentThread().interrupt();
        }
        
        return intento;
    }

    public synchronized int compruebaProductor(int intento) {

        while (p.GetNum() == capacidad && intento < 3) {
            try {
                System.out.println("El productor " + Thread.currentThread().getId() + " está esperando: intento " + (intento + 1));
                wait(); 
                intento++;
            } catch (InterruptedException ex) {
                //return -1;
            }
        }

        if (intento == 3) {
            System.out.println("Productor " + Thread.currentThread().getId() + " ha abandonado.");
            Thread.currentThread().interrupt();

        }
        
        return intento;
    }

    public synchronized void finaliza() {
        notifyAll();
    }
}
