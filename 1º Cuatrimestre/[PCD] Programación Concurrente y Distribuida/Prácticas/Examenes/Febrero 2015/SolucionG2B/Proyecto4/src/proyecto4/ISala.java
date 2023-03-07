/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package proyecto4;

import java.rmi.Remote;

/**
 *
 * @author pedro
 */
public interface ISala extends Remote{

    void ConcitaIN() throws Exception;

    void ConcitaOUT() throws Exception;

    void SincitaIN() throws Exception;

    void SincitaOUT() throws Exception;
    
}
