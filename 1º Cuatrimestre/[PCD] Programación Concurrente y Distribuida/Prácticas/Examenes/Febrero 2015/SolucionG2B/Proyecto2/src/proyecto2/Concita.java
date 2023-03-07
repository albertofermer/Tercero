/*
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
public class Concita extends Thread{
    private int id;
    private Sala s;
    
    public Concita(int i, Sala _s){
        id=i;
        s=_s;
    }
    
    @Override
    public void run(){
        try {  
            Random rnd = new Random();
            rnd.setSeed(System.currentTimeMillis()+id);
            System.out.println("Llego con cita "+id);
            s.ConcitaIN();
            System.out.println("EN LA SALA "+id);
            Thread.sleep((rnd.nextInt(5)+2)*1000);
            System.out.println("Salgo con cita "+id);
            s.ConcitaOUT();
            
        } catch (InterruptedException ex) {
            Logger.getLogger(Concita.class.getName()).log(Level.SEVERE, null, ex);
        }                   
    }
    
}
