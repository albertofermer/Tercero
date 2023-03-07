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
public class Pantalon implements Runnable {

    Linea t;

    public Pantalon(Linea tl) {
        t = tl;
    }

    @Override
    public void run() {
        Random rnd = new Random();
        rnd.setSeed(System.currentTimeMillis());
        try {
            System.out.println("                           ----> Llega Pantalon " + Thread.currentThread().getName());
            t.EntraCorte();
            System.out.println("                           ----> Pantalon " + Thread.currentThread().getName() + " EN CORTE");
            Thread.sleep(2000);
            System.out.println("                           ----> Pantalon " + Thread.currentThread().getName() + " CORTADO");            
            t.CoserPantalon();
            System.out.println("                           ----> Pantalon " + Thread.currentThread().getName() + " COSIENDO");            
            Thread.sleep(3000);
            System.out.println("                           <---- FIN Pantalon " + Thread.currentThread().getName());
            t.SaleCoser();

        } catch (InterruptedException ex) {
            Logger.getLogger(Pantalon.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
