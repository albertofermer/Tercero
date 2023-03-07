package pkg2021.pkg2022;

import static java.lang.Math.abs;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Representará mediante un hilo, cada uno de los coches que llegan al túnel de
 * lavado. El hilo se creará heredando de la clase Thread. El tiempo de lavado
 * será aleatorio entre 1 y 3 segundos.
 *
 * @author Alberto Fernández
 */
public class Coche extends Thread {

    private Tunel tunel;
    private int TIEMPO_MIN = 5000;
    private int TIEMPO_MAX = 10000;
    private Random r = new Random();

    public Coche(Tunel t) {
        tunel = t;
    }

    @Override
    public void run() {

        int numeroTunel = tunel.EntraCoche();
        
        //Sección Crítica
        System.out.println("Lavando coche");
        try {
            sleep(abs(r.nextInt()%(TIEMPO_MAX-TIEMPO_MIN)+TIEMPO_MIN));
        } catch (InterruptedException ex) {
            Logger.getLogger(Coche.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        tunel.SaleCoche(numeroTunel);
    }
}
