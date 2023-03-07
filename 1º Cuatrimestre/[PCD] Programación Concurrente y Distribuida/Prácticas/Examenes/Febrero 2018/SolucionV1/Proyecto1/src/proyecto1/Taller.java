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
public class Taller {

    private int operarios = 4, esperacamion = 0;

    public synchronized void entracoche() {
        while (operarios == 0 || esperacamion > 0) {
            try {
                wait();
            } catch (InterruptedException ex) {
                Logger.getLogger(Taller.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        operarios--;
    }

    public synchronized void salecoche() {
        operarios++;
        notifyAll();
    }

    public synchronized void entracamion() {
        esperacamion++;
        while (operarios < 2) {
            try {
                wait();
            } catch (InterruptedException ex) {
                Logger.getLogger(Taller.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        esperacamion--;
        operarios = operarios - 2;
    }

    public synchronized void salecamion() {
        operarios = operarios + 2;
        notifyAll();
    }
}
