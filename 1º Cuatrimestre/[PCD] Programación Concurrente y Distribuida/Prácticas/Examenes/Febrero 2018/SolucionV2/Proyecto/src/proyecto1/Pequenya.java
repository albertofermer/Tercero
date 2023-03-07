/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proyecto1;

import java.util.Random;
import java.util.concurrent.Semaphore;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author pedro
 */
public class Pequenya extends Thread {

    private int id;
    private Semaphore depositos;

    public Pequenya(int num, Semaphore t) {
        id = num;
        depositos = t;
    }

    @Override
    public void run() {
        Random rnd = new Random();
        rnd.setSeed(System.currentTimeMillis() + id);
        for (int i = 0; i < 10; i++) {
            try {
                System.out.println("Pequenya "+id+" esperando ...");
                depositos.acquire();
                System.out.println("        Soy la pequenya " + id + " iteracion " + i);
                Thread.sleep((rnd.nextInt(3) + 1) * 1000);
            } catch (InterruptedException ex) {
            }
        }
    }

}
