/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Aparcamientos;

/**
 *
 * @author pedro
 */
public class Parking {

    private int numcoches = 0, numbus=0, busesperando = 0;

    public synchronized int entraCoche() throws InterruptedException {

        int cual;
        while (numcoches >= 5 && (busesperando > 0 || numbus>=2)) {
            wait();
        }
        if (numcoches < 5) {
            numcoches++;
            cual = 1;
        } else {
            numbus++;
            cual = 2;
        }
        return cual;
    }

    public synchronized void saleCoche(int cual) {

        if (cual == 1) {
            numcoches--;
        } else {
            numbus--;
        }
        notifyAll();
    }

    public synchronized void entraBus() throws InterruptedException {
        busesperando++;
        while (numbus >= 2) {
            wait();
        }
        busesperando--;
        numbus++;
    }

    public synchronized void saleBus() {
        numbus--;
        notifyAll();
    }
}
