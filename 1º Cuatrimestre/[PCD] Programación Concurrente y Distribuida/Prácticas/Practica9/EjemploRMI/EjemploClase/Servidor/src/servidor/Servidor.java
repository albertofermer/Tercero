/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package servidor;

//.InetAddress;
import Remoto.Ejemplo;
import java.io.IOException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author pedro
 */
public class Servidor {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        try {
             /*
             NetworkInterface ni = NetworkInterface.getByName("eth0");
             Enumeration<InetAddress> inetAddresses = ni.getInetAddresses();


             while (inetAddresses.hasMoreElements()) {
             InetAddress ia = inetAddresses.nextElement();
             if (!ia.isLinkLocalAddress()) {
             System.out.println("IP: " + ia.getHostAddress());
             }
             }
             */

            Registry registro = LocateRegistry.createRegistry(2015);
            Ejemplo ejem = new Ejemplo();
            registro.rebind("ejemremoto", ejem);
            
            
            System.out.println("Servidor Funcionando ....");
            System.out.println("pulsa una tecla para finalizar");
            ejem.incrementa("Servidor", 10);

            System.in.read();

            System.out.println("Saliendo del servidor ...");
            System.exit(0);


        } catch (IOException ex) {
            Logger.getLogger(Servidor.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
