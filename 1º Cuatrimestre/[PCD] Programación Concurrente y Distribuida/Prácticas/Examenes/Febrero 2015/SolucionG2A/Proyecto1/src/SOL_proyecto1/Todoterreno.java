/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package SOL_proyecto1;

import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author pedro
 */
public class Todoterreno extends Thread{
    private int id;
    
    public Todoterreno(int i){
        id=i;
    }
    
    @Override
    public void run(){
        try {  
            Random rnd = new Random();
            rnd.setSeed(System.currentTimeMillis()+id);
            System.out.println("Soy el Todoterreno "+id+" sucio ");
            Thread.sleep((rnd.nextInt(5)+2)*1000);
            System.out.println("Soy el Todoterreno "+id+" LIMPIO");
            
        } catch (InterruptedException ex) {
            Logger.getLogger(Todoterreno.class.getName()).log(Level.SEVERE, null, ex);
        }                   
    }
    
}
