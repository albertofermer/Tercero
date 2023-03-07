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
public class Coche implements Runnable {

    int id;
    Parking p;
    Random rnd = new Random();
    CanvasParking cv;

    public Coche(int id, Parking p, CanvasParking cv) {
        this.id = id;
        this.p = p;
        this.cv = cv;
    }

    @Override
    public void run() {
        try {

            System.out.println("Soy el coche " + id);
            cv.inserta(1, id);
            Thread.sleep(200);
            int cual = p.entraCoche();
            cv.quita(1, id);
            cv.aparcacoche(id, cual);
            System.out.println("Coche --->" + id + " Aparcado");
            Thread.sleep((rnd.nextInt(5) + 3) * 1000);
            cv.salecoche(id, cual);
            Thread.sleep(200);
            p.saleCoche(cual);
            System.out.println("<--- Coche " + id + " se marcha");

        } catch (InterruptedException ex) {
            Logger.getLogger(Bus.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
