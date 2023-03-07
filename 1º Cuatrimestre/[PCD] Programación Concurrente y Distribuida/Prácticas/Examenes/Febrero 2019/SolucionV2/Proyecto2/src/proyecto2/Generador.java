/*PEDRO
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package proyecto2;

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
        int personastotales=20;
        Thread[] persona = new Thread[personastotales];
        Piscina agua = new Piscina();
        Random rnd = new Random();
        rnd.setSeed(System.currentTimeMillis());
        try {
           

            for (int i = 0; i < personastotales; i++) {
                    persona[i] = new Thread(new Nadador(agua));
                    persona[i].start();
               
                Thread.sleep((rnd.nextInt(1) + 1) * 1000);                
            }
            for (int i = 0; i < personastotales; i++) {
                persona[i].join();
            }            
        } catch (InterruptedException ex) {
            Logger.getLogger(Generador.class.getName()).log(Level.SEVERE, null, ex);


        }
    }
}
