/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proyecto2;

/**
 *
 * @author usuario
 */
public class Sala {

    private boolean libre = true;
    int esperandoconcita = 0;

    public synchronized void ConcitaIN() throws InterruptedException {
        esperandoconcita++;
        while (!libre) {
            wait();
        }
        esperandoconcita--;

        libre = false;
    }

    public synchronized void ConcitaOUT() {
        libre = true;
        notifyAll();
    }

    public synchronized void SincitaIN() throws InterruptedException {
        while (!libre || esperandoconcita > 0) {
            wait();
        }
        libre = false;
    }

    public synchronized void SincitaOUT() {
        libre = true;
        notifyAll();
    }
}
