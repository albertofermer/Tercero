/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package proyecto2;

import java.util.Random;
import java.util.concurrent.Semaphore;

/**
 *
 * @author pedro
 */
public class Proveedor implements Runnable {

    private int id;
    private Semaphore fruta;

    public Proveedor(int i, Semaphore fruta) {
        id = i;
        this.fruta = fruta;
    }

    @Override
    public void run() {
        Random rnd = new Random();
        rnd.setSeed(System.currentTimeMillis() + id);
        int cantidad = rnd.nextInt(4) + 2;
        System.out.println(" .......................... Soy el Proveedor " + id + " y pongo " + cantidad);
        for (int i = 0; i < cantidad; i++) {
            fruta.release();
        }
        System.out.println("........................... Soy el Proveedor " + id + " marchandome");
    }
}
