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
public class Coche implements Runnable{
      int id;
    Parking p;
    Random rnd = new Random();

    public Coche(int id, Parking p) {
        this.id = id;
        this.p = p;
    }

      @Override
    public void run() {
        try {
            
            System.out.println("Soy el coche " + id);
            int cual=p.entraCoche();
            System.out.println("Coche --->" + id + " Aparcado");
            Thread.sleep((rnd.nextInt(3) + 2) * 1000);            
            p.saleCoche(cual);
            System.out.println("<--- Coche " + id + " se marcha");
            
        } catch (InterruptedException ex) {
            Logger.getLogger(Bus.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}

