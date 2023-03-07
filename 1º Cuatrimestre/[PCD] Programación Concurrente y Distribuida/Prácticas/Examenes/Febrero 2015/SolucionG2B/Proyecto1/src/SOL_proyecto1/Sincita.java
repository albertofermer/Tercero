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
public class Sincita implements Runnable{
    private int id;
    
    public Sincita(int i){
        id=i;
    }
    @Override
    public void run(){
        try {
            Random rnd = new Random();
            rnd.setSeed(System.currentTimeMillis()+id);
            System.out.println(" .......................... Llego sin cita "+id);
            Thread.sleep((rnd.nextInt(5)+2)*1000);
            System.out.println("........................... Salgo sin cita "+id);
        } catch (InterruptedException ex) {
            Logger.getLogger(Sincita.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
