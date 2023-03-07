/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package proyecto1;

import java.util.Random;

/**
 *
 * @author pedro
 */
public class Cliente extends Thread{
    private int id;
    
    public Cliente(int i){
        id=i;
    }
    @Override
    public void run(){
  
            Random rnd = new Random();
            rnd.setSeed(System.currentTimeMillis()+id);
            int cantidad=rnd.nextInt(4)+2;
            System.out.println("Soy el cliente "+id+" y quiero "+ cantidad);
            System.out.println("Soy el cliente "+id+" marchandome con la fruta");
            
       
    }
    
}
