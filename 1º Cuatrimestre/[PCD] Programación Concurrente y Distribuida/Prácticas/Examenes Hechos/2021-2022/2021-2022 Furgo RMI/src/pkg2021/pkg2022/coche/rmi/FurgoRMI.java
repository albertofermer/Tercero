package pkg2021.pkg2022.coche.rmi;

import IRemoto.ITunel;
import static java.lang.Thread.sleep;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

/**
 *
 * @author Alberto Fernández
 */
public class FurgoRMI {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws RemoteException, NotBoundException, MalformedURLException, InterruptedException {
        Registry reg = LocateRegistry.getRegistry("localhost", 2022);
        ITunel tunel = (ITunel) Naming.lookup("rmi://localhost:2022/tunelRemoto");

        int idTunel = tunel.EntraFurgo();
        System.out.println("Lava furgo");
        sleep(5000);
        tunel.SaleFurgo(idTunel);
    }
    
}
