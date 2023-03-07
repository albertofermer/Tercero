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
public class Club implements Runnable {

    Piscina t;

    public Club(Piscina tl) {
        t = tl;
    }

    @Override
    public void run() {
        Random rnd = new Random();
        rnd.setSeed(System.currentTimeMillis());
        try {

            
            System.out.println("Soy del club " + Thread.currentThread().getName());
            int cual=t.entraclub();
            Thread.sleep((rnd.nextInt(3) + 3) * 1000);
            t.saleclub(cual);

        } catch (InterruptedException ex) {
            Logger.getLogger(Club.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
