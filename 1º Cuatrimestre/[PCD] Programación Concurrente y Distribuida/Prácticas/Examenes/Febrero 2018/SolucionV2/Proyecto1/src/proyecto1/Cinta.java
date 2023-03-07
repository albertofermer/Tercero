/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proyecto1;

import java.util.Random;

/**
 *
 * @author pedro
 */
public class Cinta extends Thread {

    private Monton depositos;

    public Cinta(Monton t) {
        depositos = t;
    }

    @Override
    public void run() {
        Random rnd = new Random();
        rnd.setSeed(System.currentTimeMillis());

        int ca = 0;
        try {
            while (true) {
                Thread.sleep((rnd.nextInt(3) + 1) * 1000);
                ca = rnd.nextInt(4) + 2;
                System.out.println("Soy la cinta dejando " + ca);

                depositos.Rellena(ca);

            }
        } catch (InterruptedException ex) {
        }

    }
}
