package practica.pkg6.semáforos;

import static java.lang.Math.abs;
import java.util.Random;
import java.util.concurrent.Semaphore;

/**
 * Representará cada uno de los robots que cogen las hojas mediante un hilo. 
 * El hilo se creará heredando de la clase Thread. El robot cogerá una hoja de su montón y lo llevará a la mesa de grapado. No podrá llevar otra hoja hasta que la mesa de grapado esté libre. 
 * El tiempo que cada robot tarda en recoger una nueva hoja será aleatirio enre 1 y 3 segundos. 
 * Cada robot trasladará 10 hojas antes de finalizar
 * 
 * @author Alberto Fernández
 */
public class RobotA extends Thread {

    private Semaphore A;
    private Semaphore G;
    private Random r;
    private int id;
    private CanvasP6 lienzo;
    
    public RobotA(Semaphore sA, Semaphore sG, CanvasP6 lienzo, int i) {
        this.lienzo = lienzo;
        r = new Random();
        A = sA;
        G = sG;
        id = i;
    }

    @Override
    public void run() {
       
        for (int i = 0; i < 10; i++) {
            try {
               
                A.acquire();
                cogerHoja();
                G.release();
                
            
            } catch (InterruptedException e) {
                
            }
            
        }
    }

    public void cogerHoja() throws InterruptedException {
        
        System.out.println("RobotA " + id + " coge una hoja del montón");
        lienzo.pintaEnRobot(id);
        
        sleep(abs(r.nextInt(2000)+1000));
        
        lienzo.borraEnRobot(id);
        lienzo.pintaEnMesa(id);
    }

}
