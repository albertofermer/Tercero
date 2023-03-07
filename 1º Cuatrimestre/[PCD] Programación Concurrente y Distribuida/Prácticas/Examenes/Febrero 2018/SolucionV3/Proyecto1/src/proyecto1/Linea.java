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
public class Linea {

    private boolean librecoser=true;
    private int librecorte=2, esperapantalon=0;

    public synchronized void EntraCorte() {
        while ( librecorte ==0) {
            try {
                wait();
            } catch (InterruptedException ex) {
                Logger.getLogger(Linea.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        librecorte--;
    }
    
    public synchronized void CoserCamisa() {
        while (!librecoser || esperapantalon>0) {
            try {
                wait();
            } catch (InterruptedException ex) {
                Logger.getLogger(Linea.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        librecoser=false;
        librecorte++;
        notifyAll();
    }
    public synchronized void CoserPantalon() {
        esperapantalon++;
        while (!librecoser) {
            try {
                wait();
            } catch (InterruptedException ex) {
                Logger.getLogger(Linea.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        esperapantalon--;
        librecoser=false;
        librecorte++;
        notifyAll();
    }

    public synchronized void SaleCoser() {
        librecoser=true;
        notifyAll();
    }
}
