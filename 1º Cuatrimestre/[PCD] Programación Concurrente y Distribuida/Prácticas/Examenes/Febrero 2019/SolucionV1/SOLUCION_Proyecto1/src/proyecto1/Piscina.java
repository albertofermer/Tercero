/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package proyecto1;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author pedro
 */
public class Piscina {

    private int libresclub = 4, librelibre = 1, esperalibre = 0;

    public synchronized int entraclub() {
        int cual = 0;
        while (libresclub == 0 && (librelibre == 0 || esperalibre > 0)) {
            try {
                wait();
            } catch (InterruptedException ex) {
                Logger.getLogger(Piscina.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        if (libresclub > 0) {
            libresclub--;
            System.out.println("      ----> CLUB " + Thread.currentThread().getName() + " EN PISCINA 1");
            return 1;
        } else {
            librelibre--;
            System.out.println("      ----> CLUB " + Thread.currentThread().getName() + " EN PISCINA 2");
            return 2;
        }
    }

    public synchronized void saleclub(int cual) {
        if (cual == 1) {
            libresclub++;
        } else {
            librelibre++;
        }
        System.out.println("      <---- FIN CLUB " + Thread.currentThread().getName() + " " + cual);
        notifyAll();
    }

    public synchronized void entralibre() {
        esperalibre++;
        while (librelibre == 0) {
            try {
                wait();

            } catch (InterruptedException ex) {
                Logger.getLogger(Piscina.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        esperalibre--;
        librelibre--;
        System.out.println("                           ----> LIBRE " + Thread.currentThread().getName() + " EN PISCINA");

    }

    public synchronized void salelibre() {
        librelibre++;
        System.out.println("                           <---- FIN LIBRE " + Thread.currentThread().getName());
        notifyAll();
    }
}
