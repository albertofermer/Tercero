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
public class Vibradora {

    private int dentroFE = 0, dentroIno = 0, esperaFe = 0, esperaInox = 0;

    public synchronized void entraInox() {
        while (dentroIno > 2 || dentroFE > 0 ) {
            try {
                wait();
            } catch (InterruptedException ex) {
                Logger.getLogger(Vibradora.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        dentroIno++;
    }

    public synchronized void saleInox() {
        dentroIno--;
        notifyAll();
    }

    public synchronized void entraHierro() {
        while (dentroIno > 0 || dentroFE > 1) {
            try {
                wait();
            } catch (InterruptedException ex) {
                Logger.getLogger(Vibradora.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        dentroFE++;
    }

    public synchronized void saleHierro() {
        dentroFE--;
        notifyAll();
    }
}
