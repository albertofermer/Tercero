/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package IRemoto;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 *
 * @author pedro
 */
public interface IEjemplo extends Remote{
    public int incrementa(String quien, int valor) throws RemoteException;
}
