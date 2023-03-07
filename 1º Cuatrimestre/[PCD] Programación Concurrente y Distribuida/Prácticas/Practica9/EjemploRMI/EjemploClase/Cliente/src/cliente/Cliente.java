/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cliente;

import IRemoto.IEjemplo;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author pedro
 */
public class Cliente {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        try {
            // TODO code application logic here

             Registry Registro = LocateRegistry.getRegistry("localhost", 2015);
             //Vemos lo que ofrece el registro
             String[] oferta = Registro.list();
             for (int i = 0; i < oferta.length; i++) {
             System.out.println("Elemento " + i + " del registro: " + oferta[i]);
             }
             
            IEjemplo objrem = (IEjemplo) Naming.lookup("rmi://localhost:2015/ejemremoto");
            
            int cont=objrem.incrementa("Pedro", 5);
            System.out.println("He dejado el contador en "+cont);
            
        } catch (NotBoundException | MalformedURLException | RemoteException ex) {
            Logger.getLogger(Cliente.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
