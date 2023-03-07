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
public class Nadador implements Runnable {

    Piscina t;

    public Nadador(Piscina tl) {
        t = tl;
    }

    @Override
    public void run() {
        Random rnd = new Random();
        rnd.setSeed(System.currentTimeMillis());
        try {

            System.out.println("Soy del club " + Thread.currentThread().getName());
            t.entrapiscina();
            Thread.sleep((rnd.nextInt(3) + 3) * 1000);
            t.cogematerial();
            Thread.sleep((rnd.nextInt(3) + 3) * 1000);
            t.sueltamaterial();

            t.salepiscina();

        } catch (InterruptedException ex) {
            Logger.getLogger(Nadador.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
