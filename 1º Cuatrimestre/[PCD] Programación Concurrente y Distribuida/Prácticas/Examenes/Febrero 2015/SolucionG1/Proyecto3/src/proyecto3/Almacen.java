/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package proyecto3;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author pedro
 */
public class Almacen {

    private int capacidad;
    private int fruta;

    public Almacen(int _capacidad, int _fruta) {
        capacidad = _capacidad;
        fruta = _fruta;
    }

    public synchronized void Deposita(int id, int cuanto) {
        try {
            while (capacidad - fruta < cuanto) {
                wait();
            }
            fruta = fruta + cuanto;
            System.out.println("CONTENIDO: " + fruta);
            notifyAll();
        } catch (InterruptedException ex) {
            System.out.println(id + "No ha podido entregar la fruta");
            System.exit(0);
        }
    }

    public synchronized void Compra(int id, int cuanto) {
        try {
            while (fruta < cuanto) {
                wait();
            }
            fruta = fruta - cuanto;
            System.out.println("CONTENIDO: " + fruta);
            notifyAll();
        } catch (InterruptedException ex) {
            System.out.println(id + "No ha podido comprar la fruta");
            System.exit(0);
        }

    }
}
