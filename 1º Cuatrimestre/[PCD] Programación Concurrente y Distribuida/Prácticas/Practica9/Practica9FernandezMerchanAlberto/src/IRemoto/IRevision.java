package IRemoto;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 *
 * @author Alberto Fernández
 */
public interface IRevision extends Remote{

    void entraPracticas() throws InterruptedException, RemoteException;

    char entraTeoria() throws InterruptedException, RemoteException;

    void salePracticas() throws RemoteException;

    void saleTeoria(char quien) throws RemoteException;
    
}
