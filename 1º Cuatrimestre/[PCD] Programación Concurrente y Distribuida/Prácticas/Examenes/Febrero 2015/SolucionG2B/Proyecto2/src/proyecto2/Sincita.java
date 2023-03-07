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
public class Sincita implements Runnable{
    private int id;
    private Sala s;
    
    public Sincita(int i, Sala _s){
        id=i;
        s=_s;
    }
    @Override
    public void run(){
        try {
            Random rnd = new Random();
            rnd.setSeed(System.currentTimeMillis()+id);
            System.out.println(" .......................... Llego sin cita "+id);
            s.SincitaIN();
            System.out.println(" .......................... SE ATIENDE A "+id);
            Thread.sleep((rnd.nextInt(5)+2)*1000);
            System.out.println("........................... Salgo sin cita "+id);
            s.SincitaOUT();
        } catch (InterruptedException ex) {
            Logger.getLogger(Sincita.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
