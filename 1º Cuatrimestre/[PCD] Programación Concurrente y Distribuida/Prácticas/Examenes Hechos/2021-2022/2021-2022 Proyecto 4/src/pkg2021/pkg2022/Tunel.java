package pkg2021.pkg2022;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Controlará la entrada de los vehículos e implementará los métodos:
 *
 * @author Alberto Fernández
 */
public class Tunel {

    private boolean tunelesLibres[];

    /**
     * Constructor de la clase
     */
    public Tunel() {
        tunelesLibres = new boolean[3]; //Furgonetas solo pueden usar el 0 y el 2 (prioridad en el 2)

        for (int i = 0; i < tunelesLibres.length; i++) {
            tunelesLibres[i] = true;
        }
    }

    /**
     * Es invocado por los coches que quieren entrar al túnel
     *
     * @param id
     * @return
     */
    public synchronized int EntraCoche(int id) { //prioridad en el tunel 0
        while (!tunelesLibres[0] && !tunelesLibres[1] && !tunelesLibres[2]) {
            try {
                System.out.println("Coche " + id +" esperando en el tunel de lavado");
                wait();
            } catch (InterruptedException ex) {
                Logger.getLogger(Tunel.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        boolean encontrado = false;
        int i = 0;
        while (!encontrado) {
            if (tunelesLibres[i]) {
                tunelesLibres[i] = false;
                encontrado = true;
            } else {
                i++;
            }
        }

        System.out.println("Coche " + id + " ha ocupado el tunel " + i + ".");

        return i;
    }

    /**
     * Es invocado por las furgonetas que quieren entrar al túnel.
     * @param id
     * @return 
     */
    public synchronized int EntraFurgo(int id) { //prioridad en el tunel 0
        while (!tunelesLibres[0] && !tunelesLibres[2]) {
            try {
                System.out.println("Furgoneta" + id + " esperando en el tunel de lavado");
                wait();
            } catch (InterruptedException ex) {
                Logger.getLogger(Tunel.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        boolean encontrado = false;
        int i = 0;
        while (!encontrado) {
            if (tunelesLibres[i] && (i == 0 || i == 2)) {
                tunelesLibres[i] = false;
                encontrado = true;
            } else {
                i++;
            }
        }

        System.out.println("Furgoneta " + id +" ha ocupado el tunel " + i + ".");

        return i;
    }

    /**
     * Invocado por los coches cuando salen del túnel.
     *
     * @param i
     * @param id
     */
    public synchronized void SaleCoche(int i, int id) {
        System.out.println("Coche " + id + " ha salido del tunel " + i);
        tunelesLibres[i] = true;

        notifyAll();
    }

    /**
     * Invocado por las furgonetas cuando salen del túnel.
     *
     * @param i
     * @param id
     */
    public synchronized void SaleFurgo(int i, int id) {
        System.out.println("Furgoneta " + id + " ha salido del tunel " + i);
        tunelesLibres[i] = true;
        notifyAll();
    }
}
