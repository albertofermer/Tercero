/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proyecto1;

import java.util.Random;
import java.util.concurrent.Semaphore;

/**
 *
 * @author pedro
 */
public class Grande implements Runnable{
 
    private int id;
    private Semaphore depositos;

    public Grande(Semaphore t) {      
        depositos = t;
    }

    @Override
    public void run() {
        Random rnd = new Random();
        rnd.setSeed(System.currentTimeMillis() + id);
        for (int i = 0; i < 7; i++) {
            try {
                System.out.println("Grande esperando ...");
                depositos.acquire(2);
                System.out.println("        Cargadora grande retira 2 en iteracion  "+  i);
                Thread.sleep((rnd.nextInt(3) + 1) * 1000);
            } catch (InterruptedException ex) {
            }
        }
    }

}
