/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package proyecto4;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author pedro
 */
public class Lavadero {

    private boolean cochelibre = true, ttlibre = true;

    public synchronized int Llegacoche() {

        while (!cochelibre && !ttlibre) {
            try {
                wait();
            } catch (InterruptedException ex) {
                Logger.getLogger(Lavadero.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        if (cochelibre) {
            cochelibre = false;
            return 1;
        } else {
            ttlibre = false;
            return 2;
        }
    }

    public synchronized void LlegaTT() {

        while (!ttlibre) {
            try {
                wait();
            } catch (InterruptedException ex) {
                Logger.getLogger(Lavadero.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        ttlibre = false;
    }

    public synchronized void SaleCoche(int cual) {
        if (cual == 1) {
            cochelibre = true;
        } else {
            ttlibre = true;
        }
        notifyAll();
    }

    public synchronized void SaleTT() {
        ttlibre = true;
        notifyAll();
    }
}
