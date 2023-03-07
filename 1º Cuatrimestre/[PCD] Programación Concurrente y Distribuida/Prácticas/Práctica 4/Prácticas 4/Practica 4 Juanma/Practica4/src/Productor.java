import java.util.logging.Level;
import java.util.logging.Logger;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author theen
 */
public class Productor extends Thread{
    
    private final Pilalenta lapila;
    
    public Productor(Pilalenta lapila){
        this.lapila = lapila;
    }
    
    public void Producir() throws InterruptedException{
        int j = (int)Math.floor(Math.random()*99);
        try {
            for (int i = 0; i < 15; i++){
                sleep(1000 + (int)Math.floor(Math.random()*2000));
                lapila.Apila(j);
                System.out.println("Hilo " + Thread.currentThread().getId()+ " apilo " + j);
            }
        } catch (InterruptedException ex){
            System.out.println("Hilo " + Thread.currentThread().getId() + " productor interrumpido");
        } catch (Exception ex) {
            System.out.println("Hilo " + Thread.currentThread().getId() + " aborta al no poder introducir elemento");
        }        
    }
    
    @Override
    public void run(){
        try {
            Producir();
        } catch (InterruptedException ex) {
            System.out.println(ex.getMessage());
        }
    }
}
