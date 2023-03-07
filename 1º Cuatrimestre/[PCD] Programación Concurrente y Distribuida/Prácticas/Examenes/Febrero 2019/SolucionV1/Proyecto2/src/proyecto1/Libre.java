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
public class Libre extends Thread {
    
    Piscina t;
    
    public Libre(Piscina tl){
        t=tl;
    }
    
    @Override
    public void run(){
        Random rnd = new Random();
        rnd.setSeed(System.currentTimeMillis());
        try {
            
            System.out.println("                            Soy POR lIBRE "+getName());
            t.entralibre();
            
            Thread.sleep((rnd.nextInt(5)+3)*1000);
            t.salelibre();
            
        } catch (InterruptedException ex) {
            Logger.getLogger(Club.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
