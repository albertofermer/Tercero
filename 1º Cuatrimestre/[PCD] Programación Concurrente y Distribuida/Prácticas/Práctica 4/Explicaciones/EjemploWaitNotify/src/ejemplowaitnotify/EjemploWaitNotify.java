/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejemplowaitnotify;

/**
 *
 * @author usuario
 */
public class EjemploWaitNotify {

    /**
     * @param args the command line arguments
     * @throws java.lang.InterruptedException
     */
    public static void main(String[] args) throws InterruptedException {

        Thread[] h = new Thread[5];
        Ordenador ord = new Ordenador();

        for (int i = 0; i < 5; i++) {
            h[i] = new Hilo(i, ord);
        }
        for (int i = 0; i < 5; i++) {
            h[i].start();
        }

        h[0].join();
        h[1].join();
        h[3].join();
        h[4].join();

        System.out.println("Solo fata el 2");

        synchronized (ord) {
            ord.notify();
            //Si hacemos dos notify no se despierta dos veces porque está esperando a que
            // acabe la sincronización.
        }
            Thread.sleep(1000);
        synchronized (ord) {
            ord.notify();
            //Si ponemos dos bloques sincronizados, le da tiempo a volver al wait().
        }

    }

}
