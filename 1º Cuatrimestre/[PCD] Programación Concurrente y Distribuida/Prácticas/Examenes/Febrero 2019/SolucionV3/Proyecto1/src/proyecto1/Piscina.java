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

    private int libres = 5, esperaadulto = 0, adultos=0;

    public synchronized void entraadulto() throws InterruptedException {
        esperaadulto++;
        while (libres == 0) {
            wait();
        }
        libres--;
        esperaadulto--;
        adultos++;
        System.out.println("----> ADULTO " + Thread.currentThread().getName() + " Libres = " + libres);
    }

    public synchronized void saleadulto() {

        libres++;
        adultos--;
        System.out.println("<---- FIN ADULTO " + Thread.currentThread().getName() + " Libres = " + libres);
        notifyAll();
    }

    public synchronized boolean entraninyo() throws InterruptedException {
        if (adultos != 0) {
            while (libres < 2 || esperaadulto > 0) {
                wait();
            }
            libres -= 2;
            System.out.println("                           ----> NINYO " + Thread.currentThread().getName() + ". Libres = " + libres);
            return true;
        } else {
            return false;
        }
    }

    public synchronized void saleninyo() {
        libres += 2;
        System.out.println("                           <---- FIN NINYO " + Thread.currentThread().getName() + ". Libres = " + libres);
        notifyAll();
    }
}
