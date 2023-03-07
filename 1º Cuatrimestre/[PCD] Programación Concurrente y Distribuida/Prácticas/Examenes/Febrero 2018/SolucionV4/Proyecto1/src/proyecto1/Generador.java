/*PEDRO
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
public class Generador {

    /**
     * @param args the command line arguments
     */
   public static void main(String[] args) {
        // TODO code application logic here
        Thread[] tornillo = new Thread[20];
        Vibradora pulidora = new Vibradora();
        Random rnd = new Random();
        rnd.setSeed(System.currentTimeMillis());
        try {

            for (int i = 0; i < 20; i++) {
                if (rnd.nextInt(10) >5) {
                    tornillo[i] = new Thread(new Hierro(pulidora));
                    tornillo[i].start();
                } else {                    
                    tornillo[i] =  new Inoxidable(pulidora);
                    tornillo[i].start();
                }
                Thread.sleep((rnd.nextInt(3) + 1)*100);                
            }
            for (int i = 0; i < 20; i++) {
                tornillo[i].join();
            }            
        } catch (InterruptedException ex) {
            Logger.getLogger(Generador.class.getName()).log(Level.SEVERE, null, ex);


        }
    }
}
