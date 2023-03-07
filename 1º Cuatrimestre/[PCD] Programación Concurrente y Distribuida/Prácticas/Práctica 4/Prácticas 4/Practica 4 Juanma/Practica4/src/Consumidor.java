
import static java.lang.Thread.sleep;
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
public class Consumidor implements Runnable{
    
    private final Pilalenta lapila;
    
    public Consumidor(Pilalenta lapila){
        this.lapila = lapila;
    }
    
    public void Consumir() throws InterruptedException {
        try {
            for (int i = 0; i < 15; i++) {
                sleep((int)Math.floor(Math.random()*2000));
                System.out.println("Hilo " + Thread.currentThread().getId() + " extraigo " + lapila.Desapila());
                }
            } catch (InterruptedException ex) {
                System.out.println("Hilo " + Thread.currentThread().getId() + " consumidor interrumpido");
            } catch (Exception ex){
                System.out.println("Hilo " + Thread.currentThread().getId() + " consumidor aborta al no poder extraer");
            }
    }

    @Override
    public void run() {
        try {
            Consumir();
        } catch (InterruptedException ex) {
            System.out.println(ex.getMessage());
        }
    }
}
