/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Puente;

import java.util.Random;

/**
 *
 * @author pedro
 */
public class Generador {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws InterruptedException {
        // TODO code application logic here

        Thread vehiculo[] = new Thread[20];
        int tipo;
        Random rnd = new Random();
        ElPuente p = new ElPuente();
        rnd.setSeed(System.currentTimeMillis());
        for (int i = 0; i < 20; i++) {
            tipo = rnd.nextInt(4);
            if (tipo > 2) {
                vehiculo[i] = new Thread(new Nino(i, p));;
            } else {
                vehiculo[i] = new Adulto(i, p);
            }
            vehiculo[i].start();
            try {
                Thread.sleep((rnd.nextInt(2) + 1) );
            } catch (InterruptedException ex) {
                System.out.println(ex.toString());
            }
        }
        for (int i = 0; i < 20; i++) {
            vehiculo[i].join();
            
        }
    }

}
