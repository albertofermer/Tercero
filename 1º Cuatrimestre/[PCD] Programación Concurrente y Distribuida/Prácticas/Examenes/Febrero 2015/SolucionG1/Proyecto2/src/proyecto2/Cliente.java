/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package proyecto2;

import java.util.Random;
import java.util.concurrent.Semaphore;

/**
 *
 * @author pedro
 */
public class Cliente extends Thread{
    private int id;
    private Semaphore fruta;
    
    public Cliente(int i, Semaphore fruta){
        id=i;
        this.fruta=fruta;
    }
    @Override
    public void run(){
  
            Random rnd = new Random();
            rnd.setSeed(System.currentTimeMillis()+id);
            int cantidad=rnd.nextInt(4)+2;
            System.out.println("Soy el cliente "+id+" y quiero "+ cantidad);
            for (int i = 0; i < cantidad; i++) {
            try {
                fruta.acquire();
                System.out.println("Soy el cliente "+id+" y consigo un kilo ");
                
            } catch (InterruptedException ex) {
                System.out.println(id+" No he podido conseguir la compra");
                System.exit(0);
            }
        }
            System.out.println("Soy el cliente "+id+" marchandome con la fruta");
            
       
    }
    
}
