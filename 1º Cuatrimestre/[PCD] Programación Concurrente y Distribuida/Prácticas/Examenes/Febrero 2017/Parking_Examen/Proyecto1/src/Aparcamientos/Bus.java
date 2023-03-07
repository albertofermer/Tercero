/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Aparcamientos;

import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author pedro
 */
public class Bus extends Thread {

    int id;
    Parking p;
    Random rnd = new Random();

    public Bus(int id, Parking p) {
        this.id = id;
        this.p = p;
    }

    public void run() {
        try {
            
            System.out.println("Soy el bus " + id);
            p.entraBus();
            System.out.println("------------------------->  Bus " + id + " Aparcado");
            Thread.sleep((rnd.nextInt(3) + 2) * 1000);            
            p.saleBus();
            System.out.println("<<------------------------  Bus " + id + " se marcha");
            
        } catch (InterruptedException ex) {
            Logger.getLogger(Bus.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
