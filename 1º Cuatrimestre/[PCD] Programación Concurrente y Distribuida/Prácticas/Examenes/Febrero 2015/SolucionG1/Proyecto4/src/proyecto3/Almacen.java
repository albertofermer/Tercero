/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package proyecto3;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 *
 * @author pedro
 */
public class Almacen {

    private int capacidad;
    private int fruta;
    ReentrantLock mutex = new ReentrantLock();
    Condition hayfruta = mutex.newCondition();
    Condition hayhueco = mutex.newCondition();

    public Almacen(int _capacidad, int _fruta) {
        capacidad = _capacidad;
        fruta = _fruta;
    }

    public void Deposita(int id, int cuanto) {
        try {
            mutex.lock();
            while (capacidad - fruta < cuanto) {
                hayhueco.await();
            }
            fruta = fruta + cuanto;
            System.out.println("CONTENIDO: " + fruta);
            hayfruta.signalAll();
        } catch (InterruptedException ex) {
            System.out.println(id + "No ha podido entregar la fruta");
            System.exit(0);
        } finally {
            mutex.unlock();
        }
    }

    public void Compra(int id, int cuanto) {
        try {
            int intentos = 0;
            mutex.lock();
            while (fruta < cuanto && intentos < 2) {
                hayfruta.await();
                intentos++;
            }
            if (intentos < 2) {
                fruta = fruta - cuanto;
                System.out.println("CONTENIDO: " + fruta);
                hayhueco.signalAll();
            }
            else{
                System.out.println(id+" se marcha ABURRIDO");
            }
        } catch (InterruptedException ex) {
            System.out.println(id + "No ha podido comprar la fruta");
            System.exit(0);
        } finally {
            mutex.unlock();
        }

    }
}
