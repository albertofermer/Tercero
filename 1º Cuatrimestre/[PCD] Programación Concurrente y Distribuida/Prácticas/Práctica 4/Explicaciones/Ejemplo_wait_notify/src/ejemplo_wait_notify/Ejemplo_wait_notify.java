/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ejemplo_wait_notify;

import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author pedro
 */
public class Ejemplo_wait_notify {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        try {
            // TODO code application logic here

            int numhilos = 5;
            ordenador turno = new ordenador(1,numhilos);

            Thread[] hilos = new Thread[numhilos];

            for (int i = 0; i < numhilos; i++) {
                hilos[i] = new hilo(i, turno, numhilos);
            }

            for (int i = 0; i < numhilos; i++) {
                hilos[i].start();
            }
            for (int i = 0; i < numhilos; i++) {
                hilos[i].join();
            }

        } catch (InterruptedException ex) {
            Logger.getLogger(Ejemplo_wait_notify.class.getName()).log(Level.SEVERE, null, ex);
        }


    }
}

class hilo extends Thread {

    private int id;
    private Random rnd = new Random();
    private ordenador turno;

    public hilo(int _id, ordenador _turno, int _total) {
        id = _id;
        turno = _turno;
        rnd.setSeed(System.currentTimeMillis() + id);

    }

    @Override
    public void run() {
        for (int i = 0; i < 5; i++) {
            try {
                Thread.sleep(rnd.nextInt(5) * 1000);
            } catch (InterruptedException ex) {
                return;
            }
            turno.cogeturno(id);
            System.out.println("Soy el hilo " + id);
            turno.termina(id);
        }
    }
}

class ordenador {

    private int elturno, participantes;

    public ordenador(int _elturno, int _participantes) {
        elturno = _elturno;
        participantes=_participantes;
    }

    public synchronized void cogeturno(int id) {
        while (elturno != id) {
            try {
                wait();
                System.out.println("El hilo "+id+" chequea condicion");
            } catch (InterruptedException ex) {
                return;
            }
        }
    }

    public synchronized void termina(int id) {
        elturno = (id + 1) % participantes;
        //notify();
        notifyAll();
    }
}