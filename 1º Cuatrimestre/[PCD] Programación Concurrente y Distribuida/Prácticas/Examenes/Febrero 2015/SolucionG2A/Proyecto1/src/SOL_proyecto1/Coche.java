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
public class Coche implements Runnable{
    private int id;
    
    public Coche(int i){
        id=i;
    }
    @Override
    public void run(){
        try {
            Random rnd = new Random();
            rnd.setSeed(System.currentTimeMillis()+id);
            System.out.println(" .......................... Soy el Coche "+id+" sucio");
            Thread.sleep((rnd.nextInt(5)+2)*1000);
            System.out.println("........................... Soy el Coche "+id+" LIMPIO");
        } catch (InterruptedException ex) {
            Logger.getLogger(Coche.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
