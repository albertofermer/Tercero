/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proyecto1;

/**
 *
 * @author pedro
 */
public class Mina {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws InterruptedException {
        // TODO code application logic here
        
        Monton t = new Monton();
        Thread[] cargadora = new Thread[3];
        for (int i = 0; i < 2; i++) {
            cargadora[i]=new Pequenya(i,t);
        }
        cargadora[2]=new Thread(new Grande(t));            
        Thread lacinta = new Cinta(t);
        lacinta.start();
        for (int i = 0; i < 3; i++) {
            cargadora[i].start();            
        }
        for (int i = 0; i < 3; i++) {
            cargadora[i].join();            
        }
        lacinta.interrupt();
        
    }
    
}
