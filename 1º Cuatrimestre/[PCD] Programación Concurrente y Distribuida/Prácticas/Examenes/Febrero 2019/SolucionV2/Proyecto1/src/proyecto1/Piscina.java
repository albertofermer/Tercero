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

    private int aletaslibres = 5, manoplaslibres = 5, piscinalibre = 5;

    public synchronized void entrapiscina() throws InterruptedException {

        while (piscinalibre == 0) {
            wait();
        }
        System.out.println("----> ENTRA PISCINA " + Thread.currentThread().getName());
        piscinalibre--;

    }

    public synchronized void salepiscina() {
        piscinalibre++;
        System.out.println("<---- FIN PISCINA " + Thread.currentThread().getName());
        notifyAll();
    }

    public synchronized void cogematerial() throws InterruptedException {
        
            while (aletaslibres < 2) {
                wait();
            }
            aletaslibres -= 2;
            System.out.println("                           ----> ALETAS " + Thread.currentThread().getName() + " EN PISCINA");
            while (manoplaslibres < 2) {
                wait();
            }
            manoplaslibres -= 2;
            System.out.println("                           ----> MANOPLAS " + Thread.currentThread().getName() + " EN PISCINA");
       
    }

    public synchronized void sueltamaterial() {
        aletaslibres += 2;
        System.out.println("                           <---- FIN ALETAS " + Thread.currentThread().getName());
        manoplaslibres += 2;
        System.out.println("                           <---- FIN MANOPLAS " + Thread.currentThread().getName());
        notifyAll();
    }

}
