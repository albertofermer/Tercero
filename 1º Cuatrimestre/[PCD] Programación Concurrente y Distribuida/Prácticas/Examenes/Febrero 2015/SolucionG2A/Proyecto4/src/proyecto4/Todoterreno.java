/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package proyecto4;

import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author pedro
 */
public class Todoterreno extends Thread {

    private int id;
    private Lavadero lv;

    public Todoterreno(int i, Lavadero _lv) {
        id = i;
        lv = _lv;
    }

    @Override
    public void run() {
        try {
            Random rnd = new Random();
            rnd.setSeed(System.currentTimeMillis() + id);
            System.out.println("Soy el Todoterreno " + id + " sucio ");
            lv.LlegaTT();
            System.out.println("Soy el Todoterreno " + id + " LAVANDOME ");
            Thread.sleep((rnd.nextInt(5) + 2) * 1000);
            System.out.println("Soy el Todoterreno " + id + " LIMPIO");
            lv.SaleTT();

        } catch (InterruptedException ex) {
            Logger.getLogger(Todoterreno.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
