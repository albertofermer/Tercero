/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pila;

import static java.lang.Math.abs;
import java.util.Random;

/**
 *
 * @author Alberto Fernández
 */
public class productor extends Thread {

    private Random r;
    private PilaLenta p;

    public productor(PilaLenta p) {
        this.r = new Random();
        r.setSeed(System.nanoTime());

        this.p = p;
    }

    @Override
    public void run() {

        for (int i = 0; i < 20; i++) {
            int elemento;
            elemento = abs(r.nextInt() % 100);

            try {
                //sleep(1);
                p.Apila(elemento);           
                //System.out.println("Iteracion productor " + i + " " + getId() + ": Se ha apilado el elemento: " + elemento);
                //sleep(1);
            } catch (Exception ex) {
                    //System.out.println(Thread.currentThread().getId() + ex.getMessage());
                    
            }

        }

    }

}
