/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package proyecto1;

import java.util.Random;
import java.util.concurrent.Semaphore;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author pedro
 */
public class Pantalon implements Runnable {

    Semaphore corte, cose;

    public Pantalon(Semaphore corte, Semaphore cose) {
        this.cose = cose;
        this.corte = corte;
    }

    @Override
    public void run() {
        Random rnd = new Random();
        rnd.setSeed(System.currentTimeMillis());
        try {
            System.out.println("                           ----> Llega Pantalon " + Thread.currentThread().getName());
            corte.acquire();
            System.out.println("                           ----> Pantalon " + Thread.currentThread().getName() + " EN CORTE");
            Thread.sleep(2000);
            System.out.println("                           ----> Pantalon " + Thread.currentThread().getName() + " CORTADO");            
            cose.acquire();
            corte.release();
            System.out.println("                           ----> Pantalon " + Thread.currentThread().getName() + " COSIENDO");            
            Thread.sleep(3000);
            System.out.println("                           <---- FIN Pantalon " + Thread.currentThread().getName());
            cose.release();

        } catch (InterruptedException ex) {
            Logger.getLogger(Pantalon.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
