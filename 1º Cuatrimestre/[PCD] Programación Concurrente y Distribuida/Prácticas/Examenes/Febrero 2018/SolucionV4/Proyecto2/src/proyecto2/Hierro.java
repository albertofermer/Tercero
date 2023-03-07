/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package proyecto2;

import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author pedro
 */
public class Hierro implements Runnable {

    Vibradora t;

    public Hierro(Vibradora tl) {
        t = tl;
    }

    @Override
    public void run() {
        Random rnd = new Random();
        rnd.setSeed(System.currentTimeMillis());
        try {

            System.out.println("Soy Hierro " + Thread.currentThread().getName());
            t.entraHierro();
            System.out.println("      ----> HIERRO " + Thread.currentThread().getName() + " EN PULIDORA");
            Thread.sleep((rnd.nextInt(4) + 1) * 3000);
            System.out.println("      <---- FIN HIERRO " + Thread.currentThread().getName());
            t.saleHierro();

        } catch (InterruptedException ex) {
            Logger.getLogger(Hierro.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
