/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Puente;

import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author pedro
 */
public class Adulto extends Thread {

    int id;
    ElPuente p;
    Random rnd = new Random();

    public Adulto(int id, ElPuente p) {
        this.id = id;
        this.p = p;
    }

    public void run() {
        try {
            
            System.out.println("Soy Adulto " + id);
            p.entraAdulto();
            System.out.println("------------------------->  Adulto " + id + " Cruza");
            Thread.sleep((rnd.nextInt(3) + 2) * 1000);            
            p.saleAdulto();
            System.out.println("<<------------------------  Adulto " + id + " se marcha");
            
        } catch (InterruptedException ex) {
            Logger.getLogger(Adulto.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
