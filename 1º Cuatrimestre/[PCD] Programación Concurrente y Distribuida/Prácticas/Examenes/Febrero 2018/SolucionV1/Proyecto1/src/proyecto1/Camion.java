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
public class Camion extends Thread {
    
    Taller t;
    
    public Camion(Taller tl){
        t=tl;
    }
    
    @Override
    public void run(){
        Random rnd = new Random();
        rnd.setSeed(System.currentTimeMillis());
        try {
            
            System.out.println("Soy el CAMION "+getName());
            t.entracamion();
            System.out.println("                           ----> CAMION "+getName()+" EN TALLER");
            
            Thread.sleep((rnd.nextInt(5)+3)*3000);
            System.out.println("                           <---- FIN CAMION "+getName());
            t.salecamion();
            
        } catch (InterruptedException ex) {
            Logger.getLogger(Coche.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
