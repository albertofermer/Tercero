/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pila;

import static java.lang.Thread.sleep;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Alberto Fernández
 */
public class consumidor implements Runnable{

    private PilaLenta p;
    private Random r;

    public consumidor(PilaLenta p) {
        r = new Random();
        r.setSeed(System.nanoTime());
        this.p = p;
    }

    @Override
    public void run() {
        int elemento;
        for (int i = 0; i < 20; i++) {
            
            try {
                //sleep(2000);
                elemento = (int) p.Desapila();
                //System.out.println(" Iteracion consumidor: " + i + " " + Thread.currentThread().getId() + ": Se ha desapilado el elemento: " + elemento);
                //sleep(1000);
            } catch (Exception ex) {
                //System.out.println(Thread.currentThread().getId() + ex.getMessage());
            }

        }

    }
}
