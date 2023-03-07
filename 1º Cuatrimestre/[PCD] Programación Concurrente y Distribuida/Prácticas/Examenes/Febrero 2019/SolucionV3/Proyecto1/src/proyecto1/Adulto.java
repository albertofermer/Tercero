/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package proyecto1;

import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author pedro
 */
public class Adulto implements Runnable {

    Piscina t;

    public Adulto(Piscina tl) {
        t = tl;
    }

    @Override
    public void run() {
        Random rnd = new Random();
        rnd.setSeed(System.currentTimeMillis());
        try {            
            System.out.println("Soy Adulto " + Thread.currentThread().getName());
            t.entraadulto();
            Thread.sleep((rnd.nextInt(3) + 3) * 1000);
            t.saleadulto();

        } catch (InterruptedException ex) {
            Logger.getLogger(Adulto.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
