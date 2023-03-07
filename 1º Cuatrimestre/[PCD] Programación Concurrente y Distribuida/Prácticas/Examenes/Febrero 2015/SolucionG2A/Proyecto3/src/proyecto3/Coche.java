/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package proyecto3;

import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author pedro
 */
public class Coche implements Runnable{
    private int id;
    private Lavadero lv;
    
    public Coche(int i, Lavadero _lv){
        id=i;
        lv=_lv;
    }
    @Override
    public void run(){
        try {
            Random rnd = new Random();
            rnd.setSeed(System.currentTimeMillis()+id);
            System.out.println(" .......................... Soy el Coche "+id+" sucio");
            int cual=lv.Llegacoche();
            System.out.println(" .......................... Soy el Coche "+id+" LAVANDOSE en "+cual);            
            Thread.sleep((rnd.nextInt(5)+2)*1000);
            System.out.println("........................... Soy el Coche "+id+" LIMPIO");
            lv.SaleCoche(cual);
        } catch (InterruptedException ex) {
            Logger.getLogger(Coche.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
