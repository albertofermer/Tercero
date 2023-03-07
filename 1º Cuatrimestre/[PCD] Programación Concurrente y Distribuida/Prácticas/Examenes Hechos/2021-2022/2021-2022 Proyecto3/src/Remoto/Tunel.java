package Remoto;

import java.util.logging.Level;
import java.util.logging.Logger;
import java.rmi.server.UnicastRemoteObject;
import java.rmi.RemoteException;
import IRemoto.ITunel;

/**
 * Controlará la entrada de los vehículos e implementará los métodos:
 *
 * @author Alberto Fernández
 */
public class Tunel extends UnicastRemoteObject implements ITunel {

    private boolean tunelesLibres[];

    /**
     * Constructor de la clase
     */
    public Tunel() throws RemoteException {
        tunelesLibres = new boolean[3]; //Furgonetas solo pueden usar el 0 y el 2 (prioridad en el 2)

        for (int i = 0; i < tunelesLibres.length; i++) {
            tunelesLibres[i] = true;
        }
    }

    /**
     * Es invocado por los coches que quieren entrar al túnel
     *
     * @return
     * @throws java.lang.InterruptedException
     * @throws java.rmi.RemoteException
     */
    @Override
    public synchronized int EntraCoche() throws InterruptedException, RemoteException { //prioridad en el tunel 0
        while (!tunelesLibres[0] && !tunelesLibres[1] && !tunelesLibres[2]) {
            System.out.println("Coche esperando en el tunel de lavado");
            wait();
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

        System.out.println("Un coche ha ocupado el tunel " + i + ".");

        return i;
    }

    /**
     * Es invocado por las furgonetas que quieren entrar al túnel.
     *
     * @return
     * @throws java.rmi.RemoteException
     * @throws java.lang.InterruptedException
     */
    @Override
    public synchronized int EntraFurgo() throws RemoteException, InterruptedException { //prioridad en el tunel 0
        while (!tunelesLibres[0] && !tunelesLibres[2]) {
                System.out.println("Furgoneta esperando en el tunel de lavado");
                wait();
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

        System.out.println("Una furgoneta ha ocupado el tunel " + i + ".");

        return i;
    }

    /**
     * Invocado por los coches cuando salen del túnel.
     *
     * @param i
     * @throws java.rmi.RemoteException
     */
    @Override
    public synchronized void SaleCoche(int i) throws RemoteException {
        System.out.println("Un coche ha salido del tunel " + i);
        tunelesLibres[i] = true;

        notifyAll();
    }

    /**
     * Invocado por las furgonetas cuando salen del túnel.
     *
     * @param i
     * @throws java.rmi.RemoteException
     */
    @Override
    public synchronized void SaleFurgo(int i) throws RemoteException {
        System.out.println("Una furgoneta ha salido del tunel " + i);
        tunelesLibres[i] = true;
        notifyAll();
    }
}
