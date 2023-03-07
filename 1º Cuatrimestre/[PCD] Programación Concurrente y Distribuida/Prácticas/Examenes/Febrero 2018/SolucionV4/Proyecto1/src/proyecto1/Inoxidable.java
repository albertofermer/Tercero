/*
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
public class Inoxidable extends Thread {
    
    Vibradora t;
    
    public Inoxidable(Vibradora tl){
        t=tl;
    }
    
    @Override
    public void run(){
        Random rnd = new Random();
        rnd.setSeed(System.currentTimeMillis());
        try {
            
            System.out.println("Soy INOX "+getName());
            t.entraInox();
            System.out.println("                           ----> INOX "+getName()+" EN PULIDORA");
            
            Thread.sleep((rnd.nextInt(3)+1)*3000);
            System.out.println("                           <---- FIN INOX "+getName());
            t.saleInox();
            
        } catch (InterruptedException ex) {
            Logger.getLogger(Hierro.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
