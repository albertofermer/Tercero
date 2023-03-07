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
public class Ninyo extends Thread {

    Piscina t;

    public Ninyo(Piscina tl) {
        t = tl;
    }

    @Override
    public void run() {
        Random rnd = new Random();
        rnd.setSeed(System.currentTimeMillis());
        try {

            System.out.println("                            Soy NIÑO " + getName());
            boolean nada = t.entraninyo();
            if (nada) {
                Thread.sleep((rnd.nextInt(5) + 3) * 1000);
                t.saleninyo();
            }else System.out.println("                      NO NADA "+ getName());
        } catch (InterruptedException ex) {
            Logger.getLogger(Adulto.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
