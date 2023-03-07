package estudiantepracticas;

import IRemoto.IRevision;
import static java.lang.Math.abs;
import static java.lang.Thread.sleep;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Alberto Fernández
 */
public class EstudiantePracticas {
   

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Random ran = new Random();
        try {
            Registry Registro = LocateRegistry.getRegistry("localhost", 2015);
            String[] oferta = Registro.list();
            for (int i = 0; i < oferta.length; i++) {
                System.out.println("Elemento " + i + " del registro: " + oferta[i]);
            }
            IRevision revRemota = (IRevision) Naming.lookup("rmi://localhost:2015/revisionRemota");

            
            revRemota.entraPracticas();
            System.out.println("Estudiante " + " revisa prácticas.");
            sleep(abs(ran.nextInt() % 10000) + 5000);
            
            revRemota.salePracticas();
            
            

        } catch (NotBoundException | MalformedURLException | RemoteException | InterruptedException ex) {
            Logger.getLogger(EstudiantePracticas.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
