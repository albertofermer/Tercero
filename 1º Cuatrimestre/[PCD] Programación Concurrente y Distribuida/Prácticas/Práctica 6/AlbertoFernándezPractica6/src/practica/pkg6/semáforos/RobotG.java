package practica.pkg6.semáforos;

import static java.lang.Thread.sleep;
import java.util.concurrent.Semaphore;

/**
 * Representa al robot grapador mediante un hilo. 
 * El hilo se creará implementando el interface Runnable. 
 * El tiempo empleado por el robot en grapar y depositar el folleto en la cinta será de 2 segundos. 
 * Este robot será interrumpido por el generador cuando los otros acaben.
 * 
 * @author Alberto Fernández
 */
public class RobotG implements Runnable {

    private Semaphore[] semaforosA;
    private Semaphore[] semaforosG;
    private CanvasP6 lienzo;

    RobotG(Semaphore[] semaforosRobotA, Semaphore semaforosG[], CanvasP6 lienzo) {
        this.lienzo = lienzo;
        this.semaforosA = semaforosRobotA;
        this.semaforosG = semaforosG;
    }

    @Override
    public void run() {
        while (true) {
            try {
                for (int i = 0; i < 4; i++) {

                    semaforosG[i].acquire();
                }

                grapador();

                for (int i = 0; i < 4; i++) {
                    semaforosA[i].release();
                }

            } catch (InterruptedException ex) {
                System.out.println("- Han acabado los RobotsA -");
            }
        }
    }

    public void grapador() throws InterruptedException {
        System.out.println("----------------------------------");

        System.out.println("El robot G grapa las 4 hojas...");

        System.out.println("----------------------------------");
        lienzo.borraEnCinta();
        sleep(2000);
        lienzo.borraEnMesa();
        lienzo.pintaEnCinta();

    }

}
