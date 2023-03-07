/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Aparcamientos;

import java.applet.Applet;
import java.util.Random;

/**
 *
 * @author pedro
 */
public class ParkingApplet extends Applet {

    Generador gen;
    CanvasParking cv;

    /**
     * Initialization method that will be called after the applet is loaded into
     * the browser.
     */
    public void init() {
        // TODO start asynchronous download of heavy resources
        this.setSize(800, 500);

        cv = new CanvasParking(800, 500);
        this.add(cv);
        gen = new Generador(cv);

    }

    public void start() {
        gen.start();
    }

    // TODO overwrite start(), stop() and destroy() methods
}

class Generador extends Thread {

    CanvasParking cv;
    /**
     * @param args the command line arguments
     */
    
    public Generador(CanvasParking cv){
        this.cv = cv;
    }
    @Override
    public void run() {
        // TODO code application logic here

        Thread vehiculo;
        int tipo;
        Random rnd = new Random();
        Parking p = new Parking(3,2,1);
        rnd.setSeed(System.currentTimeMillis());
        for (int i = 0; i < 2000; i++) {
            tipo = rnd.nextInt(10);
            if (tipo >= 1) {
                vehiculo = new Thread(new Coche(i, p, cv));;
            } else {
                vehiculo = new Bus(i, p, cv);
            }
            vehiculo.start();
            try {
                Thread.sleep((rnd.nextInt(1) + 1)*1000);
            } catch (InterruptedException ex) {
                System.out.println(ex.toString());
            }
        }

    }

}
