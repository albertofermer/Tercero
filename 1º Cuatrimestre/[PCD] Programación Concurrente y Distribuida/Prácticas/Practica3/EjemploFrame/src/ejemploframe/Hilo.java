/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejemploframe;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author usuario
 */
public class Hilo extends Thread {
    
    private Recurso r;
    private int contador;
    
    
    public Hilo(Recurso r,int contador)
    {
        this.r = r;
        this.contador = contador;
        
    
    }
    
    @Override
    public void run()
    {
        for (int i = 0; i < 1000; i++) {
           r.incrementa(contador);
           
            try {
                sleep(100);
            } catch (InterruptedException ex) {
                Logger.getLogger(Hilo.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
}
