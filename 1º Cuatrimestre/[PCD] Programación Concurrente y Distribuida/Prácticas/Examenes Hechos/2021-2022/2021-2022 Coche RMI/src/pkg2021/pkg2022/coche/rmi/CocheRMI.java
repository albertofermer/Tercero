package pkg2021.pkg2022.coche.rmi;

import IRemoto.ITunel;
import static java.lang.Thread.sleep;
import java.net.MalformedURLException;
import java.rmi.RemoteException;
import java.rmi.registry.Registry;
import java.rmi.registry.LocateRegistry;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Alberto Fernández
 */
public class CocheRMI extends Thread {

    private static ITunel tunel;

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws RemoteException, NotBoundException, MalformedURLException, InterruptedException {
        Registry reg = LocateRegistry.getRegistry("localhost", 2022);
        tunel = (ITunel) Naming.lookup("rmi://localhost:2022/tunelRemoto");
        
        Thread coches[] = new CocheRMI[10];
        for (int i = 0; i < 10; i++) {
            coches[i] = new CocheRMI();
        }
        for (int i = 0; i < 10; i++) {
            coches[i].start();
        }
        for (int i = 0; i < 10; i++) {
            coches[i].join();
        }
        

    }

    @Override
    public void run() {
        int idTunel = 0;
        try {
            idTunel = tunel.EntraCoche();
        } catch (InterruptedException ex) {
            Logger.getLogger(CocheRMI.class.getName()).log(Level.SEVERE, null, ex);
        } catch (RemoteException ex) {
            Logger.getLogger(CocheRMI.class.getName()).log(Level.SEVERE, null, ex);
        }
        System.out.println("Lava coche");
        try {
            sleep(5000);
        } catch (InterruptedException ex) {
            Logger.getLogger(CocheRMI.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            tunel.SaleCoche(idTunel);
        } catch (RemoteException ex) {
            Logger.getLogger(CocheRMI.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
