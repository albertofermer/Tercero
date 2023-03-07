/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clase2;

import static java.lang.Thread.sleep;

/**
 *
 * @author usuario
 */
public class HiloThread extends Thread{

    private int id;
    Recurso r;
    
    public HiloThread(int id, Recurso r) {
        this.id = id;
        this.r = r;
    }

    @Override
//    public void run() {
//
//        for (int i = 0; i < 10; i++) {
//            
//            if(id == 1) setPriority(i+1); //No garantiza que el h1 acabe antes.
//            
//            System.out.println("Soy el hilo " + id + 
//                    " Con identificador: " + getId() + 
//                    " Con nombre: " + getName() + 
//                    " Con estado: " + getState() + 
//                    " Con prioridad: " + getPriority());
//            
//            yield(); //El hilo que esté usando el procesador, lo abandona. No lo vamos a utilizar.
//            
//            try {sleep(100);} catch (InterruptedException ex) {}
//        }
//
//    }
    public void run() {
        
        for (int i = 0; i < 1000000; i++) {
            r.incrementa();
        }
        
    }
    
    
    
}
