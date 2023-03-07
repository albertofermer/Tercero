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

    public synchronized void compruebaConsumidor() throws Exception {
        int intento = 0;
        while (p.GetNum() == 0 && intento < 3) {
            try {

                System.out.println("El consumidor " + Thread.currentThread().getId() + " está esperando: intento " + (intento + 1));
                wait();    
                intento++;
            } catch (InterruptedException ex) {
                return;
            }
        }
        if (intento == 3) {
            throw new Exception("Consumidor " + Thread.currentThread().getId() + " ha abandonado.");
        }
    }

    public synchronized void compruebaProductor() throws Exception {
        int intento = 0;
        while (p.GetNum() == capacidad && intento < 3) {
            try {
                System.out.println("El productor " + Thread.currentThread().getId() + " está esperando: intento " + (intento + 1));
                wait(); 
                intento++;
            } catch (InterruptedException ex) {
                return;
            }
        }

        if (intento == 3) {
            throw new Exception("Productor " + Thread.currentThread().getId() + " ha abandonado.");

        }
    }

    public synchronized void finaliza() {
        notifyAll();
    }
}
