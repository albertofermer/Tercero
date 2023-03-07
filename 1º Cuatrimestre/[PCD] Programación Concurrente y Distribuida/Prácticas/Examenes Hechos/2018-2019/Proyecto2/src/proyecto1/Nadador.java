package proyecto1;

import static java.lang.Math.abs;
import static java.lang.Thread.sleep;
import java.util.Random;
import java.util.concurrent.Callable;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Alberto Fernández
 */
public class Nadador implements Callable<Integer> {

    private int id;
    private Random r;
    private Piscina piscina;

    public Nadador(int id, Piscina p) {
        r = new Random();
        this.id = id;
        piscina = p;
    }

    @Override
    public Integer call() {
        int tiempo = 0;
        System.out.println("Nadador número: " + id);
        try {
            //EntraPiscina
            piscina.entraPiscina(id);
        } catch (InterruptedException ex) {
            Logger.getLogger(Nadador.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            //Calentamiento//
            sleep(abs(r.nextInt() % (2000 - 1000) + 1000));
        } catch (InterruptedException ex) {
            Logger.getLogger(Nadador.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            piscina.cogeMaterial(id);
        } catch (InterruptedException ex) {
            Logger.getLogger(Nadador.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            //Usa material//
            tiempo = abs(r.nextInt() % (3000 - 2000) + 2000);
            sleep(tiempo);
        } catch (InterruptedException ex) {
            Logger.getLogger(Nadador.class.getName()).log(Level.SEVERE, null, ex);
        }

        piscina.sueltaMaterial(id);
        piscina.salePiscina(id);
        
        return tiempo;
    }

}
