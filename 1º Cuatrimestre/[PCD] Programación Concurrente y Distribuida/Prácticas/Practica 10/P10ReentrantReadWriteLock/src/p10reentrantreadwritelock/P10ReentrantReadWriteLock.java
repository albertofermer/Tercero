package p10reentrantreadwritelock;

import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 *
 * @author usuario
 */
public class P10ReentrantReadWriteLock {

    private static int NUMLECTORES = 15;
    private static int NUMESCRITORES = 5;

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws InterruptedException {

        ReentrantReadWriteLock lecturaEscritura = new ReentrantReadWriteLock(false);
        Thread[] escritores = new Thread[NUMESCRITORES];
        Thread[] lectores = new Thread[NUMLECTORES];

        for (int i = 0; i < NUMLECTORES; i++) {
            lectores[i] = new Lector(lecturaEscritura);
        }
        for (int i = 0; i < NUMESCRITORES; i++) {
            escritores[i] = new Thread(new Escritor(lecturaEscritura));
        }

        for (int i = 0; i < NUMLECTORES; i++) {
            lectores[i].start();
        }
        for (int i = 0; i < NUMESCRITORES; i++) {
            escritores[i].start();
        }

        for (int i = 0; i < NUMLECTORES; i++) {
            lectores[i].join();
        }

        for (int i = 0; i < NUMESCRITORES; i++) {
            escritores[i].join();
        }

    }

}
