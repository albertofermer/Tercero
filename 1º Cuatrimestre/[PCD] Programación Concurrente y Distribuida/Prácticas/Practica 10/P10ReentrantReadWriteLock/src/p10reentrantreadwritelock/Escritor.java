/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package p10reentrantreadwritelock;

import static java.lang.Thread.sleep;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author usuario
 */
public class Escritor implements Runnable{

    private ReentrantReadWriteLock l = null;
    
    public Escritor(ReentrantReadWriteLock l){
        this.l = l;
    }
    
    @Override
    public void run() {
        try {
            l.writeLock().lock();
            try {
                escribiendo();
                sleep(3000);
            } finally {
                l.writeLock().unlock();
            }
        } catch (InterruptedException ex) {
            Logger.getLogger(Escritor.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }

    private void escribiendo() {
        System.out.println("El escritor está escribiendo...");
    }
    
}
