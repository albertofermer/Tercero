package IRemoto;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 *
 * @author Alberto Fernández
 */
public interface ITunel extends Remote {

    public int EntraCoche() throws InterruptedException, RemoteException;

    public int EntraFurgo() throws InterruptedException, RemoteException;

    public void SaleCoche(int i) throws RemoteException;

    public void SaleFurgo(int i) throws RemoteException;
}
