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
        Thread[] vehiculo = new Thread[10];
        Taller repara = new Taller();
        Random rnd = new Random();
        rnd.setSeed(System.currentTimeMillis());
        try {

            for (int i = 0; i < 10; i++) {
                if (rnd.nextInt(10) >2) {
                    vehiculo[i] = new Thread(new Coche(repara));
                    vehiculo[i].start();
                } else {                    
                    vehiculo[i] =  new Camion(repara);
                    vehiculo[i].start();
                }
                Thread.sleep((rnd.nextInt(3) + 1) * 1000);                
            }
            for (int i = 0; i < 10; i++) {
                vehiculo[i].join();
            }            
        } catch (InterruptedException ex) {
            Logger.getLogger(Generador.class.getName()).log(Level.SEVERE, null, ex);


        }
    }
}
