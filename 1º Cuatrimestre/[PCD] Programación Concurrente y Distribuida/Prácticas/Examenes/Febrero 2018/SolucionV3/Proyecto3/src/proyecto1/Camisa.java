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
public class Camisa extends Thread {

    Semaphore corte, cose;

    public Camisa(Semaphore corte, Semaphore cose) {
        this.cose = cose;
        this.corte = corte;
    }

    @Override
    public void run() {
        Random rnd = new Random();
        rnd.setSeed(System.currentTimeMillis());
        try {

            System.out.println("----> Llega Camisa " + getName());
            corte.acquire();
            System.out.println("----> Camisa " + getName() + " EN CORTE");
            Thread.sleep(2000);
            System.out.println("----> Camisa " + getName() + "CORTADA");
            cose.acquire();
            corte.release();
            System.out.println("---> Camisa " + getName() + " COSIENDO");            
            Thread.sleep(2000);
            System.out.println("<---- FIN Camisa " + getName());
            cose.release();

        } catch (InterruptedException ex) {
            Logger.getLogger(Pantalon.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
