/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Puente;

/**
 *
 * @author pedro
 */
public class ElPuente {

    private int numadultos = 0, esperanino = 0;
    float peso = 0;

    public synchronized void entraAdulto() throws InterruptedException {

        int cual;
        while (esperanino > 0 || peso > 1.5) {
            wait();
        }
        peso += 1.5;
    }

    public synchronized void saleAdulto() {

        peso -= 1.5;
        notifyAll();
    }

    public synchronized void entraNino() throws InterruptedException {
        esperanino++;
        while (peso > 2) {
            wait();
        }
        peso++;
        esperanino--;
    }

    public synchronized void saleNino() {
        peso--;
        notifyAll();
    }
}
