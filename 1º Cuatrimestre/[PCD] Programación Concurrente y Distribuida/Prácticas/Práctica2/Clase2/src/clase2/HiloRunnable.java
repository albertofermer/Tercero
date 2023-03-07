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
public class HiloRunnable implements Runnable {

    private int id;
    private int suma = 0;
    private Recurso r;

    public HiloRunnable(int id, Recurso r) {
        this.id = id;
        this.r = r;
    }

    public void getSuma() {
        System.out.println("Suma: " + suma);

    }

    @Override
//    public void run() {
////        Thread a = Thread.currentThread();
////        
////        for (int i = 0; i < 10; i++) {
////            
////            if(id == 1) a.setPriority(i+1); //No garantiza que el h1 acabe antes.
////            
////            System.out.println("Soy el hilo " + id + 
////                    " Con identificador: " + a.getId() + 
////                    " Con nombre: " + a.getName() + 
////                    " Con estado: " + a.getState() + 
////                    " Con prioridad: " + a.getPriority());
////            
////            a.yield(); //El hilo que esté usando el procesador, lo abandona. No lo vamos a utilizar.
////            
////            try {sleep(100);} catch (InterruptedException ex) {}
//        for (int i = 0; i < 10; i++) {
//            suma++;
//        }
//    }
    public void run() {
        for (int i = 0; i < 1000000; i++) {
            r.incrementa();
        }
    }

}
